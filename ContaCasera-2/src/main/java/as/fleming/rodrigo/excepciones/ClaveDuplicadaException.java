package as.fleming.rodrigo.excepciones;

public class ClaveDuplicadaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClaveDuplicadaException(String mensaje) {
		super(mensaje);
	}
}
