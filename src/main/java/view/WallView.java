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
//		super(tower, boardGroup, graphics, tooltips, "wall2",62, 62, 1, 1);
//		towerSpriteAnimation.setScale(2.3).setAnchorY(.5);

		super(tower, boardGroup, graphics, tooltips, "wallBuilding",102, 102, 4, 4);
		towerSpriteAnimation.setScale(1.2).setAnchorX(.09).setAnchorY(.27).setScaleY(1.4);

//		super(tower, boardGroup, graphics, tooltips, "wall",58, 58, 1, 1);
//		towerSpriteAnimation.setScale(2.5).setAnchorY(.2).setAnchorX(.14).setZIndex(1);
//		super(tower, boardGroup, graphics, tooltips, "wall3",58, 58, 1, 1);
//		towerSpriteAnimation.setScale(2);






		commitSprites();
		updateTooltip();
		towerSpriteAnimation.setPlaying(false);
		graphics.commitEntityState(.8,towerSpriteAnimation);
	}

	@Override
	public void attack(Attacker a) {

	}
}
