package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;

import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.HEALTH;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * Class representing the Player.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Player extends Actor {
    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Ability.CAN_ENTER_FLOOR);
        this.addAttribute(STAMINA, new BaseActorAttribute(100));
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        //Stamina gets recovered in each turn by 1% of their maximum stamina
        recoverStamina();

        display.println(this + "\n" + "Hitpoints: " + this.getAttribute(HEALTH) + "/" + this.getAttributeMaximum(HEALTH));
        display.println("Stamina: " + this.getAttribute(STAMINA) + "/" + this.getAttributeMaximum(STAMINA));
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }
    public void recoverStamina() {
        int maxStamina = this.getAttributeMaximum(STAMINA);
        int currentStamina = this.getAttribute(STAMINA);
        int staminaToRecover = maxStamina / 100; // 1% of maximum stamina

        if (currentStamina + staminaToRecover <= maxStamina) {
            this.modifyAttribute(STAMINA, ActorAttributeOperations.INCREASE, staminaToRecover);
        } else {
            this.modifyAttribute(STAMINA, ActorAttributeOperations.UPDATE, maxStamina);
        }
    }

}
