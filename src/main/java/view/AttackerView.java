package view;

import java.util.ArrayList;
import java.util.Random;

import TowerDefense.Constants;
import TowerDefense.Tile;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.SubTile;

public class AttackerView {
	private static final int WALK_DURATION = 800;
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
	private String[] attackerBodySprites;
	private String[] attackerHelmetSprites;
	private String[] attackerBodyDeathSprites;
	private String[] attackerHelmetDeathSprites;
	private String[] attackerBodyWinSprites;
	private String[] attackerHelmetWinSprites;

	static {
		spriteCache.add(new ArrayList<Group>());
		spriteCache.add(new ArrayList<Group>());
	}

	Circle shockWaveEffect;
	final Random random = new Random();

	public AttackerView(Attacker attacker, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {


		if (attackerBodySprites == null) {
			attackerBodySprites = graphics.createSpriteSheetSplitter().setSourceImage("att_body.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("ab").split();
			attackerHelmetSprites = graphics.createSpriteSheetSplitter().setSourceImage("att_helmet.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("ah").split();
			attackerBodyDeathSprites = graphics.createSpriteSheetSplitter().setSourceImage("die_body.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("db").split();
			attackerHelmetDeathSprites = graphics.createSpriteSheetSplitter().setSourceImage("die_helmet.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("dh").split();
			attackerBodyWinSprites = graphics.createSpriteSheetSplitter().setSourceImage("jump_body.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("jb").split();
			attackerHelmetWinSprites = graphics.createSpriteSheetSplitter().setSourceImage("jump_helmet.png").setHeight(94).setWidth(100).setImageCount(10).setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("jh").split();
		}
		this.attacker = attacker;
		this.graphics = graphics;
		this.tooltips = tooltips;
		attacker.setView(this);
		for (Group g : spriteCache.get(attacker.getOwner().getIndex())) {
			group = g;
			Tile t = attacker.getLocation();
			group.setAlpha(1).setX((int) (BoardView.CELL_SIZE * t.getX())).setY((int) (BoardView.CELL_SIZE * t.getY()));
			graphics.commitEntityState(0, group);
			spriteCache.get(attacker.getOwner().getIndex()).remove(g);
			break;
		}
		if (group == null) {
			healthBarRed = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setX(-100).setY(-10).setFillColor(0xff0000);
			healthBarGreen = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setX(-100).setY(-10).setFillColor(0x00ff00);

			attackerBody = graphics.createSpriteAnimation().
					setImages(attackerBodySprites).
					setDuration(WALK_DURATION).setLoop(true).setPlaying(true);
			attackerHelmet = graphics.createSpriteAnimation().
					setImages(attackerHelmetSprites).
					setDuration(WALK_DURATION).setLoop(true).setPlaying(true).
					setTint(attacker.getOwner().getColor());
			group = graphics.createGroup(healthBarRed, healthBarGreen, attackerBody, attackerHelmet)
					.setX((int) (BoardView.CELL_SIZE * attacker.getLocation().getX()))
					.setY((int) (BoardView.CELL_SIZE * attacker.getLocation().getY()));
			attackerBody.setX(-BoardView.CELL_SIZE);
			attackerHelmet.setX(-BoardView.CELL_SIZE);
			if (attacker.getOwner().getIndex() == 1) {
				group.setScaleX(-1); // sprite ke y axis borabor invert kore
			}
			boardGroup.add(group);
		}
		//tooltips.setTooltipText(sprite, getTooltipString());


        shockWaveEffect = graphics.createCircle();
    }






	private int finalY = -1;


	public void move(SubTile nextSubTile) {
//		if (attacker.isSlow()) {
//			if (glueSprite == null) {
//				glueSprite = graphics.createSprite().setImage("glue" + (1 + attacker.getId() % 3) + ".png").setScale(0.3).setY(50);
//				if (attacker.getOwner().getIndex() == 1) {
//					glueSprite.setX(-BoardView.CELL_SIZE);
//				}
//				group.add(glueSprite);
//				graphics.commitEntityState(0, group, glueSprite);
//			}
//			if (glueSprite.getAlpha() == 0) {
//				glueSprite.setAlpha(1);
//				graphics.commitEntityState(0, glueSprite);
//			}
//		} else if (glueSprite != null && glueSprite.getAlpha() == 1) {
//			glueSprite.setAlpha(0);
//			graphics.commitEntityState(0, glueSprite);
//		}



		graphics.commitEntityState(0, attackerBody);
		graphics.commitEntityState(0, attackerHelmet);

//		ArrayList<SubTile> neighbours = attacker.getCurrentSubTile().getNeighbors();
//
//		double destination_x = attacker.getEnemy().getIndex() == 0 ? 0 : ((Constants.MAP_WIDTH-1)+(((double)SubTile.SUBTILE_SIZE-1)/SubTile.SUBTILE_SIZE));
//		double destination_y = attacker.getEnemy().getIndex() == 0 ? ((Constants.MAP_HEIGHT-1)+(((double)SubTile.SUBTILE_SIZE-1)/SubTile.SUBTILE_SIZE)) : 0;
//		double minDist = Double.MAX_VALUE;
//		SubTile nextSubTile = attacker.getCurrentSubTile();
//
//		for (SubTile st : neighbours) {
//			if(st.getTile().isDobstacle()) continue;
//			double dx = st.getX() - destination_x;
//			double dy = st.getY() - destination_y;
//			double dist = Math.sqrt(dx * dx + dy * dy);
//			if(dist < minDist) {
//				minDist = dist;
//			}
//		}
//
//		ArrayList<SubTile>all = new ArrayList<>();
//
//		for (SubTile st : neighbours) {
//
//			if(st.getTile().isDobstacle()) {
//				continue;
//			}
//			double dx = st.getX() - destination_x;
//			double dy = st.getY() - destination_y;
//			double dist = Math.sqrt(dx * dx + dy * dy);
//			if(dist == minDist) {
//				all.add(st);
//			}
//		}

		group.setX((int) (BoardView.CELL_SIZE * nextSubTile.getX()));
		group.setY((int) (BoardView.CELL_SIZE * nextSubTile.getY()));
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
			changeAnimation(attackerBodyWinSprites, attackerHelmetWinSprites);
		} else {
			changeAnimation(attackerBodyDeathSprites, attackerHelmetDeathSprites);
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
