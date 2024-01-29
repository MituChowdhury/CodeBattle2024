import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Survive the attack waves
 **/
class TestAgent {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
//        int playerId = in.nextInt();
        // Initial inputs...
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

        String yCoords = "0 2 4 8 9";

        System.out.println(yCoords);
        System.out.println("PASS");
        System.out.println("PASS");
        System.out.println("PASS");
        System.out.println("PASS");

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

            if (cnt != 0) {
                // At first, there is neither attackers nor veterans....
                System.err.println("Player characters: ");

                for (int i = 0; i < 5; ++i) {
                    int playerCharId = in.nextInt();
                    int playerPosX = in.nextInt();
                    int playerPosY = in.nextInt();
                    int playerHealth = in.nextInt();
                    int playerSpeed = in.nextInt();

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

//            int attackerCount = in.nextInt();
//            System.err.printf("attackerCount: %d\n", attackerCount);
//
//            if (in.hasNextLine())
//                in.nextLine();
//
//            for (int i = 0; i < attackerCount; i++) {
//                String attackerAll = in.nextLine();
//                System.err.println(attackerAll);
//            }

//            if (in.hasNextLine()) {
//                in.nextLine();
//            }

//            if (cnt == 0) {
//                if (cnt == 0)
//                    System.out.println("0 2 4 7 10");
//                else
//                    System.out.println("1 3 5 8 9");
//            }
//            else
            {
//                System.out.println("BUILD 5 5 GUNTOWER");
                System.out.println("go 1");
                System.out.println("build 2 GUN_TOWER 20 14");
                System.out.println("attack 3 NORTH");
//                System.out.println("forte");
                System.out.println("");
                System.out.println("wtf");
            }

//             Write an action using System.out.println()
            System.err.println("Debug messages... here "+cnt);
            cnt++;
            System.out.flush();
        }
    }
}