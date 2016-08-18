package br.com.ged.admin.documento;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import br.com.ged.framework.AbstractManageBean;

public abstract class DocumentoSuperController extends AbstractManageBean{
	
	@EJB
	protected DocumentoValidadorView documentoValidatorView;
	
	@PostConstruct
	public void inicio(){
	}
	
}