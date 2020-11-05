package ass;

import sprites.Alien;
import sprites.Ball;
import sprites.Paddle;
import sprites.Shield;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ronit Shternfeld
 * @version 24 April 2018
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * ScoreTrackingListener.
     *
     * @param scoreCounter scoreCounter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * HitEvent.
     *  @param beingHit beingHit
     * @param hitter   hitter
     */
    public void hitEvent(Alien beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        } else {
            this.currentScore.increase(5);
        }
    }

    @Override
    public void hitEvent(Shield beingHit, Ball hitter) {

    }

    @Override
    public void hitEvent(Paddle hitted, Ball ball) {

    }
}
