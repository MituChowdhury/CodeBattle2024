package view;

import java.util.ArrayList;
import java.util.Random;

import TowerDefense.Constants;
import TowerDefense.Tile;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.SubTile;

import static TowerDefense.Constants.BOARD_DASH_WIDTH;
import static view.BoardView.CELL_SIZE;

public class AttackerView {
	private static final int WALK_DURATION = 400;
	private static final int DEATH_DURATION = 1000;

	private static ArrayList<ArrayList<Group>> spriteCache = new ArrayList<>();

	private Attacker attacker;
	private Group group;
	private Sprite glueSprite = null;
	private SpriteAnimation attackerBody, attackerHelmet;
	private Rectangle healthBarRed; // Health bar...
	private Rectangle healthBarGreen; // Health bar...
	public static final int HEALTH_BAR_LEN = 100;  // Length of the health bar...
	private GraphicEntityModule graphics;
	private TooltipModule tooltips;

//	private String[] attackerHelmetSprites;
//	private String[] attackerBodyDeathSprites;
//	private String[] attackerHelmetDeathSprites;
//	private String[] attackerBodyWinSprites;
//	private String[] attackerHelmetWinSprites;

	static {
		spriteCache.add(new ArrayList<Group>());
		spriteCache.add(new ArrayList<Group>());
	}

	Circle shockWaveEffect;
	final Random random = new Random();

	private String getResourcePath(String type){
		if (attacker.getOwner().getIndex()==0){
			return "hero_red_"+type+".png";
		}
		return "hero_blue_"+type+".png";
	}


	public AttackerView(Attacker attacker, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		this.attacker = attacker;


		this.graphics = graphics;
		this.tooltips = tooltips;
		attacker.setView(this);
		for (Group g : spriteCache.get(attacker.getOwner().getIndex())) {
			group = g;
			SubTile t = attacker.getCurrentSubTile();
			if(attacker.getOwner().getIndex()==0)
				group.setAlpha(1)
						.setX((int) (BoardView.CELL_SIZE * (t.getX()+Constants.PLAYER0_X_OFFSET)))
						.setY((int) (BoardView.CELL_SIZE * (t.getY()+Constants.PLAYER0_Y_OFFSET)));
			else
				group.setAlpha(1)
						.setX((int) (BoardView.CELL_SIZE * (t.getX()+Constants.PLAYER1_X_OFFSET)))
						.setY((int) (BoardView.CELL_SIZE * (t.getY()+Constants.PLAYER1_Y_OFFSET)));

			graphics.commitEntityState(0, group);
			spriteCache.get(attacker.getOwner().getIndex()).remove(g);
			break;
		}
		if (group == null) {
			healthBarRed = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setX(-60).setY(70).setFillColor(0xff0000);
			healthBarGreen = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setX(-60).setY(70).setFillColor(0x00ff00);

			String[] attackerBodySprites = graphics.createSpriteSheetSplitter()
					.setSourceImage(getResourcePath("walk"))
					.setHeight(64).setWidth(64).setImageCount(3)
					.setImagesPerRow(3).setOrigRow(0).setOrigCol(0).setName("ah"+attacker.getOwner().getIndex())
					.split();

			for (int i = 0; i <attackerBodySprites.length; i++) {
				System.out.println(attackerBodySprites[i]);
			}

			attackerBody = graphics.createSpriteAnimation().
					setImages(attackerBodySprites).
					setScale(3).
					setDuration(WALK_DURATION).setLoop(true).setPlaying(true);
//			attackerHelmet = graphics.createSpriteAnimation().
//					setImages(attackerHelmetSprites).
//					setDuration(WALK_DURATION).setLoop(true).setPlaying(true).
//					setTint(attacker.getOwner().getColor());
			group = graphics.createGroup(healthBarRed, healthBarGreen, attackerBody)
					.setX((int) (BoardView.CELL_SIZE * attacker.getCurrentSubTile().getX()))
					.setY((int) (BoardView.CELL_SIZE * attacker.getCurrentSubTile().getY()));
			attackerBody.setX(-BoardView.CELL_SIZE);
//			attackerHelmet.setX(-BoardView.CELL_SIZE);
			if (attacker.getOwner().getIndex() == 1) {
				group.setScaleX(-1); // sprite ke y axis borabor invert kore
			}
			boardGroup.add(group);
		}
		//tooltips.setTooltipText(sprite, getTooltipString());



    }







	public void move(SubTile nextSubTile) {


		graphics.commitEntityState(0, attackerBody);

		if(attacker.getOwner().getIndex()==0) {
			group.setX((int) (BoardView.CELL_SIZE * (nextSubTile.getX() + Constants.PLAYER0_X_OFFSET)));
			group.setY((int) (BoardView.CELL_SIZE * (nextSubTile.getY() +Constants.PLAYER0_Y_OFFSET)));
		}
		else{

			group.setX((int) (BoardView.CELL_SIZE * (nextSubTile.getX() + Constants.PLAYER1_X_OFFSET)));
			group.setY((int) (BoardView.CELL_SIZE * (nextSubTile.getY() +Constants.PLAYER1_Y_OFFSET)));
		}
		attacker.setCurrentSubtile(nextSubTile);


//		shockWaveEffect
//				.setX((int) (BoardView.CELL_SIZE * nextSubTile.getX()))
//				.setY((int) (BoardView.CELL_SIZE * nextSubTile.getY()))
//				.setRadius(20,Curve.EASE_IN)
//				.setLineWidth(3, Curve.EASE_IN)
//				.setLineAlpha(1,Curve.EASE_IN)
//				.setLineColor(0xffffff,Curve.EASE_IN)
//				.setFillAlpha(0);
//
//		graphics.commitEntityState(0, shockWaveEffect);
//
////		shockWaveEffect.setRadius(50,Curve.EASE_IN)
////				.setLineAlpha(.5,Curve.EASE_IN);
////		graphics.commitEntityState(.4, shockWaveEffect);
////
////		shockWaveEffect.setRadius(70,Curve.EASE_IN)
////				.setLineAlpha(.2,Curve.EASE_IN);
////		graphics.commitEntityState(.8, shockWaveEffect);
//
//		shockWaveEffect
//				.setRadius(100,Curve.EASE_OUT)
//				.setLineAlpha(0,Curve.EASE_OUT)
//				.setLineWidth(0,Curve.EASE_OUT);
//
//		graphics.commitEntityState(1, shockWaveEffect);

		//tooltips.setTooltipText(sprite, getTooltipString());
	}

	public void dealDamage(int hp, int maxHp) {
		System.err.println("Bar length: " + (int) (AttackerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
		this.healthBarGreen.setWidth((int) (AttackerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
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
		if (attacker.canRespawn()) {
//			changeAnimation(attackerBodyWinSprites, attackerHelmetWinSprites);
		} else {
//			changeAnimation(attackerBodyDeathSprites, attackerHelmetDeathSprites);
			graphics.commitEntityState(0.9, group);
			group.setVisible(false);
			//spriteCache.get(attacker.getOwner().getIndex()).add(group);
		}
	}

	public void disappear() {
		group.setVisible(false);
	}

	private void changeAnimation(String[] body, String[] helmet) {
		attackerBody.setImages(body);
		attackerBody.setDuration(DEATH_DURATION);
		attackerBody.reset();
		attackerHelmet.setImages(helmet);
		attackerHelmet.setDuration(DEATH_DURATION);
		attackerHelmet.reset();
		graphics.commitEntityState(0, attackerBody);
		graphics.commitEntityState(0, attackerHelmet);
	}
}
