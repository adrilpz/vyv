package es.udc.pa.p007.apuestasapp.test.model.evento;

import java.util.Calendar;
import java.util.Date;

import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.test.model.categoria.CategoriaGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

@SuppressWarnings("deprecation")
public class EventoGenerator implements net.java.quickcheck.Generator<Evento>{
	
	Generator<String> nombre = PrimitiveGenerators.strings(30);
	Date low = new Date();
	Date high = new Date(low.getYear()+20, low.getMonth(), low.getDate());
	Generator<Date> fecha = PrimitiveGenerators.dates(low, high);
	Generator<Categoria> categoria = new CategoriaGenerator();
	
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}

	@Override 
	public Evento next() {
		return new Evento(nombre.next(), toCalendar(fecha.next()), categoria.next());
	}
}