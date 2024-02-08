package view;

import TowerDefense.Attacker;
import TowerDefense.Constants;
import TowerDefense.Tower;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.tooltip.TooltipModule;

public class FireBombView extends TowerView {

	private SpriteAnimation explosionAnimation;

	public FireBombView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {

		super(tower, boardGroup, graphics, tooltips, "bomb",32, 32, 5, 5);
		towerSpriteAnimation.setScale(3).setZIndex(1000);

//		System.out.println("bomb called");


		explosionAnimation = Utils.createEffectSpriteAnimation(graphics, tower, "circle-explosion_sprite.png",256, 256,10,10 );

		explosionAnimation
				.setScale(1)
				.setZIndex(100)
				.setDuration(1000)
				.setAnchorX(.41)
				.setAnchorY(.55)
//				.setVisible(false)
		;


		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {

		explosionAnimation
				.setVisible(true)
				.setAlpha(1);


		graphics.commitEntityState(1, explosionAnimation);

		explosionAnimation
				.setVisible(false);


	}

}
