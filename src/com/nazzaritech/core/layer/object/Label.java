package com.nazzaritech.core.layer.object;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;

import com.nazzaritech.core.delegate.LabelDelegate;

public class Label extends CCLayer {

	private CCLabel label;
	private LabelDelegate delegate;

	public Label(String labelText) {

		this.setIsTouchEnabled(true);
		this.label = CCLabel.makeLabel(labelText, "DroidSans", 32);
		addChild(this.label);
	}

	public void setDelegate(LabelDelegate delegate) {
		this.delegate = delegate;
	}

	public LabelDelegate getDelegate() {
		return delegate;
	}

	public CCLabel getLabel() {
		return label;
	}

	//	@Override
//	protected void registerWithTouchDispatcher() {
//		CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 0, false);
//	}

}
