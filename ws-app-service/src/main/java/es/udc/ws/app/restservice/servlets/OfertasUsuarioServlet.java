package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoUsuarioConversor;
import es.udc.ws.app.soapservice.SoapInstanceNotFoundException;
import es.udc.ws.app.soapservice.SoapInstanceNotFoundExceptionInfo;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlOfertaDtoUsuarioConversor;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;


@SuppressWarnings("serial")
public class OfertasUsuarioServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String email = req.getParameter("email");
		List<OfertaDtoUsuario> ofertasUsuarioDtos = new ArrayList<OfertaDtoUsuario>();;
		try {
			ofertasUsuarioDtos = buscarOfertasUsuario(email);
		} catch (InstanceNotFoundException e) {
			ServletUtils
			.writeServiceResponse(resp,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor
							.toInstanceNotFoundException(e), null);
			return;
		}

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
				XmlOfertaDtoUsuarioConversor.toXml(ofertasUsuarioDtos), null);
	}
	
	public List<OfertaDtoUsuario> buscarOfertasUsuario(String email) 
			throws InstanceNotFoundException{
	
       List<Oferta> ofertas = new ArrayList<Oferta>();
       List<Calendar> fechas = new ArrayList<Calendar>();

       List<Reserva> reservas = OfertaServiceFactory.getService().obtenerReservasUsuario(email, true);
       for (Reserva r : reservas){
    	   ofertas.add(OfertaServiceFactory.getService().buscarOfertaID(r.getCodOferta()));
    	   fechas.add(r.getFechaReserva());
       }
	
       return OfertaToOfertaDtoUsuarioConversor.toOfertaDtosUsuario(ofertas,fechas);
   }
}
