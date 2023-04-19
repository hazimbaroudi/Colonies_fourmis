package Colonie_Fourmis.Gui;

import Colonie_Fourmis.Launch;
import Colonie_Fourmis.Algorithm.Barrier;
import Colonie_Fourmis.Algorithm.Board;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Controler class to communicate between the :
 * @param mainModel the model that contains data before transfering to the algorithm    
 * @param mainFrame controls the frame and all of the things showing on it
 */
public class Controler {
  private Model mainModel ;
  private Frame mainFrame;

  public Controler(Model mdl,Frame view){
    this.mainFrame=view;
    this.mainModel = mdl;  
  }
  public void initTest(){
    this.mainFrame.getMap().Drawings.add(new Rectangle(100,100,1000,500));
    this.mainModel.getBoard().setDepart(new Rectangle(100,100,1000,500));
    this.mainFrame.getMap().Drawings.add(new Rectangle(200,100,100,100));
    this.mainModel.getBoard().setDepart(new Rectangle(200,100,100,100));
    this.mainFrame.getMap().Drawings.add(new Rectangle(700,500,100,100));
    this.mainModel.getBoard().setArrivee(new Rectangle(700,500,100,100));
    this.mainFrame.getMap().repaint();
}
  /**
   * Draw a resizable Rectangle with the mouse
   */
  public void drawRect(){
    ResizeShape rshape = new ResizeShape();
    mainFrame.getMap().addMouseListener(rshape);
    mainFrame.getMap().addMouseMotionListener(rshape);
  }
  public Model getMainModel() {
    return mainModel;
  }

  public void setMainModel(Model mainModel) {
    this.mainModel = mainModel;
  }

  public Frame getMainFrame() {
    return mainFrame;
  }

  public void setMainFrame(Frame mainFrame) {
    this.mainFrame = mainFrame;
  }

  /**
   * Class ResizeShape to use the mouse to draw a Rectangle
   * boolean resize :
   *      true means it's being resized at the moment 
   *      false it sablizes and set the the final rectangle to the board
   * Rectangle resizeRect :
   *      the current size of the rectangle being drawn
   */
  private class ResizeShape implements MouseMotionListener, MouseListener{
    Rectangle resizeRect;
    boolean resize=true;
    public ResizeShape(){
      resizeRect = new Rectangle();
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
      // TODO Auto-generated method stub
      if (resize){
        resizeRect.setBounds(arg0.getX(), arg0.getY(), 20, 20);
        Controler.this.mainFrame.getMap().Drawings.add(resizeRect);
        Controler.this.mainFrame.getMap().repaint();
      }
      
    }

    @Override
    public  void mouseReleased(MouseEvent arg0) {
      /**
       * Once the mouse is released we stablizethe rectangle by setting resize = false
       * draw map
       * then if we check Departure is null  
       * then the if the Arrival is null 
       * else we are drawing the obstacles
       * 
       * Last we increase the status of the Launching process
       */
      if (resize){
        resize = false;
        if (Controler.this.mainModel.getBoard().getWidth() == 0){
          Controler.this.mainModel.getBoard().setBounds(resizeRect);
          Board.obstacle.add(new Barrier(new Point(Controler.this.mainModel.getBoard().x,Controler.this.mainModel.getBoard().y),new Point(Controler.this.mainModel.getBoard().x+Controler.this.mainModel.getBoard().width,Controler.this.mainModel.getBoard().y)));
          Board.obstacle.add(new Barrier(new Point(Controler.this.mainModel.getBoard().x,Controler.this.mainModel.getBoard().y),new Point(Controler.this.mainModel.getBoard().x,Controler.this.mainModel.getBoard().y+Controler.this.mainModel.getBoard().height)));
          Board.obstacle.add(new Barrier(new Point(Controler.this.mainModel.getBoard().x+Controler.this.mainModel.getBoard().width,Controler.this.mainModel.getBoard().y),new Point(Controler.this.mainModel.getBoard().x+Controler.this.mainModel.getBoard().width,Controler.this.mainModel.getBoard().y+Controler.this.mainModel.getBoard().height)));
          Board.obstacle.add(new Barrier(new Point(Controler.this.mainModel.getBoard().x,Controler.this.mainModel.getBoard().y+Controler.this.mainModel.getBoard().height),new Point(Controler.this.mainModel.getBoard().x+Controler.this.mainModel.getBoard().width,Controler.this.mainModel.getBoard().y+Controler.this.mainModel.getBoard().height)));      
        }
        else if (Controler.this.mainModel.getBoard().getDepart() == null){
          Controler.this.mainModel.getBoard().setDepart(resizeRect);
          System.out.println(Controler.this.mainModel.getBoard().getDepart().getX());
        }
        else if ( Controler.this.mainModel.getBoard().getArrivee() == null){
          Controler.this.mainModel.getBoard().setArrivee(resizeRect);
        }
        else {
          // Transform obstacle from a rectangle to 4 Line2D
          Point upperleft = new Point((int)resizeRect.getMinX(),(int)resizeRect.getMinY());
          Point upperright = new Point((int)resizeRect.getMaxX(),(int)resizeRect.getMinY());
          Point downleft = new Point((int)resizeRect.getMinX(),(int)resizeRect.getMaxY());
          Point downright = new Point((int)resizeRect.getMaxX(),(int)resizeRect.getMaxY());

          Controler.this.mainModel.getBoard().getObstacle().add(new Barrier(upperleft,upperright));
          Controler.this.mainModel.getBoard().getObstacle().add(new Barrier(upperleft,downleft));
          Controler.this.mainModel.getBoard().getObstacle().add(new Barrier(upperright,downright));
          Controler.this.mainModel.getBoard().getObstacle().add(new Barrier(downleft,downright));
          Launch.status =6;
          return;
        }
        Launch.status ++;
      }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
      // TODO Auto-generated method stub
      if (resize){
        resizeRect.setBounds(resizeRect.x, resizeRect.y,Math.abs(resizeRect.x - arg0.getX()), Math.abs(resizeRect.y-arg0.getY()));
        Controler.this.mainFrame.getMap().Drawings.removeLast();
        Controler.this.mainFrame.getMap().Drawings.add(resizeRect);
        Controler.this.mainFrame.getMap().repaint();
      }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
    }

  }

}
