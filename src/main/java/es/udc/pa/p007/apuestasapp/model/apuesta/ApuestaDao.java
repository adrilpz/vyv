package es.udc.pa.p007.apuestasapp.model.apuesta;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface ApuestaDao extends GenericDao<Apuesta, Long>{

	/**
	 * Returns a list of bets pertaining  to a given user. If the user has
	 * no bets, return a empty list
	 * 
	 * @param userId the user identifier
	 * @param startIndex the index (starting from 0) of the first bet to return
	 * @param count the maximum number of accounts to return
	 * @return list of bets
	 */
	public List<Apuesta> findByUserId(Long userId, int startIndex, int count);
}
