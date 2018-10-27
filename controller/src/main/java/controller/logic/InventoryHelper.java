package controller.logic;

import controller.game.Container;
import controller.game.Game;

import java.io.IOException;
import java.util.*;

public class InventoryHelper {
    private Game game;

    private List<OnContainerOpened> onContainerOpened;
    private Thread containerMonitorThread;

    public InventoryHelper(Game game) {
        this.game = game;
        this.onContainerOpened = new ArrayList<>();
        this.containerMonitorThread = new Thread(this::containerMonitor);
        this.containerMonitorThread.start();
    }

    private void callOnContainerOpened(Container container) {
        for (OnContainerOpened onContainerOpened : this.onContainerOpened) {
            onContainerOpened.onContainerOpened(container);
        }
    }

    public void addContainerOpened(OnContainerOpened onContainerOpened) {
        this.onContainerOpened.add(onContainerOpened);
    }

    public int getFirstAvailableBpIndex() throws IOException {
        List<Container> containerList = this.game.getInventory().getContainerList();
        int smallestIndex = 0;
        for (int i = 0; i < containerList.size(); i++) {
            if (smallestIndex == containerList.get(i).getPosition()) {
                i = 0;
                smallestIndex++;
            }
        }
        return smallestIndex;
    }

    private void containerMonitor() {
        try {
            Map<Integer, String> containerIds = new HashMap<>();
            while (true) {
                Map<Integer, String> currentContainerIds = new HashMap<>();

                List<Container> containerList = InventoryHelper.this.game.getInventory().getContainerList();
                for (Container container : containerList) {
                    currentContainerIds.put(container.getPosition(), container.getWindowName());

                    if (!containerIds.containsKey(container.getPosition())
                            || !containerIds.get(container.getPosition()).equals(container.getWindowName())) {

                        callOnContainerOpened(container);
                    }
                }

                containerIds = currentContainerIds;
                Thread.sleep(500);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
