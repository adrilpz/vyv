package es.udc.ws.app.serviceutil;


import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.model.oferta.Oferta;

public class OfertaToOfertaDtoConversor {

    public static List<OfertaDto> toOfertaDtos(List<Oferta> ofertas) {
        List<OfertaDto> ofertaDtos = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); i++) {
            Oferta o = ofertas.get(i);
            ofertaDtos.add(toOfertaDto(o));
        }
        return ofertaDtos;
    }

    public static OfertaDto toOfertaDto(Oferta o) {
        return new OfertaDto(o.getCodOferta(), o.getNombre(), o.getDescripcion(), 
        		o.getFechaLimiteReserva(), o.getFechaLimiteOferta(), o.getPrecioReal(),
        		o.getPrecioDescontado(), o.getComisionVenta(), o.isValida());
    }

    public static Oferta toOferta(OfertaDto o) {
        return new Oferta(o.getCodOferta(), o.getNombre(), o.getDescripcion(), 
        		o.getFechaLimiteReserva(), o.getFechaLimiteOferta(), o.getPrecioReal(),
        		o.getPrecioDescontado(), o.getComisionVenta(), o.isValida());
    }    
    
}