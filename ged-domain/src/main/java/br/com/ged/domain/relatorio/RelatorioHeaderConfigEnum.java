package br.com.ged.domain.relatorio;


public enum RelatorioHeaderConfigEnum implements HeaderContentDisposition {
	
	CONTENT_DISPOSITION,
	
	CACHE_CONTROL("Cache-Control","max-age=60");
	
	private String chave;
	private String valor;
	
	private RelatorioHeaderConfigEnum(){
		//nothing to do
	}
	
	private RelatorioHeaderConfigEnum(String chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}
	
	public String getValor() {
		return valor;
	}

	@Override
	public String getAttachmentFile(RelatorioEnum relatorio) {
		
		StringBuilder attachmentFile = new StringBuilder("attachment; filename=\"");
		
		attachmentFile.append(relatorio.getNomeArquivo());
		
		TipoRelatorioEnum TIPO_RELATORIO = relatorio.getTipoRelatorio();
		
		switch (TIPO_RELATORIO) {
		
			case PDF:
				attachmentFile.append(".pdf\"");
				break;
			case XLS:
				attachmentFile.append(".xls\"");
				break;
			case HTML:
				attachmentFile.append(".html\"");
				break;
		}
		
		return attachmentFile.toString();
	}
}