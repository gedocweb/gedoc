package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface UsuarioService {

	/**
	 * 
	 * @param filtro
	 * @return
	 */
	List<Usuario> pesquisar(FiltroUsuarioDTO filtro);

	Usuario usuarioPorUsername(String objUsuarioSessao);

	void excluir(Long id);
}
