package com.nazzaritech.marsdefence.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import com.nazzaritech.marsdefence.utils.ScreemUtil;

public class InitialLayer extends CCLayer
{
    public static CCScene scene() {
        CCScene scene = CCScene.node();
        InitialLayer layer = new InitialLayer();
        scene.addChild(layer);
        return scene;
    }
 
    protected InitialLayer() {
        super();
        this.removeAllChildren(true);
        this.setIsTouchEnabled(false);

	    CCSprite background = CCSprite.sprite("fundoInicial2.png");
	    background.setPosition(ScreemUtil.widthScreem() / 2f, ScreemUtil.heightScreem() / 2f);
	    background.setVertexZ(1);
	    addChild(background);
	    schedule("menuLayer",3);
    }

    public void menuLayer(float dt) {
        CCDirector.sharedDirector().replaceScene(MenuLayer.scene());
    }
}