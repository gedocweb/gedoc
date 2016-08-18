package br.com.ged.admin.documento;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroTipoDocumentoDTO;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;
import br.com.ged.service.TipoDocumentoService;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class TipoDocumentoValidadorView extends AbstractValidacao{
	
	@EJB
	private TipoDocumentoService tipoService;
	
	public void valida(TipoDocumento tipo) throws NegocioException {
		
		if (tipo == null || StringUtils.isBlank(tipo.getDescricao())){
			throw new NegocioException(Mensagem.CATDOC1);
		}
	}
	
	public void validaCadastro(TipoDocumento tipo) throws NegocioException{
		
		valida(tipo);
		
		FiltroTipoDocumentoDTO filtro = new FiltroTipoDocumentoDTO();
		filtro.setDescricao(tipo.getDescricao());
		
		List<TipoDocumento> cats = tipoService.pesquisar(filtro);
		
		if (!listVazia(cats)){
			throw new NegocioException(Mensagem.CATDOC3);
		}
	}

	public void validaAlteracao(TipoDocumento tipo) throws NegocioException {
		
		valida(tipo);
		
		FiltroTipoDocumentoDTO filtro = new FiltroTipoDocumentoDTO();
		filtro.setDescricao(tipo.getDescricao());
		
		List<TipoDocumento> cats = tipoService.pesquisar(filtro);
		
		if (!listVazia(cats)){
			
			if (!cats.iterator().next().getId().equals(tipo.getId())){
				throw new NegocioException(Mensagem.CATDOC3);
			}
		}
	}
}