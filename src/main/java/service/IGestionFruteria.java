package service;

import domain.Fruta;

import java.io.FileNotFoundException;
import java.util.List;

public interface IGestionFruteria {
    List<Fruta> getFrutas();
    boolean isEmptyFrutas();
    List<Fruta> mostrarInformacion(boolean ascendente);
    boolean darAltaFruta(Fruta fruta);
    boolean darBajaFrutaPorNombre(String nombreFruta);
    boolean darBajaFrutasPorProcedencia(String procedencia);
    boolean rebajar(double cantidad, int indexFruta);
    boolean rebajarNombreFruta (double cantidad, String nombreFruta);
    boolean subir(double cantidad, int indexFruta);
    boolean subirNombreFruta (double cantidad, String nombreFruta);
    double calcularInventarioTotal();
    boolean actualizarPrecioVenta(Fruta nombreFruta, double nuevoPrecioVenta);
    List<Fruta> frutasDeMismaProcedencia(String nombre1, String nombre2);
    List<Fruta> reunirFrutasporProcedencia(String procedencia);
    List<Fruta> buscarFrutaPorNombre(String nombreFruta);
    boolean removeFrutasSinContenido();
    boolean eliminarFrutasCaducadas();

    boolean updateFruta(Fruta fruta, Fruta fruta2);
    boolean removeFruta(Fruta fruta);

    boolean escribirFichero() throws FileNotFoundException;
    List<Fruta> leerFichero() throws FileNotFoundException;
    void eliminarTodo();
    List<Fruta> frutasConMenorNumeroVendido();
    List<Fruta> frutasConMayorNumeroVendido();
    boolean actualizarPrecioVentaID(int id, double nuevoPrecioVenta);
}
