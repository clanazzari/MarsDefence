package com.nazzaritech.marsdefence.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;

import com.nazzaritech.core.delegate.ButtonDelegate;
import com.nazzaritech.core.layer.object.Button;
import com.nazzaritech.marsdefence.scene.StageOne;
import com.nazzaritech.marsdefence.util.AssetsUtil;
import com.nazzaritech.marsdefence.util.ScreemUtil;

public class MenuButtonsInicial extends CCLayer implements ButtonDelegate {

	private Button btNewGame;
	private Button btExit;

	public MenuButtonsInicial() {

		// Configuracao
		this.setIsTouchEnabled(true);

		// Instancia botoes
		this.btNewGame = new Button(AssetsUtil.BUTTON_NEWGAME);
		this.btExit = new Button(AssetsUtil.BUTTON_EXIT);

		this.btNewGame.setDelegate(this);
		this.btExit.setDelegate(this);

		// Seta posicao dos botoes
	    this.btNewGame.setPosition(
	    		ScreemUtil.widthScreem() / 2f, 
	    		ScreemUtil.heightScreem() / 2f + 40);
		this.btExit.setPosition(
				ScreemUtil.widthScreem() / 2f, 
				ScreemUtil.heightScreem() / 2f - 100);

	    // Adiciona na tela
	    addChild(this.btNewGame);
	    addChild(this.btExit);
	}

	/**
	 * 
	 */
	public void buttonCliked(Button sender) {
		if (sender.equals(this.btNewGame)) {
			CCDirector.sharedDirector().replaceScene(StageOne.scene());

		} else if (sender.equals(this.btExit)) {
			System.exit(0);

		}
	}

}
