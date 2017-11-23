package com.mygdx.SnakeAI;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Bait;
import com.mygdx.game.Snake;
import com.mygdx.game.Snake.Dir;
import com.mygdx.game.World;
public class SnakeControllerAI {
	

	private Dir theLastTailDir;
	private Vector2 theLastTailEle;
	private Snake snake;
	private LinkedList<Vector2> snakeBody;
	private Bait bait;
	
	public SnakeControllerAI(World world, float width, float height) {
		this.snake = world.getSnakeAI();
		this.snakeBody = snake.getSnakeBody();
		this.bait = world.getBait();
		theLastTailEle=new Vector2(0,29);
		theLastTailDir=Dir.LEFT;
	}
	
	public Snake getSnake() {
		return snake;
	}

	// Update snake with delta time ( time between 2 consecutive frames )
	public boolean update(Dir dir) {	
		submitDirectory(dir);
		move(snake.getElementBody().width);	
		return collidedWithBait();
	}
	
//	private boolean outOfBorder() {
//		int[] tmp = new int[] {(int) snakeBody.peekLast().x,(int) snakeBody.peekLast().y};
//		if(tmp[0] < 0 || tmp[1] < 0 ||tmp[0]>29||tmp[1]>29/*|| tmp.x + tmp.width >= worldWidth || tmp.y + tmp.height >= worldHeight*/)
//			return true;
//		return false;
//	}
//	
//	private boolean collidedWithSelf() {
//		for(int i = 0; i < snakeBody.size() - 2; i++) {
//			if((snakeBody.get(i).x==snakeBody.peekLast().x)&&(snakeBody.get(i).y==snakeBody.peekLast().y))
//				return true;
//		}
//		return false;
//	}
	
	public boolean collidedWithBait() {
		if(bait.isSpawned()) {
			if((snakeBody.peekLast().x==bait.x)&&(snakeBody.peekLast().y==bait.y)) {
				snake.setGrowing(true);
				//snake.bootScore();
				return true;
			}
		}
		return false;
	}
	
	public void move(float delta) {
		//update Dir
		for(int i=0;i<snake.getSnakeBody().size();i++) {
			switch(snake.getSnakeDir().get(i)) {
			case UP:
				snake.getSnakeBody().get(i).x--;
				break;
			case DOWN:
				snake.getSnakeBody().get(i).x++;
				break;
			case LEFT:
				snake.getSnakeBody().get(i).y--;
				break;
			case RIGHT:
				snake.getSnakeBody().get(i).y++;
				break;
			}
		}		
		if(snake.isGrowing()) {
			snake.getSnakeDir().addFirst(theLastTailDir);
			snake.getSnakeBody().addFirst(new Vector2(theLastTailEle.x,theLastTailEle.y));
			snake.setGrowing(false);
		}
	}
	
	private void submitDirectory(Dir movDemand) {
		int i=0;
		theLastTailDir=snake.getSnakeDir().get(0);
		theLastTailEle.x=snakeBody.getFirst().x;
		theLastTailEle.y=snakeBody.getFirst().y;
		for(;i<(snake.getSnakeDir().size()-1);i++) {
			snake.getSnakeDir().set(i,snake.getSnakeDir().get(i+1));
		}
		snake.getSnakeDir().set(i,movDemand);

	}
}
