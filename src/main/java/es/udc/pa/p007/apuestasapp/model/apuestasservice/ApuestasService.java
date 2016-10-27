package es.udc.pa.p007.apuestasapp.model.apuestasservice;

import java.util.Calendar;
import java.util.List;

import es.udc.pa.p007.apuestasapp.model.apuesta.Apuesta;
import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
import es.udc.pa.p007.apuestasapp.model.evento.Evento;
import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuesta;
import es.udc.pa.p007.apuestasapp.model.tipoApuesta.TipoApuestaDto;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface ApuestasService {

	/**
	 * Save a Event in the database
	 * 
	 * @param nombre
	 *            the name of the event
	 * @param fecha
	 *            the date of the event
	 * @param categoria
	 *            event category id
	 * @return the created event
	 * @throws InputValidationException
	 * @throws InstanceNotFoundException
	 */
	public Evento createEvento(String nombre, Calendar fecha, Long categoria)
			throws InputValidationException, InstanceNotFoundException;

	/**
	 * Returns a block of events (It depends on the type of user)
	 * 
	 * @param nombre
	 *            the name
	 * @param codCategoria
	 *            category id
	 * @param admin
	 *            boolean indicating is an administrator or an user
	 * @param startIndex
	 *            the index (starting from 0) of the first event to return
	 * @param count
	 *            the maximum number of events to return
	 * @return a block of events
	 * @throws InstanceNotFoundException
	 */
	public EventoBlock findEventos(String nombre, Long codCategoria,
			boolean admin, int startIndex, int count)
			throws InstanceNotFoundException;

	/**
	 * Returns a block of BetType of an event
	 * 
	 * @param codEvento
	 *            the event identifier
	 * @param startIndex
	 *            the index (starting from 0) of the first tipoApuesta to return
	 * @param count
	 *            the maximum number of events to return
	 * @return a block of tipoApuesta
	 * @throws InstanceNotFoundException
	 */
	public TipoApuestaDto getTiposApuesta(Long codEvento)
			throws InstanceNotFoundException;

	/**
	 * Save a bet
	 * 
	 * @param apuesta
	 *            Bet to save
	 * @return the bet
	 * @throws InputValidationException
	 * @throws InstanceNotFoundException
	 * @throws StartedEventException 
	 */
	public Apuesta createApuesta(Long opcion, double cantidad, Long userId)
			throws InputValidationException, InstanceNotFoundException, StartedEventException;

	/**
	 * Saves a bet type
	 * 
	 * @param tipoApuesta
	 *            the bet type
	 * @param opciones
	 *            set of bet options
	 * @param eventId
	 *            the event for the bet type
	 * @return the created bet type
	 * @throws StartedEventException
	 * @throws InputValidationException
	 * @throws InstanceNotFoundException
	 */
	public TipoApuesta createTipoApuesta(TipoApuesta tipoApuesta,
			List<OpcionApuesta> opciones, Long eventId)
			throws StartedEventException, InputValidationException,
			InstanceNotFoundException;

	/**
	 * Returns the bets placed by the user
	 * 
	 * @param user
	 *            User profile
	 * @param startIndex
	 *            the index (starting from 0) of the first bet to return
	 * @param count
	 *            the maximum number of bets to return
	 * @return the block of bets
	 * @throws InstanceNotFoundException 
	 */
	public ApuestaBlock findApuestasByUserId(Long userId, int startIndex,
			int count) throws InstanceNotFoundException;

	/**
	 * Mark the winning and the losers options
	 * 
	 * @param codTipoApuesta
	 *            the type of the bet
	 * @param opcionesGanadoras
	 *            the winner options
	 * @return a Dto with the bet type
	 * @throws InstanceNotFoundException
	 * @throws NotStartedEventException
	 * @throws ValidateOptionsException
	 */
	public List<OpcionApuesta> marcarOpcionesGanadoras(Long codTipoApuesta,
			List<Long> opcionesGanadoras) throws InstanceNotFoundException,
			NotStartedEventException, ValidateOptionsException;

	/**
	 * Return a Dto with the bet types
	 * 
	 * @param codTipoApuesta
	 *            the bet type id
	 * @return a Dto withe the bet types
	 * @throws InstanceNotFoundException
	 */
	public boolean isOpcionesApuestaValidated(Long codTipoApuesta)
			throws InstanceNotFoundException;

	/**
	 * Gives the name of all categories
	 * 
	 * @return a list of categories
	 */
	public List<Categoria> getCategorias();

	/**
	 * Finds a event
	 * 
	 * @param codEvento
	 *            event id
	 * @return the found event
	 * @throws InstanceNotFoundException
	 */
	public Evento findEvento(Long codEvento) throws InstanceNotFoundException;

	/**
	 * Finds a category
	 * 
	 * @param codCategoria
	 *            the category id
	 * @return the founded category
	 * @throws InstanceNotFoundException
	 */
	public Categoria findCategoria(Long codCategoria)
			throws InstanceNotFoundException;

	/**
	 * Finds a bet type
	 * 
	 * @param codTipoApuesta
	 *            bet type id
	 * @return the founded bet type
	 * @throws InstanceNotFoundException
	 */
	public TipoApuesta findTipoApuesta(Long codTipoApuesta)
			throws InstanceNotFoundException;

	/**
	 * Finds a bet option
	 * 
	 * @param codOpcionApuesta
	 *            bet option id
	 * @return the founded bet option
	 * @throws InstanceNotFoundException
	 */
	public OpcionApuesta findOpcionApuesta(Long codOpcionApuesta)
			throws InstanceNotFoundException;

	/**
	 * Finds a bet
	 * 
	 * @param codApuesta
	 *            bet id
	 * @return the founded bet
	 * @throws InstanceNotFoundException
	 */
	public Apuesta findApuesta(Long codApuesta)
			throws InstanceNotFoundException;
}