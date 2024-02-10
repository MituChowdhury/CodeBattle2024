package TowerDefense;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.codingame.game.Player;
import com.codingame.game.Referee;

import view.AttackerView;
import view.BoardView;

public class Board {
	private Tile[][] grid;
	private static List<Attacker> attackers = new ArrayList<>();
	private List<Attacker> veterans = new ArrayList<>();
	private List<Tower> towers = new ArrayList<>();
	private int width;
	private int height;
	private List<Player> players;

	private BoardView view;
	private int objCount = 0;
	private ArrayList<String[]> objectStrMaps = new ArrayList<>();
	private int waveIndex = 0;
	private int earliestWaveStart = 1;
	//	private List<List<Attacker>> futureAttackers = new ArrayList<>();
	private List<BuildAction> buildActions = new ArrayList<>();
	private List<List<SubTile>> paths = new ArrayList<>();
	private int waveNumber = 0;

	public Board(Tile[][] tiles, List<Player> players, Random random) {
		this.players = players;
//		for (int i = 0; i <= Constants.TURN_COUNT + Constants.WAVE_TIME; i++)
//			futureAttackers.add(new ArrayList<>());

		grid = tiles;
		width = tiles.length;
		height = tiles[0].length;

		List<Tile> targets = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			Tile tile = grid[width - 1][y];
			if (tile.canEnter())
				targets.add(tile);
		}

//		for (Tile target : targets) {
//			findPaths(grid, width, height, target, paths);
//		}
	}

//	private List<SubTile> mirrorPath(List<SubTile> path) {
//		List<SubTile> result = new ArrayList<SubTile>();
//		for (SubTile s : path) {
//			result.add(s.mirror(grid, width, height));
//		}
//		return result;
//	}
//
//	private List<SubTile> selectPath(List<List<SubTile>> paths) {
//		int index = Referee.random.nextInt(paths.size());
//		return new ArrayList<>(paths.get(index));
//	}

//	private void findPaths(Tile[][] grid, int width, int height, Tile target, List<List<SubTile>> paths) {
//		int[][] dist = new int[width][height];
//		for (int x = 0; x < width; x++) {
//			for (int y = 0; y < height; y++) {
//				dist[x][y] = -1;
//			}
//		}
//
//		dist[target.getX()][target.getY()] = 0;
//		Queue<Tile> bfs = new ConcurrentLinkedQueue<>();
//		bfs.add(target);
//
//		while (bfs.size() > 0) {
//			Tile sub = bfs.poll();
//			for (Tile neighbor : sub.getNeighbors()) {
//				if (neighbor == null || !neighbor.canEnter())
//					continue;
//				if (dist[neighbor.getX()][neighbor.getY()] >= 0)
//					continue;
//				dist[neighbor.getX()][neighbor.getY()] = dist[sub.getX()][sub.getY()] + 1;
//				bfs.add(neighbor);
//			}
//		}
//
//		buildPaths(grid, width, height, target, paths, dist, new ArrayList<Tile>());
//	}

//	private void buildPaths(Tile[][] grid, int width, int height, Tile currentTile, List<List<SubTile>> paths, int[][] dist, List<Tile> currentPath) {
//		currentPath.add(currentTile);
//		if (currentTile.getX() == 0) {
//			ArrayList<Tile> path = new ArrayList<>(currentPath);
//			path.add(0, new Tile(path.get(0).getX() + 1, path.get(0).getY(), true));
//			path.add(new Tile(currentTile.getX() - 1, currentTile.getY(), true));
//			ArrayList<SubTile> result = new ArrayList<>();
//			for (int i = 1; i < path.size(); i++) {
//				Tile t1 = path.get(i - 1);
//				Tile t2 = path.get(i);
//				for (SubTile sub : t1.connectTo(t2))
//					result.add(sub);
//			}
//			paths.add(result);
//
//			currentPath.remove(currentPath.size() - 1);
//			return;
//		}
//
//		for (Tile neighbor : currentTile.getNeighbors()) {
//			if (neighbor != null && dist[neighbor.getX()][neighbor.getY()] == dist[currentTile.getX()][currentTile.getY()] + 1)
//				buildPaths(grid, width, height, neighbor, paths, dist, currentPath);
//		}
//
//		currentPath.remove(currentPath.size() - 1);
//	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public List<List<SubTile>> getPaths() {
		return paths;
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}

	public void setView(BoardView view) {
		this.view = view;
	}







	public void createAttackerAtPositions(Player owner, Player enemy, int positionY) {

			Attacker a =new Attacker(grid, owner, enemy, positionY);
			AttackerView a_view = view.addAttacker(a);
			a.setView(a_view);

			attackers.add(a);
	}

