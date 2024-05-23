package common;

public class precioVentaExcepcion extends Exception{
    public precioVentaExcepcion() {
        super("El precio de venta supera al precio de coste, no se puede realizar la acción");
    }

    public precioVentaExcepcion(String message) {
        super(message);
    }
}
