package TowerDefense;

import java.util.ArrayList;

public class Tile {
	private int x;
	private int y;
	private boolean canyon;
	private boolean Dobstacle;
	Tower obstacleTower = null;
	private boolean NDobstacle;
	private ArrayList<SubTile> subTiles = new ArrayList<>();
	private Tile[] neighbors = new Tile[4];
	private SpringTrap sTrap;

	public Tile(int x, int y, boolean canyon, boolean Dobstacle, boolean NDobstacle) {
		this.x = x;
		this.y = y;
		this.canyon = canyon;
		this.Dobstacle = Dobstacle;
		this.NDobstacle = NDobstacle;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Tile[] getNeighbors() {
		return neighbors;
	}

	public boolean canEnter() {
		return canyon;
	}

	public boolean hasDestructibleObject() { return Dobstacle;}

	public boolean hasNonDestructibleObject() { return NDobstacle;}

	public void setDestructibleObject(Tower t){
		this.Dobstacle = true;
		this.obstacleTower = t;
	}

	public void unsetDestructibleObject(){
		this.Dobstacle = false;
		this.obstacleTower = null;
	}
	public boolean hasAnyObject(){
		return  Dobstacle || NDobstacle;
	}

	public boolean canBuild() {
		return !hasAnyObject() && sTrap == null;
	}

	public char getMapChar() {
		if (canyon)
			return '.';
		return '#';
	}

	public Tile getTile(int x, int y) {
		return this;
	}

	public boolean isCanyon() {
		return !Dobstacle;
	}

	public void setCanyon(boolean canyon) {
		this.canyon = canyon;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

	private static int[] dx = { 0, 1, 0, -1 };
	private static int[] dy = { 1, 0, -1, 0 };

	public void initNeighbors(Tile[][] grid) {
		for (int dir = 0; dir < 4; dir++) {
			int x_ = x + dx[dir];
			int y_ = y + dy[dir];
			if (x_ >= 0 && x_ < grid.length && y_ >= 0 && y_ < grid[0].length) {
				neighbors[dir] = grid[x_][y_];
			}
		}
	}
	// returns the subTiles of a tile ( top left , up to down )
	public void initSubTiles() {
		for( int i = 0; i< SubTile.SUBTILE_SIZE; i++) {
			for( int j = 0; j< SubTile.SUBTILE_SIZE; j++) {
				subTiles.add(new SubTile(this, i, j));
			}
		}
	}

	public ArrayList<SubTile> getSubTiles(){
		return  subTiles;
	}


//	public ArrayList<SubTile> connectTo(Tile to) {
//		ArrayList<SubTile> result = new ArrayList<>();
//		if (this.x < to.x) {
//			for (int i = 0; i < SubTile.SUBTILE_SIZE; i++)
//				result.add(new SubTile(this, i, 0));
//		} else if (this.y < to.y) {
//			for (int i = 0; i < SubTile.SUBTILE_SIZE; i++)
//				result.add(new SubTile(this, 0, i));
//		} else if (this.x > to.x) {
//			for (int i = SubTile.SUBTILE_SIZE; i > 0; i--)
//				result.add(new SubTile(to, i, 0));
//		} else if (this.y > to.y) {
//			for (int i = SubTile.SUBTILE_SIZE; i > 0; i--)
//				result.add(new SubTile(to, 0, i));
//		}
//		return result;
//	}

	public SubTile getSubTile(int subX, int subY) {

		int gg = subX*(SubTile.SUBTILE_SIZE-1)+subX+subY;
		if( gg >= 100 ) {
			return subTiles.get(0);
		}
		return subTiles.get(subX*(SubTile.SUBTILE_SIZE-1)+subX+subY);

		// 0,0  1,0  2,0  3,0
		// 0,1  1,1  2,1  3,1
		// 0,2  1,2  2,2  3,3
	}

	public SpringTrap getSpring() {
		return sTrap;
	}

	public void setSpring(SpringTrap isSpring){
		this.sTrap = isSpring;
	}
}
