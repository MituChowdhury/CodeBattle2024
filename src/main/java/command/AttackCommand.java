package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class AttackCommand extends Command {
    private Player player;
//    private int characterId;
    private Attacker attacker;
    private String direction;

    public AttackCommand(Player player, Attacker attacker, String direction) {
        this.player = player;
        this.attacker = attacker;
        this.direction = direction;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Attacker getAttacker() {
        return this.attacker;
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
