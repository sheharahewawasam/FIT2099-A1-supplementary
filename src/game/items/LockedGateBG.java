package game.items;

import java.util.List;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Actions.OpenLockedGateBGAction;
import game.Actions.Status;
import game.actors.Player;

/**
 * A class that represents a LockedGate ground.
 * LockedGate is a type of Ground that can only be entered if it has the UNLOCKED capability.
 * It provides actions to unlock the gate and transition to another map.
 * Created by:
 * Riordan D. Alfredo
 * Modified by:
 *
 * @see Ground
 */
public class LockedGateBG extends Ground {//New Locked Gate to Burial Ground Map created from Ancient Woods

    private Action mapTransitionAction;

    /**
     * Constructor for the LockedGate class.
     * Initializes a LockedGate object with the display character '='.
     */
    public LockedGateBG() {
        super('=');
    }// Symbol given for Gate which is =

    /**
     * Determines whether an actor can enter this LockedGate ground.
     *
     * @param actor The actor that wants to enter.
     * @return True if the gate has the UNLOCKED capability, allowing the actor to enter; otherwise, false.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return this.hasCapability(Status.UNLOCKED);
    }

    /**
     * Provides a list of allowable actions for an actor interacting with this LockedGate.
     *
     * @param actor     The actor interacting with the LockedGate.
     * @param location  The Location where this Ground is located.
     * @param direction String representing the direction of interaction.
     * @return An ActionList containing allowable actions based on actor's capabilities and gate's status.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        List<Exit> adjacentLocations = location.getExits();

        // Check each adjacent location for the presence of a player
        for (Exit exit : adjacentLocations) {
            if (exit.getDestination().containsAnActor()) {
                Actor actor1 = exit.getDestination().getActor();

                // Check if the actor is an instance of Player
                if (actor1 instanceof Player) {
                    //// Add the capability to indicate that the player is nearby the gate and whether the player can enter the gate or not.It also checks whether the key is present or not.
                    if (actor.hasCapability(Status.HAS_KEY) && !this.hasCapability(Status.UNLOCKED)) {
                        actions.add(new OpenLockedGateBGAction());

                    } else if (this.hasCapability(Status.UNLOCKED)) {
                        actions.add(mapTransitionAction);
                        break;
                    }
                }
            }
        }
        return actions;
    }
}