package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class XmlExceptionConversor {

    public final static Namespace XML_NS = XmlOfertaDtoConversor.XML_NS;

    public static InputValidationException
            fromInputValidationExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new InputValidationException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static InstanceNotFoundException
            fromInstanceNotFoundExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType =
                    rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(),
                    instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static InvalidOfferDeleteException
    		fromInvalidOfferDeleteExceptionXml(InputStream ex)
    		throws ParsingException {
    	try {

    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(ex);
    		Element rootElement = document.getRootElement();

    		Element message = rootElement.getChild("message", XML_NS);

    		return new InvalidOfferDeleteException(message.getText());
    	} catch (JDOMException | IOException e) {
    		throw new ParsingException(e);
    	} catch (Exception e) {
    		throw new ParsingException(e);
    	}
    }
    
    public static InvalidOfferReclamationException
			fromInvalidOfferReclamationExceptionXml(InputStream ex)
			throws ParsingException {
    	try {

    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(ex);
    		Element rootElement = document.getRootElement();

    		Element message = rootElement.getChild("message", XML_NS);

    		return new InvalidOfferReclamationException(message.getText());
    	} catch (JDOMException | IOException e) {
    		throw new ParsingException(e);
    	} catch (Exception e) {
    		throw new ParsingException(e);
    	}
    }
    
    public static InvalidOfferReservationException
    		fromInvalidOfferReservationExceptionXml(InputStream ex)
    		throws ParsingException {
    	try {

    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(ex);
    		Element rootElement = document.getRootElement();

    		Element message = rootElement.getChild("message", XML_NS);

    		return new InvalidOfferReservationException(message.getText());
    	} catch (JDOMException | IOException e) {
    		throw new ParsingException(e);
    	} catch (Exception e) {
    		throw new ParsingException(e);
    	}
    }
    
    public static InvalidOfferUpdateException
			fromInvalidOfferUpdateExceptionXml(InputStream ex)
			throws ParsingException {
    	try {

    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(ex);
    		Element rootElement = document.getRootElement();

    		Element message = rootElement.getChild("message", XML_NS);

    		return new InvalidOfferUpdateException(message.getText());
    	} catch (JDOMException | IOException e) {
    			throw new ParsingException(e);
    	} catch (Exception e) {
    			throw new ParsingException(e);
    	}
    }

    public static Document toInputValidationExceptionXml(
                InputValidationException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InputValidationException", XML_NS);

        Element messageElement = new Element("message", XML_NS);
        messageElement.setText(ex.getMessage());
        exceptionElement.addContent(messageElement);

        return new Document(exceptionElement);
    }

    public static Document toInstanceNotFoundException (
                InstanceNotFoundException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InstanceNotFoundException", XML_NS);

        if(ex.getInstanceId() != null) {
            Element instanceIdElement = new Element("instanceId", XML_NS);
            instanceIdElement.setText(ex.getInstanceId().toString());

            exceptionElement.addContent(instanceIdElement);
        }

        if(ex.getInstanceType() != null) {
            Element instanceTypeElement = new Element("instanceType", XML_NS);
            instanceTypeElement.setText(ex.getInstanceType());

            exceptionElement.addContent(instanceTypeElement);
        }
        return new Document(exceptionElement);
    }
    
    public static Document toInvalidOfferDeleteExceptionXml(
            InvalidOfferDeleteException ex)
            throws IOException {

    	Element exceptionElement =
            new Element("InvalidOfferDeleteException", XML_NS);

    	Element messageElement = new Element("message", XML_NS);
    	messageElement.setText(ex.getMessage());
    	exceptionElement.addContent(messageElement);

    	return new Document(exceptionElement);
    }
    
    public static Document toInvalidOfferReclamationExceptionXml(
            InvalidOfferReclamationException ex)
            throws IOException {

    	Element exceptionElement =
            new Element("InvalidOfferReclamationException", XML_NS);

    	Element messageElement = new Element("message", XML_NS);
    	messageElement.setText(ex.getMessage());
    	exceptionElement.addContent(messageElement);

    	return new Document(exceptionElement);
    }
    
    public static Document toInvalidOfferReservationExceptionXml(
            InvalidOfferReservationException ex)
            throws IOException {

    	Element exceptionElement =
            new Element("InvalidOfferReservationException", XML_NS);

    	Element messageElement = new Element("message", XML_NS);
    	messageElement.setText(ex.getMessage());
    	exceptionElement.addContent(messageElement);

    	return new Document(exceptionElement);
    }
    
    public static Document toInvalidOfferUpdateExceptionXml(
            InvalidOfferUpdateException ex)
            throws IOException {

    	Element exceptionElement =
            new Element("InvalidOfferUpdateException", XML_NS);

    	Element messageElement = new Element("message", XML_NS);
    	messageElement.setText(ex.getMessage());
    	exceptionElement.addContent(messageElement);

    	return new Document(exceptionElement);
    }
}
