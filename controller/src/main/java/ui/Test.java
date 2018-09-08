package ui;

import controller.Pipe;
import controller.game.world.Creature;
import controller.game.GameWorld;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Test module for all the features so manual tests can be easier performed.
 */
public class Test extends Application {

    @FXML private TextField sayTextField;
    @FXML private TextField targetNameTextField;
    @FXML private Label hpLabel;
    @FXML private Label mpLabel;
    @FXML private Label nameLabel;
    @FXML private Label posXLabel;
    @FXML private Label posYLabel;
    @FXML private Label posZLabel;
    @FXML private Label hitsLabel;

    private GameWorld gameworld;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hello World!");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("test.fxml"));
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * This approach with maany async tasks is very silly. Can be refactored if there's some time.
     */
    @FXML
    private void initialize() {
        setupGameWorld();

        // hp
        Task<String> hpTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    Integer intHp = Test.this.gameworld.getPlayerHp();
                    updateMessage(intHp + " hp");
                    Thread.sleep(1);
                }
            }
        };
        this.hpLabel.textProperty().bind(hpTask.messageProperty());
        new Thread(hpTask).start();

        // mp
        Task<String> mpTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    Integer intMp = Test.this.gameworld.getPlayerMp();
                    updateMessage(intMp + " mp");
                    Thread.sleep(1);
                }
            }
        };
        this.mpLabel.textProperty().bind(mpTask.messageProperty());
        new Thread(mpTask).start();

        // name
        Task<String> nameTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    try {
                        String name = Test.this.gameworld.getSelf().getName();
                        updateMessage(name);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.nameLabel.textProperty().bind(nameTask.messageProperty());
        new Thread(nameTask).start();

        // posX
        Task<String> posXTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    try {
                        Integer posX = Test.this.gameworld.getSelf().getPositionX();
                        updateMessage("x: " + posX);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.posXLabel.textProperty().bind(posXTask.messageProperty());
        new Thread(posXTask).start();

        // posY
        Task<String> posYTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    try {
                        Integer posY = Test.this.gameworld.getSelf().getPositionY();
                        updateMessage("y: " + posY);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.posYLabel.textProperty().bind(posYTask.messageProperty());
        new Thread(posYTask).start();

        // posZ
        Task<String> posZTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    try {
                        Integer posZ = Test.this.gameworld.getSelf().getPositionZ();
                        updateMessage("z: " + posZ);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.posZLabel.textProperty().bind(posZTask.messageProperty());
        new Thread(posZTask).start();

        // hits
        Task<String> hitsTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                while (true) {
                    try {
                        Long hits = Test.this.gameworld.getHits();
                        updateMessage("I/O ops: " + hits);
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.hitsLabel.textProperty().bind(hitsTask.messageProperty());
        new Thread(hitsTask).start();

    }


    @FXML
    private void say() {
        try {
            String text = this.sayTextField.getText();
            if (text.length() > 0) {
                this.gameworld.say(this.sayTextField.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void attack() {
        try {
            String targetName = this.targetNameTextField.getText();
            Creature creature = this.gameworld.getBattleList().getCreatureByName(targetName);
            if (creature != null) {
                this.gameworld.attack(creature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getClosestCreature() {
        try {
            Creature self = this.gameworld.getSelf();
            Creature closestCreature =
                    this.gameworld.getBattleList().getClosestCreature(self);
            if (closestCreature != null) {
                this.targetNameTextField.setText(closestCreature.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupGameWorld() {
        try {
            Pipe pipe = Pipe.forName("\\\\.\\pipe\\oldTibiaBot17096");
            gameworld = new GameWorld(pipe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
