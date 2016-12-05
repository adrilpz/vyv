package es.udc.pa.p007.apuestasapp.test.model.tipoApuesta;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.categoria.CategoriaDao;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.evento.EventoDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDao;
import es.udc.pa.p007.apuestasapp.test.model.evento.EventoGenerator;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.generator.iterable.Iterables;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TipoApuestaDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private TipoApuestaDao tipoApuestaDao;
	
	//VARIABLES GLOBALES
	private Categoria futbol;
	private Evento evento;
	private Calendar fecha;
	private TipoApuesta tipoApuesta, tipoApuesta2;
	
	@Before
	public void initialize() {
		//Creación de fechas
		fecha = Calendar.getInstance();
		fecha.set(2017, 11, 24);
		
		//Creación de categorías
		futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);
		
		//Creación de eventos
		evento = new Evento("Barsa - Madrid", fecha, futbol);
		eventoDao.save(evento);
		
		//Creación tipos de apuesta
		tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);
		
		tipoApuesta2 = new TipoApuesta("Goles", false, evento);
		tipoApuestaDao.save(tipoApuesta2); 

	}
	
	//PR-UN-077
	@Test
	public void testSave() throws InstanceNotFoundException{
		//Setup
		TipoApuesta tipoApuestaPR077 = new TipoApuesta("Goleador", false, evento);
		
		//Llamada
		tipoApuestaDao.save(tipoApuestaPR077);
		
		//Aserción
		TipoApuesta foundTipoApuesta=tipoApuestaDao.find(tipoApuestaPR077.getCodTipoApuesta());
		assertEquals(tipoApuestaPR077, foundTipoApuesta);
	}
	
	//PR-UN-078
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		TipoApuesta foundTipoApuesta=tipoApuestaDao.find(tipoApuesta.getCodTipoApuesta());
		
		//Aserción
		assertEquals(tipoApuesta, foundTipoApuesta);
	}
	
	//PR-UN-079
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		tipoApuestaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-080
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		tipoApuestaDao.remove(tipoApuesta.getCodTipoApuesta());
			
		//Aserción
		try {
			tipoApuestaDao.find(tipoApuesta.getCodTipoApuesta());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-081
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		tipoApuestaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-082
	@Test
	public void testFindTiposApuesta(){
		//Llamada
		List<TipoApuesta> foundTiposApuesta=tipoApuestaDao.getTiposApuesta(evento.getCodEvento());
		
		//Aserción
		assertEquals(foundTiposApuesta.size(), 2);
	}
	
	//PR-UN-103
	@Test 
	public void testGenerator(){
		for(TipoApuesta ta : Iterables.toIterable(new TipoApuestaGenerator())){
			TipoApuesta newTipoApuesta = new TipoApuesta(ta.getPregunta(), ta.isMultiple(), ta.getEvento());
			assertEquals(ta.getPregunta(), newTipoApuesta.getPregunta());
			assertEquals(ta.isMultiple(), newTipoApuesta.isMultiple());
			assertEquals(ta.getEvento(), newTipoApuesta.getEvento());
		}
	}
	
	//PR-UN-104
	@Test
	public void testSaveRandomBetTypes() throws InstanceNotFoundException{
		//Setup
		for(TipoApuesta ta : Iterables.toIterable(new TipoApuestaGenerator())){
			categoriaDao.save(ta.getEvento().getCategoria());
			eventoDao.save(ta.getEvento());
			
			//Llamada
			tipoApuestaDao.save(ta);

			//Aserción
			TipoApuesta foundTipoApuesta= tipoApuestaDao.find(ta.getCodTipoApuesta());
			assertEquals(ta, foundTipoApuesta);
		}
	}
}