//	public void test() {
//		for (int i = attackers.size() - 1; i >= 0; i--) {
//			Attacker a = attackers.get(i);
//			a.kill();
//			veterans.add(a);
//			attackers.remove(i);
//		}
//	}

	public void spawnAttackers(int turn) {

		for( int i=veterans.size()-1; i>=0; i-- ) {
			Attacker a = veterans.get(i);
			veterans.remove(i);
			a.spawn();
			attackers.add(a);
		}
	}

	public void updateTowers(){
		ArrayList<Integer> to_del = new ArrayList<Integer>();

		for (int t_i=0; t_i<towers.size(); t_i++) {
			if( towers.get(t_i).isDestroyed() ) {
				to_del.add(t_i);
			}
		}

		// for firebomb only
		Collections.sort(to_del);
		for( int i=0; i<to_del.size(); i++ ) {
			Tower t = towers.get(to_del.get(i)-i);
			t.disappear();
			towers.remove(to_del.get(i)-i);
		}
	}

	public void fireTowers() {
		Collections.sort(towers, new Comparator<Tower>() {
			@Override
			public int compare(Tower t1, Tower t2) {
				int t1Index = Arrays.asList(Tower.TowerOrder).indexOf(t1.getType());
				int t2Index = Arrays.asList(Tower.TowerOrder).indexOf(t2.getType());
				if (t1Index == t2Index) {
					if (t1.getOwner() != t2.getOwner()) // order doesn't matter here, but search will crash without definite ordering
						return t1.getOwner().getIndex() - t2.getOwner().getIndex();
					int result = t1.getTile().getX() * height + t1.getTile().getY() - t2.getTile().getX() * height - t2.getTile().getY();
					if (t1.getOwner().getIndex() == 1)
						result *= -1;
					return result;
				}
				return t1Index - t2Index;
			}
		});

		ArrayList<Integer> to_del = new ArrayList<Integer>();

		//remove those who are already dead
		for (int i = attackers.size() - 1; i >= 0; i--) {
			Attacker a = attackers.get(i);
			if (a.isDead()) {
				attackers.get(i).kill();
				veterans.add(attackers.get(i));
				attackers.remove(i);
			}
		}

		//attack them all
		for (int t_i=0; t_i<towers.size(); t_i++) {
			Tower t = towers.get(t_i);
			t.attack(attackers, towers);
			for (int i = attackers.size() - 1; i >= 0; i--) {
				Attacker a = attackers.get(i);
				if (a.isDead()) {
					attackers.get(i).kill();
					veterans.add(attackers.get(i));
					attackers.remove(i);
					t.getOwner().kill(a);
				}
			}
		}



		for (int i = attackers.size() - 1; i >= 0; i--) {
			Attacker a = attackers.get(i);
//			if (a.hasSucceeded()) {
//				attackers.remove(i);
//				veterans.add(a);
//				a.kill();
//				a.getEnemy().loseLife();
//			}
		}
	}

	public static List<Attacker> getAttackers() {
		return attackers;
	}


	public List<Attacker> getAllAttackersOf(Player player) {
		List<Attacker> all = new ArrayList<>();

		for (Attacker attacker: attackers) {
			if (attacker.getOwner() == player) {
				all.add(attacker);
			}
		}
		return all;
	}

	public List<Attacker> getAllVeteransOf(Player player) {
		List<Attacker> all = new ArrayList<>();

		for (Attacker attacker: veterans) {
			if (attacker.getOwner() == player) {
				all.add(attacker);
			}
		}



		return all;
	}

	public void cacheBuild(Player player, int x, int y, String type) throws InvalidActionException {
		buildActions.add(new BuildAction(player, x, y, type));
	}

	public boolean executeBuilds() throws InvalidActionException {
		if (buildActions.isEmpty())
			return false;

		BuildAction buildAction = null;
		for (BuildAction action : buildActions) {
			if (action.isPriorityBuild()) {
				buildAction = action;
				break;
			}
		}
		if (buildAction == null) buildAction = buildActions.get(0);
		buildActions.remove(buildAction);
		build(buildAction.getPlayer(), buildAction.getX(), buildAction.getY(), buildAction.getType());
		return true;
	}

	private void build(Player player, int x, int y, String type) throws InvalidActionException {

		if(!type.equals("BOMB") && !grid[x][y].canBuild()){
			System.err.println("Player "+player.getIndex()+": Tile (" + x + "/" + y + ") is not available for building");
			return;
		}

		Tower tower = null;
		switch (type.toUpperCase()) {

		case "GUN_TOWER":
			tower = new GunTower(grid[x][y]);
			break;
//		case "FIRE_TOWER":
//			tower = new FireTower(grid[x][y]);
//			break;
		case "STUN_TOWER":
			tower = new GlueTower(grid[x][y]);
			break;
//		case "HEALTOWER":
//			tower = new HealTower(grid[x][y]);
//			break;
		case "SPRING_NORTH":
			tower = new SpringTrap(grid[x][y], 1,this);
			break;
		case "SPRING_EAST":
			tower = new SpringTrap(grid[x][y], 2,this);
			break;
		case "SPRING_SOUTH":
			tower = new SpringTrap(grid[x][y], 3,this);
			break;
		case "SPRING_WEST":
			tower = new SpringTrap(grid[x][y], 4,this);
			break;
		case "BOMB":
			tower = new FireBomb(grid[x][y]);
			break;
		case "WALL":
			tower = new Wall(grid[x][y]);
			break;
		default:
			throw new InvalidActionException("tower type " + type + " unknown", true, player);
		}


		if (player.buy(tower)) {
			towers.add(tower);
			view.addTower(tower);
		} else {
			tower.undoBuild();
			throw new InvalidActionException("not enough money to build a " + type, true, player);
		}
	}

	public void upgrade(Player player, int id, String type) throws InvalidActionException {
		type = type.toUpperCase();
		Tower tower = towers.stream().filter((t) -> t.getId() == id).findFirst().orElse(null);
		if (tower == null)
			throw new InvalidActionException("Tower " + id + " not found", true, player);
		if (tower.getOwner() != player)
			throw new InvalidActionException("Tower " + id + " belongs to the opponent", false, player);
		TowerProperty toUpgrade = TowerProperty.DAMAGE;
		if (type.equals("RANGE"))
			toUpgrade = TowerProperty.RANGE;
		else if (type.equals("RELOAD"))
			toUpgrade = TowerProperty.RELOAD;
		else if (!type.equals("DAMAGE"))
			throw new InvalidActionException("Upgrade attribute " + type + " unknown", true, player);
		if (!tower.canUpgrade(toUpgrade))
			throw new InvalidActionException("can't upgrade " + type + " of tower " + id, false, player);
		tower.upgrade(toUpgrade);
	}

	public List<String> getPlayerInput(Player player, boolean initialInput) {
		List<String> input = new ArrayList<>();
		if (initialInput) {
//			input.add(String.valueOf(player.getIndex()));

			input.add(player.getIndex()+"");
			input.add(width + " " + height);

			// Grid...h lines of w characters...
			for (int y = 0; y < height; y++) {
				StringBuilder sb = new StringBuilder();
				for (int x = 0; x < width; x++) {
					sb.append(grid[x][y].getMapChar());
				}
				input.add(sb.toString());
			}
		}

		// player + opponent
		Player opponent = players.get(0) == player ? players.get(1): player;

		input.add(player.getPlayerMoneyInput() + " " + opponent.getPlayerMoneyInput());
		input.add(player.getPlayerScoresInput() + " " + opponent.getPlayerScoresInput());

		// Collecting the attackers of both of the players...
		ArrayList<Attacker> playerAttacker = new ArrayList<>();
		ArrayList<Attacker> opponentAttacker = new ArrayList<>();

//		System.err.println("" + playerAttacker.size());
//		System.err.println("" + opponentAttacker.size());

		attackers.forEach(attacker -> {
			if (attacker.getOwner() == player) {
				playerAttacker.add(attacker);
			}
			else {
				opponentAttacker.add(attacker);
			}
		});

		veterans.forEach(attacker -> {
			if (attacker.getOwner() == player) {
				playerAttacker.add(attacker);
			}
			else {
				opponentAttacker.add(attacker);
			}
		});

		if (initialInput) {
			// Status of the players' characters before spawning...
			for (int i = 0; i < Constants.CHARACTER_COUNT; ++i) {
				int id = i;
				int posX = -1;
				int posY = -1;
				int health = -1;
				int speed = -1;

				input.add(String.format("%d %d %d %d %d", id, posX, posY, health, speed));
			}

			// Status of the opponents' characters before spawning...
			for (int i = 0; i < Constants.CHARACTER_COUNT; ++i) {
				int id = i;
				int posX = -1;
				int posY = -1;
				int health = -1;
				int speed = -1;

				input.add(String.format("%d %d %d %d %d", id, posX, posY, health, speed));
			}
		}else {

			// Status of the characters of the player...
			playerAttacker.forEach(attacker -> {
				int id = attacker.getId();
				int posX = attacker.getCurrentTile().getX();
				int posY = attacker.getCurrentTile().getY();
				int health = attacker.getHitPoints();
				int speed = attacker.getSpeed();

				input.add(String.format("%d %d %d %d %d", id, posX, posY, health, speed));
			});

			// Status of the characters of the opponent...
			opponentAttacker.forEach(attacker -> {
				int id = attacker.getId();
				int posX = attacker.getCurrentTile().getX();
				int posY = attacker.getCurrentTile().getY();
				int health = attacker.getHitPoints();
				int speed = attacker.getSpeed();

				input.add(String.format("%d %d %d %d %d", id, posX, posY, health, speed));
			});
		}


		// Object count...
		input.add("" + this.towers.size());

		// Sending information of all the objects...
		for (int i = 0; i < towers.size(); ++i) {
			// ...
			Tower t = towers.get(i);
			String type = t.getType();
			int id = t.getId();
			int owner = t.getOwner().getIndex();
			int posX = t.getTile().getX();
			int posY = t.getTile().getY();
			int health = t.getHealth();
			int damage = (int)t.getProperty(TowerProperty.DAMAGE);
			int range = (int)t.getProperty(TowerProperty.RANGE);
			int cooldown = (int)t.getProperty(TowerProperty.RELOAD);
			int bounty =(int) t.getBounty();

			input.add(type + " " + id + " " + owner + " " + posX + " " + posY + " " + health + " " + damage + " " + range + " " + cooldown + " " + bounty);
		}

		return input;
	}

	public void addObject() {
		++this.objCount;
	}

	public void updateView() {
		view.updateView();
		for (Player player : players) {
			player.updateView();
		}

		for (Tower t:towers){
			t.updateTooltip();
		}
	}

	public void checkDeadAttacker() {

		for(Attacker a:attackers){
			a.updateToolTip();
			if( a.hasReachedTarget() ) {
				a.getOwner().addTargetReachScore();
			}
			if(a.hasReachedTarget() || a.isDead()){
				a.kill();
				veterans.add(a);
			}
		}

		for(Attacker d:veterans)
			attackers.remove(d);
	}
}
