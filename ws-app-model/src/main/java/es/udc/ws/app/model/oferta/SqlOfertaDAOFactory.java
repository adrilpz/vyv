package es.udc.ws.app.model.oferta;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

/**
 * A factory to get
 * <code>SqlOfertaDao</code> objects. <p> Required configuration parameters: <ul>
 * <li><code>SqlOfertaDaoFactory.className</code>: it must specify the full class
 * name of the class implementing
 * <code>SqlOfertaDao</code>.</li> </ul>
 */
public class SqlOfertaDAOFactory {

    private final static String CLASS_NAME_PARAMETER = "SqlOfertaDAOFactory.className";
    private static SqlOfertaDAO dao = null;

    private SqlOfertaDAOFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlOfertaDAO getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlOfertaDAO) daoClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static SqlOfertaDAO getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;

    }
}
