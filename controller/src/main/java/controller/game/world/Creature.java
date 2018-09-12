package controller.game.world;

import controller.game.Utils;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Not mutable.
 */
public class Creature {

    @Getter
    private int id;
    @Getter
    private String name;
    @Getter
    private int positionX;
    @Getter
    private int positionY;
    @Getter
    private int positionZ;
    private int screenX;
    private int screenY;
    private int walking;
    private int direction;
    private int outfit;
    private int headColor;
    private int bodyColor;
    private int legsColor;
    private int feetColor;
    private int addons;
    private int light;
    private int lightColor;
    private int blackSquare;
    private int hpBar;
    private int speed;
    private int visible;
    private int skull;
    private int party;


    public Creature(ByteBuffer byteBuffer) {
        // id
        this.id = byteBuffer.getInt();
        // name
        byte[] nameBytes = new byte[32];
        byteBuffer.get(nameBytes);
        this.name = Utils.stringFromNullTerminatedBytes(nameBytes);
        // positionX
        this.positionX = byteBuffer.getInt();
        this.positionY = byteBuffer.getInt();
        this.positionZ = byteBuffer.getInt();
        this.screenX = byteBuffer.getInt();
        this.screenY = byteBuffer.getInt();
        // unknown data, todo
        byteBuffer.get(new byte[20]);
        this.walking = byteBuffer.getInt();
        this.direction = byteBuffer.getInt();
        // unknow so far, todo
        byteBuffer.get(new byte[12]);
        this.outfit = byteBuffer.getInt();
        this.headColor = byteBuffer.getInt();
        this.bodyColor = byteBuffer.getInt();
        this.legsColor = byteBuffer.getInt();
        this.feetColor = byteBuffer.getInt();
        this.addons = byteBuffer.getInt();
        this.light = byteBuffer.getInt();
        this.lightColor = byteBuffer.getInt();
        // unknown so far, todo
        byteBuffer.getInt();
        this.blackSquare = byteBuffer.getInt();
        this.hpBar = byteBuffer.getInt();
        this.speed = byteBuffer.getInt();
        this.visible = byteBuffer.getInt();
        this.skull = byteBuffer.getInt();
        this.party = byteBuffer.getInt();
        // unknown so far, todo
        byteBuffer.getInt();
        // unknown so far, todo
        byteBuffer.getInt();
        // unknown so far, todo
        byteBuffer.getInt();
    }

    public boolean isVisible() {
        return this.visible != 0;
    }

    /**
     *
     * @param other the creature to calculate distance to. Z is not considered
     * @return Distance calculated using Manhattan.
     */
    public int distanceTo(Creature other) {
        return Math.abs(getPositionX() - other.getPositionX())
                + Math.abs(getPositionY() - other.getPositionY());
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("[0x")
                .append(Integer.toHexString(this.id))
                .append(", ")
                .append(this.name)
                .append("]")
                .toString();
    }


}
