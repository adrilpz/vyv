package es.udc.pa.p007.apuestasapp.model.apuesta;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;

@Entity
@org.hibernate.annotations.BatchSize(size = 10)
@Immutable
public class Apuesta {

	/**
	 * NOTE: this entity class does not contain a "version" property since its
	 * instances are never updated after being persisted.
	 */

	private OpcionApuesta opcion;
	private double cantidad;
	private Long codApuesta;
	private UserProfile usuario;
	private Calendar fechaApuesta;

	public Apuesta() {
	}

	public Apuesta(OpcionApuesta opcion, double cantidad, UserProfile usuario,
			Calendar fecha) {

		/**
		 * NOTE: "codApuesta" must be left as "null" since it's value
		 * automatically generated
		 */
		this.opcion = opcion;
		this.cantidad = cantidad;
		this.usuario = usuario;
		this.fechaApuesta = fecha;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "codOpcionApuesta")
	public OpcionApuesta getOpcion() {
		return opcion;
	}

	public void setOpcion(OpcionApuesta opcion) {
		this.opcion = opcion;
	}

	@Column(name = "cantidad")
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "codApuesta")
	@SequenceGenerator(                   // It only takes effect for
			name = "CodApuestaGenerator", // databases providing identifier
			sequenceName = "ApuestaSeq")  // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CodApuestaGenerator")
	public Long getCodApuesta() {
		return codApuesta;
	}

	public void setCodApuesta(Long codApuesta) {
		this.codApuesta = codApuesta;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "usrId")
	public UserProfile getUsuario() {
		return usuario;
	}

	public void setUsuario(UserProfile usuario) {
		this.usuario = usuario;
	}

	@Column(name = "fechaApuesta")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFechaApuesta() {
		return fechaApuesta;
	}

	public void setFechaApuesta(Calendar fechaApuesta) {
		this.fechaApuesta = fechaApuesta;
	}

	@Override
	public String toString() {
		return "Apuesta [opcion=" + opcion.getResultado() + ", cantidad="
				+ cantidad + ", codApuesta=" + codApuesta + ", usuario="
				+ usuario.getEmail() + ", fechaApuesta="
				+ fechaApuesta.getTime() + "]";
	}

}
