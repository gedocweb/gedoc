package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroTipoDocumentoDTO;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class TipoDocumentoServiceImpl implements TipoDocumentoService{
	
	@EJB
	private ConsultasDaoJpa<TipoDocumento> reposiroty;

	@Override
	public List<TipoDocumento> pesquisar(FiltroTipoDocumentoDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, TipoDocumento.class, hbInitialize);
	}
}