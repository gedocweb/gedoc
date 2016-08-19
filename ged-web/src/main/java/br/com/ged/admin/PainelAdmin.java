package br.com.ged.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.Role;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractManageBean;

@ManagedBean(name="painelAdmin")
@SessionScoped
public class PainelAdmin extends AbstractManageBean{
	
	public void verificaAcesso(ComponentSystemEvent event){
		
		if (isColaborador()){
			
			try {
				redirecionaPagina(Pagina.PAINEL_DOCUMENTO);
			} catch (NegocioException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean getColaborador(){
		
		return isColaborador();
	}
	
	public boolean getAdmin(){
		
		return getUsuarioLogado() != null && getUsuarioLogado().getRole().equals(Role.ADMIN);
	}
	
	public boolean getGerente(){
		
		return getUsuarioLogado() != null && getUsuarioLogado().getRole().equals(Role.GERENTE);
	}

	private boolean isColaborador() {
		return getUsuarioLogado() != null && getUsuarioLogado().getRole().equals(Role.COLABORADOR);
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_ADMIN;
	}
}