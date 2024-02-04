import java.util.Scanner;

/**
 * Survive the attack waves
 **/
class TestAgentTwo {

    static String[] directions= {"EAST","EAST","EAST","EAST","EAST"};
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
//        int playerId = in.nextInt();
        // Initial inputs...
        int side = 0;
        int width = in.nextInt();
        int height = in.nextInt();

        System.err.printf("playerId: %d, width: %d, height: %d\n", 0, width, height);


        if (in.hasNextLine()) {
            in.nextLine();
        }

        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            System.err.printf("line: %s\n", line);
        }

        String yCoords = "0 2 4 12 13";

        System.out.println(0);
        System.out.println(2);
        System.out.println(4);
        System.out.println(8);
        System.out.println(9);

        int cnt =0;
        // game loop
        while (true) {
            // Money....
            int myMoney = in.nextInt();
            int opponentMoney = in.nextInt();

            // Scores....
            int myScore = in.nextInt();
            int opponentScore = in.nextInt();

            System.err.printf("Money: %d, Score: %d, OpponentMoney: %d, OpponentScore: %d\n", myMoney, myScore, opponentMoney, opponentScore);

            // Status of the player's characters....

            if (in.hasNextLine()) {
                in.nextLine();
            }

            int offset = 5;
            if (cnt != 0) {
                // At first, there is neither attackers nor veterans....
                System.err.println("Player characters: ");

                for (int i = 0; i < 5; ++i) {
                    int playerCharId = in.nextInt();
                    int playerPosX = in.nextInt();
                    int playerPosY = in.nextInt();
                    int playerHealth = in.nextInt();
                    int playerSpeed = in.nextInt();


                    if( i == 4 ) {
                        if( cnt == offset ) {
//                            System.out.println("attack " + (playerCharId) + " NORTH");
                            System.out.println("attack " + (playerCharId) + " NORTH");
//                            System.out.println("build " + playerCharId + " STUN_TOWER " + (playerPosX + 1) + " " + (playerPosY));
//                            System.out.println("attack " + playerCharId + " NORTH");
                        } else if( cnt == offset+6) {
//                            System.out.println("build " + playerCharId + " GUN_TOWER " + playerPosX + " " + (playerPosY + 1));
                            System.out.println("build " + playerCharId + " BOMB " + playerPosX + " " + (playerPosY + 1));
                        } else if( cnt == offset+9) {
                            System.out.println("build " + playerCharId + " SPRING_NORTH " + playerPosX + " " + (playerPosY));
                        } else if( cnt == offset+14) {
                            System.out.println("build " + playerCharId + " WALL " + (playerPosX) + " " + playerPosY);
                        } else {
                            System.out.println("go " + playerCharId);
                        }

                    }

//                    else if((cnt&1)==1 && cnt>=10){
//                        System.err.println("sdfsdfsadf");
//                        System.out.println("attack "+ playerCharId+" "+directions[i]);
//                    }
                    else {
                        System.out.println("go " + playerCharId);// + " " + side + " " + i);
                    }

                    System.err.printf("\tId: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Speed: %d\n", playerCharId, playerPosX, playerPosY, playerHealth, playerSpeed);

//                    String line = in.nextLine();
                }

                // Status of the opponent's characters....
                System.err.println();
                System.err.println("Opponent characters: ");

                for (int i = 0; i < 5; ++i) {
                    int opponentCharId = in.nextInt();
                    int opponentPosX = in.nextInt();
                    int opponentPosY = in.nextInt();
                    int opponentHealth = in.nextInt();
                    int opponentSpeed = in.nextInt();

                    System.err.printf("\tId: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Speed: %d\n", opponentCharId, opponentPosX, opponentPosY, opponentHealth, opponentSpeed);

//                    String line = in.nextLine();
                }
            }

            // Tower count...
//            int towerCount = in.nextInt();
            // Object count...

//            if (in.hasNextLine())
//                in.nextLine();

//            for (int i = 0; i < towerCount; i++) {
//                String towerAll = in.nextLine();
//                System.err.println(towerAll);
//            }

            int objCount = in.nextInt();

            if (in.hasNextLine()) {
                in.nextLine();
            }

            for (int i = 0; i < objCount; ++i) {
                String type = in.next();
                int id = in.nextInt();
                int owner = in.nextInt();
                int posX = in.nextInt();
                int posY = in.nextInt();
                int health = in.nextInt();
                int damage = in.nextInt();
                int range = in.nextInt();
                int cooldown = in.nextInt();
                int bounty = in.nextInt();

//                String line = in.nextLine();
            }


//            System.out.println("go 0");
//            System.out.println("go 1");
//            System.out.println("go 2");
//            System.out.println("go 3");
//            System.out.println("go 4");

//            System.err.println("Debug messages... here "+cnt);
            cnt++;
            System.out.flush();
        }
    }
}