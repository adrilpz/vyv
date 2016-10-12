package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.dto.EstadoReserva;
import es.udc.ws.app.dto.ReservaDto;


public class XmlReservaDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/reservas/xml");

    public static Document toResponse(ReservaDto reserva)
            throws IOException {

        Element reservaElement = toXml(reserva);

        return new Document(reservaElement);
    }

    public static ReservaDto toReserva(InputStream reservaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();

            return toReserva(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Document toXml(List<ReservaDto> reservas)
            throws IOException {

        Element reservasElement = new Element("reservas", XML_NS);
        for (int i = 0; i < reservas.size(); i++) {
            ReservaDto xmlReservaDto = reservas.get(i);
            Element reservaElement = toXml(xmlReservaDto);
            reservasElement.addContent(reservaElement);
        }

        return new Document(reservasElement);
    }
    
    public static Element toXml(ReservaDto reserva) {

        Element reservaElement = new Element("reserva", XML_NS);

        if (reserva.getCodReserva()!= null) {
            Element reservaCodElement = new Element("codReserva", XML_NS);
            reservaCodElement.setText(reserva.getCodReserva().toString());
            reservaElement.addContent(reservaCodElement);
        }

        if (reserva.getCodOferta() != null) {
            Element ofertaCodElement = new Element("codOferta", XML_NS);
            ofertaCodElement.setText(reserva.getCodOferta().toString());
            reservaElement.addContent(ofertaCodElement);
        }
        
        if (reserva.getFechaReserva() != null) {
        	Element dateElement = null;
        	dateElement = getFechaReserva(reserva.getFechaReserva());
        	reservaElement.addContent(dateElement);

        }
        
        Element emailElement = new Element("email", XML_NS);
        emailElement.setText(reserva.getEmail().toString());
        reservaElement.addContent(emailElement);
        
        Element estadoElement = new Element("estado", XML_NS);
        estadoElement.setText(reserva.getEstado().toString());
        reservaElement.addContent(estadoElement);
        
        Element precioElement = new Element("precio", XML_NS);
        precioElement.setText(Double.toString(reserva.getPrecioOferta()));
        reservaElement.addContent(precioElement);
        
        Element tarjetaCreditoElement = new Element("tarjetaCredito", XML_NS);
        tarjetaCreditoElement.setText(reserva.getTarjetaCredito());
        reservaElement.addContent(tarjetaCreditoElement);
        
        return reservaElement;
    }

    private static ReservaDto toReserva(Element reservaElement)
            throws ParsingException, DataConversionException,
            NumberFormatException {
    	
        if (!"reserva".equals(reservaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + reservaElement.getName() + "' ('reserva' expected)");
        }
        Element reservaCodElement = reservaElement.getChild("codReserva", XML_NS);
        Long codReserva = null;
        if (reservaCodElement != null) {
        	codReserva = Long.valueOf(reservaCodElement.getTextTrim());
        }

        Element ofertaCodElement = reservaElement.getChild("codOferta", XML_NS);
        Long codOferta = null;
        if (ofertaCodElement != null) {
        	codOferta = Long.valueOf(ofertaCodElement.getTextTrim());
        }

        Calendar fechaReserva = getFechaReserva(reservaElement.getChild(
                "fechaReserva", XML_NS));

        String email = reservaElement.getChildTextNormalize("email", XML_NS);

        double precio = Double.valueOf(
                reservaElement.getChildTextTrim("precio", XML_NS));
        
        EstadoReserva estado;
        String estadoString = reservaElement.getChildTextNormalize("estado", XML_NS);
        estado = EstadoReserva.valueOf(estadoString);
       
        String tarjeta = reservaElement.getChildTextNormalize("tarjetaCredito", XML_NS);

        return new ReservaDto(codReserva, codOferta, email, tarjeta, fechaReserva, estado,precio);
    }

    public static List<ReservaDto> toReservas(InputStream reservaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();

            if(!"reservas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('reservas' expected)");
            }
			List<Element> children = rootElement.getChildren();
            List<ReservaDto> reservaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                reservaDtos.add(toReserva(element));
            }

            return reservaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static Calendar getFechaReserva(Element dateElement)
            throws DataConversionException {

        if (dateElement == null) {
            return null;
        }
        int day = dateElement.getAttribute("day").getIntValue();
        int month = dateElement.getAttribute("month").getIntValue();
        int year = dateElement.getAttribute("year").getIntValue();
        int hour = dateElement.getAttribute("hour").getIntValue();
        int minute = dateElement.getAttribute("minute").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);
        releaseDate.set(Calendar.HOUR, hour);
        releaseDate.set(Calendar.MINUTE, minute);

        return releaseDate;

    }

    private static Element getFechaReserva(Calendar date) {

        Element releaseDateElement = new Element("fechaReserva", XML_NS);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("minute", Integer.toString(minute));

        return releaseDateElement;

    }
}
