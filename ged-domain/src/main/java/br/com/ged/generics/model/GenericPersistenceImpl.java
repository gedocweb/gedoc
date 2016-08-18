package br.com.ged.generics.model;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.LockOptions;

import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.EntidadeBasica;
import br.com.ged.model.AbstractModel;

@Stateless
public class GenericPersistenceImpl<T extends EntidadeBasica, ID extends Serializable>
					extends AbstractModel implements GenericPersistence<T, ID> {
	
	@EJB
	private ConsultasDaoJpa<T> consultaReposiroty;

	@Override
	public void salvar(T t) {
		
		if (t.getId() == null) {
			em.persist(t);
		} else {
			em.merge(t);
		}
		
		em.flush();
		em.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Class<T> entityName, ID id) {
		return (T) consultaReposiroty.getSession().get(entityName.getName(), id, LockOptions.READ);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Class<T> entityName, ID id, String... camposInitialize) {
		return (T) consultaReposiroty.inicializaCampo(camposInitialize, getById(entityName, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listarTodos(Class<T> clazz) {
		return consultaReposiroty.getSession().createCriteria(clazz).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listarTodos(Class<T> clazz, String...camposInitialize) {
		return (List<T>) consultaReposiroty.inicializaCampo(camposInitialize, consultaReposiroty.getSession().createCriteria(clazz).list());
	}

	@Override
	public void excluir(T t) {
		em.remove(merge(t));
	}

	@Override
	public T merge(T t) {
		return em.merge(t);
	}
}