import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Entity
public class Cliente extends PanacheEntity {

    // PanacheEntity ya proporciona el campo 'id' de tipo Long
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    public String nombre;
    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres.")
    public String apellido;
    @NotBlank(message = "El Telefono es obligatorio.")
    @Pattern(regexp = "^04(26|16|14|24|12|22)\\d{7}$",
            message = "El teléfono no cumple el formato (0412/0422/0414/0424/0426/0416) o la estructura xxxxxxxxxxx.")
    public String telefono;


    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email debe ser válido.")
    public String email;

    // Constructor por defecto requerido por JPA
    public Cliente() {
    }

    // Constructor para la creación
    public Cliente(String nombre, String apellido, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
}


