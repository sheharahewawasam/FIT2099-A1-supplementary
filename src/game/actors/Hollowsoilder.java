package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Actions.AttackAction;
import game.Actions.DefeatRewarder;
import game.items.HealingVial;
import game.Actions.Status;
import game.behaviour.AttackBehaviour;
import game.behaviour.WanderBehaviour;
import game.items.RefreshingFlask;

import java.util.HashMap;
import java.util.Map;

public class Hollowsoilder extends Actor implements DefeatRewarder {
    private Map<Integer, Behaviour> behaviours = new HashMap<>();

    public Hollowsoilder() {
        super("Hollowsoilder", '&', 200);
        this.behaviours.put(999, new WanderBehaviour());
        this.behaviours.put(998, new AttackBehaviour());
    }

    /**
     * At each turn, select a valid action to perform.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * The wandering undead can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {//Enemy damage to player
        return new IntrinsicWeapon(50, "kicks", 50);// public IntrinsicWeapon(int damage, String verb, int hit rate(accuracy))
    }
    @Override
    public String onDeath(Actor actor, GameMap map) {
        Location targetLocation = map.locationOf(this);
        String message = "";

        if (Math.random() < 0.20) {
            // 20% chance: Drop a healing vial (assuming you have a HealingVial class)
            Item healingVial = new HealingVial();
            targetLocation.addItem(healingVial);
            message += healingVial + " has been dropped!";
        }

        if (Math.random() < 0.30) {
            // 30% chance: Drop a refreshingflask
            Item refreshingflask = new RefreshingFlask();
            targetLocation.addItem(refreshingflask);
            message += refreshingflask + " has been dropped!";
        }

        return this.unconscious(actor, map) + "\n" + message;
    }
}
