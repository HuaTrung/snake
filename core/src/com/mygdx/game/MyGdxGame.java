package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.GameScreen;

public class MyGdxGame extends Game {
	public void create() {
		this.setScreen(new GameScreen());
	}
	public void render() {
		super.render(); //important!
	}
}
