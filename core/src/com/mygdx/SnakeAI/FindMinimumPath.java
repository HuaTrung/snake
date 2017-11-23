package com.mygdx.SnakeAI;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Bait;
import com.mygdx.game.Snake;
import com.mygdx.game.Snake.Dir;

public class FindMinimumPath {
	static class pair{
		int x;
		int y;
		public pair(int _x,int _y) {
			x=_x;
			y=_y;
		}
	}
	static Vector<pair> map2Dto1D;
	static Vector<Vector<Integer>> listAdjacency;
	static boolean[] listVertexCantAccess;
	static int[] trace;
	
	public static LinkedList<pair> solve(Snake snakeAI,Bait bait){
		LinkedList<Vector2> snakeBodyAI=snakeAI.getSnakeBody();
		LinkedList<pair> resultReturn = new LinkedList<pair>();	
		map2Dto1D=new Vector<pair>(900);  
		listAdjacency=new Vector<Vector<Integer>>(30);
		listVertexCantAccess=new boolean[900];
		trace=new int[900];
		
		//init
		for(int i=0;i<900;i++) {
			listVertexCantAccess[i]=false;
		}
		
		for(int i=0;i<30;i++) {
			for(int j=0;j<30;j++) {
				map2Dto1D.add(new pair(i,j));
			}
		}
		
		// init list adjacency
		for(int i=0;i<30;i++) {
			for(int j=0;j<30;j++) {
				Vector<Integer> temp=new Vector<Integer>();
				if((i-1)>=0)
					temp.add((i-1)*30+j);
				if((j-1)>=0)
					temp.add(i*30+j-1);
				if((i+1)<30)
					temp.add((i+1)*30+j);
				if((j+1)<30)
					temp.add(i*30+j+1);
				listAdjacency.add(temp);
			}
		}
		
		for(int i=0;i<snakeBodyAI.size();i++) {
			listVertexCantAccess[(int) (snakeBodyAI.get(i).x*30+snakeBodyAI.get(i).y)]=true;
		}
		
		listVertexCantAccess[bait.x*30+bait.y]=true;
		
		int start=(int) (snakeBodyAI.peekLast().x*30+snakeBodyAI.peekLast().y);
		int end=bait.x*30+bait.y;
		int conVertex=start;
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.add(start);
		trace[start]=-1;
		boolean stop=false;
		while(!stop&&queue.size()!=0) {
			conVertex=queue.poll();
			for(int i=0;i<listAdjacency.get(conVertex).size();i++) {
				if(listAdjacency.get(conVertex).get(i)==end) {
					trace[listAdjacency.get(conVertex).get(i)]=conVertex;
					stop=true;
					break;
				}
				else {
					if(!listVertexCantAccess[listAdjacency.get(conVertex).get(i)]) {
						trace[listAdjacency.get(conVertex).get(i)]=conVertex;
						queue.add(listAdjacency.get(conVertex).get(i));
						listVertexCantAccess[listAdjacency.get(conVertex).get(i)]=true;
					}
				}
			}
		}
		while(end!=start) {
			resultReturn.offer(map2Dto1D.get(end));
			end=trace[end];
		}
		resultReturn.offer(map2Dto1D.get(start));
		return resultReturn;
	}

	private static Dir takeDir(pair head, pair nextmove) {
		if(head.x>nextmove.x)
			return Dir.UP;
		if(head.x<nextmove.x)
			return Dir.DOWN;
		if(head.y<nextmove.y)
			return Dir.RIGHT;
		return Dir.LEFT;
	}
	public static LinkedList<pair> resultReturn = new LinkedList<pair>();
	public static Dir getDir(Snake snakeAI,Bait bait,boolean reset) {	
		if(resultReturn.size()<2||reset)
			resultReturn=solve(snakeAI,bait);			
		Dir returnDir=	takeDir(resultReturn.get(resultReturn.size()-1),resultReturn.get(resultReturn.size()-2));	
		resultReturn.removeLast();
		return returnDir;
	}
}
