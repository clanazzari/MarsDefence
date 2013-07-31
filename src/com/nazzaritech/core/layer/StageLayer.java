package com.nazzaritech.core.layer;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.util.SparseArray;
import android.view.MotionEvent;

import com.nazzaritech.marsdefence.R;
import com.nazzaritech.marsdefence.layer.MenuButtonsGame;
import com.nazzaritech.marsdefence.scene.GameOverLayer;
import com.nazzaritech.marsdefence.util.ScreemUtil;
import com.nazzaritech.marsdefence.vo.Bullet;
import com.nazzaritech.marsdefence.vo.BulletType;
import com.nazzaritech.marsdefence.vo.Monster;
import com.nazzaritech.marsdefence.vo.MonsterType;
import com.nazzaritech.marsdefence.vo.TagTypes;
import com.nazzaritech.marsdefence.vo.Trooper;
import com.nazzaritech.marsdefence.vo.TrooperType;
import com.nazzaritech.marsdefence.vo.Wave;

/**
 * Stage Layer
 * Abstract class that have all generic methods to stages
 * Must be extended by all Stages classes
 * 
 * @author Nazzaritech Games
 */
public abstract class StageLayer extends CCLayer {

	// Lista de 'Waves' de monstros
	private SparseArray<Wave> _waveList;
	private boolean _isWaveProcessing = false;
	private List<CCSprite> _monstersWaveList = new ArrayList<CCSprite>();

	// Lista de Pontos que define a ROTA do 
	private SparseArray<CGPoint> _routes;

	private ArrayList<CCSprite> _trooperList;

	private float locationTouchMoveX = 0;
	private float locationTouchMoveY = 0;
	private float locationTouchMoveXStart = 0;
	private float locationTouchMoveYStart = 0;
	private boolean isTouchInMovement = false;
	private boolean isTouchInMovementStart = false;
	private boolean enterMoveMethod = false;
    Context _context;

    // Objetos de Tela

    protected MenuButtonsGame menuLayer;
    
	private float minPositionXCenter = 0;
	private float maxPositionXCenter = 0;
		
	private float minPositionYCenter = 0;
	private float maxPositionYCenter = 0;


	// DEBUG
	private CCLabel _debug;
	private boolean _debugOn = false;
	private String _debugStr = "";

	// VARS
//	protected CCLabel _ptMonstrosDestruidos;
//	protected int _numMonstrosDestruidos;
//	protected float _posMonstrosDestruidos_X;
//	protected float _posMonstrosDestruidos_y;

	// Touch Time to Create new Trooper
	private float touchTime = 0.0f;
	private float touchTimeFinal = 0.2f;
	private boolean touchHold = false;
	private boolean actionOn = false;
	protected MotionEvent touchEvent;

	protected boolean isTouchHold(float dt) {
		if (touchHold && !actionOn) {
			touchTime+=dt;

			if (touchTime > touchTimeFinal) {
				touchHold = false;
				touchTime = 0.0f;
				return true;
			}
		}	
		return false;
	}
	
	protected MotionEvent getTouchEvent() {
		return touchEvent;
 	}

