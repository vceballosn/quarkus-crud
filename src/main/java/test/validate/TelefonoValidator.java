package test.validate;// Archivo: test.validate.TelefonoValidator.java
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class TelefonoValidator implements ConstraintValidator<TelefonoValido, String> {

    // 1. Inyectar la Expresión Regular del YAML
    @Inject
    @ConfigProperty(name = "app.validacion.telefono-pattern")
    String telefonoPattern; // <-- El patrón configurable




    // Objeto Pattern compilado para eficiencia
    private java.util.regex.Pattern pattern;

    @Override
    public void initialize(TelefonoValido constraintAnnotation) {
        // Compilar el patrón INYECTADO desde el YAML una sola vez.
        this.pattern = java.util.regex.Pattern.compile(telefonoPattern);


    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            // Permitir que @NotBlank maneje si el campo es obligatorio.
            return true;
        }

        // Aplicar el patrón inyectado
        return pattern.matcher(value).matches();
    }
}