package co.com.activos.jadm0018.ws;
import co.com.activos.jadm0018.service.ReloadPasswordService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author kpaz
 */

@Path("/restablecer-contraseña")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReloadPasswordRS {
    
    private final ReloadPasswordService reloadPasswordService = new ReloadPasswordService();

    // Endpoint para crear una solicitud de restablecimiento de contraseña
    @POST
    @Path("/request")
    public Response createResetRequest(@QueryParam("username") String username) {
        String requestId = reloadPasswordService.createPassword(username);
        if (requestId.equals("-1")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Su solicitud ya ha sido enviada").build();
        }
        return Response.ok(requestId).build();
    }

    // Endpoint para validar una solicitud de restablecimiento de contraseña
    @GET
    @Path("/validate/{requestId}")
    public Response validateRequest(@PathParam("requestId") String requestId) {
        String validation = reloadPasswordService.validatePassword(requestId);
        if (validation.equals("FALSE")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Su solicitud es inválida o ha expirado").build();
        }
        return Response.ok(validation).build();
    }

    // Endpoint para actualizar la contraseña
    @POST
    @Path("/update")
    public Response updatePassword(@QueryParam("username") String username,
                                   @QueryParam("newPassword") String newPassword,
                                   @QueryParam("domain") String domain) {
        boolean success = reloadPasswordService.updatePassword(username, newPassword, domain);
        if (success) {
            return Response.ok("Contraseña actualizada").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al actualizar la contraseña").build();
        }
    }
    
}
