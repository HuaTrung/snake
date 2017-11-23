package com.mygdx.game;

public class Bait {
	
	private boolean spawned = false;
	public int x;
	public int y;
	public int pos;
	public Bait() {
		
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isSpawned() {
		return spawned;
	}
	
	public void setSpawned(boolean set) {
		spawned = set;
	}
}