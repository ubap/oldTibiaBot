package controller.game.world;

import controller.PipeMessage;
import controller.PipeResponse;
import controller.game.GameWorld;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private Equipment equipment;
    private List<Container> containerList;

    public Inventory(GameWorld gameWorld) throws IOException {
        PipeMessage pipeMessage = PipeMessage.readMemory(
                gameWorld.getConstants().addressInventoryBegin(), byteCount());
        PipeResponse pipeResponse = gameWorld.getPipe().send(pipeMessage);
        ByteBuffer byteBuffer = pipeResponse.getData();
        this.equipment = new Equipment(byteBuffer);
        this.containerList = new ArrayList<>();
        for (int i = 0; i < gameWorld.getConstants().maxContainerWindows(); i++) {
            Container container = new Container(byteBuffer, i);
            if (container.isOpen()) {
                this.containerList.add(container);
            }
        }
    }

    public static Integer byteCount() {
        // todo
        return Equipment.byteCount() + 16 * Container.byteCount();
    }
}
