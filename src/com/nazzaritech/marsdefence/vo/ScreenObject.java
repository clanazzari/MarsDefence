package com.nazzaritech.marsdefence.vo;

import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class ScreenObject extends CCSprite {

	private CCLabel label;
	private CGPoint startPosition;

	public ScreenObject(String image) {
		super(image);
	}

	public CGPoint getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(CGPoint startPosition) {
		this.startPosition = startPosition;
		this.setPosition(startPosition);
	}

	public CCLabel getLabel() {
		return label;
	}

	public void setLabel(CCLabel label) {
		this.label = label;
	}

}
