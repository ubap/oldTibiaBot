package useful;

import controller.TestHelper;
import controller.game.Game;
import controller.game.world.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * Simple auto-attack bot. Attack creatures in given list if they are 1 sqm or less away.
 */
public class AutoAttackTest {
    private static final Logger log = LoggerFactory.getLogger(AutoAttackTest.class);

    private static final List<String> CREATURE_NAMES = Arrays.asList(
            "Cyclops",
            "Rat",
            "Troll",
            "Wolf"
    );

    public static void main(String argv[]) throws Exception {
        log.info("Running {} on {}", AutoAttackTest.class.getSimpleName(), ManagementFactory.getRuntimeMXBean().getName());
        Game game = TestHelper.getGame();

        while (true) {

            Creature self = game.getSelf();
            List<Creature> creatureList = game.getBattleList().getCreatureList();

            for (Creature creature : creatureList) {
                if (CREATURE_NAMES.contains(creature.getName()) && self.distanceTo(creature) <= 1) {
                    game.attack(creature);
                }
            }

            Thread.sleep(500);
        }
    }
}
