package es.udc.ws.app.validation;

import java.util.Calendar;

import es.udc.ws.util.exceptions.InputValidationException;

public class OfertaPropertyValidator {
	
    public static void validateBooleanTrue(String propertyName,
            boolean booleanValue) throws InputValidationException {

        if (booleanValue == false) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be false): " +
                    booleanValue);
        }

    }
	
    public static void validateFutureDate(String propertyName,
            Calendar propertyValue) throws InputValidationException {

        Calendar now = Calendar.getInstance();
        if ( (propertyValue == null) || (propertyValue.before(now)) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be a future date): " +
                    propertyValue);
        }

    }
    
    public static void validateDate1BeforeDate2(String propertyName, String propertyName2,
            Calendar propertyValue, Calendar propertyValue2) throws InputValidationException {

        if ( propertyValue.after(propertyValue2) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be a future date): " +
                    propertyValue);
        }

    }

}
