package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class AttackCommand extends Command {
    private String direction;

    public AttackCommand(Player player, Attacker attacker, String direction) {
        super(player, attacker);
        this.direction = direction;
    }

    public int getDirection() {
        int dir = 0;
        switch (this.direction) {
            case "NORTH":
                dir = 1;
                break;
            case "EAST":
                dir = 2;
                break;
            case "SOUTH":
                dir = 3;
                break;
            case "WEST":
                dir = 4;
                break;
        }
        return dir;
    }

    @Override
    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId() + ", " +
                "Direction: " + direction;
    }
}
