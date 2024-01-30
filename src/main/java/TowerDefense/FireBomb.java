package TowerDefense;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import view.FireBombView;
import view.GlueTowerView;
import view.TowerView;

import java.util.List;

public class FireBomb extends Tower {

	public FireBomb(Tile tile) {
		super("FIREBOMB", tile);
		properties[TowerProperty.DAMAGE.ordinal()] = Constants.FIREBOMB_DAMAGE;
		properties[TowerProperty.RANGE.ordinal()] = Constants.FIREBOMB_RANGE;
		properties[TowerProperty.RELOAD.ordinal()] = Constants.FIREBOMB_RELOAD;
		properties[TowerProperty.HITPOINT.ordinal()] = Constants.FIREBOMB_HITPOINTS;
		properties[TowerProperty.FUSETIME.ordinal()] = Constants.FIREBOMB_FUSETIME;
		cost = Constants.FIREBOMB_COST;
	}

	@Override
	public boolean isDestroyed() {
		return this.getProperty(TowerProperty.FUSETIME) == this.getLifetime();
	}

	@Override
	boolean doAttack(List<Attacker> attackers) {
		this.incrementLifeTime();
		if( this.getLifetime() < this.getProperty(TowerProperty.FUSETIME)  ) {
			return false;
		}
		boolean attacked = false;
		for (Attacker a : attackers) {
			if (getOwner() == a.getOwner() || !inRange(a) || a.isSlow())
				continue;
			int d = (int) getProperty(TowerProperty.DAMAGE);
			a.dealDamage((int) getProperty(TowerProperty.DAMAGE));
			getView().attack(a);
			attacked = true;
		}
		return attacked;
	}

	@Override
	public TowerView createView(Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltipModule) {
		return new FireBombView(this, boardGroup, graphics, tooltipModule);
	}

}