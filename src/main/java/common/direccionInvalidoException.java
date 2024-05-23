package common;

public class direccionInvalidoException extends Exception {
    public direccionInvalidoException() {
        super(" La direccion no es valida");
    }
    public direccionInvalidoException(String message) {
        super(message);
    }

}
