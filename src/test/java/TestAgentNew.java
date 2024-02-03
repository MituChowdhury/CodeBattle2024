import java.util.Scanner;

public class TestAgentNew {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int playerID, w, h;

        // initial input
        playerID = 0;//sc.nextInt();

        // map width and height
        w = sc.nextInt();
        h = sc.nextInt();
        System.err.println("width height done");

        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        // map inputs
        String[] arr = new String[h];

        for (int i = 0; i < h; i++) {
            arr[i] = sc.next();
        }

        // starting position of my pawns
        int start = 0;
        for (int i = 0; i < 5; i++) {
            System.out.println(start);
            start = start + (h / 5);
        }


        int myMoney, oppMoney, scorePlayer, scoreOpp;

        int[][] myplayers = new int[5][5];
        int[][] oPlayers = new int[5][5];

        int turn = 0;
        // game turn
        while (true) {

            // Money
            myMoney = sc.nextInt();
            oppMoney = sc.nextInt();

            // score
            scorePlayer = sc.nextInt();
            scoreOpp = sc.nextInt();

            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            if (turn != 0) {
                // player 0 possitions
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        myplayers[k][l] = sc.nextInt();
                    }
                }

                // player 1 positions
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        oPlayers[k][l] = sc.nextInt();
                    }
                }
            }
            // obstacle numbers input
            int n_t = sc.nextInt();

            if (sc.hasNextLine()) {
                sc.nextLine();
            }


            // obstacle array
            int[][] obs = new int[n_t][2];

            for (int m = 0; m < n_t; m++) {
                String type = sc.next();
                int id = sc.nextInt();
                int owner = sc.nextInt();
                int posX = sc.nextInt();
                int posY = sc.nextInt();
                int health = sc.nextInt();
                int damage = sc.nextInt();
                int range = sc.nextInt();
                int cooldown = sc.nextInt();
                int bounty = sc.nextInt();

                if (owner != playerID) {
                    obs[m][0] = posX;
                    obs[m][1] = posY;
                } else {
                    obs[m][0] = -1;
                    obs[m][1] = -1;
                }
            }

            // players logic to print outputs
            if(turn!=0) {

                for (int p = 0; p < 5; p++) {

                    // if no position given skip this turn
                    if (myplayers[p][0] == -1)
                        continue;

                    // build some objects early
                    if (turn <= 10) {
                        if (turn % 2 == 0 && myMoney >= 400)
                            System.out.println("build p GUN_TOWER " + myplayers[p][1] + " " + myplayers[p][2]);
                        else
                            System.out.println("go " + myplayers[p][0]);
                    }

                    // then only march forward or attack
                    else {
                        switch (isPresent(myplayers[p][1], myplayers[p][2], obs, n_t)) {
                            case 21:
                                System.out.println("attack " + myplayers[p][0] + " west");

                                break;
                            case 23:
                                System.out.println("attack " + myplayers[p][0] + " east");

                                break;
                            case 12:
                                System.out.println("attack " + myplayers[p][0] + " north");
                                break;

                            case 32:
                                System.out.println("attack " + myplayers[p][0] + " south");
                                break;

                            default:
                                System.out.println("go " + myplayers[p][0]);
                                break;
                        }
                    }


                }
            }

            turn++;
            System.out.flush();

        }

    }

    static int isPresent(int x1, int y1, int[][] obs, int size) {

        for (int i = 0; i < size; i++) {
            if (obs[i][0] == x1 && obs[i][1] == y1 + 1)
                return 23;
            else if (obs[i][0] == x1 && obs[i][1] == y1 - 1)
                return 21;
            else if (obs[i][0] == x1 + 1 && obs[i][1] == y1)
                return 32;
            else if (obs[i][0] == x1 - 1 && obs[i][1] == y1)
                return 12;
        }
        return 0;
    }
}