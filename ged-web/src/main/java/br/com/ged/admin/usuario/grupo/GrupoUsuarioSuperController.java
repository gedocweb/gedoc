package br.com.ged.admin.usuario.grupo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

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
import br.com.ged.service.UsuarioService;

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
	
	protected FiltroGrupoUsuarioDTO filtroGrupoUsuarioDTO;
	
	private String paramPesquisaUsuario;
	
	private List<SelectItem> listSelectItemUsuarios;
	private boolean renderizaManyCheckBox;
	private List<Long> usuariosSelecionados;
	
	private List<TipoFuncionalidadeDTO> listPermissoes;
	private FuncionalidadeEnum funcionalidadeParaExclusao;
	private String tituloDialogFunc;
	
	@PostConstruct
	public void inicio(){
		listSelectItemUsuarios = new ArrayList<>();
		paramPesquisaUsuario = null;
		listPermissoes = new ArrayList<>();
		listSelectItemUsuarios = new ArrayList<>();
		usuariosSelecionados = new ArrayList<>();
	}
	
	public void atualizaSelectManyCheckBox(){
		
		FiltroUsuarioDTO filtroUsuarioDTO = new FiltroUsuarioDTO();
		
		filtroUsuarioDTO.setNomeUsuario(paramPesquisaUsuario);
		
		List<Usuario> usuarios = serviceUsuario.pesquisar(filtroUsuarioDTO);
		
		listSelectItemUsuarios = montaSelectItemsUsuario(usuarios);
		
		renderizaManyCheckBox = !listSelectItemUsuarios.isEmpty();
		
		if (!renderizaManyCheckBox){
			enviaMensagem(Mensagem.GRUPOUSUARIO15);
		}
	}
	
	public void excluirFuncionalidade() {
		
		if (funcionalidadeParaExclusao == null){
			return;
		}
		
		getGrupoUsuario().getFuncionalidades().remove(funcionalidadeParaExclusao);
		
		for (TipoFuncionalidadeEnum tp : funcionalidadeParaExclusao.getFuncionalidades()){
			getGrupoUsuario().getTiposFuncionalidades().remove(tp);
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
		
		for (TipoFuncionalidadeEnum tpFunc : funcSelecionada.getFuncionalidades()){
			
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
	
	public void ativarFunc(TipoFuncionalidadeDTO func){
		
		for (TipoFuncionalidadeDTO tipoFunc : listPermissoes){
			
			if (tipoFunc.getTipoFuncionalidadeEnum().equals(func.getTipoFuncionalidadeEnum())){
				tipoFunc.setSituacao(Situacao.ATIVO);
				
				getGrupoUsuario().getTiposFuncionalidades().add(tipoFunc.getTipoFuncionalidadeEnum());
			}
		}
	}
	
	public void inativarFunc(TipoFuncionalidadeDTO func){
		
		for (TipoFuncionalidadeDTO tipoFunc : listPermissoes){
			
			if (tipoFunc.getTipoFuncionalidadeEnum().equals(func.getTipoFuncionalidadeEnum())){
				tipoFunc.setSituacao(Situacao.INATIVO);
				
				getGrupoUsuario().getTiposFuncionalidades().remove(tipoFunc.getTipoFuncionalidadeEnum());
			}
		}
	}
	
	private List<SelectItem> montaSelectItemsUsuario(List<Usuario> usuarios) {
		
		List<SelectItem> list = new ArrayList<>();
		
		if (usuarios == null || usuarios.isEmpty()){
			return list;
		}
		
		for (Usuario usuario : usuarios){
			
			SelectItem si = new SelectItem();
			
			si.setLabel(usuario.getPessoa().getCpf() +" - "+usuario.getPessoa().getNome());
			si.setValue(usuario.getId());
			
			list.add(si);
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
	
	public String getParamPesquisaUsuario() {
		return paramPesquisaUsuario;
	}

	public void setParamPesquisaUsuario(String paramPesquisaUsuario) {
		this.paramPesquisaUsuario = paramPesquisaUsuario;
	}

	public List<SelectItem> getListSelectItemUsuarios() {
		return listSelectItemUsuarios;
	}

	public boolean isRenderizaManyCheckBox() {
		return renderizaManyCheckBox;
	}

	public void setRenderizaManyCheckBox(boolean renderizaManyCheckBox) {
		this.renderizaManyCheckBox = renderizaManyCheckBox;
	}
	
	public List<Long> getUsuariosSelecionados() {
		return usuariosSelecionados;
	}

	public void setUsuariosSelecionados(List<Long> usuariosSelecionados) {
		this.usuariosSelecionados = usuariosSelecionados;
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
}