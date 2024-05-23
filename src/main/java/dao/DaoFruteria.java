package dao;



import domain.Fruta;

import java.util.List;

public interface DaoFruteria {

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
    boolean frutasDeMismaProcedencia(String nombre1, String nombre2);
    boolean reunirFrutasporProcedencia(String procedencia);
    boolean buscarFrutaPorNombre(String nombreFruta);
    boolean removeFrutasSinContenido();
    boolean eliminarFrutasCaducadas();
    void eliminarTodo();
    boolean updateFruta(Fruta fruta, Fruta fruta2);
    boolean removeFruta(Fruta fruta);
    List<Fruta> frutasConMenorNumeroVendido();
    List<Fruta> frutasConMayorNumeroVendido();
    boolean actualizarPrecioVentaID(int id, double nuevoPrecioVenta);

}
