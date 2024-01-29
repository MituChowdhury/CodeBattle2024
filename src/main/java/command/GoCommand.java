package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class GoCommand extends Command {
    private Player player;
    private Attacker attacker;
//    private Attacker attacker;

    public GoCommand(Player player, Attacker attacker) {
        this.player = player;
        this.attacker = attacker;
    }

    public Player getPlayer() {
        return player;
    }

    public Attacker getAttacker() {
        return attacker;
    }

    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId();
    }

    //    public Attacker getCharacterToMove() {
//        return this.attacker;
//    }
}
