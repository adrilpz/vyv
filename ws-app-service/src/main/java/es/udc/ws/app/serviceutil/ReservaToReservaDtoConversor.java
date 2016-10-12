package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.model.reserva.Reserva;

public class ReservaToReservaDtoConversor {

    public static List<ReservaDto> toReservaDtos(List<Reserva> reservas) {
        List<ReservaDto> reservaDtos = new ArrayList<>(reservas.size());
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            reservaDtos.add(toReservaDto(r));
        }
        return reservaDtos;
    }

    public static ReservaDto toReservaDto(Reserva r) {
        return new ReservaDto(r.getCodReserva(), r.getCodOferta(), r.getEmail(), 
        		r.getTarjetaCredito(), r.getFechaReserva(), r.getEstado(), r.getPrecioOferta());
    }

    public static Reserva toReserva(ReservaDto r) {
        return new Reserva(r.getCodReserva(), r.getCodOferta(), r.getEmail(), 
        		r.getTarjetaCredito(), r.getFechaReserva(), r.getEstado(), r.getPrecioOferta());
    }    
    
}