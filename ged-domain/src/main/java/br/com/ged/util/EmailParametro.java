package br.com.ged.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.excecao.NegocioException;

public class EmailParametro {

	private String chaveParametro;
	private String valorParametro;
	
	private List<EmailParametro> listParametros;
	
	public EmailParametro(){}
	
	public EmailParametro(String chaveParametro, String valorParametro) throws NegocioException{
		
		parametrosObrigatorios(chaveParametro, valorParametro);
		
		this.chaveParametro = chaveParametro;
		this.valorParametro = valorParametro;
	}
	
	public void addParametro(String chaveParametro, String valorParametro) throws NegocioException{
		
		parametrosObrigatorios(chaveParametro, valorParametro);
		
		if (listParametros == null || listParametros.isEmpty()){
			listParametros = new ArrayList<EmailParametro>();
		}
		
		listParametros.add(new EmailParametro(chaveParametro, valorParametro));
	}

	private void parametrosObrigatorios(String chaveParametro,String valorParametro) throws NegocioException {
		if (StringUtils.isBlank(chaveParametro) || StringUtils.isBlank(valorParametro)){
			throw new NegocioException(Mensagem.MEI013);
		}
	}

	public List<EmailParametro> getListParametros() {
		return listParametros;
	}

	public String getChaveParametro() {
		return chaveParametro;
	}

	public String getValorParametro() {
		return valorParametro;
	}
}
