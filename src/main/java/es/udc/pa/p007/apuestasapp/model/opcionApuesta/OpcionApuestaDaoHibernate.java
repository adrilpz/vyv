package es.udc.pa.p007.apuestasapp.model.opcionApuesta;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("opcionApuestaDao")
public class OpcionApuestaDaoHibernate extends GenericDaoHibernate<OpcionApuesta, Long>
implements OpcionApuestaDao{
	
	@Override
	public void validarOpciones(List<Long> opciones){
		String sentencia = "UPDATE OpcionApuesta o set o.ganadora = :ganadora where ";
		int i = 0;
		
		while (i<opciones.size()){
			if (i != opciones.size()-1)
				sentencia = sentencia + " o.codOpcionApuesta = :codOpcionApuesta"+i+" OR";
			else
				sentencia = sentencia + " o.codOpcionApuesta = :codOpcionApuesta"+i;
			i++;	
		}
		
		Query query =getSession().createQuery(sentencia);
		query.setBoolean("ganadora", true);
		
		i=0;
		while (i<opciones.size()){
			query.setParameter("codOpcionApuesta"+i, opciones.get(i));
			i++;
		}
		query.executeUpdate();
		
	}
	
	@Override
	public void invalidarOpcionesPendientes(Long codTipoApuesta){
		String sentencia = "UPDATE OpcionApuesta o set o.ganadora = :ganadora WHERE "
				+ "o.tipoApuesta.codTipoApuesta = :codTipoApuesta AND o.ganadora IS NULL";
		
		Query query =getSession().createQuery(sentencia);
		query.setParameter("codTipoApuesta", codTipoApuesta);
		query.setBoolean("ganadora", false);
		
		query.executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OpcionApuesta> getOpcionesApuesta(Long codTipoApuesta){
		return  getSession().createQuery(
				"SELECT a FROM OpcionApuesta a WHERE a.tipoApuesta.codTipoApuesta = :codTipoApuesta").
				setParameter("codTipoApuesta", codTipoApuesta).
				list();
	}
	
	public boolean isOpcionesApuestaValidated(Long codTipoApuesta) {
		return  getSession().createQuery(
				"SELECT a FROM OpcionApuesta a WHERE a.tipoApuesta.codTipoApuesta = :codTipoApuesta AND a.ganadora IS NULL").
				setParameter("codTipoApuesta", codTipoApuesta).list().isEmpty();
	}
	
}