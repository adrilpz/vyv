package es.udc.pa.p007.apuestasapp.test.model.apuestasService;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuesta.ApuestaDao;
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
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDto;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userservice.UserProfileDetails;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ApuestasServiceTest {

	private final long NON_EXISTENT_COD = -1;
	private final long NON_POSSIBLE_QUANTITY = 0;

	@Autowired
	private ApuestasService apuestasService;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private ApuestaDao apuestaDao;

	@Autowired
	private TipoApuestaDao tipoApuestaDao;

	@Autowired
	private OpcionApuestaDao opcionApuestaDao;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionFactory sessionFactory;

	//VARIABLES GLOBALES
	Categoria futbol, tenis, formula1, motos;
	Evento evento,evento2,evento3,evento4;
	Calendar fecha, fecha2;
	UserProfile userProfile, userProfile2;
	TipoApuesta tipoApuesta, tipoApuesta2, tipoApuesta3, tipoApuesta4;
	OpcionApuesta opcion, opcion1, opcion2, opcion3, opcion4, opcion5, opcion6, opcion7;
	Apuesta apuesta, apuesta2, apuesta3, apuesta4;
	
	@Before
	public void initialize() throws DuplicateInstanceException, InstanceNotFoundException, InputValidationException{
		//Creación de fechas
		fecha = Calendar.getInstance();
		fecha.set(2017, 12, 24);
		
		fecha2 = Calendar.getInstance();
		fecha2.set(2015, 11, 24);
		
		//Creación de categorías
		futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);
		
		tenis = new Categoria("Tenis");
		categoriaDao.save(tenis);
		
		formula1 = new Categoria("Formula 1");
		categoriaDao.save(formula1);

		motos = new Categoria("Motos");
		categoriaDao.save(motos);
		
		//Creación de eventos
		evento = new Evento("Barsa - Madrid", fecha, futbol);
		eventoDao.save(evento);
		
		evento2 = new Evento("Deportivo - Celta", fecha, futbol);
		eventoDao.save(evento2);
		
		evento3 = new Evento("Nadal - Federer", fecha, tenis);
		eventoDao.save(evento3);
		
		evento4 = new Evento("Nadal - Federer", fecha2, tenis);
		eventoDao.save(evento4);
		
		//Creación usuarios
		userProfile = userService.registerUser("user", "userPassword", new UserProfileDetails("name", "lastName", "user@udc.es"));
		userProfile2 = userService.registerUser("user2", "userPassword2", new UserProfileDetails("name2", "lastName2","user@udc.es2"));
		
		//Creación tipos de apuesta
		tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);
		
		tipoApuesta2 = new TipoApuesta("Goleador", true, evento);
		tipoApuestaDao.save(tipoApuesta2);
		
		tipoApuesta3 = new TipoApuesta("Hat-trick", false, evento);
		tipoApuestaDao.save(tipoApuesta3);
		
		tipoApuesta4 = new TipoApuesta("Goles", false, evento);
		tipoApuestaDao.save(tipoApuesta4);

		//Creación de opciones apuesta
		opcion = new OpcionApuesta("1", 1.40, true, tipoApuesta);
		opcionApuestaDao.save(opcion);
		
		opcion1 = new OpcionApuesta("Messi", 1.40, null,tipoApuesta2);
		opcionApuestaDao.save(opcion1);

		opcion2 = new OpcionApuesta("Neymar", 2.40, null,tipoApuesta2);
		opcionApuestaDao.save(opcion2);

		opcion3 = new OpcionApuesta("Cristiano", 2.00, null,	tipoApuesta2);
		opcionApuestaDao.save(opcion3);

		opcion4 = new OpcionApuesta("Benzemá", 2.00, null,	tipoApuesta2);
		opcionApuestaDao.save(opcion4);

		opcion5 = new OpcionApuesta("Morata", 5.00, null, tipoApuesta2);
		opcionApuestaDao.save(opcion5);
		
		opcion6 = new OpcionApuesta("5", 5.00, false, tipoApuesta4);
		opcionApuestaDao.save(opcion6);
		
		opcion7 = new OpcionApuesta("2", 5.00, true, tipoApuesta4);
		opcionApuestaDao.save(opcion7);
		
		//Creación de apuestas
		apuesta = new Apuesta(opcion, 8, userProfile, fecha);
		apuestaDao.save(apuesta);
		
		apuesta2 = new Apuesta(opcion, 5, userProfile, fecha);
		apuestaDao.save(apuesta2);
		
		apuesta3= new Apuesta(opcion, 6, userProfile, fecha);
		apuestaDao.save(apuesta3);
		
		apuesta4 = new Apuesta(opcion, 10, userProfile2, fecha);
		apuestaDao.save(apuesta4);
	}
	
	//PR-UN-001
	@Test
	public void testCreateEvento()
			throws InstanceNotFoundException, InputValidationException {
		Evento eventoPR001 = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());
		assertNotNull(eventoPR001);
		assertEquals(Evento.class, eventoPR001.getClass());
	}
	
	//PR-UN-002
	@Test(expected = InputValidationException.class)
	public void testCreateEventoWithEmptyName()
			throws InstanceNotFoundException, InputValidationException {		
		apuestasService.createEvento("", fecha, futbol.getCodCategoria());
	}
	
	//PR-UN-003
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateEventoWithNonExistentCategoria()
			throws InstanceNotFoundException, InputValidationException {
		apuestasService.createEvento("Gran premio China", fecha,NON_EXISTENT_COD);
	}
	
	//PR-UN-004
	@Test(expected = InputValidationException.class)
	public void testCreateEventoInDateBefore() throws InputValidationException,
			InstanceNotFoundException {
		apuestasService.createEvento("Sevilla-Betis", fecha2, futbol.getCodCategoria());
	}
	
	//PR-UN-005
	@Test
	public void testFindEventosByCategory() throws InstanceNotFoundException, InputValidationException {
		List<Evento> expectedEventos1 = new ArrayList<Evento>();
		expectedEventos1.add(evento);
		expectedEventos1.add(evento2);
		int count = 2;
		int startIndex = 0;
		EventoBlock eventoBlock1 = apuestasService.findEventos(null,
				futbol.getCodCategoria(), false, startIndex, count);
		assertTrue(eventoBlock1.getEventos().equals(expectedEventos1));
	}	
	
	//PR-UN-006
	@Test
	public void testFindEventosByCategoryAndName() throws InstanceNotFoundException, InputValidationException {
		List<Evento> expectedEventos2 = new ArrayList<Evento>();
		expectedEventos2.add(evento2);
		int count = 2;
		int startIndex = 0;
		EventoBlock eventoBlock2 = apuestasService.findEventos("Depor celt",
				futbol.getCodCategoria(), false, startIndex, count);
		assertTrue(eventoBlock2.getEventos().equals(expectedEventos2));
	}
	
	//PR-UN-007
	@Test
	public void testFindEventosByName() throws InstanceNotFoundException, InputValidationException {
		List<Evento> expectedEventos3 = new ArrayList<Evento>();
		expectedEventos3.add(evento3);
		int count = 2;
		int startIndex = 0;
		EventoBlock eventoBlock3 = apuestasService.findEventos("federer", null,
				false, startIndex, count);
		assertTrue(eventoBlock3.getEventos().equals(expectedEventos3));
	}

	//PR-UN-008
	@Test
	public void testFindEventosByNameBeingAdmin() throws InstanceNotFoundException, InputValidationException {
		List<Evento> expectedEventos4 = new ArrayList<Evento>();
		expectedEventos4.add(evento3);
		expectedEventos4.add(evento4);
		int count = 2;
		int startIndex = 0;
		EventoBlock eventoBlock4 = apuestasService.findEventos("federer", null,
				true, startIndex, count);
		assertTrue(eventoBlock4.getEventos().size() == count);
	}

	//PR-UN-009
	@Test
	public void testFindEventosPagination() throws InstanceNotFoundException, InputValidationException {
		List<Evento> expectedEventos5 = new ArrayList<Evento>();
		expectedEventos5.add(evento);
		expectedEventos5.add(evento2);
		expectedEventos5.add(evento3);
		expectedEventos5.add(evento4);
		int numberOfEventos = 4;
		int count = 2;
		int startIndex = 0;
		EventoBlock eventoBlock5;
		do {
			eventoBlock5 = apuestasService.findEventos(null, null, true,
					startIndex, count);
			assertTrue(eventoBlock5.getEventos().size() <= count);
			startIndex += count;
		} while (eventoBlock5.getExistsMoreEventos());
		assertTrue(numberOfEventos == startIndex - count + eventoBlock5.getEventos().size());
	}

	//PR-UN-010
	@Test(expected = InstanceNotFoundException.class)
	public void testFindEventosWithNonExistentCategoria()
			throws InstanceNotFoundException {
		apuestasService.findEventos("Depor celt", NON_EXISTENT_COD, false, 0,
				10);
	}

	//PR-UN-011
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateApuestaWithNonExistentOpcion()
			throws DuplicateInstanceException, InstanceNotFoundException,
			InputValidationException, StartedEventException {
		apuestasService.createApuesta(NON_EXISTENT_COD, 10, userProfile.getUserProfileId());
	}

	//PR-UN-012
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateApuestaWithNonExistentUser()
			throws InstanceNotFoundException, InputValidationException, StartedEventException {
		apuestasService.createApuesta(
				opcion.getCodOpcionApuesta(), 10, NON_EXISTENT_COD);
	}

	//PR-UN-013
	@Test
	public void testCreateApuesta()
			throws InstanceNotFoundException, DuplicateInstanceException, InputValidationException, StartedEventException {
		Apuesta apuestaPR012 = apuestasService.createApuesta(opcion.getCodOpcionApuesta(), 10, userProfile.getUserProfileId());
		assertNotNull(apuestaPR012);
		assertEquals(Apuesta.class, apuestaPR012.getClass());
	}
	
	//PR-UN-014
	@Test(expected=InputValidationException.class)
	public void testCreateApuestaWithNegativeQuantity()
			throws InstanceNotFoundException, DuplicateInstanceException, InputValidationException, StartedEventException {
			apuestasService.createApuesta(opcion.getCodOpcionApuesta(),
					NON_POSSIBLE_QUANTITY, userProfile.getUserProfileId());		
	}
	
	//PR-UN-015
	@Test
	public void testGetTiposApuesta() throws InstanceNotFoundException,
			StartedEventException, InputValidationException {
		List<TipoApuesta> expectedTiposApuesta = new ArrayList<TipoApuesta>();
		expectedTiposApuesta.add(tipoApuesta);
		expectedTiposApuesta.add(tipoApuesta2);
		expectedTiposApuesta.add(tipoApuesta3);
		expectedTiposApuesta.add(tipoApuesta4);
		TipoApuestaDto tiposApuesta = apuestasService.getTiposApuesta(evento.getCodEvento());
		List<TipoApuesta> tipoApuestaList = tiposApuesta.getTiposApuesta();
		assertTrue(expectedTiposApuesta.equals(tipoApuestaList));
	}
	
	//PR-UN-016
	@Test(expected = InstanceNotFoundException.class)
	public void testGetTiposApuestaWithNonExistentEvento()
			throws InstanceNotFoundException {
		apuestasService.getTiposApuesta(NON_EXISTENT_COD);

	}

	//PR-UN-017
	@Test
	public void testCreateTipoApuesta() throws InstanceNotFoundException,
			StartedEventException, InputValidationException {
		List<OpcionApuesta> opcionesNoPersistentes = new ArrayList<OpcionApuesta>();
		OpcionApuesta opcionNoPersistente = new OpcionApuesta("2", 1.40, true);
		OpcionApuesta opcionNoPersistente2 = new OpcionApuesta("3", 3.4, true);
		OpcionApuesta opcionNoPersistente3 = new OpcionApuesta("4", 4.4, true);
		opcionesNoPersistentes.add(opcionNoPersistente);
		opcionesNoPersistentes.add(opcionNoPersistente2);
		opcionesNoPersistentes.add(opcionNoPersistente3);
		TipoApuesta tipoApuestaPR016 = apuestasService.createTipoApuesta(
				new TipoApuesta("Resultado", false), opcionesNoPersistentes, evento.getCodEvento());
		assertNotNull(tipoApuestaPR016);
		assertEquals(TipoApuesta.class, tipoApuestaPR016.getClass());
	}
	
	//PR-UN-018
	@Test(expected=InputValidationException.class)
	public void testCreateTipoApuestaWithEmptyName() throws InstanceNotFoundException,
			StartedEventException, InputValidationException {
		List<OpcionApuesta> opcionesNoPersistentes = new ArrayList<OpcionApuesta>();
		OpcionApuesta opcionNoPersistente = new OpcionApuesta("2", 1.40, true);
		OpcionApuesta opcionNoPersistente2 = new OpcionApuesta("3", 3.4, true);
		OpcionApuesta opcionNoPersistente3 = new OpcionApuesta("4", 4.4, true);
		opcionesNoPersistentes.add(opcionNoPersistente);
		opcionesNoPersistentes.add(opcionNoPersistente2);
		opcionesNoPersistentes.add(opcionNoPersistente3);
		for (OpcionApuesta opc: opcionesNoPersistentes){
			tipoApuesta.addOpciones(opc);
		}
		apuestasService.createTipoApuesta(new TipoApuesta("", false), opcionesNoPersistentes, evento.getCodEvento());
	}

	//PR-UN-019
	@Test(expected = InstanceNotFoundException.class)
	public void testCreateTipoApuestaWithoutEvent()
			throws InstanceNotFoundException, StartedEventException, InputValidationException {
		List<OpcionApuesta> opcionesNoPersistentes = new ArrayList<OpcionApuesta>();
		OpcionApuesta opcionNoPersistente = new OpcionApuesta("2", 1.40, true);
		OpcionApuesta opcionNoPersistente2 = new OpcionApuesta("3", 3.4, true);
		OpcionApuesta opcionNoPersistente3 = new OpcionApuesta("4", 4.4, true);
		opcionesNoPersistentes.add(opcionNoPersistente);
		opcionesNoPersistentes.add(opcionNoPersistente2);
		opcionesNoPersistentes.add(opcionNoPersistente3);
		for (OpcionApuesta opc: opcionesNoPersistentes){
			tipoApuesta.addOpciones(opc);
		}
		apuestasService.createTipoApuesta(new TipoApuesta("Resultado", false),
				opcionesNoPersistentes, NON_EXISTENT_COD);
	}

	//PR-UN-020
	@Test(expected = StartedEventException.class)
	public void testCreateTipoApuestaWithStartedEvent()
			throws StartedEventException, InputValidationException,
			InstanceNotFoundException {
		List<OpcionApuesta> opcionesNoPersistentes = new ArrayList<OpcionApuesta>();
		OpcionApuesta opcionNoPersistente = new OpcionApuesta("2", 1.40, true);
		OpcionApuesta opcionNoPersistente2 = new OpcionApuesta("3", 3.4, true);
		OpcionApuesta opcionNoPersistente3 = new OpcionApuesta("4", 4.4, true);
		opcionesNoPersistentes.add(opcionNoPersistente);
		opcionesNoPersistentes.add(opcionNoPersistente2);
		opcionesNoPersistentes.add(opcionNoPersistente3);
		for (OpcionApuesta opc: opcionesNoPersistentes){
			tipoApuesta.addOpciones(opc);
		}
		apuestasService.createTipoApuesta(new TipoApuesta("Resultado", false),
				opcionesNoPersistentes, evento4.getCodEvento());
	}

	//PR-UN-021
	@Test
	public void testFindApuestasByUserId() throws DuplicateInstanceException,
			StartedEventException, InputValidationException, InstanceNotFoundException, InterruptedException {
		List<Apuesta> expectedBets = new ArrayList<Apuesta>();
		expectedBets.add(apuesta);
		expectedBets.add(apuesta2);
		expectedBets.add(apuesta3);
		ApuestaBlock apuestaBlock;
		int count = 2;
		int startIndex = 0;
		do {
			apuestaBlock = apuestasService.findApuestasByUserId(
					userProfile.getUserProfileId(), startIndex, count);
			assertTrue(apuestaBlock.getApuestas().size() <= count);
			startIndex += count;
		} while (apuestaBlock.getExistsMoreApuestas());
		assertTrue(3 == startIndex - count
				+ apuestaBlock.getApuestas().size());
	}

	//PR-UN-022
	@Test
	public void testMarcarOpcionesGanadoras() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta2.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras);
		sessionFactory.getCurrentSession().clear();
		OpcionApuesta o;
		int cont = 0;
		int cont2 = 0;
		int cont3 = 0;
		for (OpcionApuesta opc : opciones) {
			o = opcionApuestaDao.find(opc.getCodOpcionApuesta());
			if (o.isGanadora() == null)
				cont3 = cont3 + 1;
			else if (o.isGanadora() == true)
				cont = cont + 1;
			else if (o.isGanadora() == false)
				cont2 = cont2 + 1;
		}
		assertTrue(cont == 2);
		assertTrue(cont2 == 3);
		assertTrue(cont3 == 0);
	}
	
	//PR-UN-023
	@Test(expected=ValidateOptionsException.class)
	public void testMarcarOpcionesGanadorasMarcadasAnteriormente() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta2.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras);
		sessionFactory.getCurrentSession().clear();
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras);
	}
	
	//PR-UN-024
	@Test(expected=NotStartedEventException.class)
	public void testMarcarOpcionesGanadorasMarcadasConEventoNoEmpezado() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta2.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras);
	}
	
	//PR-UN-025
	@Test(expected=InstanceNotFoundException.class)
	public void testMarcarOpcionesGanadorasMarcadasConTipoApuestaInexistente() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta2.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());
		apuestasService.marcarOpcionesGanadoras(NON_EXISTENT_COD,opcionesGanadoras);
	}

	//PR-UN-026
	@Test(expected=ValidateOptionsException.class)
	public void testMarcarOpcionesGanadorasMarcadasConListaVacia() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		List<Long> listaVacia = new ArrayList<Long>();
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), listaVacia);
	}
	
	//PR-UN-027
	@Test(expected=ValidateOptionsException.class)
	public void testMarcarOpcionesGanadorasMarcadasConOpcionesInvalidas() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta2.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion.getCodOpcionApuesta());
		apuestasService.marcarOpcionesGanadoras(tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras);
	}

	//PR-UN-028
	@Test(expected=ValidateOptionsException.class)
	public void testMarcarOpcionesGanadorasDeApuestaSimpleConMultiplesOpciones() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {
		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);
		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);
		opciones.add(opcion5);
		tipoApuesta3.setOpciones(opciones);
		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion1.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion2.getCodOpcionApuesta());
		apuestasService.marcarOpcionesGanadoras(tipoApuesta3.getCodTipoApuesta(), opcionesGanadoras);
	}

	//PR-UN-029
	@Test
	public void testIsOpcionesApuestaValidated()
			throws InstanceNotFoundException, InputValidationException,StartedEventException {
		assertTrue(true == apuestasService.isOpcionesApuestaValidated(tipoApuesta4.getCodTipoApuesta()));
	}

	//PR-UN-030
	@Test(expected = InstanceNotFoundException.class)
	public void testIsOpcionesApuestaValidatedWithNonExistentTipoApuesta()
			throws InstanceNotFoundException {
		apuestasService.isOpcionesApuestaValidated(NON_EXISTENT_COD);
	}

	//PR-UN-031
	@Test
	public void testGetCategorias() {

		List<Categoria> expectedCategoria = new ArrayList<Categoria>();
		expectedCategoria.add(formula1);
		expectedCategoria.add(futbol);
		expectedCategoria.add(motos);
		expectedCategoria.add(tenis);
		List<Categoria> categorias = apuestasService.getCategorias();
		assertTrue(expectedCategoria.equals(categorias));
	}
	
	//PR-UN-032
	@Test
	public void testFindEvento() throws InstanceNotFoundException {
		Evento found = apuestasService.findEvento(evento.getCodEvento());
		assertEquals(evento,found);
	}
	
	//PR-UN-033
	@Test
	public void testFindCategoria() throws InstanceNotFoundException {
		Categoria found = apuestasService.findCategoria(futbol.getCodCategoria());
		assertEquals(futbol,found);
	}

	//PR-UN-034
	@Test
	public void testFindTipoApuesta() throws InstanceNotFoundException {
		TipoApuesta found = apuestasService.findTipoApuesta(tipoApuesta.getCodTipoApuesta());
		assertEquals(tipoApuesta,found);
	}
	
	//PR-UN-035
	@Test
	public void testFindOpcionApuesta() throws InstanceNotFoundException {
		OpcionApuesta found = apuestasService.findOpcionApuesta(opcion.getCodOpcionApuesta());
		assertEquals(opcion,found);
	}

	//PR-UN-036
	@Test
	public void testFindApuesta() throws InstanceNotFoundException {
		Apuesta found = apuestasService.findApuesta(apuesta.getCodApuesta());
		assertEquals(apuesta,found);
	}
}
