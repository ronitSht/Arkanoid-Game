package sprites;

import game.GameLevel;
import geometry.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates the Spreite interface.
 *
 * @author Ronit Shternfeld
 * @version 23 April 2018
 */
public class Group implements Sprite {
    private List<Alien> alienList = new ArrayList<>();
    private GameLevel game;
    private long lastShot;
    private double speed;
    private double speedBefore;
    private boolean lose;
    double leftMostPoint = 800;
    double rightMostPoint = 0;
    double leftSide = 0;
    double rightSide = 0;

    public Group(GameLevel gameLevels, Double speeds, List<Alien> alienList) {
        this.game = gameLevels;
        this.speed = speeds;
        this.alienList = new ArrayList<>();
        this.lose = false;
        this.speedBefore = this.speed;
        this.lastShot = 0;
        this.alienList = alienList;
    }

    /**
     * drawOn.
     *
     * @param drawSurface drawSurface
     */
    public void drawOn(biuoop.DrawSurface drawSurface) {
        for (Alien alien : this.alienList) {
            alien.drawOn(drawSurface);
        }
    }

    /**
     * timePassed.
     *
     * @param dt dt
     */
    public void timePassed(double dt) {
        this.move(dt);
        this.shoot();
    }

  /*  public List<Alien> minList(HashMap<Double, List<Alien>> map) {
        Alien min = null;
        //ArrayList<Alien> minList = new ArrayList<>();
        List<Alien> minList = this.alienList;
        Iterator iterator = minList.iterator();
        System.out.println(minList);
        while (iterator.hasNext()) {
            System.out.println("tttddd");
            Map.Entry pair = (Map.Entry) iterator.next();
            ArrayList<Alien> aliensList = ((ArrayList<Alien>) pair.getValue());
            for (Alien alien : minList) {
                min = minList.get(0);
                if (min.getY() <= alien.getY()) {
                    min = alien;
                }
            }
            minList.add(min);
            System.out.println(min+"ddd");
            iterator.remove();
        }
        return minList;
  *//////////  }

  /*  public List<Alien> findMinimalList(List<Alien> aliens) {
        HashMap<Double, List<Alien>> aliensMap = new HashMap<>();
        for (int i = 0; i < aliens.size(); i++) {
            double x = this.alienList.get(i).getX();
            if (aliensMap.containsKey(x)) {
                aliensMap.get(x).add(this.alienList.get(i));
            } else {
                List<Alien> alList = new ArrayList<>();
                aliensMap.put(x, alList);
                aliensMap.get(x).add(this.alienList.get(i));
            }
        }
        return this.minList(aliensMap);
  */////////////  }

    /**
     * shoot.
     */
    public void shoot() {
        if (System.currentTimeMillis() - this.lastShot > 500) {
            //List<Alien> minimalList = findMinimalList(this.alienList);
            List<Alien> minimalList = this.alienList;
            // try {
            // if (minimalList.size() > 0) {
            Random random = new Random();
            // Alien min = minimalList.get(random.nextInt(10));
            //Alien min = minimalList.get(random.nextInt(2));
            Alien min = minimalList.get(random.nextInt(minimalList.size()));
            Ball shotBall = this.game.alienShot(new Point(min.getCollisionRectangle().getEndOfScreen().middle().getX(),
                    min.getCollisionRectangle().getEndOfScreen().middle().getY()), 5, Color.red);
            shotBall.setVelocity(Velocity.fromAngleAndSpeed(180, 200));
            shotBall.addToGame(this.game);
            this.lastShot = System.currentTimeMillis();
        }
        // }// catch (Exception e) {
        //   throw new RuntimeException("n must be positive!");
        //}
        // }
    }

    /**
     * add.
     *
     * @param alien alien
     */
    public void add(Alien alien) {
        this.alienList.add(alien);
    }

    /**
     * remove.
     *
     * @param alien alien
     */
    public void remove(Alien alien) {
        this.alienList.remove(alien);
    }

   /* public void moveYY(double dt) {
        if ((this.alienList.getUpperLeft().getX() < 760)&&(this.alien.getUpperLeft().getX()>0)) {
            Point newUpperLeft = new Point(this.alien.getUpperLeft().getX() + 65 * dt,
                    this.alien.getUpperLeft().getY());
            this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
        }else{

            Point newUpperLeft = new Point(this.alien.getUpperLeft().getX() - 65 * dt,
                    this.alien.getUpperLeft().getY());
            this.alien.getCollisionRectangle().setUpperLeft(newUpperLeft);
        }
   *////////// }


    public void move(double dt) {
        if (isOneAlienReachesLimit() == true) {
            for (Alien alien : alienList) {
                alien.moveDown(dt);
            }
        }
        for (Alien alien : alienList) {
            alien.move(dt);
        }
    }
      /*  for (Alien current : this.alienList) {
            double xPosition = current.
                    getCollisionRectangle().getUpperLeft().getX();
            if (xPosition > rightMostPoint) {
                rightMostPoint = xPosition;
            }
            if (xPosition < leftMostPoint) {
                leftMostPoint = xPosition;
            }
        }

        this.leftSide = leftMostPoint;
      *///////////  this.rightSide = rightMostPoint + 40;


    //  this.speed = -60;
   /*     for(
    int i = 0;
    i< this.alienList.size();i++)

    {
        //if (this.alienList.get(i).getX() <= leftSide){
        if ((this.alienList.get(i).getX() <= 0) || (this.alienList.get(i).getX() <= 760)) {
            this.speed = -this.speed * 1.1;
            for (Alien alien : this.alienList) {
                alien.setUpperLeft(alien.getX(), alien.getY() + 20);
            }
            break;
        }
    }
        for(
    Alien alien :this.alienList)

    {
        if (alien.getY() + 30 >= 450) {
            this.lose = true;
        }
    }
        for(
    Alien alien :this.alienList)

    {
        alien.move(this.speed * dt);
  *////////////  }

/////}

    /**
     * isLose.
     *
     * @return lose
     */
    public boolean isLose() {
        return this.lose;
    }

    /**
     * reset.
     */
    public void reset() {
        for (Alien alien : this.alienList) {
            alien.setUpperLeft(alien.getStartX(), alien.getStartY());
        }
        this.lose = false;
        this.speed = this.speedBefore;
    }

    /**
     * getAlienList.
     *
     * @return alienList
     */
    public List<Alien> getAlienList() {
        return this.alienList;
    }

    /**
     * isOneAlienReachesLimit.
     *
     * @return true or false
     */
    public boolean isOneAlienReachesLimit() {
        for (Alien alien : alienList) {
            if (alien.getCollisionRectangle().getUpperLeft().getX() < 0) {
                return true;
            }
            if (alien.getCollisionRectangle().getUpperLeft().getX() > 760) {
                return true;
            }
        }
        return false;
    }
}
