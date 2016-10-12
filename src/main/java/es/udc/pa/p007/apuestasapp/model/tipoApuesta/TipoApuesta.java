package es.udc.pa.p007.apuestasapp.model.tipoApuesta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;

@Entity
public class TipoApuesta {

	private String pregunta;
	private boolean multiple;
	private Long codTipoApuesta;
	private Evento evento;
	private List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();

	public TipoApuesta() {
	}

	public TipoApuesta(String pregunta, boolean multiple, Evento evento) {
		this.pregunta = pregunta;
		this.multiple = multiple;
		this.evento = evento;
	}

	public TipoApuesta(String pregunta, boolean multiple) {
		this.pregunta = pregunta;
		this.multiple = multiple;
	}

	@Column(name = "pregunta")
	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	@Column(name = "multiple")
	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	@Column(name = "codTipoApuesta")
	@SequenceGenerator( // It only takes effect for
	name = "CodTipoApuestaGenerator", // databases providing identifier
	sequenceName = "TipoApuestaSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CodTipoApuestaGenerator")
	public Long getCodTipoApuesta() {
		return codTipoApuesta;
	}

	public void setCodTipoApuesta(Long codTipoApuesta) {
		this.codTipoApuesta = codTipoApuesta;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "codEvento")
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@OneToMany(mappedBy = "tipoApuesta")
	public List<OpcionApuesta> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionApuesta> opciones) {
		this.opciones = opciones;
	}

	public void addOpciones(OpcionApuesta opcion) {
		opciones.add(opcion);
		opcion.setTipoApuesta(this);
	}

}
