package TowerDefense;

import com.codingame.game.Referee;

import java.util.*;

public class PathFinder {

    //enemy==1 right hand side base is the target
    private static  boolean [][] visited;
    private static  Tile [][] parents;
    private static final Random random = Referee.random;

    private static int enemy;
    public static void init(int _enemy) {
        enemy = _enemy;
    }

    private  static  int [] dx= {0,-1,0,1};
    private static  int [] dy = {1,0,-1,0};

    private static  boolean inRange(int x, int y){
        return x>=0 && x<Constants.MAP_WIDTH && y>=0 && y<Constants.MAP_HEIGHT;
    }

    public static void BFS(Tile curr, Tile [][] grid) {


        LinkedList<Tile> q = new LinkedList<>();
        q.push(curr);


        int x = curr.getX();
        int y = curr.getY();
        visited[x][y] = true;
        parents[x][y] = null;



        while (!q.isEmpty()) {
            curr = q.poll();

            x = curr.getX();
            y = curr.getY();


            for (int i = 0; i < dx.length; i++) {

                int _x = x + dx[i];
                int _y = y + dy[i];

                if (!inRange(_x, _y)) continue;

                if (!grid[_x][_y].hasAnyObject() && !visited[_x][_y]) {

                            parents[_x][_y] = curr;
                            q.add(grid[_x][_y]);
                            visited[_x][_y] = true;

                }
            }
        }
    }

    private static void printMap(boolean [][]visited, Tile[][] grid){
        System.out.println("-----------------------------");
        for (int j = 0; j <visited[0].length ; j++) {
            for (int i = 0; i <visited.length ; i++) {


                if(grid[i][j].hasAnyObject())
                    System.out.print('#');
                else
                    System.out.print(visited[i][j]?".":"*");
            }
            System.out.println();
        }

        System.out.println("-----------------------------");

    }

    public  static boolean[][] getOptimalPathTiles(Tile start, Tile[][] grid){

        visited = new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
        parents = new Tile[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
        BFS(start ,grid);


        int dest_x = enemy==1?Constants.MAP_WIDTH-1:0;
        int dest_y = Constants.MAP_HEIGHT/2;


        double min_dist = Double.MAX_VALUE/2;


        for (int i = 0; i <Constants.MAP_WIDTH ; i++) {
            for (int j = 0; j <Constants.MAP_HEIGHT ; j++) {

                if(!visited[i][j]) continue;

                double dist = Math.sqrt( Math.pow(i-dest_x,2)+Math.pow(j-dest_y,2));

                if(dist<min_dist)
                    min_dist = dist;
            }
        }


        ArrayList<Tile> destTiles = new ArrayList<>();
        for (int i = 0; i <Constants.MAP_WIDTH ; i++) {
            for (int j = 0; j <Constants.MAP_HEIGHT ; j++) {

                if(!visited[i][j]) continue;

                double dist = Math.sqrt(Math.pow(i-dest_x,2)+Math.pow(j-dest_y,2));

                if(Math.abs(dist-min_dist)<0.0000001){
                        destTiles.add(grid[i][j]);
                }
            }
        }

        boolean[][] allPathsTiles = new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
        for(Tile t: destTiles){

            Tile curr = t;
            while (curr!=null && curr!=start){
                allPathsTiles[curr.getX()][curr.getY()] = true;
                curr = parents[curr.getX()][curr.getY()];
            }
            if(curr!=null)
                allPathsTiles[curr.getX()][curr.getY()] = true;
        }





        boolean optimalPathTile [][]= new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];

        optimalPathTile[start.getX()][start.getY()] = true;

        Tile currentTile = start;

        while(true){
            ArrayList<Tile> possibleNeighbours = new ArrayList<>();

            for (int i = 0; i <dx.length ; i++) {
                    int _x = currentTile.getX() + dx[i];
                    int _y = currentTile.getY() + dy[i];

                    if(!inRange(_x,_y))continue;

                    if(allPathsTiles[_x][_y] && !optimalPathTile[_x][_y]){
                        possibleNeighbours.add(grid[_x][_y]);
                    }

            }

            if(possibleNeighbours.isEmpty())
                break;

            Tile choosenNextTile = possibleNeighbours.get(random.nextInt(possibleNeighbours.size()));
            optimalPathTile[choosenNextTile.getX()][choosenNextTile.getY()] = true;
            currentTile = choosenNextTile;

        }


        return optimalPathTile;


    }

    private static  double getDist(SubTile a, SubTile b){

        double x = a.getX();
        double y = a.getY();

        return  Math.sqrt(Math.pow(x-b.getX(),2)+Math.pow(y-b.getY(),2));
    }

    public static ArrayList<SubTile> getOptimalPath(SubTile currentSubTile, boolean[][] optimalTiles) {

        ArrayList<SubTile> path = new ArrayList<>();

        while (true) {

            Tile currentTile = currentSubTile.getTile();
            int x = currentTile.getX();
            int y = currentTile.getY();
            optimalTiles[x][y] = false;

            SubTile targetSubtile = null;

            for (Tile t : currentTile.getNeighbors()) {
                if (t != null && optimalTiles[t.getX()][t.getY()]){

                    if(targetSubtile!=null){
                        if (random.nextDouble()>.5)
                            targetSubtile = t.getSubTile(SubTile.SUBTILE_SIZE/2,SubTile.SUBTILE_SIZE/2);
                    }
                    else
                        targetSubtile = t.getSubTile(SubTile.SUBTILE_SIZE/2,SubTile.SUBTILE_SIZE/2);
                }
            }

            if(targetSubtile==null){
                path.add(currentTile.getSubTile(SubTile.SUBTILE_SIZE/2,SubTile.SUBTILE_SIZE/2));
                break;
            }

            while(currentSubTile.getTile() == currentTile){

                ArrayList<SubTile> negh =  currentSubTile.getNeighbors();
                SubTile minSub = negh.get(0);

                for(SubTile st:negh){
                    if(getDist(st,targetSubtile)<getDist(minSub,targetSubtile)){
                        minSub = st;
                    }
                }

                path.add(minSub);
                currentSubTile = minSub;
            }

            if(path.size()>180)
                break;

        }

        return path;
    }
}
