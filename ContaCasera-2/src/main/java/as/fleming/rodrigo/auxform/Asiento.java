package as.fleming.rodrigo.auxform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import as.fleming.rodrigo.entidades.Apunte;
import as.fleming.rodrigo.validadores.CuentasConstraint;
import as.fleming.rodrigo.validadores.FechaConstraint;
import lombok.Data;

@Data
@CuentasConstraint
public class Asiento {
	String codigo;
	@FechaConstraint(message="Fecha inválida")
	private String fecha;
	@NotBlank(message="Obligatorio seleccionar concepto")
	private String concepto;
	@NotBlank(message="Obligatorio seleccionar cuenta")
	String origen;
	@NotBlank(message="Obligatorio seleccionar cuenta")
	String destino;
	
	@NotNull(message="Campo obligatorio")
	@Pattern(regexp="^([-+]?[0-9]{1,3}(.[0-9]{3})?(,[0-9]+)?)$",
			message="Formato incorrecto")
	private String importe;
	
	private MultipartFile documento;
	
	public Apunte toApunte(Apunte apunte) {
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
		apunte.setCodigo(codigo);
		apunte.setFecha(LocalDate.parse(fecha, formatoFecha));
		apunte.setCodConcepto(concepto);
		apunte.setCodOrigen(origen);
		apunte.setCodDestino(destino);
		apunte.setImporte(new BigDecimal(importe.replace(".","").replace(',','.')));
		if (documento != null && !documento.isEmpty()) {
			apunte.setDocumento(documento);
			apunte.setExtension(getExtension());
		}
		return apunte;
	}
	
	public String getExtension() {
		if (documento != null) {
			String nombre = documento.getOriginalFilename();
			return nombre.substring(nombre.lastIndexOf('.') + 1);
		}
		return "";
	}
}
