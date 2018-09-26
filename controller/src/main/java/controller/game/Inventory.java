package controller.game;

import lombok.Getter;
import remote.PipeMessage;
import remote.PipeResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    @Getter
    private Equipment equipment;
    @Getter
    private List<Container> containerList;

    public Inventory(Game game) throws IOException {
        PipeMessage pipeMessageImpl = game.getRemoteMemoryFactory().readBytes(
                game.getConstants().getAddressInventoryBegin(), byteCount());
        PipeResponse pipeResponse = pipeMessageImpl.execute(game.getPipe());
        ByteBuffer byteBuffer = pipeResponse.getData();
        this.equipment = new Equipment(byteBuffer);
        this.containerList = new ArrayList<>();
        for (int i = 0; i < game.getConstants().getMaxContainerWindows(); i++) {
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
