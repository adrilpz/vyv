package es.udc.pa.p007.apuestasapp.web.pages.apuestas;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.StartedEventException;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class CreateTipoApuesta {
	
	@Property
	Long codEvento;

	private Evento evento;

	private boolean mul;

	public enum Multiple {
		YES, NO
	};

	@Property
	private String pregunta;

	@Property
	private Multiple multiple;

	@Component
	private Form CreateTipoApuestaForm;
	
	@Component
	private Form CreateOpcionesForm;
	
	@Inject
	private ApuestasService apuestasService;

	@Property
	private TipoApuesta tipoApuesta = new TipoApuesta();

	@Property
	private OpcionApuesta opcion;

	@InjectPage
	private CreatedTipoApuesta createdTipoApuesta;

	@Property
	String resultado;

	@Property
	String cuota;

	double cuotaAsDouble;

	@Component(id = "cuota")
	private TextField cuotaTextField;

	@Inject
	private Locale locale;

	@Inject
	private Messages messages;

	@Inject
	private Request request;

	@Property
	@SessionState(create = false)
	private UserSession userSession;
	 
	@InjectComponent
	private Zone myZone;
	

	public Evento getEvento() {

		Evento evento = null;
		try {
			evento = apuestasService.findEvento(userSession.getCodEvento());
		} catch (InstanceNotFoundException e) {
		}
		return evento;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}
	
	public Format getNumberFormat() {
		return NumberFormat.getInstance(locale);
	}

	void onValidateFromCreateOpcionesForm() {
		
		if (!CreateOpcionesForm.isValid()) {
			return;
		}
		
		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(cuota, position);

		if (position.getIndex() != cuota.length()) {
			CreateOpcionesForm.recordError(cuotaTextField,
					messages.format("error-incorrectNumberFormat", cuota));
		} else {
			cuotaAsDouble = number.doubleValue();
		}

	}
	
	Object onFailureFromCreateOpcionesForm(){
		return myZone.getBody();
	}

	Object onSuccessFromCreateOpcionesForm() {
		OpcionApuesta newOpcion = new OpcionApuesta(resultado, cuotaAsDouble,
				null);

		List<OpcionApuesta> opcs = userSession.getOpciones();
		opcs.add(newOpcion);
		userSession.setOpciones(opcs);
		
		this.resultado=null;
		this.cuota=null;
		
		return myZone.getBody();

	}

	void onValidateFromCreateTipoApuestaForm() {
		
		if (!CreateTipoApuestaForm.isValid()) {
			return;
		}
			mul = multiple == Multiple.YES;
	
			tipoApuesta = new TipoApuesta();
			tipoApuesta.setMultiple(mul);
			tipoApuesta.setPregunta(pregunta);
	
			try {
				apuestasService.createTipoApuesta(tipoApuesta, userSession.getOpciones(),
								userSession.getCodEvento()).getCodTipoApuesta();
			} catch (InstanceNotFoundException e) {
				CreateTipoApuestaForm.recordError(messages.format(
						"error-eventNotFound", userSession.getCodEvento()));
			} catch (StartedEventException e) {
				CreateTipoApuestaForm.recordError(messages
						.format("error-eventStarted"));
			} catch (InputValidationException e) {
				if (userSession.getOpciones().size() == 0)
					CreateTipoApuestaForm.recordError(messages
							.format("error-notSelectedOptions"));
				else
					CreateTipoApuestaForm.recordError(messages
							.format("error-inputValidation"));
			}
	
			userSession.setOpciones(null);
			userSession.setOpciones(new ArrayList<OpcionApuesta>());
			
	}

	Object onSuccessFromCreateTipoApuestaForm() {
			return createdTipoApuesta;
	}
	

	void onActivate(Long codEvento) {
		
		this.codEvento= codEvento;

		if (userSession.getCodEvento() == null)
			userSession.setCodEvento(codEvento);
		else if (userSession.getCodEvento() != codEvento) {
			userSession.setOpciones(null);
			userSession.setOpciones(new ArrayList<OpcionApuesta>());
			userSession.setCodEvento(codEvento);
		}
		
	}
	
	Long onPasivate(){
		return codEvento;
	}

}