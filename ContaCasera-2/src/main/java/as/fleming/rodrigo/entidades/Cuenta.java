package as.fleming.rodrigo.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="cuentas")
@Data
public class Cuenta {
	@Id
	@Pattern(regexp = "^[0-9]{3}$", message="Obligatorio código de 3 dígitos")
	@Size(max = 3)
	private String codigo;
	@NotBlank(message="Obligatorio teclear nombre")
	@Size(max = 50, message="Máximo 50 caracteres")
	private String nombre;
	
}
