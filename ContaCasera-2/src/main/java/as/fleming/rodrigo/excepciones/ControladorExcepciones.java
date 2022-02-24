package as.fleming.rodrigo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControladorExcepciones extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ClaveAjenaException.class)
	public ResponseEntity<String> tratarExcepcionClaveAjena(ClaveAjenaException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(ClaveInexistenteException.class)
	public ResponseEntity<String> tratarExcepcionClaveInexistente(ClaveInexistenteException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(ClaveDuplicadaException.class)
	public ResponseEntity<String> tratarExcepcionClaveDuplicada(ClaveDuplicadaException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

}
