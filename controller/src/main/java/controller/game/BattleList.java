package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;
import controller.game.world.Creature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.constants.Consts854.ADDR_BATTLELIST_START;
import static controller.constants.Consts854.BATTLELIST_ENTRY_SIZE;
import static controller.constants.Consts854.BATTLELIST_MAX_ENTRIES;

/**
 * Not mutable.
 */
public class BattleList {
    private List<Creature> creatureList;

    private BattleList() { }

    public static BattleList allVisible(Pipe pipe) throws IOException {

        PipeMessage readMemoryMessage = PipeMessage.readMemory(
                ADDR_BATTLELIST_START, BATTLELIST_MAX_ENTRIES * BATTLELIST_ENTRY_SIZE);
        PipeResponse pipeResponse = pipe.send(readMemoryMessage);
        BattleList battleList = new BattleList();
        battleList.creatureList = new ArrayList<>(BATTLELIST_MAX_ENTRIES);
        for (int i = 0; i < BATTLELIST_MAX_ENTRIES; i++) {
            Creature creature = new Creature(pipeResponse.getData());
            // this basically means this creature is VALID
            if (creature.isVisible()) {
                battleList.creatureList.add(creature);
            }
        }
        return battleList;
    }

    public static BattleList allVisibleWithoutGiven(Pipe pipe, Integer creatureId)
            throws IOException {

        BattleList battleList = BattleList.allVisible(pipe);
        Creature creatureToRemove = null;
        for (Creature creature : battleList.creatureList) {
            if (creature.getId().equals(creatureId)) {
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
            if (!creature.getPositionZ().equals(from.getPositionZ())) {
                continue;
            }
            int currentDistance = Math.abs(from.getPositionX() - creature.getPositionX())
                    + Math.abs(from.getPositionY() - creature.getPositionY());
            if (distance > currentDistance) {
                distance = currentDistance;
                closesCreature = creature;
            }
        }
        return closesCreature;
    }

    /**
     *
     *
     * @param name
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
            if (creature.getId().equals(id)) {
                return creature;
            }
        }
        return null;
    }
}
