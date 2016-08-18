package br.com.ged.domain.relatorio;

import java.io.File;

public enum RelatorioEnum implements RelatorioPath{
	
	BALANCO_GASTO("balancoGasto", TipoRelatorioEnum.PDF,"balanco_gasto");
	
	private static final String EXTENSAO_JRXML =".jrxml";
	private static final String EXTENSAO_JASPER =".jasper";
	
	private String nomeArquivo;
	private TipoRelatorioEnum tipoRelatorio;
	private String path;
	
	private RelatorioEnum(String nomeArquivo,TipoRelatorioEnum tipoRelatorio, String path){
		this.nomeArquivo = nomeArquivo;
		this.tipoRelatorio = tipoRelatorio;
		this.path = path;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public TipoRelatorioEnum getTipoRelatorio() {
		return tipoRelatorio;
	}

	public String getPath() {
		return path;
	}
	
	public String getPathFileCompilado() {
		return path + File.separator + getNomeArquivoJasper();
	}
	
	public String getPathFileXml() {
		return path + File.separator + getNomeArquivoJrxml();
	}
	
	public String getNomeArquivoJrxml() {
		return nomeArquivo + EXTENSAO_JRXML;
	}

	public String getNomeArquivoJasper() {
		return nomeArquivo + EXTENSAO_JASPER;
	}

	@Override
	public String getPathRaiz() {
		return PATH_RAIZ;
	}
}