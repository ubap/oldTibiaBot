package manual;

import controller.TestHelper;
import controller.game.Game;

public class UseItemOnCreatureManualTest {

    private static final int RUNE_IH = 3152;

    public static void main(String argv[]) throws Exception {
        Game game = TestHelper.getGame();

        game.getRemoteMethod().useItemOnCreature(RUNE_IH, game.getSelf());
    }
}
