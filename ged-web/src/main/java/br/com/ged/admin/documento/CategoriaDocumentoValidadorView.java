package br.com.ged.admin.documento;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.entidades.Categoria;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;
import br.com.ged.service.CategoriaDocumentoService;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class CategoriaDocumentoValidadorView extends AbstractValidacao{
	
	@EJB
	private CategoriaDocumentoService categoriaService;
	
	public void valida(Categoria categoria) throws NegocioException {
		
		if (categoria == null || StringUtils.isBlank(categoria.getDescricao())){
			throw new NegocioException(Mensagem.CATDOC1);
		}
	}
	
	public void validaCadastro(Categoria categoria) throws NegocioException{
		
		valida(categoria);
		
		FiltroCategoriaDocumentoDTO filtro = new FiltroCategoriaDocumentoDTO();
		filtro.setDescricao(categoria.getDescricao());
		
		List<Categoria> cats = categoriaService.pesquisar(filtro);
		
		if (!listVazia(cats)){
			throw new NegocioException(Mensagem.CATDOC3);
		}
	}

	public void validaAlteracao(Categoria categoria) throws NegocioException {
		
		valida(categoria);
		
		FiltroCategoriaDocumentoDTO filtro = new FiltroCategoriaDocumentoDTO();
		filtro.setDescricao(categoria.getDescricao());
		
		List<Categoria> cats = categoriaService.pesquisar(filtro);
		
		if (!listVazia(cats)){
			
			if (!cats.iterator().next().getId().equals(categoria.getId())){
				throw new NegocioException(Mensagem.CATDOC3);
			}
		}
	}
}