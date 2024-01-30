package com.codingame.game;

import TowerDefense.Attacker;
import TowerDefense.Board;
import TowerDefense.Constants;
import command.AttackCommand;
import command.BuildCommand;
import command.GoCommand;
import exception.BadCommandException;

public class Util {
    // Go: go charId
    public static boolean checkGoStructure(String[] commandArgs) throws BadCommandException {
        if (commandArgs.length < 2) {
            throw new BadCommandException("Not enough arguments for a \"go\" command.");
        }

        if (commandArgs.length > 2) {
            throw new BadCommandException("Too many arguments for a \"go\" command");
        }

        if (!commandArgs[0].equals("go")) {
            throw new BadCommandException("Invalid command: " + commandArgs[0]);
        }

        try {
            int id = Integer.parseInt(commandArgs[1]);

            if (id < 0 || id > 4) {
                throw new BadCommandException("Invalid attacker id: " + id + ".");
            }
        }
        catch (NumberFormatException ex) {
            throw new BadCommandException("Not a number.");
        }

        return true;
    }

    // Build: build character_id object position_x position_y
    public static boolean checkBuildStructure(String[] commandArgs) throws BadCommandException {
        if (commandArgs.length < 5) {
            throw new BadCommandException("Not enough arguments for a \"build\" command.");
        }

        if (commandArgs.length > 5) {
            throw new BadCommandException("Too many arguments for a \"build\" command");
        }

        StringBuilder messages = new StringBuilder();

        String cmd = commandArgs[0];
        String charIdStr = commandArgs[1];
        String objectName = commandArgs[2];
        String positionX = commandArgs[3];
        String positionY = commandArgs[4];

        if (!cmd.equals("build")) {
            throw new BadCommandException("Invalid command: " + commandArgs[0]);
        }

        try {
            int charId = Integer.parseInt(charIdStr);

            if (charId < 0 || charId > 4) {
                messages.append("\nAt 2nd argument: Invalid character id (id: ").append(charId).append(").");
            }
        }
        catch (NumberFormatException ex) {
            messages.append("\nAt 2nd argument: Expected an integer.");
        }

        if (!Referee.VALID_OBJECT_NAMES.contains(objectName)) {
            messages.append("\nAt 3rd argument: Invalid object name. (Name: ").append(objectName).append(").");
        }

        try {
            int posX = Integer.parseInt(positionX);

            if (posX < 0 || posX > Constants.MAP_WIDTH - 1) {
                messages.append("\nAt 4th argument: Invalid x coordinate: ").append(posX).append(".");
            }
        }
        catch (NumberFormatException ex) {
            messages.append("\nAt 4th argument: Expected an integer.");
        }

        try {
            int posY = Integer.parseInt(positionY);

            if (posY < 0 || posY > Constants.MAP_HEIGHT - 1) {
                messages.append("\nAt 5th argument: Invalid y coordinate: ").append(posY).append(".");
            }
        }
        catch (NumberFormatException ex) {
            messages.append("\nAt 5th argument: Expected an integer.");
        }

        if (messages.length() != 0) {
            throw new BadCommandException(messages.toString());
        }

        return true;
    }

    // attack character_id direction
    public static boolean checkAttackStructure(String[] commandArgs) throws BadCommandException {
        if (commandArgs.length < 3) {
            throw new BadCommandException("Not enough arguments for an \"attack\" command.");
        }

        if (commandArgs.length > 3) {
            throw new BadCommandException("Too many arguments for an \"attack\" command");
        }

        StringBuilder messages = new StringBuilder();

        String cmd = commandArgs[0];
        String attackerIdStr = commandArgs[1];
        String direction = commandArgs[2];

        if (!cmd.equals("attack")) {
            throw new BadCommandException("Invalid command: " + cmd);
        }

        try {
            int attackerId = Integer.parseInt(attackerIdStr);

            if (attackerId < 0 || attackerId > 4) {
                messages.append("\nAt 2nd argument: Invalid attacker id. (id: ").append(attackerId).append(").");
            }
        }
        catch (NumberFormatException ex) {
            messages.append("\nAt 2nd argument: Expected a number.");
        }

        if (!Referee.VALID_DIRECTIONS.contains(direction)) {
            messages.append("\nAt 3rd argument: Invalid direction. (Direction: ").append(direction).append(").");
        }

        if (messages.length() > 0) {
            throw new BadCommandException(messages.toString());
        }

        return true;
    }

    public static GoCommand getGoCommand(Player player, Board board, String[] commandArgs) throws BadCommandException {
        if (!checkGoStructure(commandArgs)) {
            return null;
        }

        int charId = Integer.parseInt(commandArgs[1]);
        Attacker attacker = null;

        for (Attacker _attacker: board.getAllAttackersOf(player)) {
            if (_attacker.getId() == charId) {
                attacker = _attacker;
                break;
            }
        }

        return new GoCommand(player, attacker);
    }

    public static BuildCommand getBuildCommand(Player player, Board board, String[] commandArgs) throws BadCommandException {
        if (!checkBuildStructure(commandArgs)) {
            return null;
        }

        int charId = Integer.parseInt(commandArgs[1]);
        String objectName = commandArgs[2];
        int positionX = Integer.parseInt(commandArgs[3]);
        int positionY = Integer.parseInt(commandArgs[4]);

        Attacker attacker = null;

        for (Attacker _attacker: board.getAllAttackersOf(player)) {
            if (_attacker.getId() == charId) {
                attacker = _attacker;
                break;
            }
        }

        return new BuildCommand(player, attacker, objectName, positionX, positionY);
    }

    public static AttackCommand getAttackCommand(Player player, Board board, String[] commandArgs) throws BadCommandException {
        if (!checkAttackStructure(commandArgs)) {
            return null;
        }

        int charId = Integer.parseInt(commandArgs[1]);
        String direction = commandArgs[2];

        Attacker attacker = null;

        for (Attacker _attacker: board.getAllAttackersOf(player)) {
            if (_attacker.getId() == charId) {
                attacker = _attacker;
                break;
            }
        }

        return new AttackCommand(player, attacker, direction);
    }
}
