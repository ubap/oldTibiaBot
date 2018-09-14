package controller.constants;

public class Consts854 implements Constants {
    private static int ADDR_PLAYER_ID = 0x00635F10;
    private static int ADDR_BATTLELIST_START = 0x00635F70;
    private static int BATTLELIST_MAX_ENTRIES = 250;
    private static int BATTLELIST_ENTRY_SIZE = 168;
    private static int PLAYER_HP = 0x00635F0C;
    private static int PLAYER_MP = 0x00635EF0;
    private static int PLAYER_CAP = 0x00635EE0;
    private static int TARGET_ID = 0x00635EDC;
    private static int INVETORY_BEGIN = 0x00642BC8;
    private static int MAX_CONTAINER_WINDOWS = 0x10;

    // methods
    private static int METHOD_SAY = 0x00407310;
    private static int METHOD_ATTACK = 0x00408E40;
    private static int METHOD_TURN_NORTH = 0x00404BC0;
    private static int METHOD_TURN_WEST = 0x004050A0;
    private static int METHOD_TURN_SOUTH = 0x00404f00;
    private static int METHOD_TURN_EAST = 0x00404d60;
    private static int METHOD_USE = 0x00406670;

    @Override
    public int getAddressPlayerId() {
        return ADDR_PLAYER_ID;
    }

    @Override
    public int getAddressBattleListStart() {
        return ADDR_BATTLELIST_START;
    }

    @Override
    public int getBattleListMaxEntries() {
        return BATTLELIST_MAX_ENTRIES;
    }

    @Override
    public int getBattleListEntrySize() {
        return BATTLELIST_ENTRY_SIZE;
    }

    @Override
    public int getAddressPlayerHp() {
        return PLAYER_HP;
    }

    @Override
    public int getAddressPlayerMp() {
        return PLAYER_MP;
    }

    @Override
    public int getAddressPlayerCap() {
        return PLAYER_CAP;
    }

    @Override
    public int getAddressTargetId() {
        return TARGET_ID;
    }

    @Override
    public int getAddressInventoryBegin() {
        return INVETORY_BEGIN;
    }

    @Override
    public int getMaxContainerWindows() {
        return MAX_CONTAINER_WINDOWS;
    }

    @Override
    public int getAddressMethodSay() {
        return METHOD_SAY;
    }

    @Override
    public int getAddressMethodAttack() {
        return METHOD_ATTACK;
    }

    @Override
    public int getAddressTurnNorth() {
        return METHOD_TURN_NORTH;
    }

    @Override
    public int getAddressTurnWest() {
        return METHOD_TURN_WEST;
    }

    @Override
    public int getAddressTurnSouth() {
        return METHOD_TURN_SOUTH;
    }

    @Override
    public int getAddressTurnEast() {
        return METHOD_TURN_EAST;
    }

    @Override
    public int getAddressUse() {
        return METHOD_USE;
    }
}
