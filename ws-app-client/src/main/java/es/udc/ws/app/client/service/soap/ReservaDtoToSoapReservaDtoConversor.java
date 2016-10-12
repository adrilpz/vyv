package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.client.service.soap.wsdl.EstadoReserva;
import es.udc.ws.app.dto.ReservaDto;

public class ReservaDtoToSoapReservaDtoConversor {
	
    public static XMLGregorianCalendar toXMLGregorianCalendar(Calendar calendar) throws DatatypeConfigurationException{
        Date date= calendar.getTime(); 
    	GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        return xmlCalendar;
    }
	
    public static Calendar toCalendar (XMLGregorianCalendar calendar) {
    	if (calendar == null) {
    		return null;
    	}
    	return calendar.toGregorianCalendar();
    }

	public static es.udc.ws.app.client.service.soap.wsdl.ReservaDto 
			toSoapReservaDto(ReservaDto r) {
		es.udc.ws.app.client.service.soap.wsdl.ReservaDto soapReservaDto = 
				new es.udc.ws.app.client.service.soap.wsdl.ReservaDto();
		soapReservaDto.setCodReserva(r.getCodReserva());
		soapReservaDto.setCodOferta(r.getCodOferta());
		soapReservaDto.setEmail(r.getEmail());
		soapReservaDto.setEstado(EstadoReserva.valueOf(r.getEstado().toString()));
		Calendar date1 = r.getFechaReserva();
		XMLGregorianCalendar xmldate1;
		try {
			xmldate1 = toXMLGregorianCalendar(date1);
			soapReservaDto.setFechaReserva(xmldate1);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		soapReservaDto.setPrecioOferta(r.getPrecioOferta());
		soapReservaDto.setTarjetaCredito(r.getTarjetaCredito());
		return soapReservaDto;
	}    

	public static ReservaDto toReservaDto(
			es.udc.ws.app.client.service.soap.wsdl.ReservaDto r) {
		return new ReservaDto(r.getCodReserva(), r.getCodOferta(), r.getEmail(), 
				r.getTarjetaCredito(), toCalendar(r.getFechaReserva()), 
				es.udc.ws.app.dto.EstadoReserva.valueOf(r.getEstado().toString()),r.getPrecioOferta());
	}     

	public static List<ReservaDto> toReservaDtos(
			List<es.udc.ws.app.client.service.soap.wsdl.ReservaDto> reservas) {
		List<ReservaDto> reservaDtos = new ArrayList<>(reservas.size());
		for (int i = 0; i < reservas.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.ReservaDto r = 
			reservas.get(i);
			reservaDtos.add(toReservaDto(r));

		}
		return reservaDtos;
	}
}
