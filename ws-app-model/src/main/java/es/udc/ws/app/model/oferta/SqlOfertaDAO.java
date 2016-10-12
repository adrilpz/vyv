package es.udc.ws.app.model.oferta;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlOfertaDAO {

    public Oferta create(Connection connection, Oferta o);
    
    public void update(Connection connection, Oferta o)
            throws InstanceNotFoundException;

    public void remove(Connection connection, Long codOferta)
            throws InstanceNotFoundException;

    public Oferta findByID(Connection connection, Long codOferta)
            throws InstanceNotFoundException;

    public List<Oferta> find(Connection connection,
            String palabrasclave, boolean todas ,Calendar fecha);
    
}