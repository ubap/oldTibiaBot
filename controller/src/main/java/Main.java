import controller.constants.Consts854;
import controller.game.Game;
import os.ProcessListUtil;
import remote.Pipe;

import java.util.List;

public class Main {

    public static void main(String[] argv) {

        try {

            List<Integer> processList = ProcessListUtil.getProcessList("Kasteria.exe");
            Pipe pipe;
            if (processList.size() > 0) {
                pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot" + processList.get(0));
            } else {
                return;
            }
            Game game = new Game(pipe, new Consts854());

            Long timeStart = System.nanoTime();

            for (int i = 0; i < 10000; i++) {
                String playerName = game.getSelf().getName();
            }

            timeStart = System.nanoTime() - timeStart;

            System.out.println("Avg time: " + (timeStart / 1000000.) / 10000. + "ms");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
