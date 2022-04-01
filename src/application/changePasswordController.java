package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class changePasswordController {

    @FXML
    private PasswordField currentPasswordTF;

    @FXML
    private PasswordField newPasswordTF;

    @FXML
    private PasswordField verifyPasswordTF;

    @FXML
    private Button saveChangeBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancel(ActionEvent event) throws IOException {
    	cancelBtn.getScene().getWindow().hide();
    	Parent root = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
		Scene scene = new Scene(root);
		Stage window = new Stage();
		window.setScene(scene);
		window.setTitle("Transaction");
		window.show();
    }

    @FXML
    void saveChange(ActionEvent event) throws SQLException, IOException {
    	//set variables for all user's input
    	String currentPassword = currentPasswordTF.getText();
    	String newPassword = newPasswordTF.getText();
    	String verifyPassword = verifyPasswordTF.getText();
    	
    	// if all or either of text field is empty
    	if (currentPassword.equals("") || newPassword.equals("") || verifyPassword.equals("")) {
    		JOptionPane.showMessageDialog(null,"Please fill all the blank!");
    	}
    	
    	else {
    		// First check current password is correct
    		if (currentPassword.equals(users.getPassword())) {
    			
    			// if so, check new password and verify password have to be the same
    			//let's check if it's not the same
    			if (!newPassword.equals(verifyPassword)) {
    				JOptionPane.showMessageDialog(null,"New password and Verify password don't match!");
    			}
    			
    			// if everything is correct, then update the new password to database
    			else {
    				//create connection to database
    				Connection con = myConnection.connectDB();
    				//update new password to database
    			    PreparedStatement ps = con.prepareStatement("update users set password = ? where username = ?");
    			    ps.setString(1, newPassword);
    	        	ps.setString(2, users.getUsername());
    	        	ps.executeUpdate();
    	        	
    	        	//update new password to object
    	        	users.setPassword(newPassword);
    	        	
    	        	//show successful message
    	        	JOptionPane.showMessageDialog(null, "Your password has changed successfully");
    	        	
    	        	//back to Transaction screen       	
    	        	Parent root = FXMLLoader.load(getClass().getResource("Transaction.fxml"));
    	    		Scene scene = new Scene(root);
    	    		Stage window = new Stage();
    	    		window.setScene(scene);
    	    		window.setTitle("Transaction");
    	    		saveChangeBtn.getScene().getWindow().hide();
    	    		window.show();
    			}
    		}
    		//wrong current password
    		else {
    			JOptionPane.showMessageDialog(null,"Your current password is not correct!");
    		}
    	}
    	
    }

}

/*
 * get the email from user when they created account
 * store it into Object
 * After they deposit or withdraw money, ask users if they want their email receipt
 * Thank you users and ask them if they want to continue. Yes or No
 * Pin change send them email for notification
 * date and time in their email receipt of the transaction
 * --------------------
 * transfer money to another account by typing phone number (Zell).
 * send email notification
 * ----------------------
 * Facebook app
 * send message to another user
 * get message from the one who sent by button Check Message
 * 
 */
 

