package br.com.ged.domain;

public enum Sexo {

	M("Masculino"),
	F("Feminino");
	
	private String value;
	
	private Sexo(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}