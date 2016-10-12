package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlOfertaDtoUsuarioConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class RestClientOfertaService implements ClientOfertaService{

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientOfertaService.endpointAddress";
	private String endpointAddress;

	@Override
	public OfertaDto crearOferta(OfertaDto oferta) throws InputValidationException {

		try {
			
			HttpResponse response = 
				Request.Post(getEndpointAddress() + "ofertas").
				bodyStream(toInputStream(oferta), ContentType.create("application/xml")).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_CREATED, response);
			
			return XmlOfertaDtoConversor.toOferta(response.getEntity().getContent());
			
		} catch (InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void actualizarOferta(OfertaDto oferta) throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException {
		
		try {
			
			HttpResponse response = 
				Request.Put(getEndpointAddress() + "ofertas/" + + oferta.getCodOferta()).
				bodyStream(toInputStream(oferta), ContentType.create("application/xml")).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InputValidationException | InstanceNotFoundException | InvalidOfferUpdateException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void borrarOferta(Long codOferta) throws InstanceNotFoundException,
			InvalidOfferDeleteException {
		
		try {
			
			HttpResponse response = 
				Request.Delete(getEndpointAddress() + "ofertas/" + codOferta).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InstanceNotFoundException | InvalidOfferDeleteException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void invalidarOferta(Long codOferta)
			throws InstanceNotFoundException, InputValidationException,
			InvalidOfferUpdateException {
		
		try {
			
			HttpResponse response = 
				Request.Post(getEndpointAddress() + "ofertas/" + codOferta + "/invalidacion").
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_CREATED, response);

		} catch (InstanceNotFoundException |InputValidationException | InvalidOfferUpdateException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}

	@Override
	public OfertaDto buscarOfertaID(Long codOferta)
			throws InstanceNotFoundException {

		try {
			
			HttpResponse response = 
				Request.Get(getEndpointAddress() + "ofertas/"
					+ codOferta).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_OK, response);
			
			return XmlOfertaDtoConversor.toOferta(response.getEntity()
					.getContent());

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<OfertaDto> buscarOfertas(String palabrasClave) {
		
		try {
			
			HttpResponse response = 
				Request.Get(getEndpointAddress() + "ofertas?palabrasClave="
					+ URLEncoder.encode(palabrasClave, "UTF-8")).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlOfertaDtoConversor.toOfertas(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public Long hacerReserva(String email, String tarjetaCredito, Long codOferta)
			throws InstanceNotFoundException, InputValidationException,
			InvalidOfferReservationException {
		
		try {
			
			HttpResponse response = 
				Request.Post(getEndpointAddress() + "reservas").
				bodyForm(
					Form.form().
						add("codOferta", Long.toString(codOferta)).
						add("email", email).
						add("tarjetaCredito",tarjetaCredito).
						build()).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_CREATED, response);
			
                        return XmlReservaDtoConversor.toReserva(
				response.getEntity().getContent()).getCodReserva();
			
		} catch (InputValidationException | InstanceNotFoundException | InvalidOfferReservationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<ReservaDto> obtenerReservas(Long codOferta)
			throws InstanceNotFoundException {
		
		try {
			
			HttpResponse response = 
				Request.Get(getEndpointAddress() + "reservas/"
					+ codOferta).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_OK, response);
			
			return XmlReservaDtoConversor.toReservas(response.getEntity()
					.getContent());

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ReservaDto> obtenerReservasUsuario(String email, boolean todas) {	

		try {
			
			HttpResponse response = 
				Request.Get(getEndpointAddress() + "reservas?email="
					+ URLEncoder.encode(email, "UTF-8")+"&todas="+todas).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_OK, response);
			
			return XmlReservaDtoConversor.toReservas(response.getEntity()
					.getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void reclamarReserva(Long codReserva)
			throws InstanceNotFoundException, InvalidOfferReclamationException {
		
		try {
			
			HttpResponse response = 
				Request.Post(getEndpointAddress() + "reservas/" + codReserva + "/reclamacion").
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_CREATED, response);

		} catch (InstanceNotFoundException | InvalidOfferReclamationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}

	public List<OfertaDtoUsuario> buscarOfertasUsuario(String email)
			throws InstanceNotFoundException {
		
		try {
			
			HttpResponse response = 
				Request.Get(getEndpointAddress() + "ofertasUsuario?email="
					+ URLEncoder.encode(email, "UTF-8")).
				execute().returnResponse();
			
			validateStatusCode(HttpStatus.SC_OK, response);
			
			return XmlOfertaDtoUsuarioConversor.toOfertasUsuario(response.getEntity()
					.getContent());

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private synchronized String getEndpointAddress() {
		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}
	
	private InputStream toInputStream(OfertaDto oferta) {
		
		try {
		
			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			
			outputter.output(XmlOfertaDtoConversor.toXml(oferta), xmlOutputStream);
			
			return new ByteArrayInputStream(xmlOutputStream.toByteArray());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private void validateStatusCode(int successCode, HttpResponse response)
		throws InstanceNotFoundException,InputValidationException, 
		ParsingException, InvalidOfferUpdateException, InvalidOfferDeleteException, 
		InvalidOfferReservationException, InvalidOfferReclamationException, IllegalStateException, JDOMException{
		
		try {

			int statusCode = response.getStatusLine().getStatusCode();
			
			/* Success? */

			if (statusCode == successCode) {
				return;
			}
			
			/* Handler error. */
			
			SAXBuilder builder = null;
	        Document document = null;
	        Element rootElement = null;
			

			switch (statusCode) {
			
			
			case HttpStatus.SC_NOT_FOUND:

	            builder = new SAXBuilder();
	            document = builder.build(response.getEntity().getContent());
	            rootElement = document.getRootElement();
	            
	            if (rootElement.getName()=="InstanceNotFoundException"){
	            	throw XmlExceptionConversor.fromInstanceNotFoundExceptionXml
	            		(response.getEntity().getContent());
	            }
	            else if (rootElement.getName()=="InvalidOfferReservationException"){
	            	throw XmlExceptionConversor.fromInvalidOfferReservationExceptionXml
        			(response.getEntity().getContent());
	            }
	            else if (rootElement.getName()=="InvalidOfferReclamationException"){
	            	throw XmlExceptionConversor.fromInvalidOfferReclamationExceptionXml
        			(response.getEntity().getContent());
	            }
			case HttpStatus.SC_BAD_REQUEST:	
				
	            builder = new SAXBuilder();
	            document = builder.build(response.getEntity().getContent());
	            rootElement = document.getRootElement();
	            
	            if (rootElement.getName()=="InputValidationException"){	
					throw XmlExceptionConversor.fromInputValidationExceptionXml(
						response.getEntity().getContent());
	            }	
				else if (rootElement.getName()=="InvalidOfferUpdateException"){
		            throw XmlExceptionConversor.fromInvalidOfferUpdateExceptionXml
	            		(response.getEntity().getContent());
		        }
		        else if (rootElement.getName()=="InvalidOfferDeleteException"){
		            throw XmlExceptionConversor.fromInvalidOfferDeleteExceptionXml
	            		(response.getEntity().getContent());
		        }
			default:
				throw new RuntimeException("HTTP error; status code = "
						+ statusCode);
			}
		
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private static String getResponseHeader(HttpResponse response,
			String headerName) {
		String headerAsString = null;
		Header header = response.getFirstHeader(headerName);
		if (header != null) {
			headerAsString = header.getValue();
		}
		return headerAsString;
	}

}
