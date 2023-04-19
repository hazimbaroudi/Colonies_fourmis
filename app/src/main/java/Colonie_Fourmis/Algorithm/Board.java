package Colonie_Fourmis.Algorithm;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;
import java.awt.geom.Ellipse2D;


public class Board extends Rectangle{
	/**
	 * Class board with represents the map extends from a rectangle
	 * @param depart the departure point or the nest of the ants
	 * @param arrivee the food for the ants or the end point
	 */
		private Rectangle depart;
		private Rectangle arrivee;
		public static LinkedList<Barrier> obstacle;
		public static LinkedList<Pheromone> pheromone;
		private LinkedList<Ant> ants;

		public Board() {
			super();
			obstacle = new LinkedList<Barrier>();
			pheromone = new LinkedList<Pheromone>();
			this.ants = new LinkedList<Ant>();
		}
		/**
		 * checks if the ant e found the food
		 * @param e
		 * @return
		 */
		public boolean foundFood(Ant e){
			Ellipse2D.Double ant = new Ellipse2D.Double(e.getCenterX(),e.getCenterY(),e.step/2,e.step/2);
			boolean inter =ant.intersects(arrivee);
			
			if (inter){
				e.path.add(new Point2D.Double(arrivee.getCenterX(),arrivee.getCenterY()));
				e.path.add(new Point2D.Double(arrivee.getCenterX(),arrivee.getCenterY()));
				e.path.add(new Point2D.Double(arrivee.getCenterX(),arrivee.getCenterY()));
			} 
			return inter;
		}

		/**
		 * Does the evaporation with taux 
		 * @param taux
		 */
		public void evaporation_phero(double taux){
			LinkedList<Pheromone> pheromone_copie1 =new LinkedList<Pheromone>();
			LinkedList<Pheromone> pheromone_copie2 =new LinkedList<Pheromone>();
			
			try{
				pheromone_copie1.addAll(pheromone);
				pheromone_copie2.addAll(pheromone);		
			} catch (Exception e) {
				//TODO: handle exception
			}
			for (Pheromone e:pheromone_copie1){
				try {
					if(e.getIntensite()*(taux)<=pheromone.size()/100){
						pheromone_copie2.remove(e);
					}else{
						e.setIntensite(e.getIntensite()*(taux));
					}				
				} catch (java.lang.NullPointerException ee) {
					//TODO: handle exception
					continue;
				}
			}
			Board.pheromone.clear();
			Board.pheromone.addAll(pheromone_copie2);
		}




		public void addAnts(int nbr){
			for(int i =0;i<nbr;i++){
				Random r1 = new Random();
				this.ants.add(new Ant(r1.nextInt(depart.x,depart.x+(int)depart.getWidth()),r1.nextInt(depart.y,depart.y+(int)depart.getHeight()),10,this.ants.size()));
			}
			TheAlgo.numAnt += nbr;
		}

		public Rectangle getDepart() {
			return depart;
		}

		public void setDepart(Rectangle depart) {
			this.depart = depart;
		}

		public Rectangle getArrivee() {
			return arrivee;
		}

		public void setArrivee(Rectangle arrivee) {
			this.arrivee = arrivee;
		}

		public LinkedList<Barrier> getObstacle() {
			return obstacle;
		}

		public void setObstacle(LinkedList<Barrier> obstacles) {
			obstacle = obstacles;
		}

		public LinkedList<Pheromone> getPheromone() {
			return pheromone;
		}

		public void setPheromone(LinkedList<Pheromone> pheromones) {
			pheromone = pheromones;
		}

		public LinkedList<Ant> getAnts() {
			return ants;
		}

		public void setAnts(LinkedList<Ant> ants) {
			this.ants = ants;
		}
			
}
