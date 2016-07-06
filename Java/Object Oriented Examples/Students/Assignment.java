
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Extends the Markable class to model an assignment with a due date
 * 
 * @see Markable
 * 
 * @author Jake Darby
 */
public class Assignment extends Markable 
{
	private int latePenaltyAmount;
	private static final SimpleDateFormat dueAndSubFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), 
							 		      intervalFormat  = new SimpleDateFormat("DD HH:mm:ss");
	private Date dueDate, lateInterval, submittedDate;

	/**
	 * Constructor which creates a new object
	 * 
	 * @param descriptor The descriptor of the Assignment
	 * @param percentageMark The maximum Mark attainable in the Assignment
	 * @param dueDate The due date of the assignment in the format of "dd/MM/yyyy HH:mm:ss"
	 * @param lateInterval The interval of time at which penalties are added in the format "DD HH:mm:ss"
	 * @param latePenaltyAmount The percentage taken off each late interval as a penalty
	 */
	public Assignment(String descriptor, int percentageMark, String dueDate, String lateInterval, int latePenaltyAmount)
	{
		super(descriptor, percentageMark);
		dueAndSubFormat.setLenient(false);
		intervalFormat.setLenient(false);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-00:00"));
		setDueDate(dueDate);
		setLateInterval(lateInterval);
		setLatePenaltyAmount(latePenaltyAmount);
		//Sets submitted date to a far future date to imply that it hasn't been submitted and thus return 0 from the getPecentageAchieved method
		submittedDate = new Date(123456789123456789L);
	}
	
	/**
	 * Constructor which is used primarily for copying the object
	 * 
	 * @param markable_ID The ID for the Assignment
	 * @param descriptor The descriptor of the Assignment
	 * @param percentageMark The maximum Mark attainable in the Assignment
	 * @param percentageAchieved The percentage that has been achieved in the Assignment
	 * @param exempt If the Assignment is exempt or not
	 * @param dueDate The due date of the assignment in the format of "dd/MM/yyyy HH:mm:ss"
	 * @param lateInterval The interval of time at which penalties are added in the format "DD HH:mm:ss"
	 * @param latePenaltyAmount The percentage taken off each late interval as a penalty
	 * @param submittedDate The date and time on which the assignment was submitted
	 */
	private Assignment(int markable_ID, String descriptor, int percentageMark, int percentageAchieved, boolean exempt, String dueDate, String lateInterval, int latePenaltyAmount, String submittedDate)
	{
		super(markable_ID, descriptor, percentageMark, percentageAchieved, exempt);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-00:00"));
		setDueDate(dueDate);
		setLateInterval(lateInterval);
		setLatePenaltyAmount(latePenaltyAmount);
		setSubmittedDate(submittedDate);
	}
	
	/**
	 * @return {@inheritDoc}, minus any marks for late submission
	 */
	@Override
	public int getPercentageAchieved()
	{
		try
		{
			if (submittedDate.after(dueDate) & submittedDate.getTime() != 123456789123456789L)
				return Math.max(super.getPercentageAchieved() - latePenaltyAmount*(int)(((submittedDate.getTime()) - dueDate.getTime())/(lateInterval.getTime() - 86400000L) + 1), 0); //Dark voodoos afoot here
			return super.getPercentageAchieved();
		}
		catch(ArithmeticException e)
		{
			return super.getPercentageAchieved();
		}
		
	}

	/**
	 * Sets the percentage reduction for each late penalty
	 * 
	 * @param latePenaltyAmount
	 */
	protected final void setLatePenaltyAmount(int latePenaltyAmount)
	{
		this.latePenaltyAmount = latePenaltyAmount;
	}
	
	/**
	 * @return The percentage reduction for each late penalty
	 */
	public final int getLatePenaltyAmount()
	{
		return latePenaltyAmount;
	}
	
	/**
	 * Sets the due date of the Assignment
	 * 
	 * @param dueDate Must be in the format "dd/MM/yyyy HH:mm:ss
	 * 
	 * @throws IllegalArgumentException Thrown when the dueDate given is not in the correct format
	 */
	protected void setDueDate(String dueDate) throws IllegalArgumentException
	{
		try
		{
			this.dueDate = dueAndSubFormat.parse(dueDate);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("dueDate was not correctly formatted",e);
		}
	}
	
	/**
	 * @return The due date of the assignment
	 */
	public final Date getDueDate()
	{
		return dueDate;
	}
	
	/**
	 * @return The String representation of the due date of the assignment
	 */
	public String getDueDateString()
	{
		return dueAndSubFormat.format(dueDate);
	}
	
	/**
	 * Sets the interval after which another late penalty is added
	 * 
	 * @param lateInterval Must be in the form "DD HH:mm:ss"
	 * 
	 * @throws IllegalArgumentException Thrown if the argument is not in the correct format
	 */
	public void setLateInterval(String lateInterval) throws IllegalArgumentException
	{
		try 
		{
			lateInterval = String.format("%02d %s",
										  Integer.parseInt(lateInterval.substring(0, 2)) + 1,
										  lateInterval.substring(3));
			this.lateInterval = new Date(intervalFormat.parse(lateInterval).getTime() + 86400000L);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("lateInterval was not correctly formatted",e);
		}
	}
	
	/**
	 * @return The late interval of the assignment
	 */
	public final Date getLateInterval()
	{
		return lateInterval;
	}
	
	/**
	 * @return The String representation of the late interval of the assignment
	 */
	public String getLateIntervalString()
	{
		String temp = intervalFormat.format(getLateInterval());
		return String.format("%02d %s",
							 Integer.parseInt(temp.substring(0,2)) - 2,
							 temp.substring(3));
	}
	
	/**
	 * Sets the date on which the assignment was submitted
	 * 
	 * @param submittedDate Must be in the format "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @throws IllegalArgumentException Thrown when the submittedDate is not correctly formatted
	 */
	protected void setSubmittedDate(String submittedDate) throws IllegalArgumentException
	{
		try
		{
			this.submittedDate = dueAndSubFormat.parse(submittedDate);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("submittedDate was not correctly formatted",e);
		}
	}
	
	/**
	 * @return The date on which the assignment was submitted
	 */
	public final Date getSubmittedDate()
	{
		return submittedDate;
	}
	
	/**
	 * @return The String representation of the date on which the assignment was submitted
	 */
	public String getSubmittedDateString()
	{
		return dueAndSubFormat.format(submittedDate);
	}
	
	/**
	 * Marks the assignment by setting the percentageAchieved and the submittedDate
	 * 
	 * @param percentageAchieved The percentage this assignment achieved
	 * @param args The first argument should be a String formatted as "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @throws IllegalArgumentException Thrown when an exception occurs with args
	 */
	@Override
	public void mark(int percentageAchieved, Object... args) throws IllegalArgumentException
	{
		try
		{
			setPercentageAchieved(percentageAchieved);
			setSubmittedDate((String) args[0]);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("args was invalid", e);
		}
	}
	
	@Override
	public Assignment copy()
	{
		return new Assignment(getMarkableID(), getDescriptor(), getPercentageMark(), getPercentageAchieved(), isExempt(),
						  	  getDueDateString(), getLateIntervalString(), getLatePenaltyAmount(), getSubmittedDateString());
	}
	
	@Override
	public String toString()
	{
		return String.format("%s\n\tDue Date: %s\n\tSubmitted Date: %s",
							 super.toString(), getDueDateString(), getSubmittedDate().getTime() == 123456789123456789L ? "N/A" : getSubmittedDateString());
	}
}
