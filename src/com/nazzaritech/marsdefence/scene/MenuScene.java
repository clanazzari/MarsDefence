package com.nazzaritech.marsdefence.scene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;

import com.nazzaritech.core.sprite.SceneBackground;
import com.nazzaritech.marsdefence.layer.MenuButtonsInicial;
import com.nazzaritech.marsdefence.util.AssetsUtil;
import com.nazzaritech.marsdefence.util.ScreemUtil;

/**
 *  - Menu Scene
 *  - Cena do Menu: Mostra o menu inicial do jogo
 *  
 *  DESENVOLVIMENTO: OK
 */
public class MenuScene extends CCLayer {

	// Variaveis
	private SceneBackground background; // Background

    public static CCScene scene() {
        CCScene scene = CCScene.node();
        MenuScene layer = new MenuScene();
        scene.addChild(layer);
        return scene;
    }

    protected MenuScene() {
        super();

	    // Inicia Background
        this.background = new SceneBackground(AssetsUtil.BACKGROUND_MENU);
        this.background.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f);
	    addChild(this.background);

	    // Insere menu
	    MenuButtonsInicial menuLayer = new MenuButtonsInicial();
	    addChild(menuLayer);

    }

}