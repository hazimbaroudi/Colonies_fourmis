package Colonie_Fourmis.Gui;
import javax.swing.JButton;
import javax.swing.JPanel;

import Colonie_Fourmis.Launch;
import Colonie_Fourmis.Algorithm.Board;
import Colonie_Fourmis.Algorithm.TheAlgo;

import java.awt.event.*;  
import java.awt.*;  
import javax.swing.*;

public class ControlPanel extends JPanel{
  /**
   * Control panel that shows to right of the map
   * Control during the execution of algorithm 
   * @param info shows the step we currently in
   * @stats shows stats about the execution
   */
  JSlider evaporationSpeed;
  JButton pause,start,drawObstacle;
  JLabel evaporation,info,stats;
  public ControlPanel(){
    this.setLayout(new GridBagLayout());  
    GridBagConstraints gbc = new GridBagConstraints() ;
		gbc.insets = new Insets(20,20,20,20);

    pause = new JButton("Pause");
    start = new JButton("Start");
    drawObstacle = new JButton("Draw obstacle");
    evaporation = new JLabel("Evaporation Speed");
    info = new JLabel("");
    stats = new JLabel(String.format("<html>Number of Ants %d<br/>Number of pheromones %d<br/>Evaporation Speed %.1f%%per 0.1s<br/>Longueur chemin %d</html>",TheAlgo.numAnt,Board.pheromone.size(),1000-TheAlgo.taux_evaporation*1000,TheAlgo.length_short_road));
    evaporation.setForeground(Color.WHITE);
    evaporationSpeed = new JSlider(0, 100,2);
		evaporationSpeed.setMajorTickSpacing(10);
		evaporationSpeed.setMinorTickSpacing(5);
		evaporationSpeed.setPaintTicks(true);
		evaporationSpeed.setPaintLabels(true);
    evaporationSpeed.setForeground(Color.WHITE);
    evaporationSpeed.setBackground(Color.DARK_GRAY);
    info.setForeground(Color.WHITE);
    stats.setForeground(Color.WHITE);
    evaporationSpeed.setValue(10);
    
    TheAlgo.taux_evaporation=(1000- (double)evaporationSpeed.getValue())/1000;
    this.updateSpeed();
		evaporationSpeed.addChangeListener((event) -> this.updateSpeed());


    gbc.gridx = 0 ;
		gbc.gridy =0 ;
    gbc.gridwidth =2;
    gbc.gridheight =2;
    this.add(stats,gbc);

    gbc.gridheight =1;
    gbc.gridx = 0 ;
		gbc.gridy =2 ;
    gbc.gridwidth =2;
    this.add(info,gbc);

    gbc.gridwidth =1;

    gbc.gridx = 1 ;
		gbc.gridy =5 ;
    this.add(pause,gbc);

    gbc.gridx = 0 ;
		gbc.gridy =5 ;
    this.add(start,gbc);

    gbc.gridx=1;
    gbc.gridy=6;
    this.add(evaporationSpeed,gbc);

    gbc.gridx=0;
    gbc.gridy=6;
    this.add(evaporation,gbc);
    this.setBackground(Color.DARK_GRAY);

    gbc.gridx=0;
    gbc.gridy=7;
    this.add(drawObstacle,gbc);
    

    drawObstacle.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event) {
        Launch.status = 3;
      }
    });;
    start.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event) {
        Launch.status = 4;
      }
    });;
    pause.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event) {
        Launch.status = 6;
      }
    });;
 
  }
  public void updateSpeed(){
    TheAlgo.taux_evaporation=(1000- (double)evaporationSpeed.getValue())/1000;
    // System.out.printf("Changed to %.4f\n",TheAlgo.taux_evaporation);
    this.updateStats();
  }
  public void updateStats(){
    stats.setText(String.format("<html>Number of Ants %d<br/>Number of pheromones %d<br/>Evaporation Speed %.1f%%per 0.1s<br/>Longueur chemin %d</html>",TheAlgo.numAnt,Board.pheromone.size(),1000-TheAlgo.taux_evaporation*1000,TheAlgo.length_short_road));    
    repaint();
  }
  public void setInfo(int status){
    switch (status){
      case 0 : info.setText("Draw Map of ants");break;
      case 1 : info.setText("Draw the nest");break;
      case 2 : info.setText("Draw food for ants");break;
      case 3 : info.setText("Draw obstacle");break;
      case 4 : info.setText("Stuck");break;
      case  5 : info.setText("Ants are looking for food ...");break;
      default : info.setText("Waiting ...");
    }
  }
  public JSlider getEvaporationSpeed() {
    return evaporationSpeed;
  }
  public void setEvaporationSpeed(JSlider evaporationSpeed) {
    this.evaporationSpeed = evaporationSpeed;
  }

}
