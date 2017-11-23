package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.SnakeAI.SnakeControllerAI;
import com.mygdx.SnakeAI.FindMinimumPath;;
//import com.mygdx.game.AISnake.*;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;
//import com.snake.game.*;

public class GameScreen implements Screen,InputProcessor  {
	private static final float CAM_WIDTH = Gdx.graphics.getWidth();
	private static final float CAM_HEIGHT = Gdx.graphics.getHeight();
	private enum GameState {
		GAME_READY, GAME_RUNNING, GAME_PAUSED, GAME_OVER;
	}
	
	private GameState STATE;
	private Renderer renderer;
	private SnakeController snakeController;
	private SnakeControllerAI snakeControllerAI;
	private BaitController baitController;
	private World world;
	private boolean resetGame;
	private float stateTime=0f;
	private Sound soundBait;
	@Override
	public void show() {
		this.world = new World(CAM_WIDTH, CAM_HEIGHT);
		this.renderer = new Renderer(world, CAM_WIDTH, CAM_HEIGHT);
		this.snakeController = new SnakeController(world, CAM_WIDTH, CAM_HEIGHT);
		this.snakeControllerAI=new SnakeControllerAI(world,CAM_WIDTH,CAM_HEIGHT);
		this.baitController = new BaitController(world);
		Gdx.input.setInputProcessor(this);
		STATE = GameState.GAME_READY;
		this.soundBait=Gdx.audio.newSound(Gdx.files.internal("data/eat.wav"));
		resetGame=true;
	}
	@Override
	public void render(float delta) {
		switch (STATE) {
		case GAME_READY:
			Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			renderer.renderReady();			
			break;
		case GAME_RUNNING:
			stateTime+=delta;
			Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
			baitController.update();
			stateTime+=delta;
			if(stateTime>0.1f) {
				stateTime=0;
				if(snakeController.update()) {
					STATE=GameState.GAME_OVER;
				}
				else {
					if(snakeController.collidedWithBait())
						soundBait.play();
				}
				
				if(snakeControllerAI.update(FindMinimumPath.getDir(snakeControllerAI.getSnake(), baitController.getBait(),resetGame))) {
					soundBait.play();
				}
			}		
			if(STATE!=GameState.GAME_OVER)
				renderer.render();
			if(snakeControllerAI.getSnake().getSnakeBody().size()==13||snakeController.getSnake().getSnakeBody().size()==8)
				STATE=GameState.GAME_OVER;
			resetGame=false;
			break;
		case GAME_PAUSED:
			Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			renderer.render();
			break;
		case GAME_OVER:
			Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			renderer.renderFail();
			break;
		}
	}
	private void reset() {
		this.world = new World(CAM_WIDTH, CAM_HEIGHT);
		this.snakeController = new SnakeController(world, CAM_WIDTH, CAM_HEIGHT);
		this.baitController = new BaitController(world);
		this.snakeControllerAI=new SnakeControllerAI(world, CAM_WIDTH, CAM_HEIGHT);
		this.stateTime=0f;
		this.renderer = new Renderer(world, CAM_WIDTH, CAM_HEIGHT);
		STATE = GameState.GAME_READY;
		FindMinimumPath.resultReturn.clear();
	}
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		soundBait.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.W || keycode == Keys.UP) 
			snakeController.upPressed();
		if(keycode == Keys.S || keycode == Keys.DOWN) 
			snakeController.downPressed();
		if(keycode == Keys.A || keycode == Keys.LEFT)
			snakeController.leftPressed();
		if(keycode == Keys.D || keycode == Keys.RIGHT)
			snakeController.rightPressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.W || keycode == Keys.UP)
			snakeController.upReleased();
		if(keycode == Keys.S || keycode == Keys.DOWN)
			snakeController.downReleased();
		if(keycode == Keys.A || keycode == Keys.LEFT)
			snakeController.leftReleased();
		if(keycode == Keys.D || keycode == Keys.RIGHT)
			snakeController.rightReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(STATE == GameState.GAME_PAUSED || STATE == GameState.GAME_READY)
			STATE = GameState.GAME_RUNNING;
		else if(STATE == GameState.GAME_OVER) {
			reset();
		} else 			
			STATE = GameState.GAME_PAUSED;
			
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
}