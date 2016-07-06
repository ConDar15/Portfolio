
/**
 * An abstract class that holds methods and fields that all markable 
 * 
 * @author Jake Darby
 */
public abstract class Markable
{
	private static int staticMarkableID = 0;
	private int markableID;
	private String descriptor;
	private int percentageMark;
	private int percentageAchieved;
	private boolean exempt;
	
	/**
	 * Sets the descriptor and percentage mark of the markable as given and initialises the other fields including the markableID
	 * 
	 * @param descriptor A description of the Markable object
	 * @param percentageMark The total percentage mark available for the Markable
	 * 
	 * @throws IllegalArgumentException Thrown when percentageMark is not in the range 1 to 100 inclusive
	 */
	public Markable(String descriptor, int percentageMark) throws IllegalArgumentException
	{
		this(++staticMarkableID, descriptor, percentageMark, 0, false);
	}
	
	/**
	 * Creates a new markable with all the given data, primarily called during copying of a Markable
	 * 
	 * @param markableID
	 * @param descriptor
	 * @param percentageMark
	 * @param percentageAchieved
	 * @param exempt
	 * 
	 * @throws IllegalArgumentException Thrown when percentageMark is not in the range 1 to 100 inclusive
	 */
	protected Markable(int markableID, String descriptor, int percentageMark, int percentageAchieved, boolean exempt) throws IllegalArgumentException
	{
		setMarkableID(markableID);
		setDescriptor(descriptor);
		setPercentageMark(percentageMark);
		setPercentageAchieved(percentageAchieved);
		makeExempt(exempt);
	}
	
	/**
	 * Sets the markableID
	 * 
	 * @param markableID
	 */
	protected void setMarkableID(int markableID)
	{
		this.markableID = markableID;
	}
	
	/**
	 * @return The markableID associated with the Markable object
	 */
	public final int getMarkableID()
	{
		return markableID;
	}
	
	/**
	 * Sets the descriptor of the Markable object
	 * 
	 * @param descriptor A description of the Markable object
	 */
	public final void setDescriptor(String descriptor)
	{
		this.descriptor = descriptor;
	}
	
	/**
	 * @return The descriptor of the Markable object
	 */
	public final String getDescriptor()
	{
		return descriptor;
	}
	
	/**
	 * Sets the percentage mark that the Markable contributes
	 * 
	 * @param percentageMark Must be between 1 and 100 inclusive
	 * 
	 * @throws IllegalArgumentException Thrown when the percentage does not meet the above criteria
	 */
	protected final void setPercentageMark(int percentageMark) throws IllegalArgumentException
	{
		if(percentageMark > 0 & percentageMark <= 100)
			this.percentageMark = percentageMark;
		else
			throw new IllegalArgumentException("percentageMark given was out of range");
	}
	
	/**
	 * @return The percentage mark the Markable contributes
	 */
	public final int getPercentageMark()
	{
		return percentageMark;
	}
	
	/**
	 * Sets the percentage that the Student achieved on the markable
	 * 
	 * @param percentageAchieved Must be between 1 and the percentage mark of the Markable object inclusively
	 * 
	 * @throws IllegalArgumentException Thrown when the percentage achieved is not in the of 1 and the percentage mark of the Markable object inclusively
	 */
	protected final void setPercentageAchieved(int percentageAchieved) throws IllegalArgumentException
	{
		if(percentageAchieved >= 0 & percentageAchieved <= getPercentageMark())
			this.percentageAchieved = percentageAchieved;
		else
			throw new IllegalArgumentException("percentageAchieved given was out of range");
	}
	
	/**
	 * @return The percentage achieved, as recorded by the Markable
	 */
	public int getPercentageAchieved()
	{
		return percentageAchieved;
	}
	
	/**
	 * Sets whether this particular markable is exempt or not
	 * 
	 * @param exempt true for exempt, false for not exempt
	 */
	public void makeExempt(boolean exempt)
	{
		this.exempt = exempt;
	}
	
	/**
	 * @return true if the Markable is exempt and false otherwise
	 */
	public boolean isExempt()
	{
		return exempt;
	}
	
	/**
	 * Used to assign the marks for the Markable object to allow for subClasses which may have different methods of marking
	 * 
	 * @param percentageAchieved 
	 * @param args
	 * 
	 * @throws IllegalArgumentException 
	 */
	public abstract void mark(int percentageAchieved, Object... args) throws IllegalArgumentException;
	
	/**
	 * @return A new Markable object with all the same fields as the current one
	 */	
	public abstract Markable copy();
	
	/**
	 * Merely tests whether the markableID of the two objects is equal, not the actual internal values
	 * 
	 * @param obj An object to compare
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass() == getClass())
			return getMarkableID() == ((Markable)obj).getMarkableID();
		return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("Markable #%d\n\tDescriptor: %s\n\tPercentage Mark: %3d\n\tPercentage Achieved: %3d\n\tExempt: %b",
							 getMarkableID(), getDescriptor(), getPercentageMark(), getPercentageAchieved(), isExempt());
	}
}
