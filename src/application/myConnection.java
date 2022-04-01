package application;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class myConnection {
	
	public static Connection connectDB() {
		Connection connect = null;
		try {
			//loading the driver and create a connection
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/bankusers","root", "");
			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return connect;
	}
}
