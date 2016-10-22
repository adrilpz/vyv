package es.udc.pa.p007.apuestasapp.test.categoria;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.sql.Connection;
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
public class CategoriaDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private CategoriaDao categoriaDao;
	
	//VARIABLES GLOBALES
	private Categoria futbol, baloncesto;
	
	@Before
	public void initialize() {
		
		//Creación de categorías
		futbol = new Categoria("Fútbol");
		categoriaDao.save(futbol);
		
		baloncesto = new Categoria("Baloncesto");
		categoriaDao.save(baloncesto);		
	}
	
	//PR-UN-056
	@Test
	public void testSave() throws InstanceNotFoundException{
		//Setup
		Categoria categoriaPR056 = new Categoria("categoria");
		
		//Llamada
		categoriaDao.save(categoriaPR056);
		
		//Aserción
		Categoria foundCategoria= categoriaDao.find(categoriaPR056.getCodCategoria());
		assertEquals(categoriaPR056, foundCategoria);
	}
	
	//PR-UN-057
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		Categoria foundCategoria= categoriaDao.find(futbol.getCodCategoria());
		
		//Aserción
		assertEquals(futbol, foundCategoria);
	}
	
	//PR-UN-058
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		categoriaDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-059
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		categoriaDao.remove(futbol.getCodCategoria());
		
		//Aserción
		try {
			categoriaDao.find(futbol.getCodCategoria());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-060
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		categoriaDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-061
	@Test
	public void testFindCategorias(){
		//Llamada
		List<Categoria> foundCategorias=categoriaDao.findCategorias();
		
		//Aserción
		assertEquals(foundCategorias.size(), 2);
	}
}
