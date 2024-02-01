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

	protected SpriteAnimation destroyedSpriteAnimation;
	protected Sprite attackSprite;
	protected Line attackLine;

	protected GraphicEntityModule graphics;
	protected TooltipModule tooltipModule;
	protected Group boardGroup;

	String spriteFileBaseName;
	int upgradeLevel = 0;
	
	public TowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips, String sprite, int w, int h, int img_c, int img_pr) {
		this.tower = tower;
		this.graphics = graphics;
		tower.setView(this);
		this.tooltipModule = tooltips;
		this.boardGroup = boardGroup;
		this.spriteFileBaseName = sprite;

		towerSpriteAnimation = Utils.createTowerSpriteAnimation(graphics, sprite + ".png", tower.getTile().getX(), tower.getTile().getY(), w, h, img_c, img_pr);
//		towerSpriteAnimation.setTint(tower.getOwner().getColor());

		destroyedSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower, "destroyed1.png", 762,762,14, 14);
		destroyedSpriteAnimation
				.setDuration(800)
				.setAnchorY(.55)
				.setAnchorX(.45)
				.setScale(.3)
		;
	}

	protected void commitSprites() {

		boardGroup.add(towerSpriteAnimation);

		if (towerFixedSpriteAnimation != null) {
			boardGroup.add(towerFixedSpriteAnimation);
		}

		graphics.commitEntityState(0, boardGroup, towerSpriteAnimation);
		graphics.commitEntityState(0,towerSpriteAnimation);

		graphics.commitEntityState(0, destroyedSpriteAnimation);

		if (towerFixedSpriteAnimation != null)
			graphics.commitEntityState(0, towerFixedSpriteAnimation);
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
	}
}
