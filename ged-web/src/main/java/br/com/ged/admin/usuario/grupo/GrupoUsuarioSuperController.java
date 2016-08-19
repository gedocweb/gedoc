package br.com.ged.admin.usuario.grupo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoFuncionalidadeEnum;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractManageBean;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.UsuarioService;
import br.com.ged.util.container.AtributoSessao;

public abstract class GrupoUsuarioSuperController extends AbstractManageBean{
	
	protected GrupoUsuario grupoUsuario;
	
	@EJB
	protected GenericServiceController<GrupoUsuario, Long> service;
	
	@EJB
	protected GenericServiceController<Usuario, Long> serviceGenericUsuario;
	
	@EJB
	protected UsuarioService serviceUsuario;
	
	@EJB
	protected GrupoUsuarioValidadorView validacaoGrupoUsuario;
	
	@EJB
	protected GrupoUsuarioService grupoUsuarioService;
	
	protected FiltroGrupoUsuarioDTO filtroGrupoUsuarioDTO;
	
	private List<Usuario> listUsuario;
	private List<Usuario> listUsuarioSelecionados;
	private List<Usuario> listUsuarioFiltrados;
	
	private List<TipoFuncionalidadeDTO> listPermissoes;
	private FuncionalidadeEnum funcionalidadeParaExclusao;
	private String tituloDialogFunc;
	
	@PostConstruct
	public void inicio(){
		listUsuario = new ArrayList<>();
		listPermissoes = new ArrayList<>();
	}
	
	public void atualizaListaUsuariosSemGrupo(){
		
		List<Usuario> usuarios = serviceUsuario.pesquisar(new FiltroUsuarioDTO());
		
		listUsuario = montaListUsuario(usuarios);
	}
	
	public void excluirFuncionalidade(GrupoUsuario grupoUsuario) {
		
		if (funcionalidadeParaExclusao == null){
			return;
		}
		
		if (grupoUsuario.getId() != null && grupoUsuario.getFuncionalidades() != null && grupoUsuario.getFuncionalidades().size() == 1){
			
			enviaMensagem(Mensagem.GRUPOUSUARIO18);
			return;
		}
		
		grupoUsuario.getFuncionalidades().remove(funcionalidadeParaExclusao);
		
		for (TipoFuncionalidadeEnum tp : funcionalidadeParaExclusao.getPermissoes()){
			grupoUsuario.getTiposFuncionalidades().remove(tp);
		}
		
		if(grupoUsuario.getId() != null){
			atualizaGrupoUsuarioLogado(grupoUsuario);
		}
		
		listPermissoes = new ArrayList<>();
	}

	private void atualizaGrupoUsuarioLogado(GrupoUsuario grupoUsuario) {
		
		service.merge(grupoUsuario);
		
		if (getGrupoUsuarioLogado().getId().equals(grupoUsuario.getId())){
			
			setAtributoSessao(AtributoSessao.GRUPO_USUARIO_LOGADO, grupoUsuario);
		}
	}
	
	public void verificaTipoFuncionalidadeSelecianda(FuncionalidadeEnum funcSelecionada) {
		
		verificaFuncionalidadeSelecionadaPeloPainel(funcSelecionada, getGrupoUsuario());
	}

	public void verificaFuncionalidadeSelecionadaPeloPainel(FuncionalidadeEnum funcSelecionada, GrupoUsuario grupoUs) {
		
		listPermissoes = new ArrayList<>();
		
		if (funcSelecionada == null){
			return;
		}
		
		tituloDialogFunc = funcSelecionada.getLabel();
		
		for (TipoFuncionalidadeEnum tpFunc : funcSelecionada.getPermissoes()){
			
			TipoFuncionalidadeDTO tpFuncDTO = new TipoFuncionalidadeDTO();
			
			tpFuncDTO.setSituacao(Situacao.INATIVO);
			tpFuncDTO.setTipoFuncionalidadeEnum(tpFunc);
			
			for (TipoFuncionalidadeEnum labelTpSelecionado : grupoUs.getTiposFuncionalidades()){
				
				if (tpFunc.equals(labelTpSelecionado)){
					tpFuncDTO.setSituacao(Situacao.ATIVO);
					break;
				}
			}
			
			listPermissoes.add(tpFuncDTO);
		}
	}
	
