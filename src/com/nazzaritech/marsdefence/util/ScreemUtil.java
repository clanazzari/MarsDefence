package com.nazzaritech.marsdefence.util;

import java.util.Random;

import org.cocos2d.nodes.CCDirector;

/**
 * 
 * @author clanazzari
 */
public class ScreemUtil {

	public static float randomPositionY() {

		Random rand = new Random();
		// Area de Spawn minino - eixo Y
	    int minY = (int)2;
		
	    // Area de Spawn maximo - eixo Y
	    int maxY = (int)heightScreem() - 2;
	    int rangeY = maxY - minY;
	    int actualY = rand.nextInt(rangeY) + minY;
	    
	    return actualY;

	}

	public static float heightScreem() {
		return CCDirector.sharedDirector().displaySize().height;
	}

	public static float widthScreem() {
		return CCDirector.sharedDirector().displaySize().width;
	}

}
