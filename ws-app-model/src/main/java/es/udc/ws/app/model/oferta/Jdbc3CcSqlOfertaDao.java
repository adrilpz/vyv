package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlOfertaDao extends AbstractSqlOfertaDAO {

	@Override
	public Oferta create(Connection connection, Oferta o) {
		 /* Create "queryString". */
         String queryString = "INSERT INTO OFERTA"
                + " (NOMBRE, DESCRIPCION, FECHA_LIMITE_RESERVA, FECHA_LIMITE_OFERTA, "
                + "PRECIO_REAL, PRECIO_DESCONTADO, COMISION_VENTA, VALIDA)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

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

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long codOferta = resultSet.getLong(1);

            /* Return oferta. */
            return new Oferta(codOferta, o.getNombre(), o.getDescripcion(),
            		o.getFechaLimiteReserva(), o.getFechaLimiteOferta(),
            		o.getPrecioReal(),o.getPrecioDescontado(),o.getComisionVenta(), o.isValida());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
