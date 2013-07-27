package com.nazzaritech.marsdefence.layer;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
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
import com.nazzaritech.marsdefence.utils.ScreemUtil;
import com.nazzaritech.marsdefence.vo.Bullet;
import com.nazzaritech.marsdefence.vo.BulletType;
import com.nazzaritech.marsdefence.vo.Monster;
import com.nazzaritech.marsdefence.vo.MonsterType;
import com.nazzaritech.marsdefence.vo.ScreenObject;
import com.nazzaritech.marsdefence.vo.TagTypes;
import com.nazzaritech.marsdefence.vo.Trooper;
import com.nazzaritech.marsdefence.vo.TrooperType;

/**
 * Stage Layer
 * Abstract class that have all generic methods to stages
 * Must be extended by all Stages classes
 * 
 * @author Nazzaritech Games
 */
public abstract class StageLayer extends CCLayer {

	private ArrayList<CCSprite> _monsterList;
	private ArrayList<CCSprite> _bulletList;
	private ArrayList<CCSprite> _trooperList;
	private ArrayList<CGRect> _roteAreaList;
	private float locationTouchMoveX = 0;
	private float locationTouchMoveY = 0;
	private float locationTouchMoveXStart = 0;
	private float locationTouchMoveYStart = 0;
	private boolean isTouchInMovement = false;
	private boolean isTouchInMovementStart = false;
	private boolean enterMoveMethod = false;
    Context _context;

    // Objetos de Tela
    protected ScreenObject _goldBar;
    protected ScreenObject _silverBar;

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
	
	protected CCSprite _bt_radio;

	protected CCLabel _ptVidas;
	protected int _numVidas;
	protected float _posVidas_X;
	protected float _posVidas_y;
	
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
	 * Construtor
	 * 
	 * @param color
	 */
	protected StageLayer() {
		super();
		setContentSize(stageSize());

		this.minPositionXCenter = - (getContentSize().width - ScreemUtil.widthScreem());
		this.maxPositionXCenter = 0;

		this.minPositionYCenter = - (getContentSize().height - ScreemUtil.heightScreem());
		this.maxPositionYCenter = 0;

	    CCSprite background = CCSprite.sprite(stageBackgrounImage());
	    background.setPosition(getContentSize().width / 2f, getContentSize().height/ 2f);
	    background.setVertexZ(1);
	    addChild(background);

		_goldBar = new ScreenObject();
		_goldBar.setSprite(CCSprite.sprite("bar_goldcoin.png"));
	    _goldBar.setStartPosition(CGPoint.ccp(100,ScreemUtil.heightScreem() - 40));
	    _goldBar.getSprite().setPosition(CGPoint.ccp(100,ScreemUtil.heightScreem() - 40));
	    _goldBar.getSprite().setVertexZ(1);
	    addChild(_goldBar.getSprite());

		_silverBar = new ScreenObject();
		_silverBar.setSprite(CCSprite.sprite("bar_silvercoin.png"));
	    _silverBar.setStartPosition(CGPoint.ccp(240,ScreemUtil.heightScreem() - 40));
	    _silverBar.getSprite().setPosition(CGPoint.ccp(240,ScreemUtil.heightScreem() - 40));
	    _silverBar.getSprite().setVertexZ(1);
	    addChild(_silverBar.getSprite());

		this._context = CCDirector.sharedDirector().getActivity();

		SoundEngine.sharedEngine().preloadEffect(_context, R.raw.tshot);
		SoundEngine.sharedEngine().preloadEffect(_context, R.raw.teleport);
		_monsterList = new ArrayList<CCSprite>();
		_bulletList = new ArrayList<CCSprite>();
		_trooperList = new ArrayList<CCSprite>();
		
		CCTextureCache.sharedTextureCache().addImage("alien_left_1.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_2.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_3.png");
		CCTextureCache.sharedTextureCache().addImage("alien_left_4.png");

	}

