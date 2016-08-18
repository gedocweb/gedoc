package br.com.ged.controller.pessoa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.framework.AbstractManageBean;

@ManagedBean(name="gedBeanPesquisa")
@ViewScoped
public class PessoaControllerPesquisa extends AbstractManageBean{
	

//	@EJB
//	private GenericServiceController<Pessoa, Long> genericServiceExemplo;
//	
//	@EJB
//	private TemplateService gedService;
//	
//	private Pessoa entidadeSelecionada;
//	
//	private FiltroEntidadeExemploDTO filtroEntidadeExemploDTO;
//	
//	private List<Pessoa> entidades;
//	
//	@PostConstruct
//	public void init(){
//		
//		filtroEntidadeExemploDTO = new FiltroEntidadeExemploDTO();
//	}
//	
//	public void gerarRelatorio() throws NegocioException {
//		
//		PessoaRelatorioParametro parametroRelatorio = new PessoaRelatorioParametro(entidades);
//		
//		super.gerarRelatorio(RelatorioEnum.BALANCO_GASTO, parametroRelatorio);
//	}
//	
//	public void pesquisar(){
//		
//		entidades = gedService.pesquisar(filtroEntidadeExemploDTO);
//	}
//	
//	public void redirecionaParaTelaAlterar(Pessoa pessoa) throws IOException, NegocioException{
//		
//		setAtributoSessao(AtributoSessao.OBJ_ALTERAR_PESSOA, pessoa);
//		
//		redirecionaPagina(Pagina.ALTERAR_PESSOA);
//	}
//	
//	public void excluir(){
//		genericServiceExemplo.excluir(entidadeSelecionada);
//		this.pesquisar();
//	}
//	
//	public FiltroEntidadeExemploDTO getFiltroEntidadeExemploDTO() {
//		return filtroEntidadeExemploDTO;
//	}
//
//	public Pessoa getEntidadeSelecionada() {
//		return entidadeSelecionada;
//	}
//
//	public void setEntidadeSelecionada(Pessoa entidadeSelecionada) {
//		this.entidadeSelecionada = entidadeSelecionada;
//	}
//
//	public List<Pessoa> getEntidades() {
//		return entidades;
//	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.TODAS;
	}
}