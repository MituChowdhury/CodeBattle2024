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


	private SpriteAnimation shootSpriteAnimation;
	public GunTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "gunTower",70, 130, 6, 6);
		towerSpriteAnimation.setScale(1.5).setAnchorY(.5).setAnchorX(0);

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);


		shootSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower,"fireball_sprite.png", 48,48,8, 4);
		shootSpriteAnimation
				.setScale(1.2)
				.setAlpha(0)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
				.setAnchor(.5);

		// canonball
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

		shootSpriteAnimation.setAlpha(1, Curve.EASE_OUT)
				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg))
				.setRotation(Math.PI * 2 - 1);
//				.setRotation(Math.atan(((BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg) - (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg))/
//						(( (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)- ((BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH))), Curve.IMMEDIATE);


		//canonball
//		canonBall
//				.setAlpha(1, Curve.EASE_OUT)
//				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
//				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg));
//		graphics.commitEntityState(.45, canonBall);

		graphics.commitEntityState(.45, shootSpriteAnimation);

		shootSpriteAnimation
				.setAlpha(0, Curve.IMMEDIATE)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg));

	}
}
