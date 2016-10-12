package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.dto.OfertaDto;



public class OfertaDtoToSoapOfertaDtoConversor {
	
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
    		toSoapOfertaDto(OfertaDto o) {
		es.udc.ws.app.client.service.soap.wsdl.OfertaDto soapOfertaDto = 
				new es.udc.ws.app.client.service.soap.wsdl.OfertaDto();
		soapOfertaDto.setCodOferta(o.getCodOferta());
		soapOfertaDto.setNombre(o.getNombre());
		soapOfertaDto.setDescripcion(o.getDescripcion());
		
		Calendar date1 = o.getFechaLimiteOferta();
		XMLGregorianCalendar xmldate1;
		xmldate1 = toXMLGregorianCalendar(date1);
		soapOfertaDto.setFechaLimiteOferta(xmldate1);	
		
		
		Calendar date2 = o.getFechaLimiteReserva();
		XMLGregorianCalendar xmldate2;
		xmldate2 = toXMLGregorianCalendar(date2);
		soapOfertaDto.setFechaLimiteReserva(xmldate2);
		
		
		soapOfertaDto.setPrecioReal(o.getPrecioReal());
		soapOfertaDto.setPrecioDescontado(o.getPrecioDescontado());
		soapOfertaDto.setComisionVenta(o.getComisionVenta());
		soapOfertaDto.setValida(o.isValida());
		
		return soapOfertaDto;
	}    
	
	public static OfertaDto toOfertaDto(
			es.udc.ws.app.client.service.soap.wsdl.OfertaDto o) {
		return new OfertaDto(o.getCodOferta(), o.getNombre(), 
		o.getDescripcion(), toCalendar(o.getFechaLimiteReserva()), toCalendar(o.getFechaLimiteOferta()), 
		o.getPrecioReal(),o.getPrecioDescontado(), o.getComisionVenta(), o.isValida());
	}

	public static List<OfertaDto> toOfertaDtos(
		List<es.udc.ws.app.client.service.soap.wsdl.OfertaDto> ofertas) {
		List<OfertaDto> ofertaDtos = new ArrayList<>(ofertas.size());
		for (int i = 0; i < ofertas.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.OfertaDto o = 
					ofertas.get(i);
			ofertaDtos.add(toOfertaDto(o));    
		}
		return ofertaDtos;
	}
}