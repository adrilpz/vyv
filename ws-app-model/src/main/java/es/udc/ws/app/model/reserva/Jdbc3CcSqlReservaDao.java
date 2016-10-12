package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;




public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDAO{

	@Override
	public Reserva create(Connection connection, Reserva r) {
		/* Create "queryString". */
        String queryString = "INSERT INTO RESERVA"
                + " (COD_OFERTA, EMAIL, TARJETA_CREDITO,"
                + " ESTADO, FECHA_RESERVA, PRECIO_OFERTA) VALUES (?, ?, ?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

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

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long codReserva = resultSet.getLong(1);

            /* Return reserva. */
            return new Reserva(codReserva, r.getCodOferta(), r.getEmail(),
                    r.getTarjetaCredito(), r.getFechaReserva(), r.getEstado(), r.getPrecioOferta());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

}
