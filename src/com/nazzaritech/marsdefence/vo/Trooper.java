package com.nazzaritech.marsdefence.vo;

import java.util.UUID;

import org.cocos2d.nodes.CCSprite;

/**
 * Virtual Object: Trooper
 * VO for all trooper in the game
 * 
 * @author Nazzaritech Games
 */
public class Trooper {

    // vars
    private UUID id;
	private boolean shootAlowed = true;
	private float shootInterval = 0.0f;
	public CCSprite radioSprite;

    // Start Position vars
    private TrooperType trooperType;

    /**
     * Monster constructor 
     * 
     * @param trooperType    - Trooper Type
     */
    public Trooper(TrooperType trooperType) {
        this.trooperType = trooperType;
        this.id = UUID.randomUUID();
    }

    public String getId() {
    	return id.toString();
    }

	public TrooperType getTrooperType() {
		return trooperType;
	}

	public boolean isShootAlowed() {
		return shootAlowed;
	}

	public void setShootAlowed(boolean shootAlowed) {
		this.shootAlowed = shootAlowed;
	}

	public float getShootInterval() {
		return shootInterval;
	}

	public void setShootInterval(float shootInterval) {
		this.shootInterval = shootInterval;
	}
}
