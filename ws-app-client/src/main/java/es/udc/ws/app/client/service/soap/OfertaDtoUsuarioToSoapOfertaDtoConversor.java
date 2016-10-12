package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;

public class OfertaDtoUsuarioToSoapOfertaDtoConversor {
	
    public static XMLGregorianCalendar toXMLGregorianCalendar(Calendar calendar) {
    	GregorianCalendar gCalendar= new GregorianCalendar();
    	gCalendar.setTimeInMillis(calendar.getTimeInMillis());
    	XMLGregorianCalendar xc;
    	try{
    		xc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    		return xc;
    	} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
    	return null;
    	
    }
    
    public static Calendar toCalendar (XMLGregorianCalendar xmlcalendar) {
    	Calendar c = Calendar.getInstance();
    	c.setTimeInMillis(xmlcalendar.toGregorianCalendar().getTimeInMillis());
		return c;
    }
    
	public static es.udc.ws.app.client.service.soap.wsdl.OfertaDto 
    		toSoapOfertaDto(OfertaDtoUsuario o) {
		es.udc.ws.app.client.service.soap.wsdl.OfertaDto soapOfertaDto = 
				new es.udc.ws.app.client.service.soap.wsdl.OfertaDto();
		
		soapOfertaDto.setDescripcion(o.getDescripcion());
		
		Calendar date = o.getFechaReserva();
		XMLGregorianCalendar xmldate;
		xmldate = toXMLGregorianCalendar(date);
		soapOfertaDto.setFechaLimiteReserva(xmldate);
		
		soapOfertaDto.setPrecioDescontado(o.getPrecioDescontado());
		
		return soapOfertaDto;
	}    
	
	public static OfertaDtoUsuario toOfertaDtoUsuario(
			es.udc.ws.app.client.service.soap.wsdl.OfertaDtoUsuario o) {
		return new OfertaDtoUsuario(o.getDescripcion(), toCalendar(o.getFechaReserva()), o.getPrecioDescontado());
	}    

	public static List<OfertaDtoUsuario> toOfertaDtosUsuario(
		List<es.udc.ws.app.client.service.soap.wsdl.OfertaDtoUsuario> ofertas) {
		List<OfertaDtoUsuario> ofertaDtosUsuario = new ArrayList<>(ofertas.size());
		for (int i = 0; i < ofertas.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.OfertaDtoUsuario o = 
					ofertas.get(i);
			ofertaDtosUsuario.add(toOfertaDtoUsuario(o));    
		}
		return ofertaDtosUsuario;
	}
}
