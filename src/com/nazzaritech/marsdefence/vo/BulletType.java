package com.nazzaritech.marsdefence.vo;

/**
 * Enum: BulletType
 * All kinds of bullets 
 * 
 * @author Nazzaritech Games
 */
public enum BulletType {

	MACHINE_GUN(1, "MACHINE_GUN", "bullet_machine_gun.png",2,1200f,0.2f, 1);

	private int number;
	private int tag;
	private float speed;
	private String name;
	private String image;
	private float frequenceShoot;
	private long power;
	
    private BulletType(int number, String name, String image, int tag, float speed, float frequenceShoot, long power) {
    	this.number = number;
    	this.name = name;
    	this.image = image;
    	this.tag = tag;
    	this.speed = speed;
    	this.frequenceShoot = frequenceShoot;
    	this.power = power;
    }

    public String getImage() {
    	return this.image;
    }

    public int getNumber() {
    	return this.number;
    }

    public int getTag() {
    	return this.tag;
    }

    public String getName() {
    	return this.name;
    }

	public float getSpeed() {
		return speed;
	}

	public float getFrequenceShoot() {
		return frequenceShoot;
	}

	public long getPower() {
		return power;
	}
};


