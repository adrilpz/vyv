package es.udc.pa.p007.apuestasapp.web.pages.apuestas;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class CreatedApuesta {
	

}
