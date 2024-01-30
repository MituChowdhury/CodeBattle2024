package com.codingame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import TowerDefense.*;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;


import org.apache.commons.lang3.ObjectUtils;

import command.AttackCommand;
import command.BuildCommand;
import command.GoCommand;
import exception.BadCommandException;

import view.BoardView;

public class Referee extends AbstractReferee {
	public static final int FRAME_DURATION = 500; // THIS THING IS NEVER USED SO CHANGING IT HAS NO EFFECT
	public static Random random;

	@Inject
	private MultiplayerGameManager<Player> gameManager;
	@Inject
	private GraphicEntityModule graphicEntityModule;
	@Inject
	private TooltipModule tooltipModule;
	@Inject
	private EndScreenModule endScreenModule;

	private Board board;
	private ArrayList<Integer> playerOneXs;
	private ArrayList<Integer> playerTwoXs;
	public static final ArrayList<String> VALID_OBJECT_NAMES = new ArrayList<>();
	public static final ArrayList<String> VALID_DIRECTIONS = new ArrayList<>();

	@Override
	public void init() {
		Locale.setDefault(new Locale("en", "US"));

		random = new Random(gameManager.getSeed());
		Tile[][] grid = MapGenerator.generateMap(random);
		gameManager.setMaxTurns(Constants.TURN_COUNT);
		board = new Board(grid, gameManager.getPlayers(), random);

		BoardView view = new BoardView(board, graphicEntityModule, tooltipModule);
		for (Player player : gameManager.getPlayers()) {
			player.initView(graphicEntityModule);
		}

		playerOneXs = new ArrayList<>();
		playerTwoXs = new ArrayList<>();

		VALID_OBJECT_NAMES.add("GUN_TOWER");
		VALID_OBJECT_NAMES.add("HEAL_TOWER");
		VALID_OBJECT_NAMES.add("STUN_TOWER");
		VALID_OBJECT_NAMES.add("SPRING_NORTH");
		VALID_OBJECT_NAMES.add("SPRING_SOUTH");
		VALID_OBJECT_NAMES.add("SPRING_EAST");
		VALID_OBJECT_NAMES.add("SPRING_WEST");
		VALID_OBJECT_NAMES.add("BOMB");
		VALID_OBJECT_NAMES.add("WALL");

		VALID_DIRECTIONS.add("NORTH");
		VALID_DIRECTIONS.add("SOUTH");
		VALID_DIRECTIONS.add("EAST");
		VALID_DIRECTIONS.add("WEST");
	}

