package game.Actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for defining defeat-related rewards when an actor is defeated (e.g., an enemy).
 * Implementing classes can specify custom rewards or actions to be taken when an actor is defeated.
 */
public interface DefeatRewarder {

    /**
     * Called when the implementing actor is defeated (e.g., when an enemy is defeated).
     *
     * @param actor The defeated Actor (e.g., the enemy).
     * @param map   The GameMap in which the defeat occurs.
     * @return A message describing the reward or action taken upon defeat.
     */
    String onDeath(Actor actor, GameMap map);
}
