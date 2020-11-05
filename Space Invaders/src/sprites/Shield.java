package sprites;

import ass.ShieldRemover;
import biuoop.DrawSurface;
import game.GameLevel;
import ass.HitListener;
import ass.HitNotifier;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the Sprite interface.
 *
 * @author Ronit Shternfeld
 * @version 23 April 2018
 */
public class Shield implements Collidable, Sprite, HitNotifier {
    private Rectangle shield;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Shield.
     *
     * @param point point
     * @param wid   wid
     * @param heigh heigh
     */
    public Shield(Point point, int wid, int heigh) {
        this.shield = new Rectangle(point, wid, heigh);
    }

    /**
     * addHitListener.
     *
     * @param hl hl
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * removeHitListener.
     *
     * @param hitListener hitListener
     */
    public void removeHitListener(ShieldRemover hitListener) {
        this.hitListeners.remove(hitListener);
    }

    /**
     * getCollisionRectangle.
     *
     * @return Rectangle
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.shield.getUpperLeft(), this.shield.getWidth(), this.shield.getHeight());
    }

    /**
     * Velocity.
     *
     * @param hitter          hitter
     * @param collisionPoint  collisionPoint
     * @param currentVelocity currentVelocity
     * @returncurrentVelocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        hitter.notifyHit(this);
        return currentVelocity;
    }

    /**
     * notifyHit.
     *
     * @param hitter hitter
     */
    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener listener : listeners) {
            listener.hitEvent(this, hitter);
        }
    }

    /**
     * drawOn.
     *
     * @param surface surface
     */
    public void drawOn(DrawSurface surface) {
        double width = this.getCollisionRectangle().getWidth();
        double height = this.getCollisionRectangle().getHeight();
        surface.setColor(Color.cyan);
        surface.fillRectangle((int) this.shield.getUpperLeft().getX(), (int) this.shield.getUpperLeft().getY(),
                (int) width, (int) height);
    }

    /**
     * timePassed.
     *
     * @param dt dt
     */
    public void timePassed(double dt) {
    }

    /**
     * addToGame.
     *
     * @param gameLevel
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * removeFromGame.
     *
     * @param gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * removeHitListener.
     *
     * @param hl hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}