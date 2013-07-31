package com.nazzaritech.marsdefence.vo;


/**
 * Virtual Object: Bullet
 * VO for all Bullets in the game
 * 
 * @author Nazzaritech Games
 */
public class Wave {

    // 
    private float waveTime;

    private MonsterType [] listMonster; 

    private float [] listMonsterTime;

	public float getWaveTime() {
		return waveTime;
	}

	public void setWaveTime(float waveTime) {
		this.waveTime = waveTime;
	}

	public MonsterType[] getListMonster() {
		return listMonster;
	}

	public void setListMonster(MonsterType[] listMonster) {
		this.listMonster = listMonster;
	}

	public float[] getListMonsterTime() {
		return listMonsterTime;
	}

	public void setListMonsterTime(float[] listMonsterTime) {
		this.listMonsterTime = listMonsterTime;
	}

}
