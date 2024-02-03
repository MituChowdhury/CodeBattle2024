package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import TowerDefense.*;
import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.SubTile;

import static TowerDefense.Constants.BOARD_DASH_WIDTH;
import static TowerDefense.Constants.SUBTILE_SIZE;
import static view.BoardView.CELL_SIZE;

public class AttackerView {
    private static final int WALK_DURATION = 200;
    private static final int DEATH_DURATION = 800;
    private static final int HURT_DURATION = 250;
    private static final int JUMP_DURATION = 200;
    private static final int STAB_DURATION = 500;
    private static final int CELEBRATE_DURATION = 150;
    private static final int SPAWN_DURATION =320;

    private static ArrayList<ArrayList<Group>> spriteCache = new ArrayList<>();
    private final String[] attackerBodySprites,  attackerHurtSprites, attackerDeadSprites,
            attackerSpawnSprites, attackerJumpSprites, attackerUpStabSprites,
            attackerLeftStabSprites, attackerDownStabSprites;;


    private Attacker attacker;
    private Group group;
    private Sprite glueSprite = null;
    private SpriteAnimation attackerBody;
    private Rectangle healthBarRed; // Health bar...
    private Rectangle healthBarGreen; // Health bar...
    public static final int HEALTH_BAR_LEN = 100;  // Length of the health bar...
    private GraphicEntityModule graphics;
    private TooltipModule tooltips;



	static {
		spriteCache.add(new ArrayList<Group>());
		spriteCache.add(new ArrayList<Group>());
	}

	Circle shockWaveEffect;
	final Random random = new Random();

    private String getResourcePath(String type) {
        if (attacker.getOwner().getIndex() == 0) {
            return "hero_red_" + type + ".png";
        }
        return "hero_blue_" + type + ".png";
    }

    public void updateTooltip() {
        StringBuilder sb = new StringBuilder();
        sb.append("Attacker\n");
        sb.append("x: ").append(attacker.getCurrentTile().getX()).append("\ny: ").append(attacker.getCurrentTile().getY());
        sb.append("\nid: ").append(attacker.getId());
        sb.append("\nowner: ").append(attacker.getOwner().getIndex());
        tooltips.setTooltipText(attackerBody, sb.toString());
    }



    public AttackerView(Attacker attacker, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
        this.attacker = attacker;
        this.graphics = graphics;
        this.tooltips = tooltips;
        attacker.setView(this);

        healthBarRed = graphics.createRectangle().setWidth(HEALTH_BAR_LEN)
                .setHeight(8).setX(-60).setY(70)
                .setFillColor(0xff0000)
                .setZIndex(attacker.getCurrentTile().getY());

        healthBarGreen = graphics.createRectangle().setWidth(HEALTH_BAR_LEN)
                .setHeight(8).setX(-60).setY(70)
                .setFillColor(0x00ff00)
                .setZIndex(attacker.getCurrentTile().getY());

        attackerBodySprites = graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("walk"))
                .setHeight(64).setWidth(64).setImageCount(3)
                .setImagesPerRow(3).setOrigRow(0).setOrigCol(0).setName("ah" + attacker.getOwner().getIndex())
                .split();


        attackerBody = graphics.createSpriteAnimation().
                setImages(attackerBodySprites).
                setScale(3).
                setDuration(WALK_DURATION).setLoop(true).setPlaying(true).
                setZIndex(attacker.getCurrentTile().getY());


        group = graphics.createGroup(healthBarRed, healthBarGreen, attackerBody)
                .setX((int) (BoardView.CELL_SIZE * attacker.getCurrentSubTile().getX()))
                .setY((int) (BoardView.CELL_SIZE * attacker.getCurrentSubTile().getY()))
                .setZIndex(0);

        attackerBody.setX(-BoardView.CELL_SIZE);


        if (attacker.getOwner().getIndex() == 1) {
            group.setScaleX(-1); // sprite ke y axis borabor invert kore
        }
        boardGroup.add(group);

