package es.udc.pa.p007.apuestasapp.model.apuestasservice;

import java.util.List;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;


public class ApuestaBlock {
	
	private List<Apuesta> apuestas;
	private boolean existsMoreApuestas;
	
	public ApuestaBlock(List<Apuesta> apuestas, boolean existsMoreApuestas) {
		
		this.apuestas = apuestas;
		this.existsMoreApuestas = existsMoreApuestas;

	}
	
	public List<Apuesta> getApuestas() {
		return apuestas;
	}
	
	public boolean getExistsMoreApuestas() {
		return existsMoreApuestas;
	}
 }
