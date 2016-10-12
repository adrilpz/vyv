package es.udc.ws.app.client.service;

import java.util.List;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientOfertaService {
	public OfertaDto crearOferta(OfertaDto o) throws InputValidationException;
	public void actualizarOferta(OfertaDto o) throws InputValidationException,
    	InstanceNotFoundException, InvalidOfferUpdateException;
	public void borrarOferta(Long codOferta) throws InstanceNotFoundException, InvalidOfferDeleteException;
	public void invalidarOferta(Long codOferta) throws InstanceNotFoundException, InputValidationException, InvalidOfferUpdateException;
	public OfertaDto buscarOfertaID(Long codOferta) throws InstanceNotFoundException;
	public List<OfertaDto> buscarOfertas(String palabrasClave);
	public Long hacerReserva(String email,String tarjetaCredito, Long codOferta) 
			throws InstanceNotFoundException, InputValidationException, InvalidOfferReservationException;;
	public List<ReservaDto> obtenerReservas(Long codOferta)  throws InstanceNotFoundException;
	public List<ReservaDto> obtenerReservasUsuario(String email, boolean todas);
	public void reclamarReserva(Long codReserva) throws InstanceNotFoundException, InvalidOfferReclamationException;
	public List<OfertaDtoUsuario> buscarOfertasUsuario(String email) throws InstanceNotFoundException;
}
