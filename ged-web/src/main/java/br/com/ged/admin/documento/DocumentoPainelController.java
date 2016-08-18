package br.com.ged.admin.documento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.dto.FiltroDocumentoDTO;
import br.com.ged.entidades.Arquivo;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.Documento;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.DocumentoService;

@ManagedBean(name="painelDocumento")
@ViewScoped
public class DocumentoPainelController extends DocumentoSuperController{
	
	private List<Documento> listDocumento;
	private Documento documentoSelecionado;
	
	@EJB
	private DocumentoService documentoService;
	
	@EJB
	protected GenericServiceController<Categoria, Long> serviceCategoria;
	
	@EJB
	protected GenericServiceController<TipoDocumento, Long> serviceTipoDocumento;
	
	@EJB
	protected GenericServiceController<Documento, Long> serviceDocumento;
	
	private TreeNode categorias;
    private Categoria categoriaSelecionada;
    private Boolean diretorioRaizSelecionado;
    
    private Categoria categoria;
    
    private boolean renderizaBotoesAlterarExcluirCategoria;
    private boolean renderizaCategoriaSelecionada;
    
    @EJB
    private CategoriaDocumentoValidadorView categoriaValidador;
    
    //Tipo documento
    private List<TipoDocumento> listTipoDocumento;
    private TipoDocumento tipoDocumentoSelecionado;
    private boolean renderizaBotoesAlterarExcluirTipoDocumento;
    
    private TipoDocumento tipoDocumento;
    
    @EJB
    private TipoDocumentoValidadorView tipoDocumentoValidador;
    
    @ManagedProperty("#{tipoDocumentoController}")
    private TipoDocumentoController tipoDocumentoController;
    
    private Documento documento;
    
    private boolean renderizaCadastro = false;
    private String tituloFildSetDocumento;
    private boolean arquivoAnexado;
    private boolean renderizaAlterar = false;
    
    private FiltroDocumentoDTO filtroDocumentoDTO;
	
	@PostConstruct
	public void inicio(){
		diretorioRaizSelecionado = Boolean.FALSE;

		categoria = new Categoria();
		limpaCategoriaSelecionada();
		
		tipoDocumento = new TipoDocumento();
		tipoDocumentoSelecionado = new TipoDocumento();
		super.externalContext().getSessionMap().put("tipoDocumentoController", tipoDocumentoController);
		
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		listDocumento = new ArrayList<Documento>();
		
		filtroDocumentoDTO = new FiltroDocumentoDTO();
		
		iniciaCategoria();
		iniciaTipoDocumento();
		renderizaTituloFieldSet();
	}

	private Documento inicializaDocumento() {
		
		Documento novoDoc = new Documento();
		novoDoc.setCategoria(new Categoria());
		novoDoc.setTipoDocumento(new TipoDocumento());
		novoDoc.setArquivo(new Arquivo());
		
		return novoDoc;
	}
	
	private void renderizaTituloFieldSet() {
		
		if (!renderizaCadastro){
			tituloFildSetDocumento = "Filtro para pesquisa";
		}else{
			tituloFildSetDocumento = "Informações para cadastro";
		}
	}

	public void preparaCadastro(){
		
		arquivoAnexado = false;
		renderizaCadastro = true;
		renderizaAlterar = Boolean.FALSE;
		renderizaTituloFieldSet();
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
	}
	
	public void preparaPesquisa(){
		
		arquivoAnexado = false;
		renderizaCadastro = false;
		renderizaAlterar = Boolean.FALSE;
		renderizaTituloFieldSet();
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		listDocumento = new ArrayList<>();
	}
	
	public void preparaAlterar(Documento doc){
		
		documentoSelecionado = doc;
		arquivoAnexado = doc.getArquivo().getId() != null;
		renderizaAlterar = Boolean.TRUE;
		renderizaCadastro = Boolean.FALSE;
	}
	
