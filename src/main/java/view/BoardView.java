package view;

import TowerDefense.*;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
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

		graphics.createRectangle().setFillColor(dashboardBackgroundColor)
				.setWidth(width)
				.setHeight(height);

		graphics.createRectangle().setFillColor(boardBackgroundColor)
				.setWidth(width)
				.setHeight(height)
				.setX(Constants.BOARD_DASH_WIDTH * 4/3);

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

		for (int y = 0; y < board.getHeight(); y++) {
			innerGroup.add(Utils.createBoardSprite(graphics, "canyon.png", -1, y).setAlpha(0.7));
			innerGroup.add(Utils.createBoardSprite(graphics, "canyon.png", board.getWidth(), y).setAlpha(0.7));
			for (int x = 0; x < board.getWidth(); x++) {
				 if (board.getGrid()[x][y].canEnter()) {
					Sprite canyon = Utils.createBoardSprite(graphics, "canyon.png", x, y).setZIndex(-1);

					if (x == 0 && y == board.getHeight()/2) {

						Sprite headquarter = Utils.createBoardSprite(graphics, "headquarter.png",x,y-.5);
						headquarter.setTint(board.getPlayer(0).getColor());
						canyon.setTint(board.getPlayer(0).getColor()).setAlpha(.3);
						innerGroup.add(headquarter);
					}
					if (x == board.getWidth() - 1 && y == board.getHeight()/2) {
						Sprite headquarter = Utils.createBoardSprite(graphics, "headquarter.png", x,y-.5);
						headquarter.setTint(board.getPlayer(1).getColor());
						canyon.setTint(board.getPlayer(1).getColor()).setAlpha(.3);
						innerGroup.add(headquarter);
					}
					 tooltips.setTooltipText(canyon, "x: " + x + "\ny: " + y);
					 boardGroup.add(canyon);
				}
				if (board.getGrid()[x][y].hasNonDestructibleObject()) {  // if there is obstacle
					Sprite NDobstacle = Utils.createBoardSprite(graphics, "plateau.png", x, y); // for not destructable tiles
					tooltips.setTooltipText(NDobstacle, "x: " + x + "\ny: " + y);
					innerGroup.add(NDobstacle);
				}
				else if (board.getGrid()[x][y].hasDestructibleObject()) {  // if there is obstacle
					Sprite Dobstacle = Utils.createBoardSprite(graphics, "plateau.png", x, y).setAlpha(0.4);
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
