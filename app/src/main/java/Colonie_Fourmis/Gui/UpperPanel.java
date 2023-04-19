package Colonie_Fourmis.Gui;

import javax.swing.JPanel;
import java.awt.*;
/**
 * UpperPanel is the Panel above the map that shows
 * some instructions and the current state of the program
 */
public class UpperPanel extends JPanel{
  
  public UpperPanelModel mdl ;
  public UpperPanel(){
    this.mdl = new UpperPanelModel();
  }
  
  public void paintComponent(Graphics g){
		super.paintComponent(g);
    Graphics2D g2d= (Graphics2D) g;
    g2d.drawString(mdl.text,this.getWidth()/2,this.getHeight());
  }
  /**
   * Model that contains the data used in this panel
   */
  public class UpperPanelModel{
    private String text="Draw something";

    public String getText() {
      return text;
    }
    /**
     * set the tip text to the parameters and repaint the panel
     * @param text Tip Text
     */
    public void setText(String text) {
      this.text = text;
      repaint();
    }
  }
}
