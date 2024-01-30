package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class AttackCommand extends Command {
    private String direction;

    public AttackCommand(Player player, Attacker attacker, String direction) {
        super(player, attacker);
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId() + ", " +
                "Direction: " + direction;
    }
}
