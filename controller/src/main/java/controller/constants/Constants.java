package controller.constants;

public interface Constants {
    Integer getAddressPlayerId();

    Integer getAddressBattleListStart();

    Integer getBattleListMaxEntries();

    Integer getBattleListEntrySize();

    Integer getAddressPlayerHp();

    Integer getAddressPlayerMp();

    Integer getAddressPlayerCap();

    Integer getAddressTargetId();

    Integer getAddressInventoryBegin();

    Integer getMaxContainerWindows();

    // region Remote methods
    Integer getAddressMethodSay();

    Integer getAddressMethodAttack();
    // endregion
}
