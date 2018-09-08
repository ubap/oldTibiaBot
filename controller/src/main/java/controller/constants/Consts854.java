package controller.constants;

public class Consts854 implements Constants {
    private static Integer ADDR_PLAYER_ID = 0x00635F10;
    private static Integer ADDR_BATTLELIST_START = 0x00635F70;
    private static Integer BATTLELIST_MAX_ENTRIES = 250;
    private static Integer BATTLELIST_ENTRY_SIZE = 168;
    private static Integer PLAYER_HP = 0x00635F0C;
    private static Integer PLAYER_MP = 0x00635EF0;
    private static Integer PLAYER_CAP = 0x00635EE0;
    private static Integer TARGET_ID = 0x00635EDC;

    @Override
    public Integer addressPlayerId() {
        return ADDR_PLAYER_ID;
    }

    @Override
    public Integer addressBattleListStart() {
        return ADDR_BATTLELIST_START;
    }

    @Override
    public Integer battleListMaxEntries() {
        return BATTLELIST_MAX_ENTRIES;
    }

    @Override
    public Integer battleListEntrySize() {
        return BATTLELIST_ENTRY_SIZE;
    }

    @Override
    public Integer addressPlayerHp() {
        return PLAYER_HP;
    }

    @Override
    public Integer addressPlayerMp() {
        return PLAYER_MP;
    }

    @Override
    public Integer addressPlayerCap() {
        return PLAYER_CAP;
    }

    @Override
    public Integer addressTargetId() {
        return TARGET_ID;
    }
}
