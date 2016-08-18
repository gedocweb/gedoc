package br.com.ged.login.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class AutorizacaoServiceImpl implements UserDetailsService {

	@EJB
	private ConsultasDaoJpa<Usuario> consultaDao;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		
		FiltroUsuarioDTO filtro = new FiltroUsuarioDTO();
		
		filtro.setUsuario(username);
	
		final Usuario ususario = consultaDao.primeiroRegistroPorFiltro(filtro, Usuario.class,"grupoUsuario");
		
		if (ususario == null){
			throw new UsernameNotFoundException(Mensagem.MNG003.name());
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		authorities.add(new SimpleGrantedAuthority(ususario.getRole().name()));

		return buildUserForAuthentication(ususario, authorities);
	}

	private User buildUserForAuthentication(Usuario user, List<GrantedAuthority> authorities) {
		return new User(user.getUsuario(), user.getSenha(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, authorities);
	}
}