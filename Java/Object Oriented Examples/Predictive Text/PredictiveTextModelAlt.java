
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class PredictiveTextModelAlt extends Observable implements ActionListener 
{
	String body;
	String currentPrefix;
	String[] predictions;
	String[] empty = {};
	TreeDictionary dict;
	int currentIndex;
	
	public PredictiveTextModelAlt() throws FileNotFoundException
	{
		this("default");
	}
	
	public PredictiveTextModelAlt(String location) throws FileNotFoundException
	{
		body = "";
		currentPrefix = "";
		currentIndex = 0;
		
		JOptionPane pane = new JOptionPane("Dictionary Loading...", 
				JOptionPane.INFORMATION_MESSAGE, 
				JOptionPane.DEFAULT_OPTION, 
				null, 
				new Object[]{});
		JDialog dialog = new JDialog();
		dialog.setTitle("Loading");
		dialog.setContentPane(pane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.pack();
		dialog.setVisible(true);

		//Loads the dictionary
		try
		{
			dict = new TreeDictionary(location);
		}
		finally
		{
			//On loading the dictionary (or throwing an Exception) the dialog box is removed
			dialog.dispose();
		}
		
		predictions = empty;
	}
	
	private String getWord()
	{
		if (predictions.length == 0 && currentPrefix.length() == 0) 
			return "";
		else if (predictions.length == 0 || currentIndex >= predictions.length)
			return currentPrefix;
		else if (currentIndex >= predictions.length)
			currentIndex = 0;
		
		return predictions[currentIndex];
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmd = Integer.parseInt(e.getActionCommand());
		setChanged();
		switch(cmd)
		{
			//Clears the currently typed in prefix
			case 1:
				body = "";
				currentPrefix = "";
				currentIndex = 0;
				predictions = empty;
				notifyObservers(body + getWord());
				break;
			//These keys just add their number to the prefix	
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				currentPrefix += Integer.toString(cmd);
				currentIndex = 0;
				predictions = dict.signatureToWords(currentPrefix).toArray(empty);
				notifyObservers(body + getWord());
				break;
			//Removes the last word (or new line) in the body
			case 10:
				currentIndex += 1;
				notifyObservers(body + getWord());
				break;

			//Adds the first item on the predictions list to the body
			case 11:
				if (currentPrefix.length() != 0)
					body += getWord() + " ";
				currentPrefix = "";
				currentIndex = 0;
				predictions = empty;
				break;

			//Adds a new Line character
			case 12:
				currentIndex = 0;
				if (currentPrefix.length() == 0)
				{
					if (body.length() != 0)
					{
						int i = body.lastIndexOf(" ");
						body = body.substring(0, i);
						i = body.lastIndexOf(" ");
						if (i == -1)
						{
							currentPrefix = dict.wordToSignature(body);
							body = "";
						}
						else
						{
							currentPrefix = dict.wordToSignature(body.substring(i+1));
							body = body.substring(0, i+1);
						}
					}
				}
				else
					currentPrefix = currentPrefix.substring(0, 
							currentPrefix.length() - 1);
				predictions = dict.signatureToWords(currentPrefix).toArray(empty);
				notifyObservers(body + getWord());
				break;
		}
	}
}
