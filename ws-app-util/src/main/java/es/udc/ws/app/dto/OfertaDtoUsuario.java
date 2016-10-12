package es.udc.ws.app.dto;

import java.util.Calendar;

public class OfertaDtoUsuario {
	private String descripcion;
	private Calendar fechaReserva;
	private double precioDescontado;
	
	public OfertaDtoUsuario(){};
	
	public OfertaDtoUsuario(String descripcion, Calendar fechaReserva,
		double precioDescontado){
		this.descripcion= descripcion;
		this.fechaReserva = fechaReserva;
		this.precioDescontado = precioDescontado;
	}
	
	@Override
	public String toString() {
		return "OfertaDtoUsuario [descripcion=" + descripcion
				+ ", fechaReserva=" + fechaReserva + ", precioDescontado="
				+ precioDescontado + "]";
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Calendar getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Calendar fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public double getPrecioDescontado() {
		return precioDescontado;
	}
	public void setPrecioDescontado(double precioDescontado) {
		this.precioDescontado = precioDescontado;
	}
}
