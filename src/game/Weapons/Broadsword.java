package game.Weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Actions.ActivateFocusAction;
import game.Actions.AttackAction;

public class Broadsword extends WeaponItem {
    // Constants for Broadsword properties
    private static final String NAME = "Broadsword";
    private static final char DISPLAY_CHAR = '1';
    private static final int DEFAULT_DAMAGE = 110;
    private static final String VERB = "zaps";
    private static final int DEFAULT_HIT_RATE = 80;
    // Properties specific to Broadsword
    private float damageMultiplier ;

    private static final int SKILL_TURNS_DURATION = 6;

    public boolean isActive ; // Flag to track if the skill is currently active
    private int skillTurnsLeft; // Number of turns the skill is still active
    /**
     * Constructor.
     *
     */
    public Broadsword() {
        super(NAME, DISPLAY_CHAR, DEFAULT_DAMAGE, VERB, DEFAULT_HIT_RATE);
        this.damageMultiplier=1.0F;
        this.isActive=false;
    }
    /**
     * Activates the "Focus" skill of Broadsword.
     */
    public void activate(){
        this.isActive = true;
        resetSkillTurnsLeft();
        this.increaseDamageMultiplier(0.10f);;
    }
    /**
     * Deactivates the "Focus" skill of Broadsword.
     */
    public void deactivate(){
        this.isActive=false;
        this.skillTurnsLeft = 0; // Reset skill turns left
        updateDamageMultiplier(1.0F);
    }
    /**
     * Calculates the damage based on the damage multiplier.
     */
    @Override
    public int damage(){return Math.round(DEFAULT_DAMAGE * damageMultiplier);}
    /**
     * Updates the damage multiplier to a new value.
     */
    @Override
    public void updateDamageMultiplier(float newDamageMultiplier) {
        this.damageMultiplier = newDamageMultiplier;
    }
    /**
     * Generates a list of allowable actions for Broadsword when interacting with other actors at a location.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();
        actions.add(new AttackAction(otherActor, location.toString(), this)); // Add attack action
        return actions; // Return the list of allowable actions
    }

    /**
     * Generates a list of allowable actions for Broadsword when owned by an actor.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();
        actions.add(new ActivateFocusAction(this));
        return actions;
    }
    /**
     * Updates the state of Broadsword on each game tick, decreasing skill turns left and deactivating the skill if its duration expires.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (this.isActive) {
            this.skillTurnsLeft -= 1; // Decrease skill turns left
            System.out.println(this.skillTurnsLeft); // Debug: Print skill turns left
        }
        if (skillTurnsLeft == 0) {
            this.deactivate(); // Deactivate the skill when its duration expires
        }
    }
    /**
     * Increases the damage multiplier by a specified value.
     */
    @Override
    public void increaseDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier += damageMultiplier; // Increase the damage multiplier
    }
    /**
     * Returns the chance to hit, considering the "Focus" skill if active.
     */
    @Override
    public int chanceToHit() {
        if (isActive) {
            return 90; // If the skill is active, return a 90% hit rate
        }
        return DEFAULT_HIT_RATE; // Otherwise, return the default hit rate
    }
    /**
     * Resets the skill turns left to its maximum value.
     */
    public void resetSkillTurnsLeft() {
        this.skillTurnsLeft = SKILL_TURNS_DURATION; // Reset skill turns left to its maximum value
    }


}
