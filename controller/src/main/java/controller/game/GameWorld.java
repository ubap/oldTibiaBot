package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;
import controller.constants.Consts854;

import java.io.IOException;
import java.util.List;

public class GameWorld {
    private Pipe pipe;
    private BattleListReader battleListReader;

    public GameWorld(Pipe pipe) {
        this.pipe = pipe;
        this.battleListReader = new BattleListReader(this.pipe);
    }

    public String getPlayerName() throws IOException {
        PipeResponse pipeResponse
                = this.pipe.send(PipeMessage.readMemory(Consts854.ADDR_PLAYER_ID, 4));
        Integer playerId = pipeResponse.getData().getInt();
        List<BattleListEntry> battleListEntryList = this.battleListReader.read();
        for (BattleListEntry battleListEntry : battleListEntryList) {
            if (battleListEntry.getId().equals(playerId)) {
                return battleListEntry.getName();
            }
        }
        throw new RuntimeException("self not found in the battlelist");
    }

    public Integer getPlayerHp() throws IOException {
        PipeResponse pipeResponse = this.pipe.send(PipeMessage.readMemory(Consts854.PLAYER_HP, 4));
        return pipeResponse.getData().getInt();
    }

}
