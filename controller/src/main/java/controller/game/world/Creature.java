package controller.game.world;

public interface Creature {
    int getId();

    String getName();

    int getPositionX();

    int getPositionY();

    int getPositionZ();

    int getHpPc();

    boolean isVisible();

    double distanceTo(Creature other);
}
