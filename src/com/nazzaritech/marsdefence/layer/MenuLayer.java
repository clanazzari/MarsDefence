package com.nazzaritech.marsdefence.layer;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import android.view.MotionEvent;

import com.nazzaritech.marsdefence.utils.ScreemUtil;

public class MenuLayer extends CCLayer
{
    protected CCSprite _bt_exit;
    protected CCSprite _bt_newGame;
    protected CCSprite _bt_exit_on;
    protected CCSprite _bt_newGame_on;
    
    public static CCScene scene() {
        CCScene scene = CCScene.node();
        MenuLayer layer = new MenuLayer();
        scene.addChild(layer);
        return scene;
    }
 
    protected MenuLayer() {
        super();
        this.removeAllChildren(true);
        this.setIsTouchEnabled(true);

	    CCSprite background = CCSprite.sprite("fundoInicial.png");
	    background.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f);
	    background.setVertexZ(1);
	    addChild(background);

	    _bt_exit = CCSprite.sprite("bt_exit.png");
	    _bt_exit.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f - 100);
	    _bt_exit.setVertexZ(2);

	    _bt_exit_on = CCSprite.sprite("bt_exit_on.png");
	    _bt_exit_on.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f - 100);
	    _bt_exit_on.setVertexZ(2);

	    _bt_newGame = CCSprite.sprite("bt_newGame.png");
	    _bt_newGame.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f + 40);
	    _bt_newGame.setVertexZ(2);

	    _bt_newGame_on = CCSprite.sprite("bt_newGame_on.png");
	    _bt_newGame_on.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f + 40);
	    _bt_newGame_on.setVertexZ(2);

	    addChild(_bt_exit);
	    addChild(_bt_newGame);
	    
        
    }

    public void newGame()
    {
        CCDirector.sharedDirector().replaceScene(StageOne.scene());
    }
    
    public void exit()
    {
        CCDirector.sharedDirector().end();
    	CCDirector.sharedDirector().getActivity().finish();
    }
    
    private boolean isInsideArea(CCSprite area, float x, float y) {
    	float areaMinX = area.getPosition().x - area.getContentSize().width / 2f;
    	float areaMaxX = area.getPosition().x + area.getContentSize().width / 2f;
    	float areaMinY = (ScreemUtil.heightScreem() - area.getPosition().y) - area.getContentSize().height / 2f;
    	float areaMaxY = (ScreemUtil.heightScreem() - area.getPosition().y) + area.getContentSize().height / 2f;

    	if (x <= areaMaxX && x >= areaMinX) {
        	if (y <= areaMaxY && y >= areaMinY) {
            	return true;
        	}
    	}

    	return false;
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
    	
    	// Se clicou em EXIT
    	if (isInsideArea(_bt_exit, event.getX(), event.getY())) {
            this.runAction(CCSequence.actions(CCDelayTime.action(0.2f), CCCallFunc.action(this, "exit")));
    	}
    	
    	// Se clicou em NEW GAME
    	if (isInsideArea(_bt_newGame, event.getX(), event.getY())) {
            this.runAction(CCSequence.actions(CCDelayTime.action(0.2f), CCCallFunc.action(this, "newGame")));
    	}

    	removeChild(_bt_exit_on, false);
		removeChild(_bt_newGame_on, false);
    	removeChild(_bt_exit, false);
		removeChild(_bt_newGame, false);
		addChild(_bt_exit);
		addChild(_bt_newGame);

    	return super.ccTouchesEnded(event);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
    	
    	// Se clicou em EXIT
    	if (isInsideArea(_bt_exit, event.getX(), event.getY())) {
    		removeChild(_bt_exit, false);
    		addChild(_bt_exit_on);

    	// Se clicou em NEW GAME
    	} else if (isInsideArea(_bt_newGame, event.getX(), event.getY())) {
    		removeChild(_bt_newGame, false);
    		addChild(_bt_newGame_on);
    	
    	} 

    	return super.ccTouchesEnded(event);
    }

}