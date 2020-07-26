package assignment5;

/*
 * Critter4
 * This critter will resemble the natural prey.
 * It is likely to fall victim to the apex predators: the Goblins.
 * Its natural instinct is to flee at the sight of danger,
 * much akin to a deer or a peasant.
 * Since it isn't a fighter, it was balanced with stronger reproduction.
 */

import java.util.List;

public class Critter4 extends Critter {

    @Override
    public String toString() {
        return "4";
    }

    private static final int GENE_TOTAL = 24;
    private int[] genes = new int[8];
    private int dir;

    public Critter4() {
        for (int k = 0; k < 8; k += 1) {
            genes[k] = GENE_TOTAL / 8;
        }
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        run(getRandomInt(8));
        return false;
    }

    @Override
    public void doTimeStep() {
        /* take one step forward */
    	if(look(dir, false).equals("Critter3")) {
    		dir = getRandomInt(8);
    	}
        walk(dir);

        if (getEnergy() > 120) {
            Critter4 child = new Critter4();
            for (int k = 0; k < 8; k += 1) {
                child.genes[k] = this.genes[k];
            }
            int g = Critter.getRandomInt(8);
            while (child.genes[g] == 0) {
                g = Critter.getRandomInt(8);
            }
            child.genes[g] -= 1;
            g = Critter.getRandomInt(8);
            child.genes[g] += 1;
            reproduce(child, Critter.getRandomInt(8));
        }

        /* pick a new direction based on our genes */
        int roll = Critter.getRandomInt(GENE_TOTAL);
        int turn = 0;
        while (genes[turn] <= roll) {
            roll = roll - genes[turn];
            turn = turn + 1;
        }
        assert (turn < 8);

        dir = (dir + turn) % 8;
    }

    public static String runStats(List<Critter> critter4s) {
        int total_straight = 0;
        int total_left = 0;
        int total_right = 0;
        int total_back = 0;
        for (Object obj : critter4s) {
            Critter4 c = (Critter4) obj;
            total_straight += c.genes[0];
            total_right += c.genes[1] + c.genes[2] + c.genes[3];
            total_back += c.genes[4];
            total_left += c.genes[5] + c.genes[6] + c.genes[7];
        }
        String str = critter4s.size() + " total critter4s    " +
                total_straight / (GENE_TOTAL * 0.01 * critter4s.size())
                + "% straight   " +
                total_back / (GENE_TOTAL * 0.01 * critter4s.size())
                + "% back   " +
                total_right / (GENE_TOTAL * 0.01 * critter4s.size())
                + "% right   " +
                total_left / (GENE_TOTAL * 0.01 * critter4s.size())
                + "% left   ";
        
        return str;
    }

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	
	@Override
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.ORANGERED;
    }
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.CYAN;
    }
}