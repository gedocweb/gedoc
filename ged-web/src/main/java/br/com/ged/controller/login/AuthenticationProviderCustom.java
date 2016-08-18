package br.com.ged.controller.login;

import static br.com.ged.util.InitMessageProperties.getValue;

import javax.ejb.EJBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import br.com.ged.domain.Mensagem;
import br.com.ged.login.service.AutorizacaoService;
import br.com.ged.util.container.AtributoSessao;
import br.com.ged.util.criptografia.CriptografiaUtil;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Classe que realiza a autenticação via Spring Security de maneira customizada a partir da implementação da interface AuthenticationProvider.java </p>
 *
 * <p>Controlada pelo spring a partir do arquivo de configuração applicationContext.xml </>
 * 
 */
public class AuthenticationProviderCustom implements AuthenticationProvider {
	
	/**
	 * Injeta interface EJB a partir do contexto do spring, configuração feita no applicationContext.xml (Pela tag: jee:local-slsb)
	 */
	@Autowired
	private AutorizacaoService usuarioService;
	
	/**
	 * Método sobrescrito da interface do Spring Security AuthenticationProvider que deve retornar um objeto Authentication preenchido
	 */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	 
    	Authentication auth = null;
    	
    	if (autenticacaoComSucesso(authentication)) {
         	
         	auth = criaAutenticacao(authentication);
    	}
    	
        return auth;
    }

    /**
     * Método realiza as seguintes funções:
     * 
     * <ul>
     * 	<li>Verifica se usuário existe na base de dados</li>
     * 	<li>Verifica se a senha do usuário no banco de dados é igual a informada no formulário</li>
     *  <li>Adiciona as permissões do usuário em um atributo de sessão (HttpSession)</li>
     * </ul>
     * 
     * @param authentication vem do contexto do spring
     * @return true se o usuário estiver autenticado, false caso contrário
     */
	private boolean autenticacaoComSucesso(Authentication authentication)  {
		
		UserDetails userDetails = null;
		
		try{
			userDetails = usuarioService.loadUserByUsername(authentication.getName());
		}catch(EJBException e){
			throw new UsernameNotFoundException(getValue(Mensagem.MNG003), e);
		}
		
		String senhaCriptografada = CriptografiaUtil.criptografar(authentication.getCredentials());
		
		if (!userDetails.getPassword().equals(senhaCriptografada)){
			
			throw new BadCredentialsException(getValue(Mensagem.MNG002));
		}
		
		return adicionaPermissoesUsuarioNaSessao(userDetails);
	}

	/**
	 * Método obtem as autorizações do usuário userDetails.getAuthorities(),
	 * transforma em Json, criptografa e adiciona em um atributo de sessão (HttpSession).
	 * 
	 * @param userDetails recebe as autorizações que vieram do banco na coluna Role
	 * @return Caso tudo tenha ocorrido bem, o método retorna true para finalizar a autenticação do usuário, caso contrário false.
	 */
    private boolean adicionaPermissoesUsuarioNaSessao(UserDetails userDetails)  {
    	
    	Boolean isUsuarioNaSessao = Boolean.FALSE;
    	
    	try{
    		
    		String permissoesUsuario = CriptografiaUtil.criptografar(userDetails.getAuthorities());
    		
    		RequestAttributes mapAtributos = RequestContextHolder.currentRequestAttributes();
    		mapAtributos.setAttribute(AtributoSessao.PERMISSOES_USUARIO.name(), permissoesUsuario, RequestAttributes.SCOPE_SESSION);
    		mapAtributos.setAttribute(AtributoSessao.USUARIO.name(), userDetails.getUsername(), RequestAttributes.SCOPE_SESSION);
    		
    		isUsuarioNaSessao = Boolean.TRUE;
    		
    	}catch(Exception ex){
    		
    		throw new AuthenticationServiceException(getValue(Mensagem.MEI011), ex);
    	}
    	
		return isUsuarioNaSessao;
	}

	@Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    protected Authentication criaAutenticacao(Authentication authentication) {
    	
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }
}