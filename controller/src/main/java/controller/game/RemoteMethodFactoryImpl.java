package controller.game;

import controller.game.world.Creature;
import remote.RemoteMethodArgument;
import controller.constants.Constants;
import remote.RemoteMethod;
import remote.PipeMessage;
import remote.RemoteMethodFactory;

/**
 * Expose GAME methods.
 */
public class RemoteMethodFactoryImpl implements RemoteMethodFactory {

    private Constants constants;

    public RemoteMethodFactoryImpl(Constants constants) {
        this.constants = constants;
    }

    @Override
    public PipeMessage say(String text) {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressMethodSay())
                .addArgument(RemoteMethodArgument.int32(1))
                .addArgument(RemoteMethodArgument.text(text))
                .build();
    }

    @Override
    public PipeMessage attack(Integer creatureId) {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressMethodAttack())
                .addArgument(RemoteMethodArgument.int32(creatureId))
                .build();
    }

    @Override
    public PipeMessage turnNorth() {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnNorth())
                .build();
    }

    @Override
    public PipeMessage turnWest() {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnWest())
                .build();
    }

    @Override
    public PipeMessage turnSouth() {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnSouth())
                .build();
    }

    @Override
    public PipeMessage turnEast() {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressTurnEast())
                .build();
    }

    @Override
    public PipeMessage useItemOnCreature(int itemId, Creature creature) {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressUse())
                .addArgument(RemoteMethodArgument.int32(0xFFFF))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(itemId))
                .addArgument(RemoteMethodArgument.int32(0))
                .addArgument(RemoteMethodArgument.int32(creature.getId()))
                .build();
    }
}
