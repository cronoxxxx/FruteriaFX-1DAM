package ui;

import common.*;
import domain.*;
import service.GestionMostrador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InterfazMostrador {

    private static final String pass = "messi";

    public static void getInterfazMostrador(GestionMostrador gestionMostrador) {

        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        Optional<String> contraOptional = Optional.empty();
        System.out.println(Constantes.INGRESE_CONTRASENA);
        try {
            String contra = entradaReader.readLine().strip();
            contraOptional = Optional.of(contra);
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
        if (contraOptional.isPresent() && !contraOptional.get().equals(pass)) {
            System.out.println(Constantes.CONTRASENA_INCORRECTA_INGRESE_UNA_VALIDA);
        } else {
            int op;
            do {
                System.out.println(Constantes.MENU_MOSTRADOR);
                op = menu();
                switch (op) {
                    case 1 -> mostrarInformacion(gestionMostrador);
                    case 2 -> realizarVentaCliente(gestionMostrador);
                    case 3 -> calcularBeneficiosTotales(gestionMostrador);
                    case 4 -> darAlta(gestionMostrador);
                    case 5 -> buscarClientes(gestionMostrador);
                    case 6 -> eliminarCliente(gestionMostrador);
                    case 7 -> aplicarDescuentos(gestionMostrador);
                    case 8 -> reunirClientesPorCiudad(gestionMostrador);
                    case 9 -> mostrarFacturas(gestionMostrador);
                    case 10 -> buscarFacturas(gestionMostrador);
                    case 11 -> actualizarFactura(gestionMostrador);
                    case 12 -> lecturaFinal(gestionMostrador);

                    default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
                }
            } while (op != 12);
        }
    }

    public static void actualizarFactura(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
            String nombre = entradaReader.readLine();
            System.out.println(Constantes.INGRESE_APELLIDOS_DEL_CLIENTE);
            String apellidos = entradaReader.readLine();
            Set<Factura> facturasSet = gestionMostrador.devolverFacturasNombreSet(nombre, apellidos);
            List <Factura> facturas = new ArrayList<>(facturasSet);
            if ( !facturas.isEmpty()) {
                for (int i = 0; i < facturas.size(); i++) {
                    System.out.println(i + " " + facturas.get(i));
                }
            } else {
                System.out.println(Constantes.FACTURAS_NO_ENCONTRADAS);
            }
            System.out.println(Constantes.INGRESE_LA_FACTURA_A_ACTUALIZAR);
            int op = Integer.parseInt(entradaReader.readLine());
            if (op >= 0 && op < facturas.size()) {
                Factura factura = facturas.get(op);
                System.out.println(Constantes.INGRESE_NUEVO_NOMBRE_DEL_CLIENTE);
                String nuevoNombre = entradaReader.readLine();
                System.out.println(Constantes.INGRESE_NUEVOS_APELLIDOS_DEL_CLIENTE);
                String nuevoApellidos = entradaReader.readLine();
                if (gestionMostrador.actualizarFactura(factura, nuevoNombre, nuevoApellidos)) {
                    System.out.println(Constantes.ACTUALIZADO_EXITOSO);
                } else {
                    System.out.println(Constantes.ACTUALIZADO_FALLIDO);
                }
            } else {
                System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }

    }

    public static void buscarFacturas(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.MENU_BUSCAR_FACTURAS);
        int op = menu();
        try {
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_FECHA);
                    String fecha = entradaReader.readLine();
                    if (gestionMostrador.buscarFacturasPorFecha(fecha) != null) {
                        System.out.println(Constantes.ENCONTRADO_EXITOSO);
                        System.out.println(gestionMostrador.buscarFacturasPorFecha(fecha));
                    } else {
                        System.out.println(Constantes.ENCONTRADO_FALLIDO);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_APELLIDOS_DEL_CLIENTE);
                    String apellidos = entradaReader.readLine();
                    if (gestionMostrador.devolverFacturasNombreSet(nombre, apellidos) != null) {
                        System.out.println(Constantes.ENCONTRADO_EXITOSO);
                        System.out.println(gestionMostrador.devolverFacturasNombreSet(nombre, apellidos));
                    } else {
                        System.out.println(Constantes.ENCONTRADO_FALLIDO);
                    }
                }
                default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }


    }

    public static void lecturaFinal(GestionMostrador gestionMostrador) {
        System.out.println(Constantes.VUELVA_PRONTO);
        gestionMostrador.escribirFicheroBinario();
        System.out.println(gestionMostrador.leerFicheroBinario());
        gestionMostrador.escribirCambiosFrutaTexto();
        System.out.println(gestionMostrador.leerCambiosFrutaTexto());

    }

    public static void mostrarFacturas(GestionMostrador gestionMostrador) {
        if (gestionMostrador.getFacturas() != null && !gestionMostrador.getFacturas().isEmpty()) {
            System.out.println(Constantes.MOSTRAR_FACTURAS);
            gestionMostrador.getFacturas().forEach(System.out::println);
        } else {
            System.out.println(Constantes.NO_HAY_FACTURAS);
        }
    }

    public static void reunirClientesPorCiudad(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.INGRESE_CIUDAD);
            String ciudad = entradaReader.readLine();
            if (gestionMostrador.reunirClientesPorCiudad(ciudad)) {
                System.out.println(Constantes.CLIENTES_REUNIDOS_CON_EXITO);
            } else {
                System.out.println(Constantes.CLIENTES_NO_REUNIDOS);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }


    }

    public static void aplicarDescuentos(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.MENU_DESCUENTOS);
        try {
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_ID_DEL_CLIENTE);
                    int id = Integer.parseInt(entradaReader.readLine());
                    if (gestionMostrador.aplicarDescuentosClienteporID(id)) {
                        System.out.println(Constantes.DESCUENTO_APLICADO_CON_EXITO);
                    } else {
                        System.out.println(Constantes.DESCUENTO_NO_APLICADO);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_SUS_APELLIDOS);
                    String apellidos = entradaReader.readLine();
                    List<Cliente> clientes = gestionMostrador.clienteAccion(nombre, apellidos);
                    if (!clientes.isEmpty()) {
                        for (int i = 0; i < clientes.size(); i++) {
                            System.out.println(i + 1 + ". " + clientes.get(i).getNombre());
                        }
                        System.out.println(Constantes.INGRESE_ID_DEL_CLIENTE);
                        int id = Integer.parseInt(entradaReader.readLine());
                        Cliente actual = clientes.get(id - 1);
                        if (actual != null) {
                            if (gestionMostrador.aplicarDescuentosporClienteNombreApellidos(actual)) {
                                System.out.println(Constantes.DESCUENTO_APLICADO_CON_EXITO);
                            } else {
                                System.out.println(Constantes.DESCUENTO_NO_APLICADO);
                            }
                        } else {
                            System.out.println(Constantes.EL_ID_NO_EXISTE);
                        }
                    } else {
                        System.out.println(Constantes.CLIENTE_VACIO);
                    }
                }
                default -> System.out.println(Constantes.OPCION_NO_VALIDA);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void eliminarCliente(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.MENU_ELIMINAR_CLIENTE);
        try {
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_ID_DEL_CLIENTE);
                    int id = Integer.parseInt(entradaReader.readLine());
                    if (gestionMostrador.removeClienteporID(id)) {
                        System.out.println(Constantes.ELIMINADO_EXITOSAMENTE);
                    } else {
                        System.out.println(Constantes.ELIMINADO_FALLIDO);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_SUS_APELLIDOS);
                    String apellidos = entradaReader.readLine();
                    List<Cliente> clientes = gestionMostrador.clienteAccion(nombre, apellidos);
                    if (!clientes.isEmpty()) {
                        for (int i = 0; i < clientes.size(); i++) {
                            System.out.println(i + 1 + ". " + clientes.get(i).getNombre());
                        }
                        System.out.println(Constantes.SELECCIONE_QUE_CLIENTE_QUIERE_ELIMINAR);
                        int index = Integer.parseInt(entradaReader.readLine()) - 1;
                        Cliente online = clientes.get(index);
                        if (online != null) {
                            if (gestionMostrador.removeClienteporNombreApellidos(online)) {
                                System.out.println(Constantes.ELIMINADO_EXITOSAMENTE);
                            } else {
                                System.out.println(Constantes.ELIMINADO_FALLIDO);
                            }
                        } else {
                            System.out.println(Constantes.CLIENTE_VACIO);
                        }
                    } else {
                        System.out.println(Constantes.CLIENTE_VACIO);
                    }
                }
                default -> System.out.println(Constantes.OPCION_NO_VALIDA);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void calcularBeneficiosTotales(GestionMostrador gestionMostrador) {
        if (gestionMostrador.getBeneficios() <= 0) {
            System.out.println(Constantes.NO_HAY_NINGUN_BENEFICIO);
        } else {
            System.out.println(Constantes.BENEFICIOS_TOTALES + gestionMostrador.getBeneficios());
        }
    }

    public static void buscarClientes(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.MENU_BUSCAR_CLIENTE);
        try {
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_ID_DEL_CLIENTE);

                    int id = Integer.parseInt(entradaReader.readLine());
                    gestionMostrador.buscarClienteporID(id);
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_APELLIDOS_DEL_CLIENTE);
                    String apellidos = entradaReader.readLine();
                    gestionMostrador.buscarClienteNombreApellido(nombre, apellidos);

                }
                default -> System.out.println(Constantes.OPCION_NO_VALIDA);

            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }


    public static void realizarVentaCliente(GestionMostrador gestionMostrador) {
        StringBuilder sb = new StringBuilder();
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.SELECCIONE_QUE_CLIENTE_VENDER);
        try {
            int op = menu();
            switch (op) {
                case 1 -> {
                    Cliente fisico = gestionMostrador.devolverClienteFisico();
                    if (fisico != null) {
                        System.out.println(Constantes.INGRESE_CUANTAS_FRUTAS_DESEA_VENDER);
                        int cant = Integer.parseInt(entradaReader.readLine());
                        int[] cantidades = new int[cant];
                        for (int i = 0; i < cant; i++) {
                            System.out.println(Constantes.INGRESE_LA_FRUTA + (i + 1));
                            sb.append(entradaReader.readLine()).append(", ");
                            System.out.println(Constantes.INGRESE_LA_CANTIDAD);
                            cantidades[i] = Integer.parseInt(entradaReader.readLine());
                        }

                        //sb.delete(sb.length() - 2, sb.length());
                        if (gestionMostrador.venderClienteFisico(fisico, sb, cantidades)) {
                            System.out.println(Constantes.OPERACION_EXITOSA);
                        } else {
                            System.out.println(Constantes.OPERACION_FALLIDA);
                        }
                    } else {
                        System.out.println(Constantes.CLIENTE_VACIO);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.SELECCIONE_POR_ID_O_NOMBRE_APELLIDO);
                    op = menu();
                    switch (op) {
                        case 1 -> {
                            System.out.println(Constantes.INGRESE_ID_DEL_CLIENTE);
                            int id = Integer.parseInt(entradaReader.readLine());
                            Cliente online = gestionMostrador.devolverClienteOnline(id);
                            if (online != null) {
                                System.out.println(Constantes.INGRESE_CUANTAS_FRUTAS_DESEA_VENDER);
                                int cant = Integer.parseInt(entradaReader.readLine());
                                int[] cantidades = new int[cant];
                                for (int i = 0; i < cant; i++) {
                                    System.out.println(Constantes.INGRESE_LA_FRUTA + (i + 1));
                                    sb.append(entradaReader.readLine()).append(", ");
                                    System.out.println(Constantes.INGRESE_LA_CANTIDAD);
                                    cantidades[i] = Integer.parseInt(entradaReader.readLine());
                                }
                                if (gestionMostrador.venderClienteOnline(online, sb, cantidades)) {
                                    System.out.println(Constantes.OPERACION_EXITOSA);
                                } else {
                                    System.out.println(Constantes.OPERACION_FALLIDA);
                                }
                            } else {
                                System.out.println(Constantes.CLIENTE_VACIO);
                            }
                        }
                        case 2 -> {
                            System.out.println(Constantes.INGRESE_NOMBRE_DEL_CLIENTE);
                            String nombre = entradaReader.readLine();
                            System.out.println(Constantes.INGRESE_APELLIDO_DEL_CLIENTE);
                            String apellidos = entradaReader.readLine();
                            List<Cliente> clientes = gestionMostrador.clienteAccion(nombre, apellidos);
                            if (!clientes.isEmpty()) {
                                for (int i = 0; i < clientes.size(); i++) {
                                    System.out.println(i + 1 + ". " + clientes.get(i).getNombre());
                                }
                                System.out.println(Constantes.SELECCIONE_QUE_CLIENTE_QUIERE_VENDER);
                                int index = Integer.parseInt(entradaReader.readLine()) - 1;
                                Cliente online = clientes.get(index);
                                if (online != null) {
                                    System.out.println(Constantes.INGRESE_CUANTAS_FRUTAS_DESEA_VENDER);
                                    int cant = Integer.parseInt(entradaReader.readLine());
                                    int[] cantidades = new int[cant];
                                    for (int i = 0; i < cant; i++) {
                                        System.out.println(Constantes.INGRESE_LA_FRUTA + (i + 1));
                                        sb.append(entradaReader.readLine()).append(", ");
                                        cantidades[i] = Integer.parseInt(entradaReader.readLine());
                                    }
                                    if (gestionMostrador.venderClienteOnline(online, sb, cantidades)) {
                                        System.out.println(Constantes.OPERACION_EXITOSA);
                                    } else {
                                        System.out.println(Constantes.OPERACION_FALLIDA);
                                    }
                                } else {
                                    System.out.println(Constantes.CLIENTE_VACIO);
                                }
                            } else {
                                System.out.println(Constantes.CLIENTE_VACIO);
                            }
                        }
                        default -> System.out.println(Constantes.OPCION_NO_VALIDA);
                    }
                }
                default -> System.out.println(Constantes.OPCION_NO_VALIDA);
            }
        } catch (IOException ignored) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }


    private static void darAlta(GestionMostrador gestionMostrador) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.QUE_CLIENTE_DAR_ALTA);
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_SUS_APELLIDOS);
                    String apellidos = entradaReader.readLine();
                    System.out.println(Constantes.SE_DESEA_AGREGA_DESCUENTO_AL_CLIENTE);
                    boolean descuento = false;
                    String respuesta;
                    do {
                        respuesta = entradaReader.readLine().strip();
                        if (!(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("no"))) {
                            System.out.println(Constantes.ERROR_SOLO_SE_ACEPTAN_VALORES_SI_O_NO);
                        } else descuento = respuesta.strip().equalsIgnoreCase("si");
                    } while (!(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("no")));
                    Cliente fisico = new ClienteFisico(nombre, apellidos, descuento);
                    if(gestionMostrador.putCliente(fisico)) {
                        System.out.println(Constantes.OPERACION_EXITOSA);
                    } else {
                        System.out.println(Constantes.OPERACION_FALLIDA);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DEL_CLIENTE);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_SUS_APELLIDOS);
                    String apellidos = entradaReader.readLine();
                    System.out.println(Constantes.SE_DESEA_AGREGA_DESCUENTO_AL_CLIENTE);
                    boolean descuento = false;
                    String respuesta;
                    do {
                        respuesta = entradaReader.readLine().strip();
                        if (!(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("no"))) {
                            System.out.println(Constantes.ERROR_SOLO_SE_ACEPTAN_VALORES_SI_O_NO);
                        } else descuento = respuesta.strip().equalsIgnoreCase("si");
                    } while (!(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("no")));
                    System.out.println(Constantes.INGRESE_LA_FECHA_DE_ENTREGA);
                    String fechaCaducidadStr = entradaReader.readLine();
                    LocalDate fechaCaducidad = LocalDate.parse(fechaCaducidadStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    System.out.println(Constantes.INGRESE_LA_DIRECCION_DEL_CLIENTE);
                    String direccion = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_LA_CIUDAD_DEL_CLIENTE);
                    String ciudad = entradaReader.readLine();
                    Cliente online;
                    try {
                        online = new ClienteOnline(nombre, apellidos, fechaCaducidad, direccion, descuento, ciudad);
                        if(gestionMostrador.putCliente(online)){
                            System.out.println(Constantes.OPERACION_EXITOSA);
                        } else {
                            System.out.println(Constantes.OPERACION_FALLIDA);
                        }
                    } catch (FechaInvalidaException | DateTimeParseException e) {
                        System.out.println(Constantes.DEBE_INGRESAR_FECHA_VALIDA);
                    } catch (direccionInvalidoException e) {
                        System.out.println(Constantes.DEBE_INGRESAR_FORMATO_DE_DIRECCION_VALIDA);
                    } catch (AgregarProvinciasException e) {
                        System.out.println(Constantes.DEBE_INGRESAR_PROVINCIA_VALIDA);
                    }
                }
                default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
            }

        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }


    public static void mostrarInformacion(GestionMostrador gestionMostrador) {
        System.out.println(Constantes.ORDENAR_MOSTRAR_MOSTRADOR);
        int op = menu();
        switch (op) {
            case 1 -> {
                System.out.println(Constantes.SELECCIONE_ORDEN_DE_MUESTRA);
                op = menu();
                switch (op) {
                    case 1 -> System.out.println(gestionMostrador.mostrarInformacion(true));
                    case 2 -> System.out.println(gestionMostrador.mostrarInformacion(false));
                    default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
                }
            }
            case 2 -> {
                System.out.println(Constantes.SELECCIONE_ORDEN_DE_MUESTRA);
                op = menu();
                switch (op) {
                    case 1 -> System.out.println(gestionMostrador.mostrarInformacionporNombre(true));
                    case 2 -> System.out.println(gestionMostrador.mostrarInformacionporNombre(false));
                    default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
                }
            }
            default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
        }
    }


    public static int menu() {
        int op = 0;
        boolean valido = false;
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                op = Integer.parseInt(entradaReader.readLine());
                valido = true;

            } catch (NumberFormatException e) {
                System.out.println(Constantes.DEBE_INGRESAR_UN_NUMERO);
            } catch (IOException e) {
                System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
            }
        } while (!valido);

        return op;
    }

}
