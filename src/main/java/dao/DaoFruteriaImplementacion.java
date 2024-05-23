package dao;



import lombok.Getter;
import lombok.Setter;
import domain.Fruta;

import java.util.List;
@Getter@Setter
public class DaoFruteriaImplementacion implements DaoFruteria {
    private Fruteria fruteria;

    public DaoFruteriaImplementacion(Fruteria fruteria) {
        this.fruteria = fruteria;
    }

    public DaoFruteriaImplementacion() {
        this.fruteria = new Fruteria();
    }
    //JUnit5
    @Override
    public List<Fruta> getFrutas() {
        return fruteria.getFrutas();
    }
    //JUnit5
    @Override
    public boolean isEmptyFrutas() {
        return fruteria.isEmptyFrutas();
    }
    //JUnit5
    @Override
    public List<Fruta> mostrarInformacion(boolean ascendente) {
        return fruteria.mostrarInformacion(ascendente);
    }
    //JUnit5
    @Override
    public boolean darAltaFruta(Fruta fruta) {
        return fruteria.darAltaFruta(fruta);
    }

    //JUnit5
    @Override
    public boolean darBajaFrutaPorNombre(String nombreFruta) {
        return fruteria.darBajaFrutaPorNombre(nombreFruta);
    }
    //JUnit5
    @Override
    public boolean darBajaFrutasPorProcedencia(String procedencia) {
        return fruteria.darBajaFrutasPorProcedencia(procedencia);
    }
    //JUnit5
    @Override
    public boolean rebajar(double cantidad, int indexFruta) {
        return fruteria.rebajar(cantidad, indexFruta);
    }
    //JUnit5
    @Override
    public boolean rebajarNombreFruta(double cantidad, String nombreFruta) {
        return fruteria.rebajarNombreFruta(cantidad,nombreFruta);
    }
    //JUnit5
    @Override
    public boolean subir(double cantidad, int indexFruta) {
        return fruteria.subir(cantidad,indexFruta);
    }
    //JUnit5
    @Override
    public boolean subirNombreFruta(double cantidad, String nombreFruta) {
        return fruteria.subirNombreFruta(cantidad,nombreFruta);
    }

    @Override
    public double calcularInventarioTotal() {
        return fruteria.calcularInventarioTotal();
    }
    //JUnit5
    @Override
    public boolean actualizarPrecioVenta(Fruta nombreFruta, double nuevoPrecioVenta) {
        return fruteria.actualizarPrecioVenta(nombreFruta,nuevoPrecioVenta);
    }
//JUnit5
    @Override
    public boolean frutasDeMismaProcedencia(String nombre1, String nombre2) {
        return fruteria.frutasDeMismaProcedencia(nombre1,nombre2);
    }


    public boolean reunirFrutasporProcedencia(String procedencia) {
        return fruteria.reunirFrutasporProcedencia(procedencia);
    }
    //JUnit5
    @Override
    public boolean buscarFrutaPorNombre(String nombreFruta) {
        return fruteria.buscarFrutaPorNombre(nombreFruta);
    }


    //JUnit5
    @Override
    public boolean removeFrutasSinContenido() {
        return fruteria.removeFrutasSinContenido();
    }
    //JUnit5
    @Override
    public boolean eliminarFrutasCaducadas() {
        return fruteria.eliminarFrutasCaducadas();
    }
    public void eliminarTodo(){
        fruteria.eliminarTodo();
    }

    @Override
    public boolean updateFruta(Fruta fruta, Fruta nuevo) {
        return fruteria.updateFruta(fruta,nuevo);
    }

    @Override
    public boolean removeFruta(Fruta fruta) {
        return fruteria.removeFruta(fruta);
    }

    @Override
    public List<Fruta> frutasConMenorNumeroVendido() {
        return fruteria.frutasConMenorNumeroVendido();
    }

    @Override
    public List<Fruta> frutasConMayorNumeroVendido() {
        return fruteria.frutasConMayorNumeroVendido();
    }

    @Override
    public boolean actualizarPrecioVentaID(int id, double nuevoPrecioVenta) {
        return fruteria.actualizarPrecioVentaID(id,nuevoPrecioVenta);
    }

}
