import java.util.Scanner;

/**
 * Survive the attack waves
 **/
class TestAgentCodingame {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
//        int playerId = in.nextInt();
        // width (space) height
        int width = in.nextInt();
        int height = in.nextInt();

        System.err.printf("playerId: %d, width: %d, height: %d\n", 1, width, height);


        if (in.hasNextLine()) {
            in.nextLine();
        }

        // "height" lines of "width" characters for map....
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
            System.err.printf("line: %s\n", line);
        }

        // Player's output for attacker coordinates...
//        System.out.println("1 3 5 8 10");

        int cnt =0;
        // game loop
        while (true) {
            // myMoney (space) myLives
            int myMoney = in.nextInt();
            int myLives = in.nextInt();

            // opponentMoney, opponentLives
            int opponentMoney = in.nextInt();
            int opponentLives = in.nextInt();

            // towerCount
            int towerCount = in.nextInt();

            System.err.printf("Money: %d, Lives: %d, OpponentMoney: %d, OpponentLives: %d, towerCount: %d\n", myMoney, myLives, opponentMoney, opponentLives, towerCount);

            if (in.hasNextLine())
                in.nextLine();

            // "towerCount" lines of tower inputs...
            for (int i = 0; i < towerCount; i++) {
                String towerAll = in.nextLine();
                System.err.println(towerAll);
            }

            // attackerCount
            int attackerCount = in.nextInt();
            System.err.printf("attackerCount: %d\n", attackerCount);

            if (in.hasNextLine())
                in.nextLine();

            // "attackerCount" lines of attacker inputs...
            for (int i = 0; i < attackerCount; i++) {
                String attackerAll = in.nextLine();
                System.err.println(attackerAll);
            }

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
                // Commands for attackers.....
                System.out.println("BUILD 2 9 FIRETOWER");
                System.out.println("BUILD 3 9 FIRETOWER");
                System.out.println("BUILD 4 9 FIRETOWER");
                System.out.println("BUILD 5 9 FIRETOWER");
                System.out.println("BUILD 9 9 FIRETOWER");
            }

//             Write an action using System.out.println()
            System.err.println("Debug messages... here "+cnt);
            cnt++;
            System.out.flush();
        }
    }
}