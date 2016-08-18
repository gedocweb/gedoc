package br.com.ged.admin.usuario.grupo;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class GrupoUsuarioValidadorView extends AbstractValidacao{
	
	public void valida(GrupoUsuario grupoUsuario) throws NegocioException {
		camposObrigatorios(grupoUsuario);
	}

	private void camposObrigatorios(GrupoUsuario grupoUsuario) throws NegocioException {
		
		if (StringUtils.isBlank(grupoUsuario.getGrupo())){
			throw new NegocioException(Mensagem.GRUPOUSUARIO11);
		}
		
		if ( listVazia(grupoUsuario.getFuncionalidades())){
			throw new NegocioException(Mensagem.GRUPOUSUARIO12);
		}
		
		if ( listVazia(grupoUsuario.getTiposFuncionalidades())){
			throw new NegocioException(Mensagem.GRUPOUSUARIO13);
		}
	}
}