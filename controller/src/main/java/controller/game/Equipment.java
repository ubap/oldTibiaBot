package controller.game;

import java.nio.ByteBuffer;

public class Equipment {

    private ItemEntry head;
    private ItemEntry necklace;
    private ItemEntry backpack;
    private ItemEntry armor;
    private ItemEntry rightHand;
    private ItemEntry leftHand;
    private ItemEntry legs;
    private ItemEntry boots;
    private ItemEntry ring;
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
