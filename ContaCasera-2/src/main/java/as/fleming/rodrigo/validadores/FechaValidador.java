package as.fleming.rodrigo.validadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FechaValidador implements ConstraintValidator<FechaConstraint, String> {

	@Override
	public boolean isValid(String fecha, ConstraintValidatorContext context) {
		try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
