package es.udc.pa.p007.apuestasapp.test.model.userservice;

import static es.udc.pa.p007.apuestasapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.p007.apuestasapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfileDao;
import es.udc.pa.p007.apuestasapp.model.userservice.IncorrectPasswordException;
import es.udc.pa.p007.apuestasapp.model.userservice.UserProfileDetails;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
import es.udc.pa.p007.apuestasapp.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserServiceTest {

    private final long NON_EXISTENT_USER_PROFILE_ID = -1;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserProfileDao userProfileDao;
    
    //Variables globales
    private UserProfile userProfile;
    private String loginName, clearPassword;
    private UserProfileDetails userProfileDetails;
    
    @Before
	public void initialize(){
    	//Creación detalles usuario
    	loginName = "user";
        clearPassword = "userPassword";
        userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");
    	
    	//Creación usuarios
    	userProfile = new UserProfile(loginName, PasswordEncrypter.crypt(clearPassword), "name", "lastName", "user@udc.es");
    	userProfileDao.save(userProfile);
    }

    //PR-UN-037
    @Test
    public void testRegisterUser() throws DuplicateInstanceException{
    	//Llamada
    	UserProfile userProfilePR037 = userService.registerUser("prueba", "password", userProfileDetails);
        
    	//Aserción
    	assertNotNull(userProfilePR037);
		assertEquals(UserProfile.class, userProfilePR037.getClass());
    }
    
    //PR-UN-038
    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicatedUser() throws DuplicateInstanceException{        
      //Llamada
        userService.registerUser(loginName, clearPassword, userProfileDetails);
    }
    
    //PR-UN-039
    @Test
    public void FindUserProfile() throws InstanceNotFoundException {
    	//Llamada
    	UserProfile foundUserProfile = userService.findUserProfile(userProfile.getUserProfileId());
        
    	//Aserción
    	assertEquals(userProfile, foundUserProfile);
    }

    //PR-UN-040
    @Test
    public void testLoginClearPassword() throws IncorrectPasswordException,
        	InstanceNotFoundException, DuplicateInstanceException {
      //Llamada
        UserProfile loginUserProfile = userService.login(userProfile.getLoginName(), clearPassword, false);
        
        //Aserción
        assertEquals(userProfile, loginUserProfile);
    }

    //PR-UN-041
    @Test
    public void testLoginEncryptedPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {
        //Llamada
    	UserProfile loginUserProfile = userService.login(userProfile.getLoginName(), userProfile.getEncryptedPassword(), true);
        
    	//Aserción
    	assertEquals(userProfile, loginUserProfile);

    }

    //PR-UN-042
    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPasword() throws IncorrectPasswordException,
            InstanceNotFoundException {
    	//Llamada
    	userService.login(userProfile.getLoginName(), 'X' + clearPassword, false);
    }

    //PR-UN-043
    @Test(expected = InstanceNotFoundException.class)
    public void testLoginWithNonExistentUser()
            throws IncorrectPasswordException, InstanceNotFoundException {
    	//Llamada
    	userService.login("userNonExistent", "userPassword", false);
    }

    //PR-UN-044
    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentUser() throws InstanceNotFoundException {
    	//Llamada
    	userService.findUserProfile(NON_EXISTENT_USER_PROFILE_ID);
    }

    //PR-UN-045
    @Test
    public void testUpdate() throws InstanceNotFoundException,
            IncorrectPasswordException, DuplicateInstanceException {
    	//Setup        
        UserProfileDetails newUserProfileDetails = new UserProfileDetails(
            'X' + userProfile.getFirstName(), 'X' + userProfile.getLastName(),
            'X' + userProfile.getEmail());

        //Llamada
        userService.updateUserProfileDetails(userProfile.getUserProfileId(), newUserProfileDetails);

        //Aserción
        UserProfile foundUserProfile = userService.findUserProfile(userProfile.getUserProfileId());
        assertEquals(newUserProfileDetails.getFirstName(), foundUserProfile.getFirstName());
        assertEquals(newUserProfileDetails.getLastName(), foundUserProfile.getLastName());
        assertEquals(newUserProfileDetails.getEmail(), foundUserProfile.getEmail());
    }

    //PR-UN-046
    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateWithNonExistentUser()
            throws InstanceNotFoundException {
    	//Llamada
    	userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID, new UserProfileDetails("name", "lastName", "user@udc.es"));
    }

    //PR-UN-047
    @Test
    public void testChangePassword() throws InstanceNotFoundException,
            IncorrectPasswordException, DuplicateInstanceException {
        //Setup
        String newClearPassword = 'X' + clearPassword;
      
        //Llamada
        userService.changePassword(userProfile.getUserProfileId(), clearPassword, newClearPassword);
        
        //Aserción
        UserProfile loggedUserProfile= userService.login(loginName, newClearPassword, false);
        assertEquals(userProfile, loggedUserProfile);
    }
  
    //PR-UN-048
    @Test(expected = IncorrectPasswordException.class)
    public void testChangePasswordWithIncorrectPassword()
            throws InstanceNotFoundException, IncorrectPasswordException {
    	//Llamada
    	userService.changePassword(userProfile.getUserProfileId(), 'X' + clearPassword, 'Y' + clearPassword);
    }

    //PR-UN-049
    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordWithNonExistentUser()
            throws InstanceNotFoundException, IncorrectPasswordException {
    	//Llamada
    	userService.changePassword(NON_EXISTENT_USER_PROFILE_ID, "userPassword", "XuserPassword");
    }
}
