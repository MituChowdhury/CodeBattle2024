package TowerDefense;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import view.FireBombView;
import view.GlueTowerView;
import view.TowerView;

import java.util.List;

public class FireBomb extends Tower {

	private boolean used = false;
	public FireBomb(Tile tile) {
		super("FIREBOMB", tile);
		properties[TowerProperty.DAMAGE.ordinal()] = Constants.FIREBOMB_DAMAGE;
		properties[TowerProperty.RANGE.ordinal()] = Constants.FIREBOMB_RANGE;
		properties[TowerProperty.RELOAD.ordinal()] = Constants.FIREBOMB_RELOAD;
		cost = Constants.FIREBOMB_COST;
	}


	public boolean isUsed() {
		return used;
	}

	@Override
	boolean doAttack(List<Attacker> attackers) {
		this.used = true;
		boolean attacked = false;
		for (Attacker a : attackers) {
			if (getOwner() == a.getOwner() || !inRange(a) || a.isSlow())
				continue;
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

	public void disappear() {
		((FireBombView) this.getView()).disappear();
	}
}