package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.service.GenericService;

@Stateless
public class UsuarioServiceImpl extends ConsultasDaoJpa<Usuario> implements UsuarioService{
	
	@EJB
	private GenericService<Usuario, Long> serviceGeneric;
	
	@Override
	public List<Usuario> pesquisar(FiltroUsuarioDTO filtro) {
		return filtrarPesquisa(filtro, Usuario.class);
	}

	@Override
	public Usuario usuarioPorUsername(String username) {
		FiltroUsuarioDTO filtro = new FiltroUsuarioDTO();
		
		filtro.setUsuario(username);
		
		return primeiroRegistroPorFiltro(filtro, Usuario.class,"pessoa");
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void excluir(Long id) {
		
		Query qr = em.createNativeQuery("DELETE FROM rl_grupousuario_usuario WHERE id_usuario = :idUsuario");
		qr.setParameter("idUsuario", id);
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		qr.executeUpdate();
		serviceGeneric.excluir(usuario);
	}
}