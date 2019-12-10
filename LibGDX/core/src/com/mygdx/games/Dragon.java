package com.mygdx.games;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Dragon {
	public int x, y;
	public float width, height;
	public Sprite sprite;
	public Texture bolt;
	public ShapeRenderer shape;
	public OrthographicCamera camera;
	public Polygon poly1;
	public int startX;
	public int startY;
	public float midX;
	public float midY;
	public float endX;
	public float endY;
	Circle vision;
	public int tileW = 16;
	public float startTime;
	public int tileH = 16;
	float[] vertices = new float[8];
	public boolean active;

	public Dragon(Texture texture, int x1, int y1, OrthographicCamera cam) {
		startTime = 0;
		sprite = new Sprite(texture);
		width = sprite.getWidth();
		height = sprite.getHeight();
		sprite.setCenter(width / 2, height / 2);
		x = x1;
		y = y1;
		endX = x + width;
		endY = y + height;
		active = true;
		startX = x1;
		startY = y1;
		shape = new ShapeRenderer();
		sprite.setPosition(x, y);
		camera = cam;
		midX = (2 * x + width) / 2;
		midY = (2 * y + height) / 2;
		vertices[0] = x + 3;
		vertices[1] = y;
		vertices[2] = x + width - 3;
		vertices[3] = y;
		vertices[4] = x + width - 3;
		vertices[5] = y + height;
		vertices[6] = x + 3;
		vertices[7] = y + height;
		poly1 = new Polygon();
		poly1.setVertices(vertices);
		poly1.setOrigin(midX, midY);
		vision = new Circle(midX, midY, 64);
		bolt = new Texture(Gdx.files.internal("bolt.png"));

	}

	public void render(SpriteBatch batch, Player player, TiledMapTileLayer walls, ArrayList<Bolt> shots, float time) {
		if (active) {
			sprite.draw(batch);
			sprite.setX(x);
			sprite.setY(y);
			update(player, walls, shots, time);
		}

	}

	public void update(Player player, TiledMapTileLayer walls, ArrayList<Bolt> shots, float time) {
		if (active && Math.abs(Math.sqrt(Math.pow(player.midX - midX, 2) + Math.pow(player.midY - midY, 2))) <= 128) {
			if ((startTime + 1) <= time) {
				shots.add(0, new Bolt(bolt, midX, midY, player.midX, player.midY));
				startTime = time;
			}
		}
	}

	public void setPos(int x1, int y1) {
		x = x1;
		y = y1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
