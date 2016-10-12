package es.udc.ws.app.soapservice;


import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapInvalidOfferReservationException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapInvalidOfferReservationException extends Exception {  
    
    public SoapInvalidOfferReservationException(String message) {
        super(message);
    }
    
    public String getFaultInfo() {
        return getMessage();
    }    
}