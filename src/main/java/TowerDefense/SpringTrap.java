package TowerDefense;

import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import view.GunTowerView;
import view.HealTowerView;
import view.SpringTrapView;
import view.TowerView;

import java.util.ArrayList;
import java.util.List;

public class SpringTrap extends Tower {
	Board board;
	int dir;

	ArrayList<Attacker> toRelocate = new ArrayList<Attacker>();

	// dir -> UP-1 RIGHT-2 DOWN-3 LEFT-4
	public SpringTrap(Tile tile, int dir, Board board) {
		super("SPRINGTRAP", tile);
		properties[TowerProperty.DAMAGE.ordinal()] = Constants.SPRINGTRAP_DAMAGE;
		properties[TowerProperty.RANGE.ordinal()] = Constants.SPRINGTRAP_RANGE;
		properties[TowerProperty.RELOAD.ordinal()] = Constants.SPRINGTRAP_RELOAD;
		properties[TowerProperty.HITPOINT.ordinal()] = Constants.SPRINGTRAP_HITPOINT;
		properties[TowerProperty.FUSETIME.ordinal()] = Constants.SPRINGTRAP_FUSETIME;
		cost = Constants.SPRINGTRAP_COST;
		this.dir = dir;
		this.board = board;
		this.hitPoints = (int) this.getProperty(TowerProperty.HITPOINT);
		this.tile.unsetDestructibleObject();
		this.bounty = Constants.SPRINGTRAP_BOUNTY;
		this.tile.unsetDestructibleObject();
	}

	public int getSpringDistance() {
		int rangeIndex = TowerProperty.DAMAGE.ordinal();
		double range = properties[rangeIndex][this.upgradeStates[rangeIndex]];
		return (int) range;
	}

	public boolean isDestroyed() {
		return this.getProperty(TowerProperty.FUSETIME) == this.getLifetime();
	}

	private SubTile getRelocateSubTile( Attacker target ) {
		int newSubX = target.getCurrentSubTile().subX;
		int newSubY = target.getCurrentSubTile().subY;
		int newTileX = target.getCurrentTile().getX();
		int newTileY = target.getCurrentTile().getY();

		int targetDir = target.getDirection();
		Tile tttt = target.getCurrentTile();

		int distance = this.getSpringDistance();

		if( this.dir == 3 || this.dir == 1 ) {
			newTileY += (dir == 1 ? -1 : 1) * distance;
			newSubX += ( targetDir == 2 ? -1 : ( targetDir == 4 ? 1 : 0 )) * target.getSpeed()/2;
		} else if( this.dir == 4 || this.dir == 2 ) {
			newTileX += (dir == 4 ? -1 : 1) * distance;
			newSubY += ( targetDir == 3 ? -1 : ( targetDir == 1 ? 1 : 0 )) * target.getSpeed()/2;
		}


		if( newSubX > Constants.SUBTILE_SIZE -1 ) {
			newTileX += newSubX / Constants.SUBTILE_SIZE;
			newSubX = newSubX % Constants.SUBTILE_SIZE;
		} else if( newSubX < 0 ) {
			newTileX -= newSubX / Constants.SUBTILE_SIZE + 1;
			newSubX = Constants.SUBTILE_SIZE - (-newSubX) % Constants.SUBTILE_SIZE;  // why didnt we do -1 here?
		}

		if( newSubY > Constants.SUBTILE_SIZE-1 ) {
			newTileY += newSubY / Constants.SUBTILE_SIZE;
			newSubY = newSubY % Constants.SUBTILE_SIZE;
		} else if( newSubY < 0 ) {
			newTileY -= newSubY / Constants.SUBTILE_SIZE + 1;
			newSubY = Constants.SUBTILE_SIZE - (-newSubY) % Constants.SUBTILE_SIZE;
		}

		newTileX = Math.min(Constants.MAP_WIDTH-1, newTileX);
		newTileX = Math.max(0, newTileX);
		newTileY = Math.min(Constants.MAP_WIDTH-1, newTileY);
		newTileY = Math.max(0, newTileY);

		Tile t = board.getGrid()[newTileX][newTileY];

		return t.getSubTile(newSubX, newSubY);
	}

	@Override
	public boolean inRange(Attacker a) {

		for( int i=0; i< toRelocate.size(); i++ ) {
			if( toRelocate.get(i) == a ) {
				toRelocate.remove(i);
				return true;
			};
		}

		Tile tt = a.getCurrentTile();

		if( a.getCurrentTile() == this.tile ) {

			// This piece of code defies any logic but it works
			if( a.getOwner().getIndex() == 0 ) return true;
			else toRelocate.add(a);
		}

		return false;
	}

	@Override
	boolean doAttack(List<Attacker> attackers, List<Tower> towers) {
		this.incrementLifeTime();
		Attacker target = null;
		for (Attacker a : attackers) {
			Player p1 = getOwner();
			Tile t = a.getCurrentTile();
			Player p2 = a.getOwner();
			if (getOwner() == a.getOwner() || !inRange(a))
				continue;
			if (target == null)  // lagte pare
				target = a;
			target.relocate( this.getRelocateSubTile(target) );
		}
		if (target == null)
			return false;

		return true;
	}

	@Override
	public TowerView createView(Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltipModule) {
		return new SpringTrapView(this, boardGroup, graphics, tooltipModule);
	}
}
