package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;
import controller.constants.Consts854;
import controller.game.world.Creature;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GameWorld {
    @Getter
    private Pipe pipe;

    public GameWorld(Pipe pipe) {
        this.pipe = pipe;
    }

    public Integer getSelfId() throws IOException {
        PipeResponse pipeResponse
                = this.pipe.send(PipeMessage.readMemory(Consts854.ADDR_PLAYER_ID, 4));
        return pipeResponse.getData().getInt();
    }

    /**
     * Returns creature describing Self, blocks thread for few moments if self is not found in the battlelist.
     * This happens on Re-Log
     * @return
     * @throws IOException
     */
    public Creature getSelf() throws IOException {
        int retries = 0;
        Creature self;
        while (retries < 200) {
            self = BattleList.allVisible(this.pipe).getCreatureById(getSelfId());
            if (self != null) {
                return self;
            }
            retries++;
            try {
                System.out.println("Waiting because self not found in battlelist, retries: " + retries);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns battlelist with all visible creatures, without self.
     *
     * @return BattleList
     */
    public BattleList getBattleList() throws IOException {
        return BattleList.allVisibleWithoutGiven(this.pipe, getSelfId());
    }

    public Integer getPlayerHp() throws IOException {
        PipeResponse pipeResponse = this.pipe.send(PipeMessage.readMemory(Consts854.PLAYER_HP, 4));
        return pipeResponse.getData().getInt();
    }

    public Integer getPlayerMp() throws IOException {
        PipeResponse pipeResponse = this.pipe.send(PipeMessage.readMemory(Consts854.PLAYER_MP, 4));
        return pipeResponse.getData().getInt();
    }

    public void say(String text) throws IOException {
        this.pipe.send(PipeMessage.say(text));
    }

    public void attack(Integer creatureId) throws IOException {
        // send a packet to game world
        this.pipe.send(PipeMessage.attack(creatureId));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(creatureId);
        this.pipe.send(PipeMessage.writeMemory(Consts854.TARGET_ID, 4, byteBuffer.array()));
    }

    public Long getHits() {
        return this.pipe.getHits();
    }

}
