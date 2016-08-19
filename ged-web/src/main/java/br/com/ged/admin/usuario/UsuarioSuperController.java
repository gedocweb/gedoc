package br.com.ged.admin.usuario;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import br.com.ged.domain.Role;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
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
	
	@EJB
	protected GenericServiceController<GrupoUsuario, Long> serviceGrupoUsuario;
	
	protected List<SelectItem> listGrupoUsuario;
	
	protected void cadastrar() throws NegocioException{
		
		validacaoUsuario.validaCadastro(usuario, getConfirmarSenha());
		
		String senhaCriptografada = CriptografiaUtil.criptografar(usuario.getSenha());
		
		usuario.setSenha(senhaCriptografada);
		
		service.salvar(usuario);
	}
	
	public List<SelectItem> getRolesSelectItem(){
		
		if (!getUsuarioLogado().getRole().equals(Role.ADMIN)){
			return Role.selectItemsGerencia();
		}
		
		return Role.selectItems();
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

	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<SelectItem> getListGrupoUsuario() {
		return listGrupoUsuario;
	}

	public void setListGrupoUsuario(List<SelectItem> listGrupoUsuario) {
		this.listGrupoUsuario = listGrupoUsuario;
	}
}