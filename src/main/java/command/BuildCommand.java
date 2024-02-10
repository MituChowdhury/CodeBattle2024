package command;

import TowerDefense.Attacker;
import TowerDefense.Constants;
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

        //if null then this command will be ignored.
        if(attacker!=null) {
            int attackerX = attacker.getCurrentTile().getX();
            int attackerY = attacker.getCurrentTile().getY();


            if (attackerX == posX && attackerY == posY) {
                throw new BadCommandException("An attacker cannot build anything in its current position.");
            }

            if (posX < 0 || posX > Constants.MAP_WIDTH - 1 || posY < 0 || posY > Constants.MAP_HEIGHT - 1) {
                throw new BadCommandException("The position is out of the map.");
            }

            boolean isValid = false;

            if (!objectName.equals("BOMB")) {
                isValid = (Math.abs(posX - attackerX) + Math.abs(posY - attackerY)) == 1;
            } else {

                isValid = Math.abs(posX - attackerX) <= Constants.FIREBOMB_THROW_RANGE &&
                        Math.abs(posY - attackerY) <= Constants.FIREBOMB_THROW_RANGE;
            }

            if (!isValid) {
                throw new BadCommandException("Build not possible. Minion cords- :("+attackerX+"/"+attackerY+"). target: ("
                        +posX+"/"+posY+").");
            }
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
