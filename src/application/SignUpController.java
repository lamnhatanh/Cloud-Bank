package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField firstnameText;

    @FXML
    private TextField lastnameText;

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passwordText;
	
    @FXML
    private PasswordField confirmText;
    
    @FXML
    private Button CABtn;

    @FXML
    private Button cancelBtn;

    
    @FXML
    void cancel(ActionEvent event) throws IOException {
    	cancelBtn.getScene().getWindow().hide();
    	Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage window = new Stage();
		window.setScene(scene);
		window.setTitle("Cloud Bank");
		window.show();
    }
    
    @FXML
    void createAccount(ActionEvent event) {
    	//get all inputs from user
    	String firstname = firstnameText.getText();
    	String lastname = lastnameText.getText();
    	String username = usernameText.getText();
    	String password = passwordText.getText();
    	String confirm = confirmText.getText();
    	  	
    	//create connection to database
    	Connection con = myConnection.connectDB();
    	
    	//either of them cannot be empty (if)
    	if (firstname.equals("") || lastname.equals("") || username.equals("") || password.equals("")
    			|| confirm.equals("")) {
    		JOptionPane.showMessageDialog(null, "Please fill all the blank!");
    	}
    	
    	//someone's already created this username (else if)
    	else if (checkUsername(username)) {
    		JOptionPane.showMessageDialog(null, "This username has already existed!");
    	}
    	
    	//password and confirm have to be the same (else if)
    	else if (!password.equals(confirm)) {
    		JOptionPane.showMessageDialog(null, "Password and Confirm Password don't match!");
    	}
    	
    	//Account creates successfully (else)
    	else {
    		String sql = "insert into users (firstname,lastname,username,password) values (?,?,?,?)";
    		try {
    			//put all inputs to database
    			PreparedStatement ps = con.prepareStatement(sql);
    			ps.setString(1, firstname);
    			ps.setString(2, lastname);
    			ps.setString(3, username);
    			ps.setString(4, password);
    			ps.execute();
    			//let users know they successfully create the account
    			JOptionPane.showMessageDialog(null, "Your Account Has Been Created");
    			
    			//automatically back to login window    			
    	    	Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
    			Scene scene = new Scene(root);
    			Stage window = new Stage();
    			window.setScene(scene);
    			window.setTitle("Cloud Bank");
    			CABtn.getScene().getWindow().hide();
    			window.show();
    			
    		} catch (Exception e) {
    			JOptionPane.showMessageDialog(null, e);
    		}
    	}
    }
    
    public static boolean checkUsername(String username) {
    	boolean userExist = false;
    	//create connection to database
    	Connection con = myConnection.connectDB();
    	
    	try {
    		PreparedStatement ps = con.prepareStatement("select * from users where username = ?");
    		ps.setString(1, username);
    		ResultSet rs = ps.executeQuery();
    		//in case the user exist
    		if (rs.next()) {
    			userExist = true;
    		}
    		//otherwise, this user is new
    		else {
    			userExist = false;
    		}
    	} catch (Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
    	return userExist;
    }
}