package es.udc.pa.p007.apuestasapp.web.pages.eventos;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class CreateEvento {

	@Property
	private String nombre;

	@Property
	private String fechaEvento;

	@Property
	private String horaEvento;

	@Property
	private Long selectedCategoriaId;

	@Property
	private SelectModel categoriaSelectModel;

	@Inject
	SelectModelFactory selectModelFactory;

	private Date fechaEventoAsDate;

	@Component(id = "fechaEvento")
	private TextField fechaEventoField;

	@Component(id = "horaEvento")
	private TextField horaEventoField;

	@Component
	private Form createEventoForm;

	@Inject
	private Messages messages;

	@Inject
	private Locale locale;

	@Inject
	private ApuestasService apuestasService;

	@InjectPage
	private CreatedEvento eventoCreated;

	void setupRender() {
		// invoke my service to find all categories, e.g. in the database
		List<Categoria> categorias = apuestasService.getCategorias();

		// create a SelectModel from my list of categories
		categoriaSelectModel = selectModelFactory.create(categorias, "nombre");
	}

	public ValueEncoder<Categoria> getCategoriaEncoder() {

		return new ValueEncoder<Categoria>() {

			@Override
			public String toClient(Categoria value) {
				// return the given object's ID
				return String.valueOf(value.getCodCategoria());
			}

			@Override
			public Categoria toValue(String id) {
				// find the color object of the given ID in the database
				Categoria categoria = null;
				try {
					categoria = apuestasService.findCategoria(Long
							.parseLong(id));
				} catch (NumberFormatException e) {
				} catch (InstanceNotFoundException e) {
				}
				return categoria;
			}
		};
	}

	void onValidateFromCreateEventoForm() {

		if (!createEventoForm.isValid()) {
			return;
		}

		fechaEventoAsDate = validateDate(fechaEventoField, fechaEvento,
				horaEventoField, horaEvento);

		if (fechaEventoAsDate!=null){
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(this.fechaEventoAsDate);

			Evento evento = new Evento();
	
			try {
				evento = apuestasService.createEvento(nombre, fecha,
						selectedCategoriaId);
			} catch (InstanceNotFoundException e) {
				createEventoForm.recordError(messages.format(
						"error-categoryNotFound", selectedCategoriaId));
			} catch (InputValidationException e) {
				createEventoForm.recordError(messages
						.format("error-inputValidation"));
	
			}
	
		}
		
	}

	Object onSuccess() {
		return eventoCreated;

	}

	void onActivate() {
		fechaEvento = dateToString(Calendar.getInstance().getTime());
		horaEvento = hourToString(Calendar.getInstance().getTime());
	}

	private Date validateDate(TextField textField, String dateAsString,
			TextField horaField, String horaAsString) {
		
		boolean error = false;
		ParsePosition position = new ParsePosition(0);

		Date date = DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(
				dateAsString, position);

		if (position.getIndex() != dateAsString.length()) {
			error = true;
			createEventoForm.recordError(textField,
					messages.format("error-incorrectDateFormat", dateAsString));
			
		}

		position.setIndex(0);
		date= DateFormat.getTimeInstance(DateFormat.SHORT, locale).
			parse(horaAsString, position);

		if (position.getIndex() != horaAsString.length()) {
			error = true;
			createEventoForm.recordError(horaField,
					messages.format("error-incorrectHourFormat", horaAsString));
			
		}

		position.setIndex(0);
		String fecha = dateAsString.concat(" ").concat(horaAsString);
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.SHORT, locale).parse(fecha, position);
		
		if (!error) {
			if (date.before(Calendar.getInstance().getTime())) {
				createEventoForm.recordError(messages.format(
						"error-dateMustBeAfterNow", date));
			}
			Calendar fecha1 = Calendar.getInstance();
			fecha1.setTime(date);
			if (fecha1.get(Calendar.YEAR) > Calendar.getInstance().get(
					Calendar.YEAR) + 5)
				createEventoForm.recordError(messages.format(
						"error-eventMustBeWithinFiveYears", date));

		}

		return date;
	}

	private String dateToString(Date date) {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale)
				.format(date);
	}

	private String hourToString(Date date) {
		return DateFormat.getTimeInstance(DateFormat.SHORT, locale)
				.format(date);
	}
}
