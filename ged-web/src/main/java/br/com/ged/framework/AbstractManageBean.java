package br.com.ged.framework;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ged.domain.AutorizacaoEnum;
import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Role;
import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoFuncionalidadeEnum;
import br.com.ged.domain.TipoMensagem;
import br.com.ged.domain.relatorio.RelatorioEnum;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.UsuarioService;
import br.com.ged.util.InitMessageProperties;
import br.com.ged.util.container.AtributoSessao;
import br.com.ged.util.relatorio.AbstractRelatorioParametro;
import br.com.ged.util.relatorio.RelatorioUtil;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Classe Pai de todos os ManagesBean do projeto</p>
 * 
 * <p>Possui métodos auxiliares para facilitar o manuseio do contexto do JSF nos manage Beans</p>
 * 
 * <p>Possui métodos pré-codificados para abstrar a implementação no ManageBean filho Ex.: gerarRelatorio e os métodos de autorização.</p>
 *
 */
public abstract class AbstractManageBean extends AutorizacaoManageBean {
	
	protected static final String FORM = "form";
	
	private AutorizacaoEnum autorizacao;
	protected boolean paraThread;
	
	@EJB
	private GenericServiceController<GrupoUsuario, Long> service;
	
	@EJB
	protected UsuarioService usuarioService;
	
	@EJB
	private GrupoUsuarioService grupoUsuarioService;
	
	private Usuario usuarioLogado;
	private GrupoUsuario grupoUsuarioLogado;
	private Authentication authentication;
	
	private boolean administrador;
	
	@Override
	public boolean autorizaFuncionalidade(TipoFuncionalidadeEnum tipoFuncionalidade){
		
		if (grupoUsuarioLogado == null){
			
			if (usuarioLogado != null && !AutorizacaoEnum.rolesDisponiveisParaPagina(getPaginaManageBean()).contains(usuarioLogado.getRole().name())){
				
				try {
					redirecionaPagina(Pagina.PAINEL_ADMIN);
					enviaMensagem(Mensagem.AUTH01);
				} catch (NegocioException e) {
					e.printStackTrace();
				}
			}
			
			return false;
		}
		
		return grupoUsuarioLogado.getTiposFuncionalidades().contains(tipoFuncionalidade);
	}
	
	@PostConstruct
	@Override
	public void validaPermissionamento() throws IOException, NegocioException {
		
		context().getApplication().setDefaultLocale(new Locale("pt", "BR"));
		
		try{
			authentication = SecurityContextHolder.getContext().getAuthentication();
	    	autorizacao = AutorizacaoEnum.usuarioComAcesso(getPaginaManageBean(), permissoesUsuarioLogado());
	    	
	    	if (autorizacao == null || !authentication.isAuthenticated()) {

	    		logout();
	    		
	        }else{
	        	
	        	usuarioLogado = usuario();
	        	
	        	if (usuarioLogado == null){
	        		return;
	        	}
	        	
	        	if (getAtributoSessao(AtributoSessao.GRUPO_USUARIO_LOGADO) == null){
	        		
	        		try{
	        			
	        			FiltroGrupoUsuarioDTO filtroGrupo = new FiltroGrupoUsuarioDTO();
			        	filtroGrupo.setIdUsuario(usuarioLogado.getId());
			        	grupoUsuarioLogado = grupoUsuarioService.pesquisar(filtroGrupo,"tiposFuncionalidades").iterator().next();
			        	
			        	setAtributoSessao(AtributoSessao.GRUPO_USUARIO_LOGADO, grupoUsuarioLogado);
	        			
	        		}catch(NoSuchElementException ns){
	        			
	        			ns.printStackTrace();
	        			
        				if (AutorizacaoEnum.ADMINISTRADOR.equals(autorizacao)){
        					
        					GrupoUsuario grupoUsuarioAdmin = new GrupoUsuario();
        					grupoUsuarioAdmin.setFuncionalidades(Arrays.asList(FuncionalidadeEnum.MANTER_GRUPO_USUARIO, FuncionalidadeEnum.MANTER_USUARIO));
        					
        					grupoUsuarioAdmin.setTiposFuncionalidades(TipoFuncionalidadeEnum.permissoesPorFuncionalidades(FuncionalidadeEnum.MANTER_GRUPO_USUARIO, FuncionalidadeEnum.MANTER_USUARIO));
        					
        					grupoUsuarioAdmin.setSituacao(Situacao.ATIVO);
        					grupoUsuarioAdmin.setGrupo("Grupo Suporte Admin - "+usuarioLogado.getPessoa().getNome());
        					grupoUsuarioAdmin.setUsuarios(Arrays.asList(usuarioLogado));
        					
        					service.salvarSemMensagem(grupoUsuarioAdmin);
        					
        					setAtributoSessao(AtributoSessao.GRUPO_USUARIO_LOGADO, grupoUsuarioAdmin);
        				}
	        		}
	        	
	        	}else{
	        		grupoUsuarioLogado = (GrupoUsuario) getAtributoSessao(AtributoSessao.GRUPO_USUARIO_LOGADO);
	        	}
	        }
	    	
		}catch (Exception ex){
			
			ex.printStackTrace();
			logout();
		}
	}
	
