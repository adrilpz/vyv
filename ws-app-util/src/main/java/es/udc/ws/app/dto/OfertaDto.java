package es.udc.ws.app.dto;

import java.util.Calendar;

public class OfertaDto {
	private String nombre;
	private String descripcion;
	private Calendar fechaLimiteReserva;
	private Calendar fechaLimiteOferta;
	private double precioReal;
	private double precioDescontado;
	private double comisionVenta;
	private Long codOferta;
	private boolean valida;
	
	public OfertaDto(){};
	
	
	public OfertaDto(Long codOferta, String nombre, String descripcion, Calendar fechaLimiteReserva,
			Calendar fechaLimiteOferta, double precioReal, double precioDescontado,
			double comisionVenta, boolean valida){
		this.codOferta=codOferta;
		this.nombre= nombre;
		this.descripcion= descripcion;
		this.fechaLimiteReserva = fechaLimiteReserva;
		this.fechaLimiteOferta = fechaLimiteOferta;
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.comisionVenta = comisionVenta;
		this.valida = valida;
	}
	


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Calendar getFechaLimiteReserva() {
		return fechaLimiteReserva;
	}
	public void setFechaLimiteReserva(Calendar fechaLimiteReserva) {
		this.fechaLimiteReserva = fechaLimiteReserva;
	}
	public Calendar getFechaLimiteOferta() {
		return fechaLimiteOferta;
	}
	public void setFechaLimiteOferta(Calendar fechaLimiteOferta) {
		this.fechaLimiteOferta = fechaLimiteOferta;
	}
	public double getPrecioReal() {
		return precioReal;
	}
	public void setPrecioReal(double precioReal) {
		this.precioReal = precioReal;
	}
	public double getPrecioDescontado() {
		return precioDescontado;
	}
	public void setPrecioDescontado(double precioDescontado) {
		this.precioDescontado = precioDescontado;
	}
	public double getComisionVenta() {
		return comisionVenta;
	}
	public void setComisionVenta(double comisionVenta) {
		this.comisionVenta = comisionVenta;
	}
	public Long getCodOferta() {
		return codOferta;
	}
	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
	}
	public boolean isValida() {
		return valida;
	}
	public void setValida(boolean valida) {
		this.valida = valida;
	}

	@Override
	public String toString() {
		return "OfertaDto [nombre=" + nombre + ", descripcion=" + descripcion
				+ ", fechaLimiteReserva=" + fechaLimiteReserva
				+ ", fechaLimiteOferta=" + fechaLimiteOferta + ", precioReal="
				+ precioReal + ", precioDescontado=" + precioDescontado
				+ ", comisionVenta=" + comisionVenta + ", codOferta="
				+ codOferta + ", valida=" + valida + "]";
	}
	
}
