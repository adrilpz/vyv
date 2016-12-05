package es.udc.pa.p007.apuestasapp.test.model.categoria;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.generator.iterable.Iterables;

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
	
	//PR-UN-097
	@Test 
	public void testGenerator(){
		for(Categoria categoria : Iterables.toIterable(new CategoriaGenerator())){
			Categoria newCategoria = new Categoria(categoria.getNombre());
			assertEquals(categoria.getNombre(), newCategoria.getNombre());
		}
	}
	
	//PR-UN-098
	@Test
	public void testSaveRandomCategories() throws InstanceNotFoundException{
		//Setup
		for(Categoria categoria : Iterables.toIterable(new CategoriaGenerator())){
			
			//Llamada
			categoriaDao.save(categoria);

			//Aserción
			Categoria foundCategoria= categoriaDao.find(categoria.getCodCategoria());
			assertEquals(categoria, foundCategoria);
		}
	}
}
