package common;

public class FechaInvalidaException extends Exception{
    public FechaInvalidaException() {
        super("La fecha proporcionada es menor que la fecha actual");
    }

    public FechaInvalidaException(String message) {
        super(message);
    }
}
