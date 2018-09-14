package controller.game.world;

import controller.game.Game;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CreatureFactoryImpl implements CreatureFactory {

    private Game game;

    public CreatureFactoryImpl(Game game) {
        this.game = game;
    }


    @Override
    public Creature getVisible(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();

        int visible = byteBuffer.getInt(position + 144); // position of the visible attribute
        if (visible != 0) {
            return new CreatureImpl(byteBuffer);
        }
        byteBuffer.position(position + game.getConstants().getBattleListEntrySize());
        return null;
    }

    @Override
    public Player getPlayer(Creature creature) throws IOException {
        return new PlayerImpl(this.game, creature);
    }
}
