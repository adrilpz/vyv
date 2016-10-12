package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.EstadoReserva;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlReservaDAO implements SqlReservaDAO{

    protected AbstractSqlReservaDAO() {
    }
	
	@Override
	public void update(Connection connection, Reserva r)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "UPDATE RESERVA"
                + " SET COD_OFERTA = ?, EMAIL = ?, TARJETA_CREDITO = ?, "
                + " ESTADO = ?, FECHA_RESERVA = ?, PRECIO_OFERTA = ? WHERE COD_RESERVA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, r.getCodOferta());
            preparedStatement.setString(i++, r.getEmail());
            preparedStatement.setString(i++, r.getTarjetaCredito());
            preparedStatement.setString(i++, r.getEstado().toString());
            Timestamp date = r.getFechaReserva() != null ? new Timestamp(
                    r.getFechaReserva().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date);
            preparedStatement.setDouble(i++, r.getPrecioOferta());
            preparedStatement.setLong(i++, r.getCodReserva());
            

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(r.getCodOferta(),
                        Reserva.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
		
	

	@Override
	public void remove(Connection connection, Long codReserva)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "DELETE FROM RESERVA WHERE" + " COD_RESERVA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codReserva);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(codReserva,
                        Reserva.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	@Override
	public Reserva find(Connection connection, Long codReserva)
			throws InstanceNotFoundException {
		/* Create "queryString". */
		 String queryString = "SELECT COD_OFERTA, EMAIL, "
	                + " TARJETA_CREDITO, ESTADO, FECHA_RESERVA, PRECIO_OFERTA FROM RESERVA"
	                + " WHERE COD_RESERVA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codReserva.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(codReserva,
                        Reserva.class.getName());
            }

            /* Get results. */
            i = 1;
            Long codOferta = resultSet.getLong(i++);
            String email = resultSet.getString(i++);
            String tarjetaCredito = resultSet.getString(i++);
            EstadoReserva estado = EstadoReserva.valueOf(resultSet.getString(i++));
            Calendar fechaReserva = Calendar.getInstance();
            fechaReserva.setTime(resultSet.getTimestamp(i++));
            double precioOferta = resultSet.getDouble(i++);

            /* Return movie. */
            return new Reserva(codReserva, codOferta, email, tarjetaCredito, 
            		fechaReserva, estado, precioOferta);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
	

	@Override
	public List<Reserva> findByID(Connection connection, Long codOferta)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "SELECT COD_RESERVA, EMAIL, "
                + " TARJETA_CREDITO, ESTADO, FECHA_RESERVA, PRECIO_OFERTA FROM RESERVA"
                + " WHERE COD_OFERTA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

           
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codOferta.longValue());
            

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read reservas. */
            List<Reserva> reservas = new ArrayList<Reserva>();

            while (resultSet.next()) {

                i = 1;
                Long codReserva = new Long(resultSet.getLong(i++));
                String email = resultSet.getString(i++);
                String tarjetaCredito = resultSet.getString(i++);
                EstadoReserva estado = EstadoReserva.valueOf(resultSet.getString(i++));
                Calendar fechaReserva = Calendar.getInstance();
                fechaReserva.setTime(resultSet.getTimestamp(i++));
                double precioOferta = resultSet.getDouble(i++);
            	

                reservas.add(new Reserva(codReserva, codOferta, email, 
                		tarjetaCredito, fechaReserva, estado, precioOferta));
            }

            /* Return reservas. */
            return reservas;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Reserva> findByUser(Connection connection, String email,
			boolean todas) {
		/* Create "queryString". */
        String queryString = "SELECT COD_RESERVA, COD_OFERTA, "
                + " TARJETA_CREDITO, ESTADO, FECHA_RESERVA, PRECIO_OFERTA FROM RESERVA"
                + " WHERE EMAIL = ?";
        
        if (todas == false){
        		queryString += " AND ";
        	queryString += " ESTADO = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

           
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, email);
            
            if (todas == false){
            	preparedStatement.setString(i++,EstadoReserva.ACTIVA.toString());
            }
            

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read reservas. */
            List<Reserva> reservas = new ArrayList<Reserva>();

            while (resultSet.next()) {

                i = 1;
                Long codReserva = new Long(resultSet.getLong(i++));
                Long codOferta = resultSet.getLong(i++);
                String tarjetaCredito = resultSet.getString(i++);
                EstadoReserva estado = EstadoReserva.valueOf(resultSet.getString(i++));
                Calendar fechaReserva = Calendar.getInstance();
                fechaReserva.setTime(resultSet.getTimestamp(i++));
                double precioOferta = resultSet.getDouble(i++);
            	

                reservas.add(new Reserva(codReserva, codOferta, email, 
                		tarjetaCredito, fechaReserva, estado, precioOferta));
            }

            /* Return reservas. */
            return reservas;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public boolean ofertaSinReservas(Connection connection, Long codOferta)
			throws InstanceNotFoundException {
		/* Create "queryString". */
		 String queryString = "SELECT COD_RESERVA, EMAIL, "
	                + " TARJETA_CREDITO, ESTADO, FECHA_RESERVA, PRECIO_OFERTA FROM RESERVA"
	                + " WHERE COD_OFERTA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codOferta.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return true;
            }
            else{
            	return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}    
        
	@Override
	public void anularReservas(Connection connection, Long codOferta)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "UPDATE RESERVA"
                + " SET ESTADO = ? WHERE COD_OFERTA = ? AND ESTADO = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, EstadoReserva.ANULADA.toString());
            preparedStatement.setLong(i++, codOferta);
            preparedStatement.setString(i++, EstadoReserva.ACTIVA.toString());
            

            /* Execute query. */
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        
	public boolean ofertaReservadaAnteriormentePorUsuario(Connection connection, Long codOferta, String email)
			throws InstanceNotFoundException {
		/* Create "queryString". */
		 String queryString = "SELECT COD_RESERVA, COD_OFERTA, EMAIL, "
	                + " TARJETA_CREDITO, ESTADO, FECHA_RESERVA, PRECIO_OFERTA FROM RESERVA"
	                + " WHERE COD_OFERTA = ? AND EMAIL = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codOferta.longValue());
            preparedStatement.setString(i++, email);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }
            else{
            	return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}     
    
}
