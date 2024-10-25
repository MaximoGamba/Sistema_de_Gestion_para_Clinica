package tpoSrc;

import tpoEnums.UserType;

public class UserSession {
    private static UserSession instance;
    private String username;
    private UserType userType;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String username, String password) {
        this.username = username;
        if ("admin".equals(username) && "admin".equals(password)) {
            this.userType = UserType.ADMIN;
        } else if ("user".equals(username) && "user".equals(password)) {
            this.userType = UserType.USER;
        }
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
    
    
}