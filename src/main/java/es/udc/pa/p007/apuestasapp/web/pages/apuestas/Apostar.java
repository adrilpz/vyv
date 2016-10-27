package es.udc.pa.p007.apuestasapp.web.pages.apuestas;

import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.StartedEventException;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class Apostar {

	private OpcionApuesta opcion;
	
	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Property
	private String cantidad;

	private double cantidadAsDouble;

	@Component(id = "cantidad")
	private TextField cantidadTextField;

	@Component
	private Form ApostarForm;

	@Inject
	private Messages messages;

	@Inject
	private ApuestasService apuestasService;

	@Inject
	private Locale locale;

	@InjectPage
	private CreatedApuesta createdApuesta;

	private Long codOpcion;

		
	public Long getCodOpcion() {
		return codOpcion;
	}

	public void setCodOpcion(Long codOpcion) {
		this.codOpcion = codOpcion;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}
	
	public OpcionApuesta getOpcion(){
		
		OpcionApuesta opcion = null;
		try {
			opcion =  apuestasService.findOpcionApuesta(userSession.getCodOpcion());
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException();
		}
		
		return opcion;
	}
	
	
	void onValidateFromApostarForm() {

		if (!ApostarForm.isValid()) {
			return;
		}

		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(cantidad, position);

		if (position.getIndex() != cantidad.length()) {
			ApostarForm.recordError(cantidadTextField,
					messages.format("error-incorrectNumberFormat", cantidad));
		} else {
			cantidadAsDouble = number.doubleValue();
		}

		Apuesta apuesta = new Apuesta();

		try {
			apuesta = apuestasService.createApuesta(userSession.getCodOpcion(),
					cantidadAsDouble, userSession.getUserProfileId());
		} catch (StartedEventException e) {
			ApostarForm.recordError(messages.format("error-eventStarted"));
		} catch (InstanceNotFoundException e) {
			ApostarForm.recordError(messages.format("error-opcionNotFound",
					userSession.getCodOpcion()));
		} catch (InputValidationException e) {
			ApostarForm.recordError(messages.format("error-inputValidation"));
		}

	}

	Object onSuccess() {

		return createdApuesta;

	}

	void onActivate(Long codOpcion) {
		if (userSession.getCodOpcion() == null)
			userSession.setCodOpcion(codOpcion);
		else if (userSession.getCodOpcion().equals(codOpcion)) {
			userSession.setCodOpcion(codOpcion);
		}
	}
	
	Long onPassivate() {
		return codOpcion;
	}
}
