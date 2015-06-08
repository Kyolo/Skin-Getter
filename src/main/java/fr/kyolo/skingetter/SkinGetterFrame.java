package fr.kyolo.skingetter;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The main Skin-Getter frame
 * 
 * @author Adrien
 * @version RELEASE-1.0.0
 */
@SuppressWarnings("serial")
public class SkinGetterFrame extends JFrame {
	
	/**
	 * The main panel of the window
	 */
	private MainPanel mainPanel = new MainPanel();
	
	/**
	 * Simple constructor starting the program
	 */
	public SkinGetterFrame() {
		this.setTitle("Minecraft Skin Getter");
		this.setSize(500, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(mainPanel);
		
		try {
			// Trying to read the icon from my dropbox
			Image icon = ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/109358506/skingetter.png"));
			
			// Then setting it
			this.setIconImage(icon);
		} catch (IOException e) {
			System.err.println("Cannot download the window's icon");
			e.printStackTrace();
		}
		
		this.setVisible(true);
	}
	
	/**
	 * Constructor with a pre-defined name
	 * 
	 * @param plrName
	 *            The pre-defined name
	 */
	public SkinGetterFrame(String plrName) {
		// Launching the default constructor
		this();
		
		// Then loading the skin
		SkinGetter.getInstance().loadSkinFromURL("http://skins.minecraft.net/MinecraftSkins/" + plrName + ".png");
		//And showing the name in the text field
		mainPanel.setTextFieldText(plrName);
	}
	
	/**
	 * Returns the current main panel
	 * 
	 * @return The current main panel
	 */
	public MainPanel getMainPanel() {
		return this.mainPanel;
	}
}
