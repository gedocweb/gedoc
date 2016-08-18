package br.com.ged.admin.usuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.excecao.NegocioException;

@ManagedBean(name="cadastroUsuario")
@ViewScoped
public class UsuarioCadastroController extends UsuarioSuperController{
	
	@PostConstruct
	public void inicio() throws NegocioException{
		
	}
	
	public void cadastrar()  {
		
		try {
			
			super.cadastrar();
			this.inicio();
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	};
	

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.CADASTRAR_USUARIO;
	}

}