package es.udc.pa.p007.apuestasapp.model.apuestasservice;

import java.util.List;

import es.udc.pa.p007.apuestasapp.model.evento.Evento;

public class EventoBlock {
	
	private List<Evento> eventos;
	private boolean existsMoreEventos;
	
	public EventoBlock(List<Evento> eventos, boolean existsMoreEventos) {
		
		this.eventos = eventos;
		this.existsMoreEventos = existsMoreEventos;

	}
	
	public List<Evento> getEventos() {
		return eventos;
	}
	
	public boolean getExistsMoreEventos() {
		return existsMoreEventos;
	}
 }