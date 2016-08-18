package br.com.ged.admin.usuario;

import javax.ejb.EJB;

import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractManageBean;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.util.criptografia.CriptografiaUtil;

public abstract class UsuarioSuperController extends AbstractManageBean{
	
	private Usuario usuario;
	
	@EJB
	protected GenericServiceController<Usuario, Long> service;
	
	@EJB
	protected UsuarioValidadorView validacaoUsuario;
	
	protected FiltroUsuarioDTO filtroUsuarioDTO;
	
	protected String confirmarSenha;
	
	private boolean usuarioAdmin;
	
	protected void cadastrar() throws NegocioException{
		
		validacaoUsuario.confirmaSenha(usuario, getConfirmarSenha());
		
		String senhaCriptografada = CriptografiaUtil.criptografar(usuario.getSenha());
		
		usuario.setSenha(senhaCriptografada);
		
		service.salvar(usuario);
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	public FiltroUsuarioDTO getFiltroUsuarioDTO() {
		return filtroUsuarioDTO;
	}

	public void setFiltroUsuarioDTO(FiltroUsuarioDTO filtroUsuarioDTO) {
		this.filtroUsuarioDTO = filtroUsuarioDTO;
	}
	
	public boolean getUsuarioAdmin() {
		return usuarioAdmin;
	}
}