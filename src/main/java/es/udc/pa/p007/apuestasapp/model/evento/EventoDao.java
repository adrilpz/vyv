package es.udc.pa.p007.apuestasapp.model.evento;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface EventoDao extends GenericDao<Evento, Long> {
	
	
	/**
	 * Returns a list of events (it depends if the user is administrator.)
	 * @param nombre
	 * @param codCategoria
	 * @param admin
	 * @param startIndex
	 * @param count
	 * @return list of events
	 */
    public List<Evento> findEventosAbiertos(String nombre, Long codCategoria,
    		boolean admin, int startIndex, int count);

}
