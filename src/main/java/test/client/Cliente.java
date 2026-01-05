package test.client; // Asegúrate de que el package sea el correcto

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ClienteEntity")
@Table(name = "cliente") // Recomendado para definir el nombre de la tabla en Postgres
@Data
@Builder
@NoArgsConstructor  // Requerido por JPA/Hibernate
@AllArgsConstructor // Requerido por @Builder
public class Cliente extends PanacheEntity {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    public String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres.")
    public String apellido;

    @NotBlank(message = "El Telefono es obligatorio.")
    @Pattern(regexp = "^04(26|16|14|24|12|22)\\d{7}$",
            message = "El teléfono debe empezar por (0412/0422/0414/0424/0426/0416) seguido de 7 dígitos.")
    public String telefono;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email debe ser válido.")
    public String email;
}