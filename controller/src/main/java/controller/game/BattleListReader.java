package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.constants.Consts854.ADDR_BATTLELIST_START;
import static controller.constants.Consts854.BATTLELIST_ENTRY_SIZE;
import static controller.constants.Consts854.BATTLELIST_MAX_ENTRIES;

public class BattleListReader {
    private Pipe pipe;

    public BattleListReader(Pipe pipe) {
        this.pipe = pipe;
    }

    public List<BattleListEntry> read() throws IOException {
        PipeMessage readMemoryMessage = PipeMessage.readMemory(ADDR_BATTLELIST_START, BATTLELIST_MAX_ENTRIES * BATTLELIST_ENTRY_SIZE);
        PipeResponse pipeResponse = this.pipe.send(readMemoryMessage);
        pipeResponse.isError();

        List<BattleListEntry> battleListEntryList = new ArrayList<>(BATTLELIST_MAX_ENTRIES);
        for (int i = 0; i < BATTLELIST_MAX_ENTRIES; i++) {
            battleListEntryList.add(new BattleListEntry(pipeResponse.getData()));
        }
        return battleListEntryList;
    }
}
