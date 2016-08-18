package br.com.ged.util.relatorio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import br.com.ged.domain.Mensagem;
import br.com.ged.domain.relatorio.HeaderContentDisposition;
import br.com.ged.domain.relatorio.RelatorioEnum;
import br.com.ged.domain.relatorio.RelatorioHeaderConfigEnum;
import br.com.ged.domain.relatorio.RelatorioPath;
import br.com.ged.excecao.NegocioException;

public class RelatorioUtil extends AbstractRelatorioUtil{

	public RelatorioUtil(HttpServletRequest request, HttpServletResponse response) throws NegocioException{
		super(request, response);
	}
	
	public byte[] exportarRelatorioPDFComoArrayBytes(RelatorioEnum relatorio,Map<String, Object> parametros, Collection<?> dados) throws NegocioException {
		
		JasperPrint print = gerarJasperPrint(relatorio, parametros, dados);
		byte[] binario = null;
		
		if (print != null) {
			
			try {
				binario =  JasperExportManager.exportReportToPdf(print);
			} catch (Exception e) {
				throw new NegocioException(Mensagem.MEI003, e);
			}
		}
		
		return binario;
	}

	public void gerarRelatorio(RelatorioEnum relatorio, AbstractRelatorioParametro parametros) throws NegocioException{
		
		Map<String, Object> params = addParametros(parametros.getParametrosRelatorio());
		Collection<?> dados = parametros.getDataSourceList();
		
		verificaRelatorioInformado(relatorio);
		
		configResponseHeader(relatorio);
		
		exportarRelatorio(relatorio, params, dados);
	}
	
	private void configResponseHeader(RelatorioEnum relatorio) {
		
		HeaderContentDisposition contentDisposition = RelatorioHeaderConfigEnum.CONTENT_DISPOSITION;
		
		response.setHeader(HeaderContentDisposition.CONTENT_DISPOSITION, contentDisposition.getAttachmentFile(relatorio));
		response.setContentType(relatorio.getTipoRelatorio().getContentType());
	}

	private void verificaRelatorioInformado(RelatorioEnum relatorio) throws NegocioException {
		
		if (relatorio == null){
			throw new NegocioException(Mensagem.MEI005);
		}
	}

	private ServletOutputStream getOutputStream() throws NegocioException {
		
		ServletOutputStream outputStream = null;
		
		try {
			outputStream = response.getOutputStream();
		} catch (Exception e) {
			throw new NegocioException(Mensagem.MEI004, e);
		}
		
		return outputStream;
	}
	
	private Map<String, Object> addParametros(Map<String, Object> parametros) {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		if (parametros != null){
			mapParam.putAll(parametros);
		}
		
		mapParam.put(RelatorioPath.PATH_RELATORIO, recuperaContexto());
		
		return mapParam;
	}

	private void exportarRelatorio(RelatorioEnum relatorio, Map<String, Object> parametros, Collection<?> dados) throws NegocioException {
		
		JasperPrint print = gerarJasperPrint(relatorio, parametros, dados);
		OutputStream out = null;

		if (print == null) {
			
			throw new NegocioException(Mensagem.MEI007);
		}
			
		try {
			
			out = getOutputStream();
			
			switch (relatorio.getTipoRelatorio()) {
			
				case HTML:
					exportarHTML(print, out);
					break;

				case PDF:
					exportarPDF(print, out);
					break;

				case XLS:
					exportarXLS(print, out);
					break;
				default:
					break;
			}

		} catch (Exception e) {
			fecharConexaoStream(out);
		} finally {
			fecharConexaoStream(out);
		}
	}

	private void exportarXLS(JasperPrint print, OutputStream out) throws JRException {
		
		JRXlsExporter xlsExporter = new JRXlsExporter();
		
		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		xlsExporter.exportReport();
	}

	private void exportarPDF(JasperPrint print, OutputStream out) throws JRException {
		
		JasperExportManager.exportReportToPdfStream(print, out);
	}

	private void exportarHTML(JasperPrint print, OutputStream out) throws JRException {
		
		JRHtmlExporter htmlExporter = new JRHtmlExporter();
		
		htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
		htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		htmlExporter.exportReport();
	}

	private void fecharConexaoStream(OutputStream out) throws NegocioException {
		
		try {
			
			out.flush();
			out.close();
			
		} catch (IOException ioe) {
			throw new NegocioException(Mensagem.MEI006, ioe);
		}
	}

	private String absolutePath(RelatorioEnum relatorio) throws NegocioException {
		
		verificaRelatorioInformado(relatorio);
		
		return recuperaContexto().concat(relatorio.getPathFileXml());
	}

	private JasperPrint gerarJasperPrint(RelatorioEnum relatorio,Map<String, Object> parametros, Collection<?> dados) throws NegocioException {
		
		JasperPrint impressao = null;
		
		verificaRelatorioInformado(relatorio);
		
		try {
			
			JasperReport relatorioCompilado = JasperCompileManager.compileReport(absolutePath(relatorio));  
		      
		    impressao = JasperFillManager.fillReport(relatorioCompilado, parametros, new JRBeanCollectionDataSource(dados));  
			
		} catch (JRException e) {
			throw new NegocioException(Mensagem.MEI008, e);
		}
		return impressao;
	}
}
