package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class GoCommand extends Command {
//    private Attacker attacker;

    public GoCommand(Player player, Attacker attacker) {
        super(player, attacker);
    }


    @Override
    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId();
    }

    //    public Attacker getCharacterToMove() {
//        return this.attacker;
//    }
}
