package es.udc.pa.p007.apuestasapp.test.model.categoria;

import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class CategoriaGenerator implements net.java.quickcheck.Generator<Categoria>{

	Generator<String> name = PrimitiveGenerators.strings(30);

	@Override 
	public Categoria next() {
		return new Categoria(name.next());
	}
}