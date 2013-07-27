package com.nazzaritech.marsdefence.layer;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.util.SparseArray;

import com.nazzaritech.marsdefence.utils.ScreemUtil;
import com.nazzaritech.marsdefence.vo.Bullet;
import com.nazzaritech.marsdefence.vo.MonsterType;
import com.nazzaritech.marsdefence.vo.SceneTag;
import com.nazzaritech.marsdefence.vo.Trooper;

public class StageOne extends StageLayer {

	protected StageOne() {
	    super();

		// Seta tamanho da fase um
		setContentSize(1000, 600);

		this.minPositionXCenter = - (getContentSize().width - ScreemUtil.widthScreem());
		this.maxPositionXCenter = 0;

		this.minPositionYCenter = - (getContentSize().height - ScreemUtil.heightScreem());
		this.maxPositionYCenter = 0;

	    CCSprite background = CCSprite.sprite("bg_stage2.png");
	    background.setPosition(getContentSize().width / 2f, getContentSize().height/ 2f);
	    background.setVertexZ(1);
	    addChild(background);

	    bar_gold.setPosition(100,ScreemUtil.heightScreem() - 40);
	    bar_gold.setVertexZ(1);
	    addChild(bar_gold);

	    bar_silver.setPosition(240,ScreemUtil.heightScreem() - 40);
	    bar_silver.setVertexZ(1);
	    addChild(bar_silver);

		// Pontuacao monstros destruidos
//		_ptMonstrosDestruidos = CCLabel.makeLabel("Destruidos: 00/90", "DroidSans", 32);
//		_ptMonstrosDestruidos.setColor(ccColor3B.ccWHITE);
//		_ptMonstrosDestruidos.setPosition(_posMonstrosDestruidos_X, _posMonstrosDestruidos_y);
//		_numMonstrosDestruidos = 0;
//		addChild(_ptMonstrosDestruidos);
		// Pontuacao monstros passaram
		_posVidas_X = ScreemUtil.widthScreem() / 8.0f;
		_posVidas_y = ScreemUtil.heightScreem() / 10.0f;
		_ptVidas = CCLabel.makeLabel("Vida: 20", "DroidSans", 32);
		_ptVidas.setColor(ccColor3B.ccWHITE);
		_ptVidas.setPosition(_posVidas_X, _posVidas_y);
		_numVidas = 20;
		addChild(_ptVidas);
		
		// ADICIONA BOTOES
	    _bt_radio = CCSprite.sprite("bt_radio.png");
	    _bt_radio.setPosition(_posVidas_X, _posVidas_y+50);
	    addChild(_bt_radio);

		//TODO Set DEBUG to TRUE
		setDebug(false);
		putDebug("position w:" + getPosition().x + " h:" + getPosition().y);

	    this.setIsTouchEnabled(true);

	    // 1. INTRODUCTION
	    this.schedule("addAlienThor", 5.0f);
	    this.schedule("addAlienJacare", 5.0f);
	    this.schedule("addAlienJacare2", 3.0f);
	    this.schedule("addAlienBarata", 1.3f);

	    // UPDATE
	    this.schedule("update");
	}

	public void addAlienJacare(float dt) {
		addMonster(MonsterType.ALIEN_JACARE);
	}

	public void addAlienBarata(float dt) {
		addMonster(MonsterType.ALIEN_BARATA);
	}

	public void addAlienThor(float dt) {
		addMonster(MonsterType.ALIEN_THOR);
	}

	public void addAlienJacare2(float dt) {
		addMonster(MonsterType.ALIEN_JACARE2);
	}

	public void addAlien1(float dt) {
		addMonsterAnimate(MonsterType.ALIEN_1);
	}

	public static CCScene scene()
	{
	    CCScene scene = CCScene.node();
	    CCLayer layer = new StageOne();
	    scene.setTag(SceneTag.CHAPTER_ONE_STAGE_ONE);
	    scene.addChild(layer);
	 
	    return scene;
	}

	/**
	 * UPTADE METHOD
	 * @param dt - TIME
	 */
	public void update(float dt) {

		// If tap is hold more than 1 minute, create trooper
//		if (isTouchHold(dt)) {
//			addTrooper(TrooperType.TROOPER);
//		}

		// To all Troopers validate
		for (CCSprite trooperSprite : getTrooperList()) {
			Trooper trooper = (Trooper) trooperSprite.getUserData();
			
			// If he cant shoot, 
			if (!trooper.isShootAlowed()) {
				trooper.setShootInterval(trooper.getShootInterval() + dt);
				if (trooper.getShootInterval() > trooper.getTrooperType().getBulletType().getFrequenceShoot()) {
					trooper.setShootInterval(0.0f);
					trooper.setShootAlowed(true);
				}
			
			// If he CAN shoot
			} else {
				trooper.setShootInterval(0.0f);
				trooper.setShootAlowed(false);

				for (CCSprite monster : getMonsterList()) {
					
					if (isMonsterOnTrooperRadio(trooperSprite.getPosition(),monster.getPosition())) {

						shootMonster(CGPoint.ccp(trooperSprite.getPosition().x, trooperSprite.getPosition().y)
								, monster, trooper.getTrooperType().getBulletType());

						break;
					}
				}				
			}

			// set new object data
			trooperSprite.setUserData(trooper);
		}
//
//		ArrayList<CCSprite> bulletToDelete = new ArrayList<CCSprite>();
//
//		for (CCSprite bullet : getBulletList()) {
//			CGRect projectileRect = CGRect.make(bullet.getPosition().x
//					- (bullet.getContentSize().width / 2.0f),
//					bullet.getPosition().y
//							- (bullet.getContentSize().height / 2.0f),
//					bullet.getContentSize().width,
//					bullet.getContentSize().height);
//
//			ArrayList<CCSprite> monstersToDelete = new ArrayList<CCSprite>();
//
//			for (CCSprite monster : getMonsterList()) {
//				CGRect targetRect = CGRect.make(monster.getPosition().x
//						- (monster.getContentSize().width),
//						monster.getPosition().y
//								- (monster.getContentSize().height),
//						monster.getContentSize().width,
//						monster.getContentSize().height);
//
//				if (CGRect.intersects(projectileRect, targetRect)) {
//					monstersToDelete.add(monster);
//					break;
//				}
//			}
//
//			for (CCSprite monster : monstersToDelete) {
////				removeMonster(monster);
//			}
//
//			if (monstersToDelete.size() > 0) {
//				bulletToDelete.add(bullet);
//			}
//		}
//
//		for (CCSprite bullet : bulletToDelete) {
////			removeBullet(bullet);
//
//			_numMonstrosDestruidos++;
//			if (_numMonstrosDestruidos < 10) {
//				_ptMonstrosDestruidos.setString("Destruidos: 0" + _numMonstrosDestruidos + "/90");
//			} else {
//				_ptMonstrosDestruidos.setString("Destruidos: " + _numMonstrosDestruidos + "/90");
//			}
//			
//			if (_numMonstrosDestruidos >= 90) {
//				_numMonstrosDestruidos = 0;
//				CCDirector.sharedDirector().replaceScene(GameOverLayer.scene("Voce destruiu os doces! Parabenz!!"));
//			}
//		}

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

		routes.put(1, CGPoint.ccp(getContentSize().width,getContentSize().height - 170));
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
        
		_ptVidas.setString("Vida: " + --_numVidas);

		if (_numVidas <= 0) {
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

	@Override
	public void monsterLivePass(Object sender) {
	    CCSprite monsterLive = (CCSprite) sender;
	    removeChild(monsterLive, true);
		
	}
}

