package es.udc.pa.p007.apuestasapp.web.pages.search;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
import es.udc.pa.p007.apuestasapp.model.apuestasservice.EventoBlock;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicy;
import es.udc.pa.p007.apuestasapp.web.services.AuthenticationPolicyType;
import es.udc.pa.p007.apuestasapp.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class BuscarEventos {

	@Property
	private String palabrasClave;

	@Property
	private Long selectedCategoriaId;

	@Property
	private SelectModel categoriaSelectModel;

	@Component
	private Form buscarEventosForm;

	@Inject
	SelectModelFactory selectModelFactory;

	@Inject
	private ApuestasService apuestasService;

	@InjectPage
	private Eventos eventos;
	
	@Property
	@SessionState(create = false)
	private UserSession userSession;

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
					throw new RuntimeException();
				} catch (InstanceNotFoundException e) {
					throw new RuntimeException();
				}
				return categoria;
			}
		};
	}
	
	List<String> onProvideCompletionsFromPalabrasClave(String partial){
		
		boolean admin = false;
		if (userSession != null)
			admin = userSession.isAdmin();
		
		List<Evento> eventos= new ArrayList<Evento>();
		try {
			eventos= apuestasService.findEventos(partial ,null, admin, 0, 10).getEventos();
		} catch (InstanceNotFoundException e) {
			return new ArrayList<String>();
		}
		
		List<String> results = new ArrayList<String>();
		
		for (Evento e: eventos){
			results.add(e.getNombre());
		}
		
		return results;
		
	}

	Object onSuccess() {

		eventos.setPalabrasClave(palabrasClave);
		eventos.setCodCategoria(selectedCategoriaId);
		return eventos;

	}

}
