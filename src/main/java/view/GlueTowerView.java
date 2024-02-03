package view;

import TowerDefense.Constants;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import TowerDefense.Attacker;
import TowerDefense.Tower;

public class GlueTowerView extends TowerView {


	private SpriteAnimation stuntSpriteAnimation;

public GlueTowerView(Tower tower, Group boardGroup, GraphicEntityModule graphics, TooltipModule tooltips) {
	//		super(tower, boardGroup, graphics, tooltips, "stunTower",  48, 48, 10, 10);
			super(tower, boardGroup, graphics, tooltips, "stb3",  48, 48, 6, 6);
			towerSpriteAnimation.setScale(2);


		SpriteAnimation idleTower = Utils.createTowerSpriteAnimation(graphics,"stunTower",tower.getTile().getX(),tower.getTile().getX(),48,48,10,10);
			idleTower.setScale(2);

		String[] idleSpriteImages = graphics.createSpriteSheetSplitter()
				.setSourceImage("stunTower.png")
				.setHeight(48).setWidth(48).setImageCount(10)
				.setImagesPerRow(10)
				.setOrigCol(0).setOrigRow(0)
				.setName( "stunTower" + "Sprite")
				.split();

			double gg = (double) graphics.getWorld().getHeight() / (Constants.MAP_HEIGHT * 100);

	//		stuntSpriteAnimation = Utils.createEffectSpriteAnimation(graphics, tower, "ground_effect.png", 64,64,8,8);
			stuntSpriteAnimation = Utils.createEffectSpriteAnimation(graphics,tower,"stunEffect.png",320,320,12,12);
			stuntSpriteAnimation
	//				.setScaleX(5)
	//				.setScaleY(12)
					.setScale(1.2)
					.setDuration(600)
					.setAnchor(.5)
					.setAnchorX(.45)
					.setVisible(false);


			commitSprites();
			updateTooltip();



			towerSpriteAnimation.setImages(idleSpriteImages);
			towerSpriteAnimation.reset();
			graphics.commitEntityState(1,towerSpriteAnimation);
}

	@Override
	public void attack(Attacker a) {

		stuntSpriteAnimation
				.setVisible(true)
				.setAlpha(1);

		graphics.commitEntityState(1, stuntSpriteAnimation);

		stuntSpriteAnimation
				.setAlpha(0, Curve.LINEAR)
				.setVisible(false);

	}
}
