package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ReservasServlet  extends HttpServlet{

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if ((path!=null)&&(path.contains("reclamacion"))){
			
			String[] parts = path.split("/");
			String codReservaParameter = parts[1]; 
			if (codReservaParameter == null) {
		            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
		                    XmlExceptionConversor.toInputValidationExceptionXml(
		                    new InputValidationException("Invalid Request: " +
		                        "parameter 'codReserva' is mandatory")), null);
		            return;
		     }
			 Long codReserva;
			 try {
		        	codReserva = Long.valueOf(codReservaParameter);
		        } catch (NumberFormatException ex) {
		            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
		                    XmlExceptionConversor.toInputValidationExceptionXml(
		                    new InputValidationException("Invalid Request: " +
		                        "parameter 'codReserva' is invalid '" +
		                        codReservaParameter + "'")),
		                    null);
		
		            return;
		        }
			 
			 try {
				 try {
					OfertaServiceFactory.getService().reclamarReserva(codReserva);
				} catch (InvalidOfferReclamationException e) {
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
		                    XmlExceptionConversor.toInvalidOfferReclamationExceptionXml(
		                    e), null);
					return;
				}
		                  
		        } catch (InstanceNotFoundException ex) {
		            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
		                    XmlExceptionConversor.toInstanceNotFoundException(ex), null);
		            return;
		        } 
			
			 Reserva reserva = null;
			 try {
					reserva = OfertaServiceFactory.getService().buscarReserva(codReserva);
				} catch (InstanceNotFoundException e) {
		            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
		                    XmlExceptionConversor.toInstanceNotFoundException(e), null);
		            return;
				}
		        
		        ReservaDto reservaDto = ReservaToReservaDtoConversor.toReservaDto(reserva);

		        String reservaURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" +
		                reserva.getCodReserva().toString();

		        Map<String, String> headers = new HashMap<>(1);
		        headers.put("Location", reservaURL);

		        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
		                XmlReservaDtoConversor.toResponse(reservaDto), headers);
		}
		else {
			String codOfertaParameter = req.getParameter("codOferta");
	        if (codOfertaParameter == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlExceptionConversor.toInputValidationExceptionXml(
	                    new InputValidationException("Invalid Request: " +
	                        "parameter 'codOferta' is mandatory")), null);
	            return;
	        }
	        Long codOferta;
	        try {
	        	codOferta = Long.valueOf(codOfertaParameter);
	        } catch (NumberFormatException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlExceptionConversor.toInputValidationExceptionXml(
	                    new InputValidationException("Invalid Request: " +
	                        "parameter 'codOferta' is invalid '" +
	                        codOfertaParameter + "'")),
	                    null);

	            return;
	        }
	        String email = req.getParameter("email");
	        if (email == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlExceptionConversor.toInputValidationExceptionXml(
	                    new InputValidationException("Invalid Request: " +
	                        "parameter 'email' is mandatory")), null);
	            return;
	        }
	        String tarjetaCredito = req.getParameter("tarjetaCredito");
	        if (tarjetaCredito == null) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlExceptionConversor.toInputValidationExceptionXml(
	                    new InputValidationException("Invalid Request: "+
	                        "parameter 'tarjetaCredito' is mandatory")), null);

	            return;
	        }
	        Reserva reserva = null;
	        Long codReserva = null;
	        try {
	        	try {
					codReserva = OfertaServiceFactory.getService().hacerReserva(email, tarjetaCredito, codOferta);
				} catch (InvalidOfferReservationException e) {
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
		                    XmlExceptionConversor.toInvalidOfferReservationExceptionXml(
		                    e), null);
				}
	                  
	        } catch (InstanceNotFoundException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
	                    XmlExceptionConversor.toInstanceNotFoundException(ex), null);
	            return;
	        } catch (InputValidationException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
	                    XmlExceptionConversor.toInputValidationExceptionXml(ex), null);
	            return;
	        }
	        
	        try {
				reserva = OfertaServiceFactory.getService().buscarReserva(codReserva);
			} catch (InstanceNotFoundException e) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
	                    XmlExceptionConversor.toInstanceNotFoundException(e), null);
	            return;
			}
	        
	        ReservaDto reservaDto = ReservaToReservaDtoConversor.toReservaDto(reserva);

	        String reservaURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" +
	                reserva.getCodReserva().toString();

	        Map<String, String> headers = new HashMap<>(1);
	        headers.put("Location", reservaURL);

	        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
	                XmlReservaDtoConversor.toResponse(reservaDto), headers);
		}
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null) {
			String email = req.getParameter("email");
			boolean valida = Boolean.valueOf(req.getParameter("todas"));
			List<Reserva> reservas = new ArrayList<Reserva>();
			reservas = OfertaServiceFactory.getService().obtenerReservasUsuario(email, valida);
			List<ReservaDto> reservaDtos = ReservaToReservaDtoConversor.toReservaDtos(reservas);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlReservaDtoConversor.toXml(reservaDtos), null);
		}
		else {
			String codOfertaAsString = path.substring(1);
			Long codOferta;
			try {
				codOferta = Long.valueOf(codOfertaAsString);
			} catch (NumberFormatException ex) {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "invalido código de reserva'"
														+ codOfertaAsString)), null);
				return;
			}
			List<Reserva> reservas = new ArrayList<Reserva>();
			try {
				reservas = OfertaServiceFactory.getService().obtenerReservas(codOferta);
				
			} catch (InstanceNotFoundException ex) {
				ServletUtils
						.writeServiceResponse(resp,
								HttpServletResponse.SC_NOT_FOUND,
								XmlExceptionConversor
										.toInstanceNotFoundException(ex), null);
				return;
			}
			
			List<ReservaDto> reservasDtos = ReservaToReservaDtoConversor.toReservaDtos(reservas);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlReservaDtoConversor.toXml(reservasDtos), null);
		
		}
	}
}
