package Colonie_Fourmis.Algorithm;
import org.apache.commons.math3.distribution.NormalDistribution;

import static java.lang.Math.PI;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;
import java.awt.geom.Ellipse2D; 


public class Ant extends Ellipse2D.Double {
    /**
     * Class Ant does the calculations to move around the map
     * Class extends a 2DEllipse Like a circle because width = height
     * @param step distance between each step for the ant
     * @param id id to recognize our ants
     * @param scanRange the radius of the circle where the ant can smell the pheromones
     */
    public Stack<Point2D.Double> path ;
    public int step=50;
    public int id;
    public int scanRange =3*step;
    public Ant(int x,int y,int radius,int id) {
        super(x,y,radius,radius);
        this.id = id;
        path = new Stack<>();
        path.add(new Point2D.Double(x,y));
    }
    /**
     * Method to return to the departure 
     * @param list_phero list of pheromones to place pheromones
     * @param obstacle list of obstacles so we don't place pheromones outside the map
     * @param depot wether to depose pheromones on the way back or not
     */
    public void returnDepart(LinkedList<Pheromone> list_phero,LinkedList<Barrier> obstacle,boolean depot){
        this.setFrame(path.firstElement().getX(),path.firstElement().getY(),this.getHeight(),this.getHeight());
        if (depot){
            if (this.path.size() < TheAlgo.length_short_road || TheAlgo.length_short_road ==0){
                TheAlgo.length_short_road = this.path.size();
            }
            this.depose_Phero(list_phero,obstacle);
        }
        else { 
            Point2D.Double np = path.firstElement();
            path.clear();
            path.push(np) ;
        }
    }
    /**
     * Place the pheromones and avoid putting them outside the bordures(obstacles)
     * Quantity and intensity of the pheromones decreases as we approach the departure
     * @param list_phero list of pheromones to place pheromones
     * @param obstacle list of obstacles so we don't place pheromones outside the map
     */
    public void depose_Phero(LinkedList<Pheromone> list_phero,LinkedList<Barrier> obstacle){
        int cap=this.path.size();
        double instense = cap;
        Point2D.Double np = path.firstElement();
        while (!this.path.isEmpty()){
            Point2D e = path.pop();
            double angle=PI/4;
            Pheromone next=new Pheromone(e.getX(), e.getY(),instense*100/cap);
            list_phero.add(next);
            for (int i=1;i<instense;i++){
                next=new Pheromone(e.getX()+this.step/2*Math.cos(i*angle), e.getY()+this.step/2*Math.sin(i*angle),instense*100/cap);
                if(this.isCrossingBorders(obstacle,next)) {
                    list_phero.add(next);
                }
            }
            instense --;
        }
        path.push(np);
    }
    /**
     * Moving the ant
     * 1-At first and to avoid too much data on the laptop if the path is x10 the shortest path
     *  the ant goes back to the nest without placing pheromones
     * 2- Ant scans the area with function getCircle then :
     *      2.1-if there are no pheromones the ant will find a random next place using no data
     *              but still the possibility of returning back lower than going forward
     *              so the Ant follow a normaal distribution with standard deviation = 0.2
     *              and mean =0 
     *              the generated angle will be used to calculate next point
     *              if there was an obstacle check (3)
     *      2.2-if there were pheromones we caculate the weighted point max_pont and calculate the angle with that angle
     *              and use the normal distribution with mean = angle with weighted point sd = the mean intensity
     *              generale an angle (chosen_angle) and create next point in the same angle and distance depends ifit's bigger than
     *              step or smaller comparing to the max_pond
     * 3-if there were obstacles in the next point we deviate by 0.09 randian to each side (positive and negative)
     *      until we got a point that does not cross the obstacles 
     * 4- finally inserting move to the next point(next)
     * 5-Call addTopath()
     * @param list_phero list of pheromones to place pheromones
     * @param obstacle list of obstacles so we don't place pheromones outside the map
     */
    public void moveNext(LinkedList<Pheromone> pheromones,LinkedList<Barrier> obstacle){
        if (this.path.size()> 10*TheAlgo.length_short_road && TheAlgo.length_short_road>0 ){
            returnDepart(pheromones,obstacle, false);
        }
        try{
            Pheromone [] pheroRange = this.getCircle(pheromones,scanRange);
            pheroRange= this.getPhero_devant(pheroRange);
        
            if (pheroRange.length ==0){
                NormalDistribution sdn = new NormalDistribution(0,2);
                double b = sdn.sample();

                double nextX =this.getX()+ Math.cos(b) * step;
                double nextY = this.getY()+ Math.sin(b) * step;

                Point2D next=new Point2D.Double(nextX,nextY);
                Point2D next_bis=new Point2D.Double(nextX,nextY);
                boolean v1=this.isCrossingAny(obstacle,next);
                boolean v2=this.isCrossingAny(obstacle,next_bis);
                double c=b;
                while(!v1&&!v2){
                    b+=0.09;
                    c-=0.09;
                    next=new Point2D.Double((this.getX()+ Math.cos(b) * step),(this.getY()+ Math.sin(b) * step));
                    next_bis=new Point2D.Double((this.getX()+ Math.cos(c) * step),(this.getY()+ Math.sin(c) * step));
                    v1=this.isCrossingAny(obstacle,next);
                    v2=this.isCrossingAny(obstacle,next_bis);
                }
                if(v2){
                    next=next_bis;
                }

                addToPath();
                this.setFrame(next.getX(),next.getY(), this.getHeight(), this.getHeight());
            }
            else{
                Pheromone max_pond = get_emplacement_moyenne_phero(pheroRange, 0, pheroRange.length);
                NormalDistribution sdn = new NormalDistribution(this.angleNexStep(max_pond),1-max_pond.getIntensite()/100);
                double nextX,nextY,chosen_angle;
                chosen_angle = sdn.sample();
                double max_pondDistance = max_pond.distance(new Point2D.Double (this.getX(),this.getY()));
                // if (max_pondDistance>step){
                    nextX =this.getX()+ Math.cos(chosen_angle) * step;
                    nextY = this.getY() - (Math.sin(chosen_angle) * step);                                    
                // }
                // else{
                //     nextX =this.getX()+ Math.cos(chosen_angle) * max_pond.distance(new Point2D.Double (this.getX(),this.getY()));
                //     nextY = this.getY()+ Math.sin(chosen_angle) * -(max_pond.distance(new Point2D.Double (this.getX(),this.getY())));    
                // }
                Point2D.Double next=new Point2D.Double(nextX,nextY);
                Point2D.Double next_bis=new Point2D.Double(nextX,nextY);
                boolean v1=this.isCrossingAny(obstacle,next);
                boolean v2=this.isCrossingAny(obstacle,next_bis);
                double b=chosen_angle;
                double c=b;
                while(!v1&&!v2){
                    b+=0.09;
                    c-=0.09;
                    next=new Point2D.Double((this.getX()+ Math.cos(b) * step),(this.getY()+ Math.sin(b) * step));
                    next_bis=new Point2D.Double((this.getX()+ Math.cos(c) * step),(this.getY()+ Math.sin(c) * step));
                    v1=this.isCrossingAny(obstacle,next);
                    v2=this.isCrossingAny(obstacle,next_bis);
                }
                if(v2){
                    next=next_bis;
                }

                addToPath();
                this.setFrame(next.x,next.y, this.getHeight(), this.getHeight());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 
     * @param pheromones pheromone
     * @param distance  distance of scanning
     * @return returns the list of pheormones inside the circle of radius distance
     */
    public Pheromone[] getCircle(LinkedList<Pheromone> pheromones,int distance){
        /*
            Fonction pour recuperer les pheromones qui sont dans un certaine circl avec rayon = distance
        */
        LinkedList<Pheromone> pheromone_copie =new LinkedList<Pheromone>();
        try {
            pheromone_copie.addAll(pheromones);
        } catch (Exception e) {
            try {
                if (pheromones.size()>1)pheromone_copie.addAll(pheromones.subList(0,pheromones.size()-1));            
                else {
                    return new Pheromone[0];
                }                    
            } catch (Exception ee) {
                //TODO: handle exception
                return new Pheromone[0];
            }

        }
        LinkedList<Pheromone> nearby_circle =  new LinkedList<Pheromone>();
        for(Pheromone p: pheromone_copie){
            if (p == null ){
                continue;
            }
            int diff = (int) p.distance(this.getX(),this.getY());
            if ( diff  <= distance && diff>0 ){
                nearby_circle.add(p);
            }
        }

        return nearby_circle.toArray(new Pheromone[nearby_circle.size()]);
    }
    /**
     * 
     * @param next the next point
     * @return  returns the angle between the ant and the next points
     *  first by chaging the plan center to the ant and then by taking in consideration
     * that the interface shows negative side of the Y axis
     */
    public double angleNexStep(Point2D next){
        Point2D now=new Point2D.Double(this.getX(),-this.getY());
        Point2D newnext=new Point2D.Double(next.getX()-now.getX(),-next.getY()+now.getY());

        double angle1 = Math.acos(newnext.getX()/newnext.distance(new Point(0,0)));
        if (-this.getY() > -next.getY()) {
            return (-1)*angle1;
        }
        else {
            return angle1;
        }
    }
    /**
     * Gets the pheromones that are only ahead of the ant
     * @param pheromones
     * @return  list of pheromones ahead of the ant
     */
    public Pheromone [] getPhero_devant(Pheromone [] pheromones){
        ArrayList<Pheromone> list=new ArrayList<Pheromone>();
        Point2D peek = path.peek();
        for (Pheromone e:pheromones){
            if(peek.distance(e)>step){
                list.add(e);
            }
        }
        return list.toArray(new Pheromone[list.size()]);
    }
    /**
     * Gets the weighted mean point
     * @param tab list of pheromoes (points with weights)
     * @param debut beginning index
     * @param fin   end index
     * @return returns the weighted mean point(Pheromone)
     */
    public Pheromone get_emplacement_moyenne_phero(Pheromone [] tab,int debut,int fin){
        double x=0;
        double y=0;
        double somme=0;
        for (int i=debut;i<fin;i++){
            x+=tab[i].x*tab[i].getIntensite();
            y+=tab[i].y*tab[i].getIntensite();
            somme+=tab[i].getIntensite();
        }
        x=x/somme;
        y=y/somme;
        return new Pheromone(x,y,somme/(fin-debut));
    }
    public void addToPath(){
        for (Point2D p : this.path){
            if (p.distance(new Point2D.Double(this.getX(),this.getY())) < this.step){
                return;
            }
        }
        this.path.add(new Point2D.Double(this.getX(),this.getY()));
    }
    /**
     * Checks of next crosses the map
     * @param obstacle obstacle list
     * @param next point to go to
     * @return false if it crosses and true if it does not
     */
    public boolean isCrossingBorders (LinkedList<Barrier> obstacle, Point2D next) {
        for(int i =0;i<4;i++) {
            Point2D.Double now=new Point2D.Double(this.getX(),this.getY());
            if (obstacle.get(i).isCrossing(now, next)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks of next crosses any obstacles from obstacles
     * @param obstacle obstacle list
     * @param next point to go to
     * @return false if it crosses and true if it does not
     */
    public boolean isCrossingAny (LinkedList<Barrier> obstacle, Point2D next) {
        for(Barrier e:obstacle) {
            Point2D.Double now=new Point2D.Double(this.getX(),this.getY());
            if (e.isCrossing(now, next)) {
                return false;
            }
        }
        return true;
    }
}