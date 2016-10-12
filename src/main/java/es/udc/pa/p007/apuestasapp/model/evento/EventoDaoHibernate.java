package es.udc.pa.p007.apuestasapp.model.evento;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("eventoDao")
public class EventoDaoHibernate extends GenericDaoHibernate<Evento, Long>
implements EventoDao {

	
	@SuppressWarnings("unchecked")
	public List<Evento> findEventosAbiertos(String nombre, Long codCategoria,
			boolean admin, int startIndex, int count) {
				
		String[] words = null; 
		String consulta = "SELECT e FROM Evento e ";
		int i = 0;
		
		if (!admin || nombre != null || codCategoria !=null)
			consulta = consulta + "WHERE ";
		
		if (!admin)
			consulta = consulta + "e.fecha > :actualDate ";
		
		if (nombre != null){
			words = nombre != null ? nombre.split(" ") : null;
		    
			if (words != null && words.length > 0) {
		            for (i = 0; i < words.length; i++) {
		            	if (!admin || i>0)
		            		consulta = consulta + "AND ";
		                consulta = consulta + "LOWER(e.nombre) LIKE LOWER(:token"+i+") ";
		            }
		        }
		}
		if (codCategoria != null){
			if (!admin || nombre != null)
				consulta = consulta + "AND ";
			consulta = consulta + "e.categoria.codCategoria = :codCategoria ";
		}
		consulta = consulta + "ORDER BY e.fecha";
		
		Query query = getSession().createQuery(consulta);
        
		if(!admin)
			query.setCalendar("actualDate", Calendar.getInstance());
		
        if (nombre != null)
        	for (int j = 0; j < i; j++){
        		query.setParameter("token" + j, "%"+words[j]+"%");
        	}
        
        if (codCategoria != null)
        	query.setParameter("codCategoria", codCategoria);
                
        return  query.setFirstResult(startIndex).
                setMaxResults(count).
                list();	
	}

}
