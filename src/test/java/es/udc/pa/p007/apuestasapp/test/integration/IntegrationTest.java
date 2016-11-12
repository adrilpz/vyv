package es.udc.pa.p007.apuestasapp.test.integration;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
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

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestaBlock;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.EventoBlock;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.NotStartedEventException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.StartedEventException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ValidateOptionsException;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.categoria.CategoriaDao;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.evento.EventoDao;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userservice.UserProfileDetails;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class IntegrationTest {

	@Autowired
	private ApuestasService apuestasService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private SessionFactory sessionFactory;
	
	//VARIABLES GLOBALES
	private Categoria futbol, tenis;
	private Calendar fecha, fecha2, now;
	private Evento eventoPasado;
	
	@Before
	public void initialize() throws DuplicateInstanceException, InstanceNotFoundException, InputValidationException{
		//Creación de fechas
		fecha = Calendar.getInstance();
		fecha.set(2017, 11, 24);
		
		fecha2 = Calendar.getInstance();
		fecha2.set(2015, 11, 24);
		
		now = Calendar.getInstance();
		
		//Creación de categorías
		futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);
		
		tenis = new Categoria("Tenis");
		categoriaDao.save(tenis);
		
		//Creación de eventos
		eventoPasado = new Evento("Barsa - Madrid", fecha2, futbol);
		eventoDao.save(eventoPasado);
	}
	
	//PR-UN-090
	@Test
	public void testFindEventosWithoutAdmin()
			throws  DuplicateInstanceException, InstanceNotFoundException, InputValidationException {
		//Inicialización
		Evento evento1 = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());
		Evento evento2 = apuestasService.createEvento("Depor - Atletico Madrid", fecha,
				futbol.getCodCategoria());
		List<Evento> expectedEventos = new ArrayList<>();
		expectedEventos.add(evento1);
		expectedEventos.add(evento2);
		
		//Llamada
		EventoBlock eventoBlock = apuestasService.findEventos("madrid", futbol.getCodCategoria(), false, 0, 5);		

		//Aserción
		List<Evento> foundEventos = eventoBlock.getEventos();
		assertEquals(expectedEventos.size(), foundEventos.size());
	}
	
	//PR-UN-091
	@Test
	public void testFindEventosWithAdmin()
			throws  DuplicateInstanceException, InstanceNotFoundException, InputValidationException {
		//Inicialización
		Evento evento1 = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());
		Evento evento2 = apuestasService.createEvento("Depor - Atletico Madrid", fecha,
				futbol.getCodCategoria());
		List<Evento> expectedEventos = new ArrayList<>();
		expectedEventos.add(evento1);
		expectedEventos.add(evento2);
		expectedEventos.add(eventoPasado);

		//Llamada
		EventoBlock eventoBlock = apuestasService.findEventos("madrid", futbol.getCodCategoria(), true, 0, 5);

		//Aserción
		List<Evento> foundEventos = eventoBlock.getEventos();
		assertEquals(expectedEventos.size(), foundEventos.size());
	}
	
	//PR-UN-092
	@Test
	public void testBet()
			throws  DuplicateInstanceException, InstanceNotFoundException, InputValidationException, StartedEventException {
		//Inicialización
        UserProfileDetails userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");
    	UserProfile user = userService.registerUser("user", "password", userProfileDetails);
		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha, futbol.getCodCategoria());
		OpcionApuesta opcion1= new OpcionApuesta("1", 2.50, null);
		OpcionApuesta opcion2= new OpcionApuesta("X", 3, null);
		OpcionApuesta opcion3= new OpcionApuesta("2", 1.40, null);
		List<OpcionApuesta> opciones = new ArrayList<>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		TipoApuesta tipoApuesta= new TipoApuesta("Resultado", false);
		tipoApuesta = apuestasService.createTipoApuesta(tipoApuesta, opciones, evento.getCodEvento()); 
		
		//Llamada
		Apuesta apuesta = apuestasService.createApuesta(opcion1.getCodOpcionApuesta(), 2, user.getUserProfileId());

		//Aserción
		Apuesta foundApuesta = apuestasService.findApuesta(apuesta.getCodApuesta());
		assertEquals(apuesta, foundApuesta);
	}
	
	//PR-UN-093
	@Test
	public void testSelectWinnerBetOptions()
			throws  DuplicateInstanceException, InstanceNotFoundException, InputValidationException, StartedEventException, NotStartedEventException, ValidateOptionsException, InterruptedException {
		//Inicialización
		now.add(Calendar.SECOND, 2);
		Evento evento = apuestasService.createEvento("Barsa - Madrid", now, futbol.getCodCategoria());
		OpcionApuesta opcion1= new OpcionApuesta("1", 2.50, null);
		OpcionApuesta opcion2= new OpcionApuesta("X", 3, null);
		OpcionApuesta opcion3= new OpcionApuesta("2", 1.40, null);
		List<OpcionApuesta> opciones = new ArrayList<>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		TipoApuesta tipoApuesta= new TipoApuesta("Resultado", false);
		tipoApuesta = apuestasService.createTipoApuesta(tipoApuesta, opciones, evento.getCodEvento()); 
		List<Long> winnerOptions = new ArrayList<>();
		winnerOptions.add(opcion1.getCodOpcionApuesta());
		Thread.sleep(2000);
		
		//Llamada
		apuestasService.marcarOpcionesGanadoras(tipoApuesta.getCodTipoApuesta(), winnerOptions);

		//Aserción
		sessionFactory.getCurrentSession().clear();
		OpcionApuesta o = new OpcionApuesta();
		int cont =0, cont2 =0, cont3 =0;
		for (OpcionApuesta opc : opciones) {
			o = apuestasService.findOpcionApuesta(opc.getCodOpcionApuesta());
			if (o.isGanadora() == null)
				cont3 = cont3 + 1;
			else if (o.isGanadora() == true)
				cont = cont + 1;
			else if (o.isGanadora() == false)
				cont2 = cont2 + 1;
		}
		
		assertTrue(cont == 1);
		assertTrue(cont2 == 2);
		assertTrue(cont3 == 0);
	}
	
	//PR-UN-095
	@Test
	public void testFindUserBets()
			throws  DuplicateInstanceException, InstanceNotFoundException, InputValidationException, StartedEventException, NotStartedEventException, ValidateOptionsException, InterruptedException {
		//Inicialización
        UserProfileDetails userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");
    	UserProfile user = userService.registerUser("user", "password", userProfileDetails);
		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha, futbol.getCodCategoria());
		OpcionApuesta opcion1= new OpcionApuesta("1", 2.50, null);
		OpcionApuesta opcion2= new OpcionApuesta("X", 3, null);
		OpcionApuesta opcion3= new OpcionApuesta("2", 1.40, null);
		List<OpcionApuesta> opciones = new ArrayList<>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		TipoApuesta tipoApuesta= new TipoApuesta("Resultado", false);
		tipoApuesta = apuestasService.createTipoApuesta(tipoApuesta, opciones, evento.getCodEvento()); 
		Apuesta apuesta1 = apuestasService.createApuesta(opcion1.getCodOpcionApuesta(), 2, user.getUserProfileId());
		Apuesta apuesta2 = apuestasService.createApuesta(opcion1.getCodOpcionApuesta(), 3, user.getUserProfileId());
		Apuesta apuesta3 = apuestasService.createApuesta(opcion2.getCodOpcionApuesta(), 4, user.getUserProfileId());
		List<Apuesta> expectedBets = new ArrayList<>();
		expectedBets.add(apuesta1);
		expectedBets.add(apuesta2);
		expectedBets.add(apuesta3);
		
		//Llamada
		ApuestaBlock apuestaBlock = apuestasService.findApuestasByUserId(user.getUserProfileId(), 0, 5); 
		
		//Aserción
		List<Apuesta> foundBets = apuestaBlock.getApuestas();
		assertEquals(expectedBets.size(), foundBets.size());
	}
}

