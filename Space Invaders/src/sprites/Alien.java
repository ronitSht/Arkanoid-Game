package sprites;

import ass.HitListener;
import ass.HitNotifier;
import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import ass.HitListener;
import ass.HitNotifier;
import geometry.Point;
import geometry.Rectangle;


import javax.imageio.ImageIO;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates the Spreite interface.
 *
 * @author Ronit Shternfeld
 * @version 23 April 2018
 */
public class Alien implements Collidable, Sprite, HitNotifier {
    private Rectangle alien;
    private double startY, startX;
    private Image image;
    private List<HitListener> hitListeners = new ArrayList<>();
    private Point upperLeft;
    private int width;
    private double alienSpeed;
    private int x, y;
    private int hitPoints;
    private int leftLimit = 0;
    private int rightLimit = 0;
    private int column;
    private boolean moveLeft, moveRight;
    private List<Alien> alienList = new ArrayList<>();

    //  public Alien(int wid, Point point, Rectangle aliens) {
    //      this.width = wid;
    //     this.upperLeft = point;
    //     this.alien = aliens;
    // }

    /**
     * Creates the alien.
     *
     * @param point point
     */
    public Alien(Point point, int wid, int heigh) {
        this.alien = new Rectangle(point, wid, heigh);
        this.startX = point.getX();
        this.startY = point.getY();
        Random rand = new Random();
        this.alienSpeed = 70;
        try {
            // this.image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("spider" + Integer.toString(rand.nextInt(5)) + ".png"));
            this.image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("resources/block_images/enemy.png"));
        } catch (Exception e) {
            throw new RuntimeException("Cannot find the image!");
        }
    }

    public Alien(Point point, int wid, int heigh, int col) {
        this.alien = new Rectangle(point, wid, heigh);
        this.startX = point.getX();
        this.startY = point.getY();
        this.column = col;
        this.alien = new Rectangle(point, wid, heigh);
        this.leftLimit = this.column * 40;
        this.rightLimit = 400 + this.column * 40;
        this.alienSpeed = 200;
        Random rand = new Random();
        try {
            // this.image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("spider" + Integer.toString(rand.nextInt(5)) + ".png"));
            this.image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("resources/block_images/enemy.png"));
        } catch (Exception e) {
            throw new RuntimeException("Cannot find the image!");
        }
    }

    /**
     * Sets the place of the alien.
     *
     * @param x axe
     * @param y axe
     */
    public void setAlien(double x, double y) {
        Point point = new Point(x, y);
        this.alien = new Rectangle(point, 40, 30);
    }

    /**
     * Makes the alien move
     *
     * @param dt dt
     */
    public void movet(double dt) {
        // double x = this.alien.getUpperLeft().getX() + 55*dt;
        // this.setUpperLeft(x, this.alien.getUpperLeft().getY());
        if (this.alien.getUpperLeft().getX() < 760) {
            Point newUpperLeft = new Point(this.alien.getUpperLeft().getX() + dt,
                    this.alien.getUpperLeft().getY());
            this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
        }
    }

    /**
     * Makes the alien move right.
     *
     * @param dt dt
     */
    public void moveDown(double dt) {
        //if (this.alien.getUpperLeft().getX() <= this.rightLimit) {
        //if ((this.alien.getUpperLeft().getX() < 760) && (this.alien.getUpperLeft().getX() > 0)) {
        this.alienSpeed *= -1.1;
        Point newUpperLeft = new Point(this.alien.getUpperLeft().getX(),
                this.alien.getUpperLeft().getY() + 400 * dt);
        this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
    }

    /**
     * move .
     *
     * @param dt dt
     */
    public void move(double dt) {
        //if (this.alien.getUpperLeft().getX() <= this.rightLimit) {
        //if ((this.alien.getUpperLeft().getX() < 760) && (this.alien.getUpperLeft().getX() > 0)) {
        Point newUpperLeft = new Point(this.alien.getUpperLeft().getX() + alienSpeed * dt,
                this.alien.getUpperLeft().getY());
        this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
        //}
        /*
        else{
            //else if (this.alien.getUpperLeft().getX() >= this.leftLimit) {
       //     Point newUpperLeft = new Point(this.alien.getUpperLeft().getX() - 65 * dt,
       //             this.alien.getUpperLeft().getY());
            this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
            this.moveLeft = false;
            this.moveRight = true;
        }
        */
    }

    /**
     * drawOn.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.drawImage((int) this.alien.getUpperLeft().getX(), (int) this.alien.getUpperLeft().getY(), this.image);
    }

    /**
     * timePassed.
     *
     * @param dt dt
     */
    public void timePassed(double dt) {
        move(dt);
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
     * @param hl hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
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
     * getCollisionRectangle.
     *
     * @return Rectangle
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.alien.getUpperLeft(), this.alien.getWidth(), this.alien.getHeight());
    }

    @Override
    // public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
    //      return null;
    //  }

    /**
     *  hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        hitter.notifyHit(this);
        return currentVelocity;
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

    /*public double getX() {
        return this.alien.getX();
    }

    public double getY() {
        return this.alien.getY();
    */////////}

    // public void addHitListener(HitListener hl) {

    //  }

    /**
     * getUpperLeft.
     *
     * @return upperLeft
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * getWidth.
     *
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * getStartX.
     *
     * @return startX
     */
    public double getStartX() {
        return startX;
    }

    /**
     * getStartY.
     *
     * @return startY
     */
    public double getStartY() {
        return startY;
    }

    /**
     * setUpperLeft.
     *
     * @param startX startX
     * @param startY startY
     */
    public void setUpperLeft(double startX, double startY) {
        Point upperLeft = new Point(this.alien.getUpperLeft().getX(), this.alien.getUpperLeft().getY());
        this.alien = new Rectangle(upperLeft, 40, 30);
    }

    /**
     * getY.
     *
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * getX.
     *
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * getHitPoints.
     *
     * @return hitPoints
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setAlienSpeed(double alienSpeed) {
        this.alienSpeed = alienSpeed;
    }
}