package br.com.ged.util.relatorio;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.relatorio.RelatorioEnum;
import br.com.ged.domain.relatorio.RelatorioPath;
import br.com.ged.excecao.NegocioException;

public abstract class AbstractRelatorioUtil {
	
	protected static final RelatorioPath PATH_RAIZ = RelatorioEnum.BALANCO_GASTO;
	
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	protected AbstractRelatorioUtil(HttpServletRequest request, HttpServletResponse response) throws NegocioException{
		this.request = request;
		this.response = response;
		
		initPathRelatorio();
	}
	
	protected String recuperaContexto() {
		return request.getServletContext().getRealPath(File.separator).concat(PATH_RAIZ.getPathRaiz());
	}
	
	private void initPathRelatorio() throws NegocioException{
		
		File file = new File(recuperaContexto());
		
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new NegocioException(Mensagem.MEI002, e);
			}
		}
	}
}
