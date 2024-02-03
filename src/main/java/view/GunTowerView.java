package view;

import TowerDefense.Board;
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
		super(tower, boardGroup, graphics, tooltips, "canon",48, 48, 30, 30);
		towerSpriteAnimation.setScale(2).setDuration(4000);

//		super(tower, boardGroup, graphics, tooltips, "gun",96, 96, 29, 29);
//		towerSpriteAnimation.setScale(2.5).setAnchor(.3).setDuration(2500);

//		super(tower, boardGroup, graphics, tooltips, "guntower_spritesheet",64, 64, 8, 8);
//		towerSpriteAnimation.setScale(1.8).setAnchorY(.2).setAnchorX(.1).setDuration(1500);


		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);


//		shootSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower,"fireball_sprite.png", 48,48,8, 4);
//		shootSpriteAnimation
//				.setScale(.5)
//				.setAlpha(0)
//				.setAnchor(.5);


		//experiment
		shootSpriteAnimation = Utils.createEffectSpriteAnimation(graphics,tower,"bullet.png", 32,32,8,8);
		shootSpriteAnimation.setScale(2).setAnchor(.5);

		commitSprites();
		updateTooltip();

	}

	@Override
	public void attack(Attacker a) {

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		//TODO: make concrete calculation

		double ax = a.getCurrentSubTile().getX();
		double ay = Constants.MAP_HEIGHT - a.getCurrentSubTile().getY();
		double tx = tower.getTile().getX();
		double ty = Constants.MAP_HEIGHT - tower.getTile().getY();
		double dhal =  ( ay - ty ) / ( ax - tx );
//		double angle = 180 + 90 + 1*Math.toDegrees( Math.tan( ( tower.getTile().getY() - a.getCurrentSubTile().getY() ) / ( tower.getTile().getX() -a.getCurrentSubTile().getX() ) ) );
		double gxxg = Math.atan(dhal);
		double angle = 90 + -1*Math.toDegrees(Math.atan(dhal));
//		double m = Math.tan(angle);

		if( ay > ty && ax < tx ) {
			angle += 180;
		}

		shootSpriteAnimation.setRotation(( Math.toRadians(angle) ));
		graphics.commitEntityState(0, shootSpriteAnimation);

		shootSpriteAnimation.setAlpha(1, Curve.EASE_OUT)
				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()+1) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg))
				.setRotation( (Math.toRadians(angle) ))
		;

		destroyedSpriteAnimation.setVisible(true);

		graphics.commitEntityState(0,destroyedSpriteAnimation);
		graphics.commitEntityState(.45, shootSpriteAnimation);


		double t = Math.toRadians( Math.tan( ( a.getCurrentSubTile().getY() - tower.getTile().getY() ) / ( a.getCurrentSubTile().getX() - tower.getTile().getX() ) ) );

		//TODO: make concrete calculation
		shootSpriteAnimation
				.setAlpha(0, Curve.IMMEDIATE)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 1 + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
//				.setRotation( -1 * (Math.toRadians(90) + Math.tan( ( a.getCurrentSubTile().getY() - tower.getTile().getY() ) / ( a.getCurrentSubTile().getX() - tower.getTile().getX() ) )));
				.setRotation( (Math.toRadians(270) ))
		;

	}
}
