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

import es.udc.ws.app.dto.OfertaDto;


public class XmlOfertaDtoConversor {

	public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/ofertas/xml");

    public static Document toXml(OfertaDto oferta)
            throws IOException {

        Element ofertaElement = toJDOMElement(oferta);

        return new Document(ofertaElement);
    }

    public static Document toXml(List<OfertaDto> oferta)
            throws IOException {

        Element ofertasElement = new Element("ofertas", XML_NS);
        for (int i = 0; i < oferta.size(); i++) {
            OfertaDto xmlOfertaDto = oferta.get(i);
            Element ofertaElement = toJDOMElement(xmlOfertaDto);
            ofertasElement.addContent(ofertaElement);
        }

        return new Document(ofertasElement);
    }

    public static OfertaDto toOferta(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            return toOferta(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<OfertaDto> toOfertas(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            if(!"ofertas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('ofertas' expected)");
            }
			List<Element> children = rootElement.getChildren();
            List<OfertaDto> ofertaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                ofertaDtos.add(toOferta(element));
            }

            return ofertaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfertaDto oferta) {

    	Element ofertaElement = new Element("oferta", XML_NS);

        if (oferta.getCodOferta() != null) {
            Element codElement = new Element("codOferta", XML_NS);
            codElement.setText(oferta.getCodOferta().toString());
            ofertaElement.addContent(codElement);
        }

        Element descripcionElement = new Element("descripcion", XML_NS);
        descripcionElement.setText(oferta.getDescripcion());
        ofertaElement.addContent(descripcionElement);
        
        Element nombreElement = new Element("nombre", XML_NS);
        nombreElement.setText(oferta.getNombre());
        ofertaElement.addContent(nombreElement);
        
        if (oferta.getFechaLimiteReserva() != null){
        	Element limiteReservaElement = null;
        	limiteReservaElement = getFechaLimiteReserva(oferta.getFechaLimiteReserva());
        	ofertaElement.addContent(limiteReservaElement);
        }
        if (oferta.getFechaLimiteOferta() != null){
        	Element limiteOfertaElement = null;
        	limiteOfertaElement = getFechaLimiteOferta(oferta.getFechaLimiteOferta());
        	ofertaElement.addContent(limiteOfertaElement);
        }
        
        Element precioElement = new Element("precioReal", XML_NS);
        precioElement.setText(Double.toString(oferta.getPrecioReal()));
        ofertaElement.addContent(precioElement);
        
        Element precioDesElement = new Element("precioDescontado", XML_NS);
        precioDesElement.setText(Double.toString(oferta.getPrecioDescontado()));
        ofertaElement.addContent(precioDesElement);

        Element comisionElement = new Element("comisionVenta", XML_NS);
        comisionElement = comisionElement.setText(Double.toString(oferta.getComisionVenta()));
        ofertaElement.addContent(comisionElement);

        Element validaElement = new Element("valida", XML_NS);
        validaElement = validaElement.setText(Boolean.toString(oferta.isValida()));
        ofertaElement.addContent(validaElement);        
        
        return ofertaElement;
    }
    
    private static OfertaDto toOferta(Element ofertaElement)
            throws ParsingException, DataConversionException {
    	
        if (!"oferta".equals(
        		ofertaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + ofertaElement.getName() + "' ('oferta' expected)");
        }
        Element codElement = ofertaElement.getChild("codOferta", XML_NS);
        Long codOferta = null;

        if (codElement != null) {
        	codOferta = Long.valueOf(codElement.getTextTrim());
        }

        String nombre = ofertaElement.getChildTextNormalize("nombre", XML_NS);
        
        String descripcion = ofertaElement
                .getChildTextNormalize("descripcion", XML_NS);
        
        Calendar fechaLimiteReserva = getFechaLimite(ofertaElement.getChild(
                "fechaLimiteReserva", XML_NS));
        
        Calendar fechaLimiteOferta = getFechaLimite(ofertaElement.getChild(
                "fechaLimiteOferta", XML_NS));
        
        double precioReal = Float.valueOf(
        		ofertaElement.getChildTextTrim("precioReal", XML_NS));
        
        double precioDescontado = Float.valueOf(
        		ofertaElement.getChildTextTrim("precioDescontado", XML_NS));
        
        double comision = Float.valueOf(
        		ofertaElement.getChildTextTrim("comisionVenta", XML_NS));
        
        boolean valida = Boolean.valueOf(
        		ofertaElement.getChildTextTrim("valida", XML_NS));

        return new OfertaDto(codOferta, nombre, descripcion, fechaLimiteReserva, fechaLimiteOferta, precioReal,
        		precioDescontado, comision, valida);   
    }
    
    
    private static Calendar getFechaLimite(Element dateElement)
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
        releaseDate.set(Calendar.HOUR_OF_DAY, hour);
        releaseDate.set(Calendar.MINUTE, minute);

        return releaseDate;

    }
    
    private static Element getFechaLimiteReserva(Calendar date) {

        Element releaseDateElement = new Element("fechaLimiteReserva", XML_NS);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("minute", Integer.toString(minute));

        return releaseDateElement;

    }
    
    private static Element getFechaLimiteOferta(Calendar date) {

        Element releaseDateElement = new Element("fechaLimiteOferta", XML_NS);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("minute", Integer.toString(minute));

        return releaseDateElement;

    }
}