package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;
import controller.constants.Consts854;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    @Getter
    private Pipe pipe;
    private BattleListReader battleListReader;

    public GameWorld(Pipe pipe) {
        this.pipe = pipe;
        this.battleListReader = new BattleListReader(this.pipe);
    }

    public Integer getSelfId() throws IOException {
        PipeResponse pipeResponse
                = this.pipe.send(PipeMessage.readMemory(Consts854.ADDR_PLAYER_ID, 4));
        return pipeResponse.getData().getInt();
    }

    public BattleListEntry getSelf() throws IOException {
        Integer playerId = getSelfId();
        List<BattleListEntry> battleListEntryList = this.battleListReader.read();
        for (BattleListEntry battleListEntry : battleListEntryList) {
            if (battleListEntry.getId().equals(playerId)) {
                return battleListEntry;
            }
        }
        throw new RuntimeException("self not found in the battlelist");
    }

    /**
     * Returns Visible creatures. Except self.
     * @return
     * @throws IOException
     */
    public List<BattleListEntry> getCreatures() throws IOException {
        List<BattleListEntry> result = new ArrayList<>();
        Integer playerId = getSelfId();
        List<BattleListEntry> battleListEntryList = this.battleListReader.read();
        for (BattleListEntry battleListEntry : battleListEntryList) {
            if (!battleListEntry.getId().equals(playerId) && battleListEntry.isVisible()) {
                result.add(battleListEntry);
            }
        }
        return result;
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
