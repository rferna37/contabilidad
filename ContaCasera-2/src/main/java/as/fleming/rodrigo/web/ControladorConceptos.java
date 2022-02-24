package as.fleming.rodrigo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.servicios.DAOService;

@Controller
@RequestMapping
public class ControladorConceptos {
	
	@Autowired
	private DAOService servicio;
	
	@GetMapping(path="/conceptos")
	public String listarConceptos(Model modelo) {
		modelo.addAttribute("conceptos", servicio.leerConceptos());
		modelo.addAttribute("concepto", new Concepto());
		return "conceptos";
	}
	
	@PostMapping(path="/grabarConcepto")
	public String grabarConcepto(@Valid Concepto concepto, Errors errores, Model modelo) {
		if (servicio.existeConcepto(concepto.getCodigo())) {
			errores.reject("999", "Ya existe un concepto con el código: " + concepto.getCodigo() + "." );
		}
		if(errores.hasErrors()) {
			return "altaConceptoCuenta :: concepto";
		} 
	//	if (servicio.existeConcepto(concepto.getCodigo())) {
	//		throw new ClaveDuplicadaException("Ya existe un concepto con el código: " + concepto.getCodigo() + "." );
	//	}
		servicio.grabarConcepto(concepto);	
		return "redirect:/conceptos";
	}
	
	
}
