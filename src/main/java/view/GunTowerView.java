package view;

import TowerDefense.Constants;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.Tower;

public class GunTowerView extends TowerView {

	public GunTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "gunTower");
		attackSprite = graphics.createSprite().setImage("gunTowerAttack.png").setAlpha(0);
		attackLine = graphics.createLine();
		attackLine.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5)));
		attackLine.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5)));
		attackLine.setLineColor(0xff0000).setAlpha(0);
		attackLine.setLineWidth(5);

		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		canonBall = graphics.createCircle();
		canonBall
				.setRadius(10)
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY()) * gg))
				.setAlpha(1)
				.setLineWidth(0)
				.setFillColor(0x3D3B40);

		graphics.commitEntityState(0, canonBall);

		commitSprites();
		updateTooltip();





	}

	@Override
	public void attack(Attacker a) {
		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

		attackSprite.setAlpha(1);
		attackSprite.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX() + .8)));
		attackSprite.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY() + .5)));

		attackLine.setAlpha(1);
		attackLine.setX2((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX() + .8 + 0.5)));
		attackLine.setY2((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY() + 0.5 + 0.5)));

		canonBall
				.setAlpha(.8, Curve.EASE_OUT)
				.setX((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getX()) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * ( a.getLocationSubTile().getY()) * gg));


		graphics.commitEntityState(1, canonBall);
//		graphics.commitEntityState(0, attackLine);
		graphics.commitEntityState(0, attackSprite, attackLine);
//		graphics.commitEntityState(1,attackSprite);
//		attackSprite.setAlpha(0);
		attackLine.setAlpha(0);
//		canonBall
//				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX()+ 0.5)), Curve.IMMEDIATE)
//				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5)), Curve.IMMEDIATE);
//		graphics.commitWorldState(1);
	}
}
