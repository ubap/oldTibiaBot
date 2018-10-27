package controller.constants;

public interface Constants {
    int getAddressPlayerId();

    int getAddressBattleListStart();

    int getBattleListMaxEntries();

    int getBattleListEntrySize();

    int getAddressPlayerHp();

    int getAddressPlayerMp();

    int getAddressPlayerCap();

    int getAddressTargetId();

    int getAddressInventoryBegin();

    int getMaxContainerWindows();

    // region Remote methods
    int getAddressMethodSay();

    int getAddressMethodAttack();

    int getAddressTurnNorth();

    int getAddressTurnWest();

    int getAddressTurnSouth();

    int getAddressTurnEast();

    int getAddressUse();
    // endregion
}
