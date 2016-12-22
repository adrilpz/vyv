
package es.udc.pa.p007.apuestasapp.model.vvs.testautomation;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Calendar;
//import java.util.List;
//
//import org.graphwalker.core.machine.ExecutionContext;
//import org.graphwalker.java.annotation.GraphWalker;
//import org.hibernate.SessionFactory;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import es.udc.pa.p007.apuestasapp.model.apuestasservice.ApuestasService;
//import es.udc.pa.p007.apuestasapp.model.apuestasservice.EventoBlock;
//import es.udc.pa.p007.apuestasapp.model.apuestasservice.InputValidationException;
//import es.udc.pa.p007.apuestasapp.model.categoria.Categoria;
//import es.udc.pa.p007.apuestasapp.model.categoria.CategoriaDao;
//import es.udc.pa.p007.apuestasapp.model.evento.Evento;
//import es.udc.pa.p007.apuestasapp.model.evento.EventoDao;
//import es.udc.pa.p007.apuestasapp.model.userservice.UserService;
//import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
//import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
//
//@GraphWalker(value = "random(edge_coverage(100))", start = "e_CreateEvent")
//public class CreateAndSearchEventsTest extends ExecutionContext implements CreateAndSearchEvents {
//
//	@Autowired
//	private ApuestasService apuestasService;
//
//	@Autowired
//	private CategoriaDao categoriaDao;
//	
//	@Autowired
//	private EventoDao eventoDao;
//	
//	//VARIABLES GLOBALES
//	private Categoria futbol;
//	private Calendar fecha;
//	private Evento evento;
//	private EventoBlock eventoBlock;
//	private List<Evento> expectedEventos;
//	private int resultsNumber= 10;
//	
//	@Transactional
//	@Override
//	public void e_CreateAnotherEvent() {
//		System.out.println("Running: e_CreateAnotherEvent");
//		//Inicialización
////		fecha = Calendar.getInstance();
////		fecha.set(2017, 11, 24);
////		
////		//Llamada
////		evento = apuestasService.createEvento("Barsa - Madrid", fecha, futbol.getCodCategoria());
////		
//	}
//
//	@Transactional(readOnly=true)
//	@Override
//	public void e_SearchEvents() {
//		System.out.println("Running: e_SearchEvents");
//		//Llamada
////		eventoBlock = apuestasService.findEventos("madrid", futbol.getCodCategoria(), false, 0, resultsNumber);			
//	}
//
//	@Transactional
//	@Override
//	public void v_FoundEvents() {
//		System.out.println("Running: v_FoundEvents");
//		//Aserción
////		List<Evento> foundEventos = eventoBlock.getEventos();
////		if (expectedEventos.size()>resultsNumber)
////			assertEquals(resultsNumber, foundEventos.size());
////		else{
////			assertEquals(expectedEventos.size(), foundEventos.size());
////		}
////		
////		for (Evento e: expectedEventos){
////			eventoDao.remove(e.getCodEvento());
////		}
////		
////		categoriaDao.remove(futbol.getCodCategoria());
//		
//	}
//
//	@Transactional(readOnly=true)
//	@Override
//	public void v_CreatedEvent() {
//		System.out.println("Running: v_CreatedEvent");
//		//Aserción
////		Evento foundEvento=eventoDao.find(evento.getCodEvento());
////		assertEquals(evento, foundEvento);
//		
//	}
//
//	@Transactional
//	@Override
//	public void e_CreateEvent() {
//		System.out.println("Running: e_CreateEvent");
//		//Inicialización
//		//fecha = Calendar.getInstance();
//		//fecha.set(2017, 11, 24);
//		//futbol = new Categoria("Fútbol");
//		//categoriaDao.save(futbol);
//		
//		//Llamada
//		//evento = apuestasService.createEvento("Barsa - Madrid", fecha, futbol.getCodCategoria());
//	}
//	
//}