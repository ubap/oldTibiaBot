package controller.game;

import controller.Argument;
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
                .addArgument(Argument.int32(1))
                .addArgument(Argument.text(text))
                .build();
    }

    @Override
    public PipeMessage attack(Integer creatureId) {
        RemoteMethod.Builder payloadCallBuilder = new RemoteMethod.Builder();
        return payloadCallBuilder
                .setMethodAddress(constants.getAddressMethodAttack())
                .addArgument(Argument.int32(creatureId))
                .build();
    }
}
