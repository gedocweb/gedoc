package br.com.ged.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 
 * @author pedro.oliveira
 *
 *	<p> Enum utilizado para autorizar aceasso as páginas xhtml/jsf </p>
 *
 *	<p> O papel desse enum é associar o enum Role.java para ter acesso a vários enum Pagina.java</p>
 *
 *	<p> Utilizado na classe AutorizacaoManageBean.java e na sub classe AbstractManageBean.java </p>
 *
 *	<p> Obs.: Caso não precise de Role.java para acessar a página, estas devem ser definidas na autorização AutorizacaoEnum.ANONIMO.</p>
 */
public enum AutorizacaoEnum {

	/**
	 * ANONIMO - Recebe várias páginas que possuem acesso público, ou seja, não precisa de usário logado para serem acessadas.
	 */
	ANONIMO(Pagina.LOGIN),
	
	/**
	 * ADMINISTRADOR - Tem acesso a todas as páginas com a Role.ADMIN.
	 */
	ADMINISTRADOR(Role.ADMIN, Pagina.TODAS),
							  
	GERENTE(Role.GERENTE, Pagina.TODAS);
	
	private Pagina[] paginasComPermissao;
	private Role role;
	
	/**
	 * 
	 * @param paginasComPermissao
	 * 
	 * Construtor referente ao enum ANONIMO
	 */
	private AutorizacaoEnum(Pagina... paginasComPermissao){
		
		this.paginasComPermissao = paginasComPermissao;
	}
	
	/**
	 * 
	 * @param paginasComPermissao
	 * 
	 * Construtor referente a todas as autorizações que tenham alguma Role.java
	 */
	private AutorizacaoEnum(Role role, Pagina... paginasComPermissao){
		
		this.role = role;
		this.paginasComPermissao = paginasComPermissao;
	}
	
	/**
	 * <p>Verifica se página possui acesso anônimo.</p>
	 * 
	 * <p>Se usuario estiver logado, verifica se página que está tentando ser acessada está dentro das permissões do usuário.</p>
	 * 
	 * @param paginaAtual - Página do ManageBean em execução
	 * @param permissoesUsuarioLogado - Roles do usuário obtidas a partir do Spring Security ({@code List<SimpleGrantedAuthority>.java})
	 * @return Autorização do Usuário
	 */
	public static AutorizacaoEnum usuarioComAcesso(Pagina paginaAtual, List<SimpleGrantedAuthority> permissoesUsuarioLogado) {
		
		AutorizacaoEnum usuarioComAcesso = null;
		
		if (paginaComAcessoAnonimo(paginaAtual)){
			
			usuarioComAcesso = AutorizacaoEnum.ANONIMO;
			
		}else if (permissoesUsuarioLogado != null){
			
			for (GrantedAuthority role : permissoesUsuarioLogado){
				
				if (rolesDisponiveisParaPagina(paginaAtual).contains(role.getAuthority())){
					usuarioComAcesso = autorizacaoPorRole(role.getAuthority());
					break;
				}
			}
		}
		
		return usuarioComAcesso;
	}

	private static AutorizacaoEnum autorizacaoPorRole(String role) {
		
		AutorizacaoEnum autorizacao = null;
		
		for (AutorizacaoEnum auth : values()){
			
			if (auth != AutorizacaoEnum.ANONIMO && auth.role.name().equals(role)){
				
				autorizacao = auth;
				break;
			}
		}
		
		return autorizacao;
	}

	private static boolean paginaComAcessoAnonimo(Pagina paginaAtual) {
		
		boolean permissaoAnonima = Boolean.FALSE;
		
		for (Pagina pagina : ANONIMO.paginasComPermissao){
			
			if (pagina.equals(paginaAtual)){
				
				permissaoAnonima = Boolean.TRUE;
				break;
			}
		}
		
		return permissaoAnonima;
	}
	
	private static List<String> rolesDisponiveisParaPagina(Pagina pagina){
		
		List<String> autorizacaoDisponivel = new ArrayList<String>();
		
		novaAutorizacao:
		for (AutorizacaoEnum autorizacao : values()){
			
			for (Pagina paginaDisponivel : autorizacao.paginasComPermissao){
				
				if (paginaDisponivel.equals(pagina) || paginaDisponivel.equals(Pagina.TODAS)){
					
					autorizacaoDisponivel.add(autorizacao.role.name());
					continue novaAutorizacao;
				}
			}
		}
		
		return autorizacaoDisponivel;
	}
}