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

import es.udc.ws.app.dto.OfertaDtoUsuario;

public class XmlOfertaDtoUsuarioConversor {

	public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/ofertasusuario/xml");

    public static Document toXml(OfertaDtoUsuario oferta)
            throws IOException {

        Element ofertaUsuarioElement = toJDOMElement(oferta);

        return new Document(ofertaUsuarioElement);
    }
    
    public static Document toXml(List<OfertaDtoUsuario> oferta)
            throws IOException {

        Element ofertasUsuarioElement = new Element("ofertasUsuario", XML_NS);
        for (int i = 0; i < oferta.size(); i++) {
        	OfertaDtoUsuario xmlOfertaDto = oferta.get(i);
            Element ofertaElement = toJDOMElement(xmlOfertaDto);
            ofertasUsuarioElement.addContent(ofertaElement);
        }

        return new Document(ofertasUsuarioElement);
    }
    
    public static OfertaDtoUsuario toOfertaUsuario(InputStream ofertaUsuarioXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaUsuarioXml);
            Element rootElement = document.getRootElement();

            return toOfertaUsuario(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static List<OfertaDtoUsuario> toOfertasUsuario(InputStream ofertaUsuarioXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaUsuarioXml);
            Element rootElement = document.getRootElement();

            if(!"ofertasUsuario".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('ofertas' expected)");
            }
			List<Element> children = rootElement.getChildren();
            List<OfertaDtoUsuario> ofertaDtosUsuario = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                ofertaDtosUsuario.add(toOfertaUsuario(element));
            }

            return ofertaDtosUsuario;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfertaDtoUsuario oferta) {

    	Element ofertaElement = new Element("ofertaUsuario", XML_NS);

        Element precioDesElement = new Element("precioDescontado", XML_NS);
        precioDesElement.setText(Double.toString(oferta.getPrecioDescontado()));
        ofertaElement.addContent(precioDesElement);

        Element descripcionElement = new Element("descripcion", XML_NS);
        descripcionElement.setText(oferta.getDescripcion());
        ofertaElement.addContent(descripcionElement);
        
        if (oferta.getFechaReserva() != null){
        	Element dateReservaElement = null;
        	dateReservaElement = getReservaDate(oferta.getFechaReserva());
        	ofertaElement.addContent(dateReservaElement);
        }
        
        return ofertaElement;
    }
    
    private static OfertaDtoUsuario toOfertaUsuario(Element ofertaElement)
            throws ParsingException, DataConversionException {
    	
        if (!"ofertaUsuario".equals(
        		ofertaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + ofertaElement.getName() + "' ('ofertaUsuario' expected)");
        }
        
        String descripcion = ofertaElement
                .getChildTextNormalize("descripcion", XML_NS);

        double precioDescontado = Float.valueOf(
        		ofertaElement.getChildTextTrim("precioDescontado", XML_NS));
        
        Calendar fechaReserva = getReservaDate(ofertaElement.getChild(
                "fechaReserva", XML_NS));
      
        return new OfertaDtoUsuario(descripcion, fechaReserva, precioDescontado);   
        
        }
    
    private static Calendar getReservaDate(Element reservaDateElement)
            throws DataConversionException {

        if (reservaDateElement == null) {
            return null;
        }
        int day = reservaDateElement.getAttribute("day").getIntValue();
        int month = reservaDateElement.getAttribute("month").getIntValue();
        int year = reservaDateElement.getAttribute("year").getIntValue();
        int hour = reservaDateElement.getAttribute("hour").getIntValue();
        int minute = reservaDateElement.getAttribute("minute").getIntValue();
        
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);
        releaseDate.set(Calendar.HOUR, hour);
        releaseDate.set(Calendar.MINUTE, minute);

        return releaseDate;

    }
    
    private static Element getReservaDate(Calendar reservaDate) {

        Element releaseDateElement = new Element("fechaReserva", XML_NS);
        int day = reservaDate.get(Calendar.DAY_OF_MONTH);
        int month = reservaDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = reservaDate.get(Calendar.YEAR);
        int hour = reservaDate.get(Calendar.HOUR);
        int minute = reservaDate.get(Calendar.MINUTE);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("minute", Integer.toString(minute));

        return releaseDateElement;

    }
}
