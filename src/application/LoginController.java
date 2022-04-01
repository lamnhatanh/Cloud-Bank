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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    public TextField usernameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button loginBtn;
    
    @FXML
    private Hyperlink signupLink;


    //create connection, resultset, prepareStatement and set them to null
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    @FXML
    void login(ActionEvent event) {
    	//get username and password from input
    	String userName = usernameTF.getText();
    	String passWord = passwordTF.getText();
    	
    	//check if either of them is empty
    	if (userName.equals("") || passWord.equals("")) {
    		JOptionPane.showMessageDialog(null, "Username or Password cannot be empty");
    	}
    	
    	//check database if username and password are correct
    	else {
    		try {
    			
    			//get connection to database
    			con = myConnection.connectDB();
    			//prepare statement
    			ps = con.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
    			
    			//add String 
    			ps.setString(1, userName);
    			ps.setString(2, passWord);
    			rs = ps.executeQuery();
    			
    			//correct username and password
    			if (rs.next()) {
    				//transfer info to object when it's correct
    				setObject(userName, passWord);

        			//hide the login window and open the transaction window
    		    	loginBtn.getScene().getWindow().hide();
    		    	Parent root = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
    		    	Stage transactionStage = new Stage();
    				Scene scene = new Scene(root);
    				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    				transactionStage.setScene(scene);
    				transactionStage.setTitle("Transaction");
    				transactionStage.show();
    			}
    			//fail to login
    			else {
    				JOptionPane.showMessageDialog(null, "Invalid Username or Password");
    			}
    		}
    		catch (Exception e) {
    			JOptionPane.showMessageDialog(null, e);
    		}
    	}
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
    	//hide the login window and open the signup window
    	signupLink.getScene().getWindow().hide();
    	Parent root = FXMLLoader.load(getClass().getResource("Sign_Up.fxml"));
    	Stage signUpStage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		signUpStage.setScene(scene);
		signUpStage.setTitle("Sign Up");
		signUpStage.show();
    }
    
    //create method to transfer info of user to object
    static public users setObject(String username, String password) {
    	users u = new users(username, password);
    	return u;
    }
}
