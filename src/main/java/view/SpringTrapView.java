package view;

import TowerDefense.Attacker;
import TowerDefense.Constants;
import TowerDefense.Tower;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;

public class SpringTrapView extends TowerView {


	public SpringTrapView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "spring3",70, 65, 4, 4);
		towerSpriteAnimation.setScale(1.5);
//		towerSpriteAnimation.setPlaying(false);

		commitSprites();
		updateTooltip();

	}

	@Override
	public void attack(Attacker a) {

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

//		towerSpriteAnimation.setPlaying(true);
//		graphics.commitEntityState(0, towerSpriteAnimation);

	}
}
