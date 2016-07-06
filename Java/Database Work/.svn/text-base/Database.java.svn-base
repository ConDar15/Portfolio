package database;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.TableModel;

/**
 * This class encapsulates the connection to the database 'test' and provides various
 * synchronized methods for the access to the database itself.
 * 
 * @version 18/03/2014
 * @author Jake Darby
 */
public class Database
{
	Connection dbConnection;
	MessageDigest encr;
	
	/**
	 * Creates the connection to the database so that the instance methods can be called to access the database
	 */
	public Database()
	{
		try
		{
			encr = MessageDigest.getInstance("MD5");
			Class.forName("org.postgresql.Driver");
			dbConnection = DriverManager.getConnection("jdbc:postgresql://dbteach2.cs.bham.ac.uk:5432/monaco_db", "jed199", "t1o2b3y4");
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("Could not initiate md5 encryption");
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Driver not found");
		}
		catch (SQLException e)
		{
			System.err.println("Error in connection to database");
		}
	}
	
	/**
	 * Searches the database for any rows whose name column starts with the parameter name
	 * 
	 * @param name The name prefix to search for
	 * @return A TableModel which encapsulates the results of the query
	 * @see TableModel
	 */
	public synchronized TableModel searchByName(String name)
	{
		try
		{
			return new SearchResult(dbConnection.createStatement().executeQuery
				("SELECT * FROM products WHERE name ILIKE '%" + name + "%'"));
		}
		catch (SQLException e)
		{
			System.err.println("Error in searching for product " + name);
			return new SearchResult();
		}
	}
	
	/**
	 * Searches the database for any rows whose name id column is the id parameter
	 * 
	 * @param id The id number to search for
	 * @return A TableModel which encapsulates the results of the query
	 * @see TableModel
	 */
	public synchronized TableModel searchByID(String id)
	{
		try
		{
			return new SearchResult(dbConnection.createStatement().executeQuery
					("SELECT * FROM products WHERE id = " + id));
		}
		catch (SQLException e)
		{
			System.err.println("Error in searching for product #" + id);
			return new SearchResult();
		}
	}
	
	/**
	 * @param pass The String representing the password to be hashed
	 * @return The md5 hash of the given password
	 */
	private synchronized String hash(String pass)
	{	try
		{
			return String.format("%32s", new BigInteger(1, encr.digest(pass.getBytes("UTF-8"))).toString(16)).replace(" ", "0");
		}
		catch (UnsupportedEncodingException e)
		{
			return "Nope";
		}
	}
	
	/**
	 * Queries the database to determine whether the given username and password are a valid login combination
	 * 
	 * @param uname The username to search for
	 * @param pass The provided password to compare against the stored password
	 * @return <code>true</code> if the username can be found and the passwords match, <code>false</code> in all other cases
	 */
	public synchronized boolean login(String uname, String pass)
	{
		try
		{
			ResultSet loginDetails = dbConnection.createStatement().executeQuery(
					"SELECT pass FROM login WHERE usr = '" + uname + "'");
			if (loginDetails.next())
			{
				if (loginDetails.getString("pass").equals(hash(pass)))
					return true;
				else
					return false;
			}
			else
				return false;
		}
		catch (SQLException e)
		{
			System.err.println("Error in accessing the login details");
			return false;
		}
	}
	
	/**
	 * Allows the user to purchase a given quantity of one product from the database
	 * 
	 * @param id The unique id number of the product to purchase
	 * @param quantity The quantity of the product to purchase
	 * @return <code>quantity</code> if the purchase was successful and negative the quantity left in the database if unsuccessful
	 */
	public synchronized int purchase(int id, int quantity)
	{
		try
		{
			ResultSet rs = dbConnection.createStatement().executeQuery("SELECT quantity FROM products WHERE id = " + id);
			if(rs.next())
				if(rs.getInt("quantity") >= quantity)
				{
					dbConnection.createStatement().executeUpdate("UPDATE products SET quantity = quantity - " + quantity + "WHERE id = " + id);
					return quantity;
				}
				else
					return -rs.getInt("quantity");
			else
				return 0;
		}
		catch (SQLException e)
		{
			System.err.println("Error in purchasing " + quantity + " of product #" + id);
			return 0;
		}
	}
	
	/**
	 * Updates a specific row of the database with new stock as well as order and expiry dates
	 * 
	 * @param id The product to update
	 * @param newQuantity The quantity now in stock
	 * @param newOrderDate The new next order date must be in the format "yyyy-mm-dd"
	 * @param newExpiryDate The new expiry date must be in the format "yyyy-mm-dd"
	 * @return <code>true</code> if the operation was successful and <code>false</code> otherwise 
	 */
	public synchronized boolean restock(int id, int newQuantity, String newOrderDate, String newExpiryDate)
	{
		try
		{
			dbConnection.createStatement().executeUpdate(
					String.format("UPDATE products SET quantity = %d, order_date = '%s',"+
							"expiry_date = '%s' WHERE id = %d", newQuantity, newOrderDate, newExpiryDate, id));
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error in restocking product #" + id);
			return false;
		}
	}
	
	/**
	 * Adds a new product to the database
	 * 
	 * @param newProduct A DataRow object that represents the new product to be added
	 * @return <code>true</code> if the update was successful and <code>false</code> otherwise
	 * @see DataRow
	 */
	public synchronized boolean addProduct(DataRow newProduct)
	{
		try
		{
			dbConnection.createStatement().executeUpdate(
					String.format("INSERT INTO products " +
					"(name, price, pack_quantity, quantity, vat, order_date, expiry_date)" +
					"VALUES ('%s', %d, %d, %d, %s, %s, %s)",
					newProduct.getName(), newProduct.getPriceWithoutVAT(), newProduct.getPackQuantity(),
					newProduct.getQuantity(), newProduct.getVAT() ? "TRUE" : "FALSE", 
					newProduct.getOrderDate() == null ? "NULL" : "'" + newProduct.getOrderDate() + "'",
					newProduct.getExpiryDate() == null ? "NULL" : "'" + newProduct.getExpiryDate() + "'"));
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error adding new product " + newProduct.getName());
			return false;
		}
	}
	
	/**
	 * Removes the indicated product from the table 'products'
	 * 
	 * @param id The unique id of the row to be removed
	 * @return <code>true</code> if successful and <code>false</code> otherwise
	 */
	public synchronized boolean removeProduct(int id)
	{
		try
		{
			dbConnection.createStatement().executeUpdate(
					"DELETE FROM products WHERE id = " + id);
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error removing product #" + id);
			return false;
		}
	}
	
	/**
	 * Adds a new user to the database of valid users
	 * 
	 * @param uname Username to store in the database
	 * @param pass Password to be encrypted and stored in the database
	 * @return <code>true</code> if successful and <code>false</code> otherwise
	 */
	public synchronized boolean register(String uname, String pass)
	{
		try
		{
			dbConnection.createStatement().executeUpdate(
					"INSERT INTO login VALUES ('" + uname + "', '" + hash(pass) + "')");
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("Error adding new user '" + uname + "'");
			return false;
		}
	}
}