	public Usuario usuario(){
		
		Usuario usuario = null;
		String username = null;
		
		if (loginUsuarioPorParametroRequest() != null && loginUsuarioPorParametroRequest().length > 0){
			username = loginUsuarioPorParametroRequest()[0];
		}
		
		if (username == null){
			
			username = getAtributoSessao(AtributoSessao.USUARIO).toString();
		}
		
		if (username != null && getAtributoSessao(AtributoSessao.USUARIO_LOGADO) == null){
			usuario = usuarioService.usuarioPorUsername(username.toString());
			setAtributoSessao(AtributoSessao.USUARIO_LOGADO, usuario);
		}else{
			usuario = (Usuario) getAtributoSessao(AtributoSessao.USUARIO_LOGADO);
		}
		
		administrador = usuario.getRole().equals(Role.ADMIN);
		
		return usuario;
	}
	
	public boolean permissaoAdmin(Usuario usuario){
		
		if (usuario == null){
			return false;
		}
		
		if (isAdministrador()){
			 return true;
		}else if (usuario.getRole().equals(Role.ADMIN) && !isAdministrador()){
			return false;
		}
		
		return true;
	}

	private String[] loginUsuarioPorParametroRequest() {
		return getHttpRequest().getParameterValues("username");
	}
	
	/**
	 * Método utilizado para saber qual página do ManageBean filho está acessando para realizar o permissionamento adequado.
	 * 
	 * @return {@link Pagina}
	 */
	protected abstract Pagina getPaginaManageBean();
	
	/**
	 * Redireciona a tela do usuário para a página passada no parametro.
	 * 
	 * @param {@link Pagina} 
	 * @throws NegocioException
	 */
	public void redirecionaPagina(Pagina pagina) throws NegocioException{
		
		String caminhoPagina = getHttpRequest().getContextPath().concat(pagina.getValor());
		
		adicionaMensagensNaSessao();
		
		try {
			externalContext().redirect(caminhoPagina);
		} catch (IOException e) {
			throw new NegocioException(Mensagem.MEI009, e);
		}
	}
	
	private void adicionaMensagensNaSessao() {
		
		if (!context().getMessageList().isEmpty()){
			setAtributoSessao(AtributoSessao.MENSAGEM, context().getMessageList());
		}
	}
	
	/**
	 * Interrompe todas as threads em execução, redireciona para tela {@link Pagina.LOGIN} e invalida a sessão do usuário.
	 * 
	 * @throws NegocioException
	 */
	public void logout() throws NegocioException {
		
		paraThread = Boolean.TRUE;
		usuarioLogado = null;
		grupoUsuarioLogado = null;
		removeTodosAtributosSessao();
		redirecionaPagina(Pagina.LOGIN);
		getHttpSession().invalidate();
	}
	
	private void removeTodosAtributosSessao() {
		
		for (AtributoSessao attr : AtributoSessao.values()){
			limparAtributoDaSessao(attr);
		}
	}

	@Override
	protected HttpSession getHttpSession(){
		return getHttpRequest().getSession(Boolean.TRUE);
	}
	
