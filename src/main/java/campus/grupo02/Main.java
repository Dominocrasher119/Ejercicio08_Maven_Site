package campus.grupo02;

import java.util.Scanner;

public class Main {

    static int idCliente = 0;
    static int idHotel = 0;

    private static final String[] opcionesPrincipales = {"Clientes", "Hotel", "Temporada"};
    private static final String[] opcionesClientes = {"Alta clientes", "Modificar clientes", "Lista consulta", "Baja clientes"};
    private static final String[] opcionesHotel = {"Alta hotel", "Modificar hotel", "Baja hotel", "Ver hoteles"};
    private static final String[] opcionesTemporada = {"Alta temporada", "Consulta temporada", "Modificar temporada", "Baja temporada"};

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        System.out.println("Proyecto Cadena Hotelera Grupo 02 Fran, Jaume y Bernat");

        EntityManager bbdd = new EntityManager();
        boolean conex = bbdd.init();

        if (!conex) {
            System.out.println("No se ha podido establecer conexion");
        } else {
            System.out.println("Conexión establecida");
            System.out.println();

            do {
                opcion = Utils.menu("Menu principal", opcionesPrincipales);
                switch (opcion) {
                    case 1 -> {
                        System.out.println();
                        System.out.println("Has seleccionado Clientes! ");
                        int opcionClientes = Utils.menu("Menu Clientes", opcionesClientes);
                        opcionesClientes(opcionClientes);

                    }

                    case 2 -> {
                        System.out.println();
                        System.out.println("Has seleccionado Hoteles! ");
                        int opcionHoteles = Utils.menu("Menu Hoteles", opcionesHotel);
                        opcion_Hotel(opcionHoteles);
                    }

                    case 3 -> {
                        System.out.println();
                        System.out.println("Has seleccionado Temporada!");
                        int opcionTemporadas = Utils.menu("Menu Temporadas", opcionesTemporada);
                        opcionesTemporada(opcionTemporadas);

                    }

                    case 0 -> {
                        System.out.println("Saliendo...");
                        // Desconectar de la base de datos
                        bbdd.unLoad();
                    }
                    default -> {
                        System.out.println("Opción no válida. Intente de nuevo.");
                    }

                }

            } while (opcion != 0);

        }

    }

    public static void opcionesClientes(int opcionClientes) {
        switch (opcionClientes) {
            case 1 -> {
                System.out.println("Has seleccionado Alta clientes");
                String nombreCliente = Utils.askText("Introduce el nombre del cliente");
                String identificador = Utils.askText("Introduce el identificador del cliente con el siguiente formato (DNI/NIE)");
                String fechaNacimiento = Utils.askText("Introduce la fecha de nacimiento del cliente con el siguiente formato (dd-mm-yyyy)");
                //Convierte la fecha de nacimiento al formato yyyy-mm-dd para la BBDD:
                //Separa la fecha en partes
                String[] fechaParts = fechaNacimiento.split("-");
                if (fechaParts.length == 3) {
                    fechaNacimiento = fechaParts[2] + "-" + fechaParts[1] + "-" + fechaParts[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                Cliente cliente = Cliente.crearCliente(-1, nombreCliente, identificador, fechaNacimiento);
                boolean anadido = cliente.persist();
                if (anadido) {
                    System.out.println("Cliente añadido correctamente.");
                } else {
                    System.out.println("Error al añadir el cliente.");
                }
            }
            case 2 -> {
                System.out.println("Has seleccionado Modificar clientes");
                EntityManager.printClientes();
                System.out.println();
                String nombreCliente = Utils.askText("Introduce el nombre del cliente a modificar");
                System.out.println();
                idCliente = EntityManager.psSelectidCliente(nombreCliente);
                if (idCliente == -1) {
                    System.out.println("No se ha encontrado el cliente con ese nombre.");
                    break;
                }
                System.out.println("Has seleccionado el cliente con id: " + idCliente);
                //pedimos los datos a modificar:
                String nombreClienteMod = Utils.askText("Introduce el nuevo nombre del cliente");
                String identificadorMod = Utils.askText("Introduce el nuevo identificador del cliente con el siguiente formato (DNI/NIE)");
                String fechaNacimientoMod = Utils.askText("Introduce la nueva fecha de nacimiento del cliente con el siguiente formato (dd-mm-yyyy)");
                //Convierte la fecha de nacimiento al formato yyyy-mm-dd para la BBDD:
                //Separa la fecha en partes
                String[] fechaParts = fechaNacimientoMod.split("-");
                if (fechaParts.length == 3) {
                    fechaNacimientoMod = fechaParts[2] + "-" + fechaParts[1] + "-" + fechaParts[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                Cliente cliente = Cliente.crearCliente(idCliente, nombreClienteMod, identificadorMod, fechaNacimientoMod);
                //llamamos al metodo de la instancia para modificar el cliente:
                boolean modificado = cliente.merge();
                if (modificado) {
                    System.out.println("Cliente modificado correctamente.");
                } else {
                    System.out.println("Error al modificar el cliente.");
                }
            }
            case 3 -> {
                System.out.println("Has seleccionado Lista consulta");

                // Determinar si quiere ver todos o filtrar
                if (Utils.confirm("¿Desea filtrar la búsqueda?")) {
                    // Preguntar por qué campo quiere filtrar
                    String[] opcionesFiltro = {"Nombre", "Identificador", "Fecha de nacimiento", "Sin filtro"};
                    int opcionFiltro = Utils.menu("Seleccione criterio de filtrado", opcionesFiltro);
                    
                    String nombreBusqueda = null;
                    String identificadorBusqueda = null;
                    String fechaBusqueda = null;
                    
                    switch(opcionFiltro) {
                        case 1 -> nombreBusqueda = Utils.askText("Nombre");
                        case 2 -> identificadorBusqueda = Utils.askText("Identificador");
                        case 3 -> {
                            fechaBusqueda = Utils.askText("Fecha de nacimiento (formato dd-mm-yyyy)");
                            // Convertir la fecha al formato yyyy-mm-dd si se ha proporcionado
                            if (!fechaBusqueda.isEmpty()) {
                                String[] fechaParts = fechaBusqueda.split("-");
                                if (fechaParts.length == 3) {
                                    fechaBusqueda = fechaParts[2] + "-" + fechaParts[1] + "-" + fechaParts[0];
                                } else {
                                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                                    break;
                                }
                            }
                        }
                        case 4 -> System.out.println("Mostrando todos los clientes");
                    }

                    // Crear objeto cliente con los criterios
                    // Usamos el constructor vacío y luego los setters si queremos filtrar,
                    // o pasamos null/valores por defecto al método de fábrica si es para búsqueda.
                    // Para la búsqueda con LIKE, es común pasar los valores directamente.
                    // Si un campo es null en el objeto 'cliente' de búsqueda, EntityManager.Find lo ignora.
                    Cliente cliente = Cliente.crearCliente(-1, nombreBusqueda, identificadorBusqueda, fechaBusqueda); // CORREGIDO (asumiendo que crearCliente maneja nulls para búsqueda)
                                                                                                                        // Si crearCliente no permite nulls para búsqueda, necesitarías un constructor público o setters.
                                                                                                                        // Por ahora, asumimos que crearCliente es flexible o que los nulls se manejan en Find.

                    // Realizar la búsqueda usando el método de instancia
                    Cliente[] resultados = cliente.find();

                    // Mostrar resultados
                    System.out.println("\n--- Resultados de la búsqueda ---");
                    if (resultados == null || resultados[0] == null) {
                        System.out.println("No se encontraron clientes con esos criterios.");
                    } else {
                        int i = 0;
                        while (i < resultados.length && resultados[i] != null) {
                            System.out.println(resultados[i]);
                            i++;
                        }
                    }
                } else {
                    // Mostrar todos los clientes como ya hacía
                    EntityManager.printClientes();
                }

                System.out.println();
            }
            case 4 -> {
                System.out.println("Has seleccionado Baja clientes");
                System.out.println();
                EntityManager.printClientes();
                System.out.println();
                String nombreCliente = Utils.askText("Introduce el nombre del cliente a eliminar");
                System.out.println();
                idCliente = EntityManager.psSelectidCliente(nombreCliente);
                if (idCliente == -1) {
                    System.out.println("No se ha encontrado el cliente con ese nombre.");
                    break;
                }
                System.out.println("Has seleccionado el cliente con id: " + idCliente);
                Cliente cliente = Cliente.crearCliente(idCliente, null, null, null);
                boolean eliminado = cliente.remove();
                if (eliminado) {
                    System.out.println("Cliente eliminado correctamente.");
                } else {
                    System.out.println("Error al eliminar el cliente.");
                }
            }
            case 0 -> {
                System.out.println("Saliendo...");
            }
        }

    }
    public static void opcion_Hotel(int opcionhotel) {
        switch (opcionhotel) {
            case 1:
                System.out.println("Has seleccionado Alta Hotel ");
                String nombreHotel = Utils.askText("Introduce el nombre del hotel");
                int num_plantasHotel = Utils.askInteger("Introduce el numero de plantas del hotel");
                String direccion = Utils.askText("Introduce la direccion del hotel");
                Hotel hotel = new Hotel (-1, nombreHotel, num_plantasHotel, direccion);
                boolean anadido = hotel.persist();
                if (anadido) {
                    System.out.println("Hotel añadido correctamente.");
                } else {
                    System.out.println("Error al añadir el Hotel.");
                }
                
                break;
            case 2:
            System.out.println("Has seleccionado Modificar Hotel");

            EntityManager.printHoteles();
            System.out.println();
            
            String nombreHotelamodificar = Utils.askText("Introduce la ID del hotel que deseas modificar");
            System.out.println();
            
            int idHotel = EntityManager.psSelectidHotel(nombreHotelamodificar);
            
            if (idHotel == -1) {
                System.out.println("No se ha encontrado ningún hotel con ese nombre.");
                break;
            }
            
            
            
            String nuevoNombre = Utils.askText("Introduce el nuevo nombre del hotel");
            int nuevasPlantas = Utils.askInteger("Introduce el nuevo número de plantas del hotel");
            String nuevaLocalizacion = Utils.askText("Introduce la nueva localización del hotel");
            
            Hotel hotelModificado = new Hotel(idHotel, nuevoNombre, nuevasPlantas, nuevaLocalizacion);
            
            boolean modificado = hotelModificado.merge();
            
            if (modificado) {
                System.out.println("Hotel modificado correctamente.");
            } else {
                System.out.println("Error al modificar el hotel.");
            }
            break;
            



            case 3:
            System.out.println("Has seleccionado Baja de hoteles");
            System.out.println();
            
            EntityManager.printHoteles();
            System.out.println();
            
            String nombreHotelaeliminar = Utils.askText("Introduce el ID del hotel a eliminar");
            System.out.println();
            
            int idHoteleliminar = EntityManager.psSelectidHotel(nombreHotelaeliminar);
            
            if (idHoteleliminar == -1) {
                System.out.println("No se ha encontrado el hotel con ese nombre.");
                break;
            }
            
            System.out.println("Has seleccionado el hotel con id: " + idHoteleliminar);
            
            Hotel hotelmodificado = new Hotel(idHoteleliminar, null, 0, null);
            
            boolean eliminado = hotelmodificado.remove();
            
            if (eliminado) {
                System.out.println("Hotel eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el hotel.");
            }


                break;
            case 4:
                System.out.println("Has seleccionado ver Hotel ");

                System.out.println("Has seleccionado lista de hoteles");
                Hotel hotelfind = new Hotel();
                Hotel[] resultado = hotelfind.find();
                
                if (resultado == null || resultado.length == 0) {
                    System.out.println("No se han encontrado hoteles.");
                } else {
                    System.out.println("Listado de hoteles encontrados:");
                    for (Hotel h : resultado) {
                        System.out.println(h);
                    }
                }
                


                break;
            case 5:
                System.out.println("Saliendo...");

                break;

        }
    }

    public static void opcionesTemporada(int opcionTemporada) {
        switch (opcionTemporada) {
            case 1 -> {
                //Alta de temporada:
                System.out.println("Has seleccionado alta de temporada");
                //tenemos que pedir los datos por consola:
                String nombreTemporada = Utils.askText("Introduce el nombre del temporada");
                String fechaInicio = Utils.askText("Introduce la fecha de inicio del temporada");
                String fechaFin = Utils.askText("Introduce la fecha de fin de temporada");


                //Convertir fechaInicio de formato dd-mm-yyyy a formato MySQL yyyy-mm-dd
                String[] fechaInicio_Split = fechaInicio.split("-");
                if (fechaInicio_Split.length == 3) {
                    fechaInicio = fechaInicio_Split[2] + "-" + fechaInicio_Split[1] + "-" + fechaInicio_Split[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                
                String[] fechaFin_Split = fechaFin.split("-");
                if (fechaFin_Split.length == 3) {
                    fechaFin = fechaFin_Split[2] + "-" + fechaFin_Split[1] + "-" + fechaFin_Split[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                //ahora tenemos que llamar al metodo del fichero EntityManager para hacer el inssert en la BBDD:
                Temporada t = new Temporada(-1, nombreTemporada, fechaInicio, fechaFin);
                boolean anadido = t.persist();
                //boolean anadido = EntityManager.Persist(t);
                if(anadido){
                    System.out.println("Temporada añadida a la BBDD.");
                } else {
                    System.out.println("No se ha podido insertar la temporada a la BBDD,");
                }
            }
            case 2 -> {
                System.out.println("Has seleccionado lista temporada");
                Temporada temp = new Temporada();
                Temporada[] resultado = temp.find();

                if(resultado == null || resultado.length == 0){
                    System.out.println("No se han encontrado temporadas.");
                }else{
                    System.out.println("Listado de temporadas encontradas:");
                    for(Temporada t : resultado){
                        System.out.println(t);
                    }
                }
            }
            case 3 -> {
                System.out.println("Has seleccionado modificar temporada");
                // Primero pedimos el ID de la temporada a modificar
                int idTemporada = Utils.askInteger("Introduce el ID de la temporada que quieres modificar: ");

                // Luego podemos elegir cómo modificar.
                // Aquí hago un "updateTodoTemporada" para actualizar todos los campos:
                String nuevoNombre = Utils.askText("Introduce el nuevo nombre: ");
                String nuevaFechaInicio = Utils.askText("Introduce la nueva fecha de inicio (dd-mm-yyyy)");
                String nuevaFechaFin = Utils.askText("Introduce la nueva fecha de fin (dd-mm-yyyy)");
                String[] fechaInicio_Split = nuevaFechaInicio.split("-");
                if (fechaInicio_Split.length == 3) {
                    nuevaFechaInicio = fechaInicio_Split[2] + "-" + fechaInicio_Split[1] + "-" + fechaInicio_Split[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                
                String[] fechaFin_Split = nuevaFechaFin.split("-");
                if (fechaFin_Split.length == 3) {
                    nuevaFechaFin = fechaFin_Split[2] + "-" + fechaFin_Split[1] + "-" + fechaFin_Split[0];
                } else {
                    System.out.println("Formato de fecha incorrecto. Debe ser dd-mm-yyyy.");
                    break;
                }
                Temporada temp = new Temporada(idTemporada, nuevoNombre, nuevaFechaInicio, nuevaFechaFin);

                boolean modificado = temp.merge();
                if (modificado) {
                    System.out.println("Temporada actualizada con éxito.");
                } else {
                    System.out.println("No se pudo actualizar la temporada.");
                }
            }
            case 4 -> {
                System.out.println("Has seleccionado baja temporada");
                int idTemporada = Utils.askInteger("Introduce el ID de la temporada que quieres eliminar: ");
                Temporada temp = new Temporada(idTemporada, null, null, null);
                boolean eliminado = temp.remove();
                if (eliminado) {
                    System.out.println("Temporada eliminada con éxito.");
                } else {
                    System.out.println("No se pudo eliminar la temporada.");
                }
            }
            case 0 -> {
                System.out.println("Saliendo...");
            }

        }
    }
}