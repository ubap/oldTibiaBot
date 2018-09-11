import controller.game.RemoteMemoryFactoryImpl;
import remote.Pipe;
import os.ProcessListUtil;
import remote.RemoteMemoryFactory;

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

            Long timeStart = System.nanoTime();

            for (int i = 0; i < 1000; i++) {

                Thread.sleep(2500);
            }

            timeStart = System.nanoTime() - timeStart;

            System.out.println(timeStart / 1000000. + "ms");

//            GameWorld gameWorld = new GameWorld(pipe, new Consts854());
//
//            while (true) {
//                String playerName = gameWorld.getSelf().getName();
//                Integer playerHp = gameWorld.getPlayerHp();
//                System.out.println(playerName + ", hp: " + playerHp);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
