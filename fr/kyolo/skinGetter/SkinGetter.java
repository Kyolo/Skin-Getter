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
	public static  SkinGetter skg = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jp = new JPanel();
	
	//The JPanel on which the skin is displayed
	private JPanel jp2 = new JPanel(){
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
	private JButton jb = new JButton("Get skin");
	private JButton jb2 = new JButton("Save skin");
	
	//The Image which hold the skin
	private Image skin = null;
	
	//The TextField where inputing the username
	private JTextField jtf = new JTextField();
	
	//A simple contructor
	public SkinGetter() {
		start();
	}
	
	//If a username is given, we load it right from the start
	public SkinGetter(String plrName) {
		start();
		try {
			skin = ImageIO.read(new URL("http://skins.minecraft.net/MinecraftSkins/" + plrName + ".png"));
			jp.repaint();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void start() {
		//We add a KeyListener to the JTextField to detect if Enter is pressed
		jtf.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10) {
					//If it's Enter which is pressed, we manualy press the GetSkin button
					jb.doClick();
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			
		});
		//We organize the window
		jp.setLayout(new BorderLayout());
		jp.add(jb, BorderLayout.EAST);
		jp.add(jtf, BorderLayout.CENTER);
		
		//We add a ActionListener to the Get Skin button
		jb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//We download the picture of the given player
					skin = ImageIO.read(new URL("http://skins.minecraft.net/MinecraftSkins/" + jtf.getText() + ".png"));
					//We refresh the JPanel to display the skin
					jp2.repaint();
				} catch (IOException e) {
					//We warn of a potential error
					JOptionPane.showMessageDialog(SkinGetter.skg, "The skin cannot be found, try to correct the player's name (please notice that the case need to be right)", "Skin not found", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		//We add an ActionListener, to detect the click on the Save Skin Button
		jb2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//We prepare the file which will hold the skin
				File toSerialize = new File(System.getProperty("user.home") + "/Pictures/" + jtf.getText() + ".png");
				
				//(WIP) JOptionPane.showInputDialog(skg, "Where the file will be saved ?", "Save", JOptionPane.)
				
				//We check if the file already exist
				if(!toSerialize.exists()){
					//And if it doesn't...
					try {
						toSerialize.createNewFile(); //We create the file
						ImageIO.write((RenderedImage) skin, "PNG", toSerialize); //We save it
						jtf.setText("");//We clear the JTextField
					JOptionPane.showMessageDialog(SkinGetter.skg, "Save done !", null, JOptionPane.INFORMATION_MESSAGE); //And we tell it's done
					} catch (IOException e) {
						//In case of error, we warn the user and print the StackTrace in the console
						JOptionPane.showMessageDialog(SkinGetter.skg, "An error has been occured during saving. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				else{
					//If there is already a file by the given name, we try the same things for an other name
					for(int i=2;i<20;i++){
						File toSerialize2 = new File(System.getProperty("user.home") + "/Pictures/" + jtf.getText() + " (" + i + ")" + ".png");
						
						if(!toSerialize2.exists()){
							
							try {
								toSerialize2.createNewFile();
								ImageIO.write((RenderedImage) skin, "PNG", toSerialize2);
								jtf.setText("");
								JOptionPane.showMessageDialog(SkinGetter.skg, "Save done !", null, JOptionPane.INFORMATION_MESSAGE);
								
							} catch (IOException e) {
								JOptionPane.showMessageDialog(SkinGetter.skg, "An error has been occured during saving. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							
							break;
							
						}
						
					}
					
				}
				
			}
			
		});
		
		//We continue to organize the window
		jp2.setLayout(new BorderLayout());
		jp2.add(jp, BorderLayout.SOUTH);
		jp2.add(jb2, BorderLayout.NORTH);
		this.setContentPane(jp2);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(326, 240);
		this.setResizable(false);
		this.setTitle("Minecraft Skin Getter");
		this.setLocationRelativeTo(null);
		//We put a window picture, stored on my Dropbox
		try {
			this.setIconImage(ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/109358506/skingetter.png")));
		} catch (IOException e) {
			System.err.println("Cannot download the window's icon");
			e.printStackTrace();
		}
		//We stock the SkinGetter in a static var, for easier acces
		skg = this;
		
		
	}
	
	public static void main(String[] args) {
		if(args.length == 1){//If a param is given, we use it like a username
			new SkinGetter(args[0]);
		}
		else{
			new SkinGetter();
		}
		
	}

}
