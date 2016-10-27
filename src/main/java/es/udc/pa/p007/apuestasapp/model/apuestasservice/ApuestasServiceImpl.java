package es.udc.pa.p007.apuestasapp.model.apuestasservice;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.apuesta.ApuestaDao;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.categoria.CategoriaDao;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.evento.EventoDao;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDao;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDto;
import es.udc.pa.p007.apuestasapp.model.userprofile.UserProfile;
import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("apuestasService")
@Transactional
public class ApuestasServiceImpl implements ApuestasService {

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private ApuestaDao apuestaDao;

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private TipoApuestaDao tipoApuestaDao;

	@Autowired
	private OpcionApuestaDao opcionApuestaDao;

	@Autowired
	private UserService userService;

	public Evento createEvento(String nombre, Calendar fecha, Long categoria)
			throws InputValidationException, InstanceNotFoundException {
		PropertyValidator.validateString("nombre", nombre);
		PropertyValidator.validateFutureDate("fecha", fecha);
		Categoria cat = categoriaDao.find(categoria);
		Evento evento = new Evento(nombre, fecha, cat);
		eventoDao.save(evento);
		return evento;
	}

	@Transactional(readOnly = true)
	public EventoBlock findEventos(String nombre, Long codCategoria,
			boolean admin, int startIndex, int count)
			throws InstanceNotFoundException {

		if (codCategoria != null)
			categoriaDao.find(codCategoria);

		List<Evento> eventos = eventoDao.findEventosAbiertos(nombre,
				codCategoria, admin, startIndex, count + 1);

		boolean existsMoreEventos = eventos.size() == (count + 1);

		if (existsMoreEventos) {
			eventos.remove(eventos.size() - 1);
		}

		return new EventoBlock(eventos, existsMoreEventos);

	}

	public Apuesta createApuesta(Long opcion, double cantidad, Long userId)
			throws InputValidationException, InstanceNotFoundException, StartedEventException {
		
		PropertyValidator.validateDoublePositive("cantidad", cantidad);

		OpcionApuesta op = opcionApuestaDao.find(opcion);
		if (op.getTipoApuesta().getEvento().getFecha().before(Calendar.getInstance()))
			throw new StartedEventException("El evento ya ha comenzado");
		
		UserProfile user = userService.findUserProfile(userId);
		Calendar fecha = Calendar.getInstance();

		Apuesta apuesta = new Apuesta(op, cantidad, user, fecha);

		apuestaDao.save(apuesta);
		return apuesta;
	}

	public TipoApuesta createTipoApuesta(TipoApuesta tipoApuesta,
			List<OpcionApuesta> opciones, Long eventId)
			throws StartedEventException, InputValidationException,
			InstanceNotFoundException {

		PropertyValidator.validateString("pregunta", tipoApuesta.getPregunta());

		if (opciones.isEmpty())
			throw new InputValidationException("No hay opciones");
		
		Evento evento = eventoDao.find(eventId);

		tipoApuesta.setEvento(evento);

		if (tipoApuesta.getEvento().getFecha().after(Calendar.getInstance())) {
			tipoApuestaDao.save(tipoApuesta);
		} else
			throw new StartedEventException("El evento ya ha comenzado");
		for (OpcionApuesta o : opciones) {
			tipoApuesta.addOpciones(o);
			opcionApuestaDao.save(o);
		}

		return tipoApuesta;
	}

	@Transactional(readOnly = true)
	public ApuestaBlock findApuestasByUserId(Long userId, int startIndex,
			int count){
		
		/*
		 * Find count+1 bets to determine if there exist more bets above
		 * the specified range
		 */
		
		List<Apuesta> apuestas = apuestaDao.findByUserId(userId, startIndex,
				count + 1);

		boolean existsMoreApuestas = apuestas.size() == (count + 1);

		/*
		 * Remove the last bet from the returned list if there exist more bets
		 * above the specified range.
		 */
		if (existsMoreApuestas) {
			apuestas.remove(apuestas.size() - 1);
		}

		/* Return ApuestaBlock. */
		return new ApuestaBlock(apuestas, existsMoreApuestas);
		
	}

	@Transactional(readOnly = true)
	public TipoApuestaDto getTiposApuesta(Long codEvento)
			throws InstanceNotFoundException {

		eventoDao.find(codEvento);

		List<TipoApuesta> tiposApuesta = tipoApuestaDao
				.getTiposApuesta(codEvento);

		return new TipoApuestaDto(tiposApuesta, codEvento);

	}

	public List<OpcionApuesta> marcarOpcionesGanadoras(Long codTipoApuesta,
			List<Long> opcionesGanadoras) throws InstanceNotFoundException,
			NotStartedEventException, ValidateOptionsException {

		TipoApuesta tipoApuesta = tipoApuestaDao.find(codTipoApuesta);

		if (tipoApuesta.getEvento().getFecha().after(Calendar.getInstance()))
			throw new NotStartedEventException("El evento aún no ha comenzado");

		if (opcionesGanadoras.size() == 0)
			throw new ValidateOptionsException(
					"Ninguna opción ganadora seleccionada");

		OpcionApuesta opcion;
		for (Long cod : opcionesGanadoras) {
			opcion = opcionApuestaDao.find(cod);
			if (opcion.getTipoApuesta().getCodTipoApuesta().equals(codTipoApuesta))
				throw new ValidateOptionsException(
						"No todas las opciones ganadoras se corresponden con el tipo de apuesta");
		}

		if (opcionApuestaDao.find(opcionesGanadoras.get(0)).isGanadora() != null)
			throw new ValidateOptionsException("Opciones ya validadas");

		if (!tipoApuesta.isMultiple() && opcionesGanadoras.size() != 1)
			throw new ValidateOptionsException(
					"Tipo de apuesta no múltiple y más de una opción ganadora seleccionada");

		opcionApuestaDao.validarOpciones(opcionesGanadoras);
		opcionApuestaDao.invalidarOpcionesPendientes(codTipoApuesta);

		return tipoApuesta.getOpciones();
	}

	@Transactional(readOnly = true)
	public boolean isOpcionesApuestaValidated(Long codTipoApuesta)
			throws InstanceNotFoundException {

		tipoApuestaDao.find(codTipoApuesta);

		return opcionApuestaDao.isOpcionesApuestaValidated(codTipoApuesta);
	}

	@Transactional(readOnly = true)
	public List<Categoria> getCategorias() {

		return categoriaDao.findCategorias();
	}

	@Transactional(readOnly = true)
	public Evento findEvento(Long codEvento) throws InstanceNotFoundException {

		return eventoDao.find(codEvento);
	}

	@Transactional(readOnly = true)
	public Categoria findCategoria(Long codCategoria)
			throws InstanceNotFoundException {

		return categoriaDao.find(codCategoria);
	}

	@Transactional(readOnly = true)
	public TipoApuesta findTipoApuesta(Long codTipoApuesta)
			throws InstanceNotFoundException {

		return tipoApuestaDao.find(codTipoApuesta);
	}

	@Transactional(readOnly = true)
	public OpcionApuesta findOpcionApuesta(Long codOpcionApuesta)
			throws InstanceNotFoundException {

		return opcionApuestaDao.find(codOpcionApuesta);
	}

	@Transactional(readOnly = true)
	public Apuesta findApuesta(Long codApuesta)
			throws InstanceNotFoundException {

		return apuestaDao.find(codApuesta);
	}

}
