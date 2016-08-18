package br.com.ged.admin.usuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.util.container.AtributoSessao;

@ManagedBean(name="alterarUsuario")
@ViewScoped
public class UsuarioAlterarController extends UsuarioSuperController{
	
	@PostConstruct
	public void inicio() throws NegocioException{
		
		filtroUsuarioDTO = new FiltroUsuarioDTO();
		
		Object dadosUsuario = getAtributoSessao(AtributoSessao.OBJ_ALTERAR_USUARIO);
		
		if (dadosUsuario != null && dadosUsuario instanceof Usuario){
			
			setUsuario((Usuario) dadosUsuario);
			
		}else{
			throw new NegocioException(Mensagem.MEI010);
		}
	}
	
	public void alterar() {
		
		try {
			
			super.cadastrar();
			this.inicio();
			
			redirecionaPagina(Pagina.PAINEL_USUARIO);
			limparAtributoDaSessao(AtributoSessao.OBJ_ALTERAR_USUARIO);
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.ALTERAR_USUARIO;
	}
}