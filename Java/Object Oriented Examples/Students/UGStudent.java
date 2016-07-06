
/**
 * Extension of Student to represent a Under Graduate Student
 * 
 * @see Student
 * 
 * @author Jake Darby
 */
public class UGStudent extends Student
{
	
	/**
	 * Constructor that creates a Under Graduate Student  with the given student ID and name
	 * 
	 * @param studentID Must match the regex "[0-9]{7}"
	 * @param name
	 * 
	 * @throws IllegalArgumentException Thrown when studentID is not correctly formatted
	 */
	UGStudent(String studentID, String name) throws IllegalArgumentException
	{
		super(studentID, name);
		setPassGrade(40);
	}
}
