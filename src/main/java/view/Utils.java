package view;

import java.util.HashMap;

import TowerDefense.Constants;
import TowerDefense.Tower;
import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;

public class Utils {
	private static HashMap<String, String[]> spriteSheets = new HashMap<>();

	public static String[] loadSheet(GraphicEntityModule graphics, String image, int width, int height, int imageCount, int rowCount) {
		if (spriteSheets.containsKey(image))
			return spriteSheets.get(image);

		String name = String.valueOf((char) ('a' + spriteSheets.size()));
		String[] sprites = graphics.createSpriteSheetSplitter().setSourceImage(image).setName(name).setWidth(width).setHeight(height).setImageCount(imageCount).setImagesPerRow(rowCount).setOrigCol(0).setOrigRow(0).split();

		spriteSheets.put(image, sprites);
		return sprites;
	}

	public static Sprite createBoardSprite(GraphicEntityModule graphics, String image, double x, double y) {
		return graphics.createSprite().setImage(image).setX((int)(BoardView.CELL_SIZE * x)).setY((int)(BoardView.CELL_SIZE * y));
	}

	public static SpriteAnimation createAnimation(GraphicEntityModule graphics, String[] images) {
		return graphics.createSpriteAnimation().setImages(images).setDuration(Referee.FRAME_DURATION).setLoop(true).play();
	}

	public static Sprite createAttackerSprite(GraphicEntityModule graphics, String image, double x, double y) {
		return graphics.createSprite().setImage(image).setX((int) (BoardView.CELL_SIZE * x)).setY((int) (BoardView.CELL_SIZE * y));
	}

	public static Sprite createTowerSprite(GraphicEntityModule graphics, String image, int x, int y) {
		return graphics.createSprite().setImage(image).setX(BoardView.CELL_SIZE * x).setY(BoardView.CELL_SIZE * y);
	}

	public static SpriteAnimation createTowerSpriteAnimation(GraphicEntityModule graphics,  String image, int x, int y, int w, int h, int img_c, int img_pr) {

		String[] spriteImages = graphics.createSpriteSheetSplitter()
				.setSourceImage(image)
				.setHeight(h).setWidth(w).setImageCount(img_c)
				.setImagesPerRow(img_pr)
				.setOrigCol(0).setOrigRow(0)
				.setName(image + "Sprite")
				.split();


		return graphics.createSpriteAnimation()
				.setImages(spriteImages)
				.setX(BoardView.CELL_SIZE * x).setY(BoardView.CELL_SIZE * y)  // THIS SHOULDN'T HAVE -1 but thats how it works
				.setDuration(1000)
//				.setScale(1.5)
//				.setAnchor(.5)
				.setZIndex(-1)
				.setLoop(true).setPlaying(true);

	}

	public static SpriteAnimation createEffectSpriteAnimation(GraphicEntityModule graphics, Tower tower, String image, int w, int h, int img_c, int img_pr) {


		double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);


		String[] spriteImages = graphics.createSpriteSheetSplitter()
				.setSourceImage(image)
				.setHeight(h).setWidth(w).setImageCount(img_c)
				.setImagesPerRow(img_pr)
				.setOrigCol(0).setOrigRow(0)
				.setName(image + "Sprite")
				.split();


		return graphics.createSpriteAnimation()
				.setImages(spriteImages)
				.setAlpha(0)
//				.setX(BoardView.CELL_SIZE * tower.getTile().getX()).setY(BoardView.CELL_SIZE * tower.getTile().getY())
				//TODO: make concrete calculation
				.setX((int) (BoardView.CELL_SIZE * (tower.getTile().getX()+ 1 + 0.5) * gg) + Constants.BOARD_DASH_WIDTH)
				.setY((int) (BoardView.CELL_SIZE * (tower.getTile().getY() + 0.5) * gg))
				.setDuration(500)
//				.setScale(1.5)
//				.setAnchor(.5)
//				.setZIndex(-1)
				.setLoop(true).setPlaying(true);

	}



}
