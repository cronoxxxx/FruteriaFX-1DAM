package dao;

import domain.Cliente;
import domain.Factura;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Map;
import java.util.Set;
@Getter@Setter
public class DaoMostradorImplementacion implements DaoMostrador {
    private Mostrador mostrador;

    public DaoMostradorImplementacion() {
        this.mostrador = new Mostrador();
    }


    @Override
    public Set<Factura> getFacturas() {
        return mostrador.getFacturas();
    }
    //JUnit5
    @Override
    public boolean isEmptyClientes() {
        return mostrador.isEmptyClientes();
    }
    //JUnit5
    @Override
    public boolean putCliente(Cliente valor) {
        return mostrador.putCliente(valor);

    }

    @Override
    public Map<Integer, Cliente> mostrarInformacion(boolean ascendente) {
        return mostrador.mostrarInformacion(ascendente);
    }

    @Override
    public Map<Integer, Cliente> mostrarInformacionporNombre(boolean ascendente) {
        return mostrador.mostrarInformacionporNombre(ascendente);
    }

    @Override
    public boolean venderClienteFisico(Cliente clienteComprador, StringBuilder sb, int... cantidadKilos) {
        return mostrador.venderClienteFisico(clienteComprador,sb,cantidadKilos);
    }


    @Override
    public double getBeneficios() {
        return mostrador.getBeneficios();
    }

    @Override
    public boolean venderClienteOnline(Cliente clienteCompradorOnline, StringBuilder sb, int... cantidadKilos) {
        return mostrador.venderClienteOnline(clienteCompradorOnline,sb,cantidadKilos);
    }

    //JUnit5
    @Override
    public boolean buscarClienteporID(int id) {
        return mostrador.buscarClienteporID(id);
    }
    //JUnit5
    @Override
    public boolean buscarClienteNombreApellido(String nombre, String apellidos) {
        return mostrador.buscarClienteNombreApellido(nombre,apellidos);
    }
    //JUnit5
    @Override
    public boolean removeClienteporID(int id) {
        return mostrador.removeClienteporID(id);
    }

    @Override
    public boolean removeClienteporNombreApellidos(Cliente cliente) {
        return mostrador.removeClienteporNombreApellidos(cliente);
    }
    //JUnit5
    @Override
    public boolean aplicarDescuentosClienteporID(int id) {
        return mostrador.aplicarDescuentosClienteporID(id);
    }

    @Override
    public boolean aplicarDescuentosporClienteNombreApellidos(Cliente descontar) {
        return mostrador.aplicarDescuentosporClienteNombreApellidos(descontar);
    }

    @Override
    public Cliente devolverClienteOnline(int id) {
        return mostrador.devolverClienteOnline(id);
    }

    @Override
    public Cliente devolverClienteFisico() {
        return mostrador.devolverClienteFisico();
    }

    @Override
    public List<Cliente> clienteAccion(String nombre, String apellidos) {
        return mostrador.clienteAccion(nombre,apellidos);
    }

    @Override
    public boolean reunirClientesPorCiudad(String ciudad) {
        return mostrador.reunirClientesPorCiudad(ciudad);
    }

    public void eliminarTodo(){
        mostrador.eliminarTodo();
    }

    @Override
    public Set<Factura> buscarFacturasPorFecha(String date) {
        return mostrador.buscarFacturasPorFecha(date);
    }



    @Override
    public Set<Factura> devolverFacturasNombreSet(String nombre, String apellidos){
        return mostrador.devolverFacturasNombreSet(nombre,apellidos);
    }

    @Override
    public boolean actualizarFactura(Factura factura,String nombre, String apellidos) {
        return mostrador.actualizarFactura(factura,nombre,apellidos);
    }
}
