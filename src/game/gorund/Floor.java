package game.gorund;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.Actions.Ability;

/**
 * A class that represents the floor inside a building.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 *
 */
public class Floor extends Ground {
    /**
     * Constructor for the Floor class.
     */
    public Floor() {
        super('_');
    }// Floor is represented by the '_' character

    /**
     * Checks if an actor can enter the floor.
     *
     * @param actor the actor attempting to enter the floor
     * @return true if the actor has the capability to enter the floor, false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Ability.CAN_ENTER_FLOOR);
    }
}
