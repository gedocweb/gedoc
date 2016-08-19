package br.com.ged.admin.usuario;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Pessoa;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.util.criptografia.CriptografiaUtil;

@ManagedBean(name="alterarUsuario")
@ViewScoped
public class UsuarioAlterarController extends UsuarioSuperController{
	
	@EJB
	protected GrupoUsuarioService grupoUsuarioService;
	
	@PostConstruct
	public void inicio() throws NegocioException{
		
		filtroUsuarioDTO = new FiltroUsuarioDTO();
		
		Usuario usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
		
		setUsuario(usuario);
	}
	
	public void preAlteracao(){
		
		super.abrirModal("alterarUsuario");
	}
	
	public void alterar() {
		
		try {
			
			validacaoUsuario.validaAlteracao(getUsuario());
			
			Usuario usuarioBanco = service.getById(Usuario.class, getUsuario().getId());
			
			getUsuario().setSenha(usuarioBanco.getSenha());
			
			service.merge(getUsuario());
			this.inicio();
			
			super.fecharModal("alterarUsuario");
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void alterarSenha() {
		
		try {
			
			validacaoUsuario.validaSenha(getUsuario(), confirmarSenha);
			
			getUsuario().setSenha(CriptografiaUtil.criptografar(getUsuario().getSenha()));
			
			service.merge(getUsuario());
			this.inicio();
			
			super.fecharModal("alterarSenha");
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.ALTERAR_USUARIO;
	}
}