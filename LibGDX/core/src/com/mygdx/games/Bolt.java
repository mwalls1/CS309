package com.mygdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class Bolt {
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
	public float sX;
	public float sY;
	private float elapsed = 0;
	private float print = 0;
	private float numHP = 25f;
	private BitmapFont font = new BitmapFont();

	public Bolt(Texture texture, float x1, float y1, float x2, float y2) {
		pos1 = new Vector2(x1, y1);
		pos2 = new Vector2(x2, y2);
		dir = pos2.sub(pos1);
		dir.nor();
		sprite = new Sprite(texture);
		sprite.rotate(dir.angle());
		sprite.setX(x1);
		sprite.setY(y1);
		x = x1;
		y = y1;
		active = true;
		sX = x1;
		sY = y1;
	}

	public void render(SpriteBatch batch, TiledMapTileLayer walls, Player player) {
		sprite.draw(batch);
		update(walls, player, batch);
	}

	public void update(TiledMapTileLayer walls, Player player, SpriteBatch batch) {
		if (active && walls.getCell((int) ((x + dir.x * 3) / 16), (int) ((y + dir.y * 3) / 16)) == null)
			x += dir.x * 2;
		else {
			active = false;
		}
		if (active && walls.getCell((int) ((x + dir.x * 3) / 16), (int) ((y + dir.y * 3) / 16)) == null)
			y += dir.y * 2;
		else {
			active = false;
		}
		if (Intersector.overlaps(sprite.getBoundingRectangle(), player.sprite.getBoundingRectangle())) {
			player.hp -= 5;
			active = false;
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
