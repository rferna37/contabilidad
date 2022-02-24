package as.fleming.rodrigo.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import as.fleming.rodrigo.auxform.Asiento;

/*
 * Esta clase va ligada a la clase Asiento mediante la anotación @CuentasConstraint.
 * Valida que en un asiento las cuentas oriegen y destino sean distintas.
 */

public class CuentasValidador implements ConstraintValidator<CuentasConstraint, Asiento> {

	@Override
	public boolean isValid(Asiento a, ConstraintValidatorContext context) {
		if (a.getOrigen().equals("") || a.getDestino().equals("") ||
				!a.getOrigen().equals(a.getDestino())) {
			return true;
		} else {
		return false;
		}
	}

}
