import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NewAgent {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        String[] linewords = new String[]{};
//
//        linewords = in.nextLine().split(" ");
        String playerId = in.nextLine();
        System.err.println("playerId: " + playerId);

//        String width_height = in.nextLine();
//        System.err.println("width, height: " + width_height);
//        int height = Integer.parseInt(width_height.split(" ")[1]);
        int width = in.nextInt();
        int height = in.nextInt();

        if (in.hasNextLine())
            in.nextLine();

        for (int i = 0; i < height; ++i) {
            String line = in.nextLine();
            System.err.println(line);
        }

        int cnt = 0;

        while (true) {
            String myMoney_myLives = in.nextLine();
            String opponentMoney_opponentLives = in.nextLine();
//            String towerCountLine = in.nextLine();
//            int towerCount = Integer.parseInt(towerCountLine.split(" ")[0]);
            int towerCount = in.nextInt();

            System.err.println(myMoney_myLives);
            System.err.println(opponentMoney_opponentLives);
            System.err.println(towerCount);

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < towerCount; ++i) {
                String all = in.nextLine();
                System.err.println(all);
            }

//            String attackerCountLine = in.nextLine();
//            int attackerCount = Integer.parseInt(attackerCountLine.split(" ")[0]);
            int attackerCount = in.nextInt();

            if (in.hasNextLine())
                in.nextLine();

            for (int i = 0; i < attackerCount; ++i) {
                String all = in.nextLine();
                System.err.println(all);
            }

            if (cnt == 0) {
                ArrayList<Integer> arr = new ArrayList<Integer>();
                Random random = new Random();

                while (arr.size() < 5) {
                    int randInt = random.nextInt(17);

                    if (!arr.contains(randInt)) {
                        arr.add(randInt);
                    }
                }

                StringBuilder str = new StringBuilder();

                for (Integer randInt: arr) {
                    str.append(randInt.toString()).append(" ");
                }

                System.out.println(str);
            }


            System.err.println("Debug messages... here " + cnt);
            cnt++;
            System.out.println("BUILD 5 5 GUNTOWER"); // BUILD x y TOWER | UPGRADE id PROPERTY
        }
    }
}
