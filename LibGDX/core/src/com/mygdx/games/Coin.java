package com.mygdx.games;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Coin {
	public float x, y;
	public int dx, dy;
	public float width, height, endX, endY;
	public Sprite sprite;
	public float elapsed;
	private Animation<TextureRegion> coin;
	private TextureAtlas coins;
	private boolean active;
	private Polygon poly1;
	private float[] vertices = new float[8];
	private float respawn = 0;

	public Coin(float spawnX, float spawnY) {
		sprite = new Sprite();
		elapsed = 0;
		x = spawnX;
		y = spawnY;
		width = sprite.getWidth();
		height = sprite.getHeight();
		endX = x + width;
		endY = y + height;
		active = true;
		coins = new TextureAtlas(Gdx.files.internal("coins.atlas"));
		coin = new Animation<TextureRegion>(1 / 5f, coins.getRegions());
		poly1 = new Polygon();
		poly1.setVertices(vertices);
		poly1.setOrigin((x + endX) / 2, (y + endY) / 2);
		vertices[0] = x + 5;
		vertices[1] = (y * 2 + height) / 2 + 2;
		vertices[2] = x + width;
		vertices[3] = (y * 2 + height) / 2 + 2;
		vertices[4] = x + width;
		vertices[5] = y + height;
		vertices[6] = x + 5;
		vertices[7] = y + height;

	}

	public void render(ShapeRenderer shape, OrthographicCamera camera, SpriteBatch batch) {
		elapsed += Gdx.graphics.getDeltaTime();
		if (active)
			batch.draw(coin.getKeyFrame(elapsed, true), x, y);
	}

	public void checkCollision(Player player) {
		if (active) {
			int midX = (player.x * 2 + player.width) / 2;
			int midY = (player.y * 2 + player.height);
			Polygon play = new Polygon();
			float[] vert = new float[8];
			vert[0] = player.x + 2;
			vert[1] = player.y + 1;
			vert[2] = player.x + player.width - 2;
			vert[3] = player.y + 1;
			vert[4] = player.x + player.width - 2;
			vert[5] = player.y + player.height;
			vert[6] = player.x + 2;
			vert[7] = player.y + player.height;
			play.setVertices(vert);
			play.setOrigin(midX, midY);
			if (Intersector.overlapConvexPolygons(poly1, play)) {
				player.numCoins++;
				active = false;
				respawn = elapsed;
				player.hp++;
			}
		}
	}

	public void setPos(int x1, int y1) {
		x = x1;
		y = y1;
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
