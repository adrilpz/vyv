package es.udc.pa.p007.apuestasapp.test.model.opcionApuesta;

import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.test.model.tipoApuesta.TipoApuestaGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class OpcionApuestaGenerator implements net.java.quickcheck.Generator<OpcionApuesta>{
	
	Generator<String> resultado = PrimitiveGenerators.strings(30);
	Generator<Double> cuota = PrimitiveGenerators.doubles(1.1, 1000);
	Generator<Boolean> ganadora = PrimitiveGenerators.booleans();
	Generator<TipoApuesta> tipoApuesta = new TipoApuestaGenerator();

	@Override 
	public OpcionApuesta next() {
		return new OpcionApuesta(resultado.next(), cuota.next(), ganadora.next(), tipoApuesta.next());
	}
}