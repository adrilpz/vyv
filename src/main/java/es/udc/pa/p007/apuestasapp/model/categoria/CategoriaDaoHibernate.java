 package es.udc.pa.p007.apuestasapp.model.categoria;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("categoriaDao")
public class CategoriaDaoHibernate extends GenericDaoHibernate<Categoria, Long>
implements CategoriaDao {

	@SuppressWarnings("unchecked")
	public List<Categoria> findCategorias() {

		return  getSession().createQuery(
	        	"SELECT a FROM Categoria a " +
	        	"ORDER BY a.nombre ASC").list();
	}
}
