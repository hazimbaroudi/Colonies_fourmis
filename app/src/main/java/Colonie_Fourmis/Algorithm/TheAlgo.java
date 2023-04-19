package Colonie_Fourmis.Algorithm;

import java.util.concurrent.TimeUnit;

public class TheAlgo extends Thread{
  /**
   * Class TheAlgo extends to a thread to run seperately from the interface
   * which is basically the Controler of the algo
   * @param board has all information and the data for the algorithm
   */
    public Board board;
    public static double taux_evaporation=0.97;
    public static int numAnt=10 ,maxAnt=100,length_short_road=0;
    private boolean active ;
    public TheAlgo(Board board ){
        this.board = board;
        board.addAnts(numAnt);
        active = false;
    }    
    public boolean isActive() {
      return active;
    }
    public void setActive(boolean active) {
      this.active = active;
    }
    public void run(){
        while (true){
        try {
            TimeUnit.MILLISECONDS.sleep(300);
              board.evaporation_phero(TheAlgo.taux_evaporation);
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
    }
}
