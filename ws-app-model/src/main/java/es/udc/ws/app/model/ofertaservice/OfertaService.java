package es.udc.ws.app.model.ofertaservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface OfertaService {
	public Oferta crearOferta(Oferta o) throws InputValidationException;
	public void actualizarOferta(Oferta o) throws InputValidationException,
    	InstanceNotFoundException, InvalidOfferUpdateException;
	public void borrarOferta(Long codOferta) throws InstanceNotFoundException, InvalidOfferDeleteException;
	public void invalidarOferta(Long codOferta) throws InstanceNotFoundException, InputValidationException, InvalidOfferUpdateException;
	public Oferta buscarOfertaID(Long codOferta) throws InstanceNotFoundException;
	public List<Oferta> buscarOfertas(String palabrasClave, boolean todas, Calendar fecha);
	public Long hacerReserva(String email,String tarjetaCredito, Long codOferta) 
			throws InstanceNotFoundException, InputValidationException, InvalidOfferReservationException;;
	public List<Reserva> obtenerReservas(Long codOferta)  throws InstanceNotFoundException;
	public List<Reserva> obtenerReservasUsuario(String email, boolean todas);
	public void reclamarReserva(Long codReserva) throws InstanceNotFoundException, InvalidOfferReclamationException;
	void borrarReserva(Long codReserva) throws InstanceNotFoundException;
	Reserva buscarReserva(Long codReserva) throws InstanceNotFoundException;
}
