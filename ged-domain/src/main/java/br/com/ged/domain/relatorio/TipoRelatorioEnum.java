package br.com.ged.domain.relatorio;

import java.io.Serializable;



/**
 * Enum TipoRelatorioEnum.
 */
public enum TipoRelatorioEnum implements Serializable {
	
	/** The pdf. */
	PDF("application/pdf"),
	
	/** The xls. */
	XLS("application/vnd.ms-excel"),
	
	/** The html. */
	HTML("text/html; charset=utf-8");
	
	private String contentType;
	
	private TipoRelatorioEnum(String contentType){
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
}