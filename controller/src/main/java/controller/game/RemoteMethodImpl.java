package controller.game;

import controller.game.world.Creature;
import remote.*;
import controller.constants.Constants;

import java.io.IOException;

/**
 * Expose GAME methods.
 */
public class RemoteMethodImpl implements RemoteMethod {

    private Constants constants;
    private Pipe pipe;


    public RemoteMethodImpl(Game game) {
        this.constants = game.getConstants();
        this.pipe = game.getPipe();
    }

    @Override
    public void say(String text) throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressMethodSay())
                .addArgument(RemoteMethodArgument.int32(1))
                .addArgument(RemoteMethodArgument.text(text))
                .build()
                .execute(this.pipe);
    }

    @Override
    public void attack(Integer creatureId) throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressMethodAttack())
                .addArgument(RemoteMethodArgument.int32(creatureId))
                .build()
                .execute(this.pipe);
    }

    @Override
    public void turnNorth() throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnNorth())
                .build()
                .execute(this.pipe);
    }

    @Override
    public void turnWest() throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnWest())
                .build()
                .execute(this.pipe);
    }

    @Override
    public void turnSouth() throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnSouth())
                .build()
                .execute(this.pipe);
    }

    @Override
    public void turnEast() throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnEast())
                .build()
                .execute(this.pipe);
    }

    @Override
    public void useItemOnCreature(int itemId, Creature creature) throws IOException {
        RemoteCall.Builder payloadCallBuilder = new RemoteCall.Builder();
        payloadCallBuilder
                .setMethodAddress(constants.getAddressUse())
                .addArgument(RemoteMethodArgument.int32(0xFFFF))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(itemId))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(creature.getId()))
                .build()
                .execute(this.pipe);
    }
}
