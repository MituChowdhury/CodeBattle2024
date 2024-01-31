package com.codingame.game;

import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

import TowerDefense.Attacker;
import TowerDefense.Constants;
import TowerDefense.Tower;
import view.PlayerView;

public class Player extends AbstractMultiplayerPlayer {
	private int money = Constants.PLAYER_MONEY;
	private int lives = Constants.PLAYER_LIVES;
	private int coins = Constants.PLAYER_MONEY;
	private int score = Constants.PLAYER_SCORE;
	private int deathCount = Constants.PLAYER_DEATH_COUNT;
	private int killCount = Constants.PLAYER_DEATH_COUNT;
	private PlayerView view;
	private String message = "";

	private static int[] colors = { 0xff4040, 0x4040ff };

	@Override
	public int getExpectedOutputLines() {
		return 5;
	}

	public void initView(GraphicEntityModule graphics) {
		new PlayerView(this, graphics);
	}

	public int getMoney() {
		return money;
	}

	public void kill(Attacker a) {
		this.coins += a.getBounty();
		this.score++;
		a.getOwner().incrementDeathCount();
	}

	public void incrementDeathCount() {
		deathCount++;
	}

	public void destroy(Tower t) {
		this.coins += t.getBounty();
	}

	public boolean buy(Tower tower) {
		if (coins < tower.getCost())
			return false;
		coins -= tower.getCost();
		tower.setOwner(this);
		return true;
	}

	public void spendCoins(int coins) {
		this.coins -= coins;
	}

	public String getPlayerInput() {
		return coins + " " + lives;
	}

	public String getPlayerMoneyInput() {
		return "" + coins;
	}

	public String getPlayerLivesInput() {
		return "" + lives;
	}

	public String getPlayerScoresInput() {
		return "" + score;
	}

	public String getPlayerDeathCountInput() {
		return "" + deathCount;
	}

	public void loseLife() {
		lives--;
	}

	public void kill() {
		lives = 0;
	}

	public int getScorePoints() {
		if (isDead() || !isActive())
			return 0;
		return 100 * lives + money;
	}

	public boolean isDead() {
		return lives <= 0;
	}

	public int getLives() {
		return lives;
	}

	public int getColor() {
		return colors[getIndex()];
	}

	public void setView(PlayerView view) {
		this.view = view;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void updateView() {
		view.updateView();
	}

	public int getCoins() {
		return coins;
	}

	public int getKillCount() {
		return deathCount;  // CAUTION: function name doesnt match with variable name beware in callers
	}

	public int getScores() {
		return score;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public void setScores(int scores) {
		this.score = scores;
	}
}
