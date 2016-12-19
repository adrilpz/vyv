package es.udc.pa.p007.apuestasapp.test.model.opcionApuesta;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
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
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.generator.iterable.Iterables;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class OpcionApuestaDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private TipoApuestaDao tipoApuestaDao;

	@Autowired
	private OpcionApuestaDao opcionApuestaDao;

	@Autowired
	private SessionFactory sessionFactory;
	
	//VARIABLES GLOBALES
	private Categoria futbol;
	private Evento evento;
	private Calendar fecha;
	private TipoApuesta tipoApuesta, tipoApuesta2;
	private OpcionApuesta opcion, opcion2, opcion3, opcion4;
	
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

		//Creación de opciones apuesta
		opcion = new OpcionApuesta("1", 1.40, null, tipoApuesta);
		opcionApuestaDao.save(opcion);
		
		opcion2 = new OpcionApuesta("2", 2.00, null, tipoApuesta);
		opcionApuestaDao.save(opcion2);
		
		opcion3 = new OpcionApuesta("3", 2.50, true, tipoApuesta2);
		opcionApuestaDao.save(opcion3);
		
		opcion4 = new OpcionApuesta("4", 2.20, null, tipoApuesta2);
		opcionApuestaDao.save(opcion4);
	}
	
	//PR-UN-068
	@Test
	public void testSave() throws InstanceNotFoundException{
		//Setup
		OpcionApuesta opcionPR068 = new OpcionApuesta("X", 1.70, null, tipoApuesta);
		
		//Llamada
		opcionApuestaDao.save(opcionPR068);
		
		//Aserción
		OpcionApuesta foundOpcion=opcionApuestaDao.find(opcionPR068.getCodOpcionApuesta());
		assertEquals(opcionPR068, foundOpcion);
	}
	
	//PR-UN-069
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		OpcionApuesta foundOpcion=opcionApuestaDao.find(opcion.getCodOpcionApuesta());
		
		//Aserción
		assertEquals(opcion, foundOpcion);
	}
	
	//PR-UN-070
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		opcionApuestaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-071
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		opcionApuestaDao.remove(opcion.getCodOpcionApuesta());
		
		//Aserción
		try {
			opcionApuestaDao.find(opcion.getCodOpcionApuesta());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-072
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		opcionApuestaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-073
	@Test
	public void testValidarOpciones() throws InstanceNotFoundException{
		//Setup
		List<Long> ganadoras=new ArrayList<Long>();
		ganadoras.add(opcion.getCodOpcionApuesta());
		
		//Llamada
		opcionApuestaDao.validarOpciones(ganadoras);
		
		//Aserción
		sessionFactory.getCurrentSession().clear();
		OpcionApuesta foundOpcion=opcionApuestaDao.find(opcion.getCodOpcionApuesta());
		assertEquals(foundOpcion.isGanadora(), true);
	}
	
	//PR-UN-074
	@Test
	public void testInValidarOpciones() throws InstanceNotFoundException{
		//Llamada
		opcionApuestaDao.invalidarOpcionesPendientes(tipoApuesta2.getCodTipoApuesta());
		
		//Aserción
		sessionFactory.getCurrentSession().clear();
		OpcionApuesta foundOpcion4=opcionApuestaDao.find(opcion4.getCodOpcionApuesta());
		assertEquals(foundOpcion4.isGanadora(), false);
	}
	
	//PR-UN-075
	@Test
	public void testGetOpcionesApuesta() throws InstanceNotFoundException{
		//Llamada
		List<OpcionApuesta> foundOpciones= opcionApuestaDao.getOpcionesApuesta(tipoApuesta.getCodTipoApuesta());
		
		//Aserción
		assertEquals(foundOpciones.size(), 2);
	}
	
	//PR-UN-076
	@Test
	public void testIsOpcionesValidated() throws InstanceNotFoundException{
		//Llamada
		boolean validated= opcionApuestaDao.isOpcionesApuestaValidated(tipoApuesta.getCodTipoApuesta());
		
		//Aserción
		assertFalse(validated);
	}

	//PR-UN-101
	@Test 
	public void testGenerator(){
		for(OpcionApuesta o : Iterables.toIterable(new OpcionApuestaGenerator())){
			OpcionApuesta newOpcionApuesta = new OpcionApuesta(o.getResultado(), o.getCuota(), o.isGanadora(), o.getTipoApuesta());
			assertEquals(o.getResultado(), newOpcionApuesta.getResultado());
			assertEquals(o.getCuota(), newOpcionApuesta.getCuota(), 0);
			assertEquals(o.isGanadora(), newOpcionApuesta.isGanadora());
			assertEquals(o.getTipoApuesta(), newOpcionApuesta.getTipoApuesta());
		}
	}
	
	//PR-UN-102
	@Test
	public void testSaveRandomBetOptions() throws InstanceNotFoundException{
		//Setup
		for(OpcionApuesta o : Iterables.toIterable(new OpcionApuestaGenerator())){
			categoriaDao.save(o.getTipoApuesta().getEvento().getCategoria());
			eventoDao.save(o.getTipoApuesta().getEvento());
			tipoApuestaDao.save(o.getTipoApuesta());
			
			//Llamada
			opcionApuestaDao.save(o);

			//Aserción
			OpcionApuesta foundOpcionApuesta= opcionApuestaDao.find(o.getCodOpcionApuesta());
			assertEquals(o, foundOpcionApuesta);
		}
	}
	
}
