package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.model.oferta.Oferta;

public class OfertaToOfertaDtoUsuarioConversor {

    public static List<OfertaDtoUsuario> toOfertaDtosUsuario(List<Oferta> ofertas, List<Calendar> fechas) {
        List<OfertaDtoUsuario> ofertaDtosUsuario = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); i++) {
            Oferta o = ofertas.get(i);
            Calendar fecha = fechas.get(i);
            ofertaDtosUsuario.add(toOfertaDtoUsuario(o,fecha));
        }
        return ofertaDtosUsuario;
    }
    
    public static OfertaDtoUsuario toOfertaDtoUsuario(Oferta o, Calendar fechaReserva) {
        return new OfertaDtoUsuario(o.getDescripcion(), fechaReserva, 
        		o.getPrecioDescontado());
    }    
}