	/**
	 * <p>Quando uma tela é redirecionada para outra, as mensagens informativas para o usuário podem se perder nessa transição.</p>
	 * 
	 * <p>Nesse caso esse método deve ser acionado atráves da tela JSF no qual você deseja mostrar as mensagens não renderizadas.</p>
	 * 
	 * <p>Exemplo: (Exemplo implementado na pagina consultarPessoa.xhtml)</p>
	 * 
	 * <ul>
	 * 	<li> 
	 * 		f:event listener="#{manageBean.renderizaMensagensDaSessao()}" type="preRenderView"  
	 *  </li>
	 * </ul>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void renderizaMensagensDaSessao() {
		
		Object listaMensagensSessao = getAtributoSessao(AtributoSessao.MENSAGEM);
		
		if (listaMensagensSessao != null && listaMensagensSessao instanceof List){
			
			List<FacesMessage> mensagens = (List<FacesMessage>) listaMensagensSessao;
			
			for (FacesMessage msg : mensagens){
				context().addMessage(null, msg);
			}
			
			limparAtributoDaSessao(AtributoSessao.MENSAGEM);
		}
	}
	
	/**
	 * 
	 * @return Contexto JSF da instância em execução.
	 */
	protected FacesContext context(){
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * 
	 * @return Contexto Externo JSF da instância em execução.
	 */
	protected ExternalContext externalContext() {
		return context().getExternalContext();
	}
	
	/**
	 * 
	 * @return HttpServletResponse a partir do contexto JSF.
	 */
	protected HttpServletResponse getHttpResponse(){
		return (HttpServletResponse) externalContext().getResponse();
	}

	/**
	 * 
	 * @return HttpServletRequest a partir do contexto JSF.
	 */
	protected HttpServletRequest getHttpRequest(){
		return (HttpServletRequest) externalContext().getRequest();
	}
	
	/**
	 * <p>Método de auxilio para gerar relatórios de maneira simples.</p>
	 * 
	 * <p>Passos:</p>
	 * 
	 * 	<ul>
	 *  	<li>1 - Criar um enum de {@link RelatorioEnum} (Ex.: RELATORIO_PESSOAS_ATIVAS("relatorioPessoaAtiva", TipoRelatorioEnum.PDF,"pasta_pessoa");)</li>
	 *  	<li>2 - Criar uma classe que implemente {@link AbstractRelatorioParametro} e implemente os métodos abstratos</li>
	 *  	<li>3 - No ManageBean filho passe como parametro o {@link RelatorioEnum} (parâmetro relatorio) e a classe filha de {@link AbstractRelatorioParametro} (parâmetro parametros)</li>
	 * 		<li>4 - O relatório será impresso na máquina do cliente.</li>
	 * 	</ul>
	 * 
	 * @param relatorio {@link RelatorioEnum}
	 * @param parametros Classe que implemente {@link AbstractRelatorioParametro}
	 * @throws NegocioException Exceção padrão do sistema
	 */
	protected void gerarRelatorio(RelatorioEnum relatorio, AbstractRelatorioParametro parametros) throws NegocioException{
		
		RelatorioUtil relatorioUtil = new RelatorioUtil(getHttpRequest(),getHttpResponse());
		
		relatorioUtil.gerarRelatorio(relatorio, parametros);
		
		context().renderResponse();
		context().responseComplete();
	}
	
	/**
	 * <p>Método que simplifica o acesso para atributos de sessão.</p>
	 * 
	 * <p>Todas as chaves de sessão devem ter as chaves dentro de {@link AtributoSessao}</p>
	 * 
	 * <p>O método para setar o atributo na sessão é o  {@link #setAtributoSessao(AtributoSessao, Object)}</p>
	 * 
	 * @param attSessao chave {@link AtributoSessao}
	 * 
	 * @return objeto na sessão
	 */
	protected Object getAtributoSessao(AtributoSessao attSessao){  
        return  getHttpSession().getAttribute(attSessao.name());  
    }  
      
	/**
	 * <p>Método que simplifica o envio de um objeto como atributo de sessão.</p>
	 * 
	 * <p>Todas as chaves de sessão devem ter as chaves dentro de {@link AtributoSessao}</p>
	 * 
	 * <p>O método para obter o atributo da sessão é o  {@link #getAtributoSessao(AtributoSessao)}</p>
	 * 
	 * @param attSessao chave {@link AtributoSessao}
	 * @param value {@link Object}
	 */
	protected void setAtributoSessao(AtributoSessao attSessao, Object value){  
        getHttpSession().setAttribute(attSessao.name(), value);
    }

	/***
	 * Remove o objeto da sessão.
	 * 
	 * @param name chave {@link AtributoSessao}
	 */
	protected void limparAtributoDaSessao(AtributoSessao name) {
		setAtributoSessao(name, null);
	}
	
	public void enviaMensagem(Mensagem mensagem){
		
		context().addMessage(null,facesMensagem(mensagem));
	}
	
	public FacesMessage facesMensagem (Mensagem mensagem){
		
		String msg = InitMessageProperties.getValue(mensagem);
		
		return new FacesMessage(TipoMensagem.severitFacesMessage(mensagem.getTipo()), msg, mensagem.getTipo().getLabel());
	}
	
	protected RequestContext contextPrimefaces(){
		return RequestContext.getCurrentInstance();
	}

	public void abrirModal(String varModal) {
		
		contextPrimefaces().execute("PF('"+varModal+"').show()");
	}

	public void fecharModal(String varModal) {
		
		contextPrimefaces().execute("PF('"+varModal+"').hide()");
	}
	
	public void updateComponente(String idComponente){
		contextPrimefaces().update(idComponente);
	}
	
	public void updateForm(){
		contextPrimefaces().update(FORM);
	}

	protected AutorizacaoEnum getAutorizacao() {
		return autorizacao;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public GrupoUsuario getGrupoUsuarioLogado() {
		return grupoUsuarioLogado;
	}
}