package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionController extends LoginController {
	
	@FXML
    private Button withdrawBtn;

    @FXML
    private Button depositBtn;

    @FXML
    private Button BIBtn;

    @FXML
    private Button logoutBtn;
    
    @FXML
    private Button changePasswordBtn;

    @FXML
    private TextField withdrawalTF;

    @FXML
    private TextField depositTF;

    @FXML
    private TextField blTF;
    
    private Float bl;
    
    //create connection to database
	Connection con = myConnection.connectDB();
    PreparedStatement ps = null;
    ResultSet rs = null;

    
    @FXML
    void balanceInquiry(ActionEvent event) throws SQLException {
    	//after getting the balance from database
    	//convert it to String and set it to the balance textfield
    	getBalanceInstant();
    	String checkBL = String.valueOf(bl);
		blTF.setText(checkBL);
    }

    @FXML
    void deposit(ActionEvent event) throws SQLException {
    	//in case the users leave it empty
    	if (depositTF.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "The amount cannot be empty!");
    	}
    	else {
    		//convert to float for calculate
    		float depositAmount = Float.parseFloat(depositTF.getText());
    		
    		//negative or 0 amount
	    	if (depositAmount <= 0) {
	    		JOptionPane.showMessageDialog(null, "Invalid amount of money");
	    		//clear the text after that
	    		depositTF.setText("");
	    	}
	    	
	    	//this is the valid case
	    	else {
	    		//total balance left after withdrawing
	    		float totalBL = getBalanceInstant() + depositAmount;
	    		String sql = "update users set balance = ? where username = ?";
	        	ps = con.prepareStatement(sql);
	        	ps.setFloat(1, totalBL);
	        	ps.setString(2, users.getUsername());
	        	ps.executeUpdate();
	        	JOptionPane.showMessageDialog(null, "Deposit successfully!");
	        	//clear the text after that
	    		depositTF.setText("");
	    	}
    	}
    	
    }

    @FXML
    void withdrawal(ActionEvent event) throws SQLException {
    	//in case the users leave it empty
    	if (withdrawalTF.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "The amount cannot be empty!");
    	}
    	else {
	    	//convert to float for calculate
	    	float withdrawalAmount = Float.parseFloat(withdrawalTF.getText());
	    	
	    	//negative or 0 amount
	    	if (withdrawalAmount <= 0) {
	    		JOptionPane.showMessageDialog(null, "Invalid amount of money");
	    		//clear the text after that
	    		withdrawalTF.setText("");
	    	}
	    	//withdraw over the amount of balance
	    	else if (withdrawalAmount > getBalanceInstant()) {
	    		JOptionPane.showMessageDialog(null, "It is over the amount of balance!");
	    		//clear the text after that
	    		withdrawalTF.setText("");
	    	}
	    	//this is the valid case
	    	else {
	    		//total balance left after withdrawing
	    		float totalBL = getBalanceInstant() - withdrawalAmount;
	    		String sql = "update users set balance = ? where username = ?";
	        	ps = con.prepareStatement(sql);
	        	ps.setFloat(1, totalBL);
	        	ps.setString(2, users.getUsername());
	        	ps.executeUpdate();
	        	JOptionPane.showMessageDialog(null, "Withdraw successfully!");
	        	//clear the text after that
	    		withdrawalTF.setText("");
	    	}
    	
    	}
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	logoutBtn.getScene().getWindow().hide();
    	Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage window = new Stage();
		window.setScene(scene);
		window.setTitle("Cloud Bank");
		window.show();
    }
    
    @FXML
    void changePW(ActionEvent event) throws IOException {
    	changePasswordBtn.getScene().getWindow().hide();
    	Parent root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
		Scene scene = new Scene(root);
		Stage window = new Stage();
		window.setScene(scene);
		window.setTitle("Change Password");
		window.show();
    }
    
    
    //create getBalanceInstant method to get the balance from database instantly
    //balance will base on the username that we've loged in
    public Float getBalanceInstant() throws SQLException {
    	ps = con.prepareStatement("select * from users where username =?");
		ps.setString(1, users.getUsername());
		rs = ps.executeQuery();
		if (rs.next()) {
			bl = rs.getFloat("balance");
		}
		return bl;
    }
    
}
