package application;

public class users {
	private static String username;
	private static String password;
	
	public users(String username, String password) {
		users.username = username;
		users.password = password;
	}
	
	public static void setUsername(String UN) {
		username = UN;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static void setPassword(String pw) {
		password = pw;
	}
	
	public static String getPassword() {
		return password;
	}
	
}
