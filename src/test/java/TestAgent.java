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
            int opponentMoney = in.nextInt();
            int opponentLives = in.nextInt();
            int towerCount = in.nextInt();

            System.err.printf("Money: %d, Lives: %d, OpponentMoney: %d, OpponentLives: %d, towerCount: %d\n", myMoney, myLives, opponentMoney, opponentLives, towerCount);

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < towerCount; i++) {
                String towerAll = in.nextLine();
                System.err.println(towerAll);
            }

            int attackerCount = in.nextInt();
            System.err.printf("attackerCount: %d\n", attackerCount);

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < attackerCount; i++) {
                String attackerAll = in.nextLine();
                System.err.println(attackerAll);
            }

//            if (in.hasNextLine()) {
//                in.nextLine();
//            }

            if (cnt == 0) {
                if (playerId == 0)
                    System.out.println("0 2 4 7 10");
                else
                    System.out.println("1 3 5 8 9");
            }
            else {
                System.out.println("BUILD 5 5 GUNTOWER");
            }

//             Write an action using System.out.println()
            System.err.println("Debug messages... here "+cnt);
            cnt++;
            System.out.flush();
        }
    }
}