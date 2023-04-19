package Colonie_Fourmis.Algorithm;

import java.awt.geom.Point2D;

public class Pheromone extends Point2D.Double {
/**
 * Class pheromone extends from Point double
 */
private double intensite;
	
public Pheromone(double x,double y ) {
	super(x,y);
	this.intensite=100;	
}
	public Pheromone(double x,double y,double phero ) {
		super(x,y);
		this.intensite=phero;
	}
public double getIntensite() {
	return intensite;
}

public void setIntensite(double intensite) {
	this.intensite = intensite;
}
public String getString(){
	return "("+this.getX() +","+this.getY()+")";
}
}