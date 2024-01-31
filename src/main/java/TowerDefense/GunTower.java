package TowerDefense;

import java.util.List;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import view.GunTowerView;
import view.TowerView;

public class GunTower extends Tower {
	public GunTower(Tile tile) {
		super("GUNTOWER", tile);
		properties[TowerProperty.DAMAGE.ordinal()] = Constants.GUNTOWER_DAMAGE;
		properties[TowerProperty.RANGE.ordinal()] = Constants.GUNTOWER_RANGE;
		properties[TowerProperty.RELOAD.ordinal()] = Constants.GUNTOWER_RELOAD;
		properties[TowerProperty.HITPOINT.ordinal()] = Constants.GUNTOWER_HITPOINT;
		properties[TowerProperty.FUSETIME.ordinal()] = Constants.GUNTOWER_FUSETIME;
		cost = Constants.GUNTOWER_COST;
		this.hitPoints = (int) this.getProperty(TowerProperty.HITPOINT);
	}

	@Override
	boolean doAttack(List<Attacker> attackers, List<Tower> towers) {
		this.incrementLifeTime();
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
		return new GunTowerView(this, boardGroup, graphics, tooltipModule);
	}
}
