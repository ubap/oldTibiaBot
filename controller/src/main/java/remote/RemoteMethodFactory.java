package remote;

public interface RemoteMethodFactory {

    PipeMessage say(String text);

    PipeMessage attack(Integer creatureId);

    PipeMessage turnNorth();

    PipeMessage turnWest();

    PipeMessage turnSouth();

    PipeMessage turnEast();

}
