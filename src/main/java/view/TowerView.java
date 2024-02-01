package view;

import java.text.DecimalFormat;

import com.codingame.game.Util;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.Constants;
import TowerDefense.Tower;
import TowerDefense.TowerProperty;

public abstract class TowerView {
	protected Tower tower;
	protected Sprite towerSprite, towerFixedSprite;
	protected SpriteAnimation towerSpriteAnimation, towerFixedSpriteAnimation;
//	protected Rectangle tileTint;  // Sets the tint of the underlying tile to the color of the player...
	protected Circle tileTint;  // Sets the tint of the underlying tile to the color of the player...
	protected Sprite tint;

	protected SpriteAnimation destroyedSpriteAnimation;
	protected Sprite attackSprite;
	protected Line attackLine;

	protected GraphicEntityModule graphics;
	protected TooltipModule tooltipModule;
	protected Group boardGroup,group;
	private Rectangle healthBarRed; // Health bar...
	private Rectangle healthBarGreen; // Health bar...
	public static final int HEALTH_BAR_LEN = 100;

	String spriteFileBaseName;
	int upgradeLevel = 0;
	
	public TowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips, String sprite, int w, int h, int img_c, int img_pr) {
		this.tower = tower;
		this.graphics = graphics;
		tower.setView(this);
		this.tooltipModule = tooltips;
		this.spriteFileBaseName = sprite;

		towerSpriteAnimation = Utils.createTowerSpriteAnimation(graphics, sprite + ".png", tower.getTile().getX(), tower.getTile().getY(), w, h, img_c, img_pr).setZIndex(1);
//		towerSpriteAnimation.setTint(tower.getOwner().getColor());
//		tileTint = graphics.createRectangle()
//				.setX(BoardView.CELL_SIZE * tower.getTile().getX())
//				.setY(BoardView.CELL_SIZE * tower.getTile().getY())
//				.setHeight(BoardView.CELL_SIZE)
//				.setWidth(BoardView.CELL_SIZE)
//				.setFillColor(tower.getOwner().getColor())   // tower.getOwner().getColor()
//				.setAlpha(0.3)
//				.setZIndex(-1);

//		tileTint = graphics.createCircle()
//				.setX(BoardView.CELL_SIZE * tower.getTile().getX() + BoardView.CELL_SIZE / 2)
//				.setY(BoardView.CELL_SIZE * tower.getTile().getY() + BoardView.CELL_SIZE / 2)
//				.setRadius(0)
//				.setFillColor(tower.getOwner().getColor())   // tower.getOwner().getColor()
//				.setAlpha(0.3)
//				.setZIndex(-1);

		tint = graphics.createSprite()
				.setImage(tower.getOwner().getIndex() == 0 ? "redblur.png": "blueblur.png")
				.setAnchor(0.5)
				.setX(BoardView.CELL_SIZE * tower.getTile().getX() + BoardView.CELL_SIZE / 2)
				.setY(BoardView.CELL_SIZE * tower.getTile().getY() + BoardView.CELL_SIZE / 2)
				.setAlpha(0.7)
				.setZIndex(-1)
				.setScale(2);

//		graphics.commitEntityState(0, tileTint);

		// Color the underlying tile to the color of the player....

		destroyedSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower, "destroyed1.png", 762,762,14, 14);
		destroyedSpriteAnimation
				.setDuration(800)
				.setAnchorY(.55)
				.setAnchorX(.45)
				.setScale(.3)
		;
		//health bar code start
		healthBarRed = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setFillColor(0xff0000);
		healthBarGreen = graphics.createRectangle().setWidth(HEALTH_BAR_LEN).setHeight(8).setFillColor(0x00ff00);

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		group = graphics.createGroup(healthBarRed, healthBarGreen).setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() ) ))
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY()-.25)));

		boardGroup.add(group);
		graphics.commitEntityState(0,group);
		//health bar code ended

		this.boardGroup=boardGroup;
		healthBarVisibility(false);
		//health bar code end

	}

	protected void commitSprites() {

		boardGroup.add(towerSpriteAnimation);
//		boardGroup.add(tileTint);
		boardGroup.add(tint);

		if (towerFixedSpriteAnimation != null) {
			boardGroup.add(towerFixedSpriteAnimation);
		}

		graphics.commitEntityState(0, boardGroup, towerSpriteAnimation);
		graphics.commitEntityState(0,towerSpriteAnimation);

		graphics.commitEntityState(0, destroyedSpriteAnimation);

		if (towerFixedSpriteAnimation != null)
			graphics.commitEntityState(0, towerFixedSpriteAnimation);
	}

	public void healthBarVisibility(boolean a){
		if(a){
			group.setVisible(true);
		}
		else {
			group.setVisible(false);
		}
	}




	public void dealDamage(int hp, int maxHp) {
		System.err.println("Bar length: " + (int) (TowerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
		this.healthBarGreen.setWidth((int) (TowerView.HEALTH_BAR_LEN * ((double) hp / maxHp)));
	}


	public void updateTooltip() {
		StringBuilder sb = new StringBuilder();
		sb.append("x: ").append(tower.getTile().getX()).append("\ny: ").append(tower.getTile().getY());
		sb.append("\ntype: ").append(tower.getType());
		sb.append("\nid: ").append(tower.getId());
		sb.append("\nowner: ").append(tower.getOwner().getIndex());
		for (TowerProperty p : TowerProperty.values()) {
			sb.append("\n").append(p).append(": ").append(new DecimalFormat("0.#").format(tower.getProperty(p)));
		}
		//sb.append("\ncooldown: ").append(tower.getCooldown());

//		tooltipModule.setTooltipText(towerSprite, sb.toString());
		tooltipModule.setTooltipText(towerSpriteAnimation, sb.toString());

	}

	public abstract void attack(Attacker a);
	
	public void upgrade() {
		if (upgradeLevel + 1 == Constants.NUM_UPGRADE_SPRITES)
			return;
		upgradeLevel++;
		this.towerSprite.setImage(spriteFileBaseName + upgradeLevel + ".png");
		if (towerFixedSprite != null)
			this.towerFixedSprite.setImage(spriteFileBaseName + "Fixed" + upgradeLevel + ".png");
	}

	// TODO: Add animation here
	public void destroy() {
		towerSpriteAnimation.setVisible(false);
		destroyedSpriteAnimation.setAlpha(.8);
		graphics.commitEntityState(1, destroyedSpriteAnimation);
		destroyedSpriteAnimation.setAlpha(0);
		healthBarVisibility(false);
//		towerSprite.setVisible(false);
		tint.setVisible(false);
	}
}
