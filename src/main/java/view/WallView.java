package view;

import TowerDefense.Attacker;
import TowerDefense.Tower;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.tooltip.TooltipModule;

public class WallView extends TowerView {

	private SpriteAnimation wallSpriteAnimation;

	public WallView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "rock",64, 64, 1, 1);
		towerSpriteAnimation.setScale(1.5);



		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {

	}
}
