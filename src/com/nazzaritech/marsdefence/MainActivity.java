package com.nazzaritech.marsdefence;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.nazzaritech.marsdefence.layer.InitialLayer;
import com.nazzaritech.marsdefence.layer.MenuLayer;
import com.nazzaritech.marsdefence.vo.SceneTag;

public class MainActivity extends Activity {
	protected CCGLSurfaceView _glSurfaceView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        _glSurfaceView = new CCGLSurfaceView(this);
        
        setContentView(_glSurfaceView);
    }

    @Override
    public void onStart()
    {
        super.onStart();
     
        CCDirector.sharedDirector().attachInView(_glSurfaceView);
        CCDirector.sharedDirector().setLandscape(false);
        CCDirector.sharedDirector().setDisplayFPS(true);
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

        CCScene scene = InitialLayer.scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        CCDirector.sharedDirector().pause();
    }
     
    @Override
    public void onResume()
    {
        super.onResume();
        CCDirector.sharedDirector().resume();
    }
     
    @Override
    public void onStop()
    {
        super.onStop();
        CCDirector.sharedDirector().end();
    }

    @Override
    public void onBackPressed() {

    	CCScene scene = CCDirector.sharedDirector().getRunningScene();

    	// if press back, and running scene is a stage
    	if (SceneTag.CHAPTER_ONE_STAGE_ONE == scene.getTag()) {
    		scene.cleanup();
    		scene.removeSelf();
    		// go to menu
    		scene = MenuLayer.scene();
            CCDirector.sharedDirector().replaceScene(scene);
    	}

    }
}
