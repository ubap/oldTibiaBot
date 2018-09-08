package controller.game;

import java.nio.ByteBuffer;

import static controller.constants.Consts854.BATTLELIST_ENTRY_SIZE;

public class BattleListEntry {

    private Integer id;
    private String name;
    private Integer positionX;
    private Integer positionY;
    private Integer positionZ;
    private Integer screenX;
    private Integer screenY;
    private Integer walking;
    private Integer direction;
    private Integer outfit;
    private Integer headColor;
    private Integer bodyColor;
    private Integer legsColor;
    private Integer feetColor;
    private Integer addons;
    private Integer light;
    private Integer lightColor;
    private Integer blackSquare;
    private Integer hpBar;
    private Integer speed;
    private Integer visible;
    private Integer skull;
    private Integer party;


    public BattleListEntry(ByteBuffer byteBuffer) {
        int startPosition = byteBuffer.position();

        // id
        this.id = byteBuffer.getInt();
        // name
        byte[] nameBytes = new byte[32];
        byteBuffer.get(nameBytes);
        int i;
        for (i = 0; i < nameBytes.length; i++) {
            if (nameBytes[i] == 0) {
                break;
            }
        }
        this.name = new String(nameBytes, 0, i);
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

        if (byteBuffer.position() != startPosition + BATTLELIST_ENTRY_SIZE) {
            // todo this is not an runtime exception
            throw new RuntimeException("incorrect battlelist structure");
        }
    }

}
