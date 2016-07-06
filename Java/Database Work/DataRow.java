package database;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a single row of the table 'products' for ease of use. 
 * <p>
 * Implements Serializable to allow it to be passed over a network by an ObjectStream.
 * 
 * @see Serializable
 * @version 18/03/2014
 * @author Jake Darby
 */
public class DataRow implements Serializable
{
	//ID required for serialisation, completely arbitrary value, definitely no special reason for choosing this exact value
	private static final long serialVersionUID = 5138008L;
	//Variables to encapsulate the data in a row
	private int id, price, packQuantity, quantity;
	private String name, orderDate, expiryDate;
	private boolean vat;
	
	/**
	 * Initialises the DataRow object with the data given
	 * 
	 * @param id The unique id of the product
	 * @param name The name of the product
	 * @param price The price (in pence) of the product
	 * @param packQuantity The quantity of a product in a single pack
	 * @param quantity The quantity of a product currently available
	 * @param vat Whether the product is liable for VAT
	 * @param orderDate The date the product will next be ordered
	 * @param expiryDate The date the product expires
	 */
	protected DataRow(int id, String name, int price, int packQuantity,
			  int quantity, boolean vat, Date orderDate, Date expiryDate)
	{
		this(id, name, price, packQuantity, quantity, vat, orderDate == null ? null : orderDate.toString(), expiryDate == null ? null : expiryDate.toString());
	}
	
	/**
	 * Initialises the DataRow object with the data given
	 * 
	 * @param id The unique id of the product
	 * @param name The name of the product
	 * @param price The price (in pence) of the product
	 * @param packQuantity The quantity of a product in a single pack
	 * @param quantity The quantity of a product currently available
	 * @param vat Whether the product is liable for VAT
	 * @param orderDate The date the product will next be ordered
	 * @param expiryDate The date the product expires
	 */
	protected DataRow(int id, String name, int price, int packQuantity,
					  int quantity, boolean vat, String orderDate, String expiryDate)
	{
		this(name, price, packQuantity, quantity, vat, orderDate, expiryDate);
		this.id = id;
	}
	
	/**
	 * Initialises the DataRow object with the data given
	 * 
	 * @param name The name of the product
	 * @param price The price (in pence) of the product
	 * @param packQuantity The quantity of a product in a single pack
	 * @param quantity The quantity of a product currently available
	 * @param vat Whether the product is liable for VAT
	 * @param orderDate The date the product will next be ordered
	 * @param expiryDate The date the product expires
	 */
	public DataRow(String name, int price, int packQuantity, 
				   int quantity, boolean vat, String orderDate, String expiryDate)
	{
		setName(name);
		setPrice(price);
		setPackQuantity(packQuantity);
		setQuantity(quantity);
		setVAT(vat);
		setOrderDate(orderDate);
		setExpiryDate(expiryDate);
	}
	
	/**
	 * @return The unique id of the product
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Sets the name of the product
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return The name of the product
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the price of the product
	 * @param price The cost of the product in pence (without VAT applied)
	 */
	public void setPrice(int price)
	{
		this.price = price;
	}
	
	/**
	 * @return The price of the product with VAT applied
	 */
	public int getPrice()
	{
		return vat ? price + price/5 : price;
	}
	
	/**
	 * @return The price of the product without VAT applied
	 */
	public int getPriceWithoutVAT()
	{
		return price;
	}
	
	/**
	 * Sets the quantity of products in a given pack
	 * @param packQuantity
	 */
	public void setPackQuantity(int packQuantity)
	{
		this.packQuantity = packQuantity;
	}
	
	/**
	 * @return The quantity of products in a given pack
	 */
	public int getPackQuantity()
	{
		return packQuantity;
	}
	
	/**
	 * Sets the quantity of packs currently in store
	 * @param quantity
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	/**
	 * @return The quantity of packs currently in store
	 */
	public int getQuantity()
	{
		return quantity;
	}
	
	/**
	 * Sets the boolean value of whether VAT is applicable to the product
	 * @param vat
	 */
	public void setVAT(boolean vat)
	{
		this.vat = vat;
	}
	
	/**
	 * @return Whether VAT is applicable to the product
	 */
	public boolean getVAT()
	{
		return vat;
	}
	
	/**
	 * Sets the date the product will next be ordered
	 * @param orderDate Must be formatted as "yyyy-mm-dd"
	 */
	public void setOrderDate(String orderDate)
	{
		try
		{
			this.orderDate = Date.valueOf(orderDate).toString();
		}
		catch(IllegalArgumentException e)
		{
			this.orderDate = null;
		}
	}
	
	/**
	 * @return The date the product will next be ordered in the format "yyyy-mm-dd"
	 */
	public String getOrderDate()
	{
		return orderDate;
	}
	
	/**
	 * Sets the date the product expires
	 * @param expiryDate Must be formatted as "yyyy-mm-dd"
	 */
	public void setExpiryDate(String expiryDate)
	{
		try
		{
			this.expiryDate = Date.valueOf(expiryDate).toString();
		}
		catch(IllegalArgumentException e)
		{
			this.expiryDate = null;
		}
	}
	
	/**
	 * @return The date the product will be next ordered in the format "yyyy-mm-dd"
	 */
	public String getExpiryDate()
	{
		return expiryDate;
	}

	/**
	 * @return An array of objects which represents the data in a given row
	 */
	public Object[] toArray()
	{
		Object[] returnValue = new Object[8];
		returnValue[0] = getID();
		returnValue[1] = getName();
		returnValue[2] = getPrice();
		returnValue[3] = getPackQuantity();
		returnValue[4] = getQuantity();
		returnValue[5] = getVAT();
		returnValue[6] = getOrderDate();
		returnValue[7] = getExpiryDate();
		return returnValue;
	}

	/**
	 * Creates a List of DataRow objects that represent a set of results that have been found by an SQL query.
	 * 
	 * @param rows The result of an SQL query on the 'products' table of the database
	 * @return A representation of the results of type List<DataRow>
	 * 
	 * @see ResultSet
	 * @see List
	 */
	public static List<DataRow> generate(ResultSet rows)
	{
		ArrayList<DataRow> list = new ArrayList<DataRow>();
		try
		{
			while (rows.next())
			{
				list.add(new DataRow(rows.getInt("id"), rows.getString("name"), rows.getInt("price"),
						rows.getInt("pack_quantity"), rows.getInt("quantity"), rows.getBoolean("vat"),
						rows.getDate("order_date"), rows.getDate("expiry_date")));
			}
			return list;
		}
		catch (SQLException e)
		{
			return new ArrayList<DataRow>();
		}
	}
}
