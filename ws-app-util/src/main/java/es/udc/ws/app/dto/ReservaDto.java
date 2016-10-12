package es.udc.ws.app.dto;

import java.util.Calendar;

public class ReservaDto {
	private Long codReserva;
	private String email;
	private String tarjetaCredito;
	private EstadoReserva estado;
	private Calendar fechaReserva;
	private Long codOferta;
	private double precioOferta;
	
	public ReservaDto(){};
	
	public ReservaDto(Long codReserva, Long codOferta, String email, String tarjetaCredito, 
			Calendar fechaReserva, EstadoReserva estado, double precioOferta){
		this.codReserva = codReserva;
		this.codOferta=codOferta;
		this.email = email;
		this.tarjetaCredito = tarjetaCredito;
		this.fechaReserva = fechaReserva;
		this.estado = estado;
		this.precioOferta = precioOferta;
	}
	
	public Long getCodReserva() {
		return codReserva;
	}
	public void setCodReserva(Long codReserva) {
		this.codReserva = codReserva;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTarjetaCredito() {
		return tarjetaCredito;
	}
	public void setTarjetaCredito(String tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}
	public EstadoReserva getEstado() {
		return estado;
	}
	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
	public Calendar getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Calendar fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public Long getCodOferta() {
		return codOferta;
	}
	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
	}
	public double getPrecioOferta() {
		return precioOferta;
	}
	public void setPrecioOferta(double precioOferta) {
		this.precioOferta = precioOferta;
	}
	@Override
	public String toString() {
		return "ReservaDto [codReserva=" + codReserva + ", email=" + email
				+ ", tarjetaCredito=" + tarjetaCredito + ", estado=" + estado
				+ ", fechaReserva=" + fechaReserva + ", codOferta=" + codOferta
				+ ", precioOferta=" + precioOferta + "]";
	}
	
}
