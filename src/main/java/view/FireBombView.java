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
		super(tower, boardGroup, graphics, tooltips, "healTower");
		attackLine = graphics.createLine();
		attackLine.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5)));
		attackLine.setY(BoardView.CELL_SIZE * tower.getTile().getY());
		attackLine.setLineColor(0xff0000).setAlpha(0);
		attackLine.setLineWidth(5);


		String[] explosionSprite = graphics.createSpriteSheetSplitter()
				.setSourceImage("circle-explosion_sprite.png")
//				.setSourceImage("Explosion_sprite.png")
//				.setSourceImage("explosion_blue.png")
				.setHeight(256).setWidth(256).setImageCount(10)
				.setImagesPerRow(10).setOrigRow(0).setOrigCol(0).setName("explosionEffectSprite").split();


		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		explosionAnimation = graphics.createSpriteAnimation()
				.setImages(explosionSprite)
				.setScale(1)
				.setDuration(1000)
				.setLoop(true).setPlaying(true)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
//				.setAnchorX(.5)
//				.setAnchorY(.55)
				.setAnchor(.5)
				.setVisible(false);


		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {
//		attackLine.setAlpha(1);
//		attackLine.setX2((int) (BoardView.CELL_SIZE * (a.getLocation().getX())));
//		attackLine.setY2((int) (BoardView.CELL_SIZE * a.getLocation().getY()+.5));
//
//		graphics.commitEntityState(0, attackLine);
//		attackLine.setAlpha(0);

		explosionAnimation
				.setVisible(true)
				.setAlpha(1);

		graphics.commitEntityState(1, explosionAnimation);

		explosionAnimation
//				.setAlpha(.2, Curve.EASE_OUT)
				.setVisible(false);


	}

}
