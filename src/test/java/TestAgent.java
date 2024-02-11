import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Survive the attack waves
 **/
public class TestAgent {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt();
        int width = in.nextInt();
        int height = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
        }

        int turn =1;

        // game loop
        while (true) {
            int myMoney = in.nextInt();
            int opponentMoney = in.nextInt();
            int myScore = in.nextInt();
            int opponentScore = in.nextInt();
            System.err.println("Player Me:");
            for (int i = 0; i < 5; i++) {
                int myCharacterId = in.nextInt();
                int positionX = in.nextInt();
                int positionY = in.nextInt();
                int health = in.nextInt();
                int speed = in.nextInt();

                System.err.printf("%d %d %d %d %d\n",myCharacterId,positionX,positionY,health,speed);
            }
            System.err.println("Player Me:");
            for (int i = 0; i < 5; i++) {
                int opponentCharacterId = in.nextInt();
                int positionX = in.nextInt();
                int positionY = in.nextInt();
                int health = in.nextInt();
                int speed = in.nextInt();
                System.err.printf("%d %d %d %d %d\n",opponentCharacterId,positionX,positionY,health,speed);
            }
            int objCount = in.nextInt();

            for (int i = 0; i < objCount; i++) {
                String type = in.next();
                int id = in.nextInt();
                int owner = in.nextInt();
                int positionX = in.nextInt();
                int positionY = in.nextInt();
                int health = in.nextInt();
                int damage = in.nextInt();
                int range = in.nextInt();
                int cooldown = in.nextInt();
                int bounty = in.nextInt();
            }



            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            if(turn==1){
                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("0");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("2");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("4");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("8");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("9");
            }
            else {



                // go character_id | build character_id object position_x position_y | attack character_id direction
                    System.out.println("go 0");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                if(turn%2==0)
                    System.out.println("attack 1 SOUTH");
                else
                // go character_id | build character_id object position_x position_y | attack character_id direction
                    System.out.println("go 1");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // go character_id | build character_id object position_x position_y | attack character_id direction
                    System.out.println("go 2");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                if(turn==2){
                    if(playerId==0)
                        System.out.println("build 3 GUN_TOWER 0 7");
                    else{
                        System.out.println("build 3 GUN_TOWER 19 7");
                    }
                }

                else if(turn==3){
                    if(playerId==0)
                        System.out.println("build 3 BOMB 0 7");
                    else{
                        System.out.println("build 3 BOMB 19 7");
                    }
                }
                else if(turn ==94 && playerId==1){
                    System.out.println("build 3 WALL 19 7");
                }
                else
                // go character_id | build character_id object position_x position_y | attack character_id direction
                System.out.println("go 3");

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");



                // go character_id | build character_id object position_x position_y | attack character_id direction

                if(turn%2==0) {
                    System.out.println("go 4");
                }
                else{
                    System.out.println("attack 4 NORTH");
                }
            }

            System.err.println("Turn: "+turn);
            turn++;
            System.out.flush();

        }
    }
}