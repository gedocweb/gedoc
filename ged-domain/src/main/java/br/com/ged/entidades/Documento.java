package br.com.ged.entidades;
 
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ged.domain.Situacao;
import br.com.ged.generics.EntidadeBasica;
 
@Entity
@Table(name = "tb_documento")
public class Documento extends EntidadeBasica{
 
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_documento")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="observacao")
	private String observacao;

	@Column(name="situacao")
	private Situacao situacao;
	
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_doc")
	private TipoDocumento tipoDocumento;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_arquivo")
	private Arquivo arquivo;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inclusao")
	private Date dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_documento")
	private Date dataDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
	public Documento(){
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}