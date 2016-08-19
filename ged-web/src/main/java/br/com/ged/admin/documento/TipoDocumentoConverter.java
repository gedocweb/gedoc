package br.com.ged.admin.documento;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.ged.entidades.TipoDocumento;
 
@ApplicationScoped
@FacesConverter("tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {
	
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if((value != null && value.trim().length() > 0)) {
            try {
            	TipoDocumentoController controller = (TipoDocumentoController) fc.getExternalContext().getApplicationMap().get(TipoDocumentoController.NOME_CONTROLLER);
            	TipoDocumento tpDoc = controller.getById(Long.valueOf(value));
                return tpDoc;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Tipo documento inválido."));
            }
        }
        else {
            return null;
        }
    }

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((TipoDocumento) object).getId());
        }
        else {
            return null;
        }
    }   
}