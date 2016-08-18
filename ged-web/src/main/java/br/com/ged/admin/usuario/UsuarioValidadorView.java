package br.com.ged.admin.usuario;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class UsuarioValidadorView extends AbstractValidacao{
	
	public void confirmaSenha(Usuario usuario, String confirmarSenha) throws NegocioException {
//		
//		camposObrigatorios(usuario, confirmarSenha);
//		
//		verificaSenhasInformadas(usuario, confirmarSenha);
		
//		if (!usuario.getSenha().equals(confirmarSenha)){
//			throw new NegocioException(Mensagem.MNG005);
//		}
	}

//	private void camposObrigatorios(Usuario usuario,String confirmarSenha) throws NegocioException {
//		
//		if (StringUtils.isBlank(usuario.getUsuario())){
//			throw new NegocioException(Mensagem.MNG037);
//		}
//		
//		if (StringUtils.isBlank(usuario.getSenha())){
//			throw new NegocioException(Mensagem.MNG038);
//		}
//		
//		if (StringUtils.isBlank(confirmarSenha)){
//			throw new NegocioException(Mensagem.MNG040);
//		}
//	}
//
//	private void verificaSenhasInformadas(Usuario usuario, String confirmarSenha)throws NegocioException {
//		
//		if (preCondicaoDiferentesNull(usuario, confirmarSenha)){
//			
//			throw new NegocioException(Mensagem.MNG004);
//		}
//	}
//
//	private boolean preCondicaoDiferentesNull(Usuario usuario,String confirmarSenha) {
//		return usuario == null || usuario.getSenha() == null || confirmarSenha == null;
//	}

}