package br.com.ged.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Situacao;

public class FiltroGrupoUsuarioDTO {

	@EntityProperty("grupo")
	private String grupo;
	
	@EntityProperty(value="grupo", ignoraCaseSensitive=true, pesquisaExata=true )
	private String grupoExato;
	
	@EntityProperty("situacao")
	private Situacao situacao;
	
	@EntityProperty("usuarios.id")
	private Long idUsuario;
	
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getGrupoExato() {
		return grupoExato;
	}

	public void setGrupoExato(String grupoExato) {
		this.grupoExato = grupoExato;
	}
}