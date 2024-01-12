package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
                new Wall(), new Floor(), new Puddle(), new Void());

        List<String> abandonedVillage = Arrays.asList(
                "...........................................................",
                "...#######.................................................",
                "...#__.......................................++++..........",
                "...#..___#...................................+++++++.......",
                "...###.###................#######..............+++.........",
                "..........................#_____#................+++.......",
                "........~~................#_____#.................+........",
                ".........~~~..............###_###................++........",
                "...~~~~~~~~....+++.........................................",
                "....~~~~~........+++++++..................###..##...++++...",
                "~~~~~~~..............+++..................#___..#...++.....",
                "~~~~~~.................++.................#..___#....+++...",
                "~~~~~~~~~.................................#######.......++.");

        GameMap abandonedVillageMap = new GameMap(groundFactory, abandonedVillage);
        world.addGameMap(abandonedVillageMap);

        // Create a new GameMap for the Burial Ground
        FancyGroundFactory burialGroundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Puddle(), new Void());

        List<String> burialGround = Arrays.asList(
                // Define the layout for the Burial Ground map
                "...........+++++++........~~~~~~++....~~",
                "...........++++++.........~~~~~~+.....~~",
                "............++++...........~~~~~......++",
                "............+.+.............~~~.......++",
                "..........++~~~.......................++",
                ".........+++~~~....#######...........+++",
                ".........++++~.....#_____#.........+++++",
                "..........+++......#_____#........++++++",
                "..........+++......###_###.......~~+++++",
                "..........~~.....................~~...++",
                "..........~~~..................++.......",
                "...........~~....~~~~~.........++.......",
                "......~~....++..~~~~~~~~~~~......~......",
                "....+~~~~..++++++++~~~~~~~~~....~~~.....",
                "....+~~~~..++++++++~~~..~~~~~..~~~~~...."
        );

        GameMap burialGroundMap = new GameMap(burialGroundFactory, burialGround);

        // Add the Burial Ground map to the world
        world.addGameMap(burialGroundMap);

        LockedGate lockedGate1 = new LockedGate();//Gate created for abandonedVillageMap to burialGroundMap
        LockedGateBG lockedGate2 = new LockedGateBG();//Gate created for burialGroundMap to abandonedVillageMap
        abandonedVillageMap.at(29, 0).setGround(lockedGate1);//the x and y labels set for the gate to be implemented in map of abandonedVillageMap
        burialGroundMap.at(19, 0).setGround(lockedGate2);///the x and y labels set for the gate to be implemented in map of burialGroundMap

        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        abandonedVillageMap.at(23, 10).addActor(new WanderingUndead());

        Player player = new Player("The Abstracted One", '@', 150);
        world.addPlayer(player, abandonedVillageMap.at(29, 5));

        abandonedVillageMap.at(24,5).addItem(new Broadsword());

        graveyard graveyard1 = new graveyard(abandonedVillageMap, burialGroundMap);
        graveyard graveyard2 = new graveyard(abandonedVillageMap, burialGroundMap);
        abandonedVillageMap.at(35, 0).setGround(graveyard1);
        abandonedVillageMap.at(31, 11).setGround(graveyard2);

        graveyard graveyard3 = new graveyard(abandonedVillageMap, burialGroundMap);
        graveyard graveyard4 = new graveyard(abandonedVillageMap, burialGroundMap);
        burialGroundMap.at(27, 5).setGround(graveyard3);
        burialGroundMap.at(26, 11).setGround(graveyard4);



        world.run();
    }
}
