package es.udc.pa.p007.apuestasapp.model.apuestasservice;

import java.util.Calendar;


public class PropertyValidator {
		
	
    	public static void validateString(String propertyName,
            String propertyValue) throws InputValidationException {

        	if ( (propertyValue == null) || propertyValue.length() == 0 ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be not null): " +
                    propertyValue);
        	}

    	}
	    
	    public static void validateDoublePositive(String propertyName,
            double propertyValue) throws InputValidationException {

	    	if (propertyValue < 1) {
	    		throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be less than 1.0): " +
                    propertyValue);
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
	
}
