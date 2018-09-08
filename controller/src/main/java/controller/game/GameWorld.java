package controller.game;

import controller.Pipe;
import controller.PipeMessage;
import controller.PipeResponse;
import controller.constants.Constants;
import controller.game.world.Creature;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GameWorld {
    private static final Logger log = LoggerFactory.getLogger(GameWorld.class);

    @Getter
    private Pipe pipe;
    @Getter
    private Constants constants;

    public GameWorld(Pipe pipe, Constants constants) {
        this.pipe = pipe;
        this.constants = constants;
    }

    public Integer getSelfId() throws IOException {
        PipeResponse pipeResponse
                = this.pipe.send(PipeMessage.readMemory(this.constants.addressPlayerId(), 4));
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
        PipeResponse pipeResponse = this.pipe.send(
                PipeMessage.readMemory(this.constants.addressPlayerHp(), 4));
        return pipeResponse.getData().getInt();
    }

    public Integer getPlayerMp() throws IOException {
        PipeResponse pipeResponse = this.pipe.send(
                PipeMessage.readMemory(this.constants.addressPlayerMp(), 4));
        return pipeResponse.getData().getInt();
    }

    public void say(String text) throws IOException {
        this.pipe.send(PipeMessage.say(text));
    }

    public void attack(Creature creature) throws IOException {
        if (!creature.getPositionZ().equals(getSelf().getPositionZ())) {
            log.warn("Cannot attack: {}, pos Z didn't match", creature.toString());
            return;
        }
        log.info("Attack: {}", creature.toString());
        // send a packet to game world
        this.pipe.send(PipeMessage.attack(creature.getId()));
        this.pipe.send(PipeMessage.writeInt(this.constants.addressTargetId(), creature.getId()));
    }

    public Long getHits() {
        return this.pipe.getHits();
    }


}
