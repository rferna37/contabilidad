package as.fleming.rodrigo.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import as.fleming.rodrigo.entidades.Apunte;
import as.fleming.rodrigo.entidades.Concepto;
import as.fleming.rodrigo.entidades.Cuenta;
import as.fleming.rodrigo.excepciones.GrabarFicheroException;
import as.fleming.rodrigo.auxform.Filtro;
import as.fleming.rodrigo.auxform.Asiento;
import as.fleming.rodrigo.servicios.DAOService;

@Controller
@RequestMapping
public class Controlador {

	@Autowired
	private DAOService servicio;
	
	@GetMapping(path="/apuntes")
	public String leerGrupos(Model modelo, Filtro f) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate hoy = LocalDate.now();
		f.setDesde(dtf.format(hoy.minusMonths(3)));
		f.setHasta(dtf.format(hoy));
		List<Apunte> apuntes = servicio.leerApuntesPorFecha(f.fechaDesde(), f.fechaHasta());
		if (apuntes != null) {
		   modelo.addAttribute("apuntes", apuntes);
		}
		modelo.addAttribute("conceptos", servicio.leerConceptos());
		modelo.addAttribute("cuentas", servicio.leerCuentas());
		modelo.addAttribute("filtro", f);
		return "apuntes";
	}
	 
	@PostMapping(path="/filtrar")
	public String leerApuntes(@Valid Filtro filtro, Errors errores, Model modelo) {
		String origen = filtro.getOrigen().equals("") ? "%" : filtro.getOrigen();
		String destino = filtro.getDestino().equals("") ? "%" : filtro.getDestino();
		String concepto = filtro.getConcepto().equals("") ? "%" : filtro.getConcepto();
		
		if(!errores.hasErrors()) {
			List<Apunte> apuntes = servicio.leerApuntesFiltro(origen, destino, concepto, filtro.fechaDesde() , filtro.fechaHasta());
			modelo.addAttribute("apuntes", apuntes);
			modelo.addAttribute("conceptos", servicio.leerConceptos());
			modelo.addAttribute("cuentas", servicio.leerCuentas());
			modelo.addAttribute("asiento", new Asiento());
		}
		return "apuntes";
	}
	
	@GetMapping(path="/altas")
	public String procesarAltas(Model modelo) {
		modelo.addAttribute("conceptos", servicio.leerConceptos()); 
		modelo.addAttribute("cuentas", servicio.leerCuentas());
		modelo.addAttribute("asiento", new Asiento());
		return "editarApunte";
	}
	
	@GetMapping(path="/editar")
	public String leerApunte(@RequestParam String codApunte, Model modelo) {
		Apunte apunte = servicio.leerApunte(codApunte); 
		Asiento ast = apunte.toAsiento(new Asiento());
		modelo.addAttribute("asiento", ast);
		modelo.addAttribute("conceptos", servicio.leerConceptos());
		modelo.addAttribute("cuentas", servicio.leerCuentas());
		return "editarApunte";
	}
	
	@PostMapping(path="/grabar")
	public String grabarApunte(@Valid Asiento asiento, Errors errores, Model modelo) {
		String salida ="altas";
		Apunte apunte;
		if(errores.hasErrors()) {
			modelo.addAttribute("conceptos", servicio.leerConceptos());
			modelo.addAttribute("cuentas", servicio.leerCuentas());
			salida = "editarApunte";
		} else {
			if(asiento.getCodigo().equals("")) { //Apunte nuevo
				apunte = new Apunte();
				salida = "redirect:/altas";
			} else {                             // Apunte modificado
				apunte = servicio.leerApunte(asiento.getCodigo());
				salida = "redirect:/apuntes";
			}
			try {
				servicio.grabar(asiento.toApunte(apunte));
			} catch (GrabarFicheroException e) {
				errores.reject("998", e.getMensaje());
				modelo.addAttribute("conceptos", servicio.leerConceptos());
				modelo.addAttribute("cuentas", servicio.leerCuentas());
				salida = "editarApunte";
			}
		}
		return salida;
	}
	
	@GetMapping("/fragmento/{nombre}")
	public String nuevoConcepto(@PathVariable("nombre") String nomFragmento, Model modelo) {
		if (nomFragmento.equals("concepto")) {
			modelo.addAttribute("concepto", new Concepto());
		} else {
			modelo.addAttribute("cuenta", new Cuenta());
		}
		return "altaConceptoCuenta :: " + nomFragmento;
	}
 
	@RequestMapping("/verDocu/{fichero}")
	public String verDocumento(@PathVariable("fichero") String nombre, Model modelo) {
		modelo.addAttribute("fichero", nombre);
		return "mostrarDocumento";
	}
	
}
