package es.udc.pa.p007.apuestasapp.test.model.tipoApuesta;

import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.test.model.evento.EventoGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class TipoApuestaGenerator implements net.java.quickcheck.Generator<TipoApuesta>{
	
	Generator<String> pregunta = PrimitiveGenerators.strings(30);
	Generator<Boolean> multiple = PrimitiveGenerators.booleans();
	Generator<Evento> evento = new EventoGenerator();

	@Override 
	public TipoApuesta next() {
		return new TipoApuesta(pregunta.next(), multiple.next(), evento.next());
	}
}
