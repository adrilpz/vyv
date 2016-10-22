package es.udc.pa.p007.apuestasapp.test.tipoApuesta;

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
public class TipoApuestaDaoTest {
	
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
	private SessionFactory sessionFactory;
	
	//VARIABLES GLOBALES
	Categoria futbol;
	Evento evento;
	Calendar fecha;
	TipoApuesta tipoApuesta, tipoApuesta2;
	
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
		
		//Creación tipos de apuesta
		tipoApuesta = new TipoApuesta("Resultado", false, evento);
		tipoApuestaDao.save(tipoApuesta);
		
		tipoApuesta2 = new TipoApuesta("Goles", false, evento);
		tipoApuestaDao.save(tipoApuesta2); 

	}
	
	//PR-UN-077
	@Test
	public void testSave() throws InstanceNotFoundException{
		TipoApuesta tipoApuestaPR077 = new TipoApuesta("Goleador", false, evento);
		tipoApuestaDao.save(tipoApuestaPR077);
		TipoApuesta foundTipoApuesta=tipoApuestaDao.find(tipoApuestaPR077.getCodTipoApuesta());
		assertEquals(tipoApuestaPR077, foundTipoApuesta);
	}
	
	//PR-UN-078
	@Test
	public void testFind() throws InstanceNotFoundException{
		TipoApuesta foundTipoApuesta=tipoApuestaDao.find(tipoApuesta.getCodTipoApuesta());
		assertEquals(tipoApuesta, foundTipoApuesta);
	}
	
	//PR-UN-079
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		tipoApuestaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-080
	/*@Test
	public void testRemove() throws InstanceNotFoundException{
		tipoApuestaDao.remove(tipoApuesta2.getCodApuesta());
		sessionFactory.getCurrentSession().clear();
		TipoApuesta foundTipoApuesta=tipoApuestaDao.find(tipoApuesta2.getCodApuesta());
		assertNull(foundTipoApuesta);
	}*/
	
	//PR-UN-081
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		tipoApuestaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-082
	@Test
	public void testFindTiposApuesta(){
		List<TipoApuesta> foundTiposApuesta=tipoApuestaDao.getTiposApuesta(evento.getCodEvento());
		assertEquals(foundTiposApuesta.size(), 2);
	}
}