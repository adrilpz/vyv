package es.udc.pa.p007.apuestasapp.test.model.evento;

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
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.generator.iterable.Iterables;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class EventoDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;
	
	//VARIABLES GLOBALES
	private Categoria futbol;
	private Evento evento, evento2;
	private Calendar fecha;
	
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
		
		evento2= new Evento("Depor-Celta",fecha,futbol);
		eventoDao.save(evento2);
		
	}
	
	//PR-UN-062
	@Test
	public void testSave() throws InstanceNotFoundException{
		//Setup
		Evento eventoPR062 = new Evento("Barsa- Atletico", fecha, futbol);
		
		//Llamada
		eventoDao.save(eventoPR062);
		
		//Aserción
		Evento foundEvento=eventoDao.find(eventoPR062.getCodEvento());
		assertEquals(eventoPR062, foundEvento);
	}
	
	//PR-UN-063
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		Evento foundEvento=eventoDao.find(evento.getCodEvento());
		
		//Aserción
		assertEquals(evento, foundEvento);
	}
	
	//PR-UN-064
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		eventoDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-065
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		eventoDao.remove(evento.getCodEvento());
			
		//Aserción
		try {
			eventoDao.find(evento.getCodEvento());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-066
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		eventoDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-067
	@Test
	public void testFindEventosAbiertos(){
		//Llamada
		List<Evento> foundEventos=eventoDao.findEventosAbiertos("barsa", futbol.getCodCategoria(), false, 0, 5);
		
		//Aserción
		assertEquals(foundEventos.size(), 1);
	}
	
	//PR-UN-099
	@Test 
	public void testGenerator(){
		for(Evento e : Iterables.toIterable(new EventoGenerator())){
			Evento newEvento = new Evento(e.getNombre(), e.getFecha(), e.getCategoria());
			assertEquals(e.getNombre(), newEvento.getNombre());
			assertEquals(e.getFecha(), newEvento.getFecha());
			assertEquals(e.getCategoria(), newEvento.getCategoria());
		}
	}
	
	//PR-UN-100
	@Test
	public void testSaveRandomEvents() throws InstanceNotFoundException{
		//Setup
		for(Evento e : Iterables.toIterable(new EventoGenerator())){
			categoriaDao.save(e.getCategoria());
			
			//Llamada
			eventoDao.save(e);

			//Aserción
			Evento foundEvento= eventoDao.find(e.getCodEvento());
			assertEquals(e, foundEvento);
		}
	}
}
