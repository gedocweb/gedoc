package br.com.ged.generics.model;

import java.io.Serializable;
import java.util.List;

import br.com.ged.generics.EntidadeBasica;

public interface GenericPersistence<T extends EntidadeBasica, ID extends Serializable> {

	void salvar(T t) ;

	T getById(Class<T> clazz,ID id);

	List<T> listarTodos(Class<T> clazz);

	T getById(Class<T> clazz, ID id, String... camposInitialize);

	List<T> listarTodos(Class<T> class1, String... camposInitialize);

	void excluir(T id);
	
	T merge(T t);
}
