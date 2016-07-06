
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * An extension of the Good class that has a best before date
 * 
 * @see Good
 * 
 * @author Jake Darby
 */
public class PerishableGood extends Good
{
	private Date bestBefore;
	//Converts a correctly formatted String to a Date and vice versa
	private static SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
	//These two values indicate the number of milliseconds in two and eight days respectively
	private static final long TWO_DAYS   = 172800000L;
	private static final long EIGHT_DAYS = 691200000L;
	
	/**
	 * Constructor sets up the perishable good and assumes that it is available
	 * 
	 * @param name The name of the good
	 * @param standardPrice The standard price of the good
	 * @param bestBefore The best before date of the object, must be formatted as 'dd/mm/yyy'
	 * 
	 * @throws IllegalArgumentException Thrown if standardPrice or bestBefore are not correctly formatted
	 */
	public PerishableGood(String name, int[] standardPrice, String bestBefore) throws IllegalArgumentException
	{
		this(name, standardPrice, bestBefore, true);
		dateParser.setLenient(false);
	}
	
	/**
	 * Constructor sets up the perishable good
	 * 
	 * @param name The name of the good
	 * @param standardPrice The standard price of the good
	 * @param bestBefore The best before date of the object, must be formatted as 'dd/mm/yyyy'
	 * @param available The availability of the good
	 * 
	 * @throws IllegalArgumentException Thrown if standardPrice or bestBefore are not correctly formatted
	 */
	public PerishableGood(String name, int[] standardPrice, String bestBefore, boolean available)
	{
		super(name, standardPrice, available);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-00:00"));
		setBestBefore(bestBefore);
	}
	
	/**
	 * Sets the best before date of the good
	 * 
	 * @param bestBefore Must be formatted as 'dd/mm/yyyy'
	 * 
	 * @throws IllegalArgumentException Thrown when a ParseException occurs
	 */
	protected void setBestBefore(String bestBefore) throws IllegalArgumentException
	{
		try
		{
			this.bestBefore = dateParser.parse(bestBefore);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("Date was not in the correct format", e);
		}
	}
	
	/**
	 * @return The Date object that represents the best before date
	 */
	public final Date getBestBefore()
	{
		return bestBefore;
	}
	
	/**
	 * @return A String representing the best before date
	 */
	public final String getBestBeforeString()
	{
		return dateParser.format(getBestBefore());
	}
	
	/**
	 * Indicates if the good is in need of disposal
	 * @return true if the current date is past the best before date, false otherwise 
	 */
	public final boolean disposalNeeded()
	{
		return new Date().after(bestBefore);
	}
	
	@Override
	public int[] getPrice()
	{
		//Checks whether the difference between the current date and the best before date is less than eight days or not
		if (getBestBefore().getTime() - new Date().getTime() >= EIGHT_DAYS)
			return getStandardPrice();
		else
		{
			int[] x = getStandardPrice();
			//Calculates half of the standard price
			return new int[] {x[0]/2, x[1]/2 + (x[0] % 2 == 1 ? 50 : 0)};
		}
	}
	
	@Override
	public boolean isAvailable()
	{
		//The good must be available and the differnce between the current and best before dates must be more than two days
		return (getBestBefore().getTime() - new Date().getTime() >= TWO_DAYS) & getAvailable();
	}
	
	@Override
	public PerishableGood copy()
	{
		return new PerishableGood(getName(), getStandardPrice(), getBestBeforeString(), getAvailable());
	}
	
	@Override
	public String toString()
	{
		return String.format("%s\nBest Before Date: %s", super.toString(), getBestBeforeString());
	}
}
