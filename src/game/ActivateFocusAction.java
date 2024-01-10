package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import game.Broadsword;

/**
 * An action class representing the activation of the "Focus" skill for the Broadsword weapon.
 */
public class ActivateFocusAction extends Action {
    private Broadsword broadsword;

    /**
     * Constructor for the ActivateFocusAction.
     *
     * @param broadsword The Broadsword instance associated with this action
     */
    public ActivateFocusAction(Broadsword broadsword) {
        this.broadsword = broadsword;
    }

    /**
     * Executes the action, activating the "Focus" skill and reducing player's stamina.
     *
     * @param actor The actor performing the action
     * @param map   The game map where the action takes place
     * @return A description of the action's outcome
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        broadsword.activate();

        // Reduce player's stamina by 20% of maximum stamina
        int maxStamina = actor.getAttributeMaximum(BaseActorAttributes.STAMINA);
        int staminaToReduce = (int) Math.ceil(maxStamina * 0.2); // 20% of maximum stamina
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaToReduce);

        return actor + " takes a deep breath and focuses all their might!";
    }

    /**
     * Provides a description of the action for display in the menu.
     *
     * @param actor The actor performing the action
     * @return A description of the action for display
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " activates the skill of Broadsword";}
}
