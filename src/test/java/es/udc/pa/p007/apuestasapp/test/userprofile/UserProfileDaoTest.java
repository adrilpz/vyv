package es.udc.pa.p007.apuestasapp.test.userprofile;

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
public class UserProfileDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private SessionFactory sessionFactory;
	
	//VARIABLES GLOBALES
	private UserProfile userProfile, userProfile2;
	
	@Before
	public void initialize() {		
		//Creación usuarios
		userProfile = new UserProfile("user", "userPassword", "name", "lastName", "user@udc.es");
		userProfileDao.save(userProfile);
		
		userProfile2 = new UserProfile("user2", "userPassword2", "name2", "lastName2", "user2@udc.es");
		userProfileDao.save(userProfile2);
		
	}
	
	//PR-UN-083
	@Test
	public void testSave() throws InstanceNotFoundException{
		UserProfile userPR083 = new UserProfile("user3", "userPassword3", "name3", "lastName3", "user3@udc.es");
		userProfileDao.save(userPR083);
		UserProfile foundUser=userProfileDao.find(userPR083.getUserProfileId());
		assertEquals(userPR083, foundUser);
	}
	
	//PR-UN-084
	@Test
	public void testFind() throws InstanceNotFoundException{
		UserProfile foundUser=userProfileDao.find(userProfile.getUserProfileId());
		assertEquals(userProfile, foundUser);
	}
	
	//PR-UN-085
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		userProfileDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-086
	/*@Test
	public void testRemove() throws InstanceNotFoundException{
		userProfileDao.remove(user2.getCodApuesta());
		sessionFactory.getCurrentSession().clear();
		UserProfile foundUser=userProfileDao.find(user2.getCodApuesta());
		assertNull(foundUser);
	}*/
	
	//PR-UN-087
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		userProfileDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-088
	@Test
	public void testFindByUserLogin() throws InstanceNotFoundException{
		UserProfile foundUser=userProfileDao.findByLoginName(userProfile.getLoginName());
		assertEquals(userProfile, foundUser);
	}
	
	//PR-UN-089
	@Test(expected=InstanceNotFoundException.class)
	public void testFindByUserLoginWithNoExistentLogin() throws InstanceNotFoundException{
		userProfileDao.findByLoginName("");
	}
}