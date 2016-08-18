package br.com.ged.dto;

import br.com.ged.anotations.EntityProperty;

public class FiltroTipoDocumentoDTO {

	@EntityProperty(value="descricao", ignoraCaseSensitive=true, pesquisaExata=true)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}