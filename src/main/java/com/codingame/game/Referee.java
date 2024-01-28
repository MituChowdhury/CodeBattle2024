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

import view.BoardView;

public class Referee extends AbstractReferee {
	public static final int FRAME_DURATION = 500;
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
	private int prturn = 0;

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
	}

	@Override
	public void gameTurn(int turn) {
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
						for (String x: xOuts) {
							playerOneXs.add(Integer.parseInt(x));
						}

						for (int i = 0; i < playerOneXs.size(); ++i) {
							System.out.printf("(%d, 0)\n", playerOneXs.get(i));
						}
					}
					else {
						for (String x: xOuts) {
							playerTwoXs.add(Integer.parseInt(x));
						}

						for (int i = 0; i < playerTwoXs.size(); ++i) {
							System.out.printf("(%d, 16)\n", playerTwoXs.get(i));
						}
					}
				}
				else {
//					actions.forEach(actionList -> {
//						for (String action: actionList.split(";")) {
//							System.out.println(action);
//						}
//					});
//					System.out.println("Size of list => " + actions.size());
//					actions.forEach(System.out::println);

					// Parse the output lines from the players and do actions...
					actions.forEach(action -> parseCommand(action.split(" ")));
				}
				// ..............................

				System.out.println("*************** No timeout ****************");

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
//				}
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
		board.spawnAttackers(turn);


		board.updateView();
		for (Player player : gameManager.getPlayers()) {
			player.setScore(player.getScorePoints());
			if (player.isDead() && player.isActive())
				player.deactivate(player.getNicknameToken() + ": no lives left");
		}
		if( turn == 20 ) {
			board.test();
		}
		if (turn == Constants.TURN_COUNT) {
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.endGame();
		}
	}

	public void parseCommand(String[] commandArgs) {
		if (commandArgs.length == 0) {
			// Gotta throw an exception for bad input....
		}

		switch (commandArgs[0]) {
			case "go":
				break;
			case "build":
				break;
			case "attack":
				break;
			default:
				break;
		}
	}

	public void checkCommandGo(String[] commandArgs) {
		int state = 1;

		/*
		* 1 -> go -> 2
		* 1 -> _ -> 3
		* 2 -> (int) -> 0
		* 2 -> _ -> 3
		* 0 -> * -> 3
		* */
		for (String arg: commandArgs) {
			switch (state) {
				case 1:
					state = (arg.equals("go") ? 2: 3);
					break;
				case 2:
					try {
						Integer temp = Integer.parseInt(arg);
						state = 0;
					}
					catch (NumberFormatException ex) {
						state = 3;
					}
					break;
			}
		}
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
