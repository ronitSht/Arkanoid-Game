package ass;

import game.GameLevel;
import sprites.Alien;
import sprites.Ball;
import sprites.Paddle;
import sprites.Shield;

public class ShieldRemover implements HitListener {
    private GameLevel game;

    public ShieldRemover(GameLevel game) {
        this.game = game;
    }

    @Override
    public void hitEvent(Shield beingHit, Ball ball) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);

    }

    @Override
    public void hitEvent(Paddle hitted, Ball ball) {
    }

    @Override
    public void hitEvent(Alien beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
    }
}

