package br.com.ged.domain;

/**
 * 
 * @author pedro.oliveira
 * 
 * Enum responsável por centralizar todos os caminhos das páginas jsf para serem utilizados como objetos java.
 *
 */
public enum Pagina {
	
	TODAS,
	LOGIN("/login.jsf"),
	
	PAINEL_ADMIN("/pages/admin/painelAdmin.jsf"),
	
	//usuario
	PAINEL_USUARIO("/pages/admin/usuario/painelUsuario.jsf"), 
	CADASTRAR_USUARIO("/pages/admin/usuario/cadastrarUsuario.jsf"),
	ALTERAR_USUARIO("/pages/admin/usuario/alterarUsuario.jsf"),
	
	PAINEL_GRUPO_USUARIO("/pages/admin/grupoUsuario/painelGrupoUsuario.jsf"), 
	CADASTRAR_GRUPO_USUARIO("/pages/admin/grupoUsuario/cadastrarGrupoUsuario.jsf"),
	
	PAINEL_DOCUMENTO("/pages/admin/documento/painelDocumento.jsf");
	
	private String valor;
	
	private Pagina(){
		//nada a fazer.
	}
	
	private Pagina(String valor){
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}