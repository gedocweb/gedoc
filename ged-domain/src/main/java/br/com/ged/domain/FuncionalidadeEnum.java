package br.com.ged.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Enum reposável por associar AutorizacaoEnum.java (Que já possui as Role.java de autorização para acesso a cada Pagina.java) a cada funcionalidade.</p>
 * 
 *
 */
public enum FuncionalidadeEnum {

	/**
	 * ADMINISTRADOR - Acesso a todas as funcionalidades
	 */
	MANTER_GRUPO_USUARIO("Manter Grupo de Usuário", 
			AutorizacaoEnum.ADMINISTRADOR, 
				TipoFuncionalidadeEnum.CADASTRAR_GRUPO_USUARIO, 
				TipoFuncionalidadeEnum.PESQUISAR_GRUPO_USUARIO);
	
	private String label;

	private AutorizacaoEnum autorizacao;
	
	private TipoFuncionalidadeEnum[] funcionalidades;
	
	private FuncionalidadeEnum(String label, AutorizacaoEnum autorizacao, TipoFuncionalidadeEnum... funcionalidades){
		
		this.label = label;
		this.autorizacao = autorizacao;
		this.funcionalidades = funcionalidades;
	}
	
	/**
	 * Verifica se o tipoFuncionalidade(TipoFuncionalidadeEnum) solicitada está dentro das que tem o {@code auth}(AutorizacaoEnum) do usuario logado. 
	 * 
	 * 
	 * @param auth variavel de instância dentro do AbstractManageBean, inicializada pelo usuario logado
	 * @param tipoFuncionalidade funcionalidade que solicita acesso 
	 * @return true caso o {@code tipoFuncionalidade} esteja dentre as permitidas no {@code auth}
	 */
	public static boolean verificaAutorizacaoComAcessoNaFuncionalidade(AutorizacaoEnum auth, TipoFuncionalidadeEnum tipoFuncionalidade) {
		
		boolean funcionalidadeLiberada = Boolean.FALSE;
		
		for (FuncionalidadeEnum funcionalidade : values()){
			
			if (funcionalidade.autorizacao.equals(auth) && contemFuncionalidade(tipoFuncionalidade, funcionalidade)){
				funcionalidadeLiberada = Boolean.TRUE;
			}
		}
		
		return funcionalidadeLiberada;
	}

	private static boolean contemFuncionalidade(TipoFuncionalidadeEnum tipoFuncionalidade, FuncionalidadeEnum funcionalidade) {
		
		List<TipoFuncionalidadeEnum> tipoFuncionalidadeList = Arrays.asList(funcionalidade.funcionalidades);
		boolean contemFuncionalidade = Boolean.FALSE;
		
		if (tipoFuncionalidadeList.contains(tipoFuncionalidade)){
			contemFuncionalidade = Boolean.TRUE;
		}
		
		return contemFuncionalidade;
	}

	public String getLabel() {
		return label;
	}

	public static List<SelectItem> selectItems() {
		
		List<SelectItem> list = new ArrayList<>();
		
		for (FuncionalidadeEnum func : values()){
			
			SelectItem si = new SelectItem();
			
			si.setLabel(func.label);
			si.setValue(func.name());
			
			list.add(si);
		}
		
		return list;
	}

	public static List<String> listTiposPorName(String funcionalidadeSelecionada) {
		
		List<String> list = new ArrayList<>();
		
		for (FuncionalidadeEnum func : values()){
			
			if (func.name().equals(funcionalidadeSelecionada)){
				
				for (TipoFuncionalidadeEnum tipoFunc : func.funcionalidades){
					
					list.add(tipoFunc.getLabel());
				}
				
				break;
			}
		}
		
		return list;
	}

	public TipoFuncionalidadeEnum[] getFuncionalidades() {
		return funcionalidades;
	}
}