package TowerDefense;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import com.codingame.game.Player;

import com.codingame.game.Referee;
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
	private boolean justRelocatedOnCurrentFrame;
	private AttackerView view;
	//	private static int idCounter;
//	private static int playerOneAttackerIdCounter = 0;
//	private static int playerTwoAttackerIdCounter = 0;
	private static TreeMap<Integer, Integer> playerAttackerCounter = new TreeMap<>();
	private ArrayList<SubTile> steps;
	public Tile lastTile;

	public boolean reachOpponentBase;

	Tile spawnTile;
	SubTile spawnSubtile;
	private boolean relocated;

	public Attacker(Tile[][] grid, Player owner, Player enemy, int spawn_position_y) {
//		id = idCounter++;
		if (!playerAttackerCounter.containsKey(owner.getIndex())) {
			playerAttackerCounter.put(owner.getIndex(), 0);
		}
//		id = (owner.getIndex() == 0 ? playerOneAttackerIdCounter++: playerTwoAttackerIdCounter++);
		id = playerAttackerCounter.get(owner.getIndex());
		playerAttackerCounter.put(owner.getIndex(), id + 1);
		//this.remainingPath = path;



		if(owner.getIndex() == 1) {
			this.spawnTile = grid[Constants.MAP_WIDTH-1][spawn_position_y];
			this.spawnSubtile = spawnTile.getSubTile(SubTile.SUBTILE_SIZE-1, SubTile.SUBTILE_SIZE-1);

		}
		else {
			this.spawnTile = grid[0][spawn_position_y];
			this.spawnSubtile = spawnTile.getSubTile(0,SubTile.SUBTILE_SIZE-1);
		}

		this.grid = grid;
		this.owner = owner;
		this.enemy = enemy;
		this.maxSpeed = Constants.SPEED;
		this.hitPoints = Constants.HP;
		this.bounty = Constants.BOUNTY;
		this.maxHealth = hitPoints;
		this.reachOpponentBase = false;

		this.currentTile = this.spawnTile;
		this.currentSubtile = this.spawnSubtile;
	}

	public void relocate(SubTile newSubTile, double t) {
		this.currentTile = newSubTile.getTile();
		this.currentSubtile = newSubTile;
		view.animateAttackerJump(t);
//		view.move(currentSubtile,t);
//		relocated=true;
		if( currentTile.hasDestructibleObject() ) {
			currentTile.obstacleTower.disappear();
			this.kill();
		}
	}
	public void spawn() {
		this.maxSpeed = Constants.SPEED;
		this.hitPoints = Constants.HP;
		this.bounty = Constants.BOUNTY;
		this.maxHealth = hitPoints;

		this.currentTile = this.spawnTile;
		this.currentSubtile = this.spawnSubtile;
		view.spawnAnimation();

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
		//bord.checkDeadAttacker() will collect the dead and kill them
		if (isDead())
			view.kill();
	}

	public void slowDown(int countdown) {
		this.slowCountdown = countdown;
	}

	public boolean isDead() {
		return hitPoints <= 0 || currentTile.hasAnyObject();
	}



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

		if( steps.size() <= 2 ) return getOwner().getIndex() == 0 ? 2 : 4;

		if( steps.get(0).getSubX() == steps.get(1).getSubX() ) {
			if( steps.get(0).getSubY() - steps.get(1).getSubY() > 0 ) dir = 3;
			else dir = 1;
		}else if( steps.get(0).getSubX() - steps.get(1).getSubX() > 0 ) dir = 2;
		else dir = 4;

		return dir;
	}

	public void move() {

//		if(relocated)
//		{
//			relocated=false;
//			return;
//		} else {
//			if( currentTile.hasDestructibleObject() ) {
//				currentTile.obstacleTower.disappear();
//				this.kill();
//			}
//		}

		boolean[][] optimalTiles = PathFinder.getOptimalPathTiles(currentTile,grid);
		ArrayList<SubTile> path = PathFinder.getOptimalPath(currentSubtile,optimalTiles);
		steps = path;



		int ln = Math.min(path.size(),getSpeed());

		view.animateAttackerWalk();
		for (int i = 0; i < ln; i++) {
			Tile prevTile = currentTile;
			view.move(path.get(i),i/(double)ln );
			if(currentTile.getSpring()!=null) {
//				view.move(path.get(i),.2);
				relocate(currentTile.getSpring().getRelocateSubTile(this), i/(double)ln);
				break;
			}
		}




		if (slowCountdown > 0)
			slowCountdown--;
	}

	public void build(int x, int y){

	}

	public void attack(Tile t) {
		if( t.obstacleTower != null ) {
			t.obstacleTower.dealDamage(Constants.ATTACKER_DAMAGE, getOwner());
		}
		Tile src = this.currentTile;

		if (t.getY() == src.getY()+1) {
			view.animateAttackerStab("DOWN");
		}
		else if(t.getY() == src.getY()-1) {
			view.animateAttackerStab("UP");
		}
		else if(t.getX() == src.getX()+1) {
			if(this.owner.getIndex() == 1){
				view.animateAttackerStab("LEFT");
			}
			else{
				view.animateAttackerStab("RIGHT");
			}
		}
		else {
			if(this.owner.getIndex() == 1){
				view.animateAttackerStab("RIGHT");
			}
			else{
				view.animateAttackerStab("LEFT");
			}
		}
		// TODO: Add attacker attack animation here
	}


	public Player getOwner() {
		return owner;
	}

	public Player getEnemy() {
		return enemy;
	}


	public boolean hasReachedTarget() {
		if(owner.getIndex()==0){
			return currentTile == grid[Constants.MAP_WIDTH-1][Constants.MAP_HEIGHT/2];
		}

		return  currentTile ==grid[0][Constants.MAP_HEIGHT/2];
	}


	public void updateToolTip() {
		view.updateTooltip();
	}
}