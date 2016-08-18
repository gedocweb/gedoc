package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroTipoDocumentoDTO;
import br.com.ged.entidades.TipoDocumento;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface TipoDocumentoService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<TipoDocumento> pesquisar(FiltroTipoDocumentoDTO tpFiltroDoc, String...strings);
}
