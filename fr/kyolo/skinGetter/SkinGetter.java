package fr.kyolo.skinGetter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author Malexan68 a.k.a Kyolo
 *
 */
public class SkinGetter extends JFrame {

	
	//Where we stock the SkinGetter, for easier acces
	public static  SkinGetter SKIN_GETTER = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel skinDisplayPanel = new JPanel();
	
	//The JPanel on which the skin is displayed
	private JPanel mainPanel = new JPanel(){
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paintComponent(Graphics g){
			//We draw a white background to clear the display
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//We draw the skin if there is one to draw
			if(skin != null)
				g.drawImage(skin, 0, 26,skin.getWidth(this)*5,skin.getHeight(this)*5, this);
		}
		
	};
	
	//The used buttons
	private JButton getButton = new JButton("Get skin");
	private JButton saveButton = new JButton("Save skin");
	
	//The Image which hold the skin
	private Image skin = null;
	
	//The TextField where inputing the username
	private JTextField usernameTextField = new JTextField();
	
	//A simple contructor
	public SkinGetter() {
		start();
	}
	
	//If a username is given, we load it right from the start
	public SkinGetter(String plrName) {
		start();
		try {
			skin = ImageIO.read(new URL("http://skins.minecraft.net/MinecraftSkins/" + plrName + ".png"));
			skinDisplayPanel.repaint();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void start() {
		//We add a KeyListener to the JTextField to detect if Enter is pressed
		usernameTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10) {
					//If it's Enter which is pressed, we manualy press the GetSkin button
					getButton.doClick();
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			
		});
		//We organize the window
		skinDisplayPanel.setLayout(new BorderLayout());
		skinDisplayPanel.add(getButton, BorderLayout.EAST);
		skinDisplayPanel.add(usernameTextField, BorderLayout.CENTER);
		
		//We add a ActionListener to the Get Skin button
		getButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//We download the picture of the given player
					skin = ImageIO.read(new URL("http://skins.minecraft.net/MinecraftSkins/" + usernameTextField.getText() + ".png"));
					//We refresh the JPanel to display the skin
					mainPanel.repaint();
				} catch (IOException e) {
					//We warn of a potential error
					JOptionPane.showMessageDialog(SkinGetter.SKIN_GETTER, "The skin cannot be found, try to correct the player's name (please notice that the case need to be right)", "Skin not found", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		//We add an ActionListener, to detect the click on the Save Skin Button
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//We ask the user in which folder we save the skin
				String where = getSavingPosition();
				
				//We check if there isn't any problem
				if(where!=null){
					if(where.equals("NOWHERE"))
						return;
				}
				
				
				for(int i=0;i<Integer.MAX_VALUE;i++){
					//We create the name of the file
					String fileName = usernameTextField.getText()+(i==0 ? "":("("+i+")"));
					File toSerialize = new File(where + fileName + ".png");
					
					//We check if the file already exist
					if(!toSerialize.exists()){
						
						try {
							//If not we create it
							toSerialize.createNewFile();
							//We fill it with the skin data
							ImageIO.write((RenderedImage) skin, "PNG", toSerialize);
							//We reset the textfield
							usernameTextField.setText("");
							//And we tell the user it's done
							JOptionPane.showMessageDialog(SkinGetter.SKIN_GETTER, "Save done !", null, JOptionPane.INFORMATION_MESSAGE);
							
						} catch (IOException e) {
							//In case of error we warn the user and print the stacktrace
							JOptionPane.showMessageDialog(SkinGetter.SKIN_GETTER, "An error has been occured during saving. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
						
						break;
						
					}
					
				}
					
			}
			
		});
		
		//We continue to organize the window
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(skinDisplayPanel, BorderLayout.SOUTH);
		mainPanel.add(saveButton, BorderLayout.NORTH);
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(326, 240);
		//this.setResizable(false);//To avoid scaling problem
		this.setResizable(true);//Else it create a bug on my computer
		this.setTitle("Minecraft Skin Getter");
		this.setLocationRelativeTo(null);
		//We put an icon to the window, stored on my Dropbox
		try {
			this.setIconImage(ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/109358506/skingetter.png")));
		} catch (IOException e) {
			System.err.println("Cannot download the window's icon");
			e.printStackTrace();
		}
		//We stock the SkinGetter in a static var, for easier acces
		SKIN_GETTER = this;
		
		
	}
	
	public static void main(String[] args) {
		if(args.length == 1){//If a param is given, we use it like a username
			new SkinGetter(args[0]);
		}
		else{
			new SkinGetter();
		}
		
	}
	
	public static String getSavingPosition(){
		//We create a JFileChooser, starting at the user default directory
		JFileChooser fle = new JFileChooser(System.getProperty("user.home"));
		//we tell we only want files
		fle.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//and then we show the dialog
		int re = fle.showSaveDialog(SKIN_GETTER);
		//we check if everything went right
		if(re==JFileChooser.APPROVE_OPTION)
			return fle.getSelectedFile().getAbsolutePath()+"/";//And we return the absolute path of the folder
		
		return "NOWHERE";
	}

}
