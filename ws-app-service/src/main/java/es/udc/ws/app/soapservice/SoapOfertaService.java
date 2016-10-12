package es.udc.ws.app.soapservice;


import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoUsuarioConversor;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
    name="OfertasProvider",
    serviceName="OfertasProviderService",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapOfertaService {

    @WebMethod(
        operationName="crearOferta"
    )
    public OfertaDto crearOferta(@WebParam(name="ofertaDto") OfertaDto ofertaDto)
            throws SoapInputValidationException {
    	Oferta o = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
        try {
        	Oferta ofertaCreada =OfertaServiceFactory.getService().crearOferta(o);
            return OfertaToOfertaDtoConversor.toOfertaDto(ofertaCreada);
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }
    }

    @WebMethod(
        operationName="actualizarOferta"
    )
    public void actualizarOferta(@WebParam(name="ofertaDto") OfertaDto ofertaDto)
            throws SoapInputValidationException, SoapInstanceNotFoundException, SoapInvalidOfferUpdateException {
        Oferta o = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
        try {
            OfertaServiceFactory.getService().actualizarOferta(o);
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (InvalidOfferUpdateException ex) {
            throw new SoapInvalidOfferUpdateException(ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        }
    }

    @WebMethod(
        operationName="borrarOferta"
    )
    public void borrarOferta(@WebParam(name="codOferta") Long codOferta)
            throws SoapInstanceNotFoundException, SoapInvalidOfferDeleteException {
        try {
            OfertaServiceFactory.getService().borrarOferta(codOferta);
        } catch (InvalidOfferDeleteException ex) {
            throw new SoapInvalidOfferDeleteException(ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(
                    ex.getInstanceId(), ex.getInstanceType()));
        }
    }
    
    @WebMethod(
            operationName="invalidarOferta"
        )
        public void invalidarOferta(@WebParam(name="codOferta") Long codOferta)
                throws SoapInputValidationException, SoapInstanceNotFoundException, SoapInvalidOfferUpdateException {
            try {
                OfertaServiceFactory.getService().invalidarOferta(codOferta);
            } catch (InputValidationException ex) {
                throw new SoapInputValidationException(ex.getMessage());
            } catch (InvalidOfferUpdateException ex) {
                throw new SoapInvalidOfferUpdateException(ex.getMessage());
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
            }
        }
    
    @WebMethod(
            operationName="buscarOfertaID"
        )
        public OfertaDto buscarOfertaID(@WebParam(name="codOferta") Long codOferta)
                throws SoapInstanceNotFoundException {

            try {
                Oferta o = OfertaServiceFactory.getService().buscarOfertaID(codOferta);
                return OfertaToOfertaDtoConversor.toOfertaDto(o);
                
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
            }
        }

    @WebMethod(
        operationName="buscarOfertas"
    )
    public List<OfertaDto> buscarOfertas(
            @WebParam(name="palabrasClave") String palabrasClave) {
        List<Oferta> ofertas =
                OfertaServiceFactory.getService().buscarOfertas(palabrasClave, false, Calendar.getInstance());
        return OfertaToOfertaDtoConversor.toOfertaDtos(ofertas);
    }

    @WebMethod(
        operationName="hacerReserva"
    )
    public Long hacerReserva(@WebParam(name="email")  String email,
    					@WebParam(name="tarjetaCredito") String tarjetaCredito,
                        @WebParam(name="codOferta") Long codOferta)
            throws SoapInstanceNotFoundException, SoapInputValidationException, SoapInvalidOfferReservationException {
        try {
            Long codReserva = OfertaServiceFactory.getService()
            		.hacerReserva(email, tarjetaCredito, codOferta);
            return codReserva;
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (InvalidOfferReservationException ex) {
            throw new SoapInvalidOfferReservationException(ex.getMessage());
        }
    }

    @WebMethod(
            operationName="obtenerReservas"
        )
        public List<ReservaDto> obtenerReservas(
                @WebParam(name="codOferta") Long codOferta) throws SoapInstanceNotFoundException {
            
    		try {
    			List<Reserva> reservas =
                    OfertaServiceFactory.getService().obtenerReservas(codOferta);
    			return ReservaToReservaDtoConversor.toReservaDtos(reservas);
    		} catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
            }
        }

    @WebMethod(
            operationName="obtenerReservasUsuario"
        )
        public List<ReservaDto> obtenerReservasUsuario(
                @WebParam(name="email") String email,  @WebParam(name="todas") boolean todas) {
    			return ReservaToReservaDtoConversor.toReservaDtos(OfertaServiceFactory.getService()
        				.obtenerReservasUsuario(email, todas));
        }
    
    @WebMethod(
            operationName="reclamarReserva"
        )
        public void reclamarReserva(@WebParam(name="codReserva") Long codReserva)
                throws SoapInstanceNotFoundException, SoapInvalidOfferReclamationException {
            try {
                OfertaServiceFactory.getService().reclamarReserva(codReserva);
            } catch (InstanceNotFoundException ex) {
                throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
            } catch (InvalidOfferReclamationException ex) {
                throw new SoapInvalidOfferReclamationException(ex.getMessage());
            }
        }
    
	@WebMethod(
            operationName="buscarOfertasUsuario"
        )
        public List<OfertaDtoUsuario> buscarOfertasUsuario(
        		 @WebParam(name="email") String email) throws SoapInstanceNotFoundException{
    	
            List<Oferta> ofertas = new ArrayList<Oferta>();
            List<Calendar> fechas = new ArrayList<Calendar>();
            try {
				List<Reserva> reservas = OfertaServiceFactory.getService().obtenerReservasUsuario(email, true);
				for (Reserva r : reservas){
					ofertas.add(OfertaServiceFactory.getService().buscarOfertaID(r.getCodOferta()));
					fechas.add(r.getFechaReserva());
				}
			} catch (InstanceNotFoundException ex) {
				throw new SoapInstanceNotFoundException(
                        new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
			}
            
            return OfertaToOfertaDtoUsuarioConversor.toOfertaDtosUsuario(ofertas,fechas);
        }
    
}