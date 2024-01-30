package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;

public class BuildCommand extends Command {
    private Player player;
//    private int characterId;
    private Attacker attacker;
    private String objectName;
    private int posX;
    private int posY;

    public BuildCommand(Player player, Attacker attacker, String objectName, int posX, int posY) {
        this.player = player;
        this.attacker = attacker;
        this.objectName = objectName;
        this.posX = posX;
        this.posY = posY;
    }

    public Player getPlayer() {
        return player;
    }

    public Attacker getAttacker() {
        return attacker;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId() + ", " +
                "Object name: " + objectName + ", " +
                "X: " + posX + ", " +
                "Y: " + posY;
    }
}
