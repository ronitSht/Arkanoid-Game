package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountDownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import ass.AlienRemover;
import ass.BallRemover;
import ass.BlockRemover;
import ass.Counter;
import ass.HitListener;
import ass.ScoreTrackingListener;
import ass.ShieldRemover;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gamelevels.LevelInformation;
import geometry.Point;
import sprites.Alien;
import sprites.Ball;
import sprites.Collidable;
import sprites.Group;
import sprites.LevelName;
import sprites.LivesIndicator;
import sprites.Paddle;
import sprites.ScoreIndicator;
import sprites.Shield;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the game - the blocks, the paddle and the two balls.
 *
 * @author Ronit Shternfeld
 * @version 30 Mars 2018
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private Paddle paddleOfGame;
    private SpriteCollection sprites;
    private GameEnvironment gameEnvironment;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter score;
    private Counter livesCounter;
    private GUI gui;
    private KeyboardSensor keyboard;
    private LevelInformation levelInfo;
    private List<Ball> shotsList = new ArrayList<>();
    private List<Shield> shieldList = new ArrayList<>();
    private Group aliensGroup;
    private BallRemover ballRemover = new BallRemover(this);
    private double velocity;
    //this.aliensGroup = new Group(this, this.velocity);

    /**
     * @param spritesCollection SpriteCollection
     * @param levelInformation  LevelInformation
     * @param scores            Counter
     * @param lives             Counter
     * @param animationRunner   AnimationRunner
     * @param keyboardSensor    KeyboardSensor
     * @param gui               GUI
     */
    public GameLevel(SpriteCollection spritesCollection, LevelInformation levelInformation,
                     Counter scores, Counter lives, AnimationRunner animationRunner, KeyboardSensor keyboardSensor,
                     GUI gui) {
        this.gameEnvironment = new GameEnvironment();
        this.sprites = spritesCollection;
        this.levelInfo = levelInformation;
        this.score = scores;
        this.livesCounter = lives;
        this.blocksCounter = new Counter(0);
        this.ballsCounter = new Counter(0);
        this.keyboard = keyboardSensor;
        this.runner = animationRunner;
        this.gui = gui;
        this.aliensGroup = new Group(this, this.velocity, new ArrayList<Alien>());
    }


    /**
     * Adds the collidables.
     *
     * @param c collidables
     */
    public void addCollidable(Collidable c) {
        gameEnvironment.addCollidable(c);
    }

    /**
     * Adds the sprites.
     *
     * @param s sprites
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Remove Collidable.
     *
     * @param c Collidable
     */
    public void removeCollidable(Collidable c) {
        gameEnvironment.removeCollidable(c);
    }

    /**
     * Remove Sprite.
     *
     * @param s Sprite
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        HitListener removeBlock = new BlockRemover(this, this.blocksCounter);
        HitListener removeBall = new BallRemover(this, this.ballsCounter);
        HitListener scores = new ScoreTrackingListener(this.score);
        Sprite scoreIndicator = new ScoreIndicator(this.score);
        Sprite livesIndicator = new LivesIndicator(this.livesCounter);
        Sprite levelName = new LevelName(this.levelInfo.levelName());

        // the setting
        addSprite(this.levelInfo.getBackground());

        /*
         * Creates the frame blocks.
         */
      /*  Block blockLeft = new Block(new Rectangle(new Point(0, 0), 20, 600), Color.gray);
        Block blockRight = new Block(new Rectangle(new Point(780, 0), 20, 600), Color.gray);
        Block blockUp = new Block(new Rectangle(new Point(0, 20), 800, 20), Color.gray);
        Block scoreBlock = new Block(new Rectangle(new Point(0, 0), 800, 20), Color.WHITE);
        blockLeft.addToGame(this);
        blockRight.addToGame(this);
        blockUp.addToGame(this);
        scoreBlock.addToGame(this);

        // the block that the balls which collide in it are going not to appear
        Block blockDownForBalls = new Block(new Rectangle(new Point(0, 610), 800, 5), Color.cyan);
        blockDownForBalls.addToGame(this);
       */////// blockDownForBalls.addHitListener(removeBall);

        //Creates the paddle.
        this.paddleOfGame = createPaddle();
        this.shieldList = createShields();

        //adds the aliens to the game
        List<Alien> aliensList = this.levelInfo.aliensList();
        for (Alien alien : aliensList) {
            alien.addToGame(this);
            alien.addHitListener(scores);
            alien.addHitListener(this.ballRemover);
            this.aliensGroup.add(alien);
        }

        this.blocksCounter.increase(this.levelInfo.numberOfBlocksToRemove());

        //Creates the two balls and their velocity.
        //createBallsOnTopOfPaddle();

        //adds the "Score" and "Lives" and the level name
        this.addSprite(scoreIndicator);
        this.addSprite(livesIndicator);
        this.addSprite(levelName);
    }

    /**
     * Should Stop.
     *
     * @return running
     */
    public boolean shouldStop() {
        if (this.blocksCounter.getValue() == 0 || this.aliensGroup.isLose() || this.ballRemover.isLose()) {
            if (this.aliensGroup.isLose() || this.ballRemover.isLose()) {
                this.livesCounter.decrease(1);
            }
            this.running = true;
            this.aliensGroup.reset();
            this.ballRemover.reset();
            for (Ball shot : this.shotsList) {
                shot.removeFromGame(this);
            }
            shotsList.clear();
            //this.running.clear();
        } else {
            this.running = false;
        }
        return this.running;
        //return !this.running;
    }

    /**
     * Do One Frame.
     *
     * @param d  DrawSurface
     * @param dt dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // the logic from the previous playOneTurn method goes here. The `return` or `break` statements should be
        // replaced with this.running = false;
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        // if the user presses "p", the game will stop.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }
        //100 points when there are no blocks anymore.
        if (blocksCounter.getValue() == 0) {
            score.increase(100);
            this.running = false;
        }
        // closes the window if there are no blocks or balls
        if (livesCounter.getValue() == 0) {
            this.running = false;
        }
        //if there are no balls and there are lives, decrease one life
        if ((ballsCounter.getValue() == 0) && (livesCounter.getValue() != 0)) {
        //if ((ballsCounter.getValue() == 0) && (livesCounter.getValue() != 0)) {
            livesCounter.decrease(1);
        }
        //if there are no balls and there are lives, creates the paddle and the balls
        if ((ballsCounter.getValue() == 0) && (livesCounter.getValue() != 0)) {
            //deletes the paddle, so after we lose a life we will be able to create a new paddle in the middle.
            this.removeCollidable(this.paddleOfGame);
            this.removeSprite(this.paddleOfGame);
            //create the paddle
            this.paddleOfGame = createPaddle();
            //creates the balls
            //createBallsOnTopOfPaddle();
        }
        if (blocksCounter.getValue() == 0) {
            this.running = false;
        }

        this.paddleOfGame.shoot(this);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.keyboard)));
        }
        this.aliensGroup.timePassed(dt);
    }

    /**
     * create Balls On To Of Paddle.
     */
  /*  public void createBallsOnTopOfPaddle() {
        List<Velocity> velocityList = this.levelInfo.initialBallVelocities();
        List<Ball> ballsList = new ArrayList<>();
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(400, 550), 5, Color.white);
            ballsList.add(ball);
            ballsList.get(i).setGameEnvironment(gameEnvironment);
            ballsList.get(i).setVelocity(velocityList.get(i));
            ballsList.get(i).addToGame(this);
            ballsCounter.increase(1);
        }
   *//// }

    /**
     * Creates Paddle.
     *
     * @return paddleOfGame
     */
    public Paddle createPaddle() {
        Point paddlePoint = new Point(400 - this.levelInfo.paddleWidth() / 2, 560);
        Paddle paddle = new Paddle(paddlePoint, this.levelInfo.paddleWidth(), 20, keyboard, Color.orange,
                this.levelInfo.paddleSpeed());
        paddle.addToGame(this);
        return paddle;
    }

    /**
     * createShields.
     *
     * @return List of shields
     */
    public List<Shield> createShields() {
        List<Shield> shields = new ArrayList<>();
        for (int k = 0; k < 3; k++) {
            for (int j = 0; j < 17; j++) {
                for (int i = 0; i < 3; i++) {
                    shields.add(new Shield(new Point(70 + (240 * k) + (j * 10), 480 + (i * 8)), 10, 8));
                }
                for (Shield shield : shields) {
                    shield.addToGame(this);
                    shield.addHitListener(this.ballRemover);
                }
            }
        }
        return shields;
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        this.runner.run(new CountDownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which
        // is one turn of the game.
        this.runner.run(this);
    }

    /**
     * playerShot.
     *
     * @param point point
     * @return Ball
     */
    public Ball playerShot(Point point) {
        //creates the shot
        Ball shot = new Ball(point, "p", this.gameEnvironment);
        //Ball shot = new Ball(point, 5, Color.RED);
        //adds it to the shots list
        this.shotsList.add(shot);
        //shot.addHitListener(new AlienRemover(this));
        shot.addHitListener(new AlienRemover(this, this.blocksCounter, this.aliensGroup));
        shot.addHitListener(new ShieldRemover(this));
        return shot;
    }

    /**
     * alienShot.
     *
     * @param point point
     * @param i     i
     * @param red   red
     * @return Ball
     */
    public Ball alienShot(Point point, int i, Color red) {
        Ball shot = new Ball(point, "e", this.gameEnvironment);
        //Ball shot = new Ball(point, 5, Color.RED);
        this.shotsList.add(shot);
        shot.addHitListener(new ShieldRemover(this));
        return shot;
    }
}