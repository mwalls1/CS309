package com.mygdx.ColesGames;

import java.awt.Rectangle;

public class Zone {
	
	private Rectangle rect;
	private String tile;
	private boolean active;
	private int x, y, w, h;

	public Zone(int startX, int startY, int width, int height) {
		x = startX;
		y = startY;
		w = width;
		h = height;
		tile = "empty";
		active = false;
		rect = new Rectangle(startX, startY, width, height);
	}
	
	//returns whether the coordinates are located in the zone
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public String getTile() {
		return tile;
	}
	
	public void setTile(String tile) {
		this.tile = tile;
	}
	
	public void setActive(boolean a) {
		this.active = a;
	}
}
