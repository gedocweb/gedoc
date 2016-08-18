package br.com.ged.util.relatorio;

import java.util.List;
import java.util.Map;

import br.com.ged.excecao.NegocioException;

public abstract class AbstractRelatorioParametro {
	
	private List<?> dataSourceList;
	
	public AbstractRelatorioParametro(List<?> dataSourceList){
		this.dataSourceList = dataSourceList;
	}

	protected abstract Map<String,Object> getParametrosRelatorio() throws NegocioException;
	
	protected List<?> getDataSourceList() {
		return dataSourceList;
	}
}
