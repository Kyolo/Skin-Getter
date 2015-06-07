package fr.kyolo.skinGetter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The 'footer' panel containing the save button and the player name field
 * 
 * @author Malexan68 a.k.a Kyolo, modified by TheShark34
 * @version RELEASE-1.0.0
 */
@SuppressWarnings("serial")
public class SouthPanel extends JPanel implements KeyListener, ActionListener {

	/**
	 * The "Get skin" button to download the skin
	 */
	private JButton getButton = new JButton("Get skin");
	
	/**
	 * The text field where the player username is typed
	 */
	private JTextField usernameTextField = new JTextField();
	
	/**
	 * Simple constructor
	 */
	public SouthPanel() {
		this.setLayout(new BorderLayout());
		
		getButton.addActionListener(this);
		this.add(getButton, BorderLayout.EAST);
		
		usernameTextField.addKeyListener(this);
		this.add(usernameTextField, BorderLayout.CENTER);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == 10) {
			// If the Enter key is pressed, doing like if the "Get Skin" button was pressed
			getButton.doClick();
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
	}
	
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void actionPerformed(ActionEvent e) {
		SkinGetter.getInstance().loadSkinFromURL("http://skins.minecraft.net/MinecraftSkins/" + usernameTextField.getText() + ".png");
	}
	
}
