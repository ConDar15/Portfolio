
import java.util.Set;

/**
 * An interface of methods to represent a predictive text dictionary
 * 
 * @author Jake Darby
 */
public interface Dictionary 
{
	/**
	 * Converts the given word into a numeric signature that represents the buttons that
	 * 		must be pressed on a phone keypad to enter the number
	 * 
	 * @param word The word that is to be converted to a numeric signature
	 * @return The string representing the numerical signature of the word
	 */
	public String wordToSignature(String word);
	
	/**
	 * @param sig A String object that represents the numerical signature of words
	 * @return A sorted set of words that match the numerical signature represented by the input
	 */
	public Set<String> signatureToWords(String sig);
}
