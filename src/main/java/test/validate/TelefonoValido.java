package test.validate;// Archivo: test.validate.TelefonoValido.java
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.PARAMETER}) // Añadí PARAMETER para usarla en métodos REST
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefonoValidator.class)
public @interface TelefonoValido {

    // 1. Mensaje por defecto (usa la clave de configuración)
    String message() default "{app.validacion.telefono-mensaje}";

    // 2. Grupos de validación
    Class<?>[] groups() default {};

    // 3. Metadata del payload
    Class<? extends Payload>[] payload() default {};

    // NOTA: Si necesitas validar colecciones, agrega la anotación @Repeatable
}