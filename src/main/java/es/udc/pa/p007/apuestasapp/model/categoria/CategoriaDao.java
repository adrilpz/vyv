package es.udc.pa.p007.apuestasapp.model.categoria;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface CategoriaDao extends GenericDao<Categoria, Long> {    
    
	/**
	 *  Return a list of the names of the Categoria
	 * @return a list of Categoria names
	 */
	public List<Categoria> findCategorias();
	
}
