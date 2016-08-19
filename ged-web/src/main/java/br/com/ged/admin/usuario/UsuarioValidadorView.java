package br.com.ged.admin.usuario;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;
import br.com.ged.service.UsuarioService;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class UsuarioValidadorView extends AbstractValidacao{
	
	@EJB
	private UsuarioService usuarioService;
	
	public void validaCadastro(Usuario usuario, String confirmarSenha) throws NegocioException {
		
		camposObrigatorios(usuario);
		
		validaSenha(usuario, confirmarSenha);
		
		FiltroUsuarioDTO filtro = new FiltroUsuarioDTO();
		filtro.setUsuario(usuario.getUsuario());
		
		List<Usuario> list = usuarioService.pesquisar(filtro);
		
		if (!listVazia(list)){
			throw new NegocioException(Mensagem.USU08);
		}
	}
	
	public void validaAlteracao(Usuario usuario) throws NegocioException {
		
		camposObrigatorios(usuario);
		
		validaNomeUsuarioJaExistente(usuario);
	}

	private void validaNomeUsuarioJaExistente(Usuario usuario) throws NegocioException {
		
		FiltroUsuarioDTO filtro = new FiltroUsuarioDTO();
		filtro.setUsuario(usuario.getUsuario());
		
		List<Usuario> list = usuarioService.pesquisar(filtro);
		
		if (!listVazia(list)){
			
			if (!list.iterator().next().getId().equals(usuario.getId())){
				throw new NegocioException(Mensagem.USU08);
			}
		}
	}

	public void validaSenha(Usuario usuario, String confirmarSenha) throws NegocioException {
		
		verificaSenhasInformadas(usuario, confirmarSenha);
		
		if (!usuario.getSenha().equals(confirmarSenha)){
			throw new NegocioException(Mensagem.USU01);
		}
	}

	private void camposObrigatorios(Usuario usuario) throws NegocioException {
		
		if (usuario.getRole() == null){
			throw new NegocioException(Mensagem.USU03);
		}
		
		if (StringUtils.isBlank(usuario.getPessoa().getNome())){
			throw new NegocioException(Mensagem.USU04);
		}
		
		if (StringUtils.isBlank(usuario.getPessoa().getCpf())){
			throw new NegocioException(Mensagem.USU05);
		}
		
		if (StringUtils.isBlank(usuario.getPessoa().getCelular())){
			throw new NegocioException(Mensagem.USU06);
		}
		
		if (StringUtils.isBlank(usuario.getUsuario())){
			throw new NegocioException(Mensagem.USU07);
		}
		
//		if (StringUtils.isBlank(usuario.getSenha())){
//			throw new NegocioException(Mensagem.USU09);
//		}
	}

	private void verificaSenhasInformadas(Usuario usuario, String confirmarSenha)throws NegocioException {
		
		if (preCondicaoDiferentesNull(usuario, confirmarSenha)){
			
			throw new NegocioException(Mensagem.USU01);
		}
	}

	private boolean preCondicaoDiferentesNull(Usuario usuario,String confirmarSenha) {
		return usuario == null || 
				senhaDiferenteNull(usuario, confirmarSenha) || 
				senhaDiferenteVazio(usuario, confirmarSenha);
	}

	private boolean senhaDiferenteVazio(Usuario usuario, String confirmarSenha) {
		return StringUtils.isBlank(usuario.getSenha()) || StringUtils.isBlank(confirmarSenha);
	}

	private boolean senhaDiferenteNull(Usuario usuario, String confirmarSenha) {
		return usuario.getSenha() == null || confirmarSenha == null;
	}
}