	/**
	 * Metodo que adiciona um monstro 
	 * 
	 * @param monsterType   - Tipo do Monstro
	 * @param monsterSpeed  - Velocidade do Monstro
	 */
	protected void addMonsterAnimate(MonsterType monsterType) {
		
		// Busca rota do monstro
		SparseArray<CGPoint> rote = monsterRote();

		// Cria um objeto MONSTRO e um ojbeto SPRITE
		Monster monster = new Monster(
				monsterType, // tipo monstro
				rote.get(1).x,  // posicao inicial X
				rote.get(1).y); // posicao inicial Y
		
        // Instancia o 'Sprite' do monstro
		CCSprite monsterSprite = CCSprite.sprite(monsterType.getImage());
		monsterSprite.setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
		monsterSprite.setTag(TagTypes.TAG_MONSTER);
		monsterSprite.setVertexZ(monster.getMonsterType().getzIndex());

		// MONSTRO ANIMADO
		CCAnimation monsterAnimations = CCAnimation.animation("ALIEN", 2 / 20f);
		monsterAnimations.addFrame("alien_left_1.png");
        monsterAnimations.addFrame("alien_left_2.png");
        monsterAnimations.addFrame("alien_left_3.png");
        monsterAnimations.addFrame("alien_left_4.png");

        CCIntervalAction monsterAnimation = CCAnimate.action(monsterAnimations, true);

		addChild(monsterSprite);
		
	    // Busca informacoes da rota do monstro
		CCFiniteTimeAction firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
		CCFiniteTimeAction[] roteList = new CCFiniteTimeAction[rote.size()-1];
		int j = 0;
		for (int i = 3; i <= rote.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
		}

		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterPass");

		CCRepeatForever.action(CCIntervalAction.action(1f));
		// Mover monstro
	    CCSequence actions = CCSequence.actions(firstRote, roteList);
	    monsterSprite.runAction(CCRepeatForever.action(monsterAnimation));
	    monsterSprite.runAction(actions);
	    
	    // Adiciona barra de vida vida ao monstro
		monster.getBarSprite().setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
		monster.getBarSprite().setVertexZ(2);
		monster.getBarSprite().setTag(TagTypes.TAG_MONSTER_LIFE);
		addChild(monster.getBarSprite());

		firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
		roteList = new CCFiniteTimeAction[rote.size()-1];
		j = 0;
		for (int i = 3; i <= rote.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
		}
		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterLivePass");

	    actions = CCSequence.actions(firstRote, roteList);
	    monster.getBarSprite().runAction(actions);

		// Adiciona monstro na lista
		monsterSprite.setUserData(monster);
	    _monsterList.add(monsterSprite);

	}

	/**
	 * Metodo que adiciona um monstro 
	 * 
	 * @param monsterType   - Tipo do Monstro
	 * @param monsterSpeed  - Velocidade do Monstro
	 */
	protected void addMonster(MonsterType monsterType) {
		
		// Busca rota do monstro
		SparseArray<CGPoint> rote = monsterRote();

		// Cria um objeto MONSTRO e um ojbeto SPRITE
		Monster monster = new Monster(
				monsterType, // tipo monstro
				rote.get(1).x,  // posicao inicial X
				rote.get(1).y); // posicao inicial Y
		
        // Instancia o 'Sprite' do monstro
		CCSprite monsterSprite = CCSprite.sprite(monsterType.getImage());
		monsterSprite.setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
		monsterSprite.setTag(TagTypes.TAG_MONSTER);
		monsterSprite.setVertexZ(monster.getMonsterType().getzIndex());

		// MONSTRO ANIMADO

		addChild(monsterSprite);
		
	    // Busca informacoes da rota do monstro
		CCFiniteTimeAction firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
		CCFiniteTimeAction[] roteList = new CCFiniteTimeAction[rote.size()-1];
		int j = 0;
		for (int i = 3; i <= rote.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
		}

		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterPass");

		CCRepeatForever.action(CCIntervalAction.action(1f));
		// Mover monstro
	    CCSequence actions = CCSequence.actions(firstRote, roteList);
	    monsterSprite.runAction(actions);
	    
	    // Adiciona barra de vida vida ao monstro
		monster.getBarSprite().setPosition(CGPoint.ccp(rote.get(1).x,rote.get(1).y));
		monster.getBarSprite().setVertexZ(2);
		monster.getBarSprite().setTag(TagTypes.TAG_MONSTER_LIFE);
		addChild(monster.getBarSprite());

		firstRote = CCMoveTo.action(monster.getRealSpeed(rote.get(1),rote.get(2)),rote.get(2));
		roteList = new CCFiniteTimeAction[rote.size()-1];
		j = 0;
		for (int i = 3; i <= rote.size(); i++) {
			roteList[j++] = CCMoveTo.action(monster.getRealSpeed(rote.get(i-1),rote.get(i)), rote.get(i));
		}
		// Apos rotas, executa metodo de monstros finalizados
		roteList[j] = CCCallFuncN.action(this, "monsterLivePass");

	    actions = CCSequence.actions(firstRote, roteList);
	    monster.getBarSprite().runAction(actions);

		// Adiciona monstro na lista
		monsterSprite.setUserData(monster);
	    _monsterList.add(monsterSprite);

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
	    _bulletList.add(bulletSprite);
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
			
			float widthAntigo = monster.getBarSprite().getContentSize().width;
			float widthNovo = monster.getBarSprite().getContentSize().width * percentualVida;
			float widthRemovido = widthAntigo - widthNovo;
			
			CGRect newRect = CGRect.make(0, 0, 
					widthNovo,
					monster.getBarSprite().getContentSize().height);
			
			// diminui tamanho da barra
			
			if (percentualVida <= 0.80f) {
				monster.getBarSprite().setTexture(
						CCTextureCache.sharedTextureCache().addImage(
								monster.getMonsterType().getBarName() + "_red.png"));
			}

			monster.getBarSprite().setTextureRect(newRect);
			monster.getBarSprite().setPosition(
					monster.getBarSprite().getPosition().x - widthRemovido, 
					monster.getBarSprite().getPosition().y);
			
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
		getMonsterList().remove(monster);
		removeChild(((Monster)monster.getUserData()).getBarSprite(), true);
		removeChild(monster, true);
		monster = null;
	}

