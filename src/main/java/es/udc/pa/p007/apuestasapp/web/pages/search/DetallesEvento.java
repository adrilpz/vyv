package es.udc.pa.p007.apuestasapp.web.pages.search;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.web.pages.user.Login;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class DetallesEvento {

	private Long codEvento;
	private Evento evento;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	private boolean adminSession;

	@Property
	private List<TipoApuesta> tiposApuesta;

	@Property
	private TipoApuesta tipoApuesta;

	@Property
	private OpcionApuesta opcion = new OpcionApuesta();

	@Inject
	private ApuestasService apuestasService;

	@Inject
	private Locale locale;

	@InjectPage
	private Login login;

	public boolean getOpcionGanadora() {
		if (opcion.isGanadora() != null && opcion.isGanadora()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getOpcionPerdedora() {

		if (opcion.isGanadora() != null && !opcion.isGanadora()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getOpcionesMarcadas() {
		boolean marcadas = false;
		try {
			marcadas = apuestasService.isOpcionesApuestaValidated(tipoApuesta
					.getCodTipoApuesta());
		} catch (InstanceNotFoundException e) {
		}
		if (marcadas)
			return false;
		else
			return true;
	}

	public boolean getEventoAnterior() {

		if (evento.getFecha().after(Calendar.getInstance()))
			return false;
		else
			return true;
	}

	public boolean getAdminSession() {

		if (userSession.isAdmin()) {
			return true;
		} else {
			return adminSession;
		}
	}

	public void setCodEvento(Long codEvento) {
		this.codEvento = codEvento;
	}

	public Evento getEvento() {
		return evento;
	}
	
	public Format getNumberFormat() {
		return NumberFormat.getInstance(locale);
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}

	void onActivate(Long codEvento) {

		this.codEvento = codEvento;

		try {
			evento = apuestasService.findEvento(codEvento);

			this.tiposApuesta = apuestasService.getTiposApuesta(
					evento.getCodEvento()).getTiposApuesta();

		} catch (InstanceNotFoundException e) {
			throw new RuntimeException();
		}

	}

	Long onPassivate() {
		return codEvento;
	}

}
