package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class GrupoUsuarioServiceImpl implements GrupoUsuarioService{
	
	@EJB
	private ConsultasDaoJpa<GrupoUsuario> reposiroty;

	@Override
	public List<GrupoUsuario> pesquisar(FiltroGrupoUsuarioDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, GrupoUsuario.class, hbInitialize);
	}
}