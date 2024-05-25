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
            try {
                frutas.add(fruta);
                DaoFicherosFruta.escribirFichero(frutas);
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

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

    public Fruta devolverFrutaNombre (String nombreFruta) {
        for (Fruta fruta : frutas) {
            if (fruta.getNombre().equalsIgnoreCase(nombreFruta)) {
                return fruta;
            }
        }
        return null;

    }


    public boolean rebajar(double cantidad, int indexFruta) {
        if (indexFruta >= 0 && indexFruta < frutas.size()) {
            Fruta fruta = frutas.get(indexFruta);
            if (fruta != null) {
                double nuevoPrecioVenta = fruta.getPrecioVentaPorKilo() - cantidad;
                if (nuevoPrecioVenta >= fruta.getPrecioCostePorKilo()) {
                    fruta.setPrecioVentaPorKilo(nuevoPrecioVenta);
                    return true;
                }
            }
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
            } else {
                return false;
            }
        } else {
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
                    return true;
                }
            }
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
            } else {
                return false;
            }
        } else {
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
        frutas.get(id).setPrecioVentaPorKilo(nuevoPrecioVenta);
        return true;
    }

    public List<Fruta> frutasDeMismaProcedencia(String nombre1, String nombre2) {

        List<Fruta> frutasProcedencia = new ArrayList<>();
        boolean valid = false;
        for (int i = 0; i < frutas.size() && !valid; i++) {
            if (frutas.get(i).getNombre().strip().equalsIgnoreCase(nombre1)) {
                for (int j = 0; j < frutas.size() && !valid; j++) {
                    if (frutas.get(j).getNombre().strip().equalsIgnoreCase(nombre2)) {
                        if (frutas.get(i).getProcedencia().equalsIgnoreCase(frutas.get(j).getProcedencia())) {
                            frutasProcedencia.add(frutas.get(i));
                            frutasProcedencia.add(frutas.get(j));
                            valid = true;
                        }
                    }
                }
            }
        }

        return frutasProcedencia;
    }


    public List<Fruta> reunirFrutasporProcedencia(String procedencia) {
        Map<String, List<Fruta>> frutasPorProcedencia = frutas.stream().collect(Collectors.groupingBy(Fruta::getProcedencia));
        List<Fruta> frutasProcedencia = frutasPorProcedencia.get(procedencia);
        if (frutasProcedencia != null && !frutasProcedencia.isEmpty()) {
            return frutasProcedencia;
        } else {
            return null;
        }
    }

    public List<Fruta> buscarFrutaPorNombre(String nombreFruta) {
        return frutas.stream().filter(fruta -> fruta.getNombre().strip().equalsIgnoreCase(nombreFruta)).toList();
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
