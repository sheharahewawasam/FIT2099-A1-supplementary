package game.gorund;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Hollowsoilder;
import game.actors.WanderingUndead;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class graveyard extends Ground {
    private Random random = new Random();
    private GameMap abandonedVillage;
    private GameMap burialGround;

    /**
     * Constructor for the Graveyard class.
     *
     * @param abandonedVillage the GameMap representing the abandoned village
     * @param burialGround the GameMap representing the burial ground
     */
    public graveyard(GameMap abandonedVillage, GameMap burialGround) {
        super('n');// Graveyard is represented by the 'n' character
        this.abandonedVillage = abandonedVillage;
        this.burialGround = burialGround;
    }

    /**
     * Performs actions during a game tick.
     *
     * @param location the current location of the graveyard
     */
    public void tick(Location location) {
            if (this.random.nextInt(4) == 0 && location.map().equals(abandonedVillage) ) {
                getLocation(location).addActor(new WanderingUndead());
            }
            if (this.random.nextInt(10) == 0 && location.map().equals(burialGround)) {
                getLocation(location).addActor(new Hollowsoilder());
            }

    }

    /**
     * Gets a random empty adjacent location from the current location.
     *
     * @param location the current location
     * @return a random empty adjacent location or null if none is found
     */
    public Location getLocation(Location location) {
        List<Location> adjacentLocations = new ArrayList<>();
        List<Exit> exits = location.getExits();

        // Iterate through exits and add empty adjacent locations to the list
        for (Exit exit : exits) {
            Location adjacentLocation = exit.getDestination();

            // Check if the adjacent location is empty (no actor on it)
            if (adjacentLocation.getActor() == null) {
                adjacentLocations.add(adjacentLocation);
            }
        }

        // If there are empty adjacent locations, choose a random one
        if (!adjacentLocations.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(adjacentLocations.size());
            return adjacentLocations.get(randomIndex);
        }

        return null;
    }
}
