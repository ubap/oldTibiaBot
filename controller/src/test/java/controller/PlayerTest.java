package controller;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static controller.TestHelper.logBenchmarkTime;
import static controller.TestHelper.startBenchmarkTime;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private static final Logger log = LoggerFactory.getLogger(PlayerTest.class);

    @Before
    public void initialize() throws Exception {
        // JIT ?
        TestHelper.getGame().getSelf().getHp();
        TestHelper.getGame().getSelf().getMp();
        TestHelper.getGame().getSelf().getCap();
    }

    @Test
    public void testHp() throws Exception {
        startBenchmarkTime();
        int hp = TestHelper.getGame().getSelf().getHp();

        logBenchmarkTime(log, "testHp");
        log.info("Player HP: {}", hp);

        assertTrue(hp >= 0);
    }

    @Test
    public void testMp() throws Exception {
        startBenchmarkTime();
        int mp = TestHelper.getGame().getSelf().getMp();

        logBenchmarkTime(log, "testMp");
        log.info("Player MP: {}", mp);

        assertTrue(mp >= 0);
    }

    @Test
    public void testCap() throws Exception {
        startBenchmarkTime();
        double cap = TestHelper.getGame().getSelf().getCap();

        logBenchmarkTime(log, "testCap");
        log.info("Player CAP: {}", cap);

        assertTrue(cap >= 0);
    }

}