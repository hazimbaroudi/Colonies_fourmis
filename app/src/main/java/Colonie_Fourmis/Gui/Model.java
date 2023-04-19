package Colonie_Fourmis.Gui;

import Colonie_Fourmis.Algorithm.Board;

public class Model {
  private Board board;
  public Model(){
    setBoard(new Board());
  }
  
  public Board getBoard() {
    return board;
  }
  public void setBoard(Board board) {
    this.board = board;
  }
}
