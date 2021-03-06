package manual;

import controller.TestHelper;
import controller.constants.Consts854;
import controller.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import os.ProcessListUtil;
import remote.Pipe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Benchmark {

    private static final Logger log = LoggerFactory.getLogger(Benchmark.class);

    public static void main(String[] argv) throws Exception {

        Game game = TestHelper.getGame();

        executeBenchmarks(game, Arrays.asList(1, 2, 4, 8, 16, 32, 64, 10000));
    }

    private static void executeBenchmarks(Game game, List<Integer> timesList) throws IOException {
        for (Integer times : timesList) {
            benchmarkBattleList(game, times);
        }
        for (Integer times : timesList) {
            benchmarkGetSelf(game, times);
        }
    }

    private static void benchmarkBattleList(Game game, int times) throws IOException {
        Long timeStart = System.nanoTime();

        for (int i = 0; i < times; i++) {
            game.getBattleList();
        }

        timeStart = System.nanoTime() - timeStart;

        log.info("benchmarkBattleList, times: {}, avg time: {} ms", times, (timeStart / 1000000.) / times);
    }

    private static void benchmarkGetSelf(Game game, int times) throws IOException {
        Long timeStart = System.nanoTime();

        for (int i = 0; i < times; i++) {
            game.getSelf();
        }

        timeStart = System.nanoTime() - timeStart;

        log.info("benchmarkGetSelf, times: {}, avg time: {} ms", times, (timeStart / 1000000.) / times);
    }

}
