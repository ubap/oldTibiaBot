package remote;

public interface RemoteMethodFactory {

    PipeMessage say(String text);

    PipeMessage attack(Integer creatureId);
}
