package game;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class graveyard extends Ground {
    private Random random = new Random();

    public graveyard() {
        super('n');
    }

    public void tick(Location location) {
            if (this.random.nextInt(4) == 0 ) {
                getLocation(location).addActor(new WanderingUndead());
            }
    }

    public Location getLocation(Location location) {
        List<Location> adjacentLocations = new ArrayList<>();
        List<Exit> exits = location.getExits();

        for (Exit exit : exits) {
            Location adjacentLocation = exit.getDestination();

            // Check if the adjacent location is empty (no actor on it)
            if (adjacentLocation.getActor() == null) {
                adjacentLocations.add(adjacentLocation);
            }
        }

        if (!adjacentLocations.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(adjacentLocations.size());
            return adjacentLocations.get(randomIndex);
        }

        return null;
    }
}
