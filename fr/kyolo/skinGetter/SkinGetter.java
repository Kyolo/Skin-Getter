package fr.kyolo.skinGetter;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The Main class containing all methods to get/save the skin
 * 
 * @author Malexan68 a.k.a Kyolo, modified by TheShark34
 * @version RELEASE-1.0.0
 */
public class SkinGetter {

	/**
	 * The current SkinGetter instance
	 */
	private static SkinGetter instance;

	/**
	 * The main SkinGetter frame
	 */
	private SkinGetterFrame mainFrame;

	public static void main(String[] args) {
		// Setting the UI style to the system default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		instance = new SkinGetter();
		instance.start(args);
	}

	/**
	 * Starts SkinGeter
	 * 
	 * @param args
	 *            The given argument
	 */
	private void start(String[] args) {
		// If an argument was given
		if (args.length == 1)
			// Starting the frame and loads the skin
			mainFrame = new SkinGetterFrame(args[0]);

		// Else if there's not arguments
		else
			// Starting the frame
			mainFrame = new SkinGetterFrame();
	}

	/**
	 * Save a skin
	 */
	public void saveSkin() {
		// Opening a dialog to select the skin path
		String skinPath = getSkinToSavePath();
		
		// Checking if the user agreed
		if(skinPath == null)
			// If not, stopping
			return;
		
		// Getting the skin to save file
		File skinToSave = new File(skinPath);
		
		//We check if the file already exist
		if(!skinToSave.exists()){
			try {					
				// Saving the skin
				ImageIO.write((RenderedImage) this.mainFrame.getMainPanel().getSkin(), "PNG", skinToSave);

				//And we tell the user it's done
				JOptionPane.showMessageDialog(this.mainFrame, "Save done !", "Skin-Getter", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				// If it failed printing an error message dialog
				JOptionPane.showMessageDialog(this.mainFrame, "An error has been occured during saving. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
				
				// Then printing the error
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Opens a dialog to choose the skin to save path
	 * 
	 * @return The absolute path of the skin to save
	 */
	private String getSkinToSavePath() {
		// Creating a file chooser at the user home
		JFileChooser fle = new JFileChooser(System.getProperty("user.home"));

		// Showing the dialog
		int re = fle.showSaveDialog(mainFrame);

		// If the user approved
		if (re == JFileChooser.APPROVE_OPTION)
			// Returning the absolute path of the selected file
			return fle.getSelectedFile().getAbsolutePath();

		// If the user didn't approved, returning null
		return null;
	}
	
	/**
	 * Loads a skin from a URL
	 * 
	 * @param url
	 *            The url of skin to load
	 */
	public void loadSkinFromURL(String url) {
		try {
			// Downloading the skin
			Image skin = ImageIO.read(new URL(url));
			
			// Setting the skin as the downloading image
			SkinGetter.getInstance().getFrame().getMainPanel().setSkin(skin);
			
			//We refresh the JPanel to display the skin
			SkinGetter.getInstance().getFrame().getMainPanel().repaint();
		} catch (IOException er) {
			// Printing an error message dialog
			JOptionPane.showMessageDialog(SkinGetter.getInstance().getFrame(), "The skin cannot be found, try to correct the player's name (please notice that the case need to be right)", "Skin not found", JOptionPane.ERROR_MESSAGE);
		
			// Printing the error
			er.printStackTrace();
		}
	}
	
	/**
	 * Returns the current SkinGetter instance
	 * 
	 * @return The current SkinGetter instance
	 */
	public static SkinGetter getInstance() {
		return instance;
	}

	/**
	 * Returns the current SkinGetter Frame
	 * 
	 * @return The current SkinGetter Frame
	 */
	public SkinGetterFrame getFrame() {
		return this.mainFrame;
	}

}
