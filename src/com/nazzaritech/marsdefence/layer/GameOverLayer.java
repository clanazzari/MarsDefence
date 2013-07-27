package com.nazzaritech.marsdefence.layer;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class GameOverLayer extends CCColorLayer
{
    protected CCLabel _label;
    protected CCLabel _labelCount;
    protected int nrLabelCount = 5;
 
    public static CCScene scene(String message)
    {
        CCScene scene = CCScene.node();
        GameOverLayer layer = new GameOverLayer(ccColor4B.ccc4(255, 255, 255, 255));
 
        layer.getLabel().setString(message);
        scene.addChild(layer);
 
        return scene;
    }
 
    public CCLabel getLabel()
    {
        return _label;
    }

    public CCLabel getLabelCounter()
    {
        return _labelCount;
    }
 
    protected GameOverLayer(ccColor4B color)
    {
        super(color);
 
        this.setIsTouchEnabled(false);
 
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        
        _label = CCLabel.makeLabel("XXXXX", "DroidSans", 32);
        _label.setColor(ccColor3B.ccBLACK);
        _label.setPosition(winSize.width / 2.0f, winSize.height / 2.0f);
        addChild(_label);
        
        _labelCount = CCLabel.makeLabel("Novo jogo em: 5", "DroidSans", 32);
        _labelCount.setColor(ccColor3B.ccBLACK);
        _labelCount.setPosition(winSize.width / 2.0f, winSize.height / 2.0f + winSize.height / 4.0f);
        addChild(_labelCount);
 
        this.runAction(CCSequence.actions(CCDelayTime.action(1.0f), CCCallFunc.action(this, "gameOverCounter")));
        this.runAction(CCSequence.actions(CCDelayTime.action(2.0f), CCCallFunc.action(this, "gameOverCounter")));
        this.runAction(CCSequence.actions(CCDelayTime.action(3.0f), CCCallFunc.action(this, "gameOverCounter")));
        this.runAction(CCSequence.actions(CCDelayTime.action(4.0f), CCCallFunc.action(this, "gameOverCounter")));
        this.runAction(CCSequence.actions(CCDelayTime.action(5.0f), CCCallFunc.action(this, "gameOverDone")));
    }

    public void gameOverCounter()
    {
    	_labelCount.setString("Novo jogo em: " + --nrLabelCount);
    }

    public void gameOverDone()
    {
        CCDirector.sharedDirector().replaceScene(StageOne.scene());
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event)
    {
        gameOverDone();
 
        return true;
    }
}