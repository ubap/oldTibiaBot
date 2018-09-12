package controller.game;

import remote.RemoteMethodArgument;
import controller.constants.Constants;
import remote.RemoteMethod;
import remote.PipeMessage;
import remote.RemoteMethodFactory;

/**
 * Expose GAME methods.
 */
public class RemoteMethodFactoryImpl implements RemoteMethodFactory {
    // todo extract constants

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
}
