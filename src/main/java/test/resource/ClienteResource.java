package test.resource;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import test.model.Cliente;

import java.net.URI;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Operaciones CRUD de clientes")
public class ClienteResource {

    // --- C: CREATE (Crear un cliente) ---
    @POST
    @Transactional
    @Valid
    @Path("/create")
    @Operation(summary = "Crear un nuevo cliente", description = "Guarda un cliente en la base de datos PostgreSQL")
    public Response crearCliente(Cliente cliente) {
        System.out.println(cliente.nombre);
        cliente.persist(); // Guardar el objeto en la base de datos
        return Response.created(URI.create("/clientes/" + cliente.id)).build();
    }

    // --- R: READ (Obtener todos los clientes) ---
    @GET
    @Path("all")
    @Operation(summary = "Consulta de  clientes", description = " Consulta todos los clientes  en la base de datos PostgreSQL")
    public List<Cliente> obtenerTodos() {
        return Cliente.listAll(); // Panache: Obtener todos
    }

    // --- R: READ (Obtener un cliente por ID) ---
    @GET
    @Path("/get/{id}")
    @Operation(summary = "Consulta de Cliente por ID", description = " Consulta los clientes por id  en la base de datos PostgreSQL")
    public Cliente obtenerPorId(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id); // Panache: Buscar por ID
        if (cliente == null) {
            throw new NotFoundException("Cliente con ID " + id + " no encontrado.");
        }
        return cliente;
    }

    // --- U: UPDATE (Actualizar un cliente existente) ---
    @PUT
    @Path("/put/{id}")
    @Transactional
    @Valid
    @Operation(summary = "Actualiza  Cliente por ID", description = " Actualiza  cliente por id  en la base de datos PostgreSQL")
    public Cliente actualizarCliente(@PathParam("id") Long id, Cliente clienteActualizado) {
        Cliente cliente = Cliente.findById(id);

        if (cliente == null) {
            throw new NotFoundException("Cliente con ID " + id + " no encontrado.");
        }

        // Actualizar los campos
        cliente.nombre = clienteActualizado.nombre;
        cliente.apellido = clienteActualizado.apellido;
        cliente.telefono = clienteActualizado.telefono;
        cliente.email = clienteActualizado.email;

        // No es necesario llamar a persist() o update() en Panache en un método @Transactional
        // La actualización ocurre automáticamente al final del método (sesión activa).
        return cliente;
    }

    // --- D: DELETE (Eliminar un cliente) ---
    @DELETE
    @Path("/delete/{id}")
    @Transactional
    @Valid
    @Operation(summary = "Elimina Cliente por ID", description = " Elimina cliente por id  en la base de datos PostgreSQL")
    public Response eliminarCliente(@PathParam("id") Long id) {
        boolean eliminado = Cliente.deleteById(id); // Panache: Eliminar por ID
        if (eliminado) {
            return Response.noContent().build(); // 204 No Content para éxito
        } else {
            throw new NotFoundException("Cliente con ID " + id + " no encontrado.");
        }
    }
}