package com.nazzaritech.marsdefence.vo;

import java.util.UUID;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * Virtual Object: Monster
 * VO for all monsters in the game
 * 
 * @author Nazzaritech Games
 */
public class Monster extends CCSprite {

    // vars
    private UUID id;

    // Start Position vars
    private float startPositionY;
    private float startPositionX;
    private MonsterType monsterType;
    private CCSprite barSprite;
    private CCSprite barSpriteLife;
    private long hpFull;
    private long hp;

    /**
     * Monster constructor 
     * 
     * @param type               - Monster Type
     * @param speed              - Monster Speed
     * @param startPositionX     - Start Position X
     * @param startPositionY     - Start Position Y
     */
    public Monster(MonsterType monsterType, float startPositionX, float startPositionY) {

        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.id = UUID.randomUUID();
        this.monsterType = monsterType;
        this.barSprite = CCSprite.sprite(monsterType.getBarName() + "_red.png");
        this.barSpriteLife = CCSprite.sprite(monsterType.getBarName() + "_green.png");
        this.hpFull = monsterType.getHp();
        this.hp = monsterType.getHp();
    }

    public float getRealSpeed(CGPoint start, CGPoint destination) {
	    float length = CGPoint.ccpDistance(start, destination);
	    float realMoveDuration = length / monsterType.getSpeed();
	    return realMoveDuration;

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

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public CCSprite getBarSprite() {
		return barSprite;
	}

	public long getHpFull() {
		return hpFull;
	}

	public MonsterType getMonsterType() {
		return monsterType;
	}
	
	public CGPoint getPosition() {
		return CGPoint.ccp(startPositionX, startPositionY);
	}

	public CCSprite getBarSpriteLife() {
		return barSpriteLife;
	}
}
