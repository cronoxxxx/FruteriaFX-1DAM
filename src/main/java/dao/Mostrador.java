package dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import common.*;


import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Log4j2
public class Mostrador implements Serializable {
    private final Map<Integer, Cliente> clientesEsperaCompra;
    private final Fruteria fruteria;
    private double beneficios;
    private final Database database;
    private int fila;
    private final Set<Factura> facturas; //No colocará doble factura

    public Mostrador() {
        database = DaoFicherosFruta.leerFicheroBinarioData();
        fruteria = new Fruteria();
        facturas = loadFacturas();
        clientesEsperaCompra = database.getClientesEsperaCompra();
        this.beneficios = database.getBeneficios();
        this.fila = database.getFila();
    }

    public boolean isEmptyClientes() {
        return clientesEsperaCompra.isEmpty();
    }

    public void saveBeneficios() {
        database.setBeneficios(this.beneficios);
        //DaoFicherosFruta.escribirFicheroBinarioData(database);
    }

    public void saveFila() {
        database.setFila(this.fila);
        //DaoFicherosFruta.escribirFicheroBinarioData(database);
    }


    public boolean putCliente(Cliente valor) {
        if (valor instanceof ClienteFisico) {
            // Obtener el último orden de fila de los clientes físicos existentes
            int ultimoOrdenFila = clientesEsperaCompra.values().stream().filter(cliente -> cliente instanceof ClienteFisico)
                    .mapToInt(cliente -> ((ClienteFisico) cliente).getOrdenFila()).max().orElse(0);
            // Asignar el nuevo cliente físico con un orden de fila incrementado
            ((ClienteFisico) valor).setOrdenFila(ultimoOrdenFila + 1);
            // Agregar el nuevo cliente a la lista
            int maxKey = clientesEsperaCompra.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            return clientesEsperaCompra.putIfAbsent(maxKey + 1, valor) == null;
        } else {
            // Para otros tipos de clientes, agregar sin modificar el orden de fila
            int maxKey = clientesEsperaCompra.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            return clientesEsperaCompra.putIfAbsent(maxKey + 1, valor) == null;
        }
    }

