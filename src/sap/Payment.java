package sap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Payment {
	
	public Connection connect() {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties connectionProps = new Properties();
			connectionProps.put("user", "root");
			connectionProps.put("password", "");
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/payment?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Colombo",
					connectionProps);
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readpayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>payment ID</th><th>payment Date</th><th>paymentMethod</th>"
					+ "<th>paymentDueDate</th><th>apt_ID</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) 
			{
				String paymentID = rs.getString("paymentID");
				String paymentDate = rs.getString("paymentDate");
				String paymentMethod = rs.getString("paymentMethod");
				String paymentDueDate = rs.getString("paymentDueDate");
				String paymentId = rs.getString("apt_ID");
				
// Add into the html table
				
				output += "<tr><td>" + paymentID + "</td>";
				output += "<td>" + paymentDate + "</td>";

				output += "<td>" + paymentId + "</td>";
				output += "<td>" + paymentMethod + "</td>";
				output += "<td>" + paymentDueDate + "</td>";
// buttons
				output += "<td><input name=\"btnUpdate\" "
						+ " type=\"button\" class=\"btn btn-primary\" value=\"Update\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">" + "<input name=\"btnRemove\" "
						+ " type=\"submit\" class=\"btn btn-danger\" value=\"Remove\">"
						+ "<input name=\"itemID\" type=\"hidden\" " + " value=\"" + paymentID + "\">"
						+ "</form></td></tr>";
			}
			con.close();
			
// Complete the html table
			output += "</table>";
			
			}
				catch (Exception e) 
		{
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertpayment(String paymentID, String paymentDate, String paymentMethod, String paymentDueDate, String apt_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into payment(paymentID,paymentDate,paymentMethod,paymentDueDate,apt_ID)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setString(1, paymentID);
			preparedStmt.setString(2, paymentDate);
			preparedStmt.setString(3, paymentMethod);
			preparedStmt.setString(4, paymentDueDate);
			preparedStmt.setString(5, apt_ID);
			
// execute the statement
			preparedStmt.executeUpdate();
			
			String newpayment = readpayment();
			output = "{\"status\":\"success\", \"data\": \"" + newpayment + "\"}";
			
		} catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatepaymant(String ID, String date,String method, String dueDate,String apt_ID)  {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE payment SET paymentID=?, paymentdate=?,paymentmethod=?,paymentdueDate=?,paymentapt_ID=? WHERE paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setString(1, ID);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, method);
			preparedStmt.setString(4, dueDate);
			preparedStmt.setString(5, apt_ID);
			
// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newpayment = readpayment();
			output = "{\"status\":\"success\", \"data\": \"" + newpayment + "\"}";
			
		} catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletepayment(String paymentID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

// create a prepared statement
			String query = "delete from payment where paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentID));
			
// execute the statement
			preparedStmt.execute();
			con.close();
			String newpayment = readpayment();
			
			output = "{\"status\":\"success\", \"data\": \"" + newpayment + "\"}";
			}
				catch (Exception e)
		{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public static void main(String[] args) {
		new Payment().insertpayment("sdf", "safsaf", "123.23", "sdfadsfsadf","11");
		//new Payment().deletePayment("1");
	}
}