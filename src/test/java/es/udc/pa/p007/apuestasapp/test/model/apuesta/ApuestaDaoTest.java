package es.udc.pa.p007.apuestasapp.test.model.apuesta;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

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

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuesta.ApuestaDao;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
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
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
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

	@Autowired
	private SessionFactory sessionFactory;
	
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
		fecha.set(2017, 12, 24);
		
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
		Apuesta apuestaPR050 = new Apuesta(opcion, 10, userProfile, fecha);
		apuestaDao.save(apuestaPR050);
		Apuesta foundApuesta=apuestaDao.find(apuestaPR050.getCodApuesta());
		assertEquals(apuestaPR050, foundApuesta);
	}
	
	//PR-UN-051
	@Test
	public void testFind() throws InstanceNotFoundException{
		Apuesta foundApuesta=apuestaDao.find(apuesta.getCodApuesta());
		assertEquals(apuesta, foundApuesta);
	}
	
	//PR-UN-052
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		apuestaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-053
	/*@Test
	public void testRemove() throws InstanceNotFoundException{
		apuestaDao.remove(apuesta2.getCodApuesta());
		sessionFactory.getCurrentSession().clear();
		Apuesta foundApuesta=apuestaDao.find(apuesta2.getCodApuesta());
		assertNull(foundApuesta);
	}*/
	
	//PR-UN-054
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		apuestaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-055
	@Test
	public void testFindByUserId(){
		List<Apuesta> foundApuestas=apuestaDao.findByUserId(apuesta.getUsuario().getUserProfileId(), 0, 5);
		assertEquals(foundApuestas.size(), 2);
	}
}
