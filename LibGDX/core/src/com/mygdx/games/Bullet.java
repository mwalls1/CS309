package com.mygdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
	public float x, y;
	public int dx, dy;
	public int width, height;
	public double area;
	public int hp;
	public Sprite sprite;
	public Texture knuckles;
	public boolean isMoving;
	public int direction;
	public float midX;
	public float midY;
	public boolean isRunning;
	public Vector2 pos1;
	public Vector2 pos2;
	public Vector2 dir;
	public boolean active;

	public Bullet(Texture texture, float x1, float y1, float x2, float y2) {
		pos1 = new Vector2(x1, y1);
		pos2 = new Vector2(x2, y2);
		dir = pos2.sub(pos1);
		dir.nor();
		sprite = new Sprite(texture);
		sprite.rotate(dir.angle());
		System.out.println("X1, Y1, X2, Y2: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
		System.out.println("Direction Vector:" + dir.x + ", " + dir.y);
		sprite.setX(x1);
		sprite.setY(y1);
		x = x1;
		y = y1;
		active = true;
		// set the center of the sprite to be where the bullet starts
		// set the x and y of the sprite
	}

	public void render(SpriteBatch batch, TiledMapTileLayer walls, ArrayList<Enemy> zombies) {
		sprite.draw(batch);
		update(walls, zombies);
	}

	public void update(TiledMapTileLayer walls, ArrayList<Enemy> zombies) {
		if (walls.getCell((int) ((x + dir.x * 3) / 16), (int) ((y + dir.y * 3) / 16)) == null)
			x += dir.x * 3;
		else {
			active = false;
			Player.numBullets--;
		}
		if (walls.getCell((int) ((x + dir.x * 3) / 16), (int) ((y + dir.y * 3) / 16)) == null)
			y += dir.y * 3;
		else {
			active = false;
			Player.numBullets--;
		}
		sprite.setX(x);
		sprite.setY(y);

	}

	public Sprite getSprite() {
		return sprite;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}