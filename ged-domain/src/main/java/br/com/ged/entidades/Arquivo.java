package br.com.ged.entidades;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ged.domain.Situacao;
import br.com.ged.generics.EntidadeBasica;
 
@Entity
@Table(name = "tb_arquivo_doc")
public class Arquivo extends EntidadeBasica{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_arquivo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="content_type")
	private String contentType;
	
	@Column(name="arquivo")
	@Lob
	private byte[] arquivo;

	@Column(name="situacao")
	private Situacao situacao;
	
	@Transient
	private transient boolean pdf;
	
	@Transient
	private transient boolean imagem;
	
	public boolean isPdf() {
		return descricao != null && (descricao.endsWith(".pdf") || descricao.endsWith(".PDF"));
	}
	
	public boolean isImagem() {
		return descricao != null && (descricao.endsWith(".gif") || descricao.endsWith(".jpg") || descricao.endsWith(".png") ||
										descricao.endsWith(".GIF") || descricao.endsWith(".JPG") || descricao.endsWith(".PNG"));
	}

	public Arquivo(){
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

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}