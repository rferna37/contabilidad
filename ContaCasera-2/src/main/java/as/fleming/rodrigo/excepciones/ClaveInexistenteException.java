package as.fleming.rodrigo.excepciones;

public class ClaveInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClaveInexistenteException(String mensaje) {
		super(mensaje);
	}

}

