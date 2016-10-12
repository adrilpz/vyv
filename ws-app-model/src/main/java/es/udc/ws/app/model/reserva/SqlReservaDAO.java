package es.udc.ws.app.model.reserva;
import java.sql.Connection;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlReservaDAO {

    public Reserva create(Connection connection, Reserva r);
    
    public void update(Connection connection, Reserva r)
            throws InstanceNotFoundException;

    public void remove(Connection connection, Long codReserva)
            throws InstanceNotFoundException;
    
    public Reserva find(Connection connection, Long codReserva)
            throws InstanceNotFoundException;

    public List<Reserva> findByID(Connection connection, Long codOferta)
            throws InstanceNotFoundException;

    public List<Reserva> findByUser(Connection connection,
            String email, boolean todas);
    
    public boolean ofertaSinReservas(Connection connection, Long codOferta)
			throws InstanceNotFoundException;
    
    public void anularReservas(Connection connection, Long codOferta)
			throws InstanceNotFoundException;
    
    public boolean ofertaReservadaAnteriormentePorUsuario(Connection connection, Long codOferta, String email)
			throws InstanceNotFoundException;
    
}