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
		this.bounty = Constants.FIREBOMB_BOUNTY;
//		this.tile.unsetDestructibleObject();
	}

	@Override
	public boolean isDestroyed() {
		return this.getProperty(TowerProperty.FUSETIME) == this.getLifetime();
	}

	boolean inRange(Tile t){
		double property = getProperty(TowerProperty.RANGE);
		return  Math.abs(t.getX()-getTile().getX())<=property
				&&Math.abs(t.getY()-getTile().getY()) <=property;
	}

	@Override
	boolean doAttack(List<Attacker> attackers, List<Tower> towers) {
		this.incrementLifeTime();
		if( this.getLifetime() < this.getProperty(TowerProperty.FUSETIME)  ) {
			return false;
		}
		boolean attacked = false;
		for (Attacker a : attackers) {
			if (getOwner() == a.getOwner() || !inRange(a.getCurrentTile()))
				continue;
			int d = (int) getProperty(TowerProperty.DAMAGE);
			a.dealDamage((int) getProperty(TowerProperty.DAMAGE));
			attacked = true;
		}
		for (Tower t : towers) {
			if (getOwner() == t.getOwner() || !inRange(t.getTile()))
				continue;
			int d = (int) getProperty(TowerProperty.DAMAGE);
			t.dealDamage((int) getProperty(TowerProperty.DAMAGE), getOwner());

			//add bounty
			if(t.isDestroyed()){
				getOwner().setCoins(getOwner().getCoins()+t.getBounty());
			}
//			getView().attack(a);
			attacked = true;
		}

		getView().attack(null);

		return attacked;
	}

	@Override
	public TowerView createView(Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltipModule) {
		return new FireBombView(this, boardGroup, graphics, tooltipModule);
	}

}