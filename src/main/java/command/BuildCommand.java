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
        int attackerX = attacker.getCurrentTile().getX();
        int attackerY = attacker.getCurrentTile().getY();

//        if( posX > attackerX+1 || posX < attackerX-1 || posY > attackerY+1 || posY < attackerY-1 ) {
//            throw new BadCommandException("Build coordinate is invalid");
//        }

        if (attackerX == posX && attackerY == posY) {
            throw new BadCommandException("An attacker cannot build anything in its current position.");
        }

        if (posX < 0 || posX > Constants.MAP_WIDTH - 1 || posY < 0 || posY > Constants.MAP_HEIGHT - 1) {
            throw new BadCommandException("The position is out of the map.");
        }

        boolean isValid = false;

        if (!objectName.equals("BOMB")) {
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};

            for (int i = 0; i < 4; ++i) {
                if (attackerX + dx[i] == posX && attackerY + dy[i] == posY) {
                    isValid = true;
                    break;
                }
            }
        }
        else {
            int[] dx = {0, 0, 2, -2};
            int[] dy = {2, -2, 0, 0};

            for (int i = 0; i < 4; ++i) {
                if ((attackerX + dx[i] == posX && attackerY + dy[i] > posY)
                        ||
                    (attackerX + dx[i] < posX && attackerY + dy[i] == posY)) {
                    isValid = true;
                    break;
                }
            }
        }

        if (!isValid) {
            throw new BadCommandException("Build coordinate is invalid.");
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
