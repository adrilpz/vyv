package es.udc.pa.p007.apuestasapp.test.model.userprofile;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfileDao;
import es.udc.pa.p007.apuestasapp.test.model.opcionApuesta.OpcionApuestaGenerator;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.generator.iterable.Iterables;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserProfileDaoTest {
	
	private final long NON_EXISTENT_COD = -1;

	@Autowired
	private UserProfileDao userProfileDao;
	
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
		//Setup
		UserProfile userPR083 = new UserProfile("user3", "userPassword3", "name3", "lastName3", "user3@udc.es");
		
		//Llamada
		userProfileDao.save(userPR083);
		
		//Aserción
		UserProfile foundUser=userProfileDao.find(userPR083.getUserProfileId());
		assertEquals(userPR083, foundUser);
	}
	
	//PR-UN-084
	@Test
	public void testFind() throws InstanceNotFoundException{
		//Llamada
		UserProfile foundUser=userProfileDao.find(userProfile.getUserProfileId());
		
		//Aserción
		assertEquals(userProfile, foundUser);
	}
	
	//PR-UN-085
	@Test(expected=InstanceNotFoundException.class)
	public void testFindWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		userProfileDao.find(NON_EXISTENT_COD);
	}
	
	//PR-UN-086
	@Test
	public void testRemove() throws InstanceNotFoundException{
		//Setup
		boolean exceptionCached=false;

		//Llamada
		userProfileDao.remove(userProfile.getUserProfileId());
				
		//Aserción
		try {
			userProfileDao.find(userProfile.getUserProfileId());
		} catch (InstanceNotFoundException e) {
			exceptionCached=true;
		}
		assertTrue(exceptionCached);
	}
	
	//PR-UN-087
	@Test(expected=InstanceNotFoundException.class)
	public void testRemoveWithNoExistentId() throws InstanceNotFoundException{
		//Llamada
		userProfileDao.remove(NON_EXISTENT_COD);
	}
	
	//PR-UN-088
	@Test
	public void testFindByUserLogin() throws InstanceNotFoundException{
		//Llamada
		UserProfile foundUser=userProfileDao.findByLoginName(userProfile.getLoginName());
		
		//Aserción
		assertEquals(userProfile, foundUser);
	}
	
	//PR-UN-089
	@Test(expected=InstanceNotFoundException.class)
	public void testFindByUserLoginWithNoExistentLogin() throws InstanceNotFoundException{
		//Llamada
		userProfileDao.findByLoginName("");
	}
	
	//PR-UN-105
	@Test 
	public void testGenerator(){
		for(UserProfile u : Iterables.toIterable(new UserProfileGenerator())){
			UserProfile newUserProfile = new UserProfile(u.getLoginName(), u.getEncryptedPassword(), u.getFirstName(), u.getLastName(), u.getEmail());
			assertEquals(u.getLoginName(), newUserProfile.getLoginName());
			assertEquals(u.getEncryptedPassword(), newUserProfile.getEncryptedPassword());
			assertEquals(u.getFirstName(), newUserProfile.getFirstName());
			assertEquals(u.getLastName(), newUserProfile.getLastName());
			assertEquals(u.getEmail(), newUserProfile.getEmail());
		}
	}
	
	//PR-UN-106
	@Test
	public void testSaveRandomUserProfiles() throws InstanceNotFoundException{
		//Setup
		for(UserProfile u : Iterables.toIterable(new UserProfileGenerator())){
			
			//Llamada
			userProfileDao.save(u);

			//Aserción
			UserProfile foundUserProfile= userProfileDao.find(u.getUserProfileId());
			assertEquals(u, foundUserProfile);
		}
	}
}