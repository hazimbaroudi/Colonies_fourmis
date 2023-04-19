package Colonie_Fourmis.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Colonie_Fourmis.Launch;

public class Frame extends JFrame{
	/**
	 * Frame Main Frame that contains all other frames of settings and of control
	 */
  private MapPanel map;
	private ControlPanel control ;
	private Sound son=new Sound();
	private Palette pal ; 
	private JPanel boutonChoix ; 
	private JPanel boutonChoix2 ; 
	private JLabel titre ; 
	private Color background = Color.red ; 

	public Frame(Palette p) {
		pal = p ; 
		this.setTitle("Algorithme de Fourmi");
		this.setSize(1200, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout()); 
		URL music= getClass().getResource("/son/bruit.wav");
		this.playMusic(music);
	    titre = new JLabel("Colonie de Fourmis"); 
	    titre.setHorizontalAlignment(JLabel.CENTER);
	    titre.setFont(new Font("Serif", Font.BOLD, 30));
	    this.getContentPane().add(titre,BorderLayout.CENTER);
	    this.setVisible(true);
	    boutonChoix = new JPanel();
	    boutonChoix.setLayout(new GridLayout(1,3));
	    boutonChoix.setBackground(background);
	    this.getContentPane().add(boutonChoix,BorderLayout.SOUTH);
	    this.getContentPane().setBackground(background);
	    JButton jouer = new JButton("Jouer") ;
	    boutonChoix.add(jouer);
	    jouer.addActionListener((ActionEvent e)->this.play());
	 
	    JButton reglages = new JButton("Reglages");
	    boutonChoix.add(reglages);
	    reglages.addActionListener((ActionEvent e)->this.page_reglages());
	        
	    JButton quitter = new JButton("Quitter"); 
	    quitter.addActionListener((ActionEvent e)->{
	    	this.son.stop(music);
	    	System.exit(EXIT_ON_CLOSE);
	    });
	    boutonChoix.add(quitter);
	        
	    JSlider slider_son=new JSlider((int)son.f.getMinimum(),(int)son.f.getMaximum());
	    slider_son.setValue((int)son.f.getValue());
	    boutonChoix.add(slider_son);
	    this.action_sound(slider_son, son);
	    
	    boutonChoix2 = new JPanel();
	    boutonChoix2.setLayout(new GridLayout(1,2));
	    
	    JButton retourMenu = new JButton("Retour menu") ; 
	    retourMenu.addActionListener((ActionEvent e)->this.retourMenu());
	    boutonChoix2.add(retourMenu);
	    
	    JButton jouerAlgo = new JButton("Jouer") ; 
	    jouerAlgo.addActionListener((ActionEvent e)->this.page_reglages());
	    this.boutonChoix2.add(jouerAlgo);
	    
	    JButton reglages2 = new JButton("Réglages");
	    reglages2.addActionListener((ActionEvent e)->this.page_reglages());
	    boutonChoix2.add(reglages2);
	    
	}
	public void action_sound(JSlider slider,Sound son) {
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent a) {
				son.currentVolume=slider.getValue();
				son.f.setValue(son.currentVolume);
			}
		}
		);
	}
	public void playMusic(URL audio) {
		son.setFile(audio);
		son.play(audio);
		son.loop(audio);
	}
	
	
	public void page_reglages(){
		this.delete();
		this.setSize(1001,400);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(this.pal.getSettings().settings,BorderLayout.CENTER);
		this.getContentPane().add(this.pal.getSettings().sauvegarde,BorderLayout.SOUTH);
		this.getContentPane().repaint();
	}
	
	public void play() {
		this.delete();
		map = new MapPanel();
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(map,BorderLayout.CENTER);

		control = new ControlPanel();
		this.getContentPane().add(control,BorderLayout.EAST);

		this.revalidate();
		this.getContentPane().repaint();
		this.repaint();
		Launch.status =0;
	}
	
	public void delete() {
		this.getContentPane().removeAll();
	}
	
	public void retourMenu() {
		delete() ; 
		this.setSize(1200, 700);
		this.getContentPane().add(boutonChoix,BorderLayout.SOUTH);
		this.getContentPane().add(titre,BorderLayout.CENTER);
		this.getContentPane().repaint();
	}
	public MapPanel getMap() {
		return map;
	}
	public void setMap(MapPanel map) {
		this.map = map;
	}
	public ControlPanel getControl() {
		return control;
	}
	public void setControl(ControlPanel control) {
		this.control = control;
	}

}

