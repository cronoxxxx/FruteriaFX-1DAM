package domain;

import java.io.Serializable;
import java.util.Comparator;
public class Comparators{
    public static class ComparatorFactura implements Comparator<Factura>, Serializable {
        public ComparatorFactura() {
        }

        @Override
        public int compare(Factura o1, Factura o2) {
            return o1.getFechaHora().compareTo(o2.getFechaHora());
        }

        @Override
        public Comparator<Factura> reversed() {
            return Comparator.super.reversed();
        }


    }

    public static class ComparatorCliente implements Comparator<Cliente>{
        public ComparatorCliente() {
        }

        @Override
        public int compare(Cliente o1, Cliente o2) {
            return o1.getNombre().compareTo(o2.getNombre());
        }

        @Override
        public Comparator<Cliente> reversed() {
            return Comparator.super.reversed();
        }
    }
}

