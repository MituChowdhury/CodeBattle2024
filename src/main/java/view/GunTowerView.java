package view;

import TowerDefense.Constants;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.Tower;

public class GunTowerView extends TowerView {


	private SpriteAnimation canonballAnim;
	public GunTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "gunTower");
		attackSprite = graphics.createSprite().setImage("gunTowerAttack.png").setAlpha(0);
		attackLine = graphics.createLine();
		attackLine.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5)));
		attackLine.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5)));
		attackLine.setLineColor(0xff0000).setAlpha(0);
		attackLine.setLineWidth(5);

		String[] fireballSprites = graphics.createSpriteSheetSplitter()
				.setSourceImage("fireball_sprite.png")
				.setHeight(48).setWidth(48).setImageCount(8)
				.setImagesPerRow(4).setOrigRow(0).setOrigCol(0).setName("fireBallSprite").split();



		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		canonballAnim = graphics.createSpriteAnimation()
				.setImages(fireballSprites)
				.setScale(1.2)
				.setDuration(500)
				.setLoop(true).setPlaying(true)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
				.setAnchor(.5)
				.setAlpha(0);

//		canonBall = graphics.createCircle();
//		canonBall
//				.setRadius(10)
//				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
//				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
//				.setAlpha(0)
//				.setLineWidth(0)
//				.setFillColor(0x3D3B40);
//
//		graphics.commitEntityState(0, canonBall);

		commitSprites();
		updateTooltip();

	}

	@Override
	public void attack(Attacker a) {
		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		canonballAnim.setAlpha(1, Curve.EASE_OUT)
				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg));

//		canonBall
//				.setAlpha(1, Curve.EASE_OUT)
//				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
//				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg));
//
//
//		graphics.commitEntityState(.45, canonBall);
//		graphics.commitEntityState(0, attackSprite, attackLine);

		graphics.commitEntityState(.45, canonballAnim);
//		canonBall
		canonballAnim
				.setAlpha(0, Curve.IMMEDIATE)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg));

	}
}
