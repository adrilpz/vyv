package es.udc.pojo.minibank.web.components;

import org.apache.tapestry5.annotations.Import;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

@Import(library = {"tapestry5/bootstrap/js/collapse.js", "tapestry5/bootstrap/js/dropdown.js"},
		stylesheet="tapestry5/bootstrap/css/bootstrap-theme.css")
public class Layout {
	
	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String title;
	
	@Parameter(defaultPrefix = "literal")
    private Boolean showTitleInBody;
	
	public boolean getShowTitleInBody() {
    	
    	if (showTitleInBody == null) {
    		return true;
    	} else {
    		return showTitleInBody;
    	}
    	
    }
	
}
