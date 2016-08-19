package br.com.ged.entidades;
 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoFuncionalidadeEnum;
import br.com.ged.generics.EntidadeBasica;
 
@Entity
@Table(name = "tb_grupo_usuario")
public class GrupoUsuario extends EntidadeBasica {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_grupo_usuario")
	@GeneratedValue(generator = "seq_grupo_usuario", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_grupo_usuario", sequenceName = "seq_grupo_usuario",allocationSize=1)
	private Long id;
	
	@Column(name="grupo")
	private String grupo;
	
	@Column(name="situacao")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@ElementCollection(targetClass=FuncionalidadeEnum.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name="rl_func_grupo_usuario")
    @Column(name="id_funcionalidade") 
	private List<FuncionalidadeEnum> funcionalidades;
	
	@ElementCollection(targetClass=TipoFuncionalidadeEnum.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name="rl_tipo_func_grupo_usuario")
    @Column(name="id_permissao") 
	private List<TipoFuncionalidadeEnum> tiposFuncionalidades;
	
	
//	@OneToMany(mappedBy="grupoUsuario", targetEntity=Usuario.class)
//	@JoinTable
//	  (
//	      name="rl_usuario_grupo_usuario",
//	      joinColumns={ @JoinColumn(name="id_grupo_usuario", referencedColumnName="id_grupo_usuario") },
//	      inverseJoinColumns={ @JoinColumn(name="usuario_id", referencedColumnName="id", unique=true) }
//	)
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH})
	@JoinTable(name = "rl_grupousuario_usuario", 
		joinColumns = @JoinColumn(name = "id_grupo_usuario"), 
		inverseJoinColumns = @JoinColumn(name = "id_usuario")
	)
    private List<Usuario> usuarios;
	
	public GrupoUsuario(){
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

	public List<FuncionalidadeEnum> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadeEnum> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public List<TipoFuncionalidadeEnum> getTiposFuncionalidades() {
		return tiposFuncionalidades;
	}

	public void setTiposFuncionalidades(List<TipoFuncionalidadeEnum> tiposFuncionalidades) {
		this.tiposFuncionalidades = tiposFuncionalidades;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}