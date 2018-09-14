package controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private static final Logger log = LoggerFactory.getLogger(PlayerTest.class);

    @Test
    public void turnHp() throws Exception {
        int hp = TestHelper.getGame().getSelf().getHp();
        log.info("Player HP: {}", hp);

        assertTrue(hp >= 0);
    }

}