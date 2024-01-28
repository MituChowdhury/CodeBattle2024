import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Survive the attack waves
 **/
class TestAgentOld {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt();
        int width = in.nextInt();
        int height = in.nextInt();

        System.err.printf("playerId: %d, width: %d, height: %d\n", playerId, width, height);


        if (in.hasNextLine()) {
            in.nextLine();
        }

        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            System.err.printf("line: %s\n", line);
        }


        int cnt =0;
        // game loop
        while (true) {
            int myMoney = in.nextInt();
            int myLives = in.nextInt();
//            String myMoneyLives = in.nextLine();
            int opponentMoney = in.nextInt();
            int opponentLives = in.nextInt();
            int towerCount = in.nextInt();

            System.err.printf("Money: %d, Lives: %d, OpponentMoney: %d, OpponentLives: %d, towerCount: %d\n", myMoney, myLives, opponentMoney, opponentLives, towerCount);
//            System.err.printf("Money, Lives: %s, OpponentMoney: %d, OpponentLives: %d, towerCount: %d\n", myMoneyLives, opponentMoney, opponentLives, towerCount);

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < towerCount; i++) {
//                int towerType = in.nextInt();
//                int towerId = in.nextInt();
//                int owner = in.nextInt();
//                int x = in.nextInt();
//                int y = in.nextInt();
//
//                System.err.printf("towerType: %d, towerId: %d, owner: %d, x: %d, y: %d\n", towerType, towerId, owner, x, y);
//
//                int damage = in.nextInt();
//                float attackRange = in.nextFloat();
//                int reload = in.nextInt();
//                int coolDown = in.nextInt();
//
//                System.err.printf("damage: %d, attackRange: %f, reload: %d, coolDown: %d\n", damage, attackRange, reload, coolDown);
                String towerAll = in.nextLine();
                System.err.println(towerAll);
            }

            int attackerCount = in.nextInt();
            System.err.printf("attackerCount: %d\n", attackerCount);

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < attackerCount; i++) {
//                int attackerId = in.nextInt();
//                int owner = in.nextInt();
//                float x = in.nextFloat();
//                float y = in.nextFloat();
//
//                System.err.printf("attackerId: %d, owner: %d, x: %f, y: %f\n", attackerId, owner, x, y);
//
//                int hitPoints = in.nextInt();
//                int maxHitPoints = in.nextInt();
//                float currentSpeed = in.nextFloat();
//                float maxSpeed = in.nextFloat();
//                int slowTime = in.nextInt();
//                int bounty = in.nextInt();
//
//                System.err.printf("hitPoints: %d, maxHitPoints: %d, currentSpeed: %f, maxSpeed: %f, slowTime: %d, bounty: %d\n", hitPoints, maxHitPoints, currentSpeed, maxSpeed, slowTime, bounty);
                String attackerAll = in.nextLine();
                System.err.println(attackerAll);
            }

//            if (in.hasNextLine()) {
//                in.nextLine();
//            }

//             Write an action using System.out.println()
            System.err.println("Debug messages... here "+cnt);
            cnt++;

//            if (playerId == 0)
//            System.out.println("BUILD 5 5 GUNTOWER"); // BUILD x y TOWER | UPGRADE id PROPERTY
//            else
//                System.out.println("BUILD 10 10 FIRETOWER");
            System.out.flush();
        }
    }
}