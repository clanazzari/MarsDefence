package com.nazzaritech.marsdefence.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import com.nazzaritech.core.delegate.ButtonDelegate;
import com.nazzaritech.core.delegate.LabelDelegate;
import com.nazzaritech.core.layer.object.Button;
import com.nazzaritech.core.layer.object.Label;
import com.nazzaritech.marsdefence.util.AssetsUtil;
import com.nazzaritech.marsdefence.util.ScreemUtil;
import com.nazzaritech.marsdefence.vo.ScreenObject;

public class MenuButtonsGame extends CCLayer implements ButtonDelegate, LabelDelegate {

	// Vars
	private Button btRadioShow;
    private ScreenObject goldBar;
    private ScreenObject silverBar;
    private Label waveInfo;
    private Label lifeInfo;
	private CGPoint startPosition;
	private int lifeAtual;
	private int lifeTotal;
	private int waveAtual;
	private int waveTotal;

	public MenuButtonsGame(int lifeInitial, int wavesInitial) {

		// Configuracao
		this.setIsTouchEnabled(true);
		this.startPosition = getPosition();
		this.lifeTotal = this.lifeAtual = lifeInitial;
		this.waveTotal = wavesInitial;
		this.waveAtual = 0;

		// Instancia botoes
		this.btRadioShow = new Button(AssetsUtil.BUTTON_RADIO_SHOW);
		this.goldBar = new ScreenObject(AssetsUtil.SCREENOBJ_GOLDBAR);
		this.silverBar = new ScreenObject(AssetsUtil.SCREENOBJ_SILVERBAR);
		this.waveInfo = new Label("Waves: " + waveAtual + " / " + waveTotal);
		this.lifeInfo = new Label("Lifes: " + lifeAtual + " / " + lifeTotal);

		// Insere delegates
		this.btRadioShow.setDelegate(this);
		this.waveInfo.setDelegate(this);
		this.lifeInfo.setDelegate(this);

		// Seta posicao dos botoes
	    this.btRadioShow.setPosition(
	    		ScreemUtil.widthScreem() / 8.0f, 
	    		ScreemUtil.heightScreem() / 10.0f+50f);
	    this.goldBar.setStartPosition(
	    		CGPoint.ccp(100,ScreemUtil.heightScreem() - 40));
	    this.silverBar.setStartPosition(
	    		CGPoint.ccp(240,ScreemUtil.heightScreem() - 40));
		this.waveInfo.setPosition(
				CGPoint.ccp(480,ScreemUtil.heightScreem() - 40));
		this.lifeInfo.setPosition(
				ScreemUtil.widthScreem() / 8.0f
				,ScreemUtil.heightScreem() / 10.0f);

	    // Adiciona na tela
	    addChild(this.btRadioShow);
	    addChild(this.goldBar);
	    addChild(this.silverBar);
	    addChild(this.waveInfo);
	    addChild(this.lifeInfo);
	}

	/**
	 * 
	 */
	public void buttonCliked(Button sender) {
		if (sender.equals(this.btRadioShow)) {
			//TODO IMPLEMENTAR
//    		actionOn = true;
//    		Integer opacity = null;
//    				
//           	for (CCSprite trooperSprite : _trooperList) {
//           		Trooper trooper = (Trooper) trooperSprite.getUserData();
//           		if (opacity == null) {
//           			opacity = trooper.radioSprite.getOpacity();
//           		}
//
//           		if (opacity == 0) {
//           			trooper.radioSprite.setOpacity(255);
//           		} else {
//           			trooper.radioSprite.setOpacity(0);
//           		}
//			}
		}
	}

	public boolean nextWave() {
		this.waveAtual++;
		
		if (this.waveAtual > this.waveTotal) {
			return false;
		} else {
			this.waveInfo.getLabel().setString("Waves: " + waveAtual + " / " + waveTotal);
			return true;
		}
	}

	public boolean looseLife() {
		this.lifeAtual--;
		
		if (this.lifeAtual <= 0) {
			return false;
		} else {
			this.lifeInfo.getLabel().setString("Lifes: " + lifeAtual + " / " + lifeTotal);
			return true;
		}
	}

	public void setLifeLabel(String text) {
		this.lifeInfo.getLabel().setString(text);
	}

	public CGPoint getStartPosition() {
		return startPosition;
	}

	public int getLifeAtual() {
		return lifeAtual;
	}

	public int getWaveAtual() {
		return waveAtual;
	}

}
