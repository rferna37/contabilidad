package as.fleming.rodrigo.excepciones;

public class GrabarFicheroException extends Exception {
	private static final long serialVersionUID = 1L;
    private String msg;

    public GrabarFicheroException(String msg) {
        this.msg = msg;
    }

    public String getMensaje() {
        return msg;
    }
}
