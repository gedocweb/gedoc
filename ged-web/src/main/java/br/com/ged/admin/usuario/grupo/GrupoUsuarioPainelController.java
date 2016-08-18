package br.com.ged.admin.usuario.grupo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.service.GrupoUsuarioService;

@ManagedBean(name="painelGrupoUsuario")
@ViewScoped
public class GrupoUsuarioPainelController extends GrupoUsuarioSuperController{
	
	private List<GrupoUsuario> listGrupoUsuario;
	private GrupoUsuario grupoUsuarioSelecionado;
	
	@EJB
	private GrupoUsuarioService grupoUsuarioService;
	
	private Usuario usuarioSelecionado;
	
	@PostConstruct
	public void inicio(){
		
		filtroGrupoUsuarioDTO = new FiltroGrupoUsuarioDTO();
	}
	
	public void pesquisar(){
		
		listGrupoUsuario = grupoUsuarioService.pesquisar(filtroGrupoUsuarioDTO,"usuarios", "usuarios.pessoa","funcionalidades","tiposFuncionalidades");
		
		for (GrupoUsuario gu : listGrupoUsuario){
			
			List<Usuario> list = new ArrayList<>();
			
			for (Usuario usuario : gu.getUsuarios()){
				
				list.add(usuario);
			}
				
			gu.setUsuarios(list);	
		}
	}
	
	public void atualizaSelectManyCheckBox(){
		
		super.atualizaSelectManyCheckBox();
		
		List<SelectItem> listSelectItem = getListSelectItemUsuarios();
		
		if (listSelectItem != null && !listSelectItem.isEmpty()){
			
			if (getGrupoUsuarioSelecionado().getUsuarios() != null && !getGrupoUsuarioSelecionado().getUsuarios().isEmpty()){
				
				if (getUsuariosSelecionados() == null){
					setUsuariosSelecionados(new ArrayList<Long>());
				}
				
				for (Usuario usuario : getGrupoUsuarioSelecionado().getUsuarios()){
					
					getUsuariosSelecionados().add(usuario.getId());
				}
			}
		}
	}
	
	public void adicionaNovosUsuarios(){
		
		for (Long idUsuario : getUsuariosSelecionados()){
			
			Usuario usuario = serviceGenericUsuario.getById(Usuario.class, idUsuario);
			getGrupoUsuarioSelecionado().getUsuarios().add(usuario);
		}
	}
	
	public void atualizaUsuarios(){
		
		service.merge(getGrupoUsuarioSelecionado());
		enviaMensagem(Mensagem.MDF001);
	}
	
	public void excluir(){
		service.excluir(grupoUsuarioSelecionado);
		this.pesquisar();
	}
	
	public void excluirUsuario(){
		
		getGrupoUsuarioSelecionado().getUsuarios().remove(usuarioSelecionado);
		service.merge(getGrupoUsuarioSelecionado());
		enviaMensagem(Mensagem.MDF001);
	}
	
	public void ativarGrupoUsuario(GrupoUsuario grupoUsuario){
		
		grupoUsuario.setSituacao(Situacao.ATIVO);
		
		service.salvar(grupoUsuario);
	}
	
	public void inativarGrupoUsuario(GrupoUsuario grupoUsuario){
		
		grupoUsuario.setSituacao(Situacao.INATIVO);
		
		service.salvar(grupoUsuario);
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_GRUPO_USUARIO;
	}

	public List<GrupoUsuario> getListGrupoUsuario() {
		return listGrupoUsuario;
	}

	public GrupoUsuario getGrupoUsuarioSelecionado() {
		return grupoUsuarioSelecionado;
	}

	public void setGrupoUsuarioSelecionado(GrupoUsuario grupoUsuarioSelecionado) {
		this.grupoUsuarioSelecionado = grupoUsuarioSelecionado;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}