	private void iniciaCategoria() {
		
		List<Categoria> listCategoria = serviceCategoria.listarTodos(Categoria.class);
		
		if (listCategoria == null || listCategoria.isEmpty()){
			
			renderizaBotoesAlterarExcluirCategoria = Boolean.FALSE;
			return;
		}
		
		categorias = new DefaultTreeNode(new Categoria(), null);
		
		for (Categoria cat : listCategoria){
			
			if (verificaRamoJaAdicionado(categorias, cat)){
				continue;
			}
			
			if (cat.getCategoriaPai() == null){
				
				TreeNode categoriaPai = new DefaultTreeNode(cat, categorias);
				adicionaPastasFilhas(cat, categoriaPai);
			}
		}
		
		renderizaBotoesAlterarExcluirCategoria = Boolean.TRUE;
	}

	private boolean verificaRamoJaAdicionado(TreeNode tree,Categoria cat) {
		
		for (TreeNode tr : tree.getChildren()){
			
			Categoria categoria = (Categoria)tr.getData();
			
			if (categoria != null && categoria.getId().equals(cat.getId())){
				return true;
			}
		}
		
		return false;
	}

	private void adicionaPastasFilhas(Categoria cat, TreeNode diretorioPai) {
		
		if (cat.getCategoriaFilha() == null || cat.getCategoriaFilha().isEmpty()){
			return;
		}
		
		for (Categoria filho : cat.getCategoriaFilha()){
			TreeNode diretorioPaiInterno = new DefaultTreeNode(filho, diretorioPai);
			adicionaPastasFilhas(filho, diretorioPaiInterno);
		}
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		
		categoria = new Categoria();
		categoriaSelecionada = (Categoria) event.getTreeNode().getData();
		renderizaCategoriaSelecionada = Boolean.TRUE;
		
		documentoSelecionado.setCategoria(categoriaSelecionada);
    }
	
	public void alterarCategoria(){
		
		try {
			
			categoriaValidador.validaAlteracao(categoriaSelecionada);
			
			serviceCategoria.merge(categoriaSelecionada);
			iniciaCategoria();
			limpaCategoriaSelecionada();
			categoria = new Categoria();
			
			
		} catch (NegocioException e) {
			e.printStackTrace();
			iniciaCategoria();
			categoriaSelecionada = new Categoria();
		}
	}

	private void limpaCategoriaSelecionada() {
		categoriaSelecionada = new Categoria();
		renderizaCategoriaSelecionada = false;
	}
	
	public void verificaExclusaoCategoria(){
		
		if (categoriaSelecionada != null && categoriaSelecionada.getId() != null){
			super.abrirModal("confirmaExclusaoCategoria");
		}else{
			enviaMensagem(Mensagem.CATDOC2);
		}
	}
	
	public void verificaAlteracaoCategoria(){
		
		if (categoriaSelecionada != null && categoriaSelecionada.getId() != null){
			super.abrirModal("dgAlterarCategoria");
		}else{
			enviaMensagem(Mensagem.CATDOC2);
		}
	}
	
	public void excluirCategoria(){
			
		serviceCategoria.excluir(categoriaSelecionada);
		iniciaCategoria();
		categoria = new Categoria();
		limpaCategoriaSelecionada();
	}
	