	/**
	 * UPTADE METHOD
	 * @param dt - TIME
	 */
	public void update(float dt) {

		// Verifica Waves
		// Se for 0, comecou agora!
		if (!_isWaveProcessing) {

			// TODO Se for ultima wave, processar
			if (!this.menuLayer.nextWave() ) {
				CCDirector.sharedDirector().replaceScene(GameOverLayer.scene("Voce venceu! Parabenz!"));
			}

			_isWaveProcessing = true;
			_monstersWaveList = new ArrayList<CCSprite>();

			for (int i = 0; i < _waveList.get(this.menuLayer.getWaveAtual()).getListMonster().length; i++) {
				MonsterType monsterType = _waveList.get(this.menuLayer.getWaveAtual()).getListMonster()[i];
				float time = _waveList.get(this.menuLayer.getWaveAtual()).getListMonsterTime()[i];
				addMonster(monsterType, time);
			}
		} else {

			// se todos da wave os monstros morreram, inicia wave nova
			if (_monstersWaveList.isEmpty()) {
				_isWaveProcessing = false;
			}

		}

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

				for (CCSprite monster : _monstersWaveList) {
					
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
	}

	/**
	 * Construtor
	 * 
	 * @param color
	 */
	protected StageLayer() {
		super();
	    this.setIsTouchEnabled(true);

		setContentSize(stageSize());

		// Seta rota do monstro
		_routes = monsterRote();
		// seta lista waves
		_waveList = waves();

		this.minPositionXCenter = - (getContentSize().width - ScreemUtil.widthScreem());
		this.maxPositionXCenter = 0;

		this.minPositionYCenter = - (getContentSize().height - ScreemUtil.heightScreem());
		this.maxPositionYCenter = 0;

	    CCSprite background = CCSprite.sprite(stageBackgrounImage());
	    background.setPosition(getContentSize().width / 2f, getContentSize().height/ 2f);
	    background.setVertexZ(1);
	    addChild(background);

	    // INICIA MENU LAYER
	    this.menuLayer = new MenuButtonsGame(20, waves().size());
	    addChild(this.menuLayer);

		this._context = CCDirector.sharedDirector().getActivity();

		SoundEngine.sharedEngine().preloadEffect(_context, R.raw.tshot);
		SoundEngine.sharedEngine().preloadEffect(_context, R.raw.teleport);
		_trooperList = new ArrayList<CCSprite>();
		
		CCTextureCache.sharedTextureCache().addImage("alien_left_1.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_2.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_3.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_4.png");

	}
//
//	/**
//	 * Metodo que adiciona um monstro 
//	 * 
//	 * @param monsterType   - Tipo do Monstro
//	 * @param monsterSpeed  - Velocidade do Monstro
//	 */
//	protected void addMonsterAnimate(MonsterType monsterType) {
//		
//		// Busca rota do monstro
//		SparseArray<CGPoint> rote = monsterRote();
//
//		// Cria um objeto MONSTRO e um ojbeto SPRITE
//		Monster monster = new Monster(
//				monsterType, // tipo monstro
//				rote.get(1).x,  // posicao inicial X
//				rote.get(1).y); // posicao inicial Y
//		
//        // Instancia o 'Sprite' do monstro
//		CCSprite monsterSprite = CCSprite.sprite(monsterType.getImage());
//		monsterSprite.setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
//		monsterSprite.setTag(TagTypes.TAG_MONSTER);
//		monsterSprite.setVertexZ(monster.getMonsterType().getzIndex());
//
//		// MONSTRO ANIMADO
//		CCAnimation monsterAnimations = CCAnimation.animation("ALIEN", 2 / 20f);
//		monsterAnimations.addFrame("alien_left_1.png");
//        monsterAnimations.addFrame("alien_left_2.png");
//        monsterAnimations.addFrame("alien_left_3.png");
//        monsterAnimations.addFrame("alien_left_4.png");
//
//        CCIntervalAction monsterAnimation = CCAnimate.action(monsterAnimations, true);
//
//		addChild(monsterSprite);
//		
//	    // Busca informacoes da rota do monstro
//		CCFiniteTimeAction firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
//		CCFiniteTimeAction[] roteList = new CCFiniteTimeAction[rote.size()-1];
//		int j = 0;
//		for (int i = 3; i <= rote.size(); i++) {
//			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
//		}
//
//		// Apos rotas, executa metodo de monstros finalizados
//		roteList[j] = CCCallFuncN.action(this, "monsterPass");
//
//		CCRepeatForever.action(CCIntervalAction.action(1f));
//		// Mover monstro
//	    CCSequence actions = CCSequence.actions(firstRote, roteList);
//	    monsterSprite.runAction(CCRepeatForever.action(monsterAnimation));
//	    monsterSprite.runAction(actions);
//	    
//	    // Adiciona barra de vida vida ao monstro
//		monster.getBarSprite().setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
//		monster.getBarSprite().setVertexZ(2);
//		monster.getBarSprite().setTag(TagTypes.TAG_MONSTER_LIFE);
//		addChild(monster.getBarSprite());
//
//		firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
//		roteList = new CCFiniteTimeAction[rote.size()-1];
//		j = 0;
//		for (int i = 3; i <= rote.size(); i++) {
//			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
//		}
//		// Apos rotas, executa metodo de monstros finalizados
//		roteList[j] = CCCallFuncN.action(this, "monsterLivePass");
//
//	    actions = CCSequence.actions(firstRote, roteList);
//	    monster.getBarSprite().runAction(actions);
//
//		// Adiciona monstro na lista
//		monsterSprite.setUserData(monster);
//	    _monsterList.add(monsterSprite);
//
//	}

	/**
	 * Metodo que adiciona um monstro 
	 * 
	 * @param monsterType   - Tipo do Monstro
	 * @param monsterSpeed  - Velocidade do Monstro
	 */
	protected void addMonster(MonsterType monsterType, float delayTime) {

		// Cria um objeto MONSTRO e um ojbeto SPRITE
		Monster monster = new Monster(
				monsterType, // tipo monstro
				_routes.get(1).x,  // posicao inicial X
				_routes.get(1).y); // posicao inicial Y
		
        // Instancia o 'Sprite' do monstro
		CCSprite monsterSprite = CCSprite.sprite(monsterType.getImage());
		monsterSprite.setPosition(CGPoint.ccp(_routes.get(1).x,_routes.get(1).y));
		monsterSprite.setTag(TagTypes.TAG_MONSTER);
		monsterSprite.setVertexZ(monster.getMonsterType().getzIndex());
		
		// MONSTRO ANIMADO
		addChild(monsterSprite);
		
	    // Busca informacoes da rota do monstro
		CCFiniteTimeAction[] roteList = new CCFiniteTimeAction[_routes.size() ];
		int j = 0;
		for (int i = 2; i <= _routes.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(_routes.get(i-1),_routes.get(i)), _routes.get(i));
		}

		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterPass");

		CCRepeatForever.action(CCIntervalAction.action(1f));
		// Mover monstro
	    CCSequence actions = CCSequence.actions(CCDelayTime.action(delayTime), roteList);

	    monsterSprite.runAction(actions);
	    
	    moveLifeBar(monster.getBarSpriteLife(), monster, delayTime);

		// Adiciona monstro na lista
		monsterSprite.setUserData(monster);
		_monstersWaveList.add(monsterSprite);

	}

