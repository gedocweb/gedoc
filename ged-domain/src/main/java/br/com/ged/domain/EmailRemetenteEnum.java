package br.com.ged.domain;


public enum EmailRemetenteEnum {
	
	REMETENTE_EMAIL_ESTOQUE("controle.estoque");
	
	//Chave do arquivo de configuração email.properties
	private String chave;
	
	EmailRemetenteEnum(String chave){
		this.chave = chave;
	}

	public String getChave() {
		return chave;
	}
}