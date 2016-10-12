package es.udc.ws.app.model.reserva;

import java.util.Calendar;

import es.udc.ws.app.dto.EstadoReserva;



public class Reserva {
	private Long codReserva;
	private String email;
	private String tarjetaCredito;
	private EstadoReserva estado;
	private Calendar fechaReserva;
	private Long codOferta;
	private double precioOferta;

	public Reserva(Long codOferta, String email, String tarjetaCredito, 
			Calendar fechaReserva, EstadoReserva estado, double precioOferta){
		this.codOferta=codOferta;
		this.email = email;
		this.tarjetaCredito = tarjetaCredito;
		this.fechaReserva = fechaReserva;
		if (fechaReserva != null) {
            this.fechaReserva.set(Calendar.MILLISECOND, 0);
        }
		this.estado = estado;
		this.precioOferta = precioOferta;
	}

	public Reserva(Long codReserva, Long codOferta, String email, String tarjetaCredito, 
			Calendar fechaReserva, EstadoReserva estado, double precioOferta){
        this(codOferta, email, tarjetaCredito, fechaReserva, estado, precioOferta);
        this.codReserva = codReserva;
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
		if (fechaReserva != null) {
            this.fechaReserva.set(Calendar.MILLISECOND, 0);
		}
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codOferta == null) ? 0 : codOferta.hashCode());
		result = prime * result
				+ ((codReserva == null) ? 0 : codReserva.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((fechaReserva == null) ? 0 : fechaReserva.hashCode());
		long temp;
		temp = Double.doubleToLongBits(precioOferta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((tarjetaCredito == null) ? 0 : tarjetaCredito.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (codOferta == null) {
			if (other.codOferta != null)
				return false;
		} else if (!codOferta.equals(other.codOferta))
			return false;
		if (codReserva == null) {
			if (other.codReserva != null)
				return false;
		} else if (!codReserva.equals(other.codReserva))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaReserva == null) {
			if (other.fechaReserva != null)
				return false;
		} else if (!fechaReserva.equals(other.fechaReserva))
			return false;
		if (Double.doubleToLongBits(precioOferta) != Double
				.doubleToLongBits(other.precioOferta))
			return false;
		if (tarjetaCredito == null) {
			if (other.tarjetaCredito != null)
				return false;
		} else if (!tarjetaCredito.equals(other.tarjetaCredito))
			return false;
		return true;
	}


	
}