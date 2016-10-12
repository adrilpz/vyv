package es.udc.ws.app.soapservice;


import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInvalidOfferUpdateException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapInvalidOfferUpdateException extends Exception {  
    
    public SoapInvalidOfferUpdateException(String message) {
        super(message);
    }
    
    public String getFaultInfo() {
        return getMessage();
    }    
}