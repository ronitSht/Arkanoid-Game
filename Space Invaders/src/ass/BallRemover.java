package ass;

import game.GameLevel;
import sprites.*;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ronit Shternfeld
 * @version 24 April 2018
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;
    private boolean stop;

    /**
     * BallRemover.
     *
     * @param game         game
     * @param removedBalls removedBalls
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * Removes the ball.
     *
     * @param gameLevel gameLevel
     */
    public BallRemover(GameLevel gameLevel) {
        this.game = gameLevel;
    }

    /**
     * Stops if the player loses.
     *
     * @return boolean
     */
    public boolean isLose() {
        return this.stop;
    }

    /**
     * reset.
     */
    public void reset() {
        this.stop = false;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit beingHit
     * @param hitter   hitter
     */
    public void hitEvent(Alien beingHit, Ball hitter) {
        //if (hitter.getColor().equals("e")) {
        //     return;
        //}
        if (hitter == null) {
            return;
        }
        //this.remainingBalls.decrease(1);
        //this.game.removeSprite(hitter);
        hitter.removeFromGame(this.game);
        hitter.removeHitListener(this);
    }

    @Override
    public void hitEvent(Shield beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        hitter.removeHitListener(this);
    }

    @Override
    public void hitEvent(Paddle hitted, Ball ball) {

    }
}
