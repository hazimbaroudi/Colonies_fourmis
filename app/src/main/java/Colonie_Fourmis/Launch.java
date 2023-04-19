package Colonie_Fourmis;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import Colonie_Fourmis.Algorithm.Ant;
import Colonie_Fourmis.Algorithm.TheAlgo;
import Colonie_Fourmis.Algorithm.ThreadAnt;
import Colonie_Fourmis.Gui.GuiThread;

/**
 * Class Launch MainClass MainThread 
 * static int status indicates the step of our process 
 * Status: 
 *  0 : Settings (draw map rectangle)
 *  1 : Settings ( draw departure of ants)
 *  2 : Settings ( draw arrival of ants)
 *  3 : Settings (draw obstacle)
 *  4: Init all the variables for the algorithm to run
 *  5 : Execute algorithm
 *  6 : Put the algorithm on pause
 */
public class Launch {
  GuiThread gui;
  TheAlgo algo ;
  public static int timer=0;
  public static int status;
  LinkedList<ThreadAnt> threads;
  public Launch(){
    gui = new GuiThread();
    gui.start();
    threads = new LinkedList<ThreadAnt>();     
    status =-1;
    while (true ){
      this.next();
    }
  }
  public void test(){
    gui.mainControler.initTest();  

  }
  public static void main(String[] args){
    Launch main_thread;
    main_thread= new Launch();
  }
  
  /**
   * Moves to next Step
   */
  public void next(){
    if (status > -1) gui.mainFrame.getFrame().getControl().setInfo(status);
    switch ( status ){
      case -1: break;
      case 0 : gui.drawMap();break;
      case 1 : gui.drawDepart();break;
      case 2 : gui.drawArrival();break;
      case 3 : {
        for (ThreadAnt tAnt : threads){
          tAnt.setActive(false);
        }
        if (algo != null){
          algo.setActive(false);
        }
        gui.drawObstacle();break;
      }
      case 4 : {
        if (algo == null){
          this.algo = new TheAlgo(gui.mainModel.getBoard());
          for (Ant a : algo.board.getAnts()){
            ThreadAnt tAnt = new ThreadAnt(a,algo.board);
            threads.add(tAnt);
            tAnt.start();
          }
          algo.start();
        }
        gui.drawAnts();
        gui.drawPheros();   
        gui.mainFrame.getFrame().repaint();
        for (ThreadAnt a :threads){
          a.setActive(true);
        }
        algo.setActive(true);
        Launch.status ++;
        return;
      }
      case 5 : {
        gui.drawAnts();
        gui.drawPheros();   
        gui.mainFrame.getFrame().repaint();
        try {
          TimeUnit.MILLISECONDS.sleep(10);
          gui.mainFrame.getFrame().getControl().updateStats();
          timer++;
          if (timer%1000==0 && algo.board.getAnts().size()<TheAlgo.maxAnt){
            algo.board.addAnts(1);
            ThreadAnt tAnt = new ThreadAnt(algo.board.getAnts().get(threads.size()),algo.board);
            tAnt.setActive(true);
            tAnt.start();
            threads.add(tAnt); 
            }
          
          return;
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      case 6 :{
        for (ThreadAnt tAnt : threads){
          tAnt.setActive(false);
        }
        if (algo != null){
          algo.setActive(false);
        }
      }
    }
    waiting();
  }
  /**
   * Waits for change in the status
   */
  public void waiting( ){
    int sts = Launch.status;
    while(sts == status && sts !=  5){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}
