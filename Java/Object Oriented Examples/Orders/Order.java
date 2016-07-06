
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class represents a particular order placed by a customer that consists of various goods
 * 
 * @see Good
 * 
 * @author Jake Darby
 */
public class Order 
{
	private static int staticOrderNumber = 0;
	private int orderNumber;
	private ArrayList<Good> orderGoods;
	private Date orderDate;
	private int[] orderPrice = new int[2];
	private static SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
		
	/**
	 * Constructor creates an Order from an ArrayList of goods that it has been given
	 * 
	 * @param orderGoods The goods used to build the order
	 * @param orderDate The date the order was placed must be formatted 'dd/mm/yyyy'
	 */
	public Order(ArrayList<Good> orderGoods, String orderDate)
	{
		setOrderNumber(++staticOrderNumber);
		setGoods(orderGoods);
		setOrderDate(orderDate);
		dateParser.setLenient(false);
	}
	
	/**
	 * Sets the orderNumber of the order
	 * 
	 * @param orderNumber
	 */
	protected void setOrderNumber(int orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	
	/**
	 * @return The order number for the order
	 */
	public final int getOrderNumber()
	{
		return orderNumber;
	}
	
	/**
	 * Adds a given good to the orderGoods of the order if the good is available
	 * 
	 * @param good
	 */
	protected void addGood(Good good)
	{
		if(good.isAvailable())
		{
			//Updates the order price with the price of the good
			int[] x = good.getPrice();
			orderPrice[1] += x[1];
			orderPrice[0] += x[0] + orderPrice[1]/100;
			orderPrice[1] %= 100;
			//Adds a copy of the good to the ArrayList rather than a reference to the original
			orderGoods.add(good.copy());
		}
		else
		{
			System.err.printf("Good: %s is not available and was not included in the order", good.getName());
		}
	}

	/**
	 * Adds the goods in goodsList if the good is available
	 *
	 * @param goodsList
	 */
	public final void addGoods(ArrayList<Good> goodsList)
	{
		for(Good good : goodsList)
			addGood(good);
	}
	
	/**
	 * Sets the orderGoods of the Order to those of the goodsList given
	 * 
	 * @param goodsList
	 */
	protected final void setGoods(ArrayList<Good> goodsList)
	{
		this.orderGoods = new ArrayList<Good>();
		addGoods(goodsList);
	}
	
	/**
	 * @return The list of goods that make up the order
	 */
	public final ArrayList<Good> getGoods()
	{
		return orderGoods;
	}
	
	/**
	 * Sets the date of the order
	 * 
	 * @param date Must be formatted as 'dd/mm/yyyy'
	 * 
	 * @throws IllegalArgumentException Thrown when a ParseException occurs i.e. when the date String is not correctly formatted
	 */
	protected void setOrderDate(String date) throws IllegalArgumentException
	{
		try
		{
			orderDate = dateParser.parse(date);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("date was not correctly formatted",e);
		}
	}
	
	/**
	 * @return The order date of the order
	 */
	public final Date getOrderDate()
	{
		return this.orderDate;
	}
	
	/**
	 * @return A string representing the date of the order
 	 */
	public String getOrderDateString()
	{
		return dateParser.format(orderDate);
	}
	
	/**
	 * @return The price of the order, which does not vary even if the cost of the individual goods do
	 */
	public final int[] getOrderPrice()
	{
		return orderPrice;
	}
	
	/**
	 * Applies a discount to the order
	 * 
	 * @param percentage A percentage discount to give where 5% discount (i.e. 95% price) is represented by the value 5
	 * 
	 * @throws IllegalArgumentException Thrown when the percentage is not in the range 0 to 100 inclusive
	 */
	public final void discount(int percentage) throws IllegalArgumentException
	{
		if(percentage < 0 | percentage > 100)
			throw new IllegalArgumentException("Percentage was out of bounds");
		else
		{
			int reduction = 100 - percentage;
			int[] x = getOrderPrice();
			x[0] = x[0] * reduction;
			x[1] = x[1] * reduction + x[0]%100;
			x[0] /= 100;
			x[1] /= 100;
			orderPrice = x;
		}
	}
}
