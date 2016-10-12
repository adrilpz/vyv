package es.udc.pa.p007.apuestasapp.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userservice.IncorrectPasswordException;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
import es.udc.pa.p007.apuestasapp.web.pages.Index;
import es.udc.pa.p007.apuestasapp.web.pages.apuestas.Apostar;
import es.udc.pa.p007.apuestasapp.web.pages.search.DetallesEvento;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.CookiesManager;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private boolean rememberMyPassword;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private Cookies cookies;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Inject
    private UserService userService;

    private UserProfile userProfile = null;
    
    private Long codOpcion;
    
    @InjectPage
    private DetallesEvento detalles;
    
    @InjectPage
    private Apostar apostar;
    

	public Long getCodOpcion() {
		return codOpcion;
	}

	public void setCodOpcion(Long codOpcion) {
		this.codOpcion = codOpcion;
	}

	void onValidateFromLoginForm() {

        if (!loginForm.isValid()) {
            return;
        }

        try {
            userProfile = userService.login(loginName, password, false);
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }

    }

    Object onSuccess() {
    	userSession = new UserSession();
        userSession.setUserProfileId(userProfile.getUserProfileId());
        userSession.setFirstName(userProfile.getFirstName());
        if (userSession.getFirstName().equals("admin"))
        	userSession.setAdmin(true);
        else
        	userSession.setAdmin(false);
        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, loginName, userProfile
                    .getEncryptedPassword());
        }
        
        if (codOpcion != null && !userSession.isAdmin()){
        	//detalles.setCodEvento(codEvento);
        	apostar.setCodOpcion(codOpcion);
        	this.codOpcion=null;
        	return apostar;
        }
        return Index.class;

    }
    
    void onActivate(Long codOpcion){
    	this.codOpcion=codOpcion;
    }
    
	Long onPassivate() {
		return codOpcion;
	}
    
    

}
