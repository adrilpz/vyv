package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class InvalidOfferDeleteException extends Exception {

    public InvalidOfferDeleteException(String message) {
        super(message);
    }
}