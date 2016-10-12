package es.udc.ws.app.test.model;

import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;
import static es.udc.ws.app.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.MAX_DISCOUNT;
import static es.udc.ws.app.model.util.ModelConstants.MAX_COMISSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.exceptions.InvalidOfferDeleteException;
import es.udc.ws.app.exceptions.InvalidOfferReclamationException;
import es.udc.ws.app.exceptions.InvalidOfferReservationException;
import es.udc.ws.app.exceptions.InvalidOfferUpdateException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaService;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.dto.EstadoReserva;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class OfertaServiceTest {

	private final long NON_EXISTENT_OFERTA_ID = -1;
	private final String USER_ID = "ws-user";

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static OfertaService ofertaService = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.app.model.ofertaservice.OfertaService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(OFERTA_DATA_SOURCE, dataSource);

		ofertaService = OfertaServiceFactory.getService();

	}
	
	private Oferta getValidOferta(String nombre) {
		
		Calendar limFechaReserva=Calendar.getInstance();
		Calendar limfechaOferta=Calendar.getInstance();
		limFechaReserva.add(Calendar.DAY_OF_MONTH, 3);
		limfechaOferta.add(Calendar.DAY_OF_MONTH, 5);
		
		return new Oferta(nombre, "Oferta description", limFechaReserva, limfechaOferta,  
				30.00, 5.00, 2.00, true);
	}
	
	private Oferta getValidOferta(String nombre, String descripcion) {
		
		Calendar limFechaReserva=Calendar.getInstance();
		Calendar limfechaOferta=Calendar.getInstance();
		limFechaReserva.add(Calendar.DAY_OF_MONTH, 3);
		limfechaOferta.add(Calendar.DAY_OF_MONTH, 5);
		
		return new Oferta(nombre, descripcion, limFechaReserva, limfechaOferta,  
				30.00, 5.00, 2.00, true);
	}
	
	private Oferta getValidOferta() {
		return getValidOferta("Nombre oferta");
	}
	
	@Test
	public void testCrearOfertaYBuscarOferta() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferDeleteException {

		Oferta o = getValidOferta();
		Oferta createdOferta = null;
		
		createdOferta = ofertaService.crearOferta(o);
		Oferta foundOferta = ofertaService.buscarOfertaID(createdOferta.getCodOferta());
		
		assertEquals(createdOferta, foundOferta);

		// Clear Database
		ofertaService.borrarOferta(createdOferta.getCodOferta());

	}

	@Test
	public void testCrearInvalidOferta() throws InvalidOfferDeleteException, InstanceNotFoundException {

		Oferta o = getValidOferta();
		Oferta createdOferta = null;
		boolean exceptionCatched = false;

		try {
			// Check nombre oferta not null
			o.setNombre(null);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check nombre oferta not empty
			exceptionCatched = false;
			o = getValidOferta();
			o.setNombre("");
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check descripcion oferta not null
			exceptionCatched = false;
			o = getValidOferta();
			o.setDescripcion(null);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check descripcion oferta not empty
			exceptionCatched = false;
			o = getValidOferta();
			o.setDescripcion("");
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva not null
			exceptionCatched = false;
			o = getValidOferta();
			o.setFechaLimiteReserva(null);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva in the future
			exceptionCatched = false;
			o = getValidOferta();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -5);
			o.setFechaLimiteReserva(calendar);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteOferta oferta not null
			exceptionCatched = false;
			o = getValidOferta();
			o.setFechaLimiteOferta(null);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteOferta in the future
			exceptionCatched = false;
			o = getValidOferta();
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -5);
			o.setFechaLimiteOferta(calendar);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva < fehaLimiteOferta in the future
			exceptionCatched = false;
			o = getValidOferta();
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.DAY_OF_MONTH, 4);
			o.setFechaLimiteReserva(calendar1);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.DAY_OF_MONTH, 3);
			o.setFechaLimiteOferta(calendar2);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check precioReal oferta >= 0
			exceptionCatched = false;
			o = getValidOferta();
			o.setPrecioReal((short) -1);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check precioReal <= MAX_PRICE
			exceptionCatched = false;
			o = getValidOferta();
			o.setPrecioReal((short) (MAX_PRICE + 1));
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}			
			
			// Check precioDescontado oferta >= 0
			exceptionCatched = false;
			o = getValidOferta();
			o.setPrecioDescontado((short) -1);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check precioDescontado <= MAX_DISCOUNT
			exceptionCatched = false;
			o = getValidOferta();
			o.setPrecioDescontado((short) (MAX_DISCOUNT + 1));
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			
			// Check comisionVenta oferta >= 0
			exceptionCatched = false;
			o = getValidOferta();
			o.setComisionVenta((short) -1);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check comisionVenta <= MAX_COMISSION
			exceptionCatched = false;
			o = getValidOferta();
			o.setComisionVenta((short) (MAX_COMISSION + 1));
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			
			// Check valida oferta true
			exceptionCatched = false;
			o = getValidOferta();
			o.setValida(false);
			try {
				createdOferta = ofertaService.crearOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (!exceptionCatched) {
				// Clear Database
				if (createdOferta != null)
					ofertaService.borrarOferta(createdOferta.getCodOferta());
			}
		}

	}
	

	@Test
	public void testActualizarOfertaSinReservas() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException, InvalidOfferDeleteException {

		Oferta o = null; 
		try {
			o = ofertaService.crearOferta(getValidOferta()); 
			o.setNombre("new offer");
			o.setDescripcion("new description");
			Calendar fecha1= Calendar.getInstance();
			fecha1.add(Calendar.DAY_OF_MONTH, 1);
			Calendar fecha2= Calendar.getInstance();
			fecha2.add(Calendar.DAY_OF_MONTH, 3);
			o.setFechaLimiteReserva(fecha1);
			o.setFechaLimiteOferta(fecha2);
			o.setPrecioReal(45.00);
			o.setPrecioDescontado(2.50);
			o.setComisionVenta(3.00);
			
			ofertaService.actualizarOferta(o);

			Oferta updatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertEquals(o, updatedOferta);
			
		} finally {
			// Clear Database
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}
	
	@Test
	public void testActualizarOfertaConReservas() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException, InvalidOfferDeleteException, InvalidOfferReservationException {

		Oferta o = null;
		Long codReserva = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			codReserva =ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			
			o.setNombre("new offer");
			o.setDescripcion("new description");
			Calendar fecha1= Calendar.getInstance();
			fecha1.add(Calendar.DAY_OF_MONTH, 2);
			Calendar fecha2= Calendar.getInstance();
			fecha2.add(Calendar.DAY_OF_MONTH, 7);
			o.setFechaLimiteReserva(fecha1);
			o.setFechaLimiteOferta(fecha2);
			o.setPrecioReal(45.00);
			o.setPrecioDescontado(2.50);
			o.setComisionVenta(3.00);
			o.setPrecioDescontado(2.00);
			ofertaService.actualizarOferta(o);

			Oferta updatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertEquals(o, updatedOferta);
			
			
			Oferta beforeUpdatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			Calendar fecha3= Calendar.getInstance();
			fecha3.add(Calendar.DAY_OF_MONTH, 6);
			o.setFechaLimiteOferta(fecha3);
			
			boolean exceptionCatched = false;
			try {
				ofertaService.actualizarOferta(o);
			} catch (InvalidOfferUpdateException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			Oferta afterUpdatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertEquals(beforeUpdatedOferta, afterUpdatedOferta);
			
			
			
			beforeUpdatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setPrecioDescontado(7.00);
			
			exceptionCatched = false;
			try {
				ofertaService.actualizarOferta(o);
			} catch (InvalidOfferUpdateException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			afterUpdatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertEquals(beforeUpdatedOferta, afterUpdatedOferta);

		} finally {
			// Clear Database
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}

	@Test
	public void testActualizarOfertaInvalida() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException, InvalidOfferDeleteException {

		Oferta o = null;
		boolean exceptionCatched = false;

		try {
			o = ofertaService.crearOferta(getValidOferta());
			
			// Check nombre oferta not null
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setNombre(null);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check nombre oferta not empty
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setNombre("");
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check descripcion oferta not null
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setDescripcion(null);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check descripcion oferta not empty
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setDescripcion("");
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva oferta not null
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setFechaLimiteReserva(null);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva in the future
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -5);
			o.setFechaLimiteReserva(calendar);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteOferta oferta not null
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setFechaLimiteOferta(null);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteOferta in the future
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -5);
			o.setFechaLimiteOferta(calendar);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check fechaLimiteReserva < fechaLimiteOferta in the future
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.DAY_OF_MONTH, 4);
			o.setFechaLimiteReserva(calendar1);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.add(Calendar.DAY_OF_MONTH, 3);
			o.setFechaLimiteOferta(calendar2);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check precioReal oferta >= 0
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setPrecioReal((short) -1);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check precioReal <= MAX_PRICE
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setPrecioReal((short) (MAX_PRICE + 1));
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}			
			
			// Check precioDescontado oferta >= 0
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setPrecioDescontado((short) -1);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check precioDescontado <= MAX_DISCOUNT
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setPrecioDescontado((short) (MAX_DISCOUNT + 1));
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			
			// Check comisionVenta oferta >= 0
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setComisionVenta((short) -1);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			// Check comisionVenta <= MAX_COMISSION
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setComisionVenta((short) (MAX_COMISSION + 1));
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			
			// Check valida oferta true
			exceptionCatched = false;
			o = ofertaService.buscarOfertaID(o.getCodOferta());
			o.setValida(false);
			try {
				ofertaService.actualizarOferta(o);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testActualizarOfertaInexistente() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException {

		Oferta o = getValidOferta();
		o.setCodOferta(NON_EXISTENT_OFERTA_ID);
		ofertaService.actualizarOferta(o);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testBuscarOfertaInexistente() throws InstanceNotFoundException {

		ofertaService.buscarOfertaID(NON_EXISTENT_OFERTA_ID);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testBorrarOfertaSinReservas() throws InstanceNotFoundException, InvalidOfferDeleteException, InputValidationException {

		Oferta o = ofertaService.crearOferta(getValidOferta());
		boolean exceptionCatched = false;
		try {
			ofertaService.borrarOferta(o.getCodOferta());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(!exceptionCatched);

		ofertaService.buscarOfertaID(o.getCodOferta());

	}
	
	@Test
	public void testBorrarOfertaConReservas() throws InstanceNotFoundException, InvalidOfferDeleteException, InputValidationException, InvalidOfferReservationException {

		Oferta o = null;
		Long codReserva = null;
		try{
			o = ofertaService.crearOferta(getValidOferta());
			codReserva = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			
			boolean exceptionCatched = false;
			try {
				ofertaService.borrarOferta(o.getCodOferta());
			} catch (InvalidOfferDeleteException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			Oferta foundOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertEquals(o, foundOferta);
	
		} finally {
			// Clear Database
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (o != null)
			ofertaService.borrarOferta(o.getCodOferta());
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testBorrarOfertaInexistente() throws InstanceNotFoundException, InvalidOfferDeleteException {

		ofertaService.borrarOferta(NON_EXISTENT_OFERTA_ID);

	}
	
	@Test
	public void testInvalidarOferta() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferUpdateException, InvalidOfferDeleteException, InvalidOfferReservationException, InterruptedException {

		Oferta o = null;
		Oferta o2 = null;
		List<Reserva> reservas = new LinkedList<Reserva>();
		Long codReserva1 = null;	
		Long codReserva2 = null;
		boolean estadoCorrecto = false;
		
		try {
			o = ofertaService.crearOferta(getValidOferta());
			o2 = ofertaService.crearOferta(getValidOferta());
			codReserva1 = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			Reserva r1 = ofertaService.buscarReserva(codReserva1);
			reservas.add(r1);
			codReserva2 = ofertaService.hacerReserva("adri", "1234567890123457", o.getCodOferta());
			Reserva r2 = ofertaService.buscarReserva(codReserva2);
			reservas.add(r2);
			
			for (Reserva r : reservas){
				estadoCorrecto = false;
				if (r.getEstado() == EstadoReserva.ACTIVA){
					estadoCorrecto = true;
				}
				assertTrue(estadoCorrecto);
			}
			
			ofertaService.invalidarOferta(o.getCodOferta());
			
			Oferta updatedOferta = ofertaService.buscarOfertaID(o.getCodOferta());
			assertFalse(updatedOferta.isValida());
			
			List<Reserva> updatedReservas = ofertaService.obtenerReservas(o.getCodOferta());
			
			for (Reserva r : updatedReservas) {
				estadoCorrecto = false;
				if (r.getEstado() == EstadoReserva.ANULADA){
					estadoCorrecto = true;
				}
				assertTrue(estadoCorrecto);
			}
			
			boolean exceptionCatched = false;
			try {
				ofertaService.invalidarOferta(o.getCodOferta());
			} catch (InvalidOfferUpdateException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			Calendar limite1 = Calendar.getInstance();
			limite1.add(Calendar.SECOND, 1);
			Calendar limite2 = Calendar.getInstance();
			limite2.add(Calendar.SECOND, 2);
			o2.setFechaLimiteReserva(limite1);
			o2.setFechaLimiteOferta(limite2);
			ofertaService.actualizarOferta(o2);
			
			Thread.sleep(4000);
			
			exceptionCatched = false;
			try {
				ofertaService.invalidarOferta(o.getCodOferta());
			} catch (InvalidOfferUpdateException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			

		} finally {
			// Clear Database
			if (codReserva1 != null)
				ofertaService.borrarReserva(codReserva1);
			if (codReserva2 != null)
				ofertaService.borrarReserva(codReserva2);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
			if (o2 != null)
				ofertaService.borrarOferta(o2.getCodOferta());
		}

	}
	
	
	@Test
	public void testBuscarOfertas() throws InvalidOfferDeleteException, InstanceNotFoundException, InputValidationException, InvalidOfferUpdateException {

		List<Oferta> ofertas = new LinkedList<Oferta>();
		Oferta oferta1 = null;
		Oferta oferta2 = null;
		Oferta oferta3 = null;

		try {
			oferta1 = ofertaService.crearOferta(getValidOferta("oferta 1","descripcion numero 1"));
			ofertas.add(oferta1);
			oferta2 = ofertaService.crearOferta(getValidOferta("oferta 2","descripcion numero 2"));
			ofertas.add(oferta2);
			oferta3 = ofertaService.crearOferta(getValidOferta("oferta 3","descripcion numero 3"));
			ofertas.add(oferta3);
			
			
			ofertaService.invalidarOferta(oferta2.getCodOferta());
			
			List<Oferta> foundOfertas = ofertaService.buscarOfertas("descripcion", true, 
					Calendar.getInstance());
			assertEquals(3, foundOfertas.size());
			
			
			foundOfertas = ofertaService.buscarOfertas(null, true, null);
			assertEquals(3, foundOfertas.size());
			
			foundOfertas = ofertaService.buscarOfertas(null, false, null);
			assertEquals(2, foundOfertas.size());
			
			assertEquals(ofertas.get(0), foundOfertas.get(0));
			assertEquals(ofertas.get(2), foundOfertas.get(1));

			foundOfertas = ofertaService.buscarOfertas("nu descripcion 1", true, 
					Calendar.getInstance());
			assertEquals(1, foundOfertas.size());
			assertEquals(ofertas.get(0), foundOfertas.get(0));

			foundOfertas = ofertaService.buscarOfertas("descripcion nu 4", true, 
					Calendar.getInstance());
			assertEquals(0, foundOfertas.size());
			
			foundOfertas = ofertaService.buscarOfertas("descripcion", false, 
					Calendar.getInstance());
			assertEquals(2, foundOfertas.size());
			assertEquals(ofertas.get(0), foundOfertas.get(0));
			assertEquals(ofertas.get(2), foundOfertas.get(1));
			
			foundOfertas = ofertaService.buscarOfertas("descr", true, null);
			assertEquals(3, foundOfertas.size());
			
			foundOfertas = ofertaService.buscarOfertas(null, false, Calendar.getInstance());
			assertEquals(2, foundOfertas.size());
			assertEquals(ofertas.get(0), foundOfertas.get(0));
			assertEquals(ofertas.get(2), foundOfertas.get(1));
			
			
		} finally {
			// Clear Database
			for (Oferta o : ofertas) {
				if (o !=null)
					ofertaService.borrarOferta(o.getCodOferta());
			}
		}

	}

	@Test
	public void testHacerReserva() throws InstanceNotFoundException,
			InputValidationException, InvalidOfferDeleteException, InvalidOfferReservationException, InvalidOfferUpdateException {

		Oferta o = null;
		Long codReserva = null;
		Long codReserva2 = null;
		Long codReserva3 = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			
			codReserva = ofertaService.hacerReserva( USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			
			/* Buscar reserva. */
			Reserva foundReserva = ofertaService.buscarReserva(codReserva);
			
			/* Check reserva. */
			assertEquals(codReserva, foundReserva.getCodReserva());
			assertEquals(VALID_CREDIT_CARD_NUMBER,
					foundReserva.getTarjetaCredito());
			assertEquals(USER_ID, foundReserva.getEmail());
			assertEquals(o.getCodOferta(), foundReserva.getCodOferta());
			
			boolean exceptionCatched = false;
			try {
				codReserva2 = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			} catch (InvalidOfferReservationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			assertEquals(codReserva2, null);
			
			ofertaService.invalidarOferta(o.getCodOferta());
			exceptionCatched = false;
			try {
				codReserva3 = ofertaService.hacerReserva("adri", "1234567890123457", o.getCodOferta());
			} catch (InvalidOfferReservationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			assertEquals(codReserva3, null);
			
		} finally {
			/* Clear database: remove reserva (if created) and oferta. */
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (codReserva2 != null)
				ofertaService.borrarReserva(codReserva2);
			if (codReserva3 != null)
				ofertaService.borrarReserva(codReserva3);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}
	
	@Test
	public void testHacerReservaFueraDePlazo() throws InstanceNotFoundException,
			InputValidationException, InvalidOfferDeleteException, InvalidOfferReservationException, InvalidOfferUpdateException, InterruptedException {

		Oferta o = null;
		Long codReserva = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			
			Calendar limite = Calendar.getInstance();
			limite.add(Calendar.SECOND, 1);
			o.setFechaLimiteReserva(limite);
			ofertaService.actualizarOferta(o);
			
			Thread.sleep(2000);
			
			boolean exceptionCatched = false;
			try {
				codReserva = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			} catch (InvalidOfferReservationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			assertEquals(codReserva, null);
			
			
		} finally {
			/* Clear database: remove reserva (if created) and oferta. */
			if (codReserva != null){
				ofertaService.borrarReserva(codReserva);
			}
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}


	@Test(expected = InputValidationException.class)
	public void testHacerReservaConInvalidCreditCard() throws 
		InputValidationException, InstanceNotFoundException, InvalidOfferReservationException, InvalidOfferDeleteException {

		Oferta o = null;
		Long codReserva = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			codReserva = ofertaService.hacerReserva(USER_ID, INVALID_CREDIT_CARD_NUMBER, 
					o.getCodOferta());
		} finally {
			// Clear database.
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testHacerReservaNonExistentOferta() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferReservationException {
			
		Long codReserva = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, 
				NON_EXISTENT_OFERTA_ID);
		/* Clear database. */
		ofertaService.borrarReserva(codReserva);

	}
	
	@Test
	public void testObtenerReservas() throws InvalidOfferDeleteException, InstanceNotFoundException, InputValidationException, InvalidOfferUpdateException, InvalidOfferReclamationException, InvalidOfferReservationException {

		Oferta o = null;
		Oferta o2 = null;
		Long codReserva1 = null;
		Long codReserva2 = null;
		Long codReserva3 = null;
		
		try {
			o = ofertaService.crearOferta(getValidOferta("oferta 1"));
			o2= ofertaService.crearOferta(getValidOferta("oferta 2"));
			codReserva1 = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER,o.getCodOferta());
			Reserva reserva1 = ofertaService.buscarReserva(codReserva1);
			codReserva2 = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER,o2.getCodOferta());
			Reserva reserva2 = ofertaService.buscarReserva(codReserva2);
			codReserva3 = ofertaService.hacerReserva("adrian", "1234567890123457",o.getCodOferta());
			Reserva reserva3 = ofertaService.buscarReserva(codReserva3);
			
			
			List<Reserva> foundReservas = ofertaService.obtenerReservas(o.getCodOferta());
			assertEquals(2, foundReservas.size());
			assertEquals(reserva1, foundReservas.get(0));
			assertEquals(reserva3, foundReservas.get(1));
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, true);
			assertEquals(2, foundReservas.size());
			assertEquals(reserva1, foundReservas.get(0));
			assertEquals(reserva2, foundReservas.get(1));
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, false);
			assertEquals(2, foundReservas.size());
			assertEquals(reserva1, foundReservas.get(0));
			assertEquals(reserva2, foundReservas.get(1));
			
			ofertaService.reclamarReserva(reserva1.getCodReserva());
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, true);
			assertEquals(2, foundReservas.size());
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, false);
			assertEquals(1, foundReservas.size());
			
			ofertaService.invalidarOferta(o2.getCodOferta());
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, true);
			assertEquals(2, foundReservas.size());
			
			foundReservas = ofertaService.obtenerReservasUsuario(USER_ID, false);
			assertEquals(0, foundReservas.size());
			
			
		} finally {
			// Clear Database
			if (codReserva1 != null)
					ofertaService.borrarReserva(codReserva1);
			if (codReserva2 != null)
				ofertaService.borrarReserva(codReserva2);
			if (codReserva3 != null)
				ofertaService.borrarReserva(codReserva3);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
			if (o2 != null)
				ofertaService.borrarOferta(o2.getCodOferta());
		}

	}
	
	@Test
	public void testReclamarReserva() throws InputValidationException,
			InstanceNotFoundException, InvalidOfferDeleteException, InvalidOfferReclamationException, InvalidOfferUpdateException, InvalidOfferReservationException {

		Oferta o = null;
		Long codReserva = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			codReserva = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			Reserva r = ofertaService.buscarReserva(codReserva);
			
			boolean estadoCorrecto = false;
			if (r.getEstado() == EstadoReserva.ACTIVA){
				estadoCorrecto = true;
			}
			assertTrue(estadoCorrecto);
			ofertaService.reclamarReserva(r.getCodReserva());
			
			Reserva foundReserva= ofertaService.buscarReserva(r.getCodReserva());
			
			estadoCorrecto = false;
			if (foundReserva.getEstado() == EstadoReserva.RECLAMADA){
				estadoCorrecto = true;
			}
			assertTrue(estadoCorrecto);
			
			
			boolean exceptionCatched = false;
			try {
				ofertaService.reclamarReserva(r.getCodReserva());
			} catch (InvalidOfferReclamationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			
			exceptionCatched = false;
			ofertaService.invalidarOferta(o.getCodOferta());
			try {
				ofertaService.reclamarReserva(r.getCodReserva());
			} catch (InvalidOfferReclamationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			// Clear Database
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}
	
	@Test
	public void testReclamarReservaFueraDePlazo() throws InstanceNotFoundException,
			InputValidationException, InvalidOfferDeleteException, InvalidOfferReservationException, InvalidOfferUpdateException, InterruptedException {

		Oferta o = null;
		Long codReserva = null;
		try {
			o = ofertaService.crearOferta(getValidOferta());
			
			Calendar limite1 = Calendar.getInstance();
			limite1.add(Calendar.SECOND, 1);
			Calendar limite2 = Calendar.getInstance();
			limite2.add(Calendar.SECOND, 2);
			o.setFechaLimiteReserva(limite1);
			o.setFechaLimiteOferta(limite2);
			ofertaService.actualizarOferta(o);
			
			codReserva = ofertaService.hacerReserva(USER_ID, VALID_CREDIT_CARD_NUMBER, o.getCodOferta());
			
			Thread.sleep(4000);
			
			boolean exceptionCatched = false;
			try {
				ofertaService.reclamarReserva(codReserva);
			} catch (InvalidOfferReclamationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			
			
		} finally {
			/* Clear database: remove reserva (if created) and oferta. */
			if (codReserva != null)
				ofertaService.borrarReserva(codReserva);
			if (o != null)
				ofertaService.borrarOferta(o.getCodOferta());
		}

	}
	
	
}
