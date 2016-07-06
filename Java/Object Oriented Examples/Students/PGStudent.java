
/**
 * Extension of Student to represent a Post Graduate Student
 * 
 * @see Student
 * 
 * @author Jake Darby
 */
public class PGStudent extends Student
{
	/**
	 * Constructor that creates a Post Graduate Student  with the given student ID and name
	 * 
	 * @param studentID Must match the regex "[0-9]{7}"
	 * @param name
	 * 
	 * @throws IllegalArgumentException Thrown when studentID is not correctly formatted
	 */
	PGStudent(String studentID, String name) throws IllegalArgumentException
	{
		super(studentID, name);
		setPassGrade(50);
	}
}
