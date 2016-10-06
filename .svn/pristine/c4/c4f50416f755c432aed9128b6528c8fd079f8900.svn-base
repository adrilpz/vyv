package es.udc.pa.p007.apuestasapp.web.pages.search;

import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestaBlock;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class Apuestas {

	private final static int APUESTAS_PER_PAGE = 10;

	private int startIndex = 0;
	private ApuestaBlock apuestaBlock;
	private Apuesta apuesta;
	private OpcionApuesta opcion;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private ApuestasService apuestasService;

	@Inject
	private Locale locale;

	@Inject
	private Block case1, case2, case3;

	public boolean getOpcionGanadora() {
		if (opcion.isGanadora() != null && opcion.isGanadora()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getGanadora() {
		if (apuesta.getOpcion().isGanadora() != null
				&& apuesta.getOpcion().isGanadora()) {
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

	public boolean getPerdedora() {
		if (apuesta.getOpcion().isGanadora() != null
				&& !apuesta.getOpcion().isGanadora()) {
			return true;
		} else {
			return false;
		}
	}

	public double getGanancia() {
		return apuesta.getCantidad() * apuesta.getOpcion().getCuota();
	}

	public List<Apuesta> getApuestas() {
		return apuestaBlock.getApuestas();
	}

	public Apuesta getApuesta() {
		return apuesta;
	}

	public void setApuesta(Apuesta apuesta) {
		this.apuesta = apuesta;
	}

	public OpcionApuesta getOpcion() {
		return opcion;
	}

	public void setOpcion(OpcionApuesta opcion) {
		this.opcion = opcion;
	}

	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}

	public Format getNumberFormat() {
		return NumberFormat.getInstance(locale);
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT, locale);
	}

	public Object[] getPreviousLinkContext() {

		if (startIndex - APUESTAS_PER_PAGE >= 0) {
			return new Object[] { startIndex - APUESTAS_PER_PAGE };
		} else {
			return null;
		}

	}

	public Object[] getNextLinkContext() {
		if (apuestaBlock.getExistsMoreApuestas()) {
			return new Object[] { startIndex + APUESTAS_PER_PAGE };
		} else {
			return null;
		}

	}

	Object[] onPassivate() {
		return new Object[] { startIndex };
	}

	void onActivate(int startIndex) {
		this.startIndex = startIndex;
		// apuestaBlock =
		// apuestasService.findApuestasByUserId(userSession.getUserProfileId(),
		// startIndex, APUESTAS_PER_PAGE);
	}

	void onActivate() {
		try {
			apuestaBlock = apuestasService.findApuestasByUserId(
					userSession.getUserProfileId(), startIndex,
					APUESTAS_PER_PAGE);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException();
		}
	}

}