	public void novaCategoria(){
		
		try {
			
			categoriaValidador.validaCadastro(categoria);
			
			if (diretorioRaizSelecionado){
				
				categoria.setCategoriaPai(null);
			}else{
				categoria.setCategoriaPai(categoriaSelecionada);
			}
			
			serviceCategoria.salvar(categoria);
			iniciaCategoria();
			categoriaSelecionada = new Categoria();
			categoria = new Categoria();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	//Tipo documento
	
	private void iniciaTipoDocumento() {
		
		listTipoDocumento = serviceTipoDocumento.listarTodos(TipoDocumento.class);
		
		if (listTipoDocumento == null || listTipoDocumento.isEmpty()){
			
			renderizaBotoesAlterarExcluirTipoDocumento = Boolean.FALSE;
			return;
		}else{
			renderizaBotoesAlterarExcluirTipoDocumento = Boolean.TRUE;
		}
	}
	
	public void novoTipoDocumento(){
		
		try {
			
			tipoDocumentoValidador.validaCadastro(tipoDocumento);
			
			serviceTipoDocumento.salvar(tipoDocumento);
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = new TipoDocumento();
			tipoDocumento = new TipoDocumento();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
		
	}
	
	public void onSelect(SelectEvent event) {
		this.tipoDocumentoSelecionado = (TipoDocumento) event.getObject();
		
		this.documentoSelecionado.setTipoDocumento(tipoDocumentoSelecionado);
    }
	
	public void verificaExclusaoTipoDocumento(){
		
		if (tipoDocumentoSelecionado != null && tipoDocumentoSelecionado.getId() != null){
			super.abrirModal("confirmaExclusaoTipoDoc");
		}else{
			enviaMensagem(Mensagem.TPDOC2);
		}
	}
	
	public void verificaAlteracaoTipoDocumento(){
		
		if (tipoDocumentoSelecionado != null && tipoDocumentoSelecionado.getId() != null){
			super.abrirModal("dgAlterarTipoDocumento");
		}else{
			enviaMensagem(Mensagem.TPDOC2);
		}
	}
	
	public void excluirTipoDocumento(){
		
		serviceTipoDocumento.excluir(tipoDocumentoSelecionado);
		iniciaTipoDocumento();
		tipoDocumento = new TipoDocumento();
	}
	
	public void alterarTipoDocumento(){
		
		try {
			
			tipoDocumentoValidador.validaAlteracao(tipoDocumentoSelecionado);
			
			serviceTipoDocumento.merge(tipoDocumentoSelecionado);
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = null;
			tipoDocumento = new TipoDocumento();
			
		} catch (NegocioException e) {
			e.printStackTrace();
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = new TipoDocumento();
		}
	}
	
	public void pesquisar(){
		
		try {
			
			documento.setCategoria(getCategoriaSelecionada());
			documento.setTipoDocumento(getTipoDocumentoSelecionado());
			
			//Verificar Campos Obrigatorios
			documentoValidatorView.validaPesquisa(documento);
			
			filtroDocumentoDTO.setDescricao(documento.getDescricao());
			filtroDocumentoDTO.setIdCategoria(getCategoriaSelecionada().getId());
			filtroDocumentoDTO.setIdTipoDocumento(getTipoDocumentoSelecionado().getId());
			filtroDocumentoDTO.setObservacao(documento.getObservacao());
			
			listDocumento = documentoService.pesquisar(filtroDocumentoDTO,"arquivo");
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void excluirDocumento(){
		
		serviceDocumento.excluir(getDocumentoSelecionado());
		
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		this.pesquisar();
	}
	
	public void cadastrarDocumento(){
		
		if (!arquivoAnexado){
			getDocumento().setArquivo(null);
		}
		
		getDocumento().setCategoria(getCategoriaSelecionada());
		getDocumento().setUsuario(getUsuarioLogado());
		getDocumento().setTipoDocumento(getTipoDocumentoSelecionado());
		
		try {
			
			documentoValidatorView.validaCadastro(getDocumento());
			
			getDocumento().setDataUltimaAlteracao(new Date());
			getDocumento().setDataInclusao(new Date());
			
			getDocumento().setSituacao(Situacao.ATIVO);
			
			serviceDocumento.salvar(getDocumento());
			
			setDocumento(inicializaDocumento());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void alterarDocumento(){
		
		if (!arquivoAnexado){
			getDocumentoSelecionado().setArquivo(null);
		}
		
		getDocumentoSelecionado().setCategoria(getCategoriaSelecionada());
		getDocumentoSelecionado().setUsuario(getUsuarioLogado());
		getDocumentoSelecionado().setTipoDocumento(getTipoDocumentoSelecionado());
		
		try {
			
			documentoValidatorView.validaCadastro(getDocumentoSelecionado());
			
			getDocumentoSelecionado().setDataUltimaAlteracao(new Date());
			getDocumentoSelecionado().setSituacao(Situacao.ATIVO);
			
			serviceDocumento.merge(getDocumentoSelecionado());
			
			setDocumentoSelecionado(inicializaDocumento());
			arquivoAnexado = Boolean.FALSE;
			
			this.preparaPesquisa();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent event) {
		
	    Arquivo arquivo = arquivoUpload(event);
		
		getDocumento().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
	}
	
	public void uploadAlterar(FileUploadEvent event) {
		
	    Arquivo arquivo = arquivoUpload(event);
		
		getDocumentoSelecionado().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
	}

	private Arquivo arquivoUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    String contentType = uploadedFile.getContentType();
	    byte[] contents = uploadedFile.getContents(); 
	    
		Arquivo arquivo = new Arquivo();
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(contentType);
		return arquivo;
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_DOCUMENTO;
	}

	public TreeNode getCategorias() {
		return categorias;
	}

	public void setCategorias(TreeNode categorias) {
		this.categorias = categorias;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public Boolean getDiretorioRaizSelecionado() {
		return diretorioRaizSelecionado;
	}

	public void setDiretorioRaizSelecionado(Boolean diretorioRaizSelecionado) {
		this.diretorioRaizSelecionado = diretorioRaizSelecionado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public boolean isRenderizaBotoesAlterarExcluirCategoria() {
		return renderizaBotoesAlterarExcluirCategoria;
	}

	public void setRenderizaBotoesAlterarExcluirCategoria(boolean renderizaBotoesAlterarExcluirCategoria) {
		this.renderizaBotoesAlterarExcluirCategoria = renderizaBotoesAlterarExcluirCategoria;
	}

	public boolean isRenderizaCategoriaSelecionada() {
		return renderizaCategoriaSelecionada;
	}

	public void setRenderizaCategoriaSelecionada(boolean renderizaCategoriaSelecionada) {
		this.renderizaCategoriaSelecionada = renderizaCategoriaSelecionada;
	}

	public List<TipoDocumento> getListTipoDocumento() {
		return listTipoDocumento;
	}

	public void setListTipoDocumento(List<TipoDocumento> listTipoDocumento) {
		this.listTipoDocumento = listTipoDocumento;
	}

	public TipoDocumento getTipoDocumentoSelecionado() {
		return tipoDocumentoSelecionado;
	}

	public void setTipoDocumentoSelecionado(TipoDocumento tipoDocumentoSelecionado) {
		this.tipoDocumentoSelecionado = tipoDocumentoSelecionado;
	}

	public boolean isRenderizaBotoesAlterarExcluirTipoDocumento() {
		return renderizaBotoesAlterarExcluirTipoDocumento;
	}

	public void setRenderizaBotoesAlterarExcluirTipoDocumento(boolean renderizaBotoesAlterarExcluirTipoDocumento) {
		this.renderizaBotoesAlterarExcluirTipoDocumento = renderizaBotoesAlterarExcluirTipoDocumento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public TipoDocumentoController getTipoDocumentoController() {
		return tipoDocumentoController;
	}

	public void setTipoDocumentoController(TipoDocumentoController tipoDocumentoController) {
		this.tipoDocumentoController = tipoDocumentoController;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getListDocumento() {
		return listDocumento;
	}

	public void setListDocumento(List<Documento> listDocumento) {
		this.listDocumento = listDocumento;
	}

	public Documento getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public boolean isRenderizaCadastro() {
		return renderizaCadastro;
	}

	public void setRenderizaCadastro(boolean renderizaCadastro) {
		this.renderizaCadastro = renderizaCadastro;
	}

	public String getTituloFildSetDocumento() {
		return tituloFildSetDocumento;
	}

	public void setTituloFildSetDocumento(String tituloFildSetDocumento) {
		this.tituloFildSetDocumento = tituloFildSetDocumento;
	}

	public boolean isArquivoAnexado() {
		return arquivoAnexado;
	}

	public void setArquivoAnexado(boolean arquivoAnexado) {
		this.arquivoAnexado = arquivoAnexado;
	}

	public boolean isRenderizaAlterar() {
		return renderizaAlterar;
	}

	public void setRenderizaAlterar(boolean renderizaAlterar) {
		this.renderizaAlterar = renderizaAlterar;
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}
}