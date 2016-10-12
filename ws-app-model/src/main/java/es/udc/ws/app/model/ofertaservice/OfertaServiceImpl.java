package es.udc.ws.app.model.ofertaservice;

import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;
import static es.udc.ws.app.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.MAX_DISCOUNT;
import static es.udc.ws.app.model.util.ModelConstants.MAX_COMISSION;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.oferta.SqlOfertaDAO;
import es.udc.ws.app.model.oferta.SqlOfertaDAOFactory;
import es.udc.ws.app.dto.EstadoReserva;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDAO;
import es.udc.ws.app.model.reserva.SqlReservaDAOFactory;
import es.udc.ws.app.validation.OfertaPropertyValidator;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;

public class OfertaServiceImpl implements OfertaService{
	
	 /*
     * IMPORTANT: Some JDBC drivers require "setTransactionIsolation" to
     * be called before "setAutoCommit".
     */

    private DataSource dataSource;
    private SqlOfertaDAO ofertaDao = null;
    private SqlReservaDAO reservaDao = null;

    public OfertaServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
        ofertaDao = SqlOfertaDAOFactory.getDao();
        reservaDao = SqlReservaDAOFactory.getDao();
    }
    
    
    private void validarOferta(Oferta o) throws InputValidationException {

        PropertyValidator.validateMandatoryString("nombre", o.getNombre());
        PropertyValidator.validateMandatoryString("descripcion",
                o.getDescripcion());
        OfertaPropertyValidator.validateFutureDate("fechaLimiteOferta", 
        		o.getFechaLimiteOferta());
        OfertaPropertyValidator.validateFutureDate("fechaLimiteReserva", 
        		o.getFechaLimiteReserva());
        OfertaPropertyValidator.validateDate1BeforeDate2("fechaLimiteReserva", 
        		"fechaLimiteOferta", o.getFechaLimiteReserva(), o.getFechaLimiteOferta());
        PropertyValidator.validateDouble("precioReal", o.getPrecioReal(), 0,
                MAX_PRICE);
        PropertyValidator.validateDouble("precioDescontado", o.getPrecioDescontado(), 0,
                MAX_DISCOUNT);
        PropertyValidator.validateDouble("comisionVenta", o.getComisionVenta(), 0,
                MAX_COMISSION);
        OfertaPropertyValidator.validateBooleanTrue("valida", o.isValida());
    }

	@Override
	public Oferta crearOferta(Oferta o) throws InputValidationException {
			
		validarOferta(o);
	        try (Connection connection = dataSource.getConnection()) {

	            try {

	                /* Prepare connection. */
	                connection
	                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	                connection.setAutoCommit(false);

	                /* Do work. */
	                Oferta createdOferta = ofertaDao.create(connection, o);

	                /* Commit. */
	                connection.commit();

	                return createdOferta;

	            } catch (SQLException e) {
	                connection.rollback();
	                throw new RuntimeException(e);
	            } catch (RuntimeException | Error e) {
	                connection.rollback();
	                throw e;
	            }

	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	}

	@Override
	public void actualizarOferta(Oferta o) throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException {
		
		validarOferta(o);
        try (Connection connection = dataSource.getConnection()) {
        	
            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                
                /* Do work. */
                if (reservaDao.ofertaSinReservas(connection, o.getCodOferta())){
                	ofertaDao.update(connection, o);
                	}
                else{
                	Oferta notUpdatedOferta = ofertaDao.findByID(connection, o.getCodOferta());
                	if (o.getFechaLimiteOferta().after(notUpdatedOferta.getFechaLimiteOferta())){
                		if(o.getPrecioDescontado() <= notUpdatedOferta.getPrecioDescontado()){
                			ofertaDao.update(connection, o);
                		}
                		else{
                			throw new InvalidOfferUpdateException("Actualización de Precio Descontado incorrecta");
                		}
                	}
                	else{
                		throw new InvalidOfferUpdateException("Actualización de Fecha Limite de Oferta incorrecta");
                	}
                }
                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}

	@Override
	public void borrarOferta(Long codOferta) throws InstanceNotFoundException, InvalidOfferDeleteException {
		try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                if (reservaDao.ofertaSinReservas(connection, codOferta)){
                	ofertaDao.remove(connection, codOferta);
                }
                else{
                	throw new InvalidOfferDeleteException("Eliminación de oferta incorrecta");
                }

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}

	@Override
	public void invalidarOferta(Long codOferta)
			throws InstanceNotFoundException, InputValidationException, InvalidOfferUpdateException {
		
		try (Connection connection = dataSource.getConnection()) {
        	
            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
        		Oferta o = ofertaDao.findByID(connection, codOferta);
        		if (o.isValida()){
        			Calendar now = Calendar.getInstance();
        			if (o.getFechaLimiteOferta().after(now)){
        				o.setValida(false);
        				ofertaDao.update(connection, o);
        			
        				if (!(reservaDao.ofertaSinReservas(connection, codOferta))){
        					reservaDao.anularReservas(connection, codOferta);
        				}
        			}
        			else{
        				throw new InvalidOfferUpdateException("Actualización de invalidez de la oferta incorrecta, oferta fuera de plazo");
        			}
        		}
        		else{
        			throw new InvalidOfferUpdateException("Actualización de invalidez de la oferta incorrecta, oferta ya invalidada");
        		}
                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }		
	}

	@Override
	public Oferta buscarOfertaID(Long codOferta)
			throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return ofertaDao.findByID(connection, codOferta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Oferta> buscarOfertas(String palabrasClave,
			boolean todas, Calendar fecha) {
		try (Connection connection = dataSource.getConnection()) {
            return ofertaDao.find(connection, palabrasClave, todas, fecha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public Long hacerReserva(String email, String tarjetaCredito, Long codOferta)
			throws InstanceNotFoundException, InputValidationException, InvalidOfferReservationException {
		
		PropertyValidator.validateCreditCard(tarjetaCredito);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Oferta oferta = ofertaDao.findByID(connection, codOferta);
                
                if (reservaDao.ofertaReservadaAnteriormentePorUsuario(connection, codOferta, email)){
                		throw new InvalidOfferReservationException("Reserva incorrecta, reserva anterior de esa oferta encontrada");
                }
                
                if (oferta.isValida()){
                	Calendar now = Calendar.getInstance();
                	if (oferta.getFechaLimiteReserva().after(now)){
                		Reserva r = reservaDao.create(connection, new Reserva(oferta.getCodOferta(), email,
                				tarjetaCredito, now, EstadoReserva.ACTIVA, oferta.getPrecioReal()));
                		
                        /* Commit. */
                        connection.commit();
                		
                		return r.getCodReserva();
                	}
                	else {
                		throw new InvalidOfferReservationException("Reserva incorrecta, fecha limite de reserva superada");
                	}
                }	
                else{
                	throw new InvalidOfferReservationException("Reserva incorrecta, la oferta ha sido invalidada");
                }

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public List<Reserva> obtenerReservas(Long codOferta)  throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
            return reservaDao.findByID(connection, codOferta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public List<Reserva> obtenerReservasUsuario(String email, boolean todas){
		try (Connection connection = dataSource.getConnection()) {
            return reservaDao.findByUser(connection, email, todas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void reclamarReserva(Long codReserva) throws InstanceNotFoundException, InvalidOfferReclamationException {

		try (Connection connection = dataSource.getConnection()) {
        	
            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Reserva r = reservaDao.find(connection, codReserva);
        		Oferta o = ofertaDao.findByID(connection, r.getCodOferta());
        		Calendar now = Calendar.getInstance();
        		if (o.isValida()){
        			if (o.getFechaLimiteOferta().after(now)){
        				if (r.getEstado()== EstadoReserva.ACTIVA){
        					r.setEstado(EstadoReserva.RECLAMADA);
        					reservaDao.update(connection, r);
        				}
        				else{
        					throw new InvalidOfferReclamationException("Reclamación de oferta incorrecta, reserva anulada o ya reclamada");
        				}
        			}	
        			else {
        				throw new InvalidOfferReclamationException("Reclamación de oferta incorrecta, fecha limite de disfrute de oferta superada");
        			}
        		}
        		else{
        			throw new InvalidOfferReclamationException("Reclamación de oferta incorrecta, oferta inválida");
        		}
                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }		
	}
	
	@Override
	public void borrarReserva(Long codReserva) throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                	reservaDao.remove(connection, codReserva);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	@Override
	public Reserva buscarReserva(Long codReserva)
			throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
        	return reservaDao.find(connection, codReserva);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}
