package useful;

import controller.TestHelper;
import controller.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * This is a simple RUNE healer bot.
 */
public class HealUsingRuneTest {
    private static final Logger log = LoggerFactory.getLogger(HealUsingRuneTest.class);
    private static final int RUNE_IH = 3152;
    private static final int RUNE_UH = 3160;

    public static void main(String argv[]) throws Exception {
        log.info("Running {} on {}", HealUsingRuneTest.class.getSimpleName(), ManagementFactory.getRuntimeMXBean().getName());
        Game game = TestHelper.getGame();

        while (true) {
            int healthPercent = game.getSelf().getHpPc();
            if (healthPercent < 50) {
                log.info("Using item: {}, healthPercent: {}", RUNE_IH, healthPercent);
                game.getRemoteMethod().useItemOnCreature(RUNE_IH, game.getSelf());
                Thread.sleep(200);
            }
            Thread.sleep(50);
        }
    }
}
