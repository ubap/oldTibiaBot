package controller.game;

import remote.PipeMessage;
import remote.PipeResponse;
import controller.game.world.Creature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Not mutable.
 */
public class BattleList {
    private List<Creature> creatureList;

    private BattleList() { }

    public static BattleList allVisible(Game game) throws IOException {

        PipeMessage readMemoryMessage = game.getRemoteMemoryFactory().readBytes(
                game.getConstants().getAddressBattleListStart(),
                game.getConstants().getBattleListMaxEntries()
                        * game.getConstants().getBattleListEntrySize());
        PipeResponse pipeResponse = readMemoryMessage.execute(game.getPipe());
        BattleList battleList = new BattleList();
        battleList.creatureList = new ArrayList<>(game.getConstants().getBattleListMaxEntries());
        for (int i = 0; i < game.getConstants().getBattleListMaxEntries(); i++) {
            Creature creature = new Creature(pipeResponse.getData());
            // this basically means this creature is VALID
            if (creature.isVisible()) {
                battleList.creatureList.add(creature);
            }
        }
        return battleList;
    }

    public static BattleList allVisibleWithoutGiven(Game game, Integer creatureId)
            throws IOException {

        BattleList battleList = BattleList.allVisible(game);
        Creature creatureToRemove = null;
        for (Creature creature : battleList.creatureList) {
            if (creature.getId() == creatureId) {
                creatureToRemove = creature;
                break;
            }
        }
        if (creatureToRemove != null) {
            battleList.creatureList.remove(creatureToRemove);
        }
        return battleList;
    }

    public Creature getClosestCreature(Creature from) {

        if (this.creatureList.size() == 0) {
            return null;
        }
        Creature closesCreature = null;
        int distance = Integer.MAX_VALUE;
        for (Creature creature : this.creatureList) {
            if (creature.getPositionZ() != from.getPositionZ()) {
                continue;
            }
            int currentDistance = from.distanceTo(creature);
            if (distance > currentDistance) {
                distance = currentDistance;
                closesCreature = creature;
            }
        }
        return closesCreature;
    }

    /**
     *
     * @param name Name of the creature to look for.
     * @return Null if creature with given name is not found.
     */
    public Creature getCreatureByName(String name) {
        if (this.creatureList.size() == 0) {
            return null;
        }
        for (Creature creature : creatureList) {
            if (creature.getName().equals(name)) {
                return creature;
            }
        }
        return null;
    }

    /**
     *
     *
     * @param id 32 bit unsigned int
     * @return Null if creature with given id is not found.
     */
    public Creature getCreatureById(Integer id) {
        if (this.creatureList.size() == 0) {
            return null;
        }
        for (Creature creature : creatureList) {
            if (creature.getId() == id) {
                return creature;
            }
        }
        return null;
    }
}
