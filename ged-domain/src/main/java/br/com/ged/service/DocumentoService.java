package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroDocumentoDTO;
import br.com.ged.entidades.Documento;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface DocumentoService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<Documento> pesquisar(FiltroDocumentoDTO filtroDoc, String...strings);
}
