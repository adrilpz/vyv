package es.udc.pa.p007.apuestasapp.web.pages.search;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.EventoBlock;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class Eventos {

	private final static int EVENTOS_PER_PAGE = 10;

	private String palabrasClave;
	private Long codCategoria;
	private int startIndex = 0;
	private EventoBlock eventoBlock;
	private Evento evento;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	private boolean adminSession;

	@Inject
	private ApuestasService apuestasService;

	@Inject
	private Locale locale;

	public String getPalabrasClave() {
		return palabrasClave;
	}

	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public Long getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}

	public List<Evento> getEventos() {
		return eventoBlock.getEventos();
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public boolean getAdminSession() {

		if (userSession.isAdmin()) {
			return true;
		} else {
			return adminSession;
		}
	}

	public boolean getEventoPosterior() {

		if (evento.getFecha().after(Calendar.getInstance()))
			return true;
		else
			return false;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}

	public Object[] getPreviousLinkContext() {

		if (startIndex - EVENTOS_PER_PAGE >= 0) {
			return new Object[] { palabrasClave, codCategoria,
					startIndex - EVENTOS_PER_PAGE };
		} else {
			return null;
		}

	}

	public Object[] getNextLinkContext() {

		if (eventoBlock.getExistsMoreEventos()) {
			return new Object[] { palabrasClave, codCategoria,
					startIndex + EVENTOS_PER_PAGE };
		} else {
			return null;
		}

	}

	Object[] onPassivate() {
		return new Object[] { palabrasClave, codCategoria, startIndex };
	}

	void onActivate(String palabrasClave, Long codCategoria, int startIndex) {
		this.palabrasClave = palabrasClave;
		this.codCategoria = codCategoria;
		this.startIndex = startIndex;
		boolean admin = false;
		if (userSession != null)
			admin = userSession.isAdmin();
		try {
			eventoBlock = apuestasService.findEventos(palabrasClave,
					codCategoria, admin, startIndex, EVENTOS_PER_PAGE);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException();
		}
	}

}