package com.nazzaritech.core.util;

import org.cocos2d.nodes.CCSprite;

import com.nazzaritech.marsdefence.util.ScreemUtil;

public class CoreUtil {
	
    public static boolean isInsideArea(CCSprite area, float x, float y) {
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
}
