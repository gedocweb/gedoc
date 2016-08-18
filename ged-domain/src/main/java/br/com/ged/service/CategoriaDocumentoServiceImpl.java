package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.entidades.Categoria;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class CategoriaDocumentoServiceImpl implements CategoriaDocumentoService{
	
	@EJB
	private ConsultasDaoJpa<Categoria> reposiroty;

	@Override
	public List<Categoria> pesquisar(FiltroCategoriaDocumentoDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, Categoria.class, hbInitialize);
	}
}