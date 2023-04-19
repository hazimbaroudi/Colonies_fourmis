package Colonie_Fourmis.Gui;
import Colonie_Fourmis.Algorithm.Ant;

/**
 * Class GuiTHread is the Thread that runs the Graphic Interface independently 
 * from the other threads (Algorithm / mainThread)
 */
public class GuiThread extends Thread{
  public Model mainModel ;
  public Palette mainFrame;      
  public Controler mainControler;
  public GuiThread(){
    mainFrame = new Palette();      
    mainModel = new Model();
    mainControler = new Controler(mainModel,mainFrame.getFrame());      
  }
  public void run()
  {
      try {
        }
      catch (Exception e) {
          // Throwing an exception
          System.out.println("Exception is caught");
      }
  }
  public void drawAnts(){
    for (Ant e : mainModel.getBoard().getAnts() ){
      if (mainFrame.getFrame().getMap().Drawings.indexOf(e) == -1){
        mainFrame.getFrame().getMap().Drawings.add(e);
      }
    }
  }
  public void drawPheros(){
    mainFrame.getFrame().getMap().pheroDraws = mainModel.getBoard().getPheromone();
  }
  public void drawDepart(){
    mainControler.drawRect();
  }
  public void drawArrival(){
    mainControler.drawRect();
  }
  public void drawObstacle(){
    mainControler.drawRect();
  }
  public void drawMap(){
    mainControler.drawRect();
  }
}
