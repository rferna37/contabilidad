package as.fleming.rodrigo.validadores;

import java.lang.annotation.Documented;

/* Con esta interface se crea la anotación @CuentasConstraint correspondiente
 * al validador definido en la clase CuentasValidador.
 * Es una anotación de clase.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CuentasValidador.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CuentasConstraint {
	String message() default "Cuentas origen y destino iguales";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
