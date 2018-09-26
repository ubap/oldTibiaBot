package controller.logic;

import controller.game.Container;
import controller.game.Game;
import controller.game.ItemEntry;

import java.util.ArrayList;
import java.util.List;

public class Looter {

    private static final int NOT_FOUND = -1;

    private Game game;
    private List<LooterRule> looterRuleList;

    public Looter(Game game) {
        this.game = game;
        this.looterRuleList = new ArrayList<>();
    }


    public int searchForItemInContainer(Container container, int id) {
        List<ItemEntry> itemEntryList = container.getBackpackItems();
        for (int i = 0; i < itemEntryList.size(); i++) {
            if (itemEntryList.get(i).getId() == id) {
                return i;
            }
        }
        return NOT_FOUND;
    }
}
