package controller.game;

import lombok.Getter;

import java.nio.ByteBuffer;

public class ItemEntry {
    @Getter
    private int id;
    private int extraData1;
    private int extraData2;

    public ItemEntry(ByteBuffer byteBuffer) {
        this.id = byteBuffer.getInt();
        this.extraData1 = byteBuffer.getInt();
        this.extraData2 = byteBuffer.getInt();
    }

    public static Integer byteCount() {
        return 12;
    }
}
