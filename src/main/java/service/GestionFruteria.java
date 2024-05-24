package service;

import lombok.Getter;
import dao.DaoFicherosFruta;
import dao.DaoFruteriaImplementacion;
import domain.Fruta;

import java.io.FileNotFoundException;
import java.util.List;
@Getter
public class GestionFruteria implements IGestionFruteria {
    private final DaoFruteriaImplementacion daoFruteriaImplementacion;

    public GestionFruteria() {
        this.daoFruteriaImplementacion = new DaoFruteriaImplementacion();
    }
    public GestionFruteria(DaoFruteriaImplementacion daoFruteriaImplementacion) {
        this.daoFruteriaImplementacion = daoFruteriaImplementacion;
    }

    @Override
    public List<Fruta> getFrutas() {
        return daoFruteriaImplementacion.getFrutas();
    }

    @Override
    public boolean isEmptyFrutas() {
        return daoFruteriaImplementacion.isEmptyFrutas();
    }

    @Override
    public List<Fruta> mostrarInformacion(boolean ascendente) {
        return daoFruteriaImplementacion.mostrarInformacion(ascendente);
    }

    @Override
    public boolean darAltaFruta(Fruta fruta) {
        return daoFruteriaImplementacion.darAltaFruta(fruta);
    }

    @Override
    public boolean darBajaFrutaPorNombre(String nombreFruta) {
        return daoFruteriaImplementacion.darBajaFrutaPorNombre(nombreFruta);
    }

    @Override
    public boolean darBajaFrutasPorProcedencia(String procedencia) {
        return daoFruteriaImplementacion.darBajaFrutasPorProcedencia(procedencia);
    }

    @Override
    public boolean rebajar(double cantidad, int indexFruta) {
        return daoFruteriaImplementacion.rebajar(cantidad, indexFruta);
    }

    @Override
    public boolean rebajarNombreFruta(double cantidad, String nombreFruta) {
        return daoFruteriaImplementacion.rebajarNombreFruta(cantidad, nombreFruta);
    }

    @Override
    public boolean subir(double cantidad, int indexFruta) {
        return daoFruteriaImplementacion.subir(cantidad, indexFruta);
    }

    @Override
    public boolean subirNombreFruta(double cantidad, String nombreFruta) {
        return daoFruteriaImplementacion.subirNombreFruta(cantidad, nombreFruta);
    }

    @Override
    public double calcularInventarioTotal() {
        return daoFruteriaImplementacion.calcularInventarioTotal();
    }

    @Override
    public boolean actualizarPrecioVenta(Fruta nombreFruta, double nuevoPrecioVenta) {
        return daoFruteriaImplementacion.actualizarPrecioVenta(nombreFruta, nuevoPrecioVenta);
    }

    @Override
    public List<Fruta> frutasDeMismaProcedencia(String nombre1, String nombre2) {
        return daoFruteriaImplementacion.frutasDeMismaProcedencia(nombre1, nombre2);
    }

    @Override
    public List<Fruta> reunirFrutasporProcedencia(String procedencia) {
        return daoFruteriaImplementacion.reunirFrutasporProcedencia(procedencia);
    }

    @Override
    public List<Fruta> buscarFrutaPorNombre(String nombreFruta) {
        return daoFruteriaImplementacion.buscarFrutaPorNombre(nombreFruta);
    }

    @Override
    public boolean removeFrutasSinContenido() {
        return daoFruteriaImplementacion.removeFrutasSinContenido();
    }

    @Override
    public boolean eliminarFrutasCaducadas() {
        return daoFruteriaImplementacion.eliminarFrutasCaducadas();
    }

    @Override
    public boolean updateFruta(Fruta fruta, Fruta nuevaFruta) {
        return daoFruteriaImplementacion.updateFruta(fruta, nuevaFruta);
    }

    @Override
    public boolean removeFruta(Fruta fruta) {
        return daoFruteriaImplementacion.removeFruta(fruta);
    }

    @Override
    public boolean escribirFichero()  {
        try {
            return DaoFicherosFruta.escribirFichero(mostrarInformacion(true));
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }
        return false;
    }

    @Override
    public List<Fruta> leerFichero() throws FileNotFoundException {
        return DaoFicherosFruta.leerFichero();
    }

    @Override
    public void eliminarTodo() {
        daoFruteriaImplementacion.eliminarTodo();
    }

    @Override
    public List<Fruta> frutasConMenorNumeroVendido() {
        return daoFruteriaImplementacion.frutasConMenorNumeroVendido();
    }

    @Override
    public List<Fruta> frutasConMayorNumeroVendido() {
        return daoFruteriaImplementacion.frutasConMayorNumeroVendido();
    }

    @Override
    public boolean actualizarPrecioVentaID(int id, double nuevoPrecioVenta) {
        return daoFruteriaImplementacion.actualizarPrecioVentaID(id, nuevoPrecioVenta);
    }
}
