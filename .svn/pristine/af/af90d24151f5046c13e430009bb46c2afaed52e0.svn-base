package es.udc.pa.p007.apuestasapp.model.tipoApuesta;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("tipoApuestaDao")
public class TipoApuestaDaoHibernate extends GenericDaoHibernate<TipoApuesta, Long>
implements TipoApuestaDao{
	
	@SuppressWarnings("unchecked")
	public List<TipoApuesta> getTiposApuesta(Long codEvento)
			 {
		return  getSession().createQuery(
				"SELECT a FROM TipoApuesta a WHERE a.evento.codEvento = :codEvento").
				setParameter("codEvento", codEvento).
				list();
	}

}
