
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Models a student of the university of Birmingham by storing the Modules that they are taking
 * 
 * @see Module
 * 
 * @author Jake Darby
 */
public abstract class Student 
{
	private String studentID;
	private String name;
	private ArrayList<Module> takenModules;
	private int passGrade;
	
	/**
	 * Creates a student with the given id and name
	 * 
	 * @param studentID Must match the regex expression "[0-9]{7}"
	 * @param name
	 * 
	 * @throws IllegalArgumentException Thrown when studentID is not correctly formatted
	 */
	public Student(String studentID, String name) throws IllegalArgumentException
	{
		this(studentID, name, new ArrayList<Module>());
	}
	
	/**
	 * Creates a student with the given id, name and modules
	 * 
	 * @param studentID Must match the regex expression "[0-9]{7}"
	 * @param name
	 * @param modules
	 * 
	 * @throws IllegalArgumentException Thrown when studentID is not correctly formatted
	 */
	public Student(String studentID, String name, ArrayList<Module> modules) throws IllegalArgumentException
	{
		setStudentID(studentID);
		setName(name);
		setModules(modules);
	}
	
	/**
	 * Sets the students id number
	 * 
	 * @param studentID Must match the regex expression "[0-9]{7}"
	 * 
	 * @throws IllegalArgumentException Thrown if the student id is not correctly formatted
	 */
	protected final void setStudentID(String studentID) throws IllegalArgumentException
	{
		if (studentID.matches("[0-9]{7}"))
		{
			this.studentID = studentID;
		}
		else
		{
			throw new IllegalArgumentException("The studentID given was not correctly formatted");
		}
	}

	/**
	 * @return The students id number
	 */
	public final String getStudentID()
	{
		return studentID;
	}

	/**
	 * Sets the name of Student
	 * 
	 * @param name
	 */
	public final void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return The name of the student
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * Sets the grade required to pass a given module. Values outside the range of 0 to 100 are set appropriately to 0 or 100
	 * 
	 * @param passGrade Must be in the range 0 to 100 inclusive
	 */
	protected final void setPassGrade(int passGrade)
	{
		this.passGrade = Math.min(Math.max(passGrade, 0), 100);
	}
	
	/**
	 * @return The grade required to pass a given module
	 */
	public final int getPassGrade()
	{
		return passGrade;
	}

	/**
	 * Sets the modules the student is taking to modules after erasing the records of any present
	 * 
	 * @param modules
	 */
	public final void setModules(ArrayList<Module> modules)
	{
		takenModules = new ArrayList<Module>();
		addModules(modules);
	}
	
	/**
	 * Adds the Module objects in modules as long as they are not duplicate
	 * 
	 * @param modules
	 */
	public final void addModules(ArrayList<Module> modules)
	{
		for(Module module : modules)
		{
			try
			{
				addModule(module);
			}
			catch(IllegalArgumentException e)
			{
				System.err.println("This module is already present for the student and was not added");
			}
		}
	}
	
	/**
	 * Adds the given module to the students taken modules
	 * 
	 * @param module
	 * 
	 * @throws IllegalArgumentException Thrown if the module given is a duplicate of one of the ones already present
	 */
	public void addModule(Module module) throws IllegalArgumentException
	{
		if (getModules().contains(module))
			throw new IllegalArgumentException("Duplicate module detected");
		takenModules.add(module.copy());
	}

	/**
	 * @return The ArrayList of modules that are taken by the student
	 */
	public final ArrayList<Module> getModules()
	{
		return takenModules;
	}
	
	/**
	 * Gets the module specified by the moduleCode given
	 * 
	 * @param moduleCode Must match the {@link Module#setModuleCode(String) module code} of one of the modules
	 * 
	 * @return The Module object that has a matching moduleCode, null if one isn't found
	 */
	public Module getModule(String moduleCode)
	{
		for(Module module : getModules())
		{
			if (module.getModuleCode().equals(moduleCode))
				return module;
		}
		return null;
	}
	
	/**
	 * @param moduleCode Must match the {@link Module#setModuleCode(String) module code} of one of the modules
	 * 
	 * @return An ArrayList of the Markable objects for the module
	 * 
	 * @throws IllegalArgumentException Thrown when the module cannot be located
	 */
	public ArrayList<Markable> getMarkables(String moduleCode) throws IllegalArgumentException
	{
		try
		{
			return getModule(moduleCode).getMarkables();	
		}
		catch(NullPointerException e)
		{
			throw new IllegalArgumentException("Module with module code: " + moduleCode + " does not exist for this student");
		}
	}
	
	/**
	 * Adds The given Markable to the Module with module code equal to the one given
	 * 
	 * @param moduleCode Must match the {@link Module#setModuleCode(String) module code} of one of the modules
	 * @param markable The markable to add
	 * 
	 * @throws IllegalArgumentException Thrown when the module can't be found or the Markable is already present in the Module
	 */
	public void addMarkable(String moduleCode, Markable markable) throws IllegalArgumentException
	{
		Module module = getModule(moduleCode);
		if(module != null)
			module.addMarkable(markable);
		else
			throw new IllegalArgumentException("Module with module code: " + moduleCode + " does not exist for this student");
	}

	/**
	 * @param moduleCode Must match the {@link Module#setModuleCode(String) module code} of one of the modules
	 * @param markableID
	 * 
	 * @return The Markable, in the Module whose module code matches moduleCode, that matches the markableID or else null 
	 */
	public Markable getMarkable(String moduleCode, int markableID)
	{
		for(Markable markable : getMarkables(moduleCode))
		{
			if (markable.getMarkableID() == markableID);
				return markable;
		}
		return null;
	}
	
	/**
	 * @return A map from a Module to a Boolean indicating if the module has been passed or not
	 */
	public HashMap<Module, Boolean> moduleResults()
	{
		HashMap<Module, Boolean> results = new HashMap<Module, Boolean>();
		int requiredGrade = getPassGrade();
		for(Module module : getModules())
			results.put(module, module.hasPassed(requiredGrade));
		return results;
	}
	
	@Override
	public String toString()
	{
		String result = String.format("Student %s\nName: %s\nPassGrade: %3d\nModules:", getStudentID(), getName(), getPassGrade());
		HashMap<Module, Boolean> modules = moduleResults();
		for(Module module : modules.keySet())
			result += String.format("\n\t%s (%s) has %sbeen passed", module.getModuleName(), module.getModuleCode(),
									modules.get(module).booleanValue() ? "" : "not ");
		return result;
	}
}
