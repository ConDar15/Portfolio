
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PredictiveTextViewAlt extends JFrame implements Observer
{
	private PredictiveTextModelAlt model;
	private JTextArea body;
	private JButton[][] key;
	private static final String[][] BUTTON_LABELS = {{"1(CLEAR)",   "2(abc)",   "3(def)"},
 		 											 {"4(ghi)",     "5(jkl)",   "6(mno)"},
 		 											 {"7(pqrs)",    "8(tuv)",   "9(wxyz)"},
 		 											 {"*(CYCLE)",   "0(SPACE)", "#(BKSPACE)"}};
	
	public static void main(String[] args)
	{
		PredictiveTextViewAlt app;
		
		//Sets up the with either the default dictionary or supplied dictionary
		if(args.length >= 2 && args[0].toLowerCase().equals("-d"))
			app = new PredictiveTextViewAlt(args[1]);
		else
			app = new PredictiveTextViewAlt();
		
		//Shows the app
		app.setSize(300, 400);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
	
	public PredictiveTextViewAlt()
	{
		this("default");
	}
	
	public PredictiveTextViewAlt(String location)
	{
		try
		{
			model = new PredictiveTextModelAlt(location);
			model.addObserver(this);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();

			//Initialises the components for the display
			body = new JTextArea();
			key = new JButton[4][3];

			//Sets the display constraints for the text body and adds it to the frame
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.BOTH;
			gc.gridheight = 4;
			gc.gridwidth = 3;
			gc.weightx = 1;
			gc.weighty = 8;
			body.setEditable(false);
			body.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			this.add(new JScrollPane(body), gc);

			//Sets the layout constraints for all the buttons
			gc.weighty = 1;
			gc.gridheight = 1;
			gc.gridwidth = 1;
			//Initialises and sets the constraints for the buttons before adding them to the frame
			for(int r = 0; r < 4; r++)
				for(int c = 0; c < 3; c++)
				{
					key[r][c] = new JButton(BUTTON_LABELS[r][c]);
					key[r][c].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
					key[r][c].setActionCommand("" + (r*3 + c + 1));
					key[r][c].addActionListener(model);
					key[r][c].setMinimumSize(new Dimension(100, 33));
					gc.gridx = c;
					gc.gridy = 4 + r;
					this.add(key[r][c], gc);
				}
		}
		catch(FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null, "Dictionary could not be loaded and program will be terminated.",
										  "Error", JOptionPane.ERROR_MESSAGE);

		}
	}
	
	public void update(Observable o, Object arg)
	{
		if (o == model)
		{
			if (arg instanceof String)
			{
				String bodyText = (String) arg;
				if (bodyText.length() == 0)
					body.setText(bodyText);
				else if (bodyText.length() == 1)
					body.setText(bodyText.toUpperCase());
				else
					body.setText(Character.toString(bodyText.charAt(0)).toUpperCase() 
							+ bodyText.substring(1));
			}
		}
	}
}
