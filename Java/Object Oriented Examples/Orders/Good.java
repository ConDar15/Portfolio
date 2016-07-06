
/**
 * Class to represent a type of good offered by a company
 * 
 * @author Jake Darby
 */
public class Good 
{
	private String name;
	private int[] standardPrice = new int[2];
	private boolean available;
	
	/**
	 * Constructor for the class that assumes that the good is available
	 *  
	 * @param name The name of good
	 * @param standardPrice The standard price of the good
	 * 
	 * @throws IllegalArgumentException thrown if the standardPrices is not correctly formatted for {@link #setStandardPrice(int[])}
	 */
	public Good(String name, int[] standardPrice) throws IllegalArgumentException
	{
		this(name, standardPrice, true);
	}
	
	/**
	 * Constructor that accepts also accepts a boolean
	 * 
	 * @param name The name of the good
	 * @param standardPrice The standard price of the good
	 * @param available Indicates if the good is available
	 * 
	 * @throws IllegalArgumentException Thrown if the standardPrices is not correctly formatted for {@link #setStandardPrice(int[])}
	 */
	public Good(String name, int[] standardPrice, boolean available) throws IllegalArgumentException
	{
		setName(name);
		setStandardPrice(standardPrice);
		setAvailable(available);
	}
	
	/**
	 * Sets the name of the good
	 * 
	 * @param name Must not be null
	 * 
	 * @throws IllegalArgumentException
	 */
	protected void setName(String name) throws IllegalArgumentException
	{
		if(name != null)
			this.name = name;
		else
			throw new IllegalArgumentException("name cannot be null");
	}
	
	/**
	 * @return The name of the good
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the standardPrice of the good
	 * 
	 * @param standardPrice must be a 2 dimensional array that represents a positive price formatted {pounds, pence}
	 * 
	 * @throws IllegalArgumentException thrown for badly formatted standardPrice arguments
	 */
	protected void setStandardPrice(int[] standardPrice) throws IllegalArgumentException
	{
		if(standardPrice.length == 2)
		{
			int[] x = new int[2];
			x[0] = standardPrice[0] + standardPrice[1]/100;
			x[1] = standardPrice[1]%100;
			
			if (x[1] < 0)
			{
				x[0]--;
				x[1] += 100;
			}
			
			if (x[0] < 0)
				throw new IllegalArgumentException("standardPrice must represent a positive value");
			
			this.standardPrice = x;
		}
		else
		{
			throw new IllegalArgumentException("standardPrice must contain exactly 2 elements");
		}
	}
	
	/**
	 * @return The standard price of the good in the format of {pounds, pence}
	 */
	public final int[] getStandardPrice()
	{
		return standardPrice;
	}
	
	/**
	 * @return The current price of the good
	 */
	public int[] getPrice()
	{
		return getStandardPrice();
	}
	
	/**
	 * Sets the availability of the good
	 * @param available
	 */
	public void setAvailable(boolean available)
	{
		this.available = available;
	}
	
	/**
	 * @return The available boolean
	 */
	protected boolean getAvailable()
	{
		return available;
	}
	
	/**
	 * @return The actual availability of the good
	 */
	public boolean isAvailable()
	{
		return getAvailable();
	}
	
	/**
	 * @return A copy of the object that has the same values as the current one
	 */
	public Good copy()
	{
		return new Good(getName(), getStandardPrice(), getAvailable());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass() == getClass())
			return obj.toString().equals(toString());
		return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("Good: %s\nStandard Price: £%d.%02d\nCurrent Price: £%d.%02d\nAvailable: %b",
							 getName(), getStandardPrice()[0], getStandardPrice()[1], getPrice()[0], getPrice()[1], isAvailable());
	}
}
