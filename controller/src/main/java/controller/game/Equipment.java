package controller.game;

import lombok.Getter;

import java.nio.ByteBuffer;

public class Equipment {

    @Getter
    private ItemEntry head;
    @Getter
    private ItemEntry necklace;
    @Getter
    private ItemEntry backpack;
    @Getter
    private ItemEntry armor;
    @Getter
    private ItemEntry rightHand;
    @Getter
    private ItemEntry leftHand;
    @Getter
    private ItemEntry legs;
    @Getter
    private ItemEntry boots;
    @Getter
    private ItemEntry ring;
    @Getter
    private ItemEntry arrow;

    public Equipment(ByteBuffer byteBuffer) {
        this.head = new ItemEntry(byteBuffer);
        this.necklace = new ItemEntry(byteBuffer);
        this.backpack = new ItemEntry(byteBuffer);
        this.armor = new ItemEntry(byteBuffer);
        this.rightHand = new ItemEntry(byteBuffer);
        this.leftHand = new ItemEntry(byteBuffer);
        this.legs = new ItemEntry(byteBuffer);
        this.boots = new ItemEntry(byteBuffer);
        this.ring = new ItemEntry(byteBuffer);
        this.arrow = new ItemEntry(byteBuffer);
    }

    public static Integer byteCount() {
        return 10 * ItemEntry.byteCount();
    }
}
