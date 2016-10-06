package es.udc.pa.p007.apuestasapp.model.apuesta;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("ApuestaDao")
public class ApuestaDaoHibernate extends GenericDaoHibernate<Apuesta, Long>
implements ApuestaDao{

	@SuppressWarnings("unchecked")
	public List<Apuesta> findByUserId(Long userId, int startIndex,
			int count) { 

		return getSession().createQuery(
			"SELECT a FROM Apuesta a WHERE " +
			"a.usuario.userProfileId = :userId " +
			"ORDER BY a.fechaApuesta DESC").
			setParameter("userId", userId).
			setFirstResult(startIndex).
			setMaxResults(count).
			list();

	}

}