package controller.game.world;

import controller.game.Game;

import java.io.IOException;

/**
 * Inheritance by composition.
 */
public class PlayerImpl implements Player {

    private Creature creature;
    private int hp;
    private int mp;
    private double cap;

    public PlayerImpl(Game game, Creature creature) throws IOException {
        this.creature = creature;
        this.hp = game.getRemoteMemoryFactory()
                .readInt(game.getConstants().getAddressPlayerHp())
                .execute(game.getPipe())
                .getData().getInt();
        this.mp = game.getRemoteMemoryFactory()
                .readInt(game.getConstants().getAddressPlayerMp())
                .execute(game.getPipe())
                .getData().getInt();
        this.cap = game.getRemoteMemoryFactory()
                .readInt(game.getConstants().getAddressPlayerCap())
                .execute(game.getPipe())
                .getData().getInt() / 100.;
    }

    // region Creature interface

    @Override
    public int getId() {
        return this.creature.getId();
    }

    @Override
    public String getName() {
        return this.creature.getName();
    }

    @Override
    public int getPositionX() {
        return this.creature.getPositionX();
    }

    @Override
    public int getPositionY() {
        return this.creature.getPositionY();
    }

    @Override
    public int getPositionZ() {
        return this.creature.getPositionZ();
    }

    @Override
    public int getHpPc() {
        return this.creature.getHpPc();
    }

    @Override
    public boolean isVisible() {
        return this.creature.isVisible();
    }

    @Override
    public double distanceTo(Creature other) {
        return this.creature.distanceTo(other);
    }

    // endregion

    // region Player interface

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public int getMp() {
        return this.mp;
    }

    @Override
    public double getCap() {
        return this.cap;
    }

    // endregion
}
