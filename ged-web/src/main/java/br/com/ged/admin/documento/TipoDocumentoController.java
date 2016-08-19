package br.com.ged.admin.documento;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.ged.entidades.TipoDocumento;
import br.com.ged.framework.GenericServiceController;

@ManagedBean(name=TipoDocumentoController.NOME_CONTROLLER, eager=true)
@Singleton
@ApplicationScoped
@javax.faces.bean.ApplicationScoped
public class TipoDocumentoController {
	
	public static final String NOME_CONTROLLER = "tipoDocumentoController";
	
	@EJB
	protected GenericServiceController<TipoDocumento, Long> serviceTipoDocumento;
	
	private List<TipoDocumento> listTipoDocumento = null;
	
	@PostConstruct
	public void inicio(){
		listTipoDocumento = new ArrayList<>();
	}
	
	public TipoDocumento getById(Long idTipoDocumento){
		
		for (TipoDocumento tp : listTipoDocumento){
			
			if (tp.getId().equals(idTipoDocumento)){
				return tp;
			}
		}
		
		TipoDocumento tipoDoc = serviceTipoDocumento.getById(TipoDocumento.class, idTipoDocumento);
		listTipoDocumento.add(tipoDoc);
		
		return tipoDoc;
	}
}