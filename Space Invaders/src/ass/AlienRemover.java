package ass;

import game.GameLevel;
import sprites.Alien;
import sprites.Ball;
import sprites.Group;
import sprites.Paddle;
import sprites.Shield;

public class AlienRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter blocksCounter;
    private Group aliensGroup;

    public AlienRemover(GameLevel game, Counter counter, Group alGroup) {
        this.gameLevel = game;
        this.blocksCounter = counter;
        this.aliensGroup = alGroup;
    }

    /**
     * Creates AlienRemover.
     *
   //  * @param game GameLevel
     */
  //  public AlienRemover(GameLevel game) {
   //     this.gameLevel = game;
   // }

    @Override
    public void hitEvent(Shield beingHit, Ball ball) {
        beingHit.removeFromGame(this.gameLevel);
        beingHit.removeHitListener(this);
    }

    @Override
    public void hitEvent(Paddle hitted, Ball ball) {
    }

    @Override
    public void hitEvent(Alien beingHit, Ball hitter) {
        beingHit.removeFromGame(this.gameLevel);
        beingHit.removeHitListener(this);
        //if (this.aliensGroup != null) {
            if (this.aliensGroup.getAlienList().contains(beingHit)) {
                this.aliensGroup.remove(beingHit);
                this.blocksCounter.decrease(1);
            }
       // }
    }
}

