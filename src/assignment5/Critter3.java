package assignment5;

/*
 * Critter3
 * Critter3 cannot reproduce.
 * Critter3 is an absolute buffoon.
 * During fight encounters, it simply runs back and forth.
 * It will then attempt to fight whatever it's up against.
 * Due to its high expenditure of energy, Critter3 will phase out
 * as a result of natural selection.
 */

import java.util.List;

public class Critter3 extends Critter {

    @Override
    public String toString() {
        return "3";
    }

    private static final int GENE_TOTAL = 24;
    private int[] genes = new int[8];
    private int dir;

    public Critter3() {
        for (int k = 0; k < 8; k += 1) {
            genes[k] = GENE_TOTAL / 8;
        }
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        run(2);
        run(6);
        return true;
    }

    @Override
    public void doTimeStep() {
        /* take one step forward */
        int count = 0;
    	while(look(dir, false) == null || count != 5) {
        	count++;
        	dir = getRandomInt(8);
        }
    	walk(dir);

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

    public static String runStats(List<Critter> critter3s) {
        int total_straight = 0;
        int total_left = 0;
        int total_right = 0;
        int total_back = 0;
        for (Object obj : critter3s) {
            Critter3 c = (Critter3) obj;
            total_straight += c.genes[0];
            total_right += c.genes[1] + c.genes[2] + c.genes[3];
            total_back += c.genes[4];
            total_left += c.genes[5] + c.genes[6] + c.genes[7];
        }
        String str = critter3s.size() + " total critter3s    " +
                total_straight / (GENE_TOTAL * 0.01 * critter3s.size())
                + "% straight   " +
                total_back / (GENE_TOTAL * 0.01 * critter3s.size())
                + "% back   " + 
                total_right / (GENE_TOTAL * 0.01 * critter3s.size())
                + "% right   " + 
                total_left / (GENE_TOTAL * 0.01 * critter3s.size())
                + "% left   ";
        
        return str;
    }

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
	
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.BLANCHEDALMOND;
    }
}
