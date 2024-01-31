package view;

import TowerDefense.Constants;
import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.*;
//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class PlayerView {
	private Player player;
	private GraphicEntityModule graphics;
	private Group group;
	private Sprite background;
	private Text gold;
	private Text lives;
	private Text pseudo;
	private Text message;

	// Text sections for code battle game...
	private Text coins;
	private Text scores;
	private Text killCount;
	// =========================================
	private Sprite avatar;

	private int currentGold;
	private int currentLives;
	private String currentMessage;
	// =========================================
	private int currentCoins = -1;
	private int currentScores = -1;
	private int currentKillCount = -1;
	// =========================================

	public PlayerView(Player player, GraphicEntityModule graphics) {
		this.player = player;
		player.setView(this);
		this.graphics = graphics;
		createPlayerView();
	}

	public void createPlayerView() {
		int baseWidth = 713;
		int baseHeight = 367;
		int textColor = 0xEDE0C9;

		Sprite frame =
				graphics.createSprite().setZIndex(-10000).setImage("panel.png")
						.setX(0)
						.setY(0)
						.setBaseWidth(Constants.BOARD_DASH_WIDTH)
						.setBaseHeight(Constants.BOARD_DASH_HEIGHT);
//		avatar = graphics.createSprite().setAnchor(0.5).setBaseHeight(0).setBaseWidth(0).setImage(player.getAvatarToken()).setX(132).setY(239).setZIndex(20);

		//background = entityModule.createSprite().setAnchor(0).setImage("HUD_" + color + ".png").setX(238 - 50 - PLAYER_AVATAR_RADIUS / 2).setY(baseY);

		// pseudo -> The name of the player in the dashboard...
//		int nameStartX = 361;
		int nameStartX = 140;
//		int nameStartX = 5;

		pseudo = graphics.createText(player.getNicknameToken())
						.setAnchor(0.5)
						.setFontSize(45)
						.setStrokeColor(4).setStrokeColor(0x000000)
						.setStrokeThickness(1.0)
						.setY(57)
						.setX(nameStartX)
						.setFillColor(player.getColor())
						.setZIndex(-1);
//		int textPos = 490;
//		int textPos = (int) (Constants.BOARD_DASH_WIDTH * 0.68723 * 0.8);
		int textStartX = 120;
		int textYGap = 70;
		int fontsize = 35;

		coins = graphics.createText("")
				.setAnchorY(0.5)
				.setFillColor(textColor).setFontSize(fontsize)
				.setStrokeColor(0x000000)
				.setStrokeThickness(1.0)
				.setX(textStartX)
				.setY(154)
				.setTextAlign(TextBasedEntity.TextAlign.LEFT);

		scores = graphics.createText("")
						.setAnchorY(0.5)
						.setFillColor(textColor).setFontSize(fontsize)
						.setStrokeColor(0x000000)
						.setStrokeThickness(1.0)
						.setX(textStartX)
						.setY(coins.getY() + textYGap)
						.setTextAlign(TextBasedEntity.TextAlign.LEFT);

		killCount = graphics.createText("")
						.setAnchorY(0.5)
						.setFillColor(textColor).setFontSize(fontsize)
						.setStrokeColor(0x000000)
						.setStrokeThickness(0.0)
						.setX(textStartX)
						.setY(scores.getY() + textYGap)
						.setZIndex(-1)
						.setTextAlign(TextBasedEntity.TextAlign.LEFT);

		group = graphics.createGroup().setScale(1).setX(0).setY(player.getIndex() == 0 ? 0 : (1080-375));
//		group.add( avatar, pseudo, gold, lives, message, frame);
		group.add(pseudo, scores, coins, killCount, frame);
	}

	public void updateView() {
		if (player.getCoins() != this.currentCoins) {
			this.currentCoins = player.getCoins();
			this.coins.setText(currentCoins + "");
		}

		if (player.getScores() != this.currentScores) {
			this.currentScores = player.getScores();
			this.scores.setText(currentScores + "");
		}

		if (player.getKillCount() != this.currentKillCount) {
			this.currentKillCount = player.getKillCount();
			this.killCount.setText(this.currentKillCount + "");  // Debuggin code...
		}
	}

//	public void updateView() {
//		if (player.getMoney() != this.currentGold) {
//			this.currentGold = player.getMoney();
//			this.gold.setText(currentGold+"");
//		}
//		if (player.getLives() != this.currentLives) {
//			this.currentLives = player.getLives();
//			this.lives.setText(currentLives+"");
//		}
//		if (!player.getMessage().equals(currentMessage)) {
//			this.currentMessage = player.getMessage();
////			this.message.setText((currentMessage+"                ").substring(0, 15));
//			this.message.setText(currentMessage+"Nah");  // Debuggin code...
//		}
//	}
}
