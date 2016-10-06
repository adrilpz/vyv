package es.udc.pa.p007.apuestasapp.model.opcionApuesta;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface OpcionApuestaDao extends GenericDao<OpcionApuesta, Long>{
	
	/**
	 * 
	 * 
	 * @param codTipoApuesta
	 * @param startIndex
	 * @param count
	 * @return
	 */
	//public List<OpcionApuesta> findOpcionesPendientes(Long codTipoApuesta);

	void validarOpciones(List<Long> opciones);

	void invalidarOpcionesPendientes(Long codTipoApuesta);
	
	public List<OpcionApuesta> getOpcionesApuesta(Long codTipoApuesta);
	
	public boolean isOpcionesApuestaValidated(Long codTipoApuesta);
}
