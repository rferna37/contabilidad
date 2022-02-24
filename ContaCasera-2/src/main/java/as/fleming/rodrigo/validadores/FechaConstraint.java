package as.fleming.rodrigo.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/* Con esta interface se crea la anotación @FechaConstraint correspondiente
 * al validador definido en la clase FechaValidador.
 */

@Documented
@Constraint(validatedBy = FechaValidador.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaConstraint {
    String message() default "Fecha no válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}