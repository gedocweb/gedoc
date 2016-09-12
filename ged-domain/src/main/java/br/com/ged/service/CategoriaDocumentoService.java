package br.com.ged.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.entidades.Categoria;
import br.com.ged.excecao.NegocioException;

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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	void excluir(Categoria categoriaSelecionada) throws NegocioException;
}