    public Map<Integer, Cliente> mostrarInformacion(boolean ascendente) {
        Map<Integer, Cliente> sortedMap = clientesEsperaCompra.entrySet()
                .stream()
                .sorted(ascendente ? Map.Entry.comparingByKey() : Map.Entry.<Integer, Cliente>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sortedMap;
    }

    public Map<Integer, Cliente> mostrarInformacionporNombre(boolean ascendente) {
        Comparator<Cliente> comparator = new Comparators.ComparatorCliente().thenComparing(Cliente::getApellidos);
        // Comparador para ordenar por nombre y luego por apellido
        Map<Integer, Cliente> sortedMap = clientesEsperaCompra.entrySet().stream()
                .sorted(ascendente ? Map.Entry.comparingByValue(comparator) : Map.Entry.comparingByValue(comparator.reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return sortedMap;
    }

    public ClienteFisico devolverClienteFisico() {
        ClienteFisico clienteComprador = null;
        List<Cliente> clientesEnEspera = new LinkedList<>(clientesEsperaCompra.values());
        //Uso del optional
        Optional<Cliente> clienteEncontrado = clientesEnEspera.stream().filter(c -> c instanceof ClienteFisico).filter(c -> ((ClienteFisico) c).getOrdenFila() == fila).findFirst();
        if (clienteEncontrado.isPresent()) {
            clienteComprador = (ClienteFisico) clienteEncontrado.get();

        }
        fila += 1;
        saveFila();
        return clienteComprador;
    }

    public boolean venderCliente(Cliente clienteComprador, StringBuilder sb, int[] cantidadKilos) {
        List<Fruta> agregarCompraFrutasFactura = new ArrayList<>();
        String nombreFruta;
        double sumadorVenta = 0;
        double descuento;
        List<Double> almacenPrecios = new LinkedList<>();

        String nombreFrutasString = sb.toString();
        nombreFrutasString = nombreFrutasString.substring(0, nombreFrutasString.length() - 2); // Eliminar la ultima coma y espacio
        Scanner frutasScanner = new Scanner(nombreFrutasString).useDelimiter("\\s*,\\s*");
        while (frutasScanner.hasNextLine()) {
            nombreFruta = frutasScanner.next();
            boolean frutaEncontrada = false;
            int j = 0;
            for (int i = 0; i < fruteria.getFrutas().size(); i++) {
                Fruta fruta = fruteria.getFrutas().get(i);
                if (fruta.getNombre().strip().equalsIgnoreCase(nombreFruta.strip())) {

                    // Se encontró la fruta, realizar la venta
                    frutaEncontrada = true;
                    if (fruta.getNumeroKilos() < cantidadKilos[j]) {
                        return false;
                    } else {
                        double precioUnitario = fruta.getPrecioVentaPorKilo();
                        double precioVentaFruta = precioUnitario * cantidadKilos[j];

                        if (clienteComprador.isHasDescuento()) {
                            descuento = precioVentaFruta * 0.7; // Aplicar descuento del 30%
                            almacenPrecios.add(precioVentaFruta * 0.7);
                        } else {
                            descuento = precioVentaFruta;
                            almacenPrecios.add(precioVentaFruta);
                        }
                        fruta.setNumeroKilos(fruta.getNumeroKilos() - cantidadKilos[j]);
                        beneficios += descuento;
                        saveBeneficios();
                        sumadorVenta += descuento;
                        agregarCompraFrutasFactura.add(fruta);
                        fruta.setAgregarNumeroVentas();
                    }
                    j++;
                }

            }

            if (!frutaEncontrada) {
                return false;
            }
        }
        Factura factura = new Factura( clienteComprador.getNombre(), clienteComprador.getApellidos(), agregarCompraFrutasFactura, sumadorVenta, almacenPrecios);
        facturas.add(factura);
        saveFacturas(facturas);
        // Eliminar al cliente del mapa lambda
        clientesEsperaCompra.entrySet().stream()
                .filter(entry -> entry.getValue().equals(clienteComprador))
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(clientesEsperaCompra::remove);
        return true;
    }



    public Cliente devolverClienteOnline(int id) {
        ClienteOnline clienteCompradorOnline;
        Cliente cliente = clientesEsperaCompra.get(id);

        if (cliente != null) {
            if (cliente instanceof ClienteOnline) {
                clienteCompradorOnline = (ClienteOnline) cliente;
                return clienteCompradorOnline;
            }
        }
        return null;
    }


    public Cliente buscarClienteporID(int id) {
        return clientesEsperaCompra.get(id);

    }

    public List<Cliente> buscarClienteNombreApellido(String nombre, String apellidos) {
        List<Cliente> clienteFisico = clientesEsperaCompra.values().stream()
                .filter(cliente -> cliente.getNombre().equalsIgnoreCase(nombre) && cliente.getApellidos().equalsIgnoreCase(apellidos)).toList();
        if (!clienteFisico.isEmpty()) {
            return clienteFisico;
        } else {
            return null;
        }
    }

    public List<ClienteOnline> reunirClientesPorCiudad(String ciudad) {
        if (ciudad == null) {
            return null;
        }
        Map<String, List<ClienteOnline>> clientesPorCiudad = clientesEsperaCompra.values().stream().filter(cliente -> cliente instanceof ClienteOnline)
                .map(cliente -> (ClienteOnline) cliente).collect(Collectors.groupingBy(ClienteOnline::getCiudad));
        if (!clientesPorCiudad.isEmpty() && clientesPorCiudad.containsKey(ciudad)) {
            List<ClienteOnline> clientesCiudad = clientesPorCiudad.get(ciudad);
            if (!clientesCiudad.isEmpty()) {
                return clientesCiudad;
            }
        } else {
            return null;
        }
        return null;
    }


    public boolean removeClienteporID(int id) {
        Cliente cliente = clientesEsperaCompra.get(id);
        if (cliente != null) {
            clientesEsperaCompra.entrySet().stream().filter(entry -> entry.getValue().equals(cliente))
                    .map(Map.Entry::getKey).findFirst().ifPresent(clientesEsperaCompra::remove);
            return true;
        }
        return false;
    }
//this

    public List<Cliente> clienteAccion(String nombre, String apellidos) {
        return clientesEsperaCompra.values().stream()
                .filter(cliente -> cliente.getNombre().strip().equalsIgnoreCase(nombre) &&
                        cliente.getApellidos().strip().equalsIgnoreCase(apellidos)).toList();
    }

    public boolean removeClienteporNombreApellidos(Cliente clienteAEliminar) {
        if (clienteAEliminar != null) {
            clientesEsperaCompra.entrySet().stream().filter(entry -> entry.getValue().equals(clienteAEliminar))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(clientesEsperaCompra::remove);
            return true;
        }
        return false;
    }


    public boolean aplicarDescuentosClienteporID(int id) {
        Cliente clienteAplicarDescuento = clientesEsperaCompra.get(id);
        if (clienteAplicarDescuento != null) {
            if (!clienteAplicarDescuento.isHasDescuento()) {
                clienteAplicarDescuento.setHasDescuento(true);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    //this
    public boolean aplicarDescuentosporClienteNombreApellidos(Cliente clienteADescontar) {
        boolean aplicado = false;
        if (clienteADescontar != null) {
            if (!clienteADescontar.isHasDescuento()) {
                clienteADescontar.setHasDescuento(true); // Aplicar descuento al cliente seleccionado
                aplicado = true;
            }
        }
        return aplicado;
    }

    public void eliminarTodo() {
        clientesEsperaCompra.clear();
    }



    /*public boolean buscarFacturasporNombreCliente(String nombre) {
        return facturas.stream().anyMatch(factura -> factura.getCliente().strip().equalsIgnoreCase(nombre));
    }*/


    public Set<Factura> buscarFacturasPorFecha(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(date, formatter);
        return facturas.stream().filter(factura -> factura.getFechaHora().toLocalDate().getMonth().equals(fecha.getMonth()))
                .collect(Collectors.toSet());
    }


    public Set<Factura> devolverFacturasNombreSet(String nombre, String apellidos) { //usar para actualizar factura
        return facturas.stream().filter(factura -> factura.getCliente().strip().equalsIgnoreCase(nombre) && factura.getApellido().strip().equalsIgnoreCase(apellidos)).collect(Collectors.toSet());
    }

    public boolean actualizarFactura(Factura factura, String nombre, String apellidos) {
        if (factura != null && nombre != null && apellidos != null) {
            factura.setCliente(nombre);
            factura.setApellido(apellidos);
            return saveFacturas(facturas);
        }
        return false;
    }


    public Set<Factura> getFacturas() {
        return loadFacturas();
    }


    public Set<Factura> loadFacturas() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
        Type userListType = new TypeToken<TreeSet<Factura>>() {
        }.getType();

        Set<Factura> facturas = null;
        try {
            facturas = gson.fromJson(
                    new FileReader(new Configuracion().loadPathProperties()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return facturas;
    }

    public boolean saveFacturas(Set<Factura> facturas) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();

        try (FileWriter fw = new FileWriter(new Configuracion().loadPathProperties())) {
            gson.toJson(facturas, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
