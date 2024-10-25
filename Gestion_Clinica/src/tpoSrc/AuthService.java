package tpoSrc;

import tpoEnums.UserType;

public class AuthService {
    public static boolean login (String username, String password) {
        if (("admin".equals(username) && "admin".equals(password)) || 
            ("user".equals(username) && "user".equals(password))) {
            UserSession.getInstance().setUser(username, password);
            return true;
        } //Si se ingresaron credenciales validas retorna True y setea el user y la password
        return false;
    }
    
    
    
    public static void displayUserInfo() {
        UserSession userSession = UserSession.getInstance();
        System.out.println("Usuario autenticado como: " + userSession.getUsername());
        System.out.println("Tipo de usuario: " + userSession.getUserType());
    }

    public static boolean isUserAdmin() {
        return UserSession.getInstance().getUserType() == UserType.ADMIN;
    }
}
