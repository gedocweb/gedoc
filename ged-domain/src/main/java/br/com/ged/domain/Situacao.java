package br.com.ged.domain;

public enum Situacao {

	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	private Situacao(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}