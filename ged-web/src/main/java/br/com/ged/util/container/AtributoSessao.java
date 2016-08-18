package br.com.ged.util.container;

import javax.servlet.http.HttpSession;

import br.com.ged.framework.AbstractManageBean;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Todas as chaves de atributos de {@link HttpSession} devem estar descritos neste enum.</p>
 * 
 * <p>Para adicionar atributos de sessão e obter o valor dos mesmos.</p>
 * 
 * <p>Sugestão é utilizar os métodos já existentes na classe {@link AbstractManageBean}</p>
 * 
 * <ul>
 * 	<li>void setAtributoSessao({@link AtributoSessao}, {@link Object});</li>
 * 	<li>{@link Object} getAtributoSessao({@link AtributoSessao})</li>
 * </ul>
 *
 */
public enum AtributoSessao {

	PERMISSOES_USUARIO,
	USUARIO,
	MENSAGEM, 
	OBJ_ALTERAR_USUARIO, 
	OBJ_ALTERAR_GRUPO_USUARIO;
	
}