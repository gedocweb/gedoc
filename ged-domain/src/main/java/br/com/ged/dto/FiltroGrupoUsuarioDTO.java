package br.com.ged.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Situacao;

public class FiltroGrupoUsuarioDTO {

	@EntityProperty("grupo")
	private String grupo;
	
	@EntityProperty("situacao")
	private Situacao situacao;
	
	private List<SelectItem> listFuncionalidades;
	
	public FiltroGrupoUsuarioDTO(){
		
		this.situacao = Situacao.ATIVO;
		this.listFuncionalidades = FuncionalidadeEnum.selectItems();
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<SelectItem> getListFuncionalidades() {
		return listFuncionalidades;
	}

	public void setListFuncionalidades(List<SelectItem> listFuncionalidades) {
		this.listFuncionalidades = listFuncionalidades;
	}
}