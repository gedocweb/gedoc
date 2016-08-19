package br.com.ged.admin.usuario.grupo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.TipoFuncionalidadeEnum;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.excecao.NegocioException;

@ManagedBean(name="cadastroGrupoUsuario")
@ViewScoped
public class GrupoUsuarioCadastroController extends GrupoUsuarioSuperController{
	
	private String funcionalidadeSelecionada;
	private List<String> tipoFuncionalidades;
	private List<String> tipoFuncionalidadesSelecionadas;
	
	private boolean mostraDataTableTipoFuncionalidade;
	
	@PostConstruct
	public void inicio() {
		
		inicializaCampos();
		super.atualizaListaUsuariosSemGrupo();
	}

	private void inicializaCampos() {
		grupoUsuario = new GrupoUsuario();
		tipoFuncionalidadesSelecionadas = new ArrayList<String>();
		
		getGrupoUsuario().setFuncionalidades(new ArrayList<FuncionalidadeEnum>());
		getGrupoUsuario().setTiposFuncionalidades(new ArrayList<TipoFuncionalidadeEnum>());
		
		filtroGrupoUsuarioDTO = new FiltroGrupoUsuarioDTO();
		
		super.inicio();
	}
	
	public void cadastrar() {
		
		try {
			
			getGrupoUsuario().setUsuarios(super.getListUsuarioSelecionados());
			validacaoGrupoUsuario.valida(getGrupoUsuario());
			service.merge(getGrupoUsuario());
			this.inicializaCampos();
			this.renderizaDataTableFuncionalidade();
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	public void adicionarFuncionalidade(){
		
		FuncionalidadeEnum func = FuncionalidadeEnum.valueOf(FuncionalidadeEnum.class, funcionalidadeSelecionada);
		
		if (!getGrupoUsuario().getFuncionalidades().contains(func)){
			
			getGrupoUsuario().getFuncionalidades().add(func);
			
			adicionaTipoFuncionalidades(func);
		}
		
		renderizaDataTableFuncionalidade();
		
		limpaEntradaFuncionalidade();
	}
	
	private void adicionaTipoFuncionalidades(FuncionalidadeEnum func) {
		
		if (func == null){
			return;
		}
		
		Set<TipoFuncionalidadeEnum> listTp = new HashSet<>();
		
		for (TipoFuncionalidadeEnum tp : func.getPermissoes()){
			
			for (String labelTpSelecionado : tipoFuncionalidadesSelecionadas){
				
				if (tp.getLabel().equals(labelTpSelecionado)){
					
					listTp.add(tp);
				}
			}
		}
		
		getGrupoUsuario().getTiposFuncionalidades().addAll(listTp);
	}
	
	public void excluir(){
		
		excluirFuncionalidade(getGrupoUsuario());
		
		renderizaDataTableFuncionalidade();
	}

	private void renderizaDataTableFuncionalidade() {
		mostraDataTableTipoFuncionalidade = !getGrupoUsuario().getFuncionalidades().isEmpty();
	}

	private void limpaEntradaFuncionalidade() {
		funcionalidadeSelecionada = null;
		tipoFuncionalidades = new ArrayList<>();
	}

	public void carregaTipoFuncionalidade(){
		
		if (StringUtils.isBlank(funcionalidadeSelecionada)){
			tipoFuncionalidades = new ArrayList<>();
			return;
		}
		
		tipoFuncionalidades = FuncionalidadeEnum.listTipos(funcionalidadeSelecionada, getUsuarioLogado().getRole());
		tipoFuncionalidadesSelecionadas.addAll(tipoFuncionalidades);
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.CADASTRAR_GRUPO_USUARIO;
	}

	public String getFuncionalidadeSelecionada() {
		return funcionalidadeSelecionada;
	}

	public void setFuncionalidadeSelecionada(String funcionalidadeSelecionada) {
		this.funcionalidadeSelecionada = funcionalidadeSelecionada;
	}

	public List<String> getTipoFuncionalidades() {
		return tipoFuncionalidades;
	}

	public void setTipoFuncionalidades(List<String> tipoFuncionalidades) {
		this.tipoFuncionalidades = tipoFuncionalidades;
	}

	public List<String> getTipoFuncionalidadesSelecionadas() {
		return tipoFuncionalidadesSelecionadas;
	}

	public void setTipoFuncionalidadesSelecionadas(List<String> tipoFuncionalidadesSelecionadas) {
		this.tipoFuncionalidadesSelecionadas = tipoFuncionalidadesSelecionadas;
	}

	public boolean isMostraDataTableTipoFuncionalidade() {
		return mostraDataTableTipoFuncionalidade;
	}

	public void setMostraDataTableTipoFuncionalidade(boolean mostraDataTableTipoFuncionalidade) {
		this.mostraDataTableTipoFuncionalidade = mostraDataTableTipoFuncionalidade;
	}
}