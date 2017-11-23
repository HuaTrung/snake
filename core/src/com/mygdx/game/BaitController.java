package com.mygdx.game;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class BaitController {
	
	private Bait bait;
	private LinkedList<Vector2> snakeBody;
	private LinkedList<Vector2> snakeBodyAI;
	private boolean spawnIt = true;
	
	public Bait getBait() {
		return bait;
	}

	public BaitController(World world) {
		snakeBody = world.getSnake().getSnakeBody();
		snakeBodyAI=world.getSnakeAI().getSnakeBody();
		bait = world.getBait();
	}
	
	public void update() {
		if(bait.isSpawned()) {
			checkCollision();
		} else 
		if(spawnIt) {
			spawnTry();
		} 
	}
	int i=0;
	private void checkCollision() {	
		if((snakeBody.peekLast().x==bait.x)&&(snakeBody.peekLast().y==bait.y)) {
			spawnIt=true;
			bait.setSpawned(false);
			return;
		}
		if((snakeBodyAI.peekLast().x==bait.x)&&(snakeBodyAI.peekLast().y==bait.y)) {
			spawnIt=true;
			bait.setSpawned(false);
			return;
		}
	}
	
	private void spawnTry() {
		Random rn = new Random();
		int y=rn.nextInt(29);
		int x=rn.nextInt(29);	
		int pos=rn.nextInt(11);
		boolean stop=false;
		while(!stop) {
			stop=true;
			for(int i=0;i<snakeBody.size();i++) {
				if(snakeBody.get(i).x==y&&snakeBody.get(i).y==x) {		
					stop=false;
					break;
				}
			}
			for(int i=0;i<snakeBodyAI.size();i++) {
				if(snakeBodyAI.get(i).x==y&&snakeBodyAI.get(i).y==x) {		
					stop=false;
					break;
				}
			}
			if(!stop) {
				y=rn.nextInt(29);
				x=rn.nextInt(29);
			}
		}
		bait.pos=pos;
		bait.setLocation(y,x);
//		if(i==0) {
//			i++;
//			bait.setLocation(2,0);
//		}
		spawnIt = false;
		bait.setSpawned(true);
	}
}
