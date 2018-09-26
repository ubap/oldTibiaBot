package controller.logic;

import lombok.Getter;

public class LooterRule {
    @Getter
    private int itemId;
    @Getter
    private String destinationBackpackName;

    public LooterRule(int itemId, String destinationBackpackName) {
        this.itemId = itemId;
        this.destinationBackpackName = destinationBackpackName;
    }


}
