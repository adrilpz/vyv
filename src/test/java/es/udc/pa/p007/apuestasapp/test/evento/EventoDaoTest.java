package es.udc.pa.p007.apuestasapp.test.evento;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

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
public class EventoDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private SessionFactory sessionFactory;
	
	//VARIABLES GLOBALES
	Categoria futbol;
	Evento evento, evento2;
	Calendar fecha;
	
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
		
		evento2= new Evento("Depor-Celta",fecha,futbol);
		eventoDao.save(evento2);
		
	}
	
	//PR-UN-062
	@Test
	public void testSave() throws InstanceNotFoundException{
		Evento eventoPR062 = new Evento("Barsa- Atletico", fecha, futbol);
		eventoDao.save(eventoPR062);
		Evento foundEvento=eventoDao.find(eventoPR062.getCodEvento());
		assertEquals(eventoPR062, foundEvento);
	}
	
	//PR-UN-063
	@Test
	public void testFind() throws InstanceNotFoundException{
		Evento foundEvento=eventoDao.find(evento.getCodEvento());
		assertEquals(evento, foundEvento);
	}
	
	//PR-UN-064
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		eventoDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-065
	/*@Test
	public void testRemove() throws InstanceNotFoundException{
		eventoDao.remove(evento2.getCodApuesta());
		sessionFactory.getCurrentSession().clear();
		Evento foundEvento=eventoDao.find(evento2.getCodApuesta());
		assertNull(foundEvento);
	}*/
	
	//PR-UN-066
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		eventoDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-067
	@Test
	public void testFindEventosAbiertos(){
		List<Evento> foundEventos=eventoDao.findEventosAbiertos("barsa", futbol.getCodCategoria(), false, 0, 5);
		assertEquals(foundEventos.size(), 1);
	}
}