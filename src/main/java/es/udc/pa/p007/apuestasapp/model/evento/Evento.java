package es.udc.pa.p007.apuestasapp.model.evento;

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

import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;

@Entity
@org.hibernate.annotations.BatchSize(size = 10)
public class Evento {

		private String nombre;
		private Calendar fecha;
		private Categoria categoria;
		private Long codEvento;
		
		public Evento() {}
		
		public Evento(String nombre, Calendar fecha, Categoria categoria) {
			this.nombre = nombre;
			this.fecha = fecha;
			this.categoria = categoria;
		}
		
		@Column(name="nombre")
		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		@Column(name="fecha")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getFecha() {
			return fecha;
		}

		public void setFecha(Calendar fecha) {
			this.fecha = fecha;
		}

	    @ManyToOne(optional=false, fetch=FetchType.LAZY)
	    @JoinColumn(name="codCategoria")
		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		@Column(name="codEvento")
		@SequenceGenerator(              // It only takes effect for
		     name="CodEventoGenerator",  // databases providing identifier
		     sequenceName="EventoSeq")   // generators.
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO, generator="CodEventoGenerator")
		public Long getCodEvento() {
			return codEvento;
		}

		public void setCodEvento(Long codEvento) {
			this.codEvento = codEvento;
		}		
		
}
