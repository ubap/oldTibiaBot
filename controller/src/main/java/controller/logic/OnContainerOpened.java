package controller.logic;

import controller.game.Container;

public interface OnContainerOpened {
    void onContainerOpened(Container container);
    void onContainerClosed(int pos);
    void onContainerChanged(Container container);
}
