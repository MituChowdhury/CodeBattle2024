package view;

import TowerDefense.*;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;

public class BoardView {
	public static final int CELL_SIZE = 100;
	private Board board;
	public GraphicEntityModule graphics;
	private Group boardGroup;
	private TooltipModule tooltips;
	private Text wave;

	public double gg() {
		return (double) graphics.getWorld().getHeight() / (board.getHeight() * CELL_SIZE);
	}
	private Sprite sponsor;
	private Sprite logo;


	public BoardView(Board board, GraphicEntityModule graphics, TooltipModule tooltips) {
		int width = 1920;
		int height = 1080;

		int boardBackgroundColor = 0xebebeb;
		int dashboardBackgroundColor = 0x1f0342;
//		int dashboardBackgroundColor = 0x3e0684;

		// The dashboard....
		graphics.createRectangle().setFillColor(dashboardBackgroundColor)
				.setWidth(width)
				.setHeight(height)
				.setZIndex(-4);

		// The background of the board...
		graphics.createRectangle().setFillColor(boardBackgroundColor)
				.setWidth(width)
				.setHeight(height)
				.setX(Constants.BOARD_DASH_WIDTH * 4/3)
				.setZIndex(-3);

		this.board = board;
		board.setView(this);
		this.graphics = graphics;
		this.tooltips = tooltips;

//		wave = graphics.createText("").setAnchor(0.5).setFillColor(0x000000).setFontSize(40).setStrokeColor(0x000000).setStrokeThickness(0.0).setX(Constants.BOARD_DASH_WIDTH / 2).setY(graphics
//		.getWorld().getHeight() / 2);

		// The images of the sponsor and the event...
		int yOffset = 100;

		sponsor = graphics.createSprite().setImage("logo_kinetik.png")
				.setAnchorX(0.8)
				.setAnchorY(1)
				.setScale(0.5)
				.setX(Constants.BOARD_DASH_WIDTH / 2)
				.setY((graphics.getWorld().getHeight() / 2) - yOffset + 40);

		logo = graphics.createSprite().setImage("logo_carnival.png")
				.setAnchorX(0.5)
				.setAnchorY(0)
				.setScale(0.8)
				.setX(Constants.BOARD_DASH_WIDTH / 2)
				.setY(graphics.getWorld().getHeight() / 2 - yOffset);
		// ==========================================

		double g = graphics.getWorld().getHeight();

		boardGroup = graphics.createGroup();

		boardGroup.setScale((double) graphics.getWorld().getHeight() / (board.getHeight() * CELL_SIZE));
//		boardGroup.setX(graphics.getWorld().getWidth() - graphics.getWorld().getHeight() * (1 + Constants.MAP_HEIGHT) / Constants.MAP_HEIGHT);
		boardGroup.setX((int) (Constants.BOARD_DASH_WIDTH * 4/3));

		Group gridGroup = graphics.createGroup();
		boardGroup.add(gridGroup);

		Group innerGroup = graphics.createGroup();
		gridGroup.add(innerGroup);

		innerGroup.setZIndex(-1);
		boardGroup.setZIndex(-1);
		gridGroup.setZIndex(-1);

		for (int y = 0; y < board.getHeight(); y++) {
			innerGroup.add(Utils.createBoardSprite(graphics, "canyon.png", -1, y).setAlpha(0.7).setZIndex(-10000));
			innerGroup.add(Utils.createBoardSprite(graphics, "canyon.png", board.getWidth(), y).setAlpha(0.7).setZIndex(-10000));
			for (int x = 0; x < board.getWidth(); x++) {

				String[] idleSpriteImages = graphics.createSpriteSheetSplitter()
						.setSourceImage("base.png")
						.setHeight(100).setWidth(70).setImageCount(6)
						.setImagesPerRow(6)
						.setOrigCol(0).setOrigRow(0)
						.setName("baseSprite")
						.split();



				if (board.getGrid()[x][y].canEnter()) {
					Sprite canyon = Utils.createBoardSprite(graphics, "plateau.png", x, y).setZIndex(-10000);

					if (x == 0 && y == board.getHeight()/2) {

						SpriteAnimation playerBase = Utils.createBoardSpriteAnimation(graphics, "baseSpawn.png",x,y-.5,70,100,16,16);
						playerBase.setScale(1.5).setZIndex(y);
						canyon.setTint(board.getPlayer(0).getColor()).setAlpha(.3);
						graphics.commitEntityState(1,playerBase);
						playerBase.setImages(idleSpriteImages);

						innerGroup.add(playerBase);
					}
					if (x == board.getWidth() - 1 && y == board.getHeight()/2) {
						SpriteAnimation playerBase = Utils.createBoardSpriteAnimation(graphics, "baseSpawn.png",x,y-.5,70,100,16,16);
						playerBase.setScale(1.5).setZIndex(y);
						canyon.setTint(board.getPlayer(1).getColor()).setAlpha(.3);
						graphics.commitEntityState(1,playerBase);
						playerBase.setImages(idleSpriteImages);

						innerGroup.add(playerBase);
					}
					 tooltips.setTooltipText(canyon, "x: " + x + "\ny: " + y);
					 boardGroup.add(canyon);
				}
				if (board.getGrid()[x][y].hasNonDestructibleObject()) {  // if there is obstacle
					Sprite NDobstacle = Utils.createBoardSprite(graphics, "obstackle1.png", x, y).setZIndex(-10000); // for not destructable tiles
					NDobstacle.setScale(.42);

					tooltips.setTooltipText(NDobstacle, "x: " + x + "\ny: " + y);
					innerGroup.add(NDobstacle);
				}
				else if (board.getGrid()[x][y].hasDestructibleObject()) {  // if there is obstacle
					Sprite Dobstacle = Utils.createBoardSprite(graphics, "plateau.png", x, y).setAlpha(0.4).setZIndex(-10000);
					tooltips.setTooltipText(Dobstacle, "x: " + x + "\ny: " + y);
					innerGroup.add(Dobstacle);
				}
			}
		}
	}

	public AttackerView addAttacker(Attacker attacker) {
		return new AttackerView(attacker, boardGroup, graphics, tooltips);
	}

	public void addTower(Tower tower) {
		TowerView view = tower.createView(boardGroup, graphics, tooltips);
	}


	public void updateView() {
		// It's here, because it doesn't break the code...
	}
}
