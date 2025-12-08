// Archivo: ValidationExceptionMapper.java

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider // Indica a JAX-RS que es un proveedor (componente que usa el framework)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        // 1. Recolectar todos los mensajes de error de las violaciones
        String errores = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")); // Une todos los mensajes con coma y espacio

        // 2. Construir el cuerpo de la respuesta JSON personalizada
        // NOTA: Usamos un String simple para construir el JSON rápidamente aquí,
        // pero en un entorno real podrías usar un objeto Mapa/POJO.
        String jsonError = String.format("{\"estado\": \"ERROR\", \"codigo_http\": 400, \"descripcion\": \"Datos de entrada inválidos\", \"detalles\": \"%s\"}", errores);

        // 3. Devolver la respuesta HTTP 400 (Bad Request)
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(jsonError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}