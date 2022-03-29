package as.fleming.rodrigo.web;

import java.net.URLConnection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.entidades.Cuenta;
import as.fleming.rodrigo.excepciones.ClaveAjenaException;
import as.fleming.rodrigo.excepciones.ClaveInexistenteException;
import as.fleming.rodrigo.servicios.DAOService;

@RestController
@RequestMapping
public class ControladorRest {
	@Autowired
	private DAOService servicio;
	
	@GetMapping(path="/eliminar/{codApunte}")
	public String eliminarApunte(@PathVariable("codApunte") String codApunte) {
		servicio.eliminar(codApunte);
		return "Fin operación";	
	}
	
	@GetMapping(path="/eliminarDocu/{codDocumento}")
	public String eliminarDocumento(@PathVariable("codDocumento") String documento) {
		servicio.eliminarDocu(documento);
		return "Fin operación";	
	}
	
	@GetMapping("/eliminarConcepto/{codConcepto}")
    public String eliminarConcepto(@PathVariable("codConcepto") String concepto) {
    	try {
    		servicio.eliminarConcepto(concepto);
    	} catch (DataIntegrityViolationException e) {
    		throw new ClaveAjenaException("No se puede eliminar porque existen apuntes con este concepto" );
    	} catch (EmptyResultDataAccessException e) {
    		throw new ClaveInexistenteException("No existe concepto con el código: " + concepto );
    	}
		
		return "Fin operación";	
    }
	
	@GetMapping(path="/modificarConcepto")
	public String modificarConcepto(@RequestParam Map<String, String> parametros) {
		Concepto concepto = new Concepto();
		concepto.setCodigo(parametros.get("codigo"));
		concepto.setDescripcion(parametros.get("descripcion"));
		if (!servicio.existeConcepto(concepto.getCodigo())) {
			throw new ClaveInexistenteException("No existe concepto con el código: " + concepto.getCodigo() + 
					    ".<br>Refresque la página" );
		}
		servicio.grabarConcepto(concepto);
		return "Fin operación";
	}
	
	@GetMapping("/eliminarCuenta/{codCuenta}")
    public String eliminarCuenta(@PathVariable("codCuenta") String cuenta) {
    	try {
    		servicio.eliminarCuenta(cuenta);
    	} catch (DataIntegrityViolationException e) {
    		throw new ClaveAjenaException("No se puede eliminar porque existen apuntes con esta cuenta" );
    	} catch (EmptyResultDataAccessException e) {
    		throw new ClaveInexistenteException("No existe cuenta con el código: " + cuenta );
    	}
		
		return "Fin operación";	
    }
	
	@GetMapping(path="/modificarCuenta")
	public String modificarCuenta(@RequestParam Map<String, String> parametros) {
		Cuenta cuenta = new Cuenta();
		cuenta.setCodigo(parametros.get("codigo"));
		cuenta.setNombre(parametros.get("descripcion"));
		if (!servicio.existeCuenta(cuenta.getCodigo())) {
			throw new ClaveInexistenteException("No existe cuenta con el código: " + cuenta.getCodigo() + 
					    ".<br>Refresque la página" );
		}
		servicio.grabarCuenta(cuenta);
		return "Fin operación";
	}
	
	@GetMapping("/documento/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = servicio.loadDocumento(filename);
        String mimeType = URLConnection.guessContentTypeFromName(file.getFilename());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }
	
}
