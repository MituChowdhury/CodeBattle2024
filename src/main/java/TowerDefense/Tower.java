package TowerDefense;

import java.text.DecimalFormat;
import java.util.List;

import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import view.TowerView;

public abstract class Tower {
	private int id;
	private String type;
	protected Tile tile;
	protected int hitPoints;
	protected int[] upgradeStates;
	protected double[][] properties;
	protected int cooldown;
	protected int cost;
	private TowerView view;
	private Player owner;
	public static final String[] TowerOrder = { "HEALTOWER", "FIRETOWER", "GUNTOWER", "GLUETOWER" };
	private int lifetime = 0;

	static int idCounter = 0;

	static Player lastAttackedPlayer;
	protected Attacker lastAttacked = null;
	protected int bounty;

	public Tower(String type, Tile tile) {
		this.id = idCounter++;
		this.type = type;
		this.tile = tile;
		this.upgradeStates = new int[TowerProperty.values().length];
		this.properties = new double[TowerProperty.values().length][];
		tile.setDestructibleObject(this);
	}

	public int getBounty() {
		return this.bounty;
	}
	public int getLifetime() {
		return lifetime;
	}

	public void dealDamage(int damage, Player dealtBy) {
		this.hitPoints = Math.max(0, hitPoints - damage);

		this.view.dealDamage(hitPoints,  (int) this.getProperty(TowerProperty.HITPOINT));
		lastAttackedPlayer = dealtBy;
	}

	public Player getDestroyer() {
		return lastAttackedPlayer;
	}

	public void incrementLifeTime() {
		this.lifetime++;
	}

	public boolean isDestroyed() {
		return this.hitPoints == 0;
	}

	// TODO: add disappear animation here in view.destroy()
	public void disappear() {
		this.tile.unsetDestructibleObject();
		view.destroy();
	}
	public boolean canUpgrade(TowerProperty property) {
		int upgradeState = upgradeStates[property.ordinal()];
		double[] values = properties[property.ordinal()];
		int upgradeCost = Constants.TOWER_UPGRADE_COSTS[upgradeState];
		return values.length > upgradeState + 1 && owner.getMoney() >= upgradeCost;
	}

	public void upgrade(TowerProperty property) {
		int upgradeState = upgradeStates[property.ordinal()];
		int upgradeCost = Constants.TOWER_UPGRADE_COSTS[upgradeState];
		this.hitPoints = (int) this.getProperty(TowerProperty.HITPOINT);
		owner.spendCoins(upgradeCost);
		upgradeStates[property.ordinal()]++;
		view.upgrade();
		view.updateTooltip();
	}

	public double getProperty(TowerProperty property) {
		int updateState = upgradeStates[property.ordinal()];
		double[] values = properties[property.ordinal()];
		return values[updateState];
	}

	public int getCost() {
		return cost;
	}

	public Tile getTile() {
		return tile;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public TowerView getView() {
		return view;
	}

	public int getCooldown() {
		return cooldown;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void attack(List<Attacker> attackers, List<Tower> towers) {
		Tower t = this;
		if (cooldown > 0) {
			cooldown--;
			return;
		}
		if (doAttack(attackers, towers)) {
			cooldown = (int) getProperty(TowerProperty.RELOAD) - 1;
		}
	}


	public boolean inRange(Attacker a) {
//		for (SubTile sub : a.getSteps()) {
		SubTile sub = a.getCurrentSubTile();
		double dx = sub.getX() - tile.getSubTile(Constants.SUBTILE_SIZE/2, Constants.SUBTILE_SIZE/2).getX();
		double dy = sub.getY() - tile.getSubTile(Constants.SUBTILE_SIZE/2, Constants.SUBTILE_SIZE/2).getY();
		int rangeIndex = TowerProperty.RANGE.ordinal();
		double range = properties[rangeIndex][upgradeStates[rangeIndex]];
		double dist = Math.sqrt(dx * dx + dy * dy);
		if (range >= dist) return true;
//		}
		return false;
	}

	public boolean inRange(Tower a) {
//		for (SubTile sub : a.getSteps()) {
		Tile tower_tile = a.getTile();
		double dx = tower_tile.getX() - this.tile.getSubTile(Constants.SUBTILE_SIZE/2, Constants.SUBTILE_SIZE/2).getX();
		double dy = tower_tile.getY() - this.tile.getSubTile(Constants.SUBTILE_SIZE/2, Constants.SUBTILE_SIZE/2).getY();
		int rangeIndex = TowerProperty.RANGE.ordinal();
		double range = properties[rangeIndex][upgradeStates[rangeIndex]];
		double dist = Math.sqrt(dx * dx + dy * dy);
		if (range >= dist) return true;
//		}
		return false;
	}

	abstract boolean doAttack(List<Attacker> attackers, List<Tower> towers);

	public String getPlayerInput() {
		StringBuilder sb = new StringBuilder();
		sb.append(type).append(" ");
		sb.append(id).append(" ");
		sb.append(owner.getIndex()).append(" ");
		sb.append(tile.toString()).append(" ");
		for (TowerProperty p : TowerProperty.values()) {
			sb.append(new DecimalFormat("0.#").format(getProperty(p))).append(" ");
		}
		sb.append(cooldown);
		return sb.toString();
	}

	public abstract TowerView createView(Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltipModule);

	public void setView(TowerView view) {
		this.view = view;
	}

	public void undoBuild() {
		idCounter--;
	}

}
