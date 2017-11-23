package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class World {

	private Snake snake;
	private Snake snakeAI;
	private Bait bait;	
	private static final float snakeBodyLength = 3;
	
	private Rectangle[][] boardgame;
	public World(float CAM_WIDTH, float CAM_HEIGHT) {
		boardgame=new Rectangle[30][30];
		float x=50;
		float y=630;
		for(int i=0;i<30;i++) {
			for(int j=0;j<30;j++) {
				boardgame[i][j]=new Rectangle();
				boardgame[i][j].x=x;
				boardgame[i][j].y=y;
				boardgame[i][j].width=20;
				boardgame[i][j].height=20;
				x+=20;
			}
			x=50;
			y-=20;
		}
		int[] posSnake= {0,1,2};
		int[] posSnakeAI= {29,28,27};
		this.snake = new Snake(snakeBodyLength,posSnake);
		this.snakeAI = new Snake(snakeBodyLength,posSnakeAI);
		this.bait = new Bait();
	}
	
	public Snake getSnake() {
		return snake;
	}
	public Snake getSnakeAI() {
		return snakeAI;
	}
	public Rectangle[][] getBoardGame(){
		return boardgame;
	}
	public Bait getBait() {
		return bait;
	}
}