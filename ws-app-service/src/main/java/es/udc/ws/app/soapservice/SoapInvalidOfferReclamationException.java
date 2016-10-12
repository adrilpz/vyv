package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInvalidOfferReclamationException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapInvalidOfferReclamationException extends Exception {  
    
    public SoapInvalidOfferReclamationException(String message) {
        super(message);
    }
    
    public String getFaultInfo() {
        return getMessage();
    }    
}