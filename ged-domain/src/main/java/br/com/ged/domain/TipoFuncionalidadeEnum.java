package br.com.ged.domain;

/**
 * 
 * @author pedro.oliveira
 *	
 *	<p>Utilizado para saber qual funcionalidade está sendo executada para realizar a autorização</p>
 *
 *	<p>Esse enum deve ser utilizado na página Xhtml/jsf para acessar o método na classe AbstractManageBean.java o método autorizaFuncionalidade(TipoFuncionalidadeEnum)</p>
 *	
 *	<p>Exemplo de implementação no arquivo xhtml:</p>
 *	
 *	<ul>
 *		<li>rendered="#{manageBean.autorizaFuncionalidade('CADASTRAR_PESSOA')}"</li>
 *	</ul>
 *
 *	<p><b>NOTA: O managebean em execução deve extender a classe AbstractManageBean</b></p>
 */
public enum TipoFuncionalidadeEnum {

	//Grupo Usuario
	CADASTRAR_GRUPO_USUARIO("Cadastro de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO),
	PESQUISAR_GRUPO_USUARIO("Pesquisa de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO);
	
	private String label;
	private FuncionalidadeEnum funcionalidade;
	
	private TipoFuncionalidadeEnum(String label, FuncionalidadeEnum funcionalidade){
		
		this.label = label;
		this.funcionalidade = funcionalidade;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public FuncionalidadeEnum getFuncionalidade() {
		return funcionalidade;
	}
}