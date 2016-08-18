package br.com.ged.admin.documento;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.ged.entidades.TipoDocumento;
import br.com.ged.framework.GenericServiceController;

@ManagedBean(name="tipoDocumentoController", eager=true)
@ApplicationScoped
public class TipoDocumentoController {
	
	@EJB
	protected GenericServiceController<TipoDocumento, Long> serviceTipoDocumento;
	
	public TipoDocumento getById(Long idTipoDocumento){
		return serviceTipoDocumento.getById(TipoDocumento.class, idTipoDocumento);
	}
}