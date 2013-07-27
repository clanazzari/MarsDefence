package com.nazzaritech.marsdefence.vo;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.nazzaritech.marsdefence.utils.ScreemUtil;

public class ScreenObject {
	private CCSprite sprite;
	private CGPoint startPosition;
	
	public CCSprite getSprite() {
		return sprite;
	}
	public void setSprite(CCSprite sprite) {
		this.sprite = sprite;
	}
	public CGPoint getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(CGPoint startPosition) {
		this.startPosition = startPosition;
	}
//	this.minPositionXCenter = - (getContentSize().width - ScreemUtil.widthScreem());
//	this.maxPositionXCenter = 0;
//	this.minPositionYCenter = - (getContentSize().height - ScreemUtil.heightScreem());
//	this.maxPositionYCenter = 0;

	public float getMinPositionX(float minPositionXCenter) {
		return - (getSprite().getContentSize().width - ScreemUtil.widthScreem());
	}
	
	public float getMaxPositionX(float maxPositionXCenter) {
		return 0;
	}
	
	public float getMinPositionY(float minPositionYCenter) {
		return - (getSprite().getContentSize().height - ScreemUtil.heightScreem());
	}
	
	public float getMaxPositionY(float maxPositionYCenter) {
		return 0;
	}

}
