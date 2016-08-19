package br.com.ged.admin.usuario.grupo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.CellEditEvent;

import br.com.ged.domain.FuncionalidadeEnum;
import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;

@ManagedBean(name="painelGrupoUsuario")
@ViewScoped
public class GrupoUsuarioPainelController extends GrupoUsuarioSuperController{
	
	private List<GrupoUsuario> listGrupoUsuario;
	private GrupoUsuario grupoUsuarioSelecionado;
	
	private Usuario usuarioSelecionado;
	
	private FuncionalidadeEnum funcionalidadeSelecionadaParaAdicionar;
	
	private String nomeGrupoAntigo;
	private String nomeGrupoNovo;
	
	@PostConstruct
	public void inicio(){
		
		filtroGrupoUsuarioDTO = new FiltroGrupoUsuarioDTO();
	}
	
	public void pesquisar(){
		
		listGrupoUsuario = grupoUsuarioService.pesquisar(filtroGrupoUsuarioDTO,"usuarios", "usuarios.pessoa","funcionalidades","tiposFuncionalidades");
		
		for (GrupoUsuario gu : listGrupoUsuario){
			
			List<Usuario> list = new ArrayList<>();
			
			for (Usuario usuario : gu.getUsuarios()){
				
				list.add(usuario);
			}
				
			gu.setUsuarios(list);	
		}
	}
	
	public void alterarNomeGrupo(){
		
		try {
			
			List<GrupoUsuario> list = grupoUsuarioService.gruposUsuarioPorNome(nomeGrupoAntigo);
			
			if (list == null || list.isEmpty()){
				return;
			}
			
			GrupoUsuario gu = list.iterator().next();
			
			gu.setGrupo(nomeGrupoNovo);
			
			validacaoGrupoUsuario.validaNomeDuplicado(gu);
			
			service.merge(gu);
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void onCellEdit(CellEditEvent event) {
		
		Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if (newValue != null && StringUtils.isNotBlank(newValue.toString())){
        	
        	this.nomeGrupoAntigo = oldValue.toString();
        	this.nomeGrupoNovo = newValue.toString();
        	
        	abrirModal("confirmaAlterarNomeGrupoDG");
        }
    }
	
	public void limpaPermissoes(){
		setListPermissoes(new ArrayList<TipoFuncionalidadeDTO>());
	}
	
	public void adicionarNovaFuncionalidade(){
		
		if (funcionalidadeSelecionadaParaAdicionar == null){
			enviaMensagem(Mensagem.GRUPOUSUARIO17);
			return;
		}
		
		getGrupoUsuarioSelecionado().getFuncionalidades().add(funcionalidadeSelecionadaParaAdicionar);
		service.merge(getGrupoUsuarioSelecionado());
	}
	
	public void adicionaNovosUsuarios(){
		
		getGrupoUsuarioSelecionado().getUsuarios().addAll(getListUsuarioSelecionados());
	}
	
	public void atualizaUsuarios(){
		
		service.merge(getGrupoUsuarioSelecionado());
	}
	
	public void excluir(){
		
		if (!service.singleLine(GrupoUsuario.class)){
			
			service.excluir(grupoUsuarioSelecionado);
			this.pesquisar();
		}else{
			enviaMensagem(Mensagem.GRUPOUSUARIO20);
		}
	}
	
	public void excluirUsuario(){
		
		getGrupoUsuarioSelecionado().getUsuarios().remove(usuarioSelecionado);
		service.merge(getGrupoUsuarioSelecionado());
	}
	
	public void ativarGrupoUsuario(GrupoUsuario grupoUsuario){
		
		grupoUsuario.setSituacao(Situacao.ATIVO);
		service.salvar(grupoUsuario);
	}
	
	public void inativarGrupoUsuario(GrupoUsuario grupoUsuario){
		
		grupoUsuario.setSituacao(Situacao.INATIVO);
		service.salvar(grupoUsuario);
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_GRUPO_USUARIO;
	}

	public List<GrupoUsuario> getListGrupoUsuario() {
		return listGrupoUsuario;
	}

	public GrupoUsuario getGrupoUsuarioSelecionado() {
		return grupoUsuarioSelecionado;
	}

	public void setGrupoUsuarioSelecionado(GrupoUsuario grupoUsuarioSelecionado) {
		this.grupoUsuarioSelecionado = grupoUsuarioSelecionado;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public FuncionalidadeEnum getFuncionalidadeSelecionadaParaAdicionar() {
		return funcionalidadeSelecionadaParaAdicionar;
	}

	public void setFuncionalidadeSelecionadaParaAdicionar(FuncionalidadeEnum funcionalidadeSelecionadaParaAdicionar) {
		this.funcionalidadeSelecionadaParaAdicionar = funcionalidadeSelecionadaParaAdicionar;
	}

	public String getNomeGrupoAntigo() {
		return nomeGrupoAntigo;
	}

	public void setNomeGrupoAntigo(String nomeGrupoAntigo) {
		this.nomeGrupoAntigo = nomeGrupoAntigo;
	}

	public String getNomeGrupoNovo() {
		return nomeGrupoNovo;
	}

	public void setNomeGrupoNovo(String nomeGrupoNovo) {
		this.nomeGrupoNovo = nomeGrupoNovo;
	}
}