package br.com.ged.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.framework.AbstractManageBean;

@ManagedBean(name="painelAdmin")
@ViewScoped
public class PainelAdmin extends AbstractManageBean{

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_ADMIN;
	}
}