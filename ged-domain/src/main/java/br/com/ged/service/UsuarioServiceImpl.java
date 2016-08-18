package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class UsuarioServiceImpl implements UsuarioService{
	
	@EJB
	private ConsultasDaoJpa<Usuario> reposiroty;

	@Override
	public List<Usuario> pesquisar(FiltroUsuarioDTO filtro) {
		return reposiroty.filtrarPesquisa(filtro, Usuario.class);
	}

	@Override
	public Usuario usuarioPorUsername(String username) {
		FiltroUsuarioDTO filtro = new FiltroUsuarioDTO();
		
		filtro.setUsuario(username);
		
		return reposiroty.primeiroRegistroPorFiltro(filtro, Usuario.class,"pessoa");
	}
}