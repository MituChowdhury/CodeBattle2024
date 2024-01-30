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


	private SpriteAnimation stuntAnimation;

	public GlueTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "glueTower");



		String[] stuntSprite = graphics.createSpriteSheetSplitter()
				.setSourceImage("ground_effect.png")
				.setHeight(64).setWidth(64).setImageCount(8)
				.setImagesPerRow(8).setOrigRow(0).setOrigCol(0).setName("stuntEffectSprite").split();


		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		stuntAnimation = graphics.createSpriteAnimation()
				.setImages(stuntSprite)
				.setScaleX(5)
				.setScaleY(12)
				.setDuration(500)
				.setLoop(true).setPlaying(true)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
				.setAnchor(.5)
				.setVisible(false);



		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {


		stuntAnimation
				.setVisible(true)
				.setAlpha(1);

		graphics.commitEntityState(1, stuntAnimation);

		stuntAnimation
				.setAlpha(0, Curve.LINEAR)
				.setVisible(false);

	}
}
