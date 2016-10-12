package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class InvalidOfferReservationException extends Exception {

    public InvalidOfferReservationException(String message) {
        super(message);
    }
}