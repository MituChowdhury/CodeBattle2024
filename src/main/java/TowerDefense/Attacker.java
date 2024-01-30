package TowerDefense;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import com.codingame.game.Player;

import view.AttackerView;



public class Attacker {
	//private List<SubTile> remainingPath;

	private Tile[][] grid;
	private Tile currentTile;
	private SubTile currentSubtile;
	private int id;
	private int maxHealth;
	private int hitPoints;
	private int maxSpeed;
	private int slowCountdown;
	private int bounty;
	private Player owner;
	private Player enemy;
	private AttackerView view;
//	private static int idCounter;
//	private static int playerOneAttackerIdCounter = 0;
//	private static int playerTwoAttackerIdCounter = 0;
	private static TreeMap<Integer, Integer> playerAttackerCounter = new TreeMap<>();
	private ArrayList<SubTile> steps;
	public Tile lastTile;

	Tile spawnTile;
	SubTile spawnSubtile;

	private boolean reachOpponentBase;

	public Attacker(Tile[][] grid, int hp, int speed, int bounty, Player owner, Player enemy, int spawn_position_y) {
//		id = idCounter++;
		if (!playerAttackerCounter.containsKey(owner.getIndex())) {
			playerAttackerCounter.put(owner.getIndex(), 0);
		}
//		id = (owner.getIndex() == 0 ? playerOneAttackerIdCounter++: playerTwoAttackerIdCounter++);
		id = playerAttackerCounter.get(owner.getIndex());
		playerAttackerCounter.put(owner.getIndex(), id + 1);
		//this.remainingPath = path;

		// Errorneous code...
//		if(owner.getIndex() == 1) {
//			this.currentTile = grid[0][Constants.MAP_WIDTH-1];
//			this.currentSubtile = currentTile.getSubTiles().get(SubTile.SUBTILE_SIZE-1);
//		}
//		else {
//			this.currentTile = grid[Constants.MAP_HEIGHT-1][0];
//			this.currentSubtile = currentTile.getSubTiles().get(SubTile.SUBTILE_SIZE*(SubTile.SUBTILE_SIZE-1));
//		}

		if(owner.getIndex() == 1) {
			this.spawnTile = grid[Constants.MAP_WIDTH-1][spawn_position_y];
			this.spawnSubtile = spawnTile.getSubTile(0,SubTile.SUBTILE_SIZE-1);
			//this.currentSubtile = currentTile.getSubTiles().get(((SubTile.SUBTILE_SIZE-1)*(SubTile.SUBTILE_SIZE-1))+(SubTile.SUBTILE_SIZE-1));
		}
		else {
			this.spawnTile = grid[0][Constants.MAP_HEIGHT-1-spawn_position_y];
			this.spawnSubtile = spawnTile.getSubTile(SubTile.SUBTILE_SIZE-1, 0);
			//this.currentSubtile = currentTile.getSubTiles().get(SubTile.SUBTILE_SIZE-1);
		}

		this.spawn();

		this.grid = grid;
		this.owner = owner;
		this.enemy = enemy;
		this.maxSpeed = speed;
		this.hitPoints = hp;
		this.bounty = bounty;
		this.maxHealth = hitPoints;
		this.reachOpponentBase = false;
	}

	public void relocate(SubTile newSubTile) {
		this.currentTile = newSubTile.getTile();
		this.currentSubtile = newSubTile;
	}
	public void spawn() {
		this.currentTile = this.spawnTile;
		this.currentSubtile = this.spawnSubtile;
	}

	public void respawn() {
		this.spawn();
	}

	public int getId() {
		return id;
	}

	public int getSpeed() {
		if (slowCountdown == 0)
			return maxSpeed;
		return maxSpeed / Constants.GLUE_SLOWDOWN;
	}

	public boolean isSlow() {
		return slowCountdown > 0;
	}

	public int getBounty() {
		return bounty;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getSlowCountdown() {
		return slowCountdown;
	}

//	public int getPathLength() {
//		return remainingPath.size();
//	}

	public Tile getLocation() {
		return currentTile;
	}

	public SubTile getLocationSubTile() {
		return currentSubtile;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public SubTile getCurrentSubTile() {
		return currentSubtile;
	}

	public void kill() {
		dealDamage(hitPoints);
	}

	// TODO: change this to proper functionality
	public boolean canRespawn() {
		return reachOpponentBase || true;
	}

	public boolean canHeal() {
		return hitPoints < maxHealth;
	}

	public void heal(int health) {
		hitPoints = Math.min(hitPoints + health, maxHealth);
	}

	//	public void dealDamage(int damage) {
//		this.hitPoints = Math.max(0, hitPoints - damage);
//		if (isDead())
//			view.kill();
//	}
	public void dealDamage(int damage) {
		this.hitPoints = Math.max(0, hitPoints - damage);
		//...
		this.view.dealDamage(hitPoints, maxHealth);
		//...
		if (isDead())
			view.kill();
	}

	public void slowDown(int countdown) {
		this.slowCountdown = countdown;
	}

	public boolean isDead() {
		return hitPoints <= 0;
	}

//	public boolean hasSucceeded() {
//		return remainingPath.size() == 1;
//	}

	public void setView(AttackerView view) {
		this.view = view;
	}
	public ArrayList<SubTile> getSteps() {
		return steps;
	}


	public void setCurrentSubtile(SubTile t){
		this.currentSubtile = t;
		this.currentTile = currentSubtile.getTile();
	}

	public int getDirection() {
		int dir = 0;

		if( steps.size() == 0 ) return getOwner().getIndex() == 0 ? 2 : 4;

		if( steps.get(0).getSubX() == steps.get(1).getSubX() ) {
			if( steps.get(0).getSubY() - steps.get(1).getSubY() > 0 ) dir = 3;
			else dir = 1;
		}else if( steps.get(0).getSubX() - steps.get(1).getSubX() > 0 ) dir = 2;
		else dir = 4;

		return dir;
	}

	public void move() {


		boolean[][] optimalTiles = PathFinder.getOptimalPathTiles(currentTile,grid);
		ArrayList<SubTile> path = PathFinder.getOptimalPath(currentSubtile,optimalTiles);

		int ln = Math.min(path.size(),getSpeed());
		for (int i = 0; i < ln; i++) {
			view.move(path.get(i));
		}

		if (slowCountdown > 0)
			slowCountdown--;
	}

	public Player getOwner() {
		return owner;
	}

	public Player getEnemy() {
		return enemy;
	}

	public String getPlayerInput() {
		StringBuilder sb = new StringBuilder();
		sb.append(id).append(" ");
		sb.append(owner.getIndex()).append(" ");
		//sb.append(getLocation().toString()).append(" ");
		sb.append(hitPoints).append(" ");
		sb.append(maxHealth).append(" ");
		sb.append(getSpeed()).append(" ");
		sb.append(maxSpeed).append(" ");
		sb.append(slowCountdown).append(" ");
		sb.append(bounty);

		return sb.toString();
	}
}
