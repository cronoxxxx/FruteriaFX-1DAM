package dao;




import domain.Cliente;
import domain.ClienteOnline;
import domain.Factura;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DaoMostrador {
    Set<Factura> getFacturas();
    boolean isEmptyClientes();
    boolean putCliente(Cliente valor);
    Map<Integer, Cliente> mostrarInformacion(boolean ascendente);
    Map<Integer, Cliente> mostrarInformacionporNombre(boolean ascendente);


    boolean venderCliente(Cliente clienteComprador, StringBuilder sb, int ...cantidadKilos);

    double getBeneficios();

    Cliente buscarClienteporID(int id);
    List<Cliente> buscarClienteNombreApellido(String nombre, String apellidos);
    boolean removeClienteporID(int id);
    boolean removeClienteporNombreApellidos(Cliente cliente);
    boolean aplicarDescuentosClienteporID(int id);
    boolean aplicarDescuentosporClienteNombreApellidos(Cliente descontar);
    Cliente devolverClienteOnline (int id);
    Cliente devolverClienteFisico ();
    List<Cliente> clienteAccion (String nombre, String apellidos);
    List<ClienteOnline> reunirClientesPorCiudad(String ciudad);
    void eliminarTodo();
    Set<Factura> buscarFacturasPorFecha(String date);


    Set<Factura> devolverFacturasNombreSet(String nombre, String apellidos);

    boolean actualizarFactura(Factura factura, String nombre, String apellidos);

}
