package br.com.ged.domain.relatorio;

public interface HeaderContentDisposition {

	String CONTENT_DISPOSITION = "Content-Disposition";

	String getAttachmentFile(RelatorioEnum relatorio);
}
