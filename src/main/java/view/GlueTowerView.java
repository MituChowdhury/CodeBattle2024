package view;

import TowerDefense.Constants;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.Tower;

public class GlueTowerView extends TowerView {


	private SpriteAnimation stuntSpriteAnimation;

	public GlueTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "stunTower",  48, 48, 10, 10);
		towerSpriteAnimation
				.setScale(2);
//				.setAnchorX(.5)
//				.setAnchorY(1);

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		stuntSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower, "ground_effect.png", 64,64,8,8);
		stuntSpriteAnimation
				.setScaleX(5)
				.setScaleY(12)
				.setDuration(500)
				.setAnchor(.5)
				.setVisible(false);

		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {

		stuntSpriteAnimation
				.setVisible(true)
				.setAlpha(1);

		graphics.commitEntityState(1, stuntSpriteAnimation);

		stuntSpriteAnimation
				.setAlpha(0, Curve.LINEAR)
				.setVisible(false);

	}
}
