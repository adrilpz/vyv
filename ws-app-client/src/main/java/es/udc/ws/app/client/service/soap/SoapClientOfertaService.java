package es.udc.ws.app.client.service.soap;

import java.util.List;

import javax.xml.ws.BindingProvider;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.soap.wsdl.*;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientOfertaService implements ClientOfertaService{

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "SoapClientOfertaService.endpointAddress";

    private String endpointAddress;

    private OfertasProvider ofertasProvider;

    public SoapClientOfertaService() {
        init(getEndpointAddress());
    }

    private void init(String ofertasProviderURL) {
        OfertasProviderService ofertasProviderService =
            new OfertasProviderService();
        ofertasProvider = ofertasProviderService
            .getOfertasProviderPort();
        ((BindingProvider) ofertasProvider).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            ofertasProviderURL);
    }
    
    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

	@Override
	public OfertaDto crearOferta(OfertaDto o) throws InputValidationException {
		try {
			es.udc.ws.app.client.service.soap.wsdl.OfertaDto offer= OfertaDtoToSoapOfertaDtoConversor.toSoapOfertaDto(o);
			OfertaDto oferta = OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(ofertasProvider.crearOferta(offer)); 
			return oferta;
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	@Override
	public void actualizarOferta(OfertaDto o) throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException {
		 try {
             ofertasProvider.actualizarOferta(OfertaDtoToSoapOfertaDtoConversor
                     .toSoapOfertaDto(o));
		 } catch (SoapInvalidOfferUpdateException ex){
	        	throw new InvalidOfferUpdateException(ex.getMessage());    
         } catch (SoapInputValidationException ex) {
             throw new InputValidationException(ex.getMessage());
         } catch (SoapInstanceNotFoundException ex) {
             throw new InstanceNotFoundException(
                     ex.getFaultInfo().getInstanceId(),
                     ex.getFaultInfo().getInstanceType());
         }
	}

	@Override
	public void borrarOferta(Long codOferta) throws InstanceNotFoundException,
			InvalidOfferDeleteException {
        try {
            ofertasProvider.borrarOferta(codOferta);
        } catch (SoapInvalidOfferDeleteException ex){
        	throw new InvalidOfferDeleteException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
		
	}

	@Override
	public void invalidarOferta(Long codOferta)
			throws InstanceNotFoundException, InputValidationException,
			InvalidOfferUpdateException {
		 try {
             ofertasProvider.invalidarOferta(codOferta);
		 } catch (SoapInvalidOfferUpdateException ex){
	        	throw new InvalidOfferUpdateException(ex.getMessage());
		 } catch (SoapInputValidationException ex) {
             throw new InputValidationException(ex.getMessage());
         } catch (SoapInstanceNotFoundException ex) {
             throw new InstanceNotFoundException(
                     ex.getFaultInfo().getInstanceId(),
                     ex.getFaultInfo().getInstanceType());
         }
		
	}

	@Override
	public OfertaDto buscarOfertaID(Long codOferta)
			throws InstanceNotFoundException {
		try{ 
			OfertaDto o = OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(
	                ofertasProvider.buscarOfertaID(codOferta));
			o.setComisionVenta(-1);
			return o;
		} catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
		
	}

	@Override
	public List<OfertaDto> buscarOfertas(String palabrasClave) {
        List<OfertaDto> ofertas = OfertaDtoToSoapOfertaDtoConversor.toOfertaDtos(
                ofertasProvider.buscarOfertas(palabrasClave));
		for (OfertaDto o : ofertas){
			o.setComisionVenta(-1);
		}
        return ofertas;
	}

	@Override
	public Long hacerReserva(String email, String tarjetaCredito, Long codOferta)
			throws InstanceNotFoundException, InputValidationException,
			InvalidOfferReservationException {
        try {
            return ofertasProvider.hacerReserva(email, tarjetaCredito, codOferta);
        } catch (SoapInvalidOfferReservationException ex) {
            throw new InvalidOfferReservationException(ex.getMessage());
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
	}

	@Override
	public List<ReservaDto> obtenerReservas(Long codOferta)
			throws InstanceNotFoundException {
		try{
			return ReservaDtoToSoapReservaDtoConversor.toReservaDtos(
					ofertasProvider.obtenerReservas(codOferta));
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
	}

	@Override
	public List<ReservaDto> obtenerReservasUsuario(String email, boolean todas) {
			return ReservaDtoToSoapReservaDtoConversor.toReservaDtos(
					ofertasProvider.obtenerReservasUsuario(email, todas));
	}

	@Override
	public void reclamarReserva(Long codReserva)
			throws InstanceNotFoundException, InvalidOfferReclamationException {
        try {
            ofertasProvider.reclamarReserva(codReserva);
        } catch (SoapInvalidOfferReclamationException ex) {
            throw new InvalidOfferReclamationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
		
	}

	@Override
	public List<OfertaDtoUsuario> buscarOfertasUsuario(String email)
			throws InstanceNotFoundException {
        try{        	
        	return OfertaDtoUsuarioToSoapOfertaDtoConversor.toOfertaDtosUsuario(
        			ofertasProvider.buscarOfertasUsuario(email));
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }

}
