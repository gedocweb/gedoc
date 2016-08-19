package br.com.ged.admin.documento;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="obsDocumento")  
@SessionScoped
public class ObservacaoDocumentoViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private String observacaoDoc;

	public String getObservacaoDoc() {
		return observacaoDoc;
	}

	public void setObservacaoDoc(String observacaoDoc) {
		this.observacaoDoc = observacaoDoc;
	}
}  