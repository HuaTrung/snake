package com.mygdx.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Snake;
import com.mygdx.game.Snake.Dir;
import com.mygdx.game.World;
public class SnakeController {
	
	enum Keys { UP, DOWN, LEFT, RIGHT }
	
	static Map<Keys, Boolean> keys = new HashMap<SnakeController.Keys, Boolean>(); 
	static {
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
	}
	private Dir theLastTailDir;
	private Vector2 theLastTailEle;
	private Snake snake;
	private LinkedList<Vector2> snakeBody;
	private Bait bait;	
	private Dir moveDir;
	public SnakeController(World world, float width, float height) {
		this.snake = world.getSnake();
		this.snakeBody = snake.getSnakeBody();
		this.bait = world.getBait();
		this.moveDir=snake.getSnakeDir().peekLast();
		theLastTailEle=new Vector2();
	}
	
	public Snake getSnake() {
		return snake;
	}

	// Update snake with delta time ( time between 2 consecutive frames )
	public boolean update() {	
		processInput();
		submitDirectory(moveDir);
		move();
		return collidedWithSelf() || outOfBorder();
	}
	
	private boolean outOfBorder() {
		int[] tmp = new int[] {(int) snakeBody.peekLast().x,(int) snakeBody.peekLast().y};
		if(tmp[0] < 0 || tmp[1] < 0 ||tmp[0]>29||tmp[1]>29/*|| tmp.x + tmp.width >= worldWidth || tmp.y + tmp.height >= worldHeight*/)
			return true;
		return false;
	}
	
	private boolean collidedWithSelf() {
		for(int i = 0; i < snakeBody.size() - 2; i++) {
			if((snakeBody.get(i).x==snakeBody.peekLast().x)&&(snakeBody.get(i).y==snakeBody.peekLast().y))
				return true;
		}
		return false;
	}
	
	public boolean collidedWithBait() {
		if(bait.isSpawned()) {
			if((snakeBody.peekLast().x==bait.x)&&(snakeBody.peekLast().y==bait.y)) {
				snake.setGrowing(true);
				return true;
			}
		}
		return false;
	}
	
	public void move() {
		//update Dir
		for(int i=0;i<snake.getSnakeBody().size();i++) {
			switch(snake.getSnakeDir().get(i)) {
			case UP:
				snake.getSnakeBody().get(i).x--;
				//System.out.println("UP ");
//				System.out.println("UP "+i+" "+snake.getSnakeBody().get(i).x+" "+snake.getSnakeBody().get(i).y);
				break;
			case DOWN:
				//System.out.println("DOWN ");
				snake.getSnakeBody().get(i).x++;
			//	System.out.println("DOWN "+i+" "+snake.getSnakeBody().get(i)[0]+" "+snake.getSnakeBody().get(i)[1]);
				break;
			case LEFT:
				//System.out.println("LEFT");
				snake.getSnakeBody().get(i).y--;
			//	System.out.println("LEFT "+i+" "+snake.getSnakeBody().get(i).x+" "+snake.getSnakeBody().get(i).y);
				break;
			case RIGHT:
				//System.out.println("RIGHT ");
				snake.getSnakeBody().get(i).y++;
			//	System.out.println("RIGHT "+i+" "+snake.getSnakeBody().get(i).x+" "+snake.getSnakeBody().get(i).y);
				break;
			}
		}		
		if(snake.isGrowing()) {
			snake.getSnakeDir().addFirst(theLastTailDir);
			snake.getSnakeBody().addFirst(new Vector2(theLastTailEle.x,theLastTailEle.y));
			snake.setGrowing(false);
		}
	}

	
	public void changeDirectory(Dir keyInput) {
		if(!snake.isMovingX() && (keyInput == Dir.LEFT || keyInput == Dir.RIGHT)) {
			snake.setMovingX(true);
			moveDir=keyInput;
		}
		 else if(snake.isMovingX() && (keyInput == Dir.UP || keyInput == Dir.DOWN)) {
			snake.setMovingX(false);
			moveDir=keyInput;		
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
	private void processInput() {	
		if(keys.get(Keys.UP)) {
			changeDirectory(Dir.UP);
			return;
		}
		if(keys.get(Keys.DOWN)) {
			changeDirectory(Dir.DOWN);
			return;
		}
		if(keys.get(Keys.LEFT)) {
			changeDirectory(Dir.LEFT);
			return;
		}
		if(keys.get(Keys.RIGHT)) {
			changeDirectory(Dir.RIGHT);
			return;
		}
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void upPressed() {
		//System.out.println("CHANGE UP TO TRUE");
		keys.get(keys.put(Keys.UP,true));
		//keys.put(Keys.UP,true);
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	public void upReleased() {
		//System.out.println("CHANGE UP TO FALSE");
		keys.get(keys.put(Keys.UP, false));
		//keys.put(Keys.UP, false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
		//keys.put(Keys.DOWN, true);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
		//keys.put(Keys.DOWN, false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
		//keys.put(Keys.LEFT, true);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
		//keys.put(Keys.LEFT, false);
	}

	@SuppressWarnings("unlikely-arg-type")
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
		//keys.put(Keys.RIGHT, true);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		//keys.put(Keys.RIGHT, true);
	}
	
}
