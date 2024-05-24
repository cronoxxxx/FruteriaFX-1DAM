package ui;

import common.*;
import domain.Fruta;
import service.GestionFruteria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InterfazFruteria {
    private static final String pass = "ronaldo";


    public static void getInterfazFruteria(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        Optional<String> contraOptional = Optional.empty();
        System.out.println(Constantes.INGRESE_CONTRASENA);
        try {
            String contra = entradaReader.readLine().strip();
            contraOptional = Optional.of(contra);
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
        if (contraOptional.isPresent() && !contraOptional.get().equals(pass)) {
            System.out.println(Constantes.CONTRASENA_INCORRECTA_INGRESE_UNA_VALIDA);
        } else {
            int op;
            do {
                System.out.println(Constantes.MENU_FRUTERIA);
                op = menu();
                switch (op) {
                    case 1 -> mostrarInformacion(gestionFruteria);
                    case 2 -> darAltaFruta(gestionFruteria);
                    case 3 -> darBajaFruta(gestionFruteria);
                    case 4 -> rebajarPrecioFruta(gestionFruteria);
                    case 5 -> subirPrecioFruta(gestionFruteria);
                    case 6 -> calcularInventarioTotal(gestionFruteria);
                    case 7 -> actualizarPrecioVentaFruta(gestionFruteria);
                    case 8 -> compararFrutasProcedencia(gestionFruteria);
                    case 9 -> reunirFrutasporProcedencia(gestionFruteria);
                    case 10 -> buscarFrutaPorNombre(gestionFruteria);
                    case 11 -> limpiezaFrutas(gestionFruteria);
                    case 12 -> Top5FrutasVendidas(gestionFruteria);
                    case 13 -> lecturaFinal(gestionFruteria);
                    default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
                }
            } while (op!=13);
        }
    }

    public static void lecturaFinal (GestionFruteria gestionFruteria) {
        System.out.println(Constantes.VUELVA_PRONTO);
        gestionFruteria.escribirFichero();
        try {
            System.out.println(gestionFruteria.leerFichero());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarInformacion(GestionFruteria gestionFruteria) {
        System.out.println(Constantes.SELECCIONE_ORDEN_DE_MUESTRA);
        int op = menu();
        switch (op) {
            case 1 -> System.out.println(gestionFruteria.mostrarInformacion(true));
            case 2 -> System.out.println(gestionFruteria.mostrarInformacion(false));
            case 3 -> System.out.println(gestionFruteria.getFrutas());
            default -> System.out.println(Constantes.INGRESE_UNA_OPCION_VALIDA);
        }

    }
    public static void calcularInventarioTotal(GestionFruteria gestionFruteria) {
        if (gestionFruteria.calcularInventarioTotal() != -1) {
            System.out.println(Constantes.EL_INVENTARIO_TOTAL_ES + gestionFruteria.calcularInventarioTotal());
        } else {
            System.err.println(Constantes.PRECIOS_DE_VENTA_MAL_INGRESADOS_VERIFICAR_PROGRAMA);
        }
    }


    public static void darAltaFruta(GestionFruteria gestionFruteria) {

        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
            String nombre = entradaReader.readLine();
            System.out.println(Constantes.INGRESE_LA_PROCENDENCIA);
            String procedencia = entradaReader.readLine();
            System.out.println(Constantes.INGRESE_EL_NUMERO_DE_KILOS);
            int numeroKilos = Integer.parseInt(entradaReader.readLine());
            System.out.println(Constantes.INGRESE_EL_PRECIO_COSTE);
            double precioCoste = Double.parseDouble(entradaReader.readLine());
            System.out.println(Constantes.INGRESE_EL_PRECIO_VENTA);
            double precioVenta = Double.parseDouble(entradaReader.readLine());
            System.out.println(Constantes.INGRESE_LA_FECHA_DE_CADUCIDAD);
            String fechaCaducidadStr = entradaReader.readLine();
            LocalDate fechaCaducidad = LocalDate.parse(fechaCaducidadStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Fruta fruta = new Fruta(nombre, procedencia, numeroKilos, precioCoste, precioVenta, fechaCaducidad);
            if (gestionFruteria.darAltaFruta(fruta)) {
                System.out.println(Constantes.FRUTA_AGREGADA_CON_EXITO);

            } else {
                System.out.println(Constantes.YA_EXISTE_UNA_FRUTA_CON_EL_MISMO_NOMBRE_EN_LA_FRUTERIA);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);

        } catch (NumberFormatException e) {
            System.out.println(Constantes.DEBE_INGRESAR_UN_NUMERO);

        } catch (DateTimeParseException | FechaInvalidaException e) {
            System.out.println(Constantes.DEBE_INGRESAR_FECHA_VALIDA);

        } catch (precioVentaExcepcion e) {
            System.out.println(Constantes.DEBE_INGRESAR_PRECIO_VENTA_MAYOR_A_PRECIO_COSTE);

        } catch (AgregarProvinciasException e) {
            System.out.println(Constantes.DEBE_INGRESAR_PROVINCIA_VALIDA);
        }
    }

    public static void darBajaFruta(GestionFruteria gestionFruteria) {

        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.MENU_BAJA_FRUTA);
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
                    String nombre = entradaReader.readLine();
                    if (gestionFruteria.darBajaFrutaPorNombre(nombre)) {
                        System.out.println(Constantes.ELIMINADO_EXITOSO);

                    } else {
                        System.out.println(Constantes.ELIMINADO_FALLIDO);
                    }
                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_LA_PROCENDENCIA);
                    String procedencia = entradaReader.readLine();
                    if (gestionFruteria.darBajaFrutasPorProcedencia(procedencia)) {
                        System.out.println(Constantes.ELIMINADO_EXITOSO);

                    } else {
                        System.out.println(Constantes.ELIMINADO_FALLIDO);
                    }
                }
                default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }

    }

    public static void rebajarPrecioFruta(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try  {
            System.out.println(Constantes.MENU_REBAJAR_FRUTA);
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_EL_INDICE_DE_LA_FRUTA);
                    int indice = Integer.parseInt(entradaReader.readLine());
                    System.out.println(Constantes.INGRESE_LA_CANTIDAD_A_REBAJAR);
                    double cantidad = Double.parseDouble(entradaReader.readLine());
                    if (indice < 0 || indice >= gestionFruteria.getFrutas().size()) {
                        System.out.println(Constantes.INDICE_FUERA_DE_RANGO);
                    } else {
                        if (gestionFruteria.rebajar(cantidad, indice-1)) {
                            Fruta aux = gestionFruteria.getFrutas().get(indice-1);
                            System.out.println(aux);
                            System.out.println(Constantes.REBAJA_EXITOSA);
                        } else {
                            System.out.println(Constantes.REBAJA_FALLIDA);
                        }
                    }

                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_LA_CANTIDAD_A_REBAJAR);
                    double cantidad = Double.parseDouble(entradaReader.readLine());
                    Fruta aux = gestionFruteria.getDaoFruteriaImplementacion().getFruteria().devolverFrutaNombre(nombre);
                    if (aux == null) {
                        System.out.println(Constantes.ERROR_FRUTA_NULA);
                    } else {
                        if (gestionFruteria.rebajarNombreFruta(cantidad, aux.getNombre())) {
                            System.out.println(aux);
                            System.out.println(Constantes.REBAJA_EXITOSA);
                        } else {
                            System.out.println(Constantes.LA_CANTIDAD_DE_REBAJA_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_MENOR_QUE_EL_PRECIO_DE_COSTE);
                        }
                    }

                }
                default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
            }

        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void subirPrecioFruta(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try  {
            System.out.println(Constantes.MENU_SUBIR_FRUTA);
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_EL_INDICE_DE_LA_FRUTA);
                    int indice = Integer.parseInt(entradaReader.readLine());
                    System.out.println(Constantes.INGRESE_LA_CANTIDAD_A_SUBIR);
                    double cantidad = Double.parseDouble(entradaReader.readLine());
                    if (indice < 0 || indice >= gestionFruteria.getFrutas().size()) {
                        System.out.println(Constantes.INDICE_FUERA_DE_RANGO);
                    } else {
                        if (gestionFruteria.subir(cantidad, indice-1)) {
                            Fruta aux = gestionFruteria.getFrutas().get(indice-1);
                            System.out.println(aux);
                            System.out.println(Constantes.SUBIDA_EXITOSA);
                        } else {
                            System.out.println(Constantes.SUBIDA_FALLIDA);
                        }
                    }

                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_LA_CANTIDAD_A_SUBIR);
                    double cantidad = Double.parseDouble(entradaReader.readLine());
                    Fruta aux = gestionFruteria.getDaoFruteriaImplementacion().getFruteria().devolverFrutaNombre(nombre);
                    if (aux == null) {
                        System.out.println(Constantes.ERROR_FRUTA_NULA);
                    } else {
                        if (gestionFruteria.subirNombreFruta(cantidad, aux.getNombre())) {
                            System.out.println(Constantes.SUBIDA_EXITOSA);
                            System.out.println(aux);
                        } else {
                            System.out.println(Constantes.LA_CANTIDAD_DE_PRECIO_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_INNACESIBLE_PARA_NUESTROS_CLIENTES);
                        }
                    }

                }
                default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void actualizarPrecioVentaFruta(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Constantes.MENU_ACTUALIZAR_PRECIO_VENTA_FRUTA);
        try  {
            int op = menu();
            switch (op) {
                case 1 -> {
                    System.out.println(Constantes.INGRESE_EL_INDICE_DE_LA_FRUTA);
                    int indice = Integer.parseInt(entradaReader.readLine());
                    System.out.println(Constantes.INGRESE_EL_PRECIO_A_ACTUALIZAR);
                    double precio = Double.parseDouble(entradaReader.readLine());
                    Fruta aux = gestionFruteria.getFrutas().get(indice-1);
                    if (indice < 0 || indice >= gestionFruteria.getFrutas().size()) {
                        System.out.println(Constantes.INDICE_FUERA_DE_RANGO);
                    } else {
                        if (gestionFruteria.actualizarPrecioVentaID(indice-1, precio)) {
                            System.out.println(aux);
                            System.out.println(Constantes.ACTUALIZACION_EXITOSA);
                        } else {
                            System.out.println(Constantes.ACTUALIZACION_FALLIDA);
                        }
                    }

                }
                case 2 -> {
                    System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
                    String nombre = entradaReader.readLine();
                    System.out.println(Constantes.INGRESE_EL_PRECIO_A_ACTUALIZAR);
                    double precio = Double.parseDouble(entradaReader.readLine());
                    List<Fruta> frutas = gestionFruteria.getFrutas();
                    List <Fruta> frutas2 = new ArrayList<>();
                    for (Fruta f : frutas) {
                        if (f.getNombre().strip().equalsIgnoreCase(nombre)) {
                            frutas2.add(f);
                        }
                    }
                    if (!frutas2.isEmpty()) {
                        for (int i = 0; i < frutas2.size(); i++) {
                            System.out.println(i + 1 + ". " + frutas2.get(i).getNombre());
                        }
                        System.out.println(Constantes.INGRESE_EL_INDICE_DE_LA_FRUTA);
                        int index = Integer.parseInt(entradaReader.readLine()) - 1;
                        Fruta fruta = frutas2.get(index);
                        if (gestionFruteria.actualizarPrecioVenta(fruta, precio)) {

                            System.out.println(Constantes.ACTUALIZACION_EXITOSA);
                            System.out.println(fruta);
                        } else {
                            System.out.println(Constantes.ACTUALIZACION_FALLIDA);
                        }
                    } else {
                        System.out.println(Constantes.FRUTA_NO_ENCONTRADA);
                    }

                }
                default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void compararFrutasProcedencia(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try  {
            System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA + 1);
            String fruta1 = entradaReader.readLine();
            System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA + 2);
            String fruta2 = entradaReader.readLine();
            if (gestionFruteria.frutasDeMismaProcedencia(fruta1, fruta2)!=null && !gestionFruteria.frutasDeMismaProcedencia(fruta1, fruta2).isEmpty()) {
                System.out.println(Constantes.COMPARACION_EXITOSA);
                gestionFruteria.frutasDeMismaProcedencia(fruta1, fruta2).forEach(System.out::println);
            } else {
                System.out.println(Constantes.COMPARACION_FALLIDA);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void reunirFrutasporProcedencia(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try  {
            System.out.println(Constantes.INGRESE_LA_PROCENDENCIA);
            String procedencia = entradaReader.readLine();
            if (gestionFruteria.reunirFrutasporProcedencia(procedencia)!=null) {
                System.out.println(Constantes.FRUTAS_REUNIDAS_EXITOSAMENTE);
                gestionFruteria.reunirFrutasporProcedencia(procedencia).forEach(System.out::println);
            } else {
                System.out.println(Constantes.FRUTAS_REUNIDAS_FALLIDAS);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void buscarFrutaPorNombre(GestionFruteria gestionFruteria) {
        BufferedReader entradaReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(Constantes.INGRESE_EL_NOMBRE_DE_LA_FRUTA);
            String nombre = entradaReader.readLine();
            if (gestionFruteria.buscarFrutaPorNombre(nombre)!=null && !gestionFruteria.buscarFrutaPorNombre(nombre).isEmpty()) {
                System.out.println(Constantes.ENCONTRADO_EXITOSO);
                gestionFruteria.buscarFrutaPorNombre(nombre).forEach(System.out::println);
            } else {
                System.out.println(Constantes.ENCONTRADO_FALLIDO);
            }
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_ENTRADA_SALIDA);
        }
    }

    public static void limpiezaFrutas(GestionFruteria gestionFruteria) {
        System.out.println(Constantes.MENU_LIMPIEZA_FRUTAS);
            int op = menu();
            switch (op) {
                case 1 -> {
                    if (gestionFruteria.eliminarFrutasCaducadas()){
                        System.out.println(Constantes.ELIMINADO_EXITOSO);
                    } else {
                        System.out.println(Constantes.ELIMINADO_FALLIDO);
                    }
                }
                case 2 -> {
                    if (gestionFruteria.removeFrutasSinContenido()){
                        System.out.println(Constantes.ELIMINADO_EXITOSO);
                    } else {
                        System.out.println(Constantes.ELIMINADO_FALLIDO);
                    }
                }
                case 3 -> gestionFruteria.eliminarTodo();
                default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
            }
    }

    public static void Top5FrutasVendidas(GestionFruteria gestionFruteria) {
        System.out.println(Constantes.MENU_TOP_FRUTAS);
        int op = menu();
        switch (op) {
            case 1 -> {
                if (gestionFruteria.frutasConMenorNumeroVendido() != null && !gestionFruteria.frutasConMenorNumeroVendido().isEmpty()) {
                    System.out.println(Constantes.SEPARADOR);
                    System.out.println(gestionFruteria.frutasConMayorNumeroVendido());
                    System.out.println(Constantes.SEPARADOR);
                } else {
                    System.out.println(Constantes.NO_SE_ENCONTRARON_FRUTAS_PARA_SER_MOSTRADAS);
                }
            }
            case 2 -> {
                if (gestionFruteria.frutasConMayorNumeroVendido()!=null && !gestionFruteria.frutasConMayorNumeroVendido().isEmpty()) {
                    System.out.println(Constantes.SEPARADOR);
                    System.out.println(gestionFruteria.frutasConMayorNumeroVendido());
                    System.out.println(Constantes.SEPARADOR);
                } else {
                    System.out.println(Constantes.NO_SE_ENCONTRARON_FRUTAS_PARA_SER_MOSTRADAS);
                }
            }
            default -> System.out.println(Constantes.DEBE_INGRESAR_UNA_OPCION_VALIDA);
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
