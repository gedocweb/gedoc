package br.com.ged.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.Documento;
import br.com.ged.excecao.NegocioException;
import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.model.GenericPersistence;

@Stateless
public class CategoriaDocumentoServiceImpl extends ConsultasDaoJpa<Categoria> implements CategoriaDocumentoService{
	
	@EJB
	private ConsultasDaoJpa<Documento> reposirotyDocumento;
	
	@EJB
	private GenericPersistence<Documento, Long> genericPersistenceDocumento;
	
	@EJB
	private GenericPersistence<Categoria, Long> genericPersistence;
	
	@Override
	public List<Categoria> pesquisar(FiltroCategoriaDocumentoDTO filtro, String... hbInitialize) {
		return filtrarPesquisa(filtro, Categoria.class, hbInitialize);
	}

	@Override
	public void excluir(Categoria categoriaSelecionada) throws NegocioException {
		
		try{
			
			for (Long idSubCategoria : extraiIdsSubCategoriasSelecionadas(categoriaSelecionada.getCategoriaFilha())){
				
				excluiCategoria(idSubCategoria);
			}
			
			excluiCategoria(categoriaSelecionada.getId());
			
		}catch(Exception e){
			e.printStackTrace();
			throw new NegocioException(Mensagem.ERRO_DEFAULT);
		}
	}

	private void excluiCategoria(Long idCategoria) {
		
		
		Query queryDocumento = em.createNativeQuery("DELETE FROM tb_documento WHERE id_categoria = :id");
		Query query = em.createNativeQuery("DELETE FROM rl_categoria_grupousuario WHERE id_categoria = :id");
		Query queryCategoria = em.createNativeQuery("DELETE FROM tb_categoria WHERE id_categoria = :id");
		
		queryDocumento.setParameter("id", idCategoria);
		query.setParameter("id", idCategoria);
		queryCategoria.setParameter("id", idCategoria);
		
		queryDocumento.executeUpdate();
		query.executeUpdate();
		queryCategoria.executeUpdate();
	}
	
	private List<Long> extraiIdsSubCategoriasSelecionadas(List<Categoria> categoriaFilha) {
		
		if (categoriaFilha == null){
			return null;
		}
		
		List<Long> ids = new ArrayList<>();
		
		for (Categoria cats : categoriaFilha){
			
			ids.add(cats.getId());
			
			if (cats.getCategoriaFilha() != null && !cats.getCategoriaFilha().isEmpty()){
				ids.addAll(extraiIdsSubCategoriasSelecionadas(cats.getCategoriaFilha()));
			}
		}
		
		return ids;
	}
}