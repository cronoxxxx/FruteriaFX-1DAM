package domain;
//INJECTMOCK -> SERVICIO ||||| MOCK --> DAOFRUTERIA | DAOMOSTRADOR
//INJECTMOCK -> DAO ||||| MOCK --> FRUTERIA | MOSTRADOR
import common.*;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Getter
@Setter
public class ClienteOnline extends Cliente implements Serializable {
    private LocalDate fechaEntregaPedido;
    private String direccionEntregaPedido,ciudad;

    public ClienteOnline(String nombre, String apellidos, LocalDate fechaEntregaPedido, String direccionEntregaPedido, boolean hasDescuento, String ciudad) throws FechaInvalidaException, direccionInvalidoException, AgregarProvinciasException {
        super(nombre, apellidos,hasDescuento);
        this.fechaEntregaPedido = fechaEntregaPedido;
        this.direccionEntregaPedido = direccionEntregaPedido;
        this.ciudad = ciudad;
        EnumComprobacionDirecta.fechaOK(fechaEntregaPedido);
        EnumComprobacionDirecta.direccionOK(direccionEntregaPedido);
        EnumComprobacionDirecta.provinciaOK(ciudad);

    }

    public ClienteOnline() {
        Provincias[] p = Provincias.values();
        Faker f = new Faker();
        this.fechaEntregaPedido = generarFechaAleatoria();
        this.direccionEntregaPedido = f.address().fullAddress();
        this.ciudad = String.valueOf(p[(int)(Math.random()*p.length)]);

    }
    public LocalDate generarFechaAleatoria() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Calcular el número de días entre la fecha actual y el año 2026
        long diasHasta2026 = ChronoUnit.DAYS.between(fechaActual, LocalDate.of(2026, 1, 1));
        // Generar un número aleatorio de días entre 0 y diasHasta2026
        long diasAleatorios = (long) (Math.random() * (diasHasta2026 + 1));
        // Calcular la fecha aleatoria sumando los días aleatorios a la fecha actual
        LocalDate fechaAleatoria = fechaActual.plusDays(diasAleatorios);

        return fechaAleatoria;
    }



    @Override
    public String toString() {
        return String.format("Nombre: %s\nApellido %s\nDireccion del pedido: %s\nTicket descuento: %b\nFecha de la entrega: %s\nCiudad %s\n",nombre,apellidos,direccionEntregaPedido,hasDescuento,fechaEntregaPedido,ciudad);
    }
}
