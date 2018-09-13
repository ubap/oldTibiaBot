package manual;

import controller.TestHelper;
import controller.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentManualTest {
    private static final Logger log = LoggerFactory.getLogger(EquipmentManualTest.class);

    public static void main(String argv[]) throws Exception {
        Game game = TestHelper.getGame();

        String equipmentString = game.getInventory().getEquipment().toString();

        log.info(equipmentString);
    }
}
