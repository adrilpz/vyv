package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.ClientOfertaServiceFactory;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaDtoUsuario;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OfertaServiceClient {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientOfertaService clientOfertaService =
                ClientOfertaServiceFactory.getService();
        
        if("-c".equalsIgnoreCase(args[0])) {
            validateArgs(args, 9, new int[] {5, 6, 7});

            /*[crearOferta] OfertaServiceClient -c <nombre> <descripcion> "
    		+ "<fechaLimiteReserva> <fechaLimiteOferta> <precioReal> "
    		+ "<precioDescontado> <comisionVenta> <valida>\n*/
            try {
            	SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            	
            	String stringFechaLimiteReserva = args [3];
            	Calendar calendarFechaLimiteReserva = Calendar.getInstance();
            	calendarFechaLimiteReserva.setTime(curFormater.parse(stringFechaLimiteReserva));
            	
               	String stringFechaLimiteOferta = args [4];
            	Calendar calendarFechaLimiteOferta = Calendar.getInstance();
            	calendarFechaLimiteOferta.setTime(curFormater.parse(stringFechaLimiteOferta));
            	
                OfertaDto o = clientOfertaService.crearOferta(new OfertaDto(null, args[1], args[2], 
                		calendarFechaLimiteReserva, calendarFechaLimiteOferta, Double.valueOf(args[5]), 
        		Double.valueOf(args[6]),Double.valueOf(args[7]),Boolean.valueOf(args[8])));
				
                System.out.println("Oferta " + o.getCodOferta() + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
		
        }else if("-b".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [borrarOferta] OfertaServiceClient -b <codOferta>

            try {
            	clientOfertaService.borrarOferta(Long.parseLong(args[1]));

                System.out.println("Oferta con código " + args[1] +
                        " eliminada correctamente");

            } catch (NumberFormatException | InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-a".equalsIgnoreCase(args[0])) {
           validateArgs(args, 10, new int[] {1, 6, 7, 8});

           /* [actualizarOferta] OfertaServiceClient -a <codOferta> <nombre> <descripcion> 
           <fechaLimiteReserva> <fechaLimiteOferta> <precioReal> <precioDescontado> 
           <comisionVenta> <valida>*/
           
           try {
        	   SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
           	
        	   String stringFechaLimiteReserva = args [4];
        	   Calendar calendarFechaLimiteReserva = Calendar.getInstance();
        	   calendarFechaLimiteReserva.setTime(curFormater.parse(stringFechaLimiteReserva));
           	
        	   String stringFechaLimiteOferta = args [5];
        	   Calendar calendarFechaLimiteOferta = Calendar.getInstance();
        	   calendarFechaLimiteOferta.setTime(curFormater.parse(stringFechaLimiteOferta));
               
        	   clientOfertaService.actualizarOferta(new OfertaDto(Long.valueOf(args[1]), args[2], 
        			   args[3], calendarFechaLimiteReserva, calendarFechaLimiteOferta, 
        			   Double.valueOf(args[6]), Double.valueOf(args[7]),Double.valueOf(args[8]),Boolean.valueOf(args[9])));

               System.out.println("Oferta " + args[1] + " actualizada correctamente");

            } catch (NumberFormatException | InputValidationException |
                     InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-i".equalsIgnoreCase(args[0])) {
        	validateArgs(args, 2, new int[] {1});

            // [invalidarOferta] OfertaServiceClient -i <codOferta> 
            
            try {
            	clientOfertaService.invalidarOferta(Long.valueOf(args[1]));
            	System.out.println("Oferta " + args[1] + " invalidada correctamente");

             } catch (Exception ex) {
                 ex.printStackTrace(System.err);
             }

        } else if("-d".equalsIgnoreCase(args[0])) {
        	validateArgs(args, 2, new int[] {1});

            // [buscarOfertaID] OfertaServiceClient -d <codOferta>
            
            try {
            	OfertaDto o = clientOfertaService.buscarOfertaID(Long.valueOf(args[1]));
            	System.out.println("Oferta " + args[1] + " encontrada");
            	
            	System.out.println("Id: " + o.getCodOferta() +
                        " Nombre: " + o.getNombre() +
                        " Descripcion: " + o.getDescripcion() +
                        " FechaLimiteReserva: " + o.getFechaLimiteReserva() +
                        " FechaLimiteOferta: " + o.getFechaLimiteOferta() +
                        " PrecioReal: " + o.getPrecioReal() +
                        " PrecioDescontado: " + o.getPrecioDescontado() +
                        " ComisionVenta: " + o.getComisionVenta() +
                		" Valida: " + o.isValida());
            	System.out.println("La comision de venta tiene valor -1, lo que indica el valor no se devuelve");

             } catch (Exception ex) {
                 ex.printStackTrace(System.err);
             }
            
        } else if("-f".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [buscarOfertas] OfertaServiceClient -f <keywords>

            try {
            	System.out.println(args[1]);
                List<OfertaDto> ofertas = clientOfertaService.buscarOfertas(args[1]);
                System.out.println("Encontrada " + ofertas.size() +
                        " oferta(s) con palabras clave:'" + args[1] + "'");
                for (OfertaDto o : ofertas) {
                	System.out.println("Id: " + o.getCodOferta() +
                            " Nombre: " + o.getNombre() +
                            " Descripcion: " + o.getDescripcion() +
                            " FechaLimiteReserva: " + o.getFechaLimiteReserva() +
                            " FechaLimiteOferta: " + o.getFechaLimiteOferta() +
                            " PrecioReal: " + o.getPrecioReal() +
                            " PrecioDescontado: " + o.getPrecioDescontado() +
                            " ComisionVenta: " + o.getComisionVenta() +
                    		" Valida: " + o.isValida());
                }
                System.out.println("La comision de venta tiene valor -1, lo que indica el valor no se devuelve");
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-h".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {3});

            // [hacerReserva] OfertaServiceClient -h <email> <tarjetaCredito> <codOferta> 

            Long codReserva;
            try {
            	codReserva = clientOfertaService.hacerReserva((args[1]),
                        args[2], Long.parseLong(args[3]));
            	
                System.out.println("Oferta " + args[3] +
                        " reservada correctamente con número de reserva " +
                        codReserva);

            } catch (NumberFormatException | InstanceNotFoundException |
                     InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

             
        } else if("-o".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [obtenerReservas] OfertaServiceClient -o <codOferta>

            try {
                List<ReservaDto> reservas = clientOfertaService.obtenerReservas(Long.valueOf(args[1]));
                System.out.println("Encontradas " + reservas.size() +
                        " reservas(s) de la oferta '" + args[1] + "'");
                for (ReservaDto r : reservas) {
                    System.out.println("CodReserva: " + r.getCodReserva() +
                    		" CodOferta: " + r.getCodOferta() +
                            " PrecioOferta: " + r.getPrecioOferta() +
                            " FechaReserva: " + r.getFechaReserva() +
                            " Estado: " + r.getEstado() +
                            " TarjetaCredito: " + r.getTarjetaCredito() +
                            " Email: " + r.getEmail());
                }
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-u".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[] {});

            //[obtenerReservasUsuario] OfertaServiceClient -u <email> <todas>\n"

            try {
                List<ReservaDto> reservas = clientOfertaService.obtenerReservasUsuario(args[1], Boolean.valueOf(args[2]));
                System.out.println("Encontradas " + reservas.size() +
                        " reservas(s) del usuario '" + args[1] + "'");
                for (ReservaDto r : reservas) {
                    System.out.println("CodReserva: " + r.getCodReserva() +
                    		" CodOferta: " + r.getCodOferta() +
                            " PrecioOferta: " + r.getPrecioOferta() +
                            " FechaReserva: " + r.getFechaReserva() +
                            " Estado: " + r.getEstado() +
                            " TarjetaCredito: " + r.getTarjetaCredito() +
                            " Email: " + r.getEmail());
                }
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-r".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [reclamarReserva] OfertaServiceClient -r <codReserva> 
            
            try {
            	clientOfertaService.reclamarReserva(Long.valueOf(args[1]));
            	System.out.println("Reserva " + args[1] + " reclamada correctamente");

             } catch (Exception ex) {
                 ex.printStackTrace(System.err);
             }
            
        } else if("-g".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [buscarOfertasUsuario] OfertaServiceClient -g <email>

            try {
                List<OfertaDtoUsuario> ofertas = clientOfertaService.buscarOfertasUsuario(args[1]);
                System.out.println("Encontrada " + ofertas.size() +
                        " oferta(s) del usuario:'" + args[1] + "'");
                for (OfertaDtoUsuario o : ofertas) {
                	System.out.println(
                            " Descripcion:" + o.getDescripcion() +
                            " FechaLimiteReserva: " + o.getFechaReserva() +
                            " PrecioDescontado: " + o.getPrecioDescontado());
                }
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [crearOferta] OfertaServiceClient -c <nombre> <descripcion> "
                		+ "<fechaLimiteReserva> <fechaLimiteOferta> <precioReal> "
                		+ "<precioDescontado> <comisionVenta> <valida>\n" +
                "    [borrarOferta] OfertaServiceClient -b <codOferta>\n" +
                "    [actualizarOferta] OfertaServiceClient -a <codOferta> <nombre> "
                		+ "<descripcion> <fechaLimiteReserva> "+"<fechaLimiteOferta> <precioReal> <precioDescontado> <comisionVenta> <valida>\n" +
                "    [invalidarOferta] OfertaServiceClient -i <codOferta>\n"+
                "    [buscarOfertaID] OfertaServiceClient -d <codOferta>\n"+
                "	 [buscarOfertas] OfertaServiceClient -f <keywords>\n" +
                "    [hacerReserva] OfertaServiceClient -h <email> <tarjetaCredito> <codOferta>\n " +
                "    [obtenerReservas] OfertaServiceClient -o <codOferta>\n" +
                "    [obtenerReservasUsuario] OfertaServiceClient -u <email> <todas>\n" +
                "    [reclamarReserva] OfertaServiceClient -r <codReserva>\n" +
                "    [buscarOfertasUsuario] OfertaServiceClient -g <email>\n");
    }

}