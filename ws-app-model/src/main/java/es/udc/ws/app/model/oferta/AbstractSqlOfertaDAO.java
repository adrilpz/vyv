package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;



/**
 * A partial implementation of
 * <code>SQLOfertaDAO</code> that leaves
 * <code>create(Connection, Oferta)</code> as abstract.
 */

public abstract class AbstractSqlOfertaDAO implements SqlOfertaDAO {

	protected AbstractSqlOfertaDAO() {
    }
	
	@Override
	public void update(Connection connection, Oferta o)
			throws InstanceNotFoundException {
		 /* Create "queryString". */
        String queryString = "UPDATE OFERTA"
                + " SET NOMBRE = ?, DESCRIPCION = ?, FECHA_LIMITE_RESERVA = ?, "
                + "FECHA_LIMITE_OFERTA = ?, PRECIO_REAL = ?, PRECIO_DESCONTADO = ?, "
                + "COMISION_VENTA = ?, VALIDA = ? WHERE COD_OFERTA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, o.getNombre());
            preparedStatement.setString(i++, o.getDescripcion());
            Timestamp date1 = o.getFechaLimiteReserva() != null ? new Timestamp(
            		o.getFechaLimiteReserva().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++,date1);
            Timestamp date2 = o.getFechaLimiteOferta() != null ? new Timestamp(
            		o.getFechaLimiteOferta().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++,date2);
            preparedStatement.setDouble(i++, o.getPrecioReal());
            preparedStatement.setDouble(i++, o.getPrecioDescontado());
            preparedStatement.setDouble(i++, o.getComisionVenta());
            preparedStatement.setBoolean(i++, o.isValida());
            preparedStatement.setLong(i++, o.getCodOferta());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(o.getCodOferta(),
                        Oferta.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

	@Override
	public void remove(Connection connection, Long codOferta)
			throws InstanceNotFoundException {
		/* Create "queryString". */
        String queryString = "DELETE FROM OFERTA WHERE" + " COD_OFERTA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codOferta);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(codOferta,
                        Oferta.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

	@Override
	public Oferta findByID(Connection connection, Long codOferta)
			throws InstanceNotFoundException {
		
		  /* Create "queryString". */
        String queryString = "SELECT NOMBRE, DESCRIPCION, FECHA_LIMITE_RESERVA,"
        		+ "FECHA_LIMITE_OFERTA, PRECIO_REAL, PRECIO_DESCONTADO,"
        		+ "COMISION_VENTA, VALIDA FROM OFERTA WHERE COD_OFERTA = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, codOferta.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(codOferta,
                        Oferta.class.getName());
            }

            /* Get results. */
            i = 1;
            String nombre = resultSet.getString(i++);
            String descripcion = resultSet.getString(i++);
            Calendar fechaLimiteReserva = Calendar.getInstance();
            fechaLimiteReserva.setTime(resultSet.getTimestamp(i++));
            Calendar fechaLimiteOferta = Calendar.getInstance();
            fechaLimiteOferta.setTime(resultSet.getTimestamp(i++));
            double precioReal = resultSet.getDouble(i++);
        	double precioDescontado = resultSet.getDouble(i++);
        	double comisionVenta = resultSet.getDouble(i++);
        	boolean valida = resultSet.getBoolean(i++);

            /* Return movie. */
            return new Oferta(codOferta, nombre, descripcion, fechaLimiteReserva, 
            		fechaLimiteOferta, precioReal, precioDescontado, comisionVenta, valida);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

	@Override
	public List<Oferta> find(Connection connection,
			String palabrasclave, boolean todas, Calendar fecha) {
		
		/* Create "queryString". */
        String[] words = palabrasclave != null ? palabrasclave.split(" ") : null;
        String queryString = "SELECT COD_OFERTA, NOMBRE, DESCRIPCION, "
                + " FECHA_LIMITE_RESERVA, FECHA_LIMITE_OFERTA, PRECIO_REAL, "
        		+ " PRECIO_DESCONTADO, COMISION_VENTA, VALIDA FROM OFERTA";
        
        if (palabrasclave != null || todas == false || fecha != null){
        	queryString += " WHERE";
        }
        
        if (words != null && words.length > 0) {
            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                    queryString += " AND";
                }
                queryString += " LOWER(DESCRIPCION) LIKE LOWER(?)";
            }
        }
        
        if (fecha != null) {
        	if (words != null){
        		queryString += " AND ";
        	}
        	queryString += " FECHA_LIMITE_RESERVA > ?";
        }
        
        if (todas == false){
        	if (words != null || fecha != null){
        		queryString += " AND ";
        	}
        	queryString += " VALIDA = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
        	/* Fill "preparedStatement". */
        	int i = 1;
            if (words != null) {
                
                for (i=1; i < words.length+1; i++) {
                    preparedStatement.setString(i, "%" + words[i-1] + "%");
                }
            }
            if (fecha != null){
                Timestamp date = fecha != null ? new Timestamp(
                		fecha.getTime().getTime()) : null;
                preparedStatement.setTimestamp(i++,date);
            }
            
            if (todas == false){
            	preparedStatement.setBoolean(i++,true);
            }
             
            

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read ofertas. */
            List<Oferta> ofertas = new ArrayList<Oferta>();

            while (resultSet.next()) {

                i = 1;
                Long codOferta = new Long(resultSet.getLong(i++));
                String nombre = resultSet.getString(i++);
                String descripcion = resultSet.getString(i++);
                Calendar fechaLimiteReserva = Calendar.getInstance();
                fechaLimiteReserva.setTime(resultSet.getTimestamp(i++));
                Calendar fechaLimiteOferta = Calendar.getInstance();
                fechaLimiteOferta.setTime(resultSet.getTimestamp(i++));
                double precioReal = resultSet.getDouble(i++);
            	double precioDescontado = resultSet.getDouble(i++);
            	double comisionVenta = resultSet.getDouble(i++);
            	boolean valida = resultSet.getBoolean(i++);

                ofertas.add(new Oferta(codOferta, nombre, descripcion, fechaLimiteReserva, fechaLimiteOferta, 
                		precioReal, precioDescontado, comisionVenta, valida));
            }

            /* Return ofertas. */
            return ofertas;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		
	}

}