package br.com.ged.domain;

/**
 * 
 * @author pedro.oliveira
 *	
 *	<p>Enum reponsável por representar o arquivo de mensagens de propriedade (messages.properties)</p>
 */
public enum Mensagem {
	
	//REGRAS DE NEGOCIO
	//MNG = Mensagem de Negócio
	MNG001(TipoMensagem.ALERTA),
	MNG002(TipoMensagem.ALERTA), 
	MNG003(TipoMensagem.ALERTA),
	MNG004(TipoMensagem.ALERTA),
	MNG005(TipoMensagem.ALERTA),
	
	//Grupo de Usuario
	GRUPOUSUARIO11(TipoMensagem.ALERTA),
	GRUPOUSUARIO12(TipoMensagem.ALERTA),
	GRUPOUSUARIO13(TipoMensagem.ALERTA),
	GRUPOUSUARIO15(TipoMensagem.ALERTA),
	
	//Categoria Documento
	CATDOC1(TipoMensagem.ALERTA),
	CATDOC2(TipoMensagem.ALERTA),
	CATDOC3(TipoMensagem.ALERTA),
	
	//tipo de documento
	TPDOC1(TipoMensagem.ALERTA),
	TPDOC2(TipoMensagem.ALERTA),
	
	//painel Documento
	DOCPESQUISA1(TipoMensagem.ALERTA),
	DOCPESQUISA2(TipoMensagem.ALERTA),
	
	//Cadastro doc
	DOCCADASTRO1(TipoMensagem.ALERTA),
	DOCCADASTRO2(TipoMensagem.ALERTA),
	DOCCADASTRO3(TipoMensagem.ALERTA),
	DOCCADASTRO4(TipoMensagem.ALERTA),
	DOCCADASTRO5(TipoMensagem.ALERTA),

	
	//ERROS INTERNOS DO SISTEMA
	//MEI = Mensagem de Erro Interno
	
	MEI001(TipoMensagem.INTERNO),
	MEI002(TipoMensagem.INTERNO),
	MEI003(TipoMensagem.INTERNO),
	MEI004(TipoMensagem.INTERNO),
	MEI005(TipoMensagem.INTERNO),
	MEI006(TipoMensagem.INTERNO),
	MEI007(TipoMensagem.INTERNO),
	MEI008(TipoMensagem.INTERNO),
	MEI009(TipoMensagem.INTERNO),
	MEI010(TipoMensagem.INTERNO),
	MEI011(TipoMensagem.INTERNO),
	MEI012(TipoMensagem.INTERNO),
	MEI013(TipoMensagem.INTERNO),
	
	//MENSAGENS PADRÃO (DEFAULT)
	//MDF = Mensagem Default
	
	MDF001(TipoMensagem.DEFAULT), 
	
	//MENSAGENS ESTATICAS NO XHTML
	//MSI = Mensagens de Interface
	
	MSI003(TipoMensagem.ESTATICO),
	MSI043(TipoMensagem.ESTATICO), 
	MSI044(TipoMensagem.ESTATICO), 
	
	MSA001(TipoMensagem.ESTATICO),
	MSA002(TipoMensagem.ESTATICO),
	MSA003(TipoMensagem.ESTATICO),
	MSA004(TipoMensagem.ESTATICO);
	
	private TipoMensagem tipo;
	
	private Mensagem(TipoMensagem tipoMensagem) {
		
		this.tipo = tipoMensagem;
	}

	public TipoMensagem getTipo() {
		return tipo;
	}
}