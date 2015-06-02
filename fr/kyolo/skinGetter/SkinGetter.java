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

	
	//L'instance du skingetter, une habitude que j'ai prise
	public static  SkinGetter skg = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jp = new JPanel();
	//Le jpanel sur lequel on affiche le skin
	private JPanel jp2 = new JPanel(){
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paintComponent(Graphics g){
			//On dessine un fond blanc pour vider l'affichage
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//on dessine le skin si il y en a un
			if(skin != null)
				g.drawImage(skin, 0, 26,skin.getWidth(this)*5,skin.getHeight(this)*5, this);
		}
		
	};
	
	//Les deux boutons utilisés, leurs noms parlent d'eux-même
	private JButton jb = new JButton("Get skin");
	private JButton jb2 = new JButton("Save skin");
	//L'image contenant le skin
	private Image skin = null;
	//Le champ de texte pour indiquer le pseudo
	private JTextField jtf = new JTextField();
	
	//Un simple constructeur
	public SkinGetter() {
		start();
	}
	//Si un pseudo est passé en parametre, on le charge déjà
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
		//On ajoute un KeyListener au champ de texte pour detecter la pression de la touche Entrée
		jtf.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10) {
					//Si c'est la touche entrée, on fait un clique sur le bouton pour récupérer le skin
					jb.doClick();
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			
		});
		//On organise la fenetre
		jp.setLayout(new BorderLayout());
		jp.add(jb, BorderLayout.EAST);
		jp.add(jtf, BorderLayout.CENTER);
		
		//On ajoute un ActionListener, pour detecter les clics sur le bouton Get Skin
		jb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//On récupère l'image sur les serveur de mojang
					skin = ImageIO.read(new URL("http://skins.minecraft.net/MinecraftSkins/" + jtf.getText() + ".png"));
					//On rafraichit la fenetre pour l'afficher
					jp2.repaint();
				} catch (IOException e) {
					//On prévient en cas d'erreur
					JOptionPane.showMessageDialog(SkinGetter.skg, "The skin cannot be found, try to correct the player's name (please notice than the case need to be right)", "Skin not found", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		//On ajoute un ActionListener, pour detecter les clics sur me bonton Save Skin
		jb2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//On prépare le fichier de reception du skin
				File toSerialize = new File(System.getProperty("user.home") + "/Pictures/" + jtf.getText() + ".png");
				
				//JOptionPane.showInputDialog(skg, "Where the file will be saved ?", "Save", JOptionPane.)
				
				//on vérifie si il existe déjà
				if(!toSerialize.exists()){
					//Si il n'existe pas déjà
					try {
						toSerialize.createNewFile(); //On crée le fichier
						ImageIO.write((RenderedImage) skin, "PNG", toSerialize); //On l'enregistre
						jtf.setText("");//On vide le champ de texte
						JOptionPane.showMessageDialog(SkinGetter.skg, "Save done !", null, JOptionPane.INFORMATION_MESSAGE); //Et on informe de la réussite
						
					} catch (IOException e) {
						//En cas d'erreur durant l'écriture on prévient et on écrit le stacktrace dans la console
						JOptionPane.showMessageDialog(SkinGetter.skg, "An error has been occured during saving. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				else{
					//Si l'image est déjà présente, on fait la même chose mais pour des noms différents
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
		
		//On organise la fenêtre
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
		//On donne une icone à la fenêtre, stockée sur ma dropbox
		try {
			this.setIconImage(ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/109358506/skingetter.png")));
		} catch (IOException e) {
			System.err.println("Cannot download the window's icon");
			e.printStackTrace();
		}
		//On stock l'instance du SkinGetter dans une variable static pour y acceder depuis les listeners
		skg = this;
		
		
	}
	
	public static void main(String[] args) {
		if(args.length == 1){
			new SkinGetter(args[0]);
		}
		else{
			new SkinGetter();
		}
		
	}
	
	

}
