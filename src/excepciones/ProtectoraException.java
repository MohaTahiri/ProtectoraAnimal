
package excepciones;

public class ProtectoraException extends Exception {

    public ProtectoraException(String mensaje) {
        super(mensaje);
    }

    public ProtectoraException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

