package remote;

import remote.PipeMessage;

public interface RemoteMethodFactory {

    PipeMessage say(String text);

    PipeMessage attack(Integer creatureId);
}
