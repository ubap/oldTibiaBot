package controller;

import controller.constants.Consts854;
import controller.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import os.ProcessListUtil;
import remote.Pipe;
import remote.RemoteMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Dance {

    private static final Logger log = LoggerFactory.getLogger(Dance.class);
    private static final TestConsts testConsts = new TestConstsImpl();

    public static void main(String[] argv) {

        try {
            List<Integer> processList = ProcessListUtil.getProcessList(testConsts.getProcessName());
            Pipe pipe;
            if (processList.size() > 0) {
                pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot" + processList.get(0));
            } else {
                return;
            }
            Game game = new Game(pipe, new Consts854());

            for (int i = 0; i < 5; i++) {
                log.info("Performing full rotation");
                dance(game.getRemoteMethod(), 500);
            }

        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }

    /**
     * performs one rotation NWSE
     */
    private static void dance(RemoteMethod remoteMethod, int delayMilliseconds)
            throws InterruptedException, IOException {

        log.info("Turning north");
        remoteMethod.turnNorth();
        Thread.sleep(delayMilliseconds);

        log.info("Turning west");
        remoteMethod.turnWest();
        Thread.sleep(delayMilliseconds);

        log.info("Turning south");
        remoteMethod.turnSouth();
        Thread.sleep(delayMilliseconds);

        log.info("Turning east");
        remoteMethod.turnEast();
        Thread.sleep(delayMilliseconds);
    }

}
