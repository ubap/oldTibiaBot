import controller.Pipe;
import controller.constants.Consts854;
import controller.game.GameWorld;

public class Main {

    public static void main(String[] argv) {

        try {

            Pipe pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot5556");
            GameWorld gameWorld = new GameWorld(pipe, new Consts854());

            while (true) {
                String playerName = gameWorld.getSelf().getName();
                Integer playerHp = gameWorld.getPlayerHp();
                System.out.println(playerName + ", hp: " + playerHp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
