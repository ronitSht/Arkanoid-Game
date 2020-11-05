package gamelevels;

import geometry.Point;
import sprites.Alien;
import sprites.Sprite;
import sprites.Velocity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ronit Shternfeld
 * @version 24 April 2018
 */
public class CreateLevels implements LevelInformation {
    private int numBlocks;
    private Sprite background;
    private ColorsParser setting;
    private String levelName;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String path;
    //  private LevelSpecificationReader levelSpecification;
    private List<LevelInformation> levelInformationList = new ArrayList<>();

    /**
     * CreateLevels.
     *
     * @param levSpec levSpec
     */
    //public CreateLevels(LevelSpecificationReader levSpec) {
    //    this.levelSpecification = levSpec;
    //}

    /**
     * CreateLevels.
     */
    public CreateLevels() {
    }

    /**
     * CreateLevels.
     *
     * @param numBlock           numBlock
     * @param levelNam           levelNam
     * @param velocit            velocit
     * @param levelInformationLi levelInformationLi
     * @param backg              backg
     */
    public CreateLevels(int numBlock, String levelNam,
                        List<Velocity> velocit, List<LevelInformation> levelInformationLi, Sprite backg) {
        this.numBlocks = numBlock;
        this.background = backg;
        this.levelName = levelNam;
        this.velocities = velocit;
        this.levelInformationList = levelInformationLi;
    }

    /**
     * getLevelInformationList.
     *
     * @return levelInformationList
     */
    public List<LevelInformation> getLevelInformationList() {
        //CreateLevels lI = new CreateLevels(this.levelSpecification);
        //this.levelInformationList.add(lI);
        return this.levelInformationList;
    }

    /**
     * setBlocks.
     *
     * @param list list
     */
    // public void setBlocks(List<Block> list) {
    //    this.blocks = list;
    // }

    /**
     * setBackground.
     *
     * @param backg backg
     */
    public void setBackground(Sprite backg) {
        this.background = backg;
    }

    /**
     * setBackground.
     *
     * @param colorParser colorParser
     */
    public void setBackground(ColorsParser colorParser) {
        this.setting = colorParser;
    }

    /**
     * numberOfBalls.
     *
     * @return velocities
     */
    // public int numberOfBalls() {
    //     return this.velocities.size();
    // }

    /**
     * initialBallVelocities.
     *
     * @return velocities
     */
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * paddleSpeed.
     *
     * @return paddleSpeed
     */
    public int paddleSpeed() {
        return 650;
    }

    /**
     * paddleWidth.
     *
     * @return paddleWidth
     */
    public int paddleWidth() {
        return 80;
    }

    /**
     * levelName.
     *
     * @return levelName
     */
    public String levelName() {
        return "Battle no.1";
    }

    /**
     * getBackground.
     *
     * @return background
     */
    public Sprite getBackground() {
        return new LevelImage(Color.BLACK);
    }

    /**
     * blocks.
     *
     * @return blocks
     */
    // public List<Block> blocks() {
    //    return this.blocks;
    // }

    /**
     * aliensList.
     *
     * @return aliens list
     */
    public List<Alien> aliensList() {
        List<Alien> aliensList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                aliensList.add(new Alien(new Point(1 + 50 * i, 30 + 50 * j),20,20));
            }
        }
        return aliensList;
    }

    /**
     * numBlocks.
     *
     * @return numBlocks
     */
    // public int numberOfBlocksToRemove() {
    //     return this.numBlocks;
    // }
    public int numberOfBlocksToRemove() {
        return this.aliensList().size();
    }

    /**
     * levelName.
     *
     * @param str levelName
     */
    public void setLevelName(String str) {
        this.levelName = str;
    }

    /**
     * setVelocities.
     *
     * @param list list
     */
    public void setVelocities(List list) {
        this.velocities = list;
    }

    /**
     * setPaddleSpeed.
     *
     * @param speed paddleSpeed
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * setPaddleWidth.
     *
     * @param width width
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * setNumBlocks.
     *
     * @param block block
     */
    public void setNumBlocks(int block) {
        this.numBlocks = block;
    }

    /**
     * getPath.
     *
     * @return path
     */
    public String getPath() {
        return this.path;
    }
}