        SubTile t = attacker.getCurrentSubTile();
        if (attacker.getOwner().getIndex() == 0)
            group.setAlpha(1)
                    .setX((int) (BoardView.CELL_SIZE * (t.getX() + Constants.PLAYER0_X_OFFSET)))
                    .setY((int) (BoardView.CELL_SIZE * (t.getY() + Constants.PLAYER0_Y_OFFSET)));
        else
            group.setAlpha(1)
                    .setX((int) (BoardView.CELL_SIZE * (t.getX() + Constants.PLAYER1_X_OFFSET)))
                    .setY((int) (BoardView.CELL_SIZE * (t.getY() + Constants.PLAYER1_Y_OFFSET)));

        //tooltips.setTooltipText(sprite, getTooltipString());

        attackerBody.setZIndex(attacker.getCurrentTile().getY());
//        System.err.println("AttackerBody initial Z index : " + attacker.getCurrentTile().getY());
        graphics.commitEntityState(1,attackerBody);

        // Creating the animation of attacker getting attacked...
        attackerHurtSprites = graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("hurt"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(4).setOrigRow(0).setOrigCol(0).
                setName("hurt" + attacker.getOwner().getIndex() ).split();

        attackerSpawnSprites = graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("spawn"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(6).setOrigRow(0).setOrigCol(0).
                setName("spawn" + attacker.getOwner().getIndex() ).split();

        attackerDeadSprites =graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("dead"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(8).setOrigRow(0).setOrigCol(0).
                setName("dead" + attacker.getOwner().getIndex() ).split();

        attackerJumpSprites =graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("jump"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(5).setOrigRow(0).setOrigCol(0).
                setName("jump" + attacker.getOwner().getIndex() ).split();

        // attacker stabbing
//        attackerRightStabSprites =graphics.createSpriteSheetSplitter()
//                .setSourceImage(getResourcePath("stab"))
//                .setHeight(64).setWidth(64).setImageCount(4)
//                .setImagesPerRow(5).setOrigRow(0).setOrigCol(0).
//                setName("stab" + attacker.getOwner().getIndex() ).split();

        attackerLeftStabSprites = graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("leftstab"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(5).setOrigRow(0).setOrigCol(0).
                setName("leftstab" + attacker.getOwner().getIndex() ).split();

        attackerUpStabSprites =graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("upstab"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(5).setOrigRow(0).setOrigCol(0).
                setName("upstab" + attacker.getOwner().getIndex() ).split();

        attackerDownStabSprites = graphics.createSpriteSheetSplitter()
                .setSourceImage(getResourcePath("downstab"))
                .setHeight(64).setWidth(64).setImageCount(4)
                .setImagesPerRow(5).setOrigRow(0).setOrigCol(0).
                setName("downstab" + attacker.getOwner().getIndex()).split();

        spawnAnimation();
    }

    public void animateAttackerHurt() {
        changeAnimation(attackerHurtSprites,HURT_DURATION);
    }
    public void animateAttackerWalk(){
        if(attackerBody.getImages()!=attackerBodySprites)
            changeAnimation(attackerBodySprites,WALK_DURATION);
    }

    public void animateAttackerStab(String dir) {
        Tile c = attacker.getCurrentTile();
        SubTile cs= attacker.getCurrentSubTile();
        SubTile ts = null;
        switch (dir) {
            case "UP":
                ts=c.getSubTile(cs.getSubX(),0);
                move(ts);
                changeAnimation(attackerUpStabSprites,STAB_DURATION);
                //move the attacker closer to attacking tile
                break;
            case "DOWN":
                ts=c.getSubTile(cs.getSubX(),SUBTILE_SIZE-1);
                move(ts);
                changeAnimation(attackerDownStabSprites,STAB_DURATION);
                break;
            case "LEFT":
                ts=c.getSubTile(0,cs.getSubY());
                move(ts);
                changeAnimation(attackerLeftStabSprites,STAB_DURATION);
                break;
            case "RIGHT":
                ts=c.getSubTile(SUBTILE_SIZE-1,cs.getSubY());
                move(ts);
                changeAnimation(attackerLeftStabSprites,STAB_DURATION);
                break;
        }
    }

    public void spawnAnimation() {
        SubTile t = attacker.getCurrentSubTile();
        if (attacker.getOwner().getIndex() == 0) {
            group.setX((int) (BoardView.CELL_SIZE * (t.getX() + Constants.PLAYER0_X_OFFSET)));
            group.setY((int) (BoardView.CELL_SIZE * (t.getY() + Constants.PLAYER0_Y_OFFSET)));
        } else {
            group.setX((int) (BoardView.CELL_SIZE * (t.getX() + Constants.PLAYER1_X_OFFSET)));
            group.setY((int) (BoardView.CELL_SIZE * (t.getY() + Constants.PLAYER1_Y_OFFSET)));
        }

        attackerBody.setZIndex(attacker.getCurrentTile().getY());
        group.setAlpha(0).setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(0,group);

        attackerBody.setZIndex(attacker.getCurrentTile().getY());
        group.setAlpha(1).setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(0,group);

        attackerBody.setImages(attackerSpawnSprites);
        attackerBody.setDuration(SPAWN_DURATION);
        attackerBody.reset();

        attackerBody.setLoop(false).setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(0, attackerBody);


        attackerBody.setImages(attackerBodySprites);
        attackerBody.setLoop(true).setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(.5, attackerBody);

        this.healthBarGreen.setWidth(HEALTH_BAR_LEN);
    }


    public void move(SubTile nextSubTile) {

        attackerBody.setZIndex(attacker.getCurrentTile().getY());
        group.setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(0, attackerBody);


        if (attacker.getOwner().getIndex() == 0) {
            group.setX((int) (BoardView.CELL_SIZE * (nextSubTile.getX() + Constants.PLAYER0_X_OFFSET)));
            group.setY((int) (BoardView.CELL_SIZE * (nextSubTile.getY() + Constants.PLAYER0_Y_OFFSET)));
        } else {
            group.setX((int) (BoardView.CELL_SIZE * (nextSubTile.getX() + Constants.PLAYER1_X_OFFSET)));
            group.setY((int) (BoardView.CELL_SIZE * (nextSubTile.getY() + Constants.PLAYER1_Y_OFFSET)));
        }



        attacker.setCurrentSubtile(nextSubTile);

    }



    public void dealDamage(int hp, int maxHp) {
        System.err.println("Bar length: " + (int) (AttackerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
        this.healthBarGreen.setWidth((int) (AttackerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
        animateAttackerHurt();
    }



    public String getTooltipString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x: ").append(attacker.getLocation().getX()).append("\ny: ").append(attacker.getLocation().getY());
        sb.append("\nid: ").append(attacker.getId());
        sb.append("\nowner: ").append(attacker.getOwner().getIndex());
        sb.append("\nhp: ").append(attacker.getHitPoints());
        sb.append("\nspeed: ").append(attacker.getSpeed());
        sb.append("\nslowdown: ").append(attacker.getSlowCountdown()).append(" rounds");
        sb.append("\nbounty: ").append(attacker.getBounty());

        return sb.toString();
    }

    public void kill() {
        if (attacker.hasReachedTarget()) {
           changeAnimation(attackerJumpSprites,CELEBRATE_DURATION);
        } else {
            changeAnimation(attackerDeadSprites,DEATH_DURATION,.8);
        }
    }

    public void disappear() {
        group.setVisible(false);
    }
    private void changeAnimation(String[] newImages, int duration){
        changeAnimation(newImages,duration,0);
    }
    private void changeAnimation(String[] newImages, int duration, double t) {
        attackerBody.setImages(newImages);
        attackerBody.setDuration(duration);
        attackerBody.reset();
        attackerBody.setZIndex(attacker.getCurrentTile().getY());
        graphics.commitEntityState(t, attackerBody);
    }


}
