package view;

import TowerDefense.Attacker;
import TowerDefense.Tower;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;

public class FireBombView extends TowerView {

	public FireBombView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
		super(tower, boardGroup, graphics, tooltips, "gunTower");
		attackLine = graphics.createLine();
		attackLine.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX() + 0.5)));
		attackLine.setY(BoardView.CELL_SIZE * tower.getTile().getY());
		attackLine.setLineColor(0xff0000).setAlpha(0);
		attackLine.setLineWidth(5);
		commitSprites();
		updateTooltip();
	}

	@Override
	public void attack(Attacker a) {
		attackLine.setAlpha(1);
		attackLine.setX2((int) (BoardView.CELL_SIZE * (a.getLocation().getX() + 0.5)));
		attackLine.setY2((int) (BoardView.CELL_SIZE * a.getLocation().getY()));

		graphics.commitEntityState(0, attackLine);
		attackLine.setAlpha(0);
	}

}
