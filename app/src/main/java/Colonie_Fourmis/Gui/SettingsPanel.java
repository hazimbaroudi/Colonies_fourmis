package Colonie_Fourmis.Gui;

import java.awt.event.*;  
import java.awt.*;  
import javax.swing.*;

import Colonie_Fourmis.Algorithm.TheAlgo;



public class SettingsPanel extends JFrame {
		/**
		 * SettingsPanel before running the algorithm
		 * Set colors and number of ants
		 */
		JButton anthill,food,obstacle,map, ant;
		JButton sauvegarde ; 
		public static Color anthillColor = Color.GREEN,foodColor = Color.LIGHT_GRAY
				,obstacleColor=(new Color (165,42,42)),mapColor=Color.BLACK,antColor=Color.BLACK;
		JSlider numberAnt ; 
		Color c ; 
		int vSpace = 50 ;
		int hSpace = 100 ;
		int length = 200 ; 
		Color background = Color.red ; 
		 
		JPanel settings, mapSettings , antSettings; 
		Palette pal ; 
	
	public SettingsPanel(Palette p) {
		
		pal = p ; 
		this.setTitle("Settings of the Game");
		this.setSize(1000, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		settings = new JPanel() ; 
		settings.setLayout(new GridLayout(1,2));
		
		mapSettings = new JPanel() ; 
		GridBagConstraints gbc = new GridBagConstraints() ;
		gbc.insets = new Insets(20,20,20,20);
		mapSettings.setLayout(new GridBagLayout()) ;
		mapSettings.setBackground(background);
		this.settings.add(mapSettings);
		
		
		JLabel anthillText = new JLabel("Colour of the Anthill :");
		gbc.gridx = 1 ;
		gbc.gridy =1 ;
		mapSettings.add(anthillText,gbc);
		JLabel foodText = new JLabel("Colour of the Food :");
		gbc.gridy = 2 ; 
		mapSettings.add(foodText,gbc);
		JLabel obstacleText = new JLabel("Colour of the Obstacle :");
		gbc.gridy = 3 ; 
		mapSettings.add(obstacleText,gbc);
		JLabel mapText = new JLabel("Colour of the Map :");
		gbc.gridy = 4 ;
		mapSettings.add(mapText,gbc);
		
		anthill=new JButton("Choose a Colour") ;
		anthill.setBackground(anthillColor);
		anthill.setOpaque(true);
		buttonColor(anthill,"anthillColor") ;
		gbc.gridx=2;
		gbc.gridy=1;
		mapSettings.add(anthill,gbc);

		food=new JButton("Choose a Colour") ;
		food.setBackground(foodColor);
		food.setOpaque(true);
		buttonColor(food,"foodColor") ;
		gbc.gridy=2;
		mapSettings.add(food,gbc);
		
		obstacle=new JButton("Choose a Colour") ; 
		obstacle.setBackground(obstacleColor);
		obstacle.setOpaque(true);
		buttonColor(obstacle,"obstacleColor") ;
		gbc.gridy=3;
		mapSettings.add(obstacle,gbc);
		
		map=new JButton("Choose a Colour") ; 
		map.setBackground(mapColor);
		map.setOpaque(true);
		buttonColor(map,"mapColor") ;
		gbc.gridy=4;
		mapSettings.add(map,gbc);
		
		antSettings = new JPanel() ; 
		antSettings.setLayout(new GridBagLayout());
		antSettings.setBackground(background);
		this.settings.add(antSettings);
		
		JLabel antText = new JLabel("Colour of the ant :") ;
		gbc.gridx=0; 
		gbc.gridy=0; 
		antSettings.add(antText,gbc);
		
		JLabel numberAntText = new JLabel("Number of ant :") ; 
		gbc.gridy=1;
		antSettings.add(numberAntText,gbc);
		
		ant=new JButton("Choose a Colour") ;
		ant.setBackground(antColor);
		ant.setOpaque(true);  
		buttonColor(ant,"antColor"); 
		gbc.gridx=1 ;
		gbc.gridy=0 ; 
		antSettings.add(ant,gbc);
		
		numberAnt = new JSlider(0, TheAlgo.maxAnt,TheAlgo.numAnt);
		numberAnt.setMajorTickSpacing(10);
		numberAnt.setMinorTickSpacing(1);
		numberAnt.setPaintTicks(true);
		numberAnt.setPaintLabels(true);
		numberAnt.addChangeListener((event) -> {sliderMoved();});
		gbc.gridy=1;
		antSettings.add(numberAnt,gbc);
		
		this.getContentPane().add(settings,BorderLayout.CENTER);
		
		sauvegarde = new JButton("Sauvegarder les reglages") ;
		sauvegarde.addActionListener((event)->{sauvegarde();});
		this.getContentPane().add(sauvegarde,BorderLayout.SOUTH);
		
	}
	
	
		public void buttonColor(JButton l,String color){
			l.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							c = JColorChooser.showDialog(null,"Pick a color", Color.BLUE) ;
							if(c==null) {
								c=Color.blue;
							}
							else if( color == "anthillColor"){
								SettingsPanel.anthillColor =c;
							}
							else if( color == "antColor"){
								SettingsPanel.antColor =c;
							}
							else if( color == "foodColor"){
								SettingsPanel.foodColor =c;
							}
							else if( color == "mapColor"){
								SettingsPanel.mapColor =c;
							}
							else if( color == "obstacleColor"){
								SettingsPanel.obstacleColor =c;
							}
							obstacle.setBackground(obstacleColor);
							// map.setBackground(mapColor);
							food.setBackground(foodColor);
							ant.setBackground(antColor);					
							anthill.setBackground(anthillColor);
						}
					}
			);
		}
		
		public void sliderMoved() {
			TheAlgo.numAnt = numberAnt.getValue();
		}
		
		public void sauvegarde() {
			this.pal.getFrame().retourMenu();
			
		}
		
}
