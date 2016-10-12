package es.udc.pa.p007.apuestasapp.model.tipoApuesta;

import java.util.List;

public class TipoApuestaDto {

	private List<TipoApuesta> tiposApuesta;
	private Long codEvento;
	
	public TipoApuestaDto () {}
	
	public TipoApuestaDto (List<TipoApuesta> tiposApuesta, Long codEvento) {
		this.tiposApuesta = tiposApuesta;
		this.codEvento = codEvento;
	}
	
	public List<TipoApuesta> getTiposApuesta() {
		return tiposApuesta;
	}
	
	public Long getCodEvento() {
		return codEvento;
	}

}
