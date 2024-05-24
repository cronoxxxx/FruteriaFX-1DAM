package service;

import lombok.Getter;
import dao.*;
import domain.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Getter
public class GestionMostrador implements IGestionMostrador {
    private final DaoMostradorImplementacion daoMostradorImplementacion;
    public GestionMostrador() {
        this.daoMostradorImplementacion = new DaoMostradorImplementacion();
    }
    public GestionMostrador(DaoMostradorImplementacion daoMostradorImplementacion) {
        this.daoMostradorImplementacion = daoMostradorImplementacion;
    }
    @Override
    public Set<Factura> getFacturas() {
        return daoMostradorImplementacion.getFacturas();
    }

    @Override
    public boolean isEmptyClientes() {
        return daoMostradorImplementacion.isEmptyClientes();
    }

    @Override
    public boolean putCliente(Cliente valor) {
        return daoMostradorImplementacion.putCliente(valor);
    }

    @Override
    public Map<Integer, Cliente> mostrarInformacion(boolean ascendente) {
        return daoMostradorImplementacion.mostrarInformacion(ascendente);
    }

    @Override
    public Map<Integer, Cliente> mostrarInformacionporNombre(boolean ascendente) {
        return daoMostradorImplementacion.mostrarInformacionporNombre(ascendente);
    }

    @Override
    public List<Double> venderCliente(Cliente clienteComprador, StringBuilder sb, int... cantidadKilos) {
        return daoMostradorImplementacion.venderCliente(clienteComprador, sb, cantidadKilos);
    }

    @Override
    public double getBeneficios() {
        return daoMostradorImplementacion.getBeneficios();
    }



    @Override
    public boolean buscarClienteporID(int id) {
        return daoMostradorImplementacion.buscarClienteporID(id);
    }

    @Override
    public boolean buscarClienteNombreApellido(String nombre, String apellidos) {
        return daoMostradorImplementacion.buscarClienteNombreApellido(nombre, apellidos);
    }

    @Override
    public boolean removeClienteporID(int id) {
        return daoMostradorImplementacion.removeClienteporID(id);
    }

    @Override
    public boolean removeClienteporNombreApellidos(Cliente cliente) {
        return daoMostradorImplementacion.removeClienteporNombreApellidos(cliente);
    }

    @Override
    public boolean aplicarDescuentosClienteporID(int id) {
        return daoMostradorImplementacion.aplicarDescuentosClienteporID(id);
    }

    @Override
    public boolean aplicarDescuentosporClienteNombreApellidos(Cliente descontar) {
        return daoMostradorImplementacion.aplicarDescuentosporClienteNombreApellidos(descontar);
    }

    @Override
    public Cliente devolverClienteOnline(int id) {
        return daoMostradorImplementacion.devolverClienteOnline(id);
    }

    @Override
    public Cliente devolverClienteFisico() {
        return daoMostradorImplementacion.devolverClienteFisico();
    }

    @Override
    public List<Cliente> clienteAccion(String nombre, String apellidos) {
        return daoMostradorImplementacion.clienteAccion(nombre, apellidos);
    }

    @Override
    public boolean reunirClientesPorCiudad(String ciudad) {
        return daoMostradorImplementacion.reunirClientesPorCiudad(ciudad);
    }

    @Override
    public Database leerFicheroBinario() {
        return DaoFicherosFruta.leerFicheroBinarioData();
    }

    @Override
    public boolean escribirFicheroBinario() {
        return DaoFicherosFruta.escribirFicheroBinarioData(daoMostradorImplementacion.getMostrador().getDatabase());
    }

    @Override
    public boolean escribirCambiosFrutaTexto() {
        try {
            return DaoFicherosFruta.escribirFichero(daoMostradorImplementacion.getMostrador().getFruteria().mostrarInformacion(true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Fruta> leerCambiosFrutaTexto() {
        try {
            return DaoFicherosFruta.leerFichero();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Factura> buscarFacturasPorFecha(String date) {
        return daoMostradorImplementacion.buscarFacturasPorFecha(date);
    }
    @Override
    public boolean actualizarFactura(Factura factura, String nombre, String apellidos) {
        return daoMostradorImplementacion.actualizarFactura(factura, nombre, apellidos);
    }
@Override
public Set<Factura> devolverFacturasNombreSet(String nombre, String apellidos){
        return daoMostradorImplementacion.devolverFacturasNombreSet(nombre,apellidos);
    }
}
