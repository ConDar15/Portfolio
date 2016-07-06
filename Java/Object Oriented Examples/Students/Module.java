
import java.util.ArrayList;

/**
 * Models a module that a student can take as part of their course, formed of a collection of Markable objects
 * 
 * @see Markable
 * 
 * @author Jake Darby
 */
public class Module 
{
	private String moduleCode;
	private String moduleName;
	private int credits;
	private ArrayList<Markable> markables;
	
	/**
	 * Creates a module with it's module code, name and the number of credits it represents
	 * 
	 * @param moduleCode The code used by the university to represent the module
	 * @param moduleName The name of the module
	 * @param credits The number of credits that this module represents
	 */
	public Module(String moduleCode, String moduleName, int credits)
	{
		this(moduleCode, moduleName, new ArrayList<Markable>(), credits);
	}
	
	/**
	 * Sets up a Modules object with a list of Markable objects and the other perameters as specified
	 * 
	 * @param moduleCode The code used by the university to represent the module
	 * @param moduleName The name of the module
	 * @param markables A list of Markable objects that represent the assignments, exams and class tests of the module
	 * @param credits The number of credits that this module represents
	 */
	public Module(String moduleCode, String moduleName, ArrayList<Markable> markables, int credits)
	{
		setModuleCode(moduleCode);
		setModuleName(moduleName);
		setMarkables (markables );
		setCredits   (credits   );
	}
	
	/**
	 * Sets the module code of the module as defined by the university
	 * 
	 * @param moduleCode Must be in the regex format of "[0-9]{5}([.][0-9])?"
	 * 
	 * @throws IllegalArgumentException Thrown when the module code does not match the specified format
	 */
	public void setModuleCode(String moduleCode) throws IllegalArgumentException
	{
		if (moduleCode.matches("[0-9]{5}([.][0-9])?"))
		{
			this.moduleCode = moduleCode;
		}
		else
			throw new IllegalArgumentException("The moduleCode given was not correctly formatted");
	}
	
	/**
	 * @return The module code for the Module
	 */
	public final String getModuleCode()
	{
		return moduleCode;
	}
	
	/**
	 * Sets the name of the module
	 * 
	 * @param moduleName
	 */
	public final void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	
	/**
	 * @return The name of the module
	 */
	public final String getModuleName()
	{
		return moduleName;
	}
	
	/**
	 * Sets the number of credits the module represents
	 * 
	 * @param credits
	 */
	protected final void setCredits(int credits)
	{
		this.credits = Math.max(Math.min(credits, 120), 0);
	}
	
	/**
	 * @return The number of credits that the module represents
	 */
	public final int getCredits()
	{
		return credits;
	}
	
	/**
	 * Sets the Markables list of the module to a list containing copies of all the objects in markables, ersing any already present
	 * 
	 * @param markables The individual Markable objects will be copied
	 */
	public final void setMarkables(ArrayList<Markable> markables)
	{
		this.markables = new ArrayList<Markable>();
		addMarkables(markables);
	}
	
	/**
	 * Adds each markable in the list to the stored Markable objects in turn; provided that the markable is not already present
	 * 
	 * @param markables A list of Markable objects to add
	 */
	public final void addMarkables(ArrayList<Markable> markables)
	{
		for(Markable markable : markables)
		{
			try
			{
				addMarkable(markable);
			}
			catch (IllegalArgumentException e)
			{
				System.err.println("Markable " + markable.getMarkableID() + " was a duplicate and thus not added");
			}
		}
	}
	
	/**
	 * Adds a single markable to the objects list of markables
	 * 
	 * @param markable Markable object to add
	 * 
	 * @throws IllegalArgumentException Thrown if the Markable object given is already present in the Module objects list
	 */
	public void addMarkable(Markable markable) throws IllegalArgumentException
	{
		if(getMarkables().contains(markable))
			throw new IllegalArgumentException("Markable object already present");
		markables.add((Markable) markable.copy());
	}
	
	/**
	 * @return The ArrayList of Markable objects assigned to the module
	 */
	public final ArrayList<Markable> getMarkables()
	{
		return markables;
	}

	/**
	 * @return A Module object that has the same values as the current one and a list of Markables containing copies of the original ones
	 */
	public Module copy()
	{
		ArrayList<Markable> markablesCopy = new ArrayList<Markable>();
		for (Markable markable : getMarkables())
			markablesCopy.add(markable.copy());
		return new Module(getModuleCode(), getModuleName(), markablesCopy, getCredits());
	}
	
	/**
	 * @return The sum of the percentage marks of all the Markables of the Module
	 */
	public final int totalMark()
	{
		int sum = 0;
		for(Markable markable : getMarkables())
			sum += markable.getPercentageMark();
		return sum;
	}
	
	/**
	 * @return The sum of the percentage marks of all the Markables of the Module that aren't exempt (capped at 100)
	 */
	public int totalMarkAvailable()
	{
		int sum = 0;
		for(Markable markable : getMarkables())
			if (!markable.isExempt())
				sum += markable.getPercentageMark();
		return Math.min(sum, 100);
	}
	
	/**
	 * @return The sum of all the percentage achieved marks of all he Markables of the Module that aren't exempt (capped at 100)
	 */
	public int finalMark()
	{
		int sum = 0;
		for(Markable markable : getMarkables())
			if (!markable.isExempt())
				sum += markable.getPercentageAchieved();
		return Math.min(sum, 100);
	}
	
	/**
	 * Determines if the module has been passed or not
	 * 
	 * @param requiredPercentage The percentage required in order to pass the module
	 * 
	 * @return {@code (int)((finalMark()/totalMarkAvailable()) * 100) >= requiredPercentage} <p>
	 * 		   i.e. true if the pecentage of marks achieved over available marks is greater or equal to the argument
	 */
	public boolean hasPassed(int requiredPercentage)
	{
		return (int)(((float)finalMark()/totalMarkAvailable()) * 100) >= requiredPercentage;
	}
	
	/**
	 * Compares the instance to a given object for equality, but only compares the moduleCodes and ignores all other data
	 * 
	 * @param obj The object to be compared with
	 * 
	 * @return true if the obj moduleCode is equal to the instances moduleCode
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass() == getClass())
			return getModuleCode().equals(((Module)obj).getModuleCode());
		return false;
	}
	
	@Override
	public String toString()
	{
		String result = String.format("%s (%s)\nCredits: %d\nMarkables:", getModuleName(), getModuleCode(), getCredits());
		for(Markable markable : getMarkables())
			result += String.format("\n\t%s(%d): %d out of %d", 
									markable.getClass().toString(), markable.getMarkableID(), 
									markable.getPercentageAchieved(), markable.getPercentageMark());
		return result;
	}
}
