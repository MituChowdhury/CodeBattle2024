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

import java.util.ArrayList;

public class SpringTrapView extends TowerView {
	static String[] direction = {"North","East","South","West"};
	static int[] widths = {70,65,70,65};
	static int[] heights = {65,70,65,70};

	public SpringTrapView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips, int dir) {
		super(tower, boardGroup, graphics, tooltips, "springSp"+direction[dir -1], widths[dir-1], heights[dir-1], 8, 8);
//		super(tower, boardGroup, graphics, tooltips, "spring"+direction[dir -1],widths[dir-1], heights[dir-1], 4, 4);

		towerSpriteAnimation.setScale(1.5);

		String[] idleSpriteImages = graphics.createSpriteSheetSplitter()
				.setSourceImage("spring"+direction[dir-1]+".png")
				.setHeight(heights[dir-1]).setWidth(widths[dir-1]).setImageCount(4)
				.setImagesPerRow(4)
				.setOrigCol(0).setOrigRow(0)
				.setName( "spring"+direction[dir-1]+"Sprite")
				.split();

		commitSprites();
		updateTooltip();

		towerSpriteAnimation.setImages(idleSpriteImages);
		graphics.commitEntityState(1,towerSpriteAnimation);
	}

	@Override
	public void attack(Attacker a) {

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

//		towerSpriteAnimation.setPlaying(true);
//		graphics.commitEntityState(0, towerSpriteAnimation);

	}
}