	@Override
	public void gameTurn(int turn) {
		try {
			if (turn == 4) {
//				board.cacheBuild(gameManager.getActivePlayers().get(1), 15, 7, "GUNTOWER");
				board.cacheBuild(gameManager.getActivePlayers().get(0), 10, 7, "GUNTOWER");
			}

			if (turn == 12) {
				board.cacheBuild(gameManager.getActivePlayers().get(1), 10, 8, "FIREBOMB");
			}
		} catch (InvalidActionException e) {
			System.out.println("asdasd #################################################################################################################################");
			System.out.println(e.getMessage());
			throw new NullPointerException();
		}

		for (Player player : gameManager.getActivePlayers()) {
			for (String line : board.getPlayerInput(player, turn == 1)) {
				player.sendInputLine(line);
				System.out.println(turn + " - mdmab: " + line);
			}

			player.execute();
		}

		for (Player player : gameManager.getActivePlayers()) {
			try {
//				String actions = player.getOutputs().get(0);
				List<String> actions = player.getOutputs();
				System.out.println("*************** No timeout ****************");

				// For debugging purpose...
				// During the 1st turn, the player have to output the y coordinates for their attackers...
				if (turn == 1) {
					String coords = actions.get(0);
					String[] xOuts = coords.split(" ");

					if (player.getIndex() == 0) {
						for (String x : xOuts) {
							playerOneXs.add(Integer.parseInt(x));
							System.err.println("Pos: " + x);
						}

						for (int i = 0; i < playerOneXs.size(); ++i) {
							System.out.printf("(%d, 0)\n", playerOneXs.get(i));
						}
					} else {
						for (String x : xOuts) {
							playerTwoXs.add(Integer.parseInt(x));
							System.err.println("Pos: " + x);
						}

						for (int i = 0; i < playerTwoXs.size(); ++i) {
							System.out.printf("(%d, 16)\n", playerTwoXs.get(i));
						}
					}

//					for (int pos: playerOneXs) {
					Player owner = board.getPlayer(player.getIndex());
					Player opponent = board.getPlayer(player.getIndex() ^ 1);
					board.createAttackerAtPositions(owner, opponent, player.getIndex() == 0 ? playerOneXs : playerTwoXs);
//					}
				} else {
//					actions.forEach(actionList -> {
//						for (String action: actionList.split(";")) {
//							System.out.println(action);
//						}
//					});
//					System.out.println("Size of list => " + actions.size());
//					actions.forEach(System.out::println);

					// Parse the output lines from the players and do actions...
					actions.forEach(action -> {
						try {
							parseCommand(player, action.split(" "));
						} catch (BadCommandException ex) {
							System.err.println("\t[Exception] " + ex.getMessage());
						}
					});
				}
				// ..............................

				System.out.println("*************** No timeout ****************");

			} catch (TimeoutException e) {
				e.printStackTrace();
				player.kill();
				player.deactivate(String.format("$%d timeout!", player.getIndex()));
				System.out.println("****** On timeout *******");
				System.out.println(String.format("$%d timeout!", player.getIndex()));
				System.out.println("" + player.getIndex() + " killed.");
				System.out.println("****** On timeout *******");
			}
		}

		while (true) {
			try {
				if (!board.executeBuilds())
					break;
			} catch (InvalidActionException ex) {
				if (ex.isGameBreaking()) {
					ex.getPlayer().kill();
					ex.getPlayer().deactivate(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
				} else {
					gameManager.addToGameSummary(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
				}
			}
		}

		board.moveAttackers(turn);
		board.fireTowers();
//		board.spawnAttackers(turn);


		board.updateView();
		for (Player player : gameManager.getPlayers()) {
			player.setScore(player.getScorePoints());
			if (player.isDead() && player.isActive())
				player.deactivate(player.getNicknameToken() + ": no lives left");
		}
//		if( turn == 20 ) {
//			board.test();
//		}
		if (turn == Constants.TURN_COUNT) {
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.endGame();
		}
	}

	public void parseCommand(Player commander, String[] commandArgs) throws BadCommandException {
		if (commandArgs.length == 1 && commandArgs[0].equals("")) {
			// Gotta throw an exception for bad input....
			System.err.println("[X] Wrong: (Empty)");
			throw new BadCommandException("No command has been sent by the player");
		}

		System.err.println("[-] " + String.join(" ", commandArgs));

		if (commandArgs[0].equals("go")) {
			// Implementation for "go" command...
			GoCommand cmd = Util.getGoCommand(commander, this.board, commandArgs);
			Attacker attacker = cmd.getAttacker();
			System.out.println(cmd.toString());
		} else if (commandArgs[0].equals("build")) {
			// Implementation for "build" command...
			BuildCommand cmd = Util.getBuildCommand(commander, this.board, commandArgs);
			Attacker attacker = cmd.getAttacker();
			System.out.println(cmd.toString());
		} else if (commandArgs[0].equals("attack") && Util.checkAttackStructure(commandArgs)) {
			// Implementation for "attack" command...
			AttackCommand cmd = Util.getAttackCommand(commander, this.board, commandArgs);
			Attacker attacker = cmd.getAttacker();
			System.out.println(cmd.toString());
		} else {
			// Exceptions for invalid commands...
			throw new BadCommandException("Invalid command sent by the player.");
		}
	}

	public void eliminatePlayer(Player player) {
		player.kill();
	}

	@Override
	public void onEnd() {
		int[] scores = gameManager.getPlayers().stream().mapToInt(p -> p.getScore()).toArray();
		String[] texts = new String[2];
		for (int i = 0; i < scores.length; i++) {
			texts[i] = gameManager.getPlayers().get(i).getLives() + " lives, " + gameManager.getPlayers().get(i).getMoney() + " gold";
		}
		endScreenModule.setScores(scores, texts);
		//String endSprite = "tie";
		//if (scores[0] > scores[1]) endSprite = "win0";
		//if (scores[0] < scores[1]) endSprite = "win1";
		//endScreenModule.setTitleRankingsSprite(endSprite + ".png");
	}
}
//}

// ----------------------------------------------------------------------------- //

//
//				for (String action : actions.split(";")) {
//					try {
//						String[] parts = action.trim().split(" ");
//						if (parts.length == 0)
//							continue;
//						parts[0] = parts[0].toUpperCase();
//						if (parts[0].equals("PASS"))
//							continue;
//						if (parts[0].equals("BUILD")) {
//							if (parts.length != 4)
//								throw new InvalidActionException("wrong amount of arguments for BUILD", true, player);
//							int x = Integer.parseInt(parts[1]);
//							int y = Integer.parseInt(parts[2]);
//							String type = parts[3];
//							//board.cacheBuild(player, x, y, type);
//						} else if (parts[0].equals("UPGRADE")) {
//							if (parts.length != 3)
//								throw new InvalidActionException("wrong amount of arguments for UPGRADE", true, player);
//							int id = Integer.parseInt(parts[1]);
//							String type = parts[2];
//							//board.upgrade(player, id, type); // upgrade before build => can't build and upgrade in the same turn
//						} else if (parts[0].equals("MSG")) {
//							player.setMessage(action.substring(4));
//						} else {
//							throw new InvalidActionException("unknown command: " + action, true, player);
//						}
//					} catch (InvalidActionException ex) {
//						if (ex.isGameBreaking()) {
//							ex.getPlayer().deactivate(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//						} else {
//							gameManager.addToGameSummary(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//						}
//					} catch (NumberFormatException ex) {
//						player.deactivate(player.getNicknameToken() + " provided a malformed output");
//					}
//
//				}
//			} catch (TimeoutException e) {
//				e.printStackTrace();
//				player.kill();
//				player.deactivate(String.format("$%d timeout!", player.getIndex()));
//				System.out.println("****** On timeout *******");
//				System.out.println(String.format("$%d timeout!", player.getIndex()));
//				System.out.println("" + player.getIndex() + " killed.");
//				System.out.println("****** On timeout *******");
//			}
//		}

//		while (true) {
//			try {
//				if (!board.executeBuilds())
//					break;
//			} catch (InvalidActionException ex) {
//				if (ex.isGameBreaking()) {
//					ex.getPlayer().kill();
//					ex.getPlayer().deactivate(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//				} else {
//					gameManager.addToGameSummary(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//				}
//			}
//		}


//		board.moveAttackers(turn);
//		board.updateView();
//
//		board.fireTowers();
//		board.spawnAttackers(turn);
//
//
//
//		for (Player player : gameManager.getPlayers()) {
//			player.setScore(player.getScorePoints());
//			if (player.isDead() && player.isActive())
//				player.deactivate(player.getNicknameToken() + ": no lives left");
//		}
//		if( turn == 100 ) {
//			board.test();
//		}
//		if (turn == Constants.TURN_COUNT) {
//			gameManager.getActivePlayers().get(0).deactivate();
//			gameManager.getActivePlayers().get(0).deactivate();
//			gameManager.endGame();
//		}
//	}
//	@Override
//	public void onEnd() {
//		int[] scores = gameManager.getPlayers().stream().mapToInt(p -> p.getScore()).toArray();
//		String[] texts = new String[2];
//		for (int i = 0; i < scores.length; i++) {
//			texts[i] = gameManager.getPlayers().get(i).getLives() + " lives, " + gameManager.getPlayers().get(i).getMoney() + " gold";
//		}
//		endScreenModule.setScores(scores, texts);
//		//String endSprite = "tie";
//		//if (scores[0] > scores[1]) endSprite = "win0";
//		//if (scores[0] < scores[1]) endSprite = "win1";
//		//endScreenModule.setTitleRankingsSprite(endSprite + ".png");
//	}
//				}
