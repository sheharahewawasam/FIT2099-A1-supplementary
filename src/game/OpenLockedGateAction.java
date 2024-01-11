package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.LockedGate;
import game.Status;

import java.util.List;

/**
 * An Action class representing the action of an Actor opening a locked gate on a GameMap.
 * This action unlocks the gate by adding the UNLOCKED capability to the LockedGate ground.
 */
public class OpenLockedGateAction extends Action {

    /**
     * Executes the action of opening a locked gate.
     *
     * @param actor The Actor performing the action.
     * @param map   The GameMap in which the action is performed.
     * @return A message indicating the result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Get the list of exits from the current location of the actor
        List<Exit> exits = map.locationOf(actor).getExits();

        // Find the exit that leads to the LockedGate ground and unlock it
        for (Exit exit : exits) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.getGround() instanceof LockedGate) {
                // Add the UNLOCKED capability to the LockedGate ground
                adjacentLocation.getGround().addCapability(Status.UNLOCKED);
                break; // Assuming there is only one LockedGate to unlock
            }
        }

        // Return a message indicating that the locked gate is unlocked
        return "The locked gate is unlocked";
    }

    /**
     * Provides a description of the action for display in a menu.
     *
     * @param actor The Actor performing the action.
     * @return A menu description of the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " opens the locked gate";
    }
}