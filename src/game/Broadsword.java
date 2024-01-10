package game;

import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Broadsword extends WeaponItem {
    private static final String NAME = "Broadsword";
    private static final char DISPLAY_CHAR = '1';
    private static final int DEFAULT_DAMAGE = 110;
    private static final String VERB = "zaps";
    private static final int DEFAULT_HIT_RATE = 80;
    /**
     * Constructor.
     *
     */
    public Broadsword() {
        super(NAME, DISPLAY_CHAR, DEFAULT_DAMAGE, VERB, DEFAULT_HIT_RATE);
    }
}
