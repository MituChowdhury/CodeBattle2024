package command;

import TowerDefense.Attacker;
import com.codingame.game.Player;
import exception.BadCommandException;

public class BuildCommand extends Command {
    private String objectName;
    private int posX;
    private int posY;

    public BuildCommand(Player player, Attacker attacker, String objectName, int posX, int posY) throws BadCommandException {
        super(player, attacker);
        this.objectName = objectName;
        this.posX = posX;
        this.posY = posY;
        int attackerX = attacker.getCurrentTile().getX();
        int attackerY = attacker.getCurrentTile().getY();
        if( posX > attackerX+1 || posX < attackerX-1 || posY > attackerY+1 || posY < attackerY-1 ) {
            throw new BadCommandException("Build coordinate is invalid");
        }
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

    @Override
    public String toString() {
        return "Player: " + player.getIndex() + ", " +
                "Attacker: " + attacker.getId() + ", " +
                "Object name: " + objectName + ", " +
                "X: " + posX + ", " +
                "Y: " + posY;
    }
}
