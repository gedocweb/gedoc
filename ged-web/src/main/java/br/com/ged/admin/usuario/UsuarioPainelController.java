package br.com.ged.admin.usuario;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.service.UsuarioService;
import br.com.ged.util.container.AtributoSessao;

@ManagedBean(name="painelUsuario")
@ViewScoped
public class UsuarioPainelController extends UsuarioSuperController{
	
	private List<Usuario> listUsuario;
	private Usuario usuarioSelecionado;
	
	@EJB
	private UsuarioService usuarioService;
	
	@PostConstruct
	public void inicio(){
		
		filtroUsuarioDTO = new FiltroUsuarioDTO();
	}
	
	public void pesquisar(){
		
		listUsuario = usuarioService.pesquisar(filtroUsuarioDTO);
	}
	
	public void redirecionaParaTelaAlterar(Usuario usuario) throws IOException, NegocioException{
		
		setAtributoSessao(AtributoSessao.OBJ_ALTERAR_USUARIO, usuario);
		
		redirecionaPagina(Pagina.ALTERAR_USUARIO);
	}
	
	public void excluir(){
		service.excluir(usuarioSelecionado);
		this.pesquisar();
	}
	
	public void ativarUsuario(Usuario usuario){
		
		usuario.setSituacao(Situacao.ATIVO);
		
		service.salvar(usuario);
	}
	
	public void inativarUsuario(Usuario usuario){
		
		usuario.setSituacao(Situacao.INATIVO);
		
		service.salvar(usuario);
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_USUARIO;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}