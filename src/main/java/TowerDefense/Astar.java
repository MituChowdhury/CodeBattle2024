package TowerDefense;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Astar {
    public static class Details {
        double cost;
        SubTile current;
        double g;
        double h;
        public Details(SubTile subTile, double g, double h) {
            this.cost = g+h;
            this.current = subTile;
            this.g = g;
            this.h = h;
        }
    }
    private final SubTile[][][][] parent = new SubTile[Constants.MAP_WIDTH][Constants.MAP_HEIGHT][SubTile.SUBTILE_SIZE][SubTile.SUBTILE_SIZE];
    private final ArrayList<SubTile> path = new ArrayList<>();
    boolean isDestination(SubTile pos, SubTile dest) {
        return pos == dest || pos.equals(dest);
    }

    // Method to calculate heuristic function
    double calculateHValue(SubTile src, SubTile dest) {
        return Math.sqrt(Math.pow((src.getX() - dest.getX()), 2.0) + Math.pow((src.getY() - dest.getY()), 2.0));
    }

    private void tracePath(SubTile src, SubTile dest) {   //A* Search algorithm path

        ArrayList<SubTile> temp = new ArrayList<>();

        SubTile st = dest;

        while (parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] != null) {
            temp.add(st);
            st = parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()];
        }
        //System.out.println("--------------------\n\n");
        for (int i=temp.size()-1;i>=0;i--) {
           // System.out.println(temp.get(i).getX() + " " + temp.get(i).getY());
            path.add(temp.get(i));
        }
    }

    public ArrayList<SubTile> findpath(SubTile src, SubTile dest) {
        AstarSearch1(src, dest);
        if(path.isEmpty() && !src.equals(dest)){
            AstarSearch2(src,dest);
        }
        return path;
    }

    private void AstarSearch1(SubTile src, SubTile dest) {
        if (src.equals(dest)) {
            //System.out.println("We're already (t)here...");
            return;
        }

        boolean[][][][] closedList = new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT][SubTile.SUBTILE_SIZE][SubTile.SUBTILE_SIZE];//our closed list
        double[][][][] cost = new double[Constants.MAP_WIDTH][Constants.MAP_HEIGHT][SubTile.SUBTILE_SIZE][SubTile.SUBTILE_SIZE];

        for (int i=0;i<Constants.MAP_WIDTH;i++) {
            for (int j=0;j<Constants.MAP_HEIGHT;j++) {
                for (int k=0;k<SubTile.SUBTILE_SIZE;k++) {
                    for (int l=0;l<SubTile.SUBTILE_SIZE;l++) {
                        closedList[i][j][k][l] = false;
                        cost[i][j][k][l] = Double.MAX_VALUE;
                        parent[i][j][k][l] = null;
                    }
                }
            }
        }

        cost[src.getTile().getX()][src.getTile().getY()][src.getSubX()][src.getSubY()] = 0.0;

        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.cost - o2.cost));

        openList.add(new Details(src,0.0,0.0));

        while (!openList.isEmpty()) {
            Details p = openList.peek();
            openList.poll();

            closedList[p.current.getTile().getX()][p.current.getTile().getY()][p.current.getSubX()][p.current.getSubY()] = true;

            ArrayList<SubTile> neighbors = p.current.getNeighbors();
            for (SubTile st : neighbors) {
                if (isDestination(st, dest)) {
                    parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = p.current;
                    tracePath(src,dest);
                    //System.out.println("The destination cell is found");
                    return;
                }
                else if (!closedList[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] && !st.getTile().hasAnyObject()) {
                    double gNew, hNew, costNew;
                    gNew = p.g + 1.0;
                    hNew = calculateHValue(st, dest);
                    costNew = gNew + hNew;
                    if (cost[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] > costNew) {
                        openList.add(new Details(st, gNew, hNew));
                        cost[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = costNew;
                        parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = p.current;
                    }
                }
            }
        }
        System.out.println("Failed to find the Destination Cell");
    }

    private void AstarSearch2(SubTile src, SubTile dest) {
        if (src.equals(dest)) {
            return;
        }

        boolean[][][][] closedList = new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT][SubTile.SUBTILE_SIZE][SubTile.SUBTILE_SIZE];//our closed list
        double[][][][] cost = new double[Constants.MAP_WIDTH][Constants.MAP_HEIGHT][SubTile.SUBTILE_SIZE][SubTile.SUBTILE_SIZE];

        for (int i=0;i<Constants.MAP_WIDTH;i++) {
            for (int j=0;j<Constants.MAP_HEIGHT;j++) {
                for (int k=0;k<SubTile.SUBTILE_SIZE;k++) {
                    for (int l=0;l<SubTile.SUBTILE_SIZE;l++) {
                        closedList[i][j][k][l] = false;
                        cost[i][j][k][l] = Double.MAX_VALUE;
                        parent[i][j][k][l] = null;
                    }
                }
            }
        }

        cost[src.getTile().getX()][src.getTile().getY()][src.getSubX()][src.getSubY()] = 0.0;

        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.cost - o2.cost));

        openList.add(new Details(src,0.0,0.0));

        while (!openList.isEmpty()) {
            Details p = openList.peek();
            openList.poll();

            closedList[p.current.getTile().getX()][p.current.getTile().getY()][p.current.getSubX()][p.current.getSubY()] = true;

            ArrayList<SubTile> neighbors = p.current.getNeighbors();
            for (SubTile st : neighbors) {
                if (isDestination(st, dest)) {
                    parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = p.current;
                    tracePath(src,dest);
                    //System.out.println("The destination cell is found");
                    return;
                }
                else if (!closedList[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] && !st.getTile().hasNonDestructibleObject()) {
                    double gNew, hNew, costNew;
                    gNew = p.g + 1.0;
                    hNew = calculateHValue(st, dest);
                    costNew = gNew + hNew;
                    if (cost[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] > costNew) {
                        openList.add(new Details(st, gNew, hNew));
                        cost[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = costNew;
                        parent[st.getTile().getX()][st.getTile().getY()][st.getSubX()][st.getSubY()] = p.current;
                    }
                }
            }
        }
        System.out.println("Failed to find the Destination Cell");
    }
}
