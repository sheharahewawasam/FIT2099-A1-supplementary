
package game.gorund;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.display.FancyMessage;
import game.actors.Player;

public class Void extends Ground {
    public Void() {
        super('+');
    }

    @Override
    public void tick(Location location) {
        if (location.containsAnActor()) {
            Actor actor = location.getActor();
            if (actor instanceof Player) {
                Display display = new Display();
                display.println("\n");
                for (String line : FancyMessage.YOU_DIED.split("\n")) {
                    display.println(line);
                }
                display.println("\n");
            }
            // Make the actor unconscious after displaying the message
            actor.unconscious(location.map());
        }
    }
}

