package com.nazzaritech.core.layer.object;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;

import com.nazzaritech.core.delegate.ButtonDelegate;

public class Button extends CCLayer {

	private CCSprite buttonImage;
	private ButtonDelegate delegate;

	public Button(String buttonImage) {

		this.setIsTouchEnabled(true);

		this.buttonImage = CCSprite.sprite(buttonImage);
		addChild(this.buttonImage);
	}

	public void setDelegate(ButtonDelegate delegate) {
		this.delegate = delegate;
	}
	
	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 0, false);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());

		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		touchLocation = this.convertToNodeSpace(touchLocation);

		if (CGRect.containsPoint(buttonImage.getBoundingBox(), touchLocation)) {
			delegate.buttonCliked(this);
		}

		return true;
	}

}
