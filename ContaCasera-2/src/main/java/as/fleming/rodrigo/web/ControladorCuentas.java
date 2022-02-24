package as.fleming.rodrigo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import as.fleming.rodrigo.entidades.Cuenta;
import as.fleming.rodrigo.servicios.DAOService;

@Controller
@RequestMapping
public class ControladorCuentas {
	
	@Autowired
	private DAOService servicio;
	
	@GetMapping(path="/cuentas")
	public String listarCuentas(Model modelo) {
		modelo.addAttribute("cuentas", servicio.leerCuentas());
		modelo.addAttribute("cuenta", new Cuenta());
		return "cuentas";
	}
	
	@PostMapping(path="/grabarCuenta")
	public String grabarCuenta(@Valid Cuenta cuenta, Errors errores, Model modelo) {
		if (servicio.existeCuenta(cuenta.getCodigo())) {
			errores.reject("999", "Ya existe una cuenta con el código: " + cuenta.getCodigo() + "." );
		}
		if(errores.hasErrors()) {
			return "altaConceptoCuenta :: cuenta";
		} 
		servicio.grabarCuenta(cuenta);	
		return "redirect:/cuentas";
	}
	
	

}
