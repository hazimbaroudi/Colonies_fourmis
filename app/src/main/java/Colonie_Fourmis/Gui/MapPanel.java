package Colonie_Fourmis.Gui;

import javax.swing.JPanel;
import Colonie_Fourmis.Algorithm.Ant;
import Colonie_Fourmis.Algorithm.Pheromone;

import java.util.LinkedList;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MapPanel extends JPanel{
  /**
   * Jpanel that draws the map 
   * @param Drawings contains shapes of ants,departure,arrival,map,obstacles
   * @param Pheromones contains pheromones to transfer them into circles and draw them
   * 
   */
  public LinkedList<Shape> Drawings ;
  public LinkedList<Pheromone> pheroDraws ;
  
  public boolean drawPhero=false;
  public MapPanel(){
    Drawings = new LinkedList<Shape>();
    pheroDraws = new LinkedList<Pheromone>();
  }
  public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D) g;
    LinkedList<Pheromone> pheroDraws_COPIE = new LinkedList<Pheromone>();
    try {
      pheroDraws_COPIE.addAll(pheroDraws);  
    } catch (Exception e) {
      //TODO: handle exception
      try {
        if (pheroDraws.size() > 0)pheroDraws_COPIE.addAll(pheroDraws.subList(0,pheroDraws.size()-1));          
      } catch (Exception ee) {
        //TODO: handle exception
      }
    }
    for (Pheromone p : pheroDraws_COPIE){
      try {
        double color = ((double)p.getIntensite()+1)/100;        
        color = color *255;
        if (color > 255) color = 255;
        g2d.setColor(new Color(255,255-(int) color, 0));
        g2d.fill(new Ellipse2D.Double(p.getX(),p.getY(),5,5));  
      } catch (Exception e) {
        //TODO: handle exception
        continue;
      }
    }  
  
    LinkedList <Shape> shapesDraw = new LinkedList<Shape>();
    shapesDraw.addAll(Drawings);
    for (Shape p : shapesDraw){
      if (p instanceof Ant){
        g2d.setColor(SettingsPanel.antColor);
        g2d.draw(p);
        g2d.fill(p);
        g2d.drawString(String.valueOf(((Ant)p).id),(int) p.getBounds().getCenterX(),(int) p.getBounds().getCenterY()-5);
        continue;
      }
      switch (Drawings.indexOf(p)){
        case 0  : {
          g2d.setColor(SettingsPanel.mapColor);
          g2d.draw(p);
          break;
        }
        case 1 :         {g2d.setColor(SettingsPanel.anthillColor);g2d.draw(p);break;}
        case 2:          {g2d.setColor(SettingsPanel.foodColor);break;}
        default:         {g2d.setColor(SettingsPanel.obstacleColor);break;}
      }
      g2d.draw(p);
      if (Drawings.indexOf(p) != 0 ){
        g2d.fill(p);
      }
      }
    }
}