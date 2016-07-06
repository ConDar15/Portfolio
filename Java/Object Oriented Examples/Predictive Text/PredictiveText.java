
/**
 * A runner class for PredictiveTextViewAlt
 * 
 * @see PredictiveTextViewAlt
 * @author Jake Darby
 */
public class PredictiveText 
{
	/**
	 * Start an instance of PredictiveTextViewAlt
	 * 
	 * @param args If the first argument is -d (case insensitive) and followed 
	 * 			     by another argument the second is used as the dictionary location
	 */
	public static void main(String[] args)
	{
		PredictiveTextViewAlt app;
		
		//Sets up the with either the default dictionary or supplied dictionary
		if(args.length >= 2 && args[0].toLowerCase().equals("-d"))
			app = new PredictiveTextViewAlt(args[1]);
		else
			app = new PredictiveTextViewAlt();
		
		//Shows the app
		app.setSize(500, 400);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
}
