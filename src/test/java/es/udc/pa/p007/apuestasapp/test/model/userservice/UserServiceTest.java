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

import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfileDao;
import es.udc.pa.p007.apuestasapp.model.userservice.IncorrectPasswordException;
import es.udc.pa.p007.apuestasapp.model.userservice.UserProfileDetails;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
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
    UserProfile userProfile;
    
    @Before
	public void initialize(){
    	//Creación usuarios
    	userProfile = new UserProfile("user", "userPassword", "name", "lastName", "user@udc.es");
    	userProfileDao.save(userProfile);
    }

    //PR-UN-037
    @Test
    public void testRegisterUser() throws DuplicateInstanceException{
        UserProfile userProfilePR037 = userService.registerUser(
            "prueba", "password", new UserProfileDetails("adrian", "lopez", "adri@udc.es"));
        assertNotNull(userProfilePR037);
		assertEquals(UserProfile.class, userProfilePR037.getClass());
    }
    
    //PR-UN-038
    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicatedUser() throws DuplicateInstanceException{
        String loginName = "user";
        String clearPassword = "userPassword";
        UserProfileDetails userProfileDetails = new UserProfileDetails(
            "name", "lastName", "user@udc.es");
        userService.registerUser(loginName, clearPassword,
            userProfileDetails);
    }
    
    //PR-UN-039
    @Test
    public void FindUserProfile() throws InstanceNotFoundException {
        UserProfile foundUserProfile = userService.findUserProfile(userProfile.getUserProfileId());
        assertEquals(userProfile, foundUserProfile);
    }

    //PR-UN-040
    @Test
    public void testLoginClearPassword() throws IncorrectPasswordException,
        InstanceNotFoundException {
        String clearPassword = "userPassword";
        UserProfile loginUserProfile = userService.login(userProfile.getLoginName(),
            clearPassword, false);
        assertEquals(userProfile, loginUserProfile);
    }

    //PR-UN-041
    @Test
    public void testLoginEncryptedPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {
        UserProfile loginUserProfile = userService.login(userProfile.getLoginName(),
            userProfile.getEncryptedPassword(), true);
        assertEquals(userProfile, loginUserProfile);

    }

    //PR-UN-042
    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPasword() throws IncorrectPasswordException,
            InstanceNotFoundException {
        String clearPassword = "userPassword";
        userService.login(userProfile.getLoginName(), 'X' + clearPassword, false);
    }

    //PR-UN-043
    @Test(expected = InstanceNotFoundException.class)
    public void testLoginWithNonExistentUser()
            throws IncorrectPasswordException, InstanceNotFoundException {
        userService.login("userNonExistent", "userPassword", false);
    }

    //PR-UN-044
    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentUser() throws InstanceNotFoundException {
        userService.findUserProfile(NON_EXISTENT_USER_PROFILE_ID);
    }

    //PR-UN-045
    @Test
    public void testUpdate() throws InstanceNotFoundException,
            IncorrectPasswordException {
        String clearPassword = "userPassword";
        UserProfileDetails newUserProfileDetails = new UserProfileDetails(
            'X' + userProfile.getFirstName(), 'X' + userProfile.getLastName(),
            'X' + userProfile.getEmail());
        userService.updateUserProfileDetails(userProfile.getUserProfileId(),
            newUserProfileDetails);
        userService.login(userProfile.getLoginName(), clearPassword, false);
        UserProfile userProfile2 = userService.findUserProfile(
            userProfile.getUserProfileId());
        assertEquals(newUserProfileDetails.getFirstName(),
            userProfile2.getFirstName());
        assertEquals(newUserProfileDetails.getLastName(),
            userProfile2.getLastName());
        assertEquals(newUserProfileDetails.getEmail(),
            userProfile2.getEmail());
    }

    //PR-UN-046
    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateWithNonExistentUser()
            throws InstanceNotFoundException {
        userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID,
            new UserProfileDetails("name", "lastName", "user@udc.es"));
    }

    //PR-UN-047
    @Test
    public void testChangePassword() throws InstanceNotFoundException,
            IncorrectPasswordException {
        /* Change password. */
        String clearPassword = "userPassword";
        String newClearPassword = 'X' + clearPassword;
        userService.changePassword(userProfile.getUserProfileId(),
            clearPassword, newClearPassword);
        /* Check new password. */
        userService.login(userProfile.getLoginName(), newClearPassword, false);
    }
    
    //PR-UN-048
    @Test(expected = IncorrectPasswordException.class)
    public void testChangePasswordWithIncorrectPassword()
            throws InstanceNotFoundException, IncorrectPasswordException {
        String clearPassword = "userPassword";
        userService.changePassword(userProfile.getUserProfileId(),
            'X' + clearPassword, 'Y' + clearPassword);

    }

    //PR-UN-049
    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordWithNonExistentUser()
            throws InstanceNotFoundException, IncorrectPasswordException {
        userService.changePassword(NON_EXISTENT_USER_PROFILE_ID,
                "userPassword", "XuserPassword");
    }
}
