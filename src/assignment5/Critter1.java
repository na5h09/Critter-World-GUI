package assignment5;

/**
 * This Critter1 resembles an Orc
 * The Orc reproduces on encounter for a fight to increase numbers if fight is lost
 * They run usually when have enough energy, but slow down as energy decreases
 * this speed however allows them to avoid conflict with themselves
 * @author 19728
 *
 */
public class Critter1 extends Critter {
	@Override
    public String toString() {
        return "1";
    }

    private int dir;

    public Critter1() {
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
    	for(int i = 0; i < 3; i++) {
    		Critter1 child = new Critter1(); //Orcs reproduce when fighting
        	reproduce(child, i);
    	}
        return true;
    }

    @Override
    public void doTimeStep() {
        /* take one step forward */
    	if(look(dir, false).equals("Critter4")) {
    		dir = getRandomInt(8);
    	}
        if(this.getEnergy() < 20) {
        	walk(dir);
        } else {
        	run(dir);
        }
        
        dir = Critter.getRandomInt(8); //randomize next direction
    }

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}
	
	@Override
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.BLUEVIOLET;
    }
}
