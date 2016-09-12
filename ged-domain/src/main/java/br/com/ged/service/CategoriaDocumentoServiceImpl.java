package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.ged.domain.Mensagem;
import br.com.ged.dto.FiltroCategoriaDocumentoDTO;
import br.com.ged.dto.FiltroDocumentoDTO;
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
			
			FiltroDocumentoDTO filtroDocumentoDTO = new FiltroDocumentoDTO();
			
			filtroDocumentoDTO.setIdCategoria(categoriaSelecionada.getId());
			
			List<Documento> documentosCategoria = reposirotyDocumento.filtrarPesquisa(filtroDocumentoDTO, Documento.class);
			
			for (Documento doc : documentosCategoria){
				
				genericPersistenceDocumento.excluir(doc);
			}
			
			Query query = em.createNativeQuery("DELETE FROM rl_categoria_grupousuario WHERE id_categoria = :id");
			Query queryCategoria = em.createNativeQuery("DELETE FROM tb_categoria WHERE id_categoria = :id");
			
			query.setParameter("id", categoriaSelecionada.getId());
			queryCategoria.setParameter("id", categoriaSelecionada.getId());
			query.executeUpdate();
			queryCategoria.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new NegocioException(Mensagem.ERRO_DEFAULT);
		}
	}
}