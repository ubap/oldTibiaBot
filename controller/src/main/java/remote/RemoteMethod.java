package remote;

import controller.game.world.Creature;

import java.io.IOException;

public interface RemoteMethod {

    void say(String text) throws IOException;

    void attack(Integer creatureId) throws IOException;

    void turnNorth() throws IOException;

    void turnWest() throws IOException;

    void turnSouth() throws IOException;

    void turnEast() throws IOException;

    /**
     * Use given item on given creature (same as user would do on battleList).
     * @param itemId ItemId to use
     * @param creature Creature to use the item on.
     * @return The message ready to execute.
     */
    void useItemOnCreature(int itemId, Creature creature) throws IOException;
}
