package dao;

import domain.Fruta;
import lombok.Getter;
import common.*;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Fruteria implements Serializable {
    private final List<Fruta> frutas;

    //crear un hashmap de clientes
    public Fruteria(List<Fruta> frutas) {
        this.frutas = frutas;
    }

    public Fruteria() {
        try {
            frutas = DaoFicherosFruta.leerFichero();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
/*
        for (int i = 0; i < cantidadAlmacen; i++) {
            try {
                double precioCoste = (Math.random() * 40) + 1;
                double precioVenta = precioCoste + (Math.random() * 40) + 1;
                frutas.add(new Fruta(precioCoste, precioVenta));
            } catch (precioVentaExcepcion e) {
                System.out.println(e.getMessage());
                entrada.nextLine();
                i--;
            }


        }*/



    /*public Fruteria (){
        this(50);
    }*/

    public boolean isEmptyFrutas() {
        return frutas.isEmpty();
    }

    //propio de fruteria
    public List<Fruta> mostrarInformacion(boolean ascendente) {
        List<Fruta> elementosOrdenados = new ArrayList<>();
        elementosOrdenados = frutas;
        Collections.sort(elementosOrdenados);
        if (!ascendente) {
            Collections.reverse(elementosOrdenados);
        }
        return elementosOrdenados;
    }


    public boolean darAltaFruta(Fruta fruta) {
        // Verificar si el nombre de la fruta ya existe en la lista
        boolean nombreRepetido = frutas.stream().anyMatch(f -> f.getNombre().equalsIgnoreCase(fruta.getNombre()));
        // Agregar la fruta si el nombre no está repetido, de lo contrario, imprimir un mensaje de error
        if (!nombreRepetido) {
            frutas.add(fruta);
            System.out.println(Constantes.FRUTA_AGREGADA_CON_EXITO);
            return true;
        } else {
            System.out.println(Constantes.YA_EXISTE_UNA_FRUTA_CON_EL_MISMO_NOMBRE_EN_LA_FRUTERIA);
        }
        try {
            DaoFicherosFruta.escribirFichero(frutas);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean removeFruta(Fruta fruta) {
        if (frutas.remove(fruta)) {
            try {
                DaoFicherosFruta.escribirFichero(frutas);
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return false;


    }

    public boolean updateFruta(Fruta fruta1, Fruta fruta2) {
        frutas.remove(fruta2);
        frutas.add(fruta1);
        try {
            DaoFicherosFruta.escribirFichero(frutas);
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean darBajaFrutaPorNombre(String nombreFruta) {
        return frutas.removeIf(fruta -> fruta.getNombre().equalsIgnoreCase(nombreFruta));
    }

    public boolean darBajaFrutasPorProcedencia(String procedencia) {
        return frutas.removeIf(fruta -> fruta.getProcedencia().equalsIgnoreCase(procedencia));
    }


    public boolean rebajar(double cantidad, int indexFruta) {
        if (indexFruta >= 0 && indexFruta < frutas.size()) {
            Fruta fruta = frutas.get(indexFruta);
            if (fruta != null) {
                double nuevoPrecioVenta = fruta.getPrecioVentaPorKilo() - cantidad;
                if (nuevoPrecioVenta >= fruta.getPrecioCostePorKilo()) {
                    fruta.setPrecioVentaPorKilo(nuevoPrecioVenta);
                    System.out.println(fruta);
                    return true;
                } else {
                    System.out.println(Constantes.LA_CANTIDAD_DE_REBAJA_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_MENOR_QUE_EL_PRECIO_DE_COSTE);
                }
            } else {
                System.out.println(Constantes.ERROR_FRUTA_NULA);
            }
        } else {
            System.out.println(Constantes.INDICE_FUERA_DE_RANGO);
        }
        return false;
    }

    public boolean rebajarNombreFruta(double cantidad, String nombreFruta) {
        Fruta auxLista = null;
        for (Fruta fruta : frutas) {
            if (fruta.getNombre().strip().equalsIgnoreCase(nombreFruta)) {
                auxLista = fruta;
            }
        }
        if (auxLista != null) {
            double nuevoPrecioVenta = auxLista.getPrecioVentaPorKilo() - cantidad;
            if (nuevoPrecioVenta >= auxLista.getPrecioCostePorKilo()) {
                auxLista.setPrecioVentaPorKilo(nuevoPrecioVenta);
                System.out.println(auxLista);
            } else {
                System.out.println(Constantes.LA_CANTIDAD_DE_REBAJA_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_MENOR_QUE_EL_PRECIO_DE_COSTE);
                return false;
            }
        } else {
            System.err.println(Constantes.NO_SE_HA_ENCONTRADO_LA_FRUTA_LLAMADA + nombreFruta);
            return false;
        }
        return true;
    }

    public boolean subir(double cantidad, int indexFruta) {
        if (indexFruta >= 0 && indexFruta < frutas.size()) {
            Fruta fruta = frutas.get(indexFruta);
            if (fruta != null) {
                double nuevoPrecioVenta = fruta.getPrecioVentaPorKilo() + cantidad;
                if (nuevoPrecioVenta >= fruta.getPrecioCostePorKilo() && nuevoPrecioVenta < 1000000) {
                    fruta.setPrecioVentaPorKilo(nuevoPrecioVenta);
                    System.out.println(fruta);
                    return true;
                } else {
                    System.out.println(Constantes.LA_CANTIDAD_DE_PRECIO_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_INNACESIBLE_PARA_NUESTROS_CLIENTES);
                }
            } else {
                System.out.println(Constantes.ERROR_FRUTA_NULA);
            }

        } else {
            System.out.println(Constantes.INDICE_FUERA_DE_RANGO);
        }
        return false;
    }

    public boolean subirNombreFruta(double cantidad, String nombreFruta) {
        Fruta auxLista = null;
        for (Fruta fruta : frutas) {
            if (fruta.getNombre().strip().equalsIgnoreCase(nombreFruta)) {
                auxLista = fruta;
            }
        }
        if (auxLista != null) {
            double nuevoPrecioVenta = auxLista.getPrecioVentaPorKilo() + cantidad;
            if (nuevoPrecioVenta >= auxLista.getPrecioCostePorKilo() && nuevoPrecioVenta < 1000000) {
                auxLista.setPrecioVentaPorKilo(nuevoPrecioVenta);
                System.out.println(auxLista);
            } else {
                System.out.println(Constantes.LA_CANTIDAD_DE_PRECIO_ES_DEMASIADO_GRANDE_Y_HARIA_QUE_EL_PRECIO_DE_VENTA_SEA_INNACESIBLE_PARA_NUESTROS_CLIENTES);
                return false;
            }
        } else {
            System.err.println(Constantes.NO_SE_HAN_ENCONTRADO_NINGUNA_FRUTA_LLAMADA + nombreFruta);
            return false;
        }
        return true;
    }

    public double calcularInventarioTotal() {
        double sum = frutas.stream().mapToDouble(fruta -> fruta.getPrecioVentaPorKilo() * fruta.getNumeroKilos()).sum();
        return sum <= 0 ? -1 : sum;
    }

    public boolean actualizarPrecioVenta(Fruta nombreFruta, double nuevoPrecioVenta) {
        if (nuevoPrecioVenta < nombreFruta.getPrecioCostePorKilo()) {
            return false;
        }
        nombreFruta.setPrecioVentaPorKilo(nuevoPrecioVenta);
        return true;
    }

    public boolean actualizarPrecioVentaID(int id, double nuevoPrecioVenta) {
        if (id < 0 || id >= frutas.size()) {
            return false;
        }
        Fruta fruta = frutas.get(id);
        if (fruta == null) {
            return false;
        }
        if (nuevoPrecioVenta < fruta.getPrecioCostePorKilo()) {
            return false;
        }
        fruta.setPrecioVentaPorKilo(nuevoPrecioVenta);
        return true;
    }

    public boolean frutasDeMismaProcedencia(String nombre1, String nombre2) {
        boolean valid = false;
        for (int i = 0; i < frutas.size() && !valid; i++) {
            if (frutas.get(i).getNombre().strip().equalsIgnoreCase(nombre1)) {
                for (int j = 0; j < frutas.size() && !valid; j++) {
                    if (frutas.get(j).getNombre().strip().equalsIgnoreCase(nombre2)) {
                        if (frutas.get(i).getProcedencia().equalsIgnoreCase(frutas.get(j).getProcedencia())) {
                            System.out.println(Constantes.SE_HAN_ENCONTRADO_DOS_FRUTAS_CON_LA_MISMA_PROCEDENCIA + frutas.get(i) + "\n" + frutas.get(j));
                            valid = true;
                        }
                    }
                }
            }
        }

        return valid;
    }


    public boolean reunirFrutasporProcedencia(String procedencia) {
        Map<String, List<Fruta>> frutasPorProcedencia = frutas.stream().collect(Collectors.groupingBy(Fruta::getProcedencia));
        List<Fruta> frutasProcedencia = frutasPorProcedencia.get(procedencia);
        if (frutasProcedencia != null && !frutasProcedencia.isEmpty()) {
            System.out.println(Constantes.FRUTAS_DE + procedencia);
            System.out.println(Constantes.SEPARADOR);
            frutasProcedencia.forEach(System.out::println);
            return true;
        } else {
            System.out.println(Constantes.NO_EXISTEN_FRUTAS_DE + procedencia);
            return false;
        }
    }

    public boolean buscarFrutaPorNombre(String nombreFruta) {
        boolean valido = false;
        Fruta aux = null;
        List<Fruta> frutasEncontradas = frutas.stream()
                .filter(fruta -> fruta.getNombre().strip().equalsIgnoreCase(nombreFruta))
                .toList();

        if (!frutasEncontradas.isEmpty()) {
            aux = frutasEncontradas.get(0);
            if (aux != null) {
                System.out.println(Constantes.SE_HA_ENCONTRADO_LA_FRUTA_LLAMADA + aux.getNombre());
                System.out.println(Constantes.SEPARADOR);
                System.out.println(aux);
                valido = true;
            } else {
                System.out.println(Constantes.ERROR_FRUTA_NULA);
            }
        } else {
            System.out.println(Constantes.LA_FRUTA_NO_SE_ENCONTRO_EN_EL_INVENTARIO);
        }
        return valido;
    }

    public List<Fruta> frutasConMayorNumeroVendido() {
        return frutas.stream().sorted(Comparator.comparingDouble(Fruta::getCuantasVecesSeVendio).reversed()).limit(5).toList();
    }

    public List<Fruta> frutasConMenorNumeroVendido() {
        return frutas.stream().sorted(Comparator.comparingDouble(Fruta::getCuantasVecesSeVendio)).limit(5).toList();
    }

    public boolean removeFrutasSinContenido() {
        return frutas.removeIf(fruta -> fruta.getNumeroKilos() <= 0);
    }

    public boolean eliminarFrutasCaducadas() {
        return frutas.removeIf(fruta -> fruta.getFechaCaducidad().isBefore(LocalDate.now().plusDays(1)));
    }

    public void eliminarTodo() {
        frutas.clear();
    }


}
