package com.mygdx.game;
import java.util.LinkedList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Snake {
	private float snakeBodyLength;
	private boolean movingX=false;
	private boolean grows=false;
	//private int score=0;
	public enum Dir  { UP, DOWN, LEFT, RIGHT };
	
	private Rectangle elementBody=new Rectangle(10,10,20,20) ;
	//private LinkedList<Rectangle> snakeBody; // include head and tail
	private LinkedList<Vector2> snakeBody;
	private LinkedList<Dir> snakeDir;
	
	public Snake(float length,int[] pos) {
		this.snakeBodyLength=length;
		this.movingX=true;
		this.grows=false;
		
		this.snakeBody=new LinkedList<Vector2>();
		this.snakeDir=new LinkedList<Dir>();
		if(pos[0]==0) {
			for(int i=0;i<length;i++) {
				snakeBody.offer(new Vector2(0,pos[i]));
				this.snakeDir.offer(Dir.RIGHT);
			}
		}
		else {
			for(int i=0;i<length;i++) {
				snakeBody.offer(new Vector2(0,pos[i]));
				this.snakeDir.offer(Dir.LEFT);
			}
		}
		
	}

	public LinkedList<Vector2> getSnakeBody() {
		return snakeBody;
	}
	
	public LinkedList<Dir> getSnakeDir() {
		return snakeDir;
	}
	
	public float getBodyLength() {
		return snakeBodyLength;
	}
	
	
	public boolean isMovingX() {
		return movingX;
	}
	
	public void setMovingX(boolean set) {
		movingX = set;
	}
	
	public boolean isGrowing() {
		return grows;
	}
	
	public void setGrowing(boolean set) {
		grows = set;
	}
	
//	public int getScore() {
//		return score;
//	}
//	
//	public void bootScore() {
//		score++;
//		snakeBodyLength++;
//	}
	 public Rectangle getElementBody() {
		 return elementBody;
	 }
	 
}
