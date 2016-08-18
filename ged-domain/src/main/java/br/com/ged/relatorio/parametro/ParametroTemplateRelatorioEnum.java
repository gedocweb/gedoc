package br.com.ged.relatorio.parametro;

public enum ParametroTemplateRelatorioEnum {

	TITULO("titulo","Pessoas");
	
	private String valor;
	private String chave;
	
	private ParametroTemplateRelatorioEnum(String chave, String valor){
		
		this.chave = chave;
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public String chave() {
		return chave;
	}
}