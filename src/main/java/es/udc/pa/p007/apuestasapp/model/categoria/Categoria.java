package es.udc.pa.p007.apuestasapp.model.categoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Categoria {
	
	private String nombre;
	private Long codCategoria;
	
	public Categoria() {}
	
	public Categoria(String nombre){
		this.nombre=nombre;
	}

	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="codCategoria")
	@SequenceGenerator(              // It only takes effect for
	     name="CodCategoriaGenerator",  // databases providing identifier
	     sequenceName="CategoriaSeq")   // generators.
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CodCategoriaGenerator")
	public Long getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}
	
}