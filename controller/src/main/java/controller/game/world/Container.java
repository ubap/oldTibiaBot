package controller.game.world;

import controller.game.Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Container {
    // position is not read from memory
    private Integer position;

    private Integer open;
    private ItemEntry item;
    private String windowName;
    private Integer itemCount;
    private List<ItemEntry> backpackItems; // max 36

    public Container(ByteBuffer byteBuffer, Integer position) {
        this.position = position;
        this.open = byteBuffer.getInt();
        this.item = new ItemEntry(byteBuffer);
        byte[] windowNameBytes = new byte[40];
        byteBuffer.get(windowNameBytes);
        this.windowName = Utils.stringFromNullTerminatedBytes(windowNameBytes);
        this.itemCount = byteBuffer.getInt();
        this.backpackItems = new ArrayList<>(this.itemCount);
        for (int i = 0; i < itemCount; i++) {
            backpackItems.add(new ItemEntry(byteBuffer));
        }
        // skip unused item positions
        byteBuffer.position(byteBuffer.position()
                + (36 - itemCount) * ItemEntry.byteCount());
    }

    public boolean isOpen() {
        return this.open != 0;
    }

    public static Integer byteCount() {
        return 492;
    }
}
