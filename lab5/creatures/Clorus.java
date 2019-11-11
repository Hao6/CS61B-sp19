package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
//
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

/**
 * @ClassName Clorus
 * @Description TODO
 * @Author hao6
 * @Data 10/30/19 9:37 PM
 * @Version 1.0
 **/
public class Clorus extends Creature {
    private double energy;

    private int r = 34;
    /**
     * green color.
     */
    private int g = 0;
    /**
     * blue color.
     */
    private int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /**
     * Called when this creature moves.
     */

    @Override
    public Color color() {
        return color(r, g, b);
    }

    @Override
    public void move() {
        energy -= 0.03;
        if (energy < 0.0) {
            energy = 0.0;
        }
    }

    /**
     * Called when this creature attacks C.
     */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Called when this creature chooses replicate.
     * Must return a creature of the same type.
     */
    @Override
    public Creature replicate() {
        energy = energy / 2;
        return new Clorus(energy);
    }

    /**
     * Called when this creature chooses stay.
     */
    @Override
    public void stay() {
        energy -= 0.01;
        if (energy < 0) {
            energy = 0;
        }
    }

    /**
     * Returns an action. The creature is provided information about its
     * immediate NEIGHBORS with which to make a decision.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if (entry.getValue().name().equals("empty")) {
                emptyNeighbors.add(entry.getKey());
            } else if (entry.getValue().name().equals("plip")) {
                plipNeighbors.add(entry.getKey());
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else {
            if (!plipNeighbors.isEmpty()) {
                return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
            } else if (energy >= 1.0) {
                return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
            }
        }
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
