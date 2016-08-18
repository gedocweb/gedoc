package br.com.ged.controller.pessoa;

import java.util.Date;

public class PessoaRelatorioBean{
		
	private String nome;
	private String sexo;
	private Date dataNascimento;
	
	public PessoaRelatorioBean(String nome, String sexo, Date dataNascimento) {
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public String getSexo() {
		return sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}
}