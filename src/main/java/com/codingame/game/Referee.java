package com.codingame.game;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import TowerDefense.*;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.common.base.StandardSystemProperty;
import com.google.inject.Inject;


import command.Command;

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

	public static final ArrayList<String> VALID_OBJECT_NAMES = new ArrayList<>();
	public static final ArrayList<String> VALID_DIRECTIONS = new ArrayList<>();
	private ArrayList<String>[] playerErrorMessages = new ArrayList[2];
	// The following variables are for ranking and displaying scores at the end of the game...
	int[] scores = {0, 0};
	String[] texts = new String[2];

	@Override
	public void init() {

		Locale.setDefault(new Locale("en", "US"));

		random = new Random(10);
		Tile[][] grid = MapGenerator.generateMap(random);
		gameManager.setMaxTurns(Constants.TURN_COUNT);
		board = new Board(grid, gameManager.getPlayers(), random);

		BoardView view = new BoardView(board, graphicEntityModule, tooltipModule);
		for (Player player : gameManager.getPlayers()) {
			player.initView(graphicEntityModule);
		}

		VALID_OBJECT_NAMES.add("GUN_TOWER");
//		VALID_OBJECT_NAMES.add("HEAL_TOWER");
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

		playerErrorMessages[0] = new ArrayList<>();
		playerErrorMessages[1] = new ArrayList<>();
	}

	@Override
	public void gameTurn(int turn) {
//		try {
//
//			if(turn==10){
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 18, 10, "WALL");
//			}
//			if (turn == 4) {
//				board.cacheBuild(gameManager.getActivePlayers().get(1), 3, 1, "STUN_TOWER");
//				board.cacheBuild(gameManager.getActivePlayers().get(0), 0, 11, "GUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 0, 11, "STUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 11, 11, "SPRING_EAST");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 12, 11, "SPRING_NORTH");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 8, 7, "GUN_TOWER");
//
//
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 2, 8, "WALL");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 2, 9, "WALL");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 2, 10, "WALL");
////
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 1, 8, "WALL");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 1, 9, "WALL");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 1, 10, "WALL");
////
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 5, 6, "WALL");
//
//
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 12, 8, "SPRING_NORTH");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 7, 2, "GUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 8, 7, "GUN_TOWER");
////
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 8, 2, "STUN_TOWER");
//////				board.cacheBuild(gameManager.getActivePlayers().get(0), 7, 8, "SPRINGTRAP_U");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 8, 8, "GUN_TOWER");
//
//////				board.cacheBuild(gameManager.getActivePlayers().get(0), 6, 9, "SPRINGTRAP_U");
////				board.cacheBuild(gameManager.getActivePlayers().get(0), 7, 9, "STUN_TOWER");
////
////				// Team blue -_- ....
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 10, 7, "STUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 15, 9, "STUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 10, 5, "GUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 10, 7, "GUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 10, 7, "GUN_TOWER");
////				board.cacheBuild(gameManager.getActivePlayers().get(1), 9, 11, "BOMB");
//			}
//		} catch ( InvalidActionException e ) {
//////			System.out.println(e.getMessage());
////			this.addErrorMessage(0, "Internal Error");
////			this.addErrorMessage(1, "Internal Error");
//			System.err.println(e.getMessage());
////			gameManager.endGame();
//
//
//		}


		board.spawnAttackers(turn); //spawn those that were killed in previous turn
		board.updateTowers();
		board.fireTowers();

		for (Player player : gameManager.getActivePlayers()) {
			for (String line : board.getPlayerInput(player, turn == 1)) {
				player.sendInputLine(line);
			}

			player.execute();
		}



		for (Player player : gameManager.getActivePlayers()) {
			try {

				List<String> actions = player.getOutputs();
//				System.out.println("*************** No timeout ****************");

				// For debugging purpose...
				// During the 1st turn, the player have to output the y coordinates for their attackers...
				if (turn == 1) {

					try {
						for (int i = 0; i < Constants.CHARACTER_COUNT; i++) {
							Player opponent = board.getPlayer(player.getIndex() ^ 1);
							int yCord = Integer.parseInt(actions.get(i));

							if (yCord < 0 || yCord >= Constants.MAP_HEIGHT) {
								System.err.printf("Expected a valid integer for y coordinate between 0 and %d\n", Constants.MAP_HEIGHT);
								addErrorMessage(player.getIndex(), "Y coordinate is not valid.");
								gameManager.addToGameSummary("Expected a valid integer for y coordinate between 0 and "+ Constants.MAP_HEIGHT+"\n");
								gameManager.endGame();
							}

							board.createAttackerAtPositions(player, opponent, yCord);
						}
					}
					catch (Exception e){
//						System.err.println("############# Invalid initial output");
						System.err.println("Expected integer for y coordinate, found something else.");
						addErrorMessage(player.getIndex(), "Y coordinate is not an integer.");
						gameManager.addToGameSummary("Expected integer for y coordinate, found something else.");
						gameManager.endGame();
					}


				} else {
					PathFinder.init(player.getIndex()^1); //initialize path toward the enemy base.


					TreeSet<Integer> usedAttackers = new TreeSet<>();
					AtomicInteger i = new AtomicInteger();

					actions.forEach(action -> {
						try {
							i.incrementAndGet();
							executeCommand(parseCommand(player, usedAttackers, action.split("\\s+")));
						} catch (BadCommandException ex) {
//							System.err.println("\t[Exception] " + ex.getMessage());
							System.out.println("Invalid input detected by player no. " + player.getIndex());
							this.addErrorMessage(player.getIndex(), ex.getMessage());
							System.err.println("*** Error by player "
									+ player.getIndex()
									+ " at command no. "
									+ i.get()
							);
							System.err.println(ex.getMessage());
							System.err.println();
							gameManager.addToGameSummary(ex.getMessage());

							gameManager.endGame();
						}
					});
				}

			} catch (TimeoutException e) {
				e.printStackTrace();
				player.kill();
				player.deactivate(String.format("$%d timeout!", player.getIndex()));
				System.out.println("****** On timeout *******");
				System.out.println(String.format("$%d timeout!", player.getIndex()));
				System.out.println("" + player.getIndex() + " killed.");
				System.out.println("****** On timeout *******");
				gameManager.addToGameSummary("Check Game statement for valid input/output");
				this.addErrorMessage(player.getIndex(),"Timed out");
				gameManager.endGame();
			}
		}

		while (true) {
			try {
				if (!board.executeBuilds())
					break;
			} catch (InvalidActionException ex) {
//				if (ex.isGameBreaking()) {
//					ex.getPlayer().kill();
//					ex.getPlayer().deactivate(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//				} else {
//					gameManager.addToGameSummary(ex.getPlayer().getNicknameToken() + ": " + ex.getMessage());
//				}
				ex.getPlayer().kill();
				ex.getPlayer().deactivate();
				System.err.println(ex.getMessage());
				gameManager.addToGameSummary(ex.getMessage());
				addErrorMessage(ex.getPlayer().getIndex(),ex.getMessage());
				gameManager.endGame();
				return;
			}
		}



		board.checkDeadAttacker(); //add those that are killed in this turn
		board.updateView();

