
import java.util.ArrayList;
import java.util.Date;

/**
 * A class to represent a Customer that may place orders of goods
 * 
 * @see Order
 * @see Good
 * 
 * @author Jake Darby
 */
public class Customer 
{
	private String customerID;
	private ArrayList<Order> customerOrders;
	private static final long ONE_MONTH = 2592000000L;
	
	/**
	 * Creates a new Customer with the given String as the customerID
	 * 
	 * @param customerID
	 */
	public Customer(String customerID)
	{
		this(customerID, new ArrayList<Order>());
	}
	
	/**
	 * Creates a new Customer with the given String as the customerId and the customerOrders as their initial set of orders
	 * 
	 * @param customerID
	 * @param customerOrders
	 */
	public Customer(String customerID, ArrayList<Order> customerOrders)
	{
		setCustomerID(customerID);
		setOrders(customerOrders);
	}
	
	/**
	 * Sets the customerID of the Customer
	 * 
	 * @param customerID
	 */
	protected void setCustomerID(String customerID)
	{
		this.customerID = customerID;
	}
	
	/**
	 * @return The customerID of the Customer
	 */
	public final String getCustomerID()
	{
		return customerID;
	}
	
	/**
	 * Adds the new order to the orders for the Customer as long as they aren't already storing that Order
	 * <p>
	 * Also discounts the order as it is added if the customer is a gold customer
	 * 
	 * @param newOrder
	 * 
	 * @throws IllegalArgumentException Thrown if the order is already assigned to the Customer
	 */
	public void addOrder(Order newOrder) throws IllegalArgumentException
	{
		for(Order order : getOrders())
			if(order.getOrderNumber() == newOrder.getOrderNumber())
				throw new IllegalArgumentException("Order has already been assigned to the customer");
		if(isGold())
		{
			newOrder.discount(5);
		}
		customerOrders.add(newOrder);
	}
	
	/**
	 * Adds the ArrayList of orders to the customers orders
	 * 
	 * @param orders
	 */
	public final void addOrders(ArrayList<Order> orders)
	{
		for(Order order : orders)//I swear there is absolutely no confusion to be had on this line
			addOrder(order);
	}
	
	/**
	 * Sets the customers orders to the ArrayList given, erasing any already present
	 * 
	 * @param orders
	 */
	protected final void setOrders(ArrayList<Order> orders)
	{
		customerOrders = new ArrayList<Order>();
		addOrders(orders);
	}
	
	/**
	 * @return The ArrayList of orders that the Customer has placed
	 */
	public final ArrayList<Order> getOrders()
	{
		return customerOrders;
	}
	
	/**
	 * @return true if the customer has spent more than £2000 in the last month, where a month is defined as 30 days, and false otherwise
	 */
	public final boolean isGold()
	{
		int[] sum = {0,0};
		Date today = new Date();
		for(Order order : getOrders())
		{
			long x = today.getTime() - order.getOrderDate().getTime();
			if(x <= ONE_MONTH & x >= 0);
			{
				sum[0] += order.getOrderPrice()[0];
				sum[1] += order.getOrderPrice()[1];
			}
		}
		return sum[0]*100 + sum[1] > 200000;
	}

}
