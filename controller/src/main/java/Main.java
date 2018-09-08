import controller.PipeMessage;
import controller.Pipe;
import controller.PipeResponse;
import controller.constants.Consts854;
import controller.game.BattleListEntry;
import controller.game.BattleListReader;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] argv) {

        try {

            Pipe pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot5556");
            PipeResponse pipeResponse;
            pipeResponse = pipe.send(PipeMessage.say("heh"));
            pipeResponse = pipe.send(PipeMessage.readMemory(Consts854.ADDR_PLAYER_ID, 4));

            System.out.println("a");
            BattleListReader battleListReader = new BattleListReader(pipe);

            while (true) {
                long milis = System.nanoTime();
                List<BattleListEntry> battleListEntryList = battleListReader.read();
                System.out.println("battlelist read took: " + (System.nanoTime() - milis) + " ns");
                battleListEntryList.size();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
