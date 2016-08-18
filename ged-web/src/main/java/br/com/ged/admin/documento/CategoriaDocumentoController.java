package br.com.ged.admin.documento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.entidades.Categoria;
import br.com.ged.framework.GenericServiceController;

@ManagedBean(name="categoriaDocumento")
@ViewScoped
public class CategoriaDocumentoController {
	
	public Categoria categoria;
	
	@EJB
	protected GenericServiceController<Categoria, Long> serviceCategoria;
	
	private List<Categoria> listCategoria;
	
	@PostConstruct
	public void inicio(){
		
		categoria = new Categoria();
		
		listCategoria = serviceCategoria.listarTodos(Categoria.class);
	}
	
	public void pesquisar(){
		
//		listGrupoUsuario = grupoUsuarioService.pesquisar(filtroGrupoUsuarioDTO,"usuarios", "usuarios.pessoa","funcionalidades","tiposFuncionalidades");
	}

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}
}