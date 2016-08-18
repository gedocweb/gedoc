package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface GrupoUsuarioService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<GrupoUsuario> pesquisar(FiltroGrupoUsuarioDTO filtro, String... hibernateInitialize);
}
