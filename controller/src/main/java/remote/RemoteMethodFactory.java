package remote;

import controller.game.world.Creature;

public interface RemoteMethodFactory {

    PipeMessage say(String text);

    PipeMessage attack(Integer creatureId);

    PipeMessage turnNorth();

    PipeMessage turnWest();

    PipeMessage turnSouth();

    PipeMessage turnEast();

    /**
     * Use given item on given creature (same as user would do on battleList).
     * @param itemId ItemId to use
     * @param creature Creature to use the item on.
     * @return The message ready to execute.
     */
    PipeMessage useItemOnCreature(int itemId, Creature creature);
}
