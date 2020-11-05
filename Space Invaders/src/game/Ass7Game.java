package game;


import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import gamelevels.CreateLevels;
import gamelevels.LevelInformation;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;
import score.HighScoresAnimation;
import score.HighScoresTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class of the main that makes the game.
 *
 * @author Ronit Shternfeld
 * @version 24 April 2018
 */
public class Ass7Game {
    private boolean running;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * The main that makes the game.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        final GUI gui = new GUI("ARKANOID", WIDTH, HEIGHT);
        final KeyboardSensor keyboard = gui.getKeyboardSensor();
        final AnimationRunner animation = new AnimationRunner(gui, 60);
        final File highScoresFile = new File("highscores");
        final Menu<Task<Void>> taskMenuAnimation = new MenuAnimation<>(animation, "Space Invaders", keyboard);
        List<LevelInformation> levels = new ArrayList<>();

        final List<LevelInformation> newList = levels;
        final String[] programArgs = args;
        Task<Void> startGame = new Task<Void>() {
            public Void run() {
                List<LevelInformation> levelList = new ArrayList<>();
                levelList.add(new CreateLevels());
                GameFlow gameFlow = new GameFlow(animation, keyboard, gui, highScoresFile);
                gameFlow.runLevels(levelList);
                return null;
            }
        };

        Task<Void> highSco = new Task<Void>() {
            private String strKey;

            public Void run() {
                HighScoresTable highScoreTab;
                try {
                    if (highScoresFile.exists()) {
                        highScoreTab = HighScoresTable.loadFromFile(highScoresFile);
                    } else {
                        highScoreTab = new HighScoresTable(10);
                        highScoreTab.save(highScoresFile);
                    }
                } catch (Exception exception) {
                    highScoreTab = new HighScoresTable(10);
                }
                final HighScoresTable highScoresTa = highScoreTab;
                animation.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                        KeyboardSensor.SPACE_KEY, new HighScoresAnimation(highScoresTa, strKey)));
                return null;
            }
        };
        Task<Void> quitTheGame = new Task<Void>() {
            public Void run() {
                gui.close();
                System.exit(0);
                return null;
            }
        };

        //the selections
        taskMenuAnimation.addSelection("s", "(s) Start Game", startGame);
        taskMenuAnimation.addSelection("h", "(h) High Score", highSco);
        taskMenuAnimation.addSelection("q", "(q) Quit", quitTheGame);

        while (true) {
            animation.run(taskMenuAnimation);
            //the user's key selection
            Task<Void> task = taskMenuAnimation.getStatus();
            task.run();
        }
    }
}


