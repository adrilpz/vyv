package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInvalidOfferDeleteException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapInvalidOfferDeleteException extends Exception {  
    
    public SoapInvalidOfferDeleteException(String message) {
        super(message);
    }
    
    public String getFaultInfo() {
        return getMessage();
    }    
}