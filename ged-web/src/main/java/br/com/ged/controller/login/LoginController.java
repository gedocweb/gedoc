package br.com.ged.controller.login;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.Role;
import br.com.ged.domain.Situacao;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.Documento;
import br.com.ged.entidades.Pessoa;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.entidades.Usuario;
import br.com.ged.framework.AbstractManageBean;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.util.criptografia.CriptografiaUtil;

/**
 * 
 * @author pedro.oliveira
 * 
 *         <p>
 *         Classe responsável por ser o filtro do primeiro acesso interno dentro
 *         do sistema
 *         </p>
 *         <p>
 *         Ao tentar logar no sistema a ação passa por esse manage bean e é
 *         passado a requisição para o Spring Security realizar a autenticação.
 *         </p>
 * 
 *         <p>
 *         Completando a resposta, o contexto do Spring Security vai enviar a
 *         requisição para o método do autenticador customizado do projeto
 *         AuthenticationProviderCustom.java método authenticate(Authentication)
 *         </p>
 */
@ManagedBean
@RequestScoped
public class LoginController extends AbstractManageBean {

	private static final String SPRING_SECURITY = "/j_spring_security_check";
	
	@EJB
	private GenericServiceController<Usuario, Long> usuarioService;
	
	@EJB
	private GenericServiceController<Categoria, Long> categoriaService;
	
	@EJB
	private GenericServiceController<TipoDocumento, Long> serviceTipoDocumento;
	
	@EJB
	private GenericServiceController<Documento, Long> serviceDocumento;
	
	@PostConstruct
	public void utilizandoBancoEmMemoria(){
		
		criaUsuarioInicial();
		
		criaCategoriaDocumento();
		
		criaTipoDocumento();
		
//		Documento documento = new Documento();
//		documento.setDescricao("Livro a");
//		
//		Documento documento1 = new Documento();
//		documento1.setDescricao("Livro b");
//		
//		Documento documento2 = new Documento();
//		documento2.setDescricao("Livro c");
//		
//		Documento documento3 = new Documento();
//		documento3.setDescricao("Livro d");
//		
//		Documento documento4 = new Documento();
//		documento4.setDescricao("Livro e");
//		
//		serviceDocumento.salvar(documento);
//		serviceDocumento.salvar(documento1);
//		serviceDocumento.salvar(documento2);
//		serviceDocumento.salvar(documento3);
//		serviceDocumento.salvar(documento4);
	}

	private void criaTipoDocumento() {
		TipoDocumento tipoDoc = new TipoDocumento();
		
		tipoDoc.setDescricao("teste");
		
		serviceTipoDocumento.salvar(tipoDoc);
	}

	private void criaCategoriaDocumento() {
		Categoria documentosAntigos = new Categoria();
		Categoria documentosAntigosAntesDeCristo = new Categoria();
		Categoria documentosAntigosAntesDeCristo1 = new Categoria();
		Categoria documentosAntigosDepoisDeCristo = new Categoria();
		
		Categoria documentosNovos = new Categoria();
		
		documentosAntigos.setDescricao("Documentos Antigos");
		documentosNovos.setDescricao("Documentos Novos");
		
		documentosAntigosAntesDeCristo.setCategoriaPai(documentosAntigos);
		documentosAntigosAntesDeCristo.setDescricao("Documentos Antes de Cristo");
		
		documentosAntigosAntesDeCristo1.setCategoriaPai(documentosAntigosAntesDeCristo);
		documentosAntigosAntesDeCristo1.setDescricao("Antigo Testamento");
		
		documentosAntigosDepoisDeCristo.setCategoriaPai(documentosAntigos);
		documentosAntigosDepoisDeCristo.setDescricao("Documentos Depois de Cristo");
		
		documentosAntigos.setCategoriaFilha(Arrays.asList(documentosAntigosAntesDeCristo, documentosAntigosDepoisDeCristo));
		
		categoriaService.salvar(documentosAntigos);
		categoriaService.salvar(documentosAntigosAntesDeCristo);
		categoriaService.salvar(documentosAntigosDepoisDeCristo);
		categoriaService.salvar(documentosNovos);
		categoriaService.salvar(documentosAntigosAntesDeCristo1);
	}

	private void criaUsuarioInicial() {
		Usuario usuario = new Usuario();
		Pessoa pessoa = new Pessoa();
		
		pessoa.setCpf("03066547175");
		pessoa.setNome("Pedro Augusto de Oliveira");
		pessoa.setSituacao(Situacao.ATIVO);
		
		usuario.setUsuario("admin");
		usuario.setSenha(CriptografiaUtil.criptografar("admin"));
		usuario.setSituacao(Situacao.ATIVO);
		usuario.setPessoa(pessoa);
		usuario.setRole(Role.ADMIN);
		
		Usuario usuario1 = new Usuario();
		Pessoa pessoa1 = new Pessoa();
		
		pessoa1.setCpf("98797987987");
		pessoa1.setNome("Jão José");
		pessoa1.setSituacao(Situacao.ATIVO);
		
		usuario1.setUsuario("joao");
		usuario1.setSenha(CriptografiaUtil.criptografar("joao"));
		usuario1.setSituacao(Situacao.ATIVO);
		usuario1.setPessoa(pessoa1);
		usuario1.setRole(Role.ADMIN);
		
		usuarioService.salvar(usuario);
		usuarioService.salvar(usuario1);
	}

	public void doLogin() throws IOException, ServletException {

		RequestDispatcher dispatcher = getHttpRequest().getRequestDispatcher(
				SPRING_SECURITY);

		dispatcher.forward(getHttpRequest(), getHttpResponse());
		context().responseComplete();
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.LOGIN;
	}
}