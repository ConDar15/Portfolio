
/**
 * Extends the Markable class to model an main examination held
 * 
 * @author Jake Darby
 */
public class Exam extends Markable 
{
	private int seatNumber;
	
	/**
	 * Creates an Exam object with the  descriptor, percentage mark and seat number as specified
	 * 
	 * @param descriptor A description of the exam
	 * @param percentageMark The maximum attainable mark for the test
	 * @param seatnumber The seat number for the test
	 */
	public Exam(String descriptor, int percentageMark, int seatnumber)
	{
		super(descriptor, percentageMark);
		setSeatNumber(seatnumber);
	}
	
	/**
	 * Used primarily for copying the object, creates an Exam object with all the specified values
	 * 
	 * @param markableID
	 * @param descriptor
	 * @param percentageMark
	 * @param percentageAchieved
	 * @param exempt
	 * @param seatNumber
	 */
	private Exam(int markableID, String descriptor, int percentageMark, int percentageAchieved, boolean exempt, int seatNumber)
	{
		super(markableID, descriptor, percentageMark, percentageAchieved, exempt);
		setSeatNumber(seatNumber);
	}
	
	/**
	 * Sets the seatNumbr for the Exam object
	 * 
	 * @param seatNumber
	 */
	public final void setSeatNumber(int seatNumber)
	{
		this.seatNumber = seatNumber;
	}
	
	/**
	 * @return The seat number for the object
	 */
	public final int getSeatNumber()
	{
		return seatNumber;
	}
	
	/**
	 * Sets the percentage achieved in the test
	 * 
	 * @param percentageAchieved The percentage achieved in the test
	 * @param args Ignored
	 */
	@Override
	public void mark(int percentageAchieved, Object... args)
	{
		setPercentageAchieved(percentageAchieved);
	}
	
	/**
	 * @return A copy of the Exam object with the seat number incremented by one
	 */
	@Override
	public Exam copy()
	{
		return new Exam(getMarkableID(), getDescriptor(), getPercentageMark(), getPercentageAchieved(), isExempt(), seatNumber+1);
	}

	@Override
	public String toString()
	{
		return String.format("%s\n\tSeat Number: %d", super.toString(), getSeatNumber());
	}
}