//
//		for (Player player : gameManager.getPlayers()) {
//			player.setScore(player.getScorePoints());
//			if (player.isDead() && player.isActive())
//				player.deactivate(player.getNicknameToken() + ": no lives left");
//		}

		if (turn == Constants.TURN_COUNT) {
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.getActivePlayers().get(0).deactivate();
			gameManager.endGame();
		}
	}

	private void executeCommand( Command cmd ) throws BadCommandException {
		try {
			if(cmd.getAttacker()==null)
			{
				System.out.println("############### Command ignored. Player dead");
				return;
			}

			if( cmd.getClass() == BuildCommand.class ) {
				BuildCommand c = (BuildCommand) cmd;
				board.cacheBuild(c.getPlayer(), c.getPosX(), c.getPosY(), c.getObjectName());
				cmd.getAttacker().build(c.getPosX(),c.getPosY());

			} else if( cmd.getClass() == GoCommand.class ) {
				cmd.getAttacker().move();
			} else if( cmd.getClass() == AttackCommand.class ) {
				AttackCommand c = (AttackCommand) cmd;
				Attacker a = c.getAttacker();
				int dir = c.getDirection();
				int newX = c.getAttacker().getCurrentTile().getX();
				int newY = c.getAttacker().getCurrentTile().getY();
				switch( dir ) {
					case 1:
						newY -= 1;
						break;
					case 2:
						newX += 1;
						break;
					case 3:
						newY += 1;
						break;
					case 4:
						newX -= 1;
						break;
				}
				if( newX >= board.getWidth() || newX < 0 || newY >= board.getHeight() || newY < 0 ) {
					throw new BadCommandException("Attack tile is out of map");
				}
				Tile attackTile = board.getGrid()[newX][newY];
				a.attack(attackTile);
			}
		} catch (InvalidActionException e) {
//			System.out.println("ERROR: #################################################################################################################################");
//			System.out.println(e.getMessage());
			this.addErrorMessage(e.getPlayer().getIndex(), "Invalid command detected.");
			System.err.println("Invalid command detected.");
			gameManager.addToGameSummary(e.getMessage());
			gameManager.endGame();
		}
	}

	public Command parseCommand(Player commander, TreeSet<Integer> usedAttackers, String[] commandArgs) throws BadCommandException {
		if (commandArgs.length == 1 && commandArgs[0].equals("")) {
			// Gotta throw an exception for bad input....
//			System.err.println("[X] Wrong: (Empty)");
			throw new BadCommandException("No command has been sent by the player");
		}

//		System.err.println("[-] " + String.join(" ", commandArgs));

		Command cmd;

		if (commandArgs[0].equals("go")) {
			// Implementation for "go" command...
			cmd = Util.getGoCommand(commander, this.board, commandArgs);
			Attacker attacker = cmd.getAttacker();

			if(attacker!=null) {
				throwIfAttackerUsed(usedAttackers, attacker.getId());
				usedAttackers.add(attacker.getId());
			}
		} else if (commandArgs[0].equals("build")) {
			// Implementation for "build" command...
			cmd = Util.getBuildCommand(commander, this.board, commandArgs);

            Attacker attacker = cmd.getAttacker();

			if(attacker!=null) {
				throwIfAttackerUsed(usedAttackers, attacker.getId());
				usedAttackers.add(attacker.getId());
			}

		} else if (commandArgs[0].equals("attack") && Util.checkAttackStructure(commandArgs)) {
			// Implementation for "attack" command...
			cmd = Util.getAttackCommand(commander, this.board, commandArgs);
			Attacker attacker = cmd.getAttacker();

			if(attacker!=null) {
				throwIfAttackerUsed(usedAttackers, attacker.getId());
				usedAttackers.add(attacker.getId());
			}

		} else {
			// Exceptions for invalid commands...
			throw new BadCommandException("Invalid command sent by the player.");
		}

		return cmd;
	}

	public void throwIfAttackerUsed(TreeSet<Integer> usedAttackers, int id) throws BadCommandException {
		if (usedAttackers.contains(id)) {
			throw new BadCommandException("Attacker has already been given a command.");
		}
	}

	public void eliminatePlayer(Player player) {
		player.kill();
	}

	@Override
	public void onEnd() {

			if (scores[0] < 0 || scores[1] < 0) {
				onErrorEnd();
			}
			else {
				onSuccessfulEnd();
			}

			for (Player player : gameManager.getPlayers()) {
				player.setScore(scores[player.getIndex()]);
			}

			endScreenModule.setScores(scores, texts);
	}

	private void onSuccessfulEnd() {
		setScores();  // Sets the values of scores in the array of "scores" in this class.

		for (int i = 0; i < this.scores.length; i++) {
//			texts[i] = gameManager.getPlayers().get(i).getKillCount() + " deaths, " + gameManager.getPlayers().get(i).getCoins() + " gold";
			Player player = gameManager.getPlayer(i);

			texts[i] = "Scores : " + player.getScores() +
					"      Coins : " + player.getCoins() +
					"      Death count : " + player.getKillCount();
		}
	}

	private void setScores() {

		Player playerOne = gameManager.getPlayer(0);
		Player playerTwo = gameManager.getPlayer(1);

		if (playerOne.getScores() == playerTwo.getScores()) {
			if (playerOne.getCoins() == playerTwo.getCoins()) {
				if (playerOne.getKillCount() < playerTwo.getKillCount()) {
					scores[0] = 1;
				}
				else if (playerOne.getKillCount() > playerTwo.getKillCount()) {
					scores[1] = 1;
				}
			}
			else {
				int i = playerOne.getCoins() > playerTwo.getCoins() ? 0: 1;
				scores[i] = 1;
			}
		}
		else {
			int i = playerOne.getScores() > playerTwo.getScores() ? 0: 1;
			scores[i] = 1;
		}
	}

	private void addErrorMessage(int playerId, String message) {
		this.playerErrorMessages[playerId].add(message);
		this.scores[playerId] = -1;
	}

	private void onErrorEnd() {
		for (int i = 0; i < scores.length; ++i) {
			if (scores[i] < 0) {
				StringBuilder sb = new StringBuilder();


				playerErrorMessages[i].forEach(err -> {
					sb.append(err);
				});

				texts[i] = playerErrorMessages[i].get(playerErrorMessages[i].size()-1);
			}
			else {
				Player player = gameManager.getPlayer(i);

				texts[i] = "Scores : " + player.getScores() +
						"      Coins : " + player.getCoins() +
						"      Death count : " + player.getKillCount();
			}
		}
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
