package controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DanceTest {
    private static final Logger log = LoggerFactory.getLogger(DanceTest.class);

    private static final int DELAY_MILLIS = 500;

    @Test
    public void turnNorth() throws Exception {
        log.info("Turning north");
        TestHelper.getGame().getRemoteMethod().turnNorth();
        Thread.sleep(DELAY_MILLIS);

    }

    @Test
    public void turnWest() throws Exception {
        log.info("Turning west");
        TestHelper.getGame().getRemoteMethod().turnWest();
        Thread.sleep(DELAY_MILLIS);
    }

    @Test
    public void turnSouth() throws Exception {
        log.info("Turning south");
        TestHelper.getGame().getRemoteMethod().turnSouth();
        Thread.sleep(DELAY_MILLIS);
    }

    @Test
    public void turnEast() throws Exception {
        log.info("Turning east");
        TestHelper.getGame().getRemoteMethod().turnEast();
        Thread.sleep(DELAY_MILLIS);
    }

}