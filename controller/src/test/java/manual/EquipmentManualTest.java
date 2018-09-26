package manual;

import controller.TestHelper;
import controller.game.Container;
import controller.game.Game;
import controller.logic.InventoryHelper;
import controller.logic.OnContainerOpened;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentManualTest {
    private static final Logger log = LoggerFactory.getLogger(EquipmentManualTest.class);

    public static void main(String argv[]) throws Exception {
        Game game = TestHelper.getGame();

        String equipmentString = game.getInventory().getEquipment().toString();

        InventoryHelper inventoryHelper = new InventoryHelper(game);
        int firstAvailableIndex = inventoryHelper.getFirstAvailableBpIndex();

        log.info("First available index: {}", firstAvailableIndex);
        log.info(equipmentString);

        inventoryHelper.addContainerOpened(new OnContainerOpened() {
            @Override
            public void onContainerOpened(Container container) {
                log.info("Container opened {}", container.getWindowName());
            }
        });

        Thread.sleep(1000000);
    }
}
