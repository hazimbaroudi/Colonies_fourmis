package Colonie_Fourmis.Algorithm;

import java.util.concurrent.TimeUnit;

public class ThreadAnt extends Thread {
  /**
   * Thread ant which contains the Ant and allows it to run seperately from the other ants
   * to make faster and in case an ant is stuck the other ants won't wait 
   */
  public Ant ant ;
  private Board board ;
  private boolean active;
  public ThreadAnt(Ant a,Board board){
    ant = a;
    this.board = board;
    active = false;
  }

  @Override
  public void run (){
    while (true){
        try {
          if (active){
            ant.moveNext(Board.pheromone,Board.obstacle);
            if (board.foundFood(ant)){
                ant.returnDepart(board.getPheromone(),board.getObstacle(),true);
              }    
          }
          TimeUnit.MILLISECONDS.sleep(100);
            
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }
  }
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}
