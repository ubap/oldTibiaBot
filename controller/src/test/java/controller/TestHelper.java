package controller;

import controller.constants.Consts854;
import controller.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import os.ProcessListUtil;
import remote.Pipe;

import java.util.List;

public class TestHelper {

    private static final Logger log = LoggerFactory.getLogger(TestHelper.class);

    private static final String PROCESS_NAME = "Kasteria.exe";

    private static Game game = null;
    private static long benchmarkTime;

    public static Game getGame() throws Exception {
        if (game == null) {
            List<Integer> processList = ProcessListUtil.getProcessList(PROCESS_NAME);
            Pipe pipe;
            if (processList.size() > 0) {
                pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot" + processList.get(0));
            } else {
                // todo custom excpetioon
                log.error("Client not found");
                throw new Exception("client not found");
            }
            game = new Game(pipe, new Consts854());
        }
        return game;
    }

    public static void startBenchmarkTime() {
        benchmarkTime = System.nanoTime();
    }

    public static double captureBenchmarkTime() {
        return System.nanoTime() - benchmarkTime;
    }

    public static void logBenchmarkTime(Logger log, String tag) {
        double benchmarkTimeMillis = nanoToMillis(captureBenchmarkTime());
        log.info("{}: Benchmarked time: {} ms", tag, benchmarkTimeMillis);
    }

    public static double nanoToMillis(double nano) {
        return (nano / 1000000.);
    }

    public static double nanoToMillis(long nano) {
        return (nano / 1000000.);
    }

}
