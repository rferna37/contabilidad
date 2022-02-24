package as.fleming.rodrigo.auxform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import as.fleming.rodrigo.validadores.FechaConstraint;
import lombok.Data;

@Data
public class Filtro {
	
	@FechaConstraint(message="Fecha inválida")
	private String desde, hasta;
	private String Concepto, origen, destino;
	
	public LocalDate fechaDesde() {
		return aFecha(desde);
	}
	
	public LocalDate fechaHasta() {
		return aFecha(hasta);
	}
	
	/* Pasa un String fecha en formato DD/MM/YYY a java.sql.Date */
	private LocalDate aFecha(String fecha) {
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
		return LocalDate.parse(fecha, formatoFecha);
	}
}
