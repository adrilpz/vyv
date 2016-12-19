package es.udc.pa.p007.apuestasapp.test.model.apuesta;

import java.util.Calendar;
import java.util.Date;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.test.model.opcionApuesta.OpcionApuestaGenerator;
import es.udc.pa.p007.apuestasapp.test.model.userprofile.UserProfileGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class ApuestaGenerator implements net.java.quickcheck.Generator<Apuesta>{
	
	Generator<Double> cantidad = PrimitiveGenerators.doubles(1.1, 500.0);
	Generator<OpcionApuesta> opcion = new OpcionApuestaGenerator();
	Generator<UserProfile> usuario = new UserProfileGenerator();
	Date low = new Date();
	@SuppressWarnings("deprecation")
	Date high = new Date(low.getYear()+2, low.getMonth(), low.getDate());
	Generator<Date> fechaApuesta = PrimitiveGenerators.dates(low, high);
	
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}

	@Override 
	public Apuesta next() {
		return new Apuesta(opcion.next(), cantidad.next(), usuario.next(), toCalendar(fechaApuesta.next()));
	}
}