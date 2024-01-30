package TowerDefense;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import view.GunTowerView;
import view.TowerView;
import view.WallView;

import java.util.List;

public class Wall extends Tower {
	public Wall(Tile tile) {
		super("WALL", tile);
		properties[TowerProperty.DAMAGE.ordinal()] = Constants.WALL_DAMAGE;
		properties[TowerProperty.RANGE.ordinal()] = Constants.WALL_RANGE;
		properties[TowerProperty.RELOAD.ordinal()] = Constants.WALL_RELOAD;
		properties[TowerProperty.HITPOINT.ordinal()] = Constants.WALL_HITPOINT;
		properties[TowerProperty.FUSETIME.ordinal()] = Constants.WALL_FUSETIME;
		cost = Constants.WALL_COST;
		this.hitPoints = (int) this.getProperty(TowerProperty.HITPOINT);
	}

	@Override
	boolean doAttack(List<Attacker> attackers) {
		Attacker target = null;
		for (Attacker a : attackers) {
			if (getOwner() == a.getOwner() || !inRange(a))
				continue;
			if (target == null)  // lagte pare
				target = a;
			if( a == lastAttacked ) {
				target = a;
				break;
			}
		}
		if (target == null)
			return false;

		this.lastAttacked = target;
		target.dealDamage((int) getProperty(TowerProperty.DAMAGE));
		getView().attack(target);
		return true;
	}

	@Override
	public TowerView createView(Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltipModule) {
		return new WallView(this, boardGroup, graphics, tooltipModule);
	}
}
