package es.udc.pa.p007.apuestasapp.web.pages.preferences;

import java.util.Locale;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.PersistentLocale;

import es.udc.pa.p007.apuestasapp.web.pages.Index;
import es.udc.pa.p007.apuestasapp.web.util.SupportedLanguages;

public class SelectLanguage {

    @Property
    private String language;

    @Inject
    private Locale locale;

    @Inject
    private PersistentLocale persistentLocale;

    void onPrepareForRender() {
        language = locale.getLanguage();
    }

    public String getLanguages() {
        return SupportedLanguages.getOptions(locale.getLanguage());
    }

    Object onSuccess() {

        persistentLocale.set(new Locale(language));

        return Index.class;

    }

}