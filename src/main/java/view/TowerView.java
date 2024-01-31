package view;

import java.text.DecimalFormat;

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
//		towerSprite = Utils.createTowerSprite(graphics, sprite + upgradeLevel + ".png", tower.getTile().getX(), tower.getTile().getY());
//		towerSprite.setTint(tower.getOwner().getColor());
		towerSpriteAnimation = Utils.createTowerSpriteAnimation(graphics, sprite + ".png", tower.getTile().getX(), tower.getTile().getY(), w, h, img_c, img_pr);
//		towerSpriteAnimation.setTint(tower.getOwner().getColor());

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
		//health bar code end

	}

	protected void commitSprites() {
//		boardGroup.add(towerSprite);

		boardGroup.add(towerSpriteAnimation);
//		if (towerFixedSprite != null) {
//			boardGroup.add(towerFixedSprite);
//		}
		if (towerFixedSpriteAnimation != null) {
			boardGroup.add(towerFixedSpriteAnimation);
		}
//		if (attackSprite != null) boardGroup.add(attackSprite);
//		if (attackLine != null) boardGroup.add(attackLine);
//		graphics.commitEntityState(0, boardGroup, towerSprite);

		graphics.commitEntityState(0, boardGroup, towerSpriteAnimation);
		graphics.commitEntityState(0,towerSpriteAnimation);
//		if (towerFixedSprite != null)
//			graphics.commitEntityState(0, towerFixedSprite);

		if (towerFixedSpriteAnimation != null)
			graphics.commitEntityState(0, towerFixedSpriteAnimation);
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
//		towerSprite.setVisible(false);
	}
}