	protected void removeBullet(CCSprite bullet) {
		getBulletList().remove(bullet);
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
	public abstract void monsterLivePass(Object sender);

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
	
	protected List<CCSprite> getMonsterList() {
		return _monsterList;
	}
	
	protected List<CCSprite> getBulletList() {
		return _bulletList;
	}
	
	protected List<CCSprite> getTrooperList() {
		return _trooperList;
	}

	private boolean isTrooperOutsideRote(CGPoint trooperPoint) {
		if (_roteAreaList == null) {
			_roteAreaList = new ArrayList<CGRect>();
			
			SparseArray<CGPoint> roteList = monsterRote();

			for (int i = 1; i <= roteList.size(); i++) {
				
				
				// Ponto um
				
				// Ponto dois 
				
				
//				CGRect rectangle = CGRect.make(roteList.get(i).x, CGSize.make(roteList.get(i+1).x, roteList.get(i+1).y));

				
//				_roteAreaList.add(CGRect.);
				
//				array_type array_element = array[i];
				
			}
			
		}
		
		monsterRote();
		
		
		
		
		return false;
	}
	
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		this.touchEvent = event;
		touchTime = 0.0f;
		touchHold = true;
	    
    	// Se clicou em RADIO
    	if (isInsideArea(_bt_radio, event.getX(), event.getY())) {
    		actionOn = true;
    		Integer opacity = null;
    				
           	for (CCSprite trooperSprite : _trooperList) {
           		Trooper trooper = (Trooper) trooperSprite.getUserData();
           		if (opacity == null) {
           			opacity = trooper.radioSprite.getOpacity();
           		}

           		if (opacity == 0) {
           			trooper.radioSprite.setOpacity(255);
           		} else {
           			trooper.radioSprite.setOpacity(0);
           		}
			}
    	}

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
		
		float distanceMovedX = (x - locationTouchMoveX)/3f;
		float distanceMovedY = (locationTouchMoveY - y)/3f;
		
		float newPositionX = getPosition().x + distanceMovedX;
		float newPositionY = getPosition().y + distanceMovedY;

		changeToNewPosition(_goldBar,x, y,newPositionX, newPositionY);
		changeToNewPosition(_silverBar,x, y,newPositionX, newPositionY);

		if (newPositionY < minPositionYCenter) {
			newPositionY = minPositionYCenter;
		} else if (newPositionY > maxPositionYCenter) {
			newPositionY = maxPositionYCenter;
		}

		if (newPositionX < minPositionXCenter) {
			newPositionX = minPositionXCenter;
		} else if (newPositionX > maxPositionXCenter) {
			newPositionX = maxPositionXCenter;
		}

		setPosition(newPositionX, newPositionY);

	}

	private void changeToNewPosition(ScreenObject screenObject, float x, float y, 
			float newPositionX, float newPositionY) {
		
		float distanceMovedX = (x - locationTouchMoveX)/3f;
		float distanceMovedY = (locationTouchMoveY - y)/3f;
		
		float newPositionXScreen = screenObject.getSprite().getPosition().x - distanceMovedX;
		float newPositionYScreen = screenObject.getSprite().getPosition().y - distanceMovedY;

		if (newPositionY < minPositionYCenter) {
			newPositionYScreen = screenObject.getStartPosition().y - minPositionYCenter;
		} else if (newPositionY > maxPositionYCenter) {
			newPositionYScreen = screenObject.getStartPosition().y;
		}

		if (newPositionX < minPositionXCenter) {
			newPositionXScreen = screenObject.getStartPosition().x - minPositionXCenter;
		} else if (newPositionX > maxPositionXCenter) {
			newPositionXScreen = screenObject.getStartPosition().x;
		}

		screenObject.getSprite().setPosition(newPositionXScreen, newPositionYScreen);

	}
	
	protected abstract CGSize stageSize();

	protected abstract String stageBackgrounImage();
}

