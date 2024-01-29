package TowerDefense;

import java.util.ArrayList;

public class SubTile {
	public final static int SUBTILE_SIZE = Constants.SUBTILE_SIZE;
	private Tile tile;
	//TODO: made them public for the sake of debugging
	public int subX;
	public int subY;
	private ArrayList<SubTile> neighbors = new ArrayList<>();

	public SubTile(Tile tile, int subX, int subY) {
		this.tile = tile;
		this.subX = subX;
		this.subY = subY;
	}

	public Tile getTile() {
		return tile;
	}

	public int getSubX() {
		return subX;
	}

	public int getSubY() {
		return subY;
	}

	public double getX() {
		return (tile.getX() + (double) subX / SUBTILE_SIZE);
	}

	public double getY() {
		return (tile.getY() + (double) subY / SUBTILE_SIZE);
	}

//	public SubTile mirror(Tile[][] grid, int width, int height) {
//		Tile t = new Tile(width - 2 - tile.getX(), height - 2 - tile.getY(), true);
//		SubTile result = new SubTile(t, SUBTILE_SIZE - subX, SUBTILE_SIZE - subY);
//		return result;
//	}
	private static int[] dx = { 0, 1, 0, -1 };
	private static int[] dy = { 1, 0, -1, 0 };

	// working here
	public void initNeighbors(Tile [][] grid) {
		for (int dir = 0; dir < 4; dir++) {
			int x_ = this.subX + dx[dir];
			int tx_ = tile.getX() + dx[dir];
			int y_ = this.subY + dy[dir];
			int ty_ = tile.getY() + dy[dir];
			if (x_ < 0) {
				if (tx_ >= 0 && tx_ < grid.length && ty_ >= 0 && ty_ < grid[0].length) {
					neighbors.add(grid[tx_][ty_].getSubTile(SUBTILE_SIZE + x_, y_));
				}
			}
			else if (y_ < 0) {
				if (tx_ >= 0 && tx_ < grid.length && ty_ >= 0 && ty_ < grid[0].length) {
					neighbors.add(grid[tx_][ty_].getSubTile(x_,SUBTILE_SIZE + y_));
				}
			}
			else if(x_ >= SUBTILE_SIZE){
				if (tx_ >= 0 && tx_ < grid.length && ty_ >= 0 && ty_ < grid[0].length) {
					neighbors.add(grid[tx_][ty_].getSubTile(SUBTILE_SIZE - x_, y_));
				}
			}
			else if(y_ >= SUBTILE_SIZE) {
				if (tx_ >= 0 && tx_ < grid.length && ty_ >= 0 && ty_ < grid[0].length) {
					neighbors.add(grid[tx_][ty_].getSubTile(x_,SUBTILE_SIZE - y_));
				}
			}
			else {
				neighbors.add(tile.getSubTile(x_,y_));
			}
		}
//		if(tile.getX()==0 && tile.getY()==16){System.out.println("--------PRINTING NEIGHBORS--------------\n\n\n");
//			for ( SubTile s : neighbors) {
//				System.out.println(s.getTile().getX() + " " + s.getTile().getY() +  " " + s.getX() + " " + s.getY());
//			}
//		}
	}

	public ArrayList<SubTile> getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		return String.format("%.1f %.1f", getX(), getY());
	}
}
