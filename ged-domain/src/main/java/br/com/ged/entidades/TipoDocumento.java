package br.com.ged.entidades;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ged.domain.Situacao;
import br.com.ged.generics.EntidadeBasica;
 
@Entity
@Table(name = "tb_tipo_doc")
public class TipoDocumento extends EntidadeBasica implements Comparable<TipoDocumento>{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_tipo_doc")
	@GeneratedValue(generator = "seq_tipo_doc", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_tipo_doc", sequenceName = "seq_tipo_doc",allocationSize=1)
	private Long id;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="situacao")
	private Situacao situacao;
	
	public TipoDocumento(){
		situacao = Situacao.ATIVO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int compareTo(TipoDocumento o) {
		return this.getDescricao().toLowerCase().compareTo(o.getDescricao().toLowerCase());
	}
}