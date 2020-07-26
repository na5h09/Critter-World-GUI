package assignment5;
/*
 * CRITTERS Critter2.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * <Pranesh Satish>
 * <ps32534>
 * <Student1 5-digit Unique No.>
 * <Nafeezur Chowdhury>
 * <nrc865>
 * <Student2 5-digit Unique No.>
 * Slip days used: <1> (Second of our Three)
 * Spring 2020
 */

import java.util.List;

/**
 * This Critter2 extends Critter except it almost never fights if it's energy is high enough
 * The critter practically teleport if the energy is over 50
 * This critter is also divided up between spotted ones and plain ones
 * When runStats is called it will write the amount of spotted and plain critters there are
 * @author 19728
 *
 */
public class Critter2 extends Critter {
	@Override
    public String toString() {
        return "2";
    }

    private int dir;
    Boolean spotted;

    public Critter2() {
        dir = Critter.getRandomInt(8);
        int random = Critter.getRandomInt(10);
        
        if(random >= 5) {
        	spotted = true;
        } else {
        	spotted = false;
        }
    }

    public boolean fight(String not_used) {
    	if(this.getEnergy() > 50) {
    		for(int j = 0; j < 3; j++) {
    			for(int i = 0; i < 4; i++) {
    				if(look(dir, false).equals("Critter1")) {
    					run(dir);
    				} else {
    					walk(dir);
    				}
        		}
    			dir = Critter.getRandomInt(8);
    		}
    	} else {
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public void doTimeStep() {
        /* take one step forward */
        if(this.getEnergy() < 20) {
        	walk(dir);
        } else {
        	run(dir);
        }
        
        dir = Critter.getRandomInt(8); //randomize next direction
    }
    
    public static String runStats(List<Critter> cheets) {
        int spotted = 0;
        int plain = 0;
        for (Object obj : cheets) {
            Critter2 c = (Critter2) obj;
            if(c.spotted) {
            	spotted++;
            } else {
            	plain++;
            }
        }
        String str = cheets.size() + " total Critter2    " +
        spotted + " spotted  " + plain + " plain";
        
        return str;
    }

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
	
	@Override
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.LIGHTGOLDENRODYELLOW;
    }
}
