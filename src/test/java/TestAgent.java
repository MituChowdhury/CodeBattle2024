import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Survive the attack waves
 **/
class TestAgent {

    static String[] directions= {"EAST","EAST","EAST","EAST","EAST"};
    static StringBuilder initInputBuilder = new StringBuilder();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt();
        // Initial inputs...
        int side = 0;
        int width = in.nextInt();
        int height = in.nextInt();

//        System.err.printf("playerId: %d, width: %d, height: %d\n", 0, width, height);
        initInputBuilder.append("width: ").append(width)
                        .append(", height: ").append(height)
                        .append("\n");

        if (in.hasNextLine()) {
            in.nextLine();
        }

        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
//            System.err.printf("line: %s\n", line);
            initInputBuilder.append(line).append("\n");
        }

//        String yCoords = "0 2 4 12 13";

        System.out.println(0);
        System.out.println(2);
        System.out.println(4);
        System.out.println(8);
        System.out.println(9);

        int cnt =0;
        // game loop
        while (cnt >= 0) {
//            System.err.print(initInputBuilder.toString());

            initInputBuilder.append("Player id: ").append(playerId).append("\n");
            getPlayerInputMoney(in);
            getPlayerInputScore(in);

            // Status of the player's characters....

            if (in.hasNextLine()) {
                in.nextLine();
            }

//            int offset = 4;
//            if (cnt >= 0) {
            getPlayerAttackerStatus("Player", in);
            System.err.println();
            getPlayerAttackerStatus("Opponent", in);
//            }

            getBoardObjectStatus(in);
            System.err.println(initInputBuilder.toString());
            initInputBuilder.setLength(0);
            initInputBuilder.trimToSize();

            System.out.println("go 0");
            System.out.println("go 1");
            System.out.println("go 2");
            System.out.println("go 3");
            System.out.println("go 4");
            System.out.flush();
            cnt++;
        }
    }

    private static void getPlayerInputMoney(Scanner in) {
        // Money....
        int myMoney = in.nextInt();
        int opponentMoney = in.nextInt();

//        System.err.printf("Money: %d, OpponentMoney: %d\n", myMoney, opponentMoney);
        initInputBuilder.append(String.format("Money: %d, OpponentMoney: %d\n", myMoney, opponentMoney));
    }

    private static void getPlayerInputScore(Scanner in) {
        // Scores....
        int myScore = in.nextInt();
        int opponentScore = in.nextInt();

//        System.err.printf("Score: %d, OpponentScore: %d\n", myScore, opponentScore);
        initInputBuilder.append(String.format("Score: %d, OpponentScore: %d\n", myScore, opponentScore));
    }

    private static void getPlayerAttackerStatus(String playerName, Scanner in) {
        // At first, there is neither attackers nor veterans....
//        System.err.println(playerName + " characters: ");
        initInputBuilder.append(String.format("%s characters: \n", playerName));

        for (int i = 0; i < 5; ++i) {
            int playerCharId = in.nextInt();
            int playerPosX = in.nextInt();
            int playerPosY = in.nextInt();
            int playerHealth = in.nextInt();
            int playerSpeed = in.nextInt();

//            System.err.printf("\tId: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Speed: %d\n", playerCharId, playerPosX, playerPosY, playerHealth, playerSpeed);
            initInputBuilder.append(String.format("\tId: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Speed: %d\n", playerCharId, playerPosX, playerPosY, playerHealth, playerSpeed));
        }
    }

    private static void getBoardObjectStatus(Scanner in) {
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

//            System.err.printf(
//                    "Type: %s, Object_id: %d, Owner_index: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Damage: %d, Range: %d, Cooldown: %d, Bounty: %d\n",
//                    type, id, owner, posX, posY, health, damage, range, cooldown, bounty
//            );

            initInputBuilder.append(
                    String.format(
                            "Type: %s, Object_id: %d, Owner_index: %d, Pos_X: %d, Pos_Y: %d, Health: %d, Damage: %d, Range: %d, Cooldown: %d, Bounty: %d\n",
                            type, id, owner, posX, posY, health, damage, range, cooldown, bounty
                    )
            );
        }
    }
}

/*

 Player 1 commands


                    if(playerCharId != -1 && i == 4 && cnt != 0) {
                        if( cnt >= offset ) {
//                            System.out.println("attack " + (playerCharId) + " NORTH");
                            System.out.println("build " + playerCharId + " GUN_TOWER " + playerPosX + " " + (playerPosY));
//                            System.out.println("attack " + playerCharId + " NORTH");
                        } else if( cnt == offset+6) {
                            System.out.println("build " + playerCharId + " GUN_TOWER " + playerPosX + " " + (playerPosY));
                        } else if( cnt == offset+9) {
                            System.out.println("build " + playerCharId + " SPRING_NORTH " + playerPosX + " " + (playerPosY));
                        } else if( cnt == offset+14) {
                            System.out.println("build " + playerCharId + " WALL " + (playerPosX) + " " + playerPosY);
                        } else {
                            System.out.println("go " + playerCharId);
                        }

                    }
                    else if (playerCharId != 1) {
                        System.out.println("go " + playerCharId);// + " " + side + " " + i);
                    }

* */

/*
* Trash
* //                    else if((cnt&1)==1 && cnt>=10){
//                        System.err.println("sdfsdfsadf");
//                        System.out.println("attack "+ playerCharId+" "+directions[i]);
//                    }
*
* //                    String line = in.nextLine();
*
* //                    String line = in.nextLine();
*             // Tower count...
//            int towerCount = in.nextInt();
            // Object count...

//            if (in.hasNextLine())
//                in.nextLine();

//            for (int i = 0; i < towerCount; i++) {
//                String towerAll = in.nextLine();
//                System.err.println(towerAll);
//            }
*
* //                String line = in.nextLine();
*
*   //            System.out.println("go 0");
//            System.out.println("go 1");
//            System.out.println("go 2");
//            System.out.println("go 3");
//            System.out.println("go 4");

//            System.err.println("Debug messages... here "+cnt);
* */