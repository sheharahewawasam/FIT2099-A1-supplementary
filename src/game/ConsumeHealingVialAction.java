package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.HealingVial;
import game.Status;

/**
 * An Action class representing the action of a player character consuming a Healing Vial item.
 * This action heals the actor and marks the Healing Vial as consumed.
 */
public class ConsumeHealingVialAction extends Action {

    /**
     * Executes the action of consuming a Healing Vial.
     *
     * @param actor The Actor (player character) performing the action.
     * @param map   The GameMap in which the action is performed.
     * @return A message describing the result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Get the actor's current health and maximum health attributes
        int currentHealth = actor.getAttribute(BaseActorAttributes.HEALTH);
        int maxHealth = actor.getAttributeMaximum(BaseActorAttributes.HEALTH);

        // Calculate the number of health points to heal (10% of max health)
        int healthPoints = (int) Math.ceil(maxHealth * 0.1);

        // Ensure that healing does not exceed maximum health
        if (currentHealth + healthPoints > maxHealth) {
            healthPoints = maxHealth - currentHealth;
        }

        // Heal the actor by the calculated health points
        actor.heal(healthPoints);

        // Mark the Healing Vial item as consumed by the actor
        for (Item item : actor.getItemInventory()) {
            if (item instanceof HealingVial) {
                item.addCapability(Status.CONSUMED);
                break; // Assuming only one Healing Vial can be consumed at a time
            }
        }

        // Return a message indicating the healing amount
        return actor + " is healed by " + healthPoints + " points";
    }

    /**
     * Provides a description of the action for display in a menu.
     *
     * @param actor The Actor (player character) performing the action.
     * @return A menu description of the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes the Healing Vial";
    }
}