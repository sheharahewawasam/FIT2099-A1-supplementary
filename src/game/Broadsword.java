package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Broadsword extends WeaponItem {
    private static final String NAME = "Broadsword";
    private static final char DISPLAY_CHAR = '1';
    private static final int DEFAULT_DAMAGE = 110;
    private static final String VERB = "zaps";
    private static final int DEFAULT_HIT_RATE = 80;
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
    public void activate(){
        this.isActive = true;
        resetSkillTurnsLeft();
        this.increaseDamageMultiplier(0.10f);;
    }
    public void deactivate(){
        this.isActive=false;
        updateDamageMultiplier(1.0F);
    }
    @Override
    public int damage(){return Math.round(DEFAULT_DAMAGE * damageMultiplier);}
    @Override
    public void updateDamageMultiplier(float newDamageMultiplier) {
        this.damageMultiplier = newDamageMultiplier;
    }
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();
        actions.add(new AttackAction(otherActor, location.toString(), this)); // Add attack action
        return actions; // Return the list of allowable actions
    }

    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();
        actions.add(new ActivateFocusAction(this));
        return actions;
    }
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
    @Override
    public void increaseDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier += damageMultiplier; // Increase the damage multiplier
    }
    @Override
    public int chanceToHit() {
        if (isActive) {
            return 90; // If the skill is active, return a 90% hit rate
        }
        return DEFAULT_HIT_RATE; // Otherwise, return the default hit rate
    }
    public void resetSkillTurnsLeft() {
        this.skillTurnsLeft = SKILL_TURNS_DURATION; // Reset skill turns left to its maximum value
    }


}
