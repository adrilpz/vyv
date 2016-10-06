package es.udc.pa.p007.apuestasapp.web.pages.apuestas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.NotStartedEventException;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.ValidateOptionsException;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class MarcarGanadoras {

	private Long codTipoApuesta;
	
	@Property
	private TipoApuesta tipoApuesta;

	@Inject
	private ApuestasService apuestasService;

	@Inject
	private Locale locale;

	@InjectPage
	ValidatedOptions validatedOptions;

	@Component
	private Form MarcarGanadorasForm;

	@Inject
	private Messages messages;
	
	@Property
	private List<Long> selectedOpcionId;

	@Inject
	SelectModelFactory selectModelFactory;

	public SelectModel getSelectedModel() {
		// invoke my service to find all categories, e.g. in the database
		List<OpcionApuesta> opciones;
		try {
			opciones = apuestasService.findTipoApuesta(
					codTipoApuesta).getOpciones();
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException();
		}
		
		List<OptionModel> opciones1 = new ArrayList<OptionModel>();
		
		for(OpcionApuesta option : opciones) {
			opciones1.add(new OptionModelImpl(option.getResultado(), option.getCodOpcionApuesta()));
		}
		SelectModel model = new SelectModelImpl(null, opciones1);
		
		return model;
		

	}

	public ValueEncoder<Long> getOpcionEncoder() {
		 
	    return new ValueEncoder<Long>() {
	 
	        @Override
	        public String toClient(Long value) {
	            // return the given object's ID
	            return String.valueOf(value); 
	        }
	 
	        @Override
	        public Long toValue(String id) { 
	            // find the color object of the given ID in the database
	        	Long opcion = null;
	        	try {
					opcion = Long.parseLong(id);
				} catch (NumberFormatException e) {
					throw new RuntimeException();
				}
	        	return opcion;
	        }
	    };
	}
	
	void onActivate(Long codTipoApuesta) {
		
		this.codTipoApuesta = codTipoApuesta;
		
		try {
			this.tipoApuesta= apuestasService.findTipoApuesta(codTipoApuesta);
		} catch (InstanceNotFoundException e1) {
			throw new RuntimeException();
		}
		
	}

	void onValidateFromMarcarGanadorasForm() {

		try {
			apuestasService.marcarOpcionesGanadoras(
					codTipoApuesta, selectedOpcionId);
		} catch (InstanceNotFoundException e) {
			MarcarGanadorasForm.recordError(messages.format(
					"error-betTypeNotFound", codTipoApuesta));
		} catch (NotStartedEventException e) {
			MarcarGanadorasForm.recordError(messages
					.format("error-notStartedEvent"));
		} catch (ValidateOptionsException e) {
			if (selectedOpcionId.size() == 0)
				MarcarGanadorasForm.recordError(messages
						.format("error-notSelectedOptions"));
			else if (!tipoApuesta.isMultiple() && selectedOpcionId.size() > 1)
				MarcarGanadorasForm.recordError(messages
						.format("error-notMultipleBetType"));
			else
				MarcarGanadorasForm.recordError(messages
						.format("error-wrongMarkOptions"));
		}
	}

	Object onSuccessFromMarcarGanadorasForm() {

		return validatedOptions;
	}

	Long onPassivate() {
		return codTipoApuesta;
	}

}
