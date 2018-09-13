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
    private static Integer INVETORY_BEGIN = 0x00642BC8;
    private static Integer MAX_CONTAINER_WINDOWS = 0x10;

    // methods
    private static Integer METHOD_SAY = 0x00407310;
    private static Integer METHOD_ATTACK = 0x00408E40;
    private static Integer METHOD_TURN_NORTH = 0x00404BC0;
    private static Integer METHOD_TURN_WEST = 0x004050A0;
    private static Integer METHOD_TURN_SOUTH = 0x00404f00;
    private static Integer METHOD_TURN_EAST = 0x00404d60;
    private static Integer METHOD_USE = 0x00406670;

    @Override
    public Integer getAddressPlayerId() {
        return ADDR_PLAYER_ID;
    }

    @Override
    public Integer getAddressBattleListStart() {
        return ADDR_BATTLELIST_START;
    }

    @Override
    public Integer getBattleListMaxEntries() {
        return BATTLELIST_MAX_ENTRIES;
    }

    @Override
    public Integer getBattleListEntrySize() {
        return BATTLELIST_ENTRY_SIZE;
    }

    @Override
    public Integer getAddressPlayerHp() {
        return PLAYER_HP;
    }

    @Override
    public Integer getAddressPlayerMp() {
        return PLAYER_MP;
    }

    @Override
    public Integer getAddressPlayerCap() {
        return PLAYER_CAP;
    }

    @Override
    public Integer getAddressTargetId() {
        return TARGET_ID;
    }

    @Override
    public Integer getAddressInventoryBegin() {
        return INVETORY_BEGIN;
    }

    @Override
    public Integer getMaxContainerWindows() {
        return MAX_CONTAINER_WINDOWS;
    }

    @Override
    public Integer getAddressMethodSay() {
        return METHOD_SAY;
    }

    @Override
    public Integer getAddressMethodAttack() {
        return METHOD_ATTACK;
    }

    @Override
    public Integer getAddressTurnNorth() {
        return METHOD_TURN_NORTH;
    }

    @Override
    public Integer getAddressTurnWest() {
        return METHOD_TURN_WEST;
    }

    @Override
    public Integer getAddressTurnSouth() {
        return METHOD_TURN_SOUTH;
    }

    @Override
    public Integer getAddressTurnEast() {
        return METHOD_TURN_EAST;
    }

    @Override
    public Integer getAddressUse() {
        return METHOD_USE;
    }
}
