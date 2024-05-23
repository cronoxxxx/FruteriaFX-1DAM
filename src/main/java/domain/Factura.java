package domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Getter@Setter
public class Factura implements Serializable, Comparable<Factura> {
   private String cliente;
   private  String apellido;
   private LocalDateTime fechaHora;
   private  List<Fruta> frutasVendidas;
   private  double precioCompra;
   private  List<Double>almacenVenta;

    public Factura(String cliente, String apellido, List<Fruta> frutasVendidas, double precioCompra, List<Double> almacenVenta) {
        this.cliente = cliente;
        this.apellido = apellido;
        this.fechaHora = LocalDateTime.now();
        this.frutasVendidas = frutasVendidas;
        this.precioCompra = precioCompra;
        this.almacenVenta = almacenVenta;
    }



    @Override
    public String toString() {
        StringBuilder nombres = new StringBuilder();
        nombres.append("-------------------------------------------------------\n");
        String var = String.format("Cliente %s\nApellido %s\nFecha de la venta %s\n", cliente, apellido, fechaHora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        nombres.append(var);
        nombres.append("Frutas compradas\n---------\n");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i<frutasVendidas.size(); i++) {
            nombres.append(i + 1).append(". ").append(frutasVendidas.get(i).getNombre()).append("\t->\t").append(decimalFormat.format(almacenVenta.get(i))).append("€\n");
        }
        nombres.append("Total: ").append(decimalFormat.format(precioCompra)).append("€\n");
        nombres.append("-------------------------------------------------------\n");
        return String.valueOf(nombres);
    }

    @Override
    public int compareTo(Factura o) {
        return LocalDateTime.now().compareTo(o.getFechaHora());
    }
}

