package Colonie_Fourmis.Algorithm;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Barrier extends Line2D.Double{
    /**
     * Class Barriere is the obstacle extends from Lined2D
     * @param a the starting point
     * @param b the end point
     */
    public Barrier (Point a, Point b) {
       super(a.x,a.y,b.x,b.y);
    }
    /**
     * if the line created by Point C and Point D intersects with the current obstacle
     * @param C
     * @param D
     * @return
     */
    public boolean isCrossing (Point2D C, Point2D D) {
        return this.intersectsLine(C.getX(),C.getY(),D.getX(),D.getY());
    }

}
