package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class OfertasServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if ((path!=null) && (path.contains("invalidacion"))){
				
			String[] parts = path.split("/");
			String codOfertaParameter = parts[1]; 
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
			 
			try {
				OfertaServiceFactory.getService().invalidarOferta(codOferta);
			} catch (InputValidationException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInputValidationExceptionXml(e),
						null);
				return;
	        } catch (InstanceNotFoundException ex) {
	            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
	                    XmlExceptionConversor.toInstanceNotFoundException(ex), null);
	            return;
	        } catch (InvalidOfferUpdateException e) {
	        	ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInvalidOfferUpdateExceptionXml(e),
						null);
	        	return;
			} 
			
			 Oferta oferta = null;
			 try {
				oferta = OfertaServiceFactory.getService().buscarOfertaID(codOferta);
			 } catch (InstanceNotFoundException e) {
				ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
	                    XmlExceptionConversor.toInstanceNotFoundException(e), null);
	            return;
			 }
		        
			 OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);

			 String ofertaURL = ServletUtils.normalizePath(req.getRequestURL()
					.toString()) + "/" + oferta.getCodOferta();
			 Map<String, String> headers = new HashMap<>(1);
			 headers.put("Location", ofertaURL);

			 ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
					XmlOfertaDtoConversor.toXml(ofertaDto), headers);
		}
		
		else {
			OfertaDto xmloferta;
			try {
				xmloferta = XmlOfertaDtoConversor.toOferta(req.getInputStream());
			} catch (ParsingException ex) {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												ex.getMessage())), null);
		
				return;
		
			}
			Oferta oferta = OfertaToOfertaDtoConversor.toOferta(xmloferta);
			try {
				
				oferta = OfertaServiceFactory.getService().crearOferta(oferta);
			} catch (InputValidationException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInputValidationExceptionXml(ex),
						null);
				return;
			}
			OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);
		
			String ofertaURL = ServletUtils.normalizePath(req.getRequestURL()
					.toString()) + "/" + oferta.getCodOferta();
			Map<String, String> headers = new HashMap<>(1);
			headers.put("Location", ofertaURL);
		
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
					XmlOfertaDtoConversor.toXml(ofertaDto), headers);
			
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalido código de oferta")),
							null);
			return;
		}
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
													+ "invalido código de oferta '"
													+ codOfertaAsString + "'")),
							null);
			return;
		}

		OfertaDto ofertaDto;
		try {
			ofertaDto = XmlOfertaDtoConversor.toOferta(req.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);
			return;

		}
		if (!codOferta.equals(ofertaDto.getCodOferta())) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid código de oferta")), null);
			return;
		}
		Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		try {
			OfertaServiceFactory.getService().actualizarOferta(oferta);
		
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		} catch (InputValidationException e) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " +
                        "parameter 'codOferta' is mandatory")), null);
            return;
		} catch (InvalidOfferUpdateException e) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInvalidOfferUpdateExceptionXml(
                    e), null);
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid código de oferta")),
							null);
			return;
		}
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
													+ "invalid código de oferta '"
													+ codOfertaAsString + "'")),
							null);

			return;
		}
		
		try {
			OfertaServiceFactory.getService().borrarOferta(codOferta);
		} catch (InvalidOfferDeleteException e) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInvalidOfferDeleteExceptionXml(
                    e), null);
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			
			String palabrasClave = req.getParameter("palabrasClave");
			Calendar fecha = Calendar.getInstance();
			List<Oferta> ofertas = OfertaServiceFactory.getService().buscarOfertas(palabrasClave, false, fecha);
			List<OfertaDto> ofertaDtos = OfertaToOfertaDtoConversor.toOfertaDtos(ofertas);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlOfertaDtoConversor.toXml(ofertaDtos), null);
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
														+ "invalido código de oferta'"
														+ codOfertaAsString + "'")),
								null);

				return;
			}
			Oferta oferta;
			try {
				oferta = OfertaServiceFactory.getService().buscarOfertaID(codOferta);
			} catch (InstanceNotFoundException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_NOT_FOUND,
						XmlExceptionConversor.toInstanceNotFoundException(ex),
						null);
				return;
			}
			OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlOfertaDtoConversor.toXml(ofertaDto), null);
		}
	}
}
