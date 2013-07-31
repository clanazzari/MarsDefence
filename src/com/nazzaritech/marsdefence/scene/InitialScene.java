package com.nazzaritech.marsdefence.scene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import com.nazzaritech.core.sprite.SceneBackground;
import com.nazzaritech.marsdefence.util.AssetsUtil;
import com.nazzaritech.marsdefence.util.ScreemUtil;

/**
 *  - Initial Scene
 *  - Primeira cena: Mostra o logo da compania e, depois de 3 segundos,
 *  vai para menu inicial
 *  
 *  DESENVOLVIMENTO: OK
 */
public class InitialScene extends CCLayer {

	private SceneBackground background;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		InitialScene layer = new InitialScene();
		scene.addChild(layer);
		return scene;
	}

	protected InitialScene() {
		super();
		this.setIsTouchEnabled(false);

		// Background
		this.background = new SceneBackground(AssetsUtil.BACKGROUND_INITIAL);
		this.background.setPosition(
				ScreemUtil.widthScreem() / 2f,
				ScreemUtil.heightScreem() / 2f);
		addChild(this.background);

		// Show menu after 3 seconds
		schedule("menuLayer", 3);
	}

	public void menuLayer(float dt) {
		CCDirector.sharedDirector().replaceScene(MenuScene.scene());
	}
}
