package controller.game;

import controller.game.world.CreatureFactory;
import controller.game.world.CreatureFactoryImpl;
import controller.game.world.Player;
import remote.*;
import controller.constants.Constants;
import controller.game.world.Creature;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Acts as a *Context* class
 */
public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);

    @Getter
    private Pipe pipe;
    @Getter
    private Constants constants;

    @Getter
    private CreatureFactory creatureFactory;
    @Getter
    private RemoteMethod remoteMethod;
    @Getter
    private RemoteMemoryFactory remoteMemoryFactory;



    public Game(Pipe pipe, Constants constants) {
        this.pipe = pipe;
        this.constants = constants;
        this.creatureFactory = new CreatureFactoryImpl(this);
        this.remoteMethod = new RemoteMethodImpl(this);
        this.remoteMemoryFactory = new RemoteMemoryFactoryImpl();
    }

    public Integer getSelfId() throws IOException {
        PipeResponse pipeResponse = this.remoteMemoryFactory
                .readInt(this.constants.getAddressPlayerId())
                .execute(this.pipe);
        return pipeResponse.getData().getInt();
    }

    /**
     * Returns creature describing Self,
     * blocks thread for few moments if self is not found in the BattleList. This happen on Re-Log
     * @return Creature representing Self or null if timed out.
     */
    public Player getSelf() throws IOException {
        int retries = 0;
        Creature self;
        while (retries < 200) {
            self = BattleList.allVisible(this).getCreatureById(getSelfId());
            if (self != null) {
                return this.creatureFactory.getPlayer(self);
            }
            retries++;
            try {
                log.warn("Waiting because self not found in BattleList, retries: {}", retries);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                log.error("thread interrupted", e);
            }
        }
        return null;
    }

    /**
     * Returns BattleList with all visible creatures, without self. Only the same floor as self.
     *
     * @return BattleList
     */
    public BattleList getBattleList() throws IOException {
        return BattleList.allVisibleWithoutGivenSameFloor(this, getSelf());
    }

    public Integer getPlayerHp() throws IOException {
        PipeResponse pipeResponse
                = this.remoteMemoryFactory.readInt(
                        this.constants.getAddressPlayerHp()).execute(this.pipe);
        return pipeResponse.getData().getInt();
    }

    public Integer getPlayerMp() throws IOException {
        PipeResponse pipeResponse = this.remoteMemoryFactory.readInt(
                        this.constants.getAddressPlayerMp()).execute(this.pipe);
        return pipeResponse.getData().getInt();
    }

    public Inventory getInventory() throws IOException {
        return new Inventory(this);
    }

    // actions

    public void say(String text) throws IOException {
        this.remoteMethod.say(text);
    }

    public void attack(Creature creature) throws IOException {
        if (creature.getPositionZ() != getSelf().getPositionZ()) {
            log.warn("Cannot attack: {}, pos Z didn't match", creature.toString());
            return;
        }
        log.info("Attack: {}", creature.toString());
        // execute a packet to game world
        this.remoteMethod.attack(creature.getId());
        this.remoteMemoryFactory.writeInt(
                this.constants.getAddressTargetId(), creature.getId()).execute(this.pipe);

    }

    public int getTargetId() throws IOException {
        return this.remoteMemoryFactory.readInt(
                this.constants.getAddressTargetId()).execute(this.pipe).getData().getInt(0);
    }

    public boolean isTargeting() throws IOException {
        int targetId = getTargetId();
        if (targetId == 0) {
            return false;
        }
        Creature creature = getBattleList().getCreatureById(targetId);
        if (creature == null) {
            return false;
        }
        return true;
    }

    public Long getHits() {
        return this.pipe.getHits();
    }


}
