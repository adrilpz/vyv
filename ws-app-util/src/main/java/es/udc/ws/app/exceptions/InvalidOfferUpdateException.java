package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class InvalidOfferUpdateException extends Exception {

    public InvalidOfferUpdateException(String message) {
        super(message);
    }
}