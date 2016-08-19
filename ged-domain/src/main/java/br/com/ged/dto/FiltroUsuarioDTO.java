package br.com.ged.dto;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.Role;
import br.com.ged.domain.Situacao;

public class FiltroUsuarioDTO {

	@EntityProperty("usuario")
	private String usuario;
	
	@EntityProperty("situacao")
	private Situacao situacao;
	
	@EntityProperty("role")
	private Role role;
	
	@EntityProperty("pessoa.nome")
	private String nomeUsuario;
	
	public FiltroUsuarioDTO(){
		situacao = Situacao.ATIVO;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
}