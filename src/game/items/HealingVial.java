package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import game.Actions.ConsumeHealingVialAction;
import game.Actions.Status;

public class HealingVial extends Item {
    /**
     * Constructor for the HealingVial class.
     * Initializes a Healing Vial object with the name "Healing Vial", display character 'a', and stackable as true.
     */
    public HealingVial() {
        super("HealingVial", 'a', true);
    }

    /**
     * Retrieves the PickUpAction for this Healing Vial when an actor tries to pick it up.
     *
     * @param actor The actor attempting to pick up the Healing Vial.
     * @return A PickUpAction if the actor is allowed to pick up the Healing Vial, null otherwise.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        for (Item item : actor.getItemInventory()) {
            if (item instanceof HealingVial) {
                return null; // Actor cannot pick up another Healing Vial if they already have one.
            }
        }

        if (this.hasCapability(Status.CONSUMED)) {
            return null; // Actor cannot pick up a consumed Healing Vial.
        }

        actor.addCapability(Status.HAS_HEALING_VIAL);
        return new PickUpAction(this);
    }
    @Override
    public DropAction getDropAction(Actor actor) {
        if (this.hasCapability(Status.CONSUMED)) {
            actor.removeCapability(Status.HAS_HEALING_VIAL);
            return new DropAction(this);
        }
        return null; // Actor cannot drop a consumed Healing Vial.
    }

    /**
     * Retrieves a list of allowable actions for this Healing Vial for the specified owner actor.
     *
     * @param owner The actor that owns this Healing Vial.
     * @return A list of allowable actions, including the option to consume the Healing Vial if it's in the inventory and not consumed.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        int currentHealth = owner.getAttribute(BaseActorAttributes.HEALTH);
        int maxHealth = owner.getAttributeMaximum(BaseActorAttributes.HEALTH);

        for (Item item : owner.getItemInventory()) {
            if (item instanceof HealingVial && !item.hasCapability(Status.CONSUMED)) {
                if (currentHealth < maxHealth) {
                    actions.add(new ConsumeHealingVialAction());
                    break;
                }
            }
        }

        return actions;
    }

}
