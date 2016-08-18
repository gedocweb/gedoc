/**
 * Disclaimer: this code is only for demo no production use
 */
package br.com.ged.controller.pessoa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.framework.AbstractManageBean;

@ManagedBean(name="gedBeanAlterar")
@ViewScoped	
public class PessoaControllerAltera extends AbstractManageBean{
	
//	@EJB
//	protected PessoaValidadorView validationView;
//	
//	@EJB
//	protected GenericServiceController<Pessoa, Long> genericServiceExemplo;
//	
//	private Pessoa entidade;
//
//	@PostConstruct
//	public void init() throws NegocioException{
//		
//		Object entidadeParaEditar = getAtributoSessao(AtributoSessao.OBJ_ALTERAR_PESSOA);
//		
//		if (entidadeParaEditar != null && entidadeParaEditar instanceof Pessoa){
//			
//			entidade = (Pessoa) entidadeParaEditar;
//		}else{
//			throw new NegocioException(Mensagem.MEI010);
//		}
//	}
//	
//	public void alterar() throws NegocioException{
//		
//		validationView.verificarExcessoCaracteres(entidade.getNome());
//		genericServiceExemplo.salvar(entidade);
//		init();
//		
//		redirecionaPagina(Pagina.CONSULTAR_PESSOA);
//		
//		limparAtributoDaSessao(AtributoSessao.OBJ_ALTERAR_PESSOA);
//	}
//	
//	public Pessoa getEntidade() {
//		return entidade;
//	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.TODAS;
	}
}