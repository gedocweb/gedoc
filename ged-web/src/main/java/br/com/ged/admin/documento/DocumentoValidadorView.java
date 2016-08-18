package br.com.ged.admin.documento;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.Documento;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class DocumentoValidadorView extends AbstractValidacao{
	
	public void validaPesquisa(Documento documento) throws NegocioException {
		
		if (documento.getCategoria() == null || documento.getCategoria().getId() == null){
			throw new NegocioException(Mensagem.DOCPESQUISA1);
		}
	}

	public void validaCadastro(Documento documento) throws NegocioException {

		if (documento.getCategoria() == null || documento.getCategoria().getId() == null){
			throw new NegocioException(Mensagem.DOCCADASTRO1);
		}
		
		if (documento.getTipoDocumento() == null || documento.getTipoDocumento().getId() == null){
			throw new NegocioException(Mensagem.DOCCADASTRO2);
		}

		if (StringUtils.isBlank(documento.getDescricao())){
			throw new NegocioException(Mensagem.DOCCADASTRO3);
		}

		if (documento.getDataDocumento() == null){
			throw new NegocioException(Mensagem.DOCCADASTRO4);
		}

		if (documento.getArquivo() == null){
			throw new NegocioException(Mensagem.DOCCADASTRO5);
		}
	}
}