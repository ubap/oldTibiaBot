package controller.game.world;

import controller.game.GameWorld;
import remote.PipeMessage;
import remote.PipeResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private Equipment equipment;
    private List<Container> containerList;

    public Inventory(GameWorld gameWorld) throws IOException {
        PipeMessage pipeMessageImpl = gameWorld.getRemoteMemoryFactory().readBytes(
                gameWorld.getConstants().getAddressInventoryBegin(), byteCount());
        PipeResponse pipeResponse = pipeMessageImpl.execute(gameWorld.getPipe());
        ByteBuffer byteBuffer = pipeResponse.getData();
        this.equipment = new Equipment(byteBuffer);
        this.containerList = new ArrayList<>();
        for (int i = 0; i < gameWorld.getConstants().getMaxContainerWindows(); i++) {
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
