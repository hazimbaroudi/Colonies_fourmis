package Colonie_Fourmis.Gui;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Colonie_Fourmis.Algorithm.Ant;

/**
 * MainWindow Frame contains all the panels and organizes them in layout
 */
public class MainWindow extends JFrame{
  public MapPanel map;
  public UpperPanel head;
  public static double WIDTH,HEIGHT;
  public MainWindow(){
    setTitle("Colonie de Fourmis");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());

    setVisible(true);    
    map = new MapPanel();
    head = new UpperPanel();
    
    HEIGHT =7*this.getSize().getHeight()/8;
    WIDTH = this.getSize().getWidth();

    head.setPreferredSize(new Dimension((int)this.getSize().getWidth(),(int)this.getSize().getHeight()/8));
    // map.setPreferredSize(new Dimension((int)this.getSize().getWidth(),(int)this.getSize().getHeight()/8));

    content.add(head,BorderLayout.NORTH);
    content.add(map,BorderLayout.CENTER);
    
  }
  public void updateFrame(){
    repaint();
    map.repaint();
    head.repaint();
  }

  /**
   * Updates the frame and it's contents
   */
  public void updateFrame(Ant a){
    repaint();
    map.Drawings.remove(map.Drawings.indexOf(a));
    map.Drawings.add(a);
    map.repaint();
    head.repaint();
  }
} 
