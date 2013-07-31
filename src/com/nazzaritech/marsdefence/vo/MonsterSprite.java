package com.nazzaritech.marsdefence.vo;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class MonsterSprite extends CCSprite {
	
	@Override
	public void draw(GL10 gl) {
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glColor4f(255, 255, 255, 255);
		gl.glLineWidth(2f);
		CCDrawingPrimitives.ccDrawRect(gl,CGRect.make(getPosition(), CGSize.make(50, 50)));

		super.draw(gl);
	}
	
    public MonsterSprite(String filepath) {
        sprite(filepath);
    }
}
