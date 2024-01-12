package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Actions.AttackAction;
import game.actors.Player;

/**
 * A Behaviour class representing the behavior of an actor to attack the player if nearby.
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Returns an action to attack the player if the player is nearby (within one block).
     *
     * @param actor The Actor performing this behavior.
     * @param map   The GameMap in which the actor exists.
     * @return An AttackAction if the player is nearby, null otherwise.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);

        // Check if the player is nearby (within one block)
        if (isPlayerNearby(actorLocation)) {
            // Return an action to attack the player
            Actor target = findPlayerInMap(map);
            if (target != null) {
                return new AttackAction(target, map.locationOf(target).toString());
            }
        }

        return null;
    }

    /**
     * Checks if the player is nearby (within one block) of the given location on the map.
     *
     * @param location the location to check.
     * @return true if the player is nearby, false otherwise.
     */
    private boolean isPlayerNearby(Location location) {
        for (Exit exit : location.getExits()) {
            Location adjacentLocation = exit.getDestination();
            if (adjacentLocation.containsAnActor() && adjacentLocation.getActor() instanceof Player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and returns the Player actor on the given GameMap if present.
     *
     * @param map The GameMap to search for the Player.
     * @return The Player actor if found, null if not found.
     */
    public static Player findPlayerInMap(GameMap map) {
        for (int x = 0; x < map.getXRange().max(); x++) {
            for (int y = 0; y < map.getYRange().max(); y++) {
                Location location = map.at(x, y);
                if (location.containsAnActor() && location.getActor() instanceof Player) {
                    return (Player) location.getActor();
                }
            }
        }
        return null; // Player not found on the map
    }
}