package game.Actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.items.RefreshingFlask;

/**
 * An Action class representing the action of a player character consuming a RefreshingFlask item.
 * This action increases the actor's stamina and marks the RefreshingFlask as consumed.
 */
public class ConsumeRefreshingFlaskAction extends Action {

    /**
     * Executes the action of consuming a Healing Vial.
     *
     * @param actor The Actor (player character) performing the action.
     * @param map   The GameMap in which the action is performed.
     * @return A message describing the result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Get the actor's current stamina and maximum stamina attributes
        int currentStamina = actor.getAttribute(BaseActorAttributes.STAMINA);
        int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);

        // Calculate the number of stamina points to heal (20% of max stamina)
        int StaminaPoints = (int) Math.ceil(maxStamina * 0.2);

        // Ensure that increasing actor's stamina does not exceed maximum stamina
        if (currentStamina + StaminaPoints > maxStamina) {
            StaminaPoints = maxStamina - currentStamina;
        }

        // Heal the actor by the calculated stamina points
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, StaminaPoints);

        // Mark the RefreshingFlask item as consumed by the actor
        for (Item item : actor.getItemInventory()) {
            if (item instanceof RefreshingFlask) {
                item.addCapability(Status.CONSUMED);
                break; // Assuming only one RefreshingFlask can be consumed at a time
            }
        }

        // Return a message indicating the increased stamina amount
        return actor + " has his stamina increased by " + StaminaPoints + " points";
    }

    /**
     * Provides a description of the action for display in a menu.
     *
     * @param actor The Actor (player character) performing the action.
     * @return A menu description of the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes the RefreshingFlask";
    }
}