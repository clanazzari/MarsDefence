package com.nazzaritech.marsdefence.vo;

/**
 * Enum: MonsterType
 * All kinds of monsters and his properties
 * 
 * @author Nazzaritech Games
 */
public enum TrooperType {

	TROOPER(1, "TROOPER", "trooper.png", 1, BulletType.MACHINE_GUN, "radio_trooper2.png");

	private int number;
	private int tag;
	private String name;
	private String image;
	private BulletType bulletType;
	private String radioImage;

    private TrooperType(int number, String name, String image, int tag, BulletType bulletType, String radioImage) {
    	this.number = number;
    	this.name = name;
    	this.image = image;
    	this.tag = tag;
    	this.bulletType = bulletType;
    	this.radioImage = radioImage;
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

	public BulletType getBulletType() {
		return bulletType;
	}

	public String getRadioImage() {
		return radioImage;
	}
};


