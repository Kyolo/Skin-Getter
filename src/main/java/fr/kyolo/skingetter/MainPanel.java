package fr.kyolo.skingetter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The main panel of the window
 * 
 * @author Malexan68 a.k.a Kyolo, modified by TheShark34
 * @version RELEASE-1.0.0
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel implements ActionListener {

	/**
	 * The current load skin
	 */
	private Image skin;
	
	/**
	 * The "Save skin" button to save the skin
	 */
	private JButton saveButton = new JButton("Save skin");
	
	/**
	 * The 'footer' panel containing the save button and the player name text field
	 */
	private SouthPanel skinDisplayPanel = new SouthPanel();
	
	/**
	 * The current skin scaling
	 */
	public static final int SKIN_SCALING = 7;
	
	/**
	 * Inits the panel
	 */
	public MainPanel() {
		this.setLayout(new BorderLayout());
		
		this.add(skinDisplayPanel, BorderLayout.SOUTH);
		
		saveButton.addActionListener(this);
		this.add(saveButton, BorderLayout.NORTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Clearing the display by drawing a white background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Drawing the skin only if there is a skin to draw
		if(skin!=null){
			
			//First, calculating the skin width
			int width = (skin.getWidth(this)/skin.getHeight(this))*this.getHeight();
			
			// Then drawing the skin
			g.drawImage(skin, 0, saveButton.getHeight(), width, this.getHeight(), this);
		}
	}
	
	/**
	 * Sets the skin to draw
	 * 
	 * @param skin
	 *            The new skin to draw
	 */
	public void setSkin(Image skin) {
		this.skin = skin;
	}
	
	/**
	 * Returns the current load skin
	 * 
	 * @return The current load skin
	 */
	public Image getSkin() {
		return this.skin;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == saveButton) {
			SkinGetter.getInstance().saveSkin();
		}
	}
	
	public void setTextFieldText(String text){
		skinDisplayPanel.setTextFieldText(text);
	}
}
