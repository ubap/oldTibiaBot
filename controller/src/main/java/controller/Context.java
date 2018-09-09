package controller;

import controller.constants.Constants;
import controller.constants.Consts854;
import controller.game.GameWorld;

import java.io.FileNotFoundException;

public class Context {
    private static Context INSTANCE;

    private GameWorld gameWorld;
    private Constants constants;

    public static void init(String pipeName) throws FileNotFoundException {
        Pipe pipe = Pipe.forName(pipeName);
        INSTANCE = new Context();
        INSTANCE.gameWorld = new GameWorld(pipe, new Consts854());
        INSTANCE.constants = new Consts854();
    }

    // todo: Context ??
}
