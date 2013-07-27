package com.nazzaritech.marsdefence.vo;

import java.util.UUID;

import org.cocos2d.nodes.CCSprite;

/**
 * Virtual Object: Bullet
 * VO for all Bullets in the game
 * 
 * @author Nazzaritech Games
 */
public class Bullet {

    // vars
    private UUID id;
    private int speed;
    private BulletType bulletType;
    private CCSprite targetMonster;

    // Start Position vars
    private float startPositionY;
    private float startPositionX;
    private long power;

    /**
     * Monster constructor 
     * 
     * @param bulletType         - Bullet Type
     * @param speed              - Bullet Speed
     * @param startPositionX     - Start Position X
     * @param startPositionY     - Start Position Y
     */
    public Bullet(BulletType bulletType, int speed, float startPositionX, float startPositionY) {
    	this.bulletType = bulletType;
        this.speed = speed;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.id = UUID.randomUUID();
        this.power = bulletType.getPower();
    }

    public int getSpeed() {
        return this.speed;
    }

    public float getStartPositionY() {
        return startPositionY;
    }

    public float startPositionX() {
        return startPositionX;
    }

    public String getId() {
    	return id.toString();
    }

	public BulletType getBulletType() {
		return bulletType;
	}

	public CCSprite getTargetMonster() {
		return targetMonster;
	}

	public void setTargetMonster(CCSprite targetMonster) {
		this.targetMonster = targetMonster;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}
}
