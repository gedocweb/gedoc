package br.com.ged.framework;

import java.io.IOException;
import java.util.List;

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
import br.com.ged.domain.TipoFuncionalidadeEnum;
import br.com.ged.domain.TipoMensagem;
import br.com.ged.domain.relatorio.RelatorioEnum;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
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
	
	protected static final String FORM_PRIMEFACES = "@form";
	
	private AutorizacaoEnum autorizacao;
	protected boolean paraThread;
	
	@EJB
	protected UsuarioService usuarioService;
	
	private Usuario usuarioLogado;
	
	@Override
	public boolean autorizaFuncionalidade(TipoFuncionalidadeEnum tipoFuncionalidade){
		
		return FuncionalidadeEnum.verificaAutorizacaoComAcessoNaFuncionalidade(autorizacao, tipoFuncionalidade);
	}
	
	@PostConstruct
	@Override
	public void validaPermissionamento() throws IOException, NegocioException {
		
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	autorizacao = AutorizacaoEnum.usuarioComAcesso(getPaginaManageBean(), permissoesUsuarioLogado());
	    	
	    	if (autorizacao == null || !authentication.isAuthenticated()) {

	    		logout();
	    		
	        }else{
	        	
	        	usuarioLogado = usuario();
	        }
	    	
		}catch (Exception ex){
			
			ex.printStackTrace();
			logout();
		}
    	
	}
	
	public Usuario usuario(){
		
		Object username = getAtributoSessao(AtributoSessao.USUARIO);
		Usuario usuario = null;
		
		if (username != null){
			usuario = usuarioService.usuarioPorUsername(username.toString());
		}
		
		return usuario;
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
		redirecionaPagina(Pagina.LOGIN);
		getHttpSession().invalidate();
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

	protected AutorizacaoEnum getAutorizacao() {
		return autorizacao;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
}