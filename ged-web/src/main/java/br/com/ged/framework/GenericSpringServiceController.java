package br.com.ged.framework;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.ged.generics.EntidadeBasica;
import br.com.ged.generics.service.GenericService;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Classe deve ser injetada em Controllers para ter acesso a funcionalidades já implementadas genericamente.</p>
 * 
 * <ul>
 *  	<li>Injetar na sua classe (De preferência no CONTROLLER seguindo o padrão MVC ou em algum validador de view).<br>

			<br>Exemplo de uso:<br>
 *  	 	<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
		</li>
 * </ul>
 * 
 *
 * @param <T> deve ser referência de uma entidade filha da {@link EntidadeBasica}
 * @param <ID> deve ser a chave primária da entidade (Objeto que implemente {@link Serializable})
 */

@Controller
public class GenericSpringServiceController<T extends EntidadeBasica, ID extends Serializable> {
	
	/**
	 * Possui os mesmos métodos desta classe, mas sem a opção do Interceptor para enviar mensagens para tela do usuário.
	 */
	@Autowired
	private GenericService<T, Serializable> genericService;

	/**
	 * <p>Persiste entidade no banco de dados e envia mensagem a tela do usuário automaticamente a partir do interceptor.</p>
	 * 
	 * 
			Exemplo de uso:<br>
   	 		<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
			<br>
			{@code private Pessoa pessoa;} 
			<br><br>
			
			{@code //Supondo que objeto pessoa já esteja preenchido.} 
			<br>
			
			{@code genericService.salvar(pessoa);}
	 * 
	 * @param t deve ser referência de uma entidade filha da {@link EntidadeBasica}
	 */
	public void salvar(T t) {
		genericService.salvar(merge(t));
	}

	/**
	 * <p>Método retorna entidade a partir do valor da chave primaria passado.</p>
	 * 

			Exemplo de uso:<br>
   	 		<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
			<br>
			{@code private Log id;} 
			<br>
			{@code private Pessoa pessoa;} 
			<br><br>
			
			{@code //Supondo que a variável id já esteja preenchida.} 
			<br>
			
			{@code pessoa = genericService.getById(Pessoa.class, id);}
	 * 
	 * 
	 * @param clazz deve ser o class de uma entidade filha da {@link EntidadeBasica}
	 * @param id deve ser a chave primária da entidade filha da {@link EntidadeBasica} (Objeto que implemente {@link Serializable})
	 * 
	 * @return
	 * 
	 * Uma entidade filha da {@link EntidadeBasica}
	 */
	public T getById(Class<T> clazz, Serializable id) {
		return genericService.getById(clazz, id);
	}
	
	/**
	 * <p>Método retorna entidade a partir do valor da chave primaria passado.</p>
	 * 

			Exemplo de uso:<br>
   	 		<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
			<br>
			{@code private Log id;} 
			<br>
			{@code private Pessoa pessoa;} 
			<br><br>
			
			{@code //Supondo que a variável id já esteja preenchida.} 
			<br>
			{@code pessoa = genericService.getById(Pessoa.class, id, "data","endereco.uf");}
	 * 
	 * 
	 * @param clazz deve ser o class de uma entidade filha da {@link EntidadeBasica}
	 * @param id deve ser a chave primária da entidade filha da {@link EntidadeBasica} (Objeto que implemente {@link Serializable})
	 * @param camposInitialize deve ser o nome das propriedades da entidade (No caso de objetos aninhados utilizar o '.' para separar os objetos, semelhante ao exemplo de endereço acima) que devem vir já inicializadas (Por padrão as consultas retornam objetos com o tipo Lazy do hibernate)
	 * 
	 * @return
	 * 
	 * Uma entidade filha da {@link EntidadeBasica}
	 */
	public T getById(Class<T> clazz, Serializable id, String... camposInitialize) {
		return genericService.getById(clazz, id, camposInitialize);
	}

	/**
	 * 
	 *  <p>Método retorna todas as entidades da classe passada como parâmetro.</p>
	 * 

			Exemplo de uso:<br>
   	 		<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
			<br>
			{@code private List<Pessoa> listPessoa;} 
			<br><br>
			
			{@code listPessoa = genericService.listarTodos(Pessoa.class);}
	 * 
	 * @param  clazz deve ser o class de uma entidade filha da {@link EntidadeBasica}
	 * 
	 * @return
	 * 
	 * Todos os registros do banco de dados referente a entidade passada como parâmetro.
	 */
	public List<T> listarTodos(Class<T> clazz) {
		return genericService.listarTodos(clazz);
	}

	/**
	 * <p>Remove entidade no banco de dados e envia mensagem a tela do usuário automaticamente a partir do interceptor.</p>
	 * 
	 * 
			Exemplo de uso:<br>
   	 		<br>@EJB <br>
			{@code private GenericServiceController<Pessoa, Long> genericService;} 
			<br>
			{@code private Pessoa pessoa;} 
			<br><br>
			
			{@code //Supondo que a entidade esteja preenchida com a chave primária.} 
			<br>
			
			{@code genericService.excluir(pessoa);}
	 * 
	 * @param t deve ser referência de uma entidade filha da {@link EntidadeBasica}
	 */
	public void excluir(T t) {
		genericService.excluir(t);
	}

	public T merge(T t) {
		return genericService.merge(t);
	}
}