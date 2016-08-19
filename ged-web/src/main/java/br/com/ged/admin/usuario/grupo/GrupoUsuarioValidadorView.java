package br.com.ged.admin.usuario.grupo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;
import br.com.ged.service.GrupoUsuarioService;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class GrupoUsuarioValidadorView extends AbstractValidacao{
	
	@EJB
	private GrupoUsuarioService grupoUsuarioService;
	
	public void valida(GrupoUsuario grupoUsuario) throws NegocioException {
		camposObrigatorios(grupoUsuario);
	}
	
	public void validaCadastro(GrupoUsuario grupoUsuario) throws NegocioException{
		
		valida(grupoUsuario);
		
		List<GrupoUsuario> list = grupoUsuarioService.gruposUsuarioPorNome(grupoUsuario.getGrupo());
		
		if (!listVazia(list)){
			throw new NegocioException(Mensagem.GRUPOUSUARIO19);
		}
	}
	

	public void validaAlteracao(GrupoUsuario grupoUsuario) throws NegocioException {
		
		valida(grupoUsuario);
		
		validaNomeDuplicado(grupoUsuario);
	}

	public void validaNomeDuplicado(GrupoUsuario grupoUsuario) throws NegocioException {
		
		List<GrupoUsuario> list = grupoUsuarioService.gruposUsuarioPorNome(grupoUsuario.getGrupo());
		
		if (!listVazia(list)){
			
			if (!list.iterator().next().getId().equals(grupoUsuario.getId())){
				throw new NegocioException(Mensagem.GRUPOUSUARIO19);
			}
		}
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