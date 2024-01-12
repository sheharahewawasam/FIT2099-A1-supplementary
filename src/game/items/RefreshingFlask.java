package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import game.Actions.ConsumeRefreshingFlaskAction;
import game.Actions.Status;

public class RefreshingFlask extends Item {
    /**
     * Constructor for the OldKey class.
     * Initializes an Old Key object with the name "Old Key", display character 'u', and portable as true.
     */
    public RefreshingFlask() {
        super("RefreshingFlask", 'u', true);
    }

    /**
     * Retrieves the PickUpAction for this Old Key when an actor tries to pick it up.
     *
     * @param actor The actor attempting to pick up the Old Key.
     * @return A PickUpAction if the actor is allowed to pick up the Old Key, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        for (Item item : actor.getItemInventory()) {
            if (item instanceof RefreshingFlask) {
                return null; // Actor cannot pick up another RefreshingFlask if they already have one.
            }
        }

        if (this.hasCapability(Status.CONSUMED)) {
            return null; // Actor cannot pick up a consumed RefreshingFlask.
        }

        actor.addCapability(Status.HAS_REFRESHING_FLASK);
        return new PickUpAction(this);
    }
    @Override
    public DropAction getDropAction(Actor actor) {
        if (this.hasCapability(Status.CONSUMED)) {
            actor.removeCapability(Status.HAS_REFRESHING_FLASK);
            return new DropAction(this);
        }
        return null; // Actor cannot drop a consumed RefreshingFlask.
    }

    /**
     * Retrieves a list of allowable actions for this RefreshingFlask for the specified owner actor.
     *
     * @param owner The actor that owns this RefreshingFlask.
     * @return A list of allowable actions, including the option to consume the RefreshingFlask if it's in the inventory and not consumed.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        int currentStamina = owner.getAttribute(BaseActorAttributes.STAMINA);
        int maxStamina = owner.getAttributeMaximum(BaseActorAttributes.STAMINA);

        for (Item item : owner.getItemInventory()) {
            if (item instanceof RefreshingFlask && !item.hasCapability(Status.CONSUMED)) {
                if (currentStamina < maxStamina) {
                    actions.add(new ConsumeRefreshingFlaskAction());
                    break;
                }
            }
        }

        return actions;
    }
}
