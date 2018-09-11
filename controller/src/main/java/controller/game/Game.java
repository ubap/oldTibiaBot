package controller.game;

import remote.*;
import controller.constants.Constants;
import controller.game.world.Creature;
import controller.game.world.Inventory;
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
    private RemoteMethodFactory remoteMethodFactory;
    @Getter
    private RemoteMemoryFactory remoteMemoryFactory;


    public Game(Pipe pipe, Constants constants) {
        this.pipe = pipe;
        this.constants = constants;
        this.remoteMethodFactory = new RemoteMethodFactoryImpl(this.constants);
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
    public Creature getSelf() throws IOException {
        int retries = 0;
        Creature self;
        while (retries < 200) {
            self = BattleList.allVisible(this).getCreatureById(getSelfId());
            if (self != null) {
                return self;
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
     * Returns BattleList with all visible creatures, without self.
     *
     * @return BattleList
     */
    public BattleList getBattleList() throws IOException {
        return BattleList.allVisibleWithoutGiven(this, getSelfId());
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
        this.remoteMethodFactory.say(text).execute(this.pipe);
    }

    public void attack(Creature creature) throws IOException {
        if (!creature.getPositionZ().equals(getSelf().getPositionZ())) {
            log.warn("Cannot attack: {}, pos Z didn't match", creature.toString());
            return;
        }
        log.info("Attack: {}", creature.toString());
        // execute a packet to game world
        this.remoteMethodFactory.attack(creature.getId()).execute(this.pipe);
        this.remoteMemoryFactory.writeInt(
                this.constants.getAddressTargetId(), creature.getId()).execute(this.pipe);

    }

    public Long getHits() {
        return this.pipe.getHits();
    }


}
