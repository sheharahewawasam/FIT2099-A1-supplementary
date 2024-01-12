package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import game.Actions.Status;

/**
 * A class that represents an Old Key item.
 * Old Keys can be picked up by actors and are used to unlock certain locked gates.
 * Created by:
 * Riordan D. Alfredo
 * Modified by:
 *
 * @see Item
 */
public class OldKey extends Item {

    /**
     * Constructor for the OldKey class.
     * Initializes an Old Key object with the name "Old Key", display character '-', and stackable as true.
     */
    public OldKey() {
        super("Old Key", '-', true);
    }// Old Key is represented by the '-' character and is portable

    /**
     * Retrieves the PickUpAction for this Old Key when an actor tries to pick it up.
     *
     * @param actor The actor attempting to pick up the Old Key.
     * @return A PickUpAction if the actor is allowed to pick up the Old Key, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        actor.addCapability(Status.HAS_KEY);// Add the 'HAS_KEY' capability to the actor
        return super.getPickUpAction(actor);
    }

    /**
     * Retrieves the DropAction for this Old Key when an actor tries to drop it.
     *
     * @param actor The actor attempting to drop the Old Key.
     * @return Always returns null because Old Keys cannot be dropped once picked up.
     */
    @Override
    public DropAction getDropAction(Actor actor) {
        return null; // Old Keys cannot be dropped once picked up.
    }
}
