package com.nazzaritech.marsdefence.scene;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.SparseArray;

import com.nazzaritech.core.layer.StageLayer;
import com.nazzaritech.marsdefence.vo.Bullet;
import com.nazzaritech.marsdefence.vo.MonsterType;
import com.nazzaritech.marsdefence.vo.SceneTag;
import com.nazzaritech.marsdefence.vo.Wave;

public class StageOne extends StageLayer {

	protected StageOne() {
	    super();

		//TODO Set DEBUG to TRUE
		setDebug(false);
		putDebug("position w:" + getPosition().x + " h:" + getPosition().y);

	    // UPDATE
	    this.schedule("update");
	}

	public static CCScene scene()
	{
	    CCScene scene = CCScene.node();
	    CCLayer layer = new StageOne();
	    scene.setTag(SceneTag.CHAPTER_ONE_STAGE_ONE);
	    scene.addChild(layer);
	 
	    return scene;
	}

	protected SparseArray<Wave> waves() {
		
		// Quantidade de Waves
		int quantityWaves = 4;
		
		// Inicia lista de waves
		SparseArray<Wave> waveList = new SparseArray<Wave>(quantityWaves);
		MonsterType [] monsterTypesList;
		float [] monsterTimeList;
		Wave wave;

		// WAVE 1
		// Quant Monstros
		monsterTypesList = new MonsterType [20];
		monsterTimeList = new float [20];
		
		monsterTypesList[0] = MonsterType.ALIEN_BARATA;
		monsterTimeList[0] = 1f;
		monsterTypesList[1] = MonsterType.ALIEN_BARATA;
		monsterTimeList[1] = 4f;
		monsterTypesList[2] = MonsterType.ALIEN_BARATA;
		monsterTimeList[2] = 8f;
		monsterTypesList[3] = MonsterType.ALIEN_BARATA;
		monsterTimeList[3] = 10f;
		monsterTypesList[4] = MonsterType.ALIEN_BARATA;
		monsterTimeList[4] = 12f;
		monsterTypesList[5] = MonsterType.ALIEN_BARATA;
		monsterTimeList[5] = 12.5f;
		monsterTypesList[6] = MonsterType.ALIEN_BARATA;
		monsterTimeList[6] = 13f;
		monsterTypesList[7] = MonsterType.ALIEN_BARATA;
		monsterTimeList[7] = 13.5f;
		monsterTypesList[8] = MonsterType.ALIEN_BARATA;
		monsterTimeList[8] = 14f;
		monsterTypesList[9] = MonsterType.ALIEN_BARATA;
		monsterTimeList[9] = 15f;
		monsterTypesList[10] = MonsterType.ALIEN_BARATA;
		monsterTimeList[10] = 16f;
		monsterTypesList[11] = MonsterType.ALIEN_BARATA;
		monsterTimeList[11] = 17f;
		monsterTypesList[12] = MonsterType.ALIEN_BARATA;
		monsterTimeList[12] = 18f;
		monsterTypesList[13] = MonsterType.ALIEN_BARATA;
		monsterTimeList[13] = 19f;
		monsterTypesList[14] = MonsterType.ALIEN_BARATA;
		monsterTimeList[14] = 20f;
		monsterTypesList[15] = MonsterType.ALIEN_BARATA;
		monsterTimeList[15] = 21f;
		monsterTypesList[16] = MonsterType.ALIEN_BARATA;
		monsterTimeList[16] = 22f;
		monsterTypesList[17] = MonsterType.ALIEN_BARATA;
		monsterTimeList[17] = 23f;
		monsterTypesList[18] = MonsterType.ALIEN_BARATA;
		monsterTimeList[18] = 24f;
		monsterTypesList[19] = MonsterType.ALIEN_BARATA;
		monsterTimeList[19] = 25f;
		wave = new Wave();
		wave.setListMonster(monsterTypesList);
		wave.setListMonsterTime(monsterTimeList);
		waveList.append(1, wave);
		
		// WAVE 2
		// Quant Monstros
		monsterTypesList = new MonsterType [5];
		monsterTimeList = new float [5];

		monsterTypesList[0] = MonsterType.ALIEN_BARATA;
		monsterTimeList[0] = 1f;
		monsterTypesList[1] = MonsterType.ALIEN_BARATA;
		monsterTimeList[1] = 2f;
		monsterTypesList[2] = MonsterType.ALIEN_JACARE;
		monsterTimeList[2] = 3f;
		monsterTypesList[3] = MonsterType.ALIEN_JACARE;
		monsterTimeList[3] = 4f;
		monsterTypesList[4] = MonsterType.ALIEN_JACARE;
		monsterTimeList[4] = 5f;
		wave = new Wave();
		wave.setListMonster(monsterTypesList);
		wave.setListMonsterTime(monsterTimeList);
		waveList.append(2, wave);

		// WAVE 3
		// Quant Monstros
		monsterTypesList = new MonsterType [5];
		monsterTimeList = new float [5];

		monsterTypesList[0] = MonsterType.ALIEN_BARATA;
		monsterTimeList[0] = 1f;
		monsterTypesList[1] = MonsterType.ALIEN_JACARE2;
		monsterTimeList[1] = 2f;
		monsterTypesList[2] = MonsterType.ALIEN_BARATA;
		monsterTimeList[2] = 3f;
		monsterTypesList[3] = MonsterType.ALIEN_JACARE2;
		monsterTimeList[3] = 4f;
		monsterTypesList[4] = MonsterType.ALIEN_JACARE2;
		monsterTimeList[4] = 5f;
		wave = new Wave();
		wave.setListMonster(monsterTypesList);
		wave.setListMonsterTime(monsterTimeList);
		waveList.append(3, wave);

		// WAVE 4
		// Quant Monstros
		monsterTypesList = new MonsterType [5];
		monsterTimeList = new float [5];

		monsterTypesList[0] = MonsterType.ALIEN_JACARE2;
		monsterTimeList[0] = 1f;
		monsterTypesList[1] = MonsterType.ALIEN_JACARE2;
		monsterTimeList[1] = 2f;
		monsterTypesList[2] = MonsterType.ALIEN_THOR;
		monsterTimeList[2] = 3f;
		monsterTypesList[3] = MonsterType.ALIEN_THOR;
		monsterTimeList[3] = 4f;
		monsterTypesList[4] = MonsterType.ALIEN_THOR;
		monsterTimeList[4] = 5f;
		wave = new Wave();
		wave.setListMonster(monsterTypesList);
		wave.setListMonsterTime(monsterTimeList);
		waveList.append(4, wave);

		return waveList;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	/**
	 * Metodo que desenha caminho, TEMPORARIO
	 * 
	 * @return lista de CGPoints com os pontos (x,y) de destino
	 */
	public void draw(GL10 gl) {
		super.draw(gl);
		
//		gl.glEnable(GL10.GL_LINE_SMOOTH);
//		gl.glColor4f(255, 255, 255, 255);
//		gl.glLineWidth(2f);
//		
//		CCDrawingPrimitives.ccDrawLine(gl, 
//				CGPoint.ccp(ScreemUtil.widthScreem(),400), 
//				CGPoint.ccp(ScreemUtil.widthScreem() - 500,400));
//		
//		CCDrawingPrimitives.ccDrawLine(gl, 
//				CGPoint.ccp(ScreemUtil.widthScreem() - 500,400), 
//				CGPoint.ccp(ScreemUtil.widthScreem() - 500,200));
//		
//		CCDrawingPrimitives.ccDrawLine(gl, 
//				CGPoint.ccp(ScreemUtil.widthScreem() - 500,200), 
//				CGPoint.ccp(ScreemUtil.widthScreem(),200));
		
	}
	
	/**
	 * Metodo Obrigatorio onde deve retornar a rota completa do monstro
	 * 
	 * @return lista de CGPoints com os pontos (x,y) de destino
	 */
	@Override
	protected SparseArray<CGPoint> monsterRote() {
		SparseArray<CGPoint> routes = new SparseArray<CGPoint>(5);

		routes.put(1, CGPoint.ccp(getContentSize().width + 80,getContentSize().height - 170));
		routes.put(2, CGPoint.ccp(630,getContentSize().height - 170));
		routes.put(3, CGPoint.ccp(630,getContentSize().height - 440));
		routes.put(4, CGPoint.ccp(200,getContentSize().height - 440));
		routes.put(5, CGPoint.ccp(200,getContentSize().height));

		return routes;
	}

	@Override
	public void monsterPass(Object sender) {
	    CCSprite monster = (CCSprite) sender;

        removeMonster(monster);

		if (!menuLayer.looseLife()) {
			CCDirector.sharedDirector().replaceScene(GameOverLayer.scene("Voce morreu! Tente novamente."));
		}
	}

	@Override
	public void bulletHitMonster(Object sender) {
	    CCSprite bulletSprite = (CCSprite)sender;
	    
	    Bullet bullet = (Bullet) bulletSprite.getUserData();
	    hitMonster(bullet.getPower(), bullet.getTargetMonster());
		removeBullet(bulletSprite);
	    
	}

	/**
	 * SETA TAMANHO DA TELA
	 */
	@Override
	protected CGSize stageSize() {
		return CGSize.make(1000, 600);
	}

	/**
	 * SETA IMAGEN DA FASE
	 */
	@Override
	protected String stageBackgrounImage() {
		return "bg_stage2.png";
	}
}

