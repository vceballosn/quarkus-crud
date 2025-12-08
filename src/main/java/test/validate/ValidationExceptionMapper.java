package test.validate;// Archivo: test.validate.ValidationExceptionMapper.java

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.core.MediaType;
import test.model.ErrorResponseDTO;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    // 1. INYECTAR EL VALOR REAL DEL MENSAJE DESDE EL YML
    // Esto es CRUCIAL para que el mensaje aparezca.
    @Inject
    @ConfigProperty(name = "app.validacion.telefono-mensaje")
    String mensajeTelefonoInvalido;

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        String descripcionGenerica = "Datos de entrada inválidos";

        // Iterar sobre las violaciones para verificar si alguna es el error de teléfono
        boolean isTelefonoError = exception.getConstraintViolations().stream()
                // Buscamos si el template (la clave) coincide con la que definimos en @test.validate.TelefonoValido
                .anyMatch(cv -> cv.getMessageTemplate().equals("{app.validacion.telefono-mensaje}"));

        // 2. CONSTRUIR EL DTO DE ERROR USANDO LA VARIABLE INYECTADA
        String mensajeDetalles;
        if (isTelefonoError) {
            // Si el error es específicamente el de teléfono, usamos el valor inyectado del YML
            mensajeDetalles = mensajeTelefonoInvalido;
        } else {
            // Si es otro error de validación (@NotBlank, @Size), recolectamos todos los mensajes
            mensajeDetalles = exception.getConstraintViolations().stream()
                    // Usar getMessage() aquí funciona si el framework resolvió otros mensajes estándar
                    .map(cv -> cv.getMessage())
                    .collect(java.util.stream.Collectors.joining("; "));
        }

        // Asumiendo que test.model.ErrorResponseDTO tiene un constructor (String estado, int codigoHttp, String descripcion, String detalles)
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR",
                statusCode,
                descripcionGenerica,
                mensajeDetalles // Aquí va el valor real del YML (si isTelefonoError es true)
        );

        // 3. Devolver la respuesta HTTP 400
        return Response.status(statusCode)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON) // Aseguramos que la respuesta sea JSON
                .build();
    }
}