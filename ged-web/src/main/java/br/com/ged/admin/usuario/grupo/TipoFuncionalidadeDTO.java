package br.com.ged.admin.usuario.grupo;

import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoFuncionalidadeEnum;

public class TipoFuncionalidadeDTO {

	private TipoFuncionalidadeEnum tipoFuncionalidadeEnum;
	private Situacao situacao;

	public TipoFuncionalidadeDTO() {
		super();
	}

	public TipoFuncionalidadeDTO(TipoFuncionalidadeEnum tipoFuncionalidadeEnum, Situacao situacao) {
		super();
		this.tipoFuncionalidadeEnum = tipoFuncionalidadeEnum;
		this.situacao = situacao;
	}

	public TipoFuncionalidadeEnum getTipoFuncionalidadeEnum() {
		return tipoFuncionalidadeEnum;
	}

	public void setTipoFuncionalidadeEnum(TipoFuncionalidadeEnum tipoFuncionalidadeEnum) {
		this.tipoFuncionalidadeEnum = tipoFuncionalidadeEnum;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}