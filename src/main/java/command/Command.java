package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public abstract class Command {
    protected Player player;
    protected Attacker attacker;

    protected Command(Player player, Attacker attacker) {
        this.player = player;
        this.attacker = attacker;
    }

    public Player getPlayer() {
        return player;
    }

    public Attacker getAttacker() {
        return attacker;
    }

    public abstract String toString();
}
