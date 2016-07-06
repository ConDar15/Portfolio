
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * An implementation of the Dictionary method that utilises a recursive structure for data storage and access.
 * 
 * @see predictive.Dictionary Dictionary
 * @author Jake Darby
 */
public class TreeDictionary implements Dictionary
{
	/*
	 * NOTES:
	 * 		- wordSigRef is used for efficiency to save repeatedly calling wordToSignature
	 * 		- Indices for branch are often given as calculations of the prefix and word,
	 * 			where Character.digit(chr, 10) - 2 gives the correct branch. Further the
	 * 			the length of the prefix at a given node gives the character in a string
	 * 			to check for the branch to traverse.
	 */
	
	private static final String defaultDictionary = "words";
	private static HashMap<String, String> wordSigRef;
	
	private TreeDictionary[] branch = {null, null, null, null,
									   null, null, null, null};
	private Set<String> wordsSet;
	private String prefix;
	
	/**
	 * Constructor that utilises the default dictionary stored in the predictive package
	 * 
	 * @throws FileNotFoundException Thrown if the dictionary cannot be loaded
	 */
	public TreeDictionary() throws FileNotFoundException
	{
		this("default");
	}
	
	/**
	 * Constructor that creates a dictionary that utilises the dictionary at the given file location
	 * 
	 * @param location The absolute path to the dictionary that is to be used
	 * @throws FileNotFoundException Thrown if the dictionary cannot be located
	 */
	public TreeDictionary(String location) throws FileNotFoundException
	{
		wordsSet = null;
		prefix = "";
		wordSigRef = new HashMap<String, String>();
		
		try
		{
			Scanner fileReader;
			//Different initialisations of the dictionary for the local dictionary and absolute paths to dictionaries
			if(location.equals("default"))
				fileReader = new Scanner(this.getClass().getResourceAsStream(defaultDictionary));
			else
				fileReader = new Scanner(new File(location));
			
			//Initialises the first children
			for(char c = 0; c < 8; c++)
				branch[c] = new TreeDictionary(Integer.toString(c+2), new TreeSet<String>());
			
			//Reads in each word and stores them in the tree
			while(fileReader.hasNext())
			{
				String word = fileReader.next().toLowerCase();
				if(PredictivePrototype.isValidWord(word))
				{
					//Adds the word to wordSigRef if it isn't already present
					if(!wordSigRef.containsKey(word)) 
						wordSigRef.put(word, wordToSignature(word));
					String sig = wordSigRef.get(word);
					//Adds the word to the correct branch
					branch[Character.digit(sig.charAt(0), 10) - 2].add(word);
				}
			}
		}
		catch(NullPointerException ex)
		{
			//Thrown if the defaultDictionary cannot be found
			throw new FileNotFoundException("Dictionary File could not be found");
		}
		
	}
	
	/**
	 * Used internally to create child nodes of the root
	 * 
	 * @param prefix The prefix for the tree to take
	 * @param initialSet The initial set to use as the wordSet
	 */
	private TreeDictionary(String prefix, Set<String> initialSet)
	{
		this.prefix = prefix;
		wordsSet = initialSet;
	}
	
	/**
	 * Recursively adds a word to the tree, moving down the branches until it has nowhere further to go
	 * 
	 * @param word The word to be added to the TreeDictionary
	 */
	private void add(String word)
	{
		//Adds the word to the nodes set of words
		wordsSet.add(word);
		//Equivalent to stopping when wordToSignature(word).equals(prefix)
		if(word.length() > prefix.length())
		{
			//Calculates the next branch to follow. See notes at class head
			int next = Character.digit(wordSigRef.get(word).charAt(prefix.length()), 10) - 2;
			//null references must be made into Trees
			if(branch[next] == null)
				branch[next] = new TreeDictionary(prefix + (next + 2), //next is in the range 0-7 so must be incremented by 2
												  new TreeSet<String>());
			//Recursively adds the word down the tree
			branch[next].add(word);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Utilises the PredictivePrototype.wordToSignature(String) method
	 * 
	 * @see PredictivePrototype#wordToSignature(String) wordToSignature(String)
	 */
	@Override
	public String wordToSignature(String word) 
	{
		return PredictivePrototype.wordToSignature(word);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returns any words with a matching prefix. A recursive function.
	 */
	@Override
	public Set<String> signatureToWords(String signature) 
	{
		//Return an empty Set when the signature has any invalid characters
		if(signature.matches(".*[^23456789].*") || signature.equals(""))
			return new LinkedHashSet<String>();
		int next;
		return prefix.equals(signature) ? truncate(wordsSet, prefix.length())
									    : (branch[next = Character.digit(signature.charAt(prefix.length()), 10) - 2] == null 
									    		? new LinkedHashSet<String>()
									    		: branch[next].signatureToWords(signature));
	}
	
	/**
	 * @param S The set to truncate the elements of
	 * @param len The length to truncate to
	 * @return A set of all the Strings in S truncated to length <code>len</code> in the order they were retrieved.
	 */
	private Set<String> truncate(Set<String> S, int len)
	{
		LinkedHashSet<String> newSet = new LinkedHashSet<String>();
		for(String word : S)
			newSet.add(word.length() > len ? word.substring(0, len) : word);
		return newSet;
	}
	
	//Methods below here created for PredictiveGUIPrototype for my own interest and as such are not currently JUnit tested.
	
	/**
	 * @param signature The prefix signature to look for
	 * @return A Set of all the words at the node that match the given signature without truncating
	 */
	public Set<String> signatureToWordsFull(String signature)
	{
		if(signature.matches(".*[^23456789].*") || signature.equals(""))
			return new LinkedHashSet<String>();
		int next;
		return prefix.equals(signature) ? wordsSet 
										: (branch[next = Character.digit(signature.charAt(prefix.length()), 10) - 2] == null 
													? new LinkedHashSet<String>() 
													: branch[next].signatureToWordsFull(signature));
	}
}
