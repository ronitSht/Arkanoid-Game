package ass;

import sprites.*;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ronit Shternfeld
 * @version 24 April 2018
 */
public interface HitListener {

    /**
     * hitEvent.
     *
     * @param beingHit beingHit
     * @param hitter   hitter
     */
    void hitEvent(Alien beingHit, Ball hitter);

    /**
     * hitEvent.
     *
     * @param beingHit beingHit
     * @param hitter   hitter
     */
    void hitEvent(Shield beingHit, Ball hitter);

    /**
     * hitEvent.
     *
     * @param hitted hitted
     * @param ball   ball
     */
    void hitEvent(Paddle hitted, Ball ball);
}
