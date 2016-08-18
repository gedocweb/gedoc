package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.entidades.Categoria;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface CategoriaDocumentoService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<Categoria> pesquisar(FiltroCategoriaDocumentoDTO tpFiltroDoc, String...strings);
}
