import controller.Pipe;
import controller.game.GameWorld;

public class Main {

    public static void main(String[] argv) {

        try {

            Pipe pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot5556");
            GameWorld gameWorld = new GameWorld(pipe);

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
