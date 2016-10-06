package es.udc.pa.p007.apuestasapp.model.opcionApuesta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;

@Entity
public class OpcionApuesta {

	private String resultado;
	private double cuota;
	private Long codOpcionApuesta;
	private Boolean ganadora;
	/* Tiene 3 valores: si(true), no(false), pendiente(null) */
	private TipoApuesta tipoApuesta;

	public OpcionApuesta() {
	}

	public OpcionApuesta(String resultado, double cuota, Boolean ganadora,
			TipoApuesta tipoApuesta) {
		this.resultado = resultado;
		this.cuota = cuota;
		this.ganadora = ganadora;
		this.tipoApuesta = tipoApuesta;
	}

	public OpcionApuesta(String resultado, double cuota, Boolean ganadora) {
		this.resultado = resultado;
		this.cuota = cuota;
		this.ganadora = ganadora;
	}

	@Column(name = "resultado")
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Column(name = "cuota")
	public double getCuota() {
		return cuota;
	}

	public void setCuota(double cuota) {
		this.cuota = cuota;
	}

	@Column(name = "codOpcionApuesta")
	@SequenceGenerator( // It only takes effect for
	name = "CodOpcionApuestaGenerator", // databases providing identifier
	sequenceName = "OpcionApuestaSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CodOpcionApuestaGenerator")
	public Long getCodOpcionApuesta() {
		return codOpcionApuesta;
	}

	public void setCodOpcionApuesta(Long codOpcionApuesta) {
		this.codOpcionApuesta = codOpcionApuesta;
	}

	@Column(name = "ganadora")
	public Boolean isGanadora() {
		return ganadora;
	}

	public void setGanadora(Boolean ganadora) {
		this.ganadora = ganadora;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "codTipoApuesta")
	public TipoApuesta getTipoApuesta() {
		return tipoApuesta;
	}

	public void setTipoApuesta(TipoApuesta tipoApuesta) {
		this.tipoApuesta = tipoApuesta;
	}
}
