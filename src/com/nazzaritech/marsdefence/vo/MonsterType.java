package com.nazzaritech.marsdefence.vo;


/**
 * Enum: MonsterType
 * All kinds of monsters and his properties
 * 
 * @author Nazzaritech Games
 */
public enum MonsterType {

	GHOST(1, "GHOST", "Ghost.png",1,50, "bar_life1", 4, 2),
	ZOMBIE(7, "ZOMBIE", "Zombie.png",1,50, "bar_life1", 4, 2),
	ALIEN_1(8, "ALIEN_1", "alien1.png",1,150, "bar_life1", 4, 2),
	ALIEN_JACARE(8, "ALIEN_1", "alien_jacare.png",1,40, "bar_life2", 25, 4),
	ALIEN_JACARE2(8, "ALIEN_1", "alien_jacare2.png",1,90, "bar_life2", 15, 3),
	ALIEN_BARATA(8, "ALIEN_1", "alien_barata.png",1,150, "bar_life2", 6, 3),
	ALIEN_THOR(8, "ALIEN_1", "alien_thor.png",1,25, "bar_life2", 40, 3);

	private int number;
	private int tag;
	private String name;
	private String image;
	private float speed;
	private String barName;
	private long hp;
	private long zIndex;

    private MonsterType(int number, String name, String image, int tag, float speed, String barName, long hp, long zIndex) {
    	this.number = number;
    	this.name = name;
    	this.image = image;
    	this.tag = tag;
    	this.speed = speed;
    	this.barName = barName;
    	this.hp = hp;
    	this.zIndex = zIndex;
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

	public String getBarName() {
		return barName;
	}

	public long getHp() {
		return hp;
	}

	public long getzIndex() {
		return zIndex;
	}
};