	private void moveLifeBar(CCSprite barSprite, Monster monster, float delayTime) {
	    // Adiciona barra de vida vida ao monstro
		barSprite.setPosition(CGPoint.ccp(_routes.get(1).x,_routes.get(1).y));
		barSprite.setVertexZ(2);
		barSprite.setTag(TagTypes.TAG_MONSTER_LIFE);
		addChild(barSprite);

	    // Busca informacoes da rota do monstro
		CCFiniteTimeAction[] roteList = new CCFiniteTimeAction[_routes.size()];
		int j = 0;
		for (int i = 2; i <= _routes.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(_routes.get(i-1),_routes.get(i)), _routes.get(i));
		}

		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterLivebarPass");

	    CCSequence actions = CCSequence.actions(CCDelayTime.action(delayTime), roteList);

	    barSprite.runAction(actions);
	}
	
	/**
	 * Add new Trooper
	 * 
	 * @param trooperType   - Trooper type
	 * @param location      - Trooper location 
	 */
	protected void addTrooper(TrooperType trooperType) {
		if (isTouchInMovement) {
			return;
		}
		// Cria um objeto Trooper
		Trooper trooper = new Trooper(trooperType); 
		
        // Instancia o 'Sprite' do trooper
		CCSprite trooperSprite = CCSprite.sprite(trooperType.getImage());
		trooperSprite.setPosition(
				getTouchEvent().getX() 
					+ (Math.abs(maxPositionXCenter - getPosition().x)),  
				getContentSize().height - getTouchEvent().getY() 
					- (Math.abs(minPositionYCenter - getPosition().y)));
		
				
	    putDebug("trooper X:" + getTouchEvent().getX() + " Y:" + Float.toString(getContentSize().height - getTouchEvent().getY()));
				
		trooperSprite.setTag(3);
		trooperSprite.setVertexZ(4);

        // Instancia o 'Sprite' do radio
		trooper.radioSprite = CCSprite.sprite(trooperType.getRadioImage());
		trooper.radioSprite.setPosition(trooperSprite.getPosition());
		trooper.radioSprite.setTag(TagTypes.TAG_RADIO);

		trooperSprite.setUserData(trooper);

		// Adiciona monstro na lista
	    _trooperList.add(trooperSprite);
		addChild(trooperSprite);
		addChild(trooper.radioSprite);
		SoundEngine.sharedEngine().playEffect(_context, R.raw.teleport);
	}

	protected void shootMonster(CGPoint trooperPoint, CCSprite monsterSprite, BulletType bulletType) {
//		Monster monster = (Monster) monsterSprite.getUserData();
		
		Bullet bullet = new Bullet(
				bulletType, 
				4, 
				monsterSprite.getPosition().x, 
				ScreemUtil.heightScreem() - monsterSprite.getPosition().y);

		bullet.setTargetMonster(monsterSprite);
		
	    CCSprite bulletSprite = CCSprite.sprite(bullet.getBulletType().getImage());
	    bulletSprite.setPosition(trooperPoint);
	    bulletSprite.setTag(2);
	    bulletSprite.setUserData(bullet);
	    bulletSprite.setVertexZ(5);
	    
	    addChild(bulletSprite);
	    SoundEngine.sharedEngine().playEffect(_context, R.raw.tshot);

	    // Choose one of the touches to work with
	    CGPoint monsterLocation = CCDirector.sharedDirector().convertToGL(
	    	CGPoint.ccp(monsterSprite.getPosition().x, ScreemUtil.heightScreem() - monsterSprite.getPosition().y));

	    // Distance betweeen monster and trooper equals RADIO
	    float lenght = CGPoint.ccpDistance(trooperPoint, monsterLocation);
	    float realSpeed = lenght / bulletType.getSpeed();
	    
	    // Move bullet to actual endpoint
	    bulletSprite.runAction(CCSequence.actions(
	            CCMoveTo.action(realSpeed, monsterLocation),
	            CCCallFuncN.action(this, "bulletHitMonster")));
	}

	protected boolean hitMonster(long bulletPower, CCSprite monsterSprite) {
		
		Monster monster = (Monster) monsterSprite.getUserData();
		monster.setHp(monster.getHp() - bulletPower);
		float percentualVida = Float.valueOf(monster.getHp()) / Float.valueOf(monster.getHpFull());
		// SE FOR MENOR = ZERO, O MONSTRO MORREU!
		if (monster.getHp() <=0) {
			removeMonster(monsterSprite);
			return true;

		// SE O MONSTRO NAO MORREU
		} else {
			
			float widthAntigo = monster.getBarSpriteLife().getContentSize().width;
			float widthNovo = monster.getBarSpriteLife().getContentSize().width * percentualVida;
			float widthRemovido = widthAntigo - widthNovo;
			
			CGRect newRect = CGRect.make(0, 0, 
					widthNovo,
					monster.getBarSpriteLife().getContentSize().height);
			
			// diminui tamanho da barra
			
			if (percentualVida <= 0.80f) {
				monster.getBarSpriteLife().setTexture(
						CCTextureCache.sharedTextureCache().addImage(
								monster.getMonsterType().getBarName() + "_red.png"));
			}

			monster.getBarSpriteLife().setTextureRect(newRect);
			monster.getBarSpriteLife().setPosition(
					monster.getBarSpriteLife().getPosition().x - widthRemovido, 
					monster.getBarSpriteLife().getPosition().y);
			
		}
		monsterSprite.setUserData(monster);

		return false;

	}

	protected boolean isMonsterOnTrooperRadio(CGPoint trooperPosition, CGPoint monsterPosition) {
		float distance = CGPoint.ccpDistance(trooperPosition, monsterPosition);
		if (distance < 130f) {
			return true;
		}

		return false;
	}

	protected void removeMonster(CCSprite monster) {
		removeChild(((Monster)monster.getUserData()).getBarSpriteLife(), true);
		removeChild(monster, true);
		_monstersWaveList.remove(monster);
	}

	protected void removeBullet(CCSprite bullet) {
		removeChild(bullet, true);
	}

	/**
	 * MonsterPass
	 * When one monster pass into the end
	 * 
	 * @param sender - Monster obj
	 */
	public abstract void monsterPass(Object sender);

	/**
	 * MonsterPass
	 * When one monster pass into the end
	 * 
	 * @param sender - Monster obj
	 */
	public void monsterLivebarPass(Object sender) {
	    CCSprite barSprite = (CCSprite) sender;
	    removeChild(barSprite, true);
		
	}

	/**
	 * bulletHitMonster
	 * 
	 * @param sender - obj
	 */
	public abstract void bulletHitMonster(Object sender);
	

	/**
	 * Metodo Obrigatorio onde deve retornar a rota completa do monstro
	 * 
	 * @return lista de CGPoints com os pontos (x,y) de destino
	 */
	protected abstract SparseArray<CGPoint> monsterRote();
	
	protected void setDebug(boolean debug) {
		if (debug) {
			_debugOn = true;
			_debug = CCLabel.makeLabel("D> ", "DroidSans", 32);
			_debug.setColor(ccColor3B.ccWHITE);
			float _posMonstrosDestruidos_X = ScreemUtil.widthScreem() / 6.0f;
			float _posMonstrosDestruidos_y = ScreemUtil.heightScreem() - (ScreemUtil.heightScreem() / 10.0f);
			_debug.setVertexZ(10);
			_debug.setPosition(
					_posMonstrosDestruidos_X,
					_posMonstrosDestruidos_y);
			addChild(_debug);
		} else {
			if (_debug != null) {
				removeChild(_debug, true);
				_debug = null;
			}
			_debugOn = false;
		}
	}
	
	protected void putDebug(String arg) {
		if (_debugOn) {
			_debugStr = arg;
			_debug.setString(arg);
		}
	}
	
	protected void increaseDebug(String arg) {
		if (_debugOn) {
			_debugStr = _debugStr + arg;
			_debug.setString(_debugStr);
		}
	}
	
	protected List<CCSprite> getTrooperList() {
		return _trooperList;
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		this.touchEvent = event;
		touchTime = 0.0f;
		touchHold = true;
	    return true;
	}

	@Override
	public boolean ccTouchesCancelled(MotionEvent event) {
		touchHold = false;
		actionOn = false;
		return super.ccTouchesCancelled(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event)
	{
		if(!isTouchInMovement) {
			addTrooper(TrooperType.TROOPER);
		}

		isTouchInMovement = false;
		locationTouchMoveX = 0;
		locationTouchMoveY = 0;
		locationTouchMoveYStart = 0;
		locationTouchMoveXStart = 0;
		isTouchInMovementStart = false;

		touchHold = false;
		actionOn = false;
	    return true;
	}
    
    private boolean isInsideArea(CCSprite area, float x, float y) {
    	float areaMinX = area.getPosition().x - area.getContentSize().width / 2f;
    	float areaMaxX = area.getPosition().x + area.getContentSize().width / 2f;
    	float areaMinY = (ScreemUtil.heightScreem() - area.getPosition().y) - area.getContentSize().height / 2f;
    	float areaMaxY = (ScreemUtil.heightScreem() - area.getPosition().y) + area.getContentSize().height / 2f;

    	if (x <= areaMaxX && x >= areaMinX) {
        	if (y <= areaMaxY && y >= areaMinY) {
            	return true;
        	}
    	}

    	return false;
    }
    
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		
		// se estiver processando, nao prossegue ate acabar
		if (enterMoveMethod) {
			return false;
		}
		
		enterMoveMethod = true;
		
		// Na primeira vez, sempre grava
		if (!isTouchInMovementStart) {
			locationTouchMoveXStart = event.getX();
			locationTouchMoveXStart = event.getY();
			isTouchInMovementStart = true;
//			return super.ccTouchesMoved(event);
		}

		// apenas considera como movimento, se moveu mais de 1 px
		if (!isTouchInMovement) {
			// guardar informacoes da posição

			if (Math.abs(locationTouchMoveXStart-locationTouchMoveX)>1
					|| Math.abs(locationTouchMoveYStart-locationTouchMoveY)>1) {
				
				isTouchInMovement = true;
				locationTouchMoveX = event.getX();
				locationTouchMoveY = event.getY();
			} else {
				isTouchInMovement = false;
				return super.ccTouchesMoved(event);
			}
			
		}

		changeToNewPositionScreen(event.getX(), event.getY());

		locationTouchMoveX = event.getX();
		locationTouchMoveY = event.getY();

		enterMoveMethod = false;

		return true;
	}

	private void changeToNewPositionScreen(float x, float y) {

		// Distancia movida
		float distanceMovedX = (x - locationTouchMoveX)/3f;
		float distanceMovedY = (locationTouchMoveY - y)/3f;

		// Nova posicao da tela
		float newPositionX = getPosition().x + distanceMovedX;
		float newPositionY = getPosition().y + distanceMovedY;

		// Nova posição do menu
		float newPositionMenuX = this.menuLayer.getPosition().x - distanceMovedX;
		float newPositionMenuY = this.menuLayer.getPosition().y - distanceMovedY;

		// Se a posicao Y ultrapassar o tamanho da tela, setar tamanho como limite
		if (newPositionY < minPositionYCenter) {
			newPositionY = minPositionYCenter;
			newPositionMenuY = this.menuLayer.getStartPosition().y - minPositionYCenter;
		} else if (newPositionY > maxPositionYCenter) {
			newPositionY = maxPositionYCenter;
			newPositionMenuY = this.menuLayer.getStartPosition().y;
		}

		// Se a posicao X ultrapassar o tamanho da tela, setar tamanho como limite
		if (newPositionX < minPositionXCenter) {
			newPositionX = minPositionXCenter;
			newPositionMenuX = this.menuLayer.getStartPosition().x - minPositionXCenter;
		} else if (newPositionX > maxPositionXCenter) {
			newPositionX = maxPositionXCenter;
			newPositionMenuX = this.menuLayer.getStartPosition().x;
		}
		
		// Seta novas posicoes
		this.menuLayer.setPosition(newPositionMenuX, newPositionMenuY);
		this.setPosition(newPositionX, newPositionY);

	}

	protected abstract SparseArray<Wave> waves();

	protected abstract CGSize stageSize();

	protected abstract String stageBackgrounImage();
}

