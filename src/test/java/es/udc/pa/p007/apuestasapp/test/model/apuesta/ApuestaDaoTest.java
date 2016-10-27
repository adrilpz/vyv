package es.udc.pa.p007.apuestasapp.test.model.apuesta;

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

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuesta.ApuestaDao;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.categoria.CategoriaDao;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.evento.EventoDao;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDao;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfileDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ApuestaDaoTest {
	
	private final long NON_EXISTENT_COD = -1;
	
	@Autowired
	private ApuestaDao apuestaDao;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private TipoApuestaDao tipoApuestaDao;

	@Autowired
	private OpcionApuestaDao opcionApuestaDao;

	@Autowired
	private UserProfileDao userProfileDao;

	//VARIABLES GLOBALES
	private Categoria futbol;
	private Evento evento;
	private Calendar fecha;
	private UserProfile userProfile;
	private TipoApuesta tipoApuesta;
	private OpcionApuesta opcion;
	private Apuesta apuesta, apuesta2;
	
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
		
		//Creación usuarios
		userProfile = new UserProfile("user", "userPassword", "name", "lastName", "user@udc.es");
		userProfileDao.save(userProfile);
		
		//Creación tipos de apuesta
		tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);

		//Creación de opciones apuesta
		opcion = new OpcionApuesta("1", 1.40, true, tipoApuesta);
		opcionApuestaDao.save(opcion);
		
		//Creación de apuesta
		apuesta = new Apuesta(opcion, 10, userProfile, fecha);
		apuestaDao.save(apuesta);
		
		apuesta2 = new Apuesta(opcion, 10, userProfile, fecha);
		apuestaDao.save(apuesta2);
	}
	
	//PR-UN-050
	@Test
	public void testSave() throws InstanceNotFoundException{
		//Setup
		Apuesta apuestaPR050 = new Apuesta(opcion, 10, userProfile, fecha);
		
		//Llamada
		apuestaDao.save(apuestaPR050);
		
		//Aserción
		Apuesta foundApuesta=apuestaDao.find(apuestaPR050.getCodApuesta());
		assertEquals(apuestaPR050, foundApuesta);
	}
	
	//PR-UN-051
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		Apuesta foundApuesta=apuestaDao.find(apuesta.getCodApuesta());
		
		//Aserción
		assertEquals(apuesta, foundApuesta);
	}
	
	//PR-UN-052
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		apuestaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-053
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		apuestaDao.remove(apuesta.getCodApuesta());
			
		//Aserción
		try {
			apuestaDao.find(apuesta.getCodApuesta());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-054
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		apuestaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-055
	@Test
	public void testFindByUserId(){
		//Llamada
		List<Apuesta> foundApuestas=apuestaDao.findByUserId(apuesta.getUsuario().getUserProfileId(), 0, 5);
		
		//Aserción
		assertEquals(foundApuestas.size(), 2);
	}
}