	public void ativarFunc(TipoFuncionalidadeDTO func, GrupoUsuario gpUsuario){
		
		for (TipoFuncionalidadeDTO tipoFunc : listPermissoes){
			
			if (tipoFunc.getTipoFuncionalidadeEnum().equals(func.getTipoFuncionalidadeEnum())){
				tipoFunc.setSituacao(Situacao.ATIVO);
				
				gpUsuario.getTiposFuncionalidades().add(tipoFunc.getTipoFuncionalidadeEnum());
			}
		}
		
		if (gpUsuario.getId() != null){
			atualizaGrupoUsuarioLogado(gpUsuario);
		}
	}
	
	public void inativarFunc(TipoFuncionalidadeDTO func, GrupoUsuario gpUsuario){
		
		for (TipoFuncionalidadeDTO tipoFunc : listPermissoes){
			
			if (tipoFunc.getTipoFuncionalidadeEnum().equals(func.getTipoFuncionalidadeEnum())){
				tipoFunc.setSituacao(Situacao.INATIVO);
				
				gpUsuario.getTiposFuncionalidades().remove(tipoFunc.getTipoFuncionalidadeEnum());
			}
		}
		
		if (gpUsuario.getId() != null){
			atualizaGrupoUsuarioLogado(gpUsuario);
		}
	}
	
	private List<Usuario> montaListUsuario(List<Usuario> usuarios) {
		
		List<Usuario> list = new ArrayList<>();
		
		if (usuarios == null || usuarios.isEmpty()){
			return list;
		}
		
		for (Usuario usuario : usuarios){
			
			try{
				
				FiltroGrupoUsuarioDTO filtro = new FiltroGrupoUsuarioDTO();
				filtro.setIdUsuario(usuario.getId());
				grupoUsuarioService.pesquisar(filtro).iterator().next(); 
				
			}catch(NoSuchElementException e){
				
				list.add(usuario);
			}
		}
		
		return list;
	}
	
	protected void cadastrar() throws NegocioException{
		service.salvar(grupoUsuario);
	}
	
	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public FiltroGrupoUsuarioDTO getFiltroGrupoUsuarioDTO() {
		return filtroGrupoUsuarioDTO;
	}

	public void setFiltroGrupoUsuarioDTO(FiltroGrupoUsuarioDTO filtroGrupoUsuarioDTO) {
		this.filtroGrupoUsuarioDTO = filtroGrupoUsuarioDTO;
	}
	
	public List<TipoFuncionalidadeDTO> getListPermissoes() {
		return listPermissoes;
	}

	public void setListPermissoes(List<TipoFuncionalidadeDTO> listPermissoes) {
		this.listPermissoes = listPermissoes;
	}

	public FuncionalidadeEnum getFuncionalidadeParaExclusao() {
		return funcionalidadeParaExclusao;
	}

	public void setFuncionalidadeParaExclusao(FuncionalidadeEnum funcionalidadeParaExclusao) {
		this.funcionalidadeParaExclusao = funcionalidadeParaExclusao;
	}
	
	public String getTituloDialogFunc() {
		return tituloDialogFunc;
	}

	public void setTituloDialogFunc(String tituloDialogFunc) {
		this.tituloDialogFunc = tituloDialogFunc;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public List<Usuario> getListUsuarioSelecionados() {
		return listUsuarioSelecionados;
	}

	public void setListUsuarioSelecionados(List<Usuario> listUsuarioSelecionados) {
		this.listUsuarioSelecionados = listUsuarioSelecionados;
	}

	public List<Usuario> getListUsuarioFiltrados() {
		return listUsuarioFiltrados;
	}

	public void setListUsuarioFiltrados(List<Usuario> listUsuarioFiltrados) {
		this.listUsuarioFiltrados = listUsuarioFiltrados;
	}
}