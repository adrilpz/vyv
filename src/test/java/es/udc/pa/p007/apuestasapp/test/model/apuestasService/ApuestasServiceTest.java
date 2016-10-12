package es.udc.pa.p007.apuestasapp.test.model.apuestasService;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
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

	/***********************************************************************
	 ***************************** PRIVATE***********************************
	 ***********************************************************************/

	private void sortStrings(List<String> categorias) {
		Collections.sort(categorias, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
	}

	private void sortOpcionApuesta(List<OpcionApuesta> opciones) {
		Collections.sort(opciones, new Comparator<OpcionApuesta>() {

			@Override
			public int compare(OpcionApuesta o1, OpcionApuesta o2) {
				return o1.getCodOpcionApuesta().compareTo(
						o2.getCodOpcionApuesta());
			}
		});
	}

	private List<OpcionApuesta> createListOpciones()
			throws InstanceNotFoundException, InputValidationException {

		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();

		OpcionApuesta opcion = new OpcionApuesta("1", 1.40, true);
		OpcionApuesta opcion2 = new OpcionApuesta("3", 3.4, true);
		OpcionApuesta opcion3 = new OpcionApuesta("4", 4.4, true);

		opciones.add(opcion);
		opciones.add(opcion2);
		opciones.add(opcion3);

		return opciones;
	}

	/***********************************************************************
	 ***********************************************************************
	 ***********************************************************************/

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateEventoWithNonExistentCategoria()
			throws InstanceNotFoundException, InputValidationException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		apuestasService.createEvento("Gran premio China", fecha,
				NON_EXISTENT_COD);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentEvento() throws InstanceNotFoundException {

		eventoDao.find(NON_EXISTENT_COD);

	}

	@Test
	public void testCreateEventoAndFindEvento()
			throws InstanceNotFoundException, InputValidationException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		Evento evento2 = eventoDao.find(evento.getCodEvento());

		assertEquals(evento, evento2);

		boolean exceptionCatched = false;
		try {
			apuestasService.createEvento("", fecha, futbol.getCodCategoria());
		} catch (InputValidationException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

	}

	@Test
	public void testFindEventos() throws InstanceNotFoundException,
			InputValidationException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 11, 24);

		Calendar fecha2 = Calendar.getInstance();
		fecha2.set(2015, 11, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Categoria tenis = new Categoria("Tenis");
		categoriaDao.save(tenis);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		Evento evento2 = apuestasService.createEvento("Deportivo - Celta",
				fecha, futbol.getCodCategoria());

		Evento evento3 = apuestasService.createEvento("Nadal - Federer", fecha,
				tenis.getCodCategoria());

		Evento evento4 = new Evento("Nadal - Federer", fecha2, tenis);
		eventoDao.save(evento4);

		List<Evento> expectedEventos1 = new ArrayList<Evento>();
		expectedEventos1.add(evento);
		expectedEventos1.add(evento2);

		List<Evento> expectedEventos2 = new ArrayList<Evento>();
		expectedEventos2.add(evento2);

		List<Evento> expectedEventos3 = new ArrayList<Evento>();
		expectedEventos3.add(evento3);

		List<Evento> expectedEventos4 = new ArrayList<Evento>();
		expectedEventos4.add(evento3);
		expectedEventos4.add(evento4);

		int numberOfEventos = 4;
		List<Evento> expectedEventos5 = new ArrayList<Evento>();
		expectedEventos5.add(evento);
		expectedEventos5.add(evento2);
		expectedEventos5.add(evento3);
		expectedEventos5.add(evento4);

		int count = 2;
		int startIndex = 0;

		EventoBlock eventoBlock1 = apuestasService.findEventos(null,
				futbol.getCodCategoria(), false, startIndex, count);

		assertTrue(eventoBlock1.getEventos().equals(expectedEventos1));

		EventoBlock eventoBlock2 = apuestasService.findEventos("Depor celt",
				futbol.getCodCategoria(), false, startIndex, count);

		assertTrue(eventoBlock2.getEventos().equals(expectedEventos2));

		EventoBlock eventoBlock3 = apuestasService.findEventos("federer", null,
				false, startIndex, count);

		assertTrue(eventoBlock3.getEventos().equals(expectedEventos3));

		EventoBlock eventoBlock4 = apuestasService.findEventos("federer", null,
				true, startIndex, count);

		assertTrue(eventoBlock4.getEventos().size() == count);

		EventoBlock eventoBlock5;

		do {
			eventoBlock5 = apuestasService.findEventos(null, null, true,
					startIndex, count);
			assertTrue(eventoBlock5.getEventos().size() <= count);

			startIndex += count;
		} while (eventoBlock5.getExistsMoreEventos());

		assertTrue(numberOfEventos == startIndex - count
				+ eventoBlock5.getEventos().size());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindEventosWithNonExistentCategoria()
			throws InstanceNotFoundException {

		apuestasService.findEventos("Depor celt", NON_EXISTENT_COD, false, 0,
				10);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateApuestaWithNonExistentOpcion()
			throws DuplicateInstanceException, InstanceNotFoundException,
			InputValidationException, StartedEventException {

		UserProfile userProfile = userService.registerUser("user",
				"userPassword", new UserProfileDetails("name", "lastName",
						"user@udc.es"));
		Apuesta apuesta = apuestasService.createApuesta(NON_EXISTENT_COD, 10,
				userProfile.getUserProfileId());
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateApuestaWithNonExistentUser()
			throws InstanceNotFoundException, InputValidationException, StartedEventException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);

		OpcionApuesta opcion = new OpcionApuesta("1", 1.40, true, tipoApuesta);
		opcionApuestaDao.save(opcion);

		Apuesta apuesta = apuestasService.createApuesta(
				opcion.getCodOpcionApuesta(), 10, NON_EXISTENT_COD);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentApuesta() throws InstanceNotFoundException {

		apuestaDao.find(NON_EXISTENT_COD);

	}

	@Test
	public void testCreateApuestaAndFindApuesta()
			throws InstanceNotFoundException, DuplicateInstanceException,
			InputValidationException, StartedEventException {

		UserProfile userProfile = userService.registerUser("user",
				"userPassword", new UserProfileDetails("name", "lastName",
						"user@udc.es"));

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);

		OpcionApuesta opcion = new OpcionApuesta("1", 1.40, true, tipoApuesta);
		opcionApuestaDao.save(opcion);

		Apuesta apuesta = apuestasService.createApuesta(
				opcion.getCodOpcionApuesta(), 10,
				userProfile.getUserProfileId());
		Apuesta apuesta2 = apuestaDao.find(apuesta.getCodApuesta());

		assertEquals(apuesta, apuesta2);

		boolean exceptionCatched = false;
		try {
			apuestasService.createApuesta(opcion.getCodOpcionApuesta(),
					NON_POSSIBLE_QUANTITY, userProfile.getUserProfileId());
		} catch (InputValidationException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

	}

	@Test
	public void testGetTiposApuesta() throws InstanceNotFoundException,
			StartedEventException, InputValidationException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Celta - Depor", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta1 = null;
		tipoApuesta1 = apuestasService
				.createTipoApuesta(new TipoApuesta("Resultado", false),
						createListOpciones(), evento.getCodEvento());

		TipoApuesta tipoApuesta2 = null;
		tipoApuesta2 = apuestasService.createTipoApuesta(new TipoApuesta(
				"Goleador", false), createListOpciones(), evento.getCodEvento());

		TipoApuesta tipoApuesta3 = null;
		tipoApuesta3 = apuestasService.createTipoApuesta(new TipoApuesta(
				"Goles", false), createListOpciones(), evento.getCodEvento());

		List<TipoApuesta> expectedTiposApuesta = new ArrayList<TipoApuesta>();
		expectedTiposApuesta.add(tipoApuesta1);
		expectedTiposApuesta.add(tipoApuesta2);
		expectedTiposApuesta.add(tipoApuesta3);

		TipoApuestaDto tiposApuesta = apuestasService.getTiposApuesta(evento
				.getCodEvento());
		List<TipoApuesta> tipoApuestaList = tiposApuesta.getTiposApuesta();
		assertTrue(expectedTiposApuesta.equals(tipoApuestaList));
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testGetTiposApuestaWithNonExistentEvento()
			throws InstanceNotFoundException {

		apuestasService.getTiposApuesta(NON_EXISTENT_COD);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentTipoApuesta()
			throws InstanceNotFoundException {

		tipoApuestaDao.find(NON_EXISTENT_COD);

	}

	@Test
	public void testCreateTipoApuesta() throws InstanceNotFoundException,
			StartedEventException, InputValidationException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = apuestasService.createTipoApuesta(
				new TipoApuesta("Resultado", false), createListOpciones(),
				evento.getCodEvento());

		TipoApuesta tipoApuesta2 = tipoApuestaDao.find(tipoApuesta
				.getCodTipoApuesta());

		assertEquals(tipoApuesta, tipoApuesta2);

		boolean exceptionCatched = false;
		try {
			apuestasService.createTipoApuesta(new TipoApuesta("", false),
					createListOpciones(), evento.getCodEvento());
		} catch (InputValidationException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testCreateTipoApuestaWithoutEvent()
			throws InstanceNotFoundException, StartedEventException,
			InputValidationException {

		apuestasService.createTipoApuesta(new TipoApuesta("Resultado", false),
				createListOpciones(), NON_EXISTENT_COD);
	}

	@Test(expected = StartedEventException.class)
	public void testCreateTipoApuestaWithStartedEvent()
			throws StartedEventException, InputValidationException,
			InstanceNotFoundException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2015, 12, 25);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();

		Evento evento = new Evento("Barsa - Madrid", fecha, futbol);
		eventoDao.save(evento);

		apuestasService.createTipoApuesta(new TipoApuesta("Resultado", false),
				createListOpciones(), evento.getCodEvento());
	}

	@Test
	public void testfindApuestasByUserIdWithoutApuestas()
			throws DuplicateInstanceException, InstanceNotFoundException {

		UserProfile userProfile = userService.registerUser("user",
				"userPassword", new UserProfileDetails("name", "lastName",
						"user@udc.es"));

		ApuestaBlock apuestaBlock;

		apuestaBlock = apuestasService.findApuestasByUserId(
				userProfile.getUserProfileId(), 0, 1);

		assertTrue(apuestaBlock.getApuestas().size() == 0
				&& !apuestaBlock.getExistsMoreApuestas());
	}

	@Test
    //@dRollback(false)
	public void testFindApuestasByUserId() throws DuplicateInstanceException,
			StartedEventException, InputValidationException,
			InstanceNotFoundException, InterruptedException {

		UserProfile userProfile = userService.registerUser("user",
				"userPassword", new UserProfileDetails("name", "lastName",
						"user@udc.es"));

		UserProfile userProfile2 = userService.registerUser("user2",
				"userPassword2", new UserProfileDetails("name2", "lastName2",
						"user@udc.es2"));

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Barsa - Madrid", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = apuestasService.createTipoApuesta(
				new TipoApuesta("Resultado", false), createListOpciones(),
				evento.getCodEvento());

		OpcionApuesta opcion = new OpcionApuesta("1", 1.40, true, tipoApuesta);
		opcionApuestaDao.save(opcion);

		/*
		 * Create the bets that should be found and store in the correct order
		 */
		int numberOfBets = 11;
		List<Apuesta> expectedBets = new ArrayList<Apuesta>();
		for (int i = 0; i < numberOfBets; i++) {
			Calendar fecha1 = Calendar.getInstance();
			fecha1.set(2016, 11, i+1);
			Apuesta apuesta = new Apuesta(opcion, 5, userProfile, fecha1);
			apuestaDao.save(apuesta);
			expectedBets.add(apuesta);
		}

		/* Create a bet that should not be found. */
		apuestasService.createApuesta(opcion.getCodOpcionApuesta(), 10,
				userProfile2.getUserProfileId());

		ApuestaBlock apuestaBlock;
		int count = 10;
		int startIndex = 0;

		/* Check bet retrieval */
		short resultIndex = 10;
		do {

			apuestaBlock = apuestasService.findApuestasByUserId(
					userProfile.getUserProfileId(), startIndex, count);
			assertTrue(apuestaBlock.getApuestas().size() <= count);

			for (Apuesta apuesta : apuestaBlock.getApuestas()) {
				assertTrue(apuesta == expectedBets.get(resultIndex--));
			}
			startIndex += count;

		} while (apuestaBlock.getExistsMoreApuestas());

		assertTrue(numberOfBets == startIndex - count
				+ apuestaBlock.getApuestas().size());
	}

	@Test
	public void testMarcarOpcionesGanadoras() throws InstanceNotFoundException,
			NotStartedEventException, InputValidationException,
			ValidateOptionsException, StartedEventException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Celta - Depor", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = apuestasService.createTipoApuesta(
				new TipoApuesta("Goleador", true), createListOpciones(),
				evento.getCodEvento());

		OpcionApuesta opcion = new OpcionApuesta("Lucas Pérez", 1.40, null,
				tipoApuesta);
		opcionApuestaDao.save(opcion);

		OpcionApuesta opcion1 = new OpcionApuesta("Iago Aspas", 2.40, null,
				tipoApuesta);
		opcionApuestaDao.save(opcion1);

		OpcionApuesta opcion2 = new OpcionApuesta("Luis Alberto", 2.00, null,
				tipoApuesta);
		opcionApuestaDao.save(opcion2);

		OpcionApuesta opcion3 = new OpcionApuesta("Fayçal", 2.00, null,
				tipoApuesta);
		opcionApuestaDao.save(opcion3);

		OpcionApuesta opcion4 = new OpcionApuesta("Fede Cartabia", 5.00, null,
				tipoApuesta);
		opcionApuestaDao.save(opcion4);

		List<OpcionApuesta> opciones = new ArrayList<OpcionApuesta>();

		opciones.add(opcion);
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		opciones.add(opcion4);

		tipoApuesta.setOpciones(opciones);

		List<Long> opcionesGanadoras = new ArrayList<Long>();
		opcionesGanadoras.add(opcion.getCodOpcionApuesta());
		opcionesGanadoras.add(opcion4.getCodOpcionApuesta());

		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);

		apuestasService.marcarOpcionesGanadoras(
				tipoApuesta.getCodTipoApuesta(), opcionesGanadoras);

		sessionFactory.getCurrentSession().clear();

		for (OpcionApuesta opc : opciones) {
			System.out.println(opc.getResultado());
		}

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

		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);

		boolean exceptionCatched = false;
		try {
			apuestasService.marcarOpcionesGanadoras(
					tipoApuesta.getCodTipoApuesta(), opcionesGanadoras);
		} catch (ValidateOptionsException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

		fecha.set(2016, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);

		TipoApuesta tipoApuesta2 = apuestasService.createTipoApuesta(
				new TipoApuesta("Resultado", false), createListOpciones(),
				evento.getCodEvento());

		OpcionApuesta opcion21 = new OpcionApuesta("1", 1.40, null,
				tipoApuesta2);
		opcionApuestaDao.save(opcion21);

		OpcionApuesta opcion22 = new OpcionApuesta("X", 2.40, null,
				tipoApuesta2);
		opcionApuestaDao.save(opcion22);

		OpcionApuesta opcion23 = new OpcionApuesta("2", 2.00, null,
				tipoApuesta2);
		opcionApuestaDao.save(opcion23);

		List<Long> opcionesGanadoras2 = new ArrayList<Long>();
		opcionesGanadoras2.add(opcion22.getCodOpcionApuesta());

		exceptionCatched = false;
		try {
			apuestasService.marcarOpcionesGanadoras(
					tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras2);
		} catch (NotStartedEventException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

		fecha.set(2015, 12, 24);
		evento.setFecha(fecha);
		eventoDao.save(evento);

		exceptionCatched = false;
		try {
			apuestasService.marcarOpcionesGanadoras(NON_EXISTENT_COD,
					opcionesGanadoras2);
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

		exceptionCatched = false;
		List<Long> listaVacia = new ArrayList<Long>();
		try {
			apuestasService.marcarOpcionesGanadoras(
					tipoApuesta2.getCodTipoApuesta(), listaVacia);
		} catch (ValidateOptionsException e) {
			exceptionCatched = true;
		}
		assertTrue(exceptionCatched);

		opcionesGanadoras2.add(opcion23.getCodOpcionApuesta());
		exceptionCatched = false;
		try {
			apuestasService.marcarOpcionesGanadoras(
					tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras2);
		} catch (ValidateOptionsException e) {
			exceptionCatched = true;
		}

		List<Long> opcionesGanadoras3 = new ArrayList<Long>();
		opcionesGanadoras3.add(opcion22.getCodOpcionApuesta());
		opcionesGanadoras3.add(opcion3.getCodOpcionApuesta());

		exceptionCatched = false;
		try {
			apuestasService.marcarOpcionesGanadoras(
					tipoApuesta2.getCodTipoApuesta(), opcionesGanadoras3);
		} catch (ValidateOptionsException e) {
			exceptionCatched = true;
		}

	}

	@Test
	public void testIsOpcionesApuestaValidated()
			throws InstanceNotFoundException, InputValidationException,
			StartedEventException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2016, 12, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		Evento evento = apuestasService.createEvento("Celta - Depor", fecha,
				futbol.getCodCategoria());

		TipoApuesta tipoApuesta = apuestasService.createTipoApuesta(
				new TipoApuesta("Goleador", false), createListOpciones(),
				evento.getCodEvento());

		OpcionApuesta opcion = new OpcionApuesta("Lucas Pérez", 1.40, true,
				tipoApuesta);
		opcionApuestaDao.save(opcion);

		OpcionApuesta opcion1 = new OpcionApuesta("Iago Aspas", 2.40, false,
				tipoApuesta);
		opcionApuestaDao.save(opcion1);

		assertTrue(true == apuestasService
				.isOpcionesApuestaValidated(tipoApuesta.getCodTipoApuesta()));

		TipoApuesta tipoApuesta1 = apuestasService.createTipoApuesta(
				new TipoApuesta("Partido", false), createListOpciones(),
				evento.getCodEvento());

		OpcionApuesta opcion2 = new OpcionApuesta("1", 1.40, null, tipoApuesta1);
		opcionApuestaDao.save(opcion2);

		OpcionApuesta opcion3 = new OpcionApuesta("X", 2.40, null, tipoApuesta1);
		opcionApuestaDao.save(opcion3);

		OpcionApuesta opcion4 = new OpcionApuesta("2", 2.40, null, tipoApuesta1);
		opcionApuestaDao.save(opcion4);

		assertTrue(false == apuestasService
				.isOpcionesApuestaValidated(tipoApuesta1.getCodTipoApuesta()));
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testIsOpcionesApuestaValidatedWithNonExistentTipoApuesta()
			throws InstanceNotFoundException {

		apuestasService.isOpcionesApuestaValidated(NON_EXISTENT_COD);

	}

	@Test
	public void testGetCategorias() {

		List<Categoria> expectedCategoria = new ArrayList<Categoria>();

		List<Categoria> categorias = apuestasService.getCategorias();

		assertEquals(expectedCategoria, categorias);

		/* Create some Categorias */

		Categoria futbol = new Categoria("Futbol");
		categoriaDao.save(futbol);

		Categoria tenis = new Categoria("Tenis");
		categoriaDao.save(tenis);
		

		Categoria formula1 = new Categoria("Formula 1");
		categoriaDao.save(formula1);
		

		Categoria motos = new Categoria("Motos");
		categoriaDao.save(motos);

		categorias = apuestasService.getCategorias();

		expectedCategoria.add(formula1);
		expectedCategoria.add(futbol);
		expectedCategoria.add(motos);
		expectedCategoria.add(tenis);
		
		assertTrue(expectedCategoria.equals(categorias));
	}

	/**
	 * Test finds
	 * 
	 * @throws InstanceNotFoundException
	 */

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentCategoria() throws InstanceNotFoundException {

		categoriaDao.find(NON_EXISTENT_COD);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentOpcionApuesta()
			throws InstanceNotFoundException {

		opcionApuestaDao.find(NON_EXISTENT_COD);
	}

	@Test(expected = InputValidationException.class)
	public void testCreateEventoBefore() throws InputValidationException,
			InstanceNotFoundException {

		Calendar fecha = Calendar.getInstance();
		fecha.set(2015, 11, 24);

		Categoria futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);

		apuestasService.createEvento("Madrid-Barça", fecha,
				futbol.getCodCategoria());
	}
}
