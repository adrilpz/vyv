package es.udc.ws.app.model.oferta;

import java.util.Calendar;

public class Oferta {
	private String nombre;
	private String descripcion;
	private Calendar fechaLimiteReserva;
	private Calendar fechaLimiteOferta;
	private double precioReal;
	private double precioDescontado;
	private double comisionVenta;
	private Long codOferta;
	private boolean valida;
	
	public Oferta(String nombre, String descripcion, Calendar fechaLimiteReserva,
			Calendar fechaLimiteOferta, double precioReal, double precioDescontado,
			double comisionVenta, boolean valida){
		
		this.nombre= nombre;
		this.descripcion= descripcion;
		this.fechaLimiteReserva = fechaLimiteReserva;
		if (fechaLimiteReserva != null) {
            this.fechaLimiteReserva.set(Calendar.MILLISECOND, 0);
        }
		this.fechaLimiteOferta = fechaLimiteOferta;
		if (fechaLimiteOferta != null) {
            this.fechaLimiteOferta.set(Calendar.MILLISECOND, 0);
        }
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.comisionVenta = comisionVenta;
		this.valida = valida;
	}
	
	public Oferta(Long codOferta, String nombre, String descripcion, Calendar fechaLimiteReserva,
			Calendar fechaLimiteOferta, double precioReal, double precioDescontado,
			double comisionVenta, boolean valida){
		
		this.codOferta=codOferta;
		this.nombre= nombre;
		this.descripcion= descripcion;
		this.fechaLimiteReserva = fechaLimiteReserva;
		if (fechaLimiteReserva != null) {
            this.fechaLimiteReserva.set(Calendar.MILLISECOND, 0);
        }
		this.fechaLimiteOferta = fechaLimiteOferta;
		if (fechaLimiteOferta != null) {
            this.fechaLimiteOferta.set(Calendar.MILLISECOND, 0);
        }
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
		if (fechaLimiteReserva != null) {
            this.fechaLimiteReserva.set(Calendar.MILLISECOND, 0);
        }
	}
	public Calendar getFechaLimiteOferta() {
		return fechaLimiteOferta;
	}
	public void setFechaLimiteOferta(Calendar fechaLimiteOferta) {
		this.fechaLimiteOferta = fechaLimiteOferta;
		if (fechaLimiteOferta != null) {
            this.fechaLimiteOferta.set(Calendar.MILLISECOND, 0);
        }
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codOferta == null) ? 0 : codOferta.hashCode());
		long temp;
		temp = Double.doubleToLongBits(comisionVenta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((fechaLimiteOferta == null) ? 0 : fechaLimiteOferta
						.hashCode());
		result = prime
				* result
				+ ((fechaLimiteReserva == null) ? 0 : fechaLimiteReserva
						.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		temp = Double.doubleToLongBits(precioDescontado);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(precioReal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (valida ? 1231 : 1237);
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
		Oferta other = (Oferta) obj;
		if (codOferta == null) {
			if (other.codOferta != null)
				return false;
		} else if (!codOferta.equals(other.codOferta))
			return false;
		if (Double.doubleToLongBits(comisionVenta) != Double
				.doubleToLongBits(other.comisionVenta))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaLimiteOferta == null) {
			if (other.fechaLimiteOferta != null)
				return false;
		} else if (!fechaLimiteOferta.equals(other.fechaLimiteOferta))
			return false;
		if (fechaLimiteReserva == null) {
			if (other.fechaLimiteReserva != null)
				return false;
		} else if (!fechaLimiteReserva.equals(other.fechaLimiteReserva))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(precioDescontado) != Double
				.doubleToLongBits(other.precioDescontado))
			return false;
		if (Double.doubleToLongBits(precioReal) != Double
				.doubleToLongBits(other.precioReal))
			return false;
		if (valida != other.valida)
			return false;
		return true;
	}


	
	
}