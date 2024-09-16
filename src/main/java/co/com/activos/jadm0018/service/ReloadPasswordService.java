package co.com.activos.jadm0018.service;
import co.com.activos.jadm0018.model.Usuario;
import co.com.activos.jadm0018.model.DominioSAAS;
import co.com.activos.jadm0018.controller.Ldap;
import co.com.activos.jadm0018.controller.View;
import java.util.List;

/**
 *
 * @author kpaz
 */
public class ReloadPasswordService {


    // Guardar la solicitud de restablecimiento de contraseña
    public String createPassword(String userName) {
        return Ldap.saveRequest(userName);
    }

    // Validar si la solicitud de restablecimiento es válida
    public String validatePassword(String requestId) {
        return Ldap.reloadPass(requestId);
    }

    // Actualizar la contraseña del usuario
    public boolean updatePassword(String userName, String newPassword, String domain) {
        List<DominioSAAS> dominios = View.loadDominios(Long.parseLong("1"));
        DominioSAAS selectedDomain = null;

        for (DominioSAAS x : dominios) {
            if (domain.equals(x.getDsa_ramaldap())) {
                selectedDomain = x;
                break;
            }
        }

        if (selectedDomain != null) {
            return Ldap.updateState(userName).isEmpty();
        }

        return false;
    }

    // Método para enviar notificación por correo
    public void sendPasswordResetEmail(String userEmail, String userName, String requestUrl) {
        String template = "<html> ... </html>"; // Construcción de plantilla de email
        Ldap.sendMail("notificacion@activos.com.co", userEmail, "Restablecer Clave", template);
    }

}
