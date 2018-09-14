package controller.game.world;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface CreatureFactory {

    /**
     * Get creature or NULL if the creature is not visible.
     *
     * @param byteBuffer The byte buffer to parse, it will be incremented by battleList entry size.
     * @return NULL if creature at given byteBuffer position is not Visible.
     */
    Creature getVisible(ByteBuffer byteBuffer);

    Player getPlayer(Creature creature) throws IOException;
}
