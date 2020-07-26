/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * <Nafeezur Chowdhury>
 * <nrc865>
 * <75535>
 * <Pranesh Satish>
 * <ps32534>
 * <75535>
 * Slip days used: <0>
 * Spring 2019
 */

package assignment5;

import javafx.*; // change later to what we need
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import assignment5.Critter;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

    /* START --- NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    /**
     * checks if a space is occupied based on direction
     * and number of steps in that direction
     *
     * @param direction
     * @param steps
     */
    protected final String look(int direction, boolean steps) {

        int tempx = this.x_coord;
        int tempy = this.y_coord;

        switch(direction) {
          case 0:
            if(!steps) {
              tempx++;
            }
            else if(steps) {
              tempx++;
              tempx++;
            }
            break;
          case 1:
            if(!steps) {
              tempx++;
              tempy++;
            }
            else if(steps) {
              tempx++;
              tempx++;
              tempy++;
              tempy++;
            }
            break;
          case 2:
            if(!steps) {
              tempy++;
            }
            else if(steps) {
              tempy++;
              tempy++;
            }
            break;
          case 3:
            if(!steps) {
              tempx--;
              tempy++;
            }
            else if(steps) {
              tempx--;
              tempx--;
              tempy++;
              tempy++;
            }
            break;
          case 4:
            if(!steps) {
              tempx--;
            }
            if(steps) {
              tempx--;
              tempx--;
            }
            break;
          case 5:
            if(!steps) {
              tempx--;
              tempy--;
            }
            else if(steps) {
              tempx--;
              tempx--;
              tempy--;
              tempy--;
            }
            break;
          case 6:
            if(!steps) {
              tempy--;
            }
            else if(steps) {
              tempy--;
              tempy--;
            }
            break;
          case 7:
            if(!steps) {
              tempx++;
              tempy--;
            }
            else if(steps) {
              tempx++;
              tempx++;
              tempy--;
              tempy--;
            }
            break;
          default: break;
        }

        if(tempx == this.x_coord && tempy == this.y_coord)
          return null;
        return occBy(tempx, tempy);

    }

    /**
     * goes through population and checks if a Critter
     * occupies a specific coordinate from look
     *
     * @param x
     * @param y
     */
    private String occBy(int x, int y) {

      for(int i = 0; i < population.size(); i++) {
        Critter c = population.get(i);
        if(x == c.x_coord && y == c.y_coord)
          return c.toString();
      }

      return null;

    }

    public static String runStats(List<Critter> critters) {
        // TODO Implement this method
    	String stats = new String ("" + critters.size() + " critters as follows -- " + critters.get(0).toString() +
    			":" + critters.size());
        return stats;
    }


    public static void displayWorld() {
        // TODO Implement this method
    	Main.critWorld.getChildren().clear();
    	for(Critter c: population) {
    		if(Main.critWorld.contains((double)c.x_coord, (double)c.y_coord)) {
    			continue;
    		} else {
    			place(c.viewShape(), c.viewOutlineColor(), c.viewFillColor(), c.x_coord, c.y_coord);
    		}
    	}
    	Main.critWorld.setGridLinesVisible(true);
    }

	/* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */

    private int energy = 0;
    
    private Boolean moved = false;

    private int x_coord;
    private int y_coord;

    private static List<Critter> population = new ArrayList<Critter>();
    private static List<Critter> babies = new ArrayList<Critter>();

    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name)
          throws InvalidCritterException {
        // TODO: Complete this method
      Class<?> myCritter = null;
      Constructor<?> constructor = null;
      Object instanceOfMyCritter = null;

      try {
        myCritter = Class.forName(myPackage + "." + critter_class_name); 	// Class object of specified name
        constructor = myCritter.getConstructor();		// No-parameter constructor object
        instanceOfMyCritter = constructor.newInstance();	// Create new object using constructor

        Critter c = (Critter) instanceOfMyCritter;

        c.energy = Params.START_ENERGY;
        c.x_coord = getRandomInt(Params.WORLD_WIDTH);
        c.y_coord = getRandomInt(Params.WORLD_HEIGHT);

        population.add(c);
      } catch (Exception e) {
        throw new InvalidCritterException(critter_class_name);
      }
    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.
     *                           Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name)
            throws InvalidCritterException {
        // TODO: Complete this method
        Class<?> myCritter = null;
		    Constructor<?> constructor = null;
		    Object instanceOfMyCritter = null;
		    Critter c;
		    List<Critter> instances = new ArrayList<Critter>();

		    try {
			     myCritter = Class.forName(myPackage + "." + critter_class_name); 	// Class object of specified name
		       constructor = myCritter.getConstructor();		// No-parameter constructor object
			     instanceOfMyCritter = constructor.newInstance();	// Create new object using constructor

			     c = (Critter) instanceOfMyCritter;
		    } catch (Exception e) {
			     throw new InvalidCritterException(critter_class_name);
        }

		    for(int i = 0; i < population.size(); i++) {
			     if(population.get(i).getClass().equals(c.getClass())) {
				       instances.add(population.get(i));
			     }
		    }

        return instances;
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        // TODO: Complete this method
        population.clear();
        babies.clear();

    }

    /**
     * Handles all time step for population
     * Adds new babies to population
     * Calculates remaining energy for population
     * Removes dead critters
     * Refreshes clovers
     */
     public static void worldTimeStep() {
         // TODO: Complete this method
         for(int i = 0; i < population.size(); i++) { // do time step for all critters
             population.get(i).doTimeStep();
         }
         doEncounters(); // resolve fights in encounters
         population.addAll(babies); // add new babies to population the reset
         babies.clear();
         for(int i = 0; i < population.size(); i++) { // calculate remaining energy
             population.get(i).energy -= Params.REST_ENERGY_COST;
         }
         Iterator<Critter> criterator = population.iterator();
         while(criterator.hasNext()) { // remove dead critters
             if(criterator.next().energy < 1) {
                 criterator.remove();
             }
         }
         for(int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++) { // refresh clover population
             Clover c = new Clover();
             c.setEnergy(Params.START_ENERGY);
             int x = Critter.getRandomInt(Params.WORLD_WIDTH);
             int y = Critter.getRandomInt(Params.WORLD_HEIGHT);
             c.setX_coord(x);
             c.setY_coord(y);
             population.add(c);
         }

     }

     /**
      * Handles fight encounters
      * Find results of energy left after a critter flees
      * Find dead critter after a fight
      */
      private static void doEncounters() {

        for(int i = 0; i < population.size(); i++) {
          Critter c1 = population.get(i); int x1 = c1.x_coord; int y1 = c1.y_coord;
          if(c1.energy < 1) { // check if critter should already be dead
            continue; // if dead check next critter
          }
          for(int j = i + 1; j < population.size(); j++) {
            Critter c2 = population.get(j); int x2 = c2.x_coord; int y2 = c2.y_coord;
            if(c2.energy < 1) { // same as above
                continue;
            }
            if(x1 == x2 && y1 == y2) {
                int d1 = 0;
                int d2 = 0;
                if(c1.fight(c2.toString())) {
                    d1 = getRandomInt(c1.energy);
                }
                if(c2.fight(c1.toString())) {
                    d2 = getRandomInt(c2.energy);
                }
                if(c1.x_coord != x1 || c1.y_coord != y1) {  // change to different critter
                    if(canMove(c1)) {
                        break;
                    }
                    else {
                        c1.x_coord = x1;
                        c1.y_coord = y1;
                    }
                }
                if(c2.x_coord != x2 || c2.y_coord != y2) { // change to different critter
                    if(canMove(c2)) {
                        if(c2.x_coord == c1.x_coord && c2.y_coord == c1.y_coord) {
                            c2.x_coord = x2;
                            c2.y_coord = y2;
                        }
                        else {
                            continue;
                        }
                    }	else {
                        c2.x_coord = x2;
                        c2.y_coord = y2;
                    }
                }
                if(d1 >= d2)  { // arbitrarily pick c1 as winner if equal damage rolls
                    c1.energy += c2.energy/2;
                    c2.energy = 0;
                    population.remove(c2); // dead from fight so remove in this step
                }
                else if(d1 < d2) { // c1 dead
                    c2.energy += c1.energy/2;
                    c1.energy = 0;
                    population.remove(c1); // dead from fight so remove in this step;
                    break;
                }
              }
            }
          }

        }

        /**
         * Checks if there is already a critter
         * at a space a critter is fleeing to
         * Returns true if the space is empty
         * False if the space is occupied
         */
         private static boolean canMove(Critter c) {

           boolean cM = true;
           for(int i = 0; i < population.size(); i++) {
             Critter cc = population.get(i);
             if(c.equals(cc)) {
               continue;
             }
             if(c.x_coord == cc.x_coord && c.y_coord == cc.y_coord) {
               cM = false;
             }
           }
           return cM;

         }

    public abstract void doTimeStep();

    public abstract boolean fight(String oponent);

    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }

    /**
     * using the direction from the parameter the walk method calls the move method once
     * and deducts the respective energy from the critter
     * @param direction
     */
    protected final void walk(int direction) {
        // TODO: Complete this method
    	this.move(direction);
    	this.energy = this.energy - Params.WALK_ENERGY_COST;
    }
    /**
     * using the direction from the parameter the walk method calls the move method twice
     * and deducts the respective energy from the critter
     * @param direction
     */
    protected final void run(int direction) {
        // TODO: Complete this method
    	this.move(direction);
    	this.move(direction);
    	this.energy = this.energy - Params.RUN_ENERGY_COST;
    }
    /**
     * the move method is called by either the walk or run methods, which input a specific direction
     * for the critter to move in. This method uses a switch statement to decide which way to move.
     * However if the critter already moved in that time step it shouldn't move again. And since the world
     * is in a torus shape, if the coordinate surpass their domain they go back to the opposide end
     * @param direction
     */
   protected final void move(int direction) {
    	if(moved) {
    		return;
    	}

    	switch(direction) {
    	case 0:
    		this.x_coord++;
    		break;
    	case 1:
    		this.x_coord++;
    		this.y_coord++;
    		break;
    	case 2:
    		this.y_coord++;
    		break;
    	case 3:
    		this.x_coord--;
    		this.y_coord++;
    		break;
    	case 4:
    		this.x_coord--;
    		break;
    	case 5:
    		this.x_coord--;
    		this.y_coord--;
    		break;
    	case 6:
    		this.y_coord--;
    		break;
    	case 7:
    		this.x_coord++;
    		this.y_coord--;
    		break;
    	default:
    		break;
    	}

    	if(this.x_coord < 0) {
    		this.x_coord = Params.WORLD_WIDTH - 1;
    	}

    	if(this.x_coord >= Params.WORLD_WIDTH) {
    		this.x_coord = 0;
    	}

    	if(this.y_coord < 0) {
    		this.y_coord = Params.WORLD_HEIGHT - 1;
    	}

    	if(this.y_coord >= Params.WORLD_HEIGHT) {
    		this.y_coord = 0;
    	}

    	this.moved = true;
    }

   /**
    * The reproduce function checks to see if the critter has enough energy to reproduce.
    * If it does then it will give half of it's energy to the offspring and place it somewhere within it's radius.
    * But the offspring cannot join the population in this timeStep so it is added to the baby list
    * @param offspring
    * @param direction
    */
    protected final void reproduce(Critter offspring, int direction) {
        // TODO: Complete this method
    	if(this.energy < Params.MIN_REPRODUCE_ENERGY) {
    		return;
    	}

    	offspring.energy = (int) Math.floor(this.energy/2);
    	this.energy = this.energy - offspring.energy;

    	offspring.x_coord = this.x_coord;
    	offspring.y_coord = this.y_coord;

    	offspring.move(direction);

    	babies.add(offspring);
    }
    
    private static void place(CritterShape shape, Color outline, Color fill, int x, int y) {
		Shape crit = null;
		int size = 3;
		
		switch(shape) {
		case CIRCLE:
			crit = new Circle(size/2);
			crit.setFill(fill);
			crit.setStroke(outline);
			break;
		case SQUARE:
			crit = new Rectangle(size,size);
			crit.setFill(fill);
			crit.setStroke(outline);
			break;
		case TRIANGLE:
			Polygon triangle = new Polygon();
			triangle.getPoints().addAll(new Double[]{
					(double)size/2.0, 1.0,
				    (double)size - 1.0, (double)size - 1.0,
				    1.0, (double)size-1.0
			});
			crit = triangle;
			crit.setFill(fill);
			crit.setStroke(outline);
			break;
		case DIAMOND:
			Polygon diamond = new Polygon();
			diamond.getPoints().addAll(new Double[]{
					(double)size/2.0, 1.0,
					(double)size - 1.0, (double)size/2.0,
					(double)size/2.0, (double)size - 1.0,
					1.0, (double)size/2.0
			});
			crit = diamond;
			crit.setFill(fill);
			crit.setStroke(outline);
			break;
		case STAR:
			Polygon star = new Polygon();
			
			Double[] points = {2050.0, 1500.0, 2170.0, 1860.0, 2590.0, 1860.0, 
				      2230.0, 2040.0, 2330.0, 2460.0, 2050.0, 2220.0, 1770.0, 2460.0, 1870.0, 2040.0, 
				      1510.0, 1860.0, 1930.0, 1860.0};
			if(size <= 6) {
				for(int i = 0; i < points.length; i++) {
					points[i] = points[i]/(45*size^2);
				}
			}
			else {
				for(int i = 0; i < points.length; i++) {
					points[i] = points[i]/(size*7);
				}
			}
			 star.getPoints().addAll( points );
			 crit=star;
			 crit.setFill(fill);
			 crit.setStroke(outline);
			 break;
		default:
			break;
		}
		
		
		Main.critWorld.add(crit, x, y);
		return;
    }


    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }
}
