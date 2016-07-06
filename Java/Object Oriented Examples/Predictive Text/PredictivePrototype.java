
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Scanner;

/**
 * A class that provides static methods for aiding predictive text input
 * 
 * @author Jake Darby
 */
public class PredictivePrototype 
{
	private static final String dictionaryLocation = "words";
	
	/**
	 * Converts a given word to a numerical signature. The signature is
	 * 		the numbers that one needs to press on a phone keypad to
	 * 		input the given word. Non alphabetic characters are converted
	 * 		to spaces.
	 * <p>
	 * Examples: "hello" = "43556", "world" = "96753"
	 * 
	 * @param word The word to find the signature for
	 * @return A string representing the signature of the word
	 */
	public static String wordToSignature(String word)
	{
		StringBuilder sig = new StringBuilder();
		for(char letter : word.toLowerCase().toCharArray())
		{
			switch(letter)
			{
				case 'a':
				case 'b':
				case 'c':
					sig.append('2');
					break;
				
				case 'd':
				case 'e':
				case 'f':
					sig.append('3');
					break;
					
				case'g':
				case'h':
				case'i':
					sig.append('4');
					break;
					
				case 'j':	
				case 'k':
				case 'l':
					sig.append('5');
					break;
				
				case 'm':
				case 'n':
				case 'o':
					sig.append('6');
					break;
				
				case 'p':
				case 'q':
				case 'r':
				case 's':
					sig.append('7');
					break;
					
				case 't':
				case 'u':
				case 'v':
					sig.append('8');
					break;
				
				case 'w':
				case 'x':
				case 'y':
				case 'z':
					sig.append('9');
					break;
					
				default:
					sig.append(' ');
			}
		}
		return sig.toString();
	}
	
	/**
	 * Reads the dictionary, at the dictionaryLocation defined in the file,
	 * 		line by line adding words with the same signature as the input.
	 * 		These words are then added to the output Set.
	 * 
	 * @param signature A String representing a numerical signature of a word 
	 * @return A set of words in the dictionary that match the signature given
	 */
	public static Set<String> signatureToWords(String signature)
	{
		//Highly inefficient as sequential file access is a very expensive function to perform
		LinkedHashSet<String> foundWords = new LinkedHashSet<String>();
		try
		{
			Scanner fileReader = new Scanner(
					PredictivePrototype.class.getResourceAsStream(dictionaryLocation));
			while (fileReader.hasNext())
			{
				String next = fileReader.next().toLowerCase();
				if (signature.equals(wordToSignature(next)))
					foundWords.add(next);
			}
			if (foundWords.isEmpty())
				foundWords.add("N/A");
		}
		catch (NullPointerException ex)
		{
			foundWords.add("<Error: Dictionary could not be found>");
		}
		return foundWords;
	}
	
	/**
	 * @param word The word to check
	 * @return <code>true</code> if the word contains no alphabetic characters and <code>false</code> otherwise
	 */
	public static boolean isValidWord(String word)
	{
		return !word.matches(".*[^a-zA-Z].*");
	}
}
