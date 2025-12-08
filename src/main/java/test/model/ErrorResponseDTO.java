package test.model;

/**
 * Data Transfer Object (DTO) para estandarizar las respuestas de error JSON
 * en la API, especialmente para errores de validación HTTP 400.
 */
public class ErrorResponseDTO {
    // Estado general de la respuesta (e.g., "ERROR", "OK")
    public String estado;

    // Código HTTP numérico (e.g., 400, 500)
    public int codigoHttp;

    // Descripción de alto nivel del error
    public String descripcion;

    // Detalles específicos del error, donde se inyecta el mensaje del YML
    public String detalles;

    /**
     * Constructor utilizado por el test.validate.ValidationExceptionMapper.
     */
    public ErrorResponseDTO(String estado, int codigoHttp, String descripcion, String detalles) {
        this.estado = estado;
        this.codigoHttp = codigoHttp;
        this.descripcion = descripcion;
        this.detalles = detalles;
    }

    // Nota: Los getters y setters no son estrictamente necesarios en Quarkus/Jackson
    // siempre y cuando los campos sean públicos o se usen records.
}
