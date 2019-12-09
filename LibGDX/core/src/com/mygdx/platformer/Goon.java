package com.mygdx.platformer;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.games.Player;
public class Goon {
    public int x,y;
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
	public int direction;
	public boolean isMoving;
	private Animation<TextureRegion> runLeft;
	private Animation<TextureRegion> runRight;
	private Animation<TextureRegion> idleLeft;
	private Animation<TextureRegion> idleRight;
	private TextureAtlas rLeft;
	private TextureAtlas rRight;
	public float elapsed;
	private boolean left = true;
	private Texture zom = new Texture(Gdx.files.internal("zombie_idle_anim_f0.png"));
	private TextureAtlas iLeft;
	private TextureAtlas iRight;
    public Goon(int x1, int y1, OrthographicCamera cam){
    	startTime = 0;
    	sprite = new Sprite(zom);
    	width = sprite.getWidth();
    	height = sprite.getHeight();
    	sprite.setCenter(width/2, height/2);
    	x = x1;
    	isMoving = false;
    	y = y1;
    	endX = x+width;
    	endY = y+height;
    	active = true;
    	startX = x1;
    	direction = 0;
    	startY = y1;
    	shape = new ShapeRenderer();
    	sprite.setPosition(x, y);
    	camera = cam;
    	midX = (2*x + width)/2;
    	midY = (2*y + height)/2;
    	vertices[0] = x+3;
    	vertices[1] = y;
    	vertices[2] = x+width-3;
    	vertices[3] = y;
    	vertices[4] = x+width-3;
    	vertices[5] = y+height;
    	vertices[6] = x+3;
    	vertices[7] = y+height;
    	poly1 = new Polygon();
    	poly1.setVertices(vertices);
    	poly1.setOrigin(midX, midY);
    	vision = new Circle(midX, midY, 64);
    	bolt = new Texture(Gdx.files.internal("dagger.png"));
		rLeft = new TextureAtlas(Gdx.files.internal("asnRunLeft.atlas"));
		rRight = new TextureAtlas(Gdx.files.internal("asnRunRight.atlas"));
		iRight = new TextureAtlas(Gdx.files.internal("asnIdleRight.atlas"));
		iLeft = new TextureAtlas(Gdx.files.internal("asnIdleLeft.atlas"));
		runLeft = new Animation<TextureRegion>(1/10f, rLeft.getRegions());
		runRight = new Animation<TextureRegion>(1/10f, rRight.getRegions());
		idleLeft = new Animation<TextureRegion>(1/5f, iLeft.getRegions());
		idleRight = new Animation<TextureRegion>(1/5f, iRight.getRegions());
    	
    }
    public void render(SpriteBatch batch, Character player, TiledMapTileLayer walls, float time){
    	if(active) {
    		sprite.setX(x);
    		sprite.setY(y);
    		midX = (2*x + width)/2;
        	midY = (2*y + height)/2;
    		elapsed+=Gdx.graphics.getDeltaTime();
    		update(player, walls, time, batch);
    	}
    	
    }

	private void update(Character player, TiledMapTileLayer walls, float time, SpriteBatch batch) {
		if (active) {
			isMoving = true;
			if (left) {
				if (walls.getCell((int) ((x - 1) / tileW), (int) (y / tileH)) == null)
					left = !left;
				else {
					x -= 1;
					direction = 0;
				}

			}
			if (!left) {
				if (walls.getCell((int) ((x +width+ 1) / tileW), (int) (y / tileH)) == null)
					left = !left;
				else {
					x += 1;
					;
					direction = 1;
				}
			}
				if (direction == 0)
					batch.draw(runLeft.getKeyFrame(elapsed, true), x, y);
				else
					batch.draw(runRight.getKeyFrame(elapsed, true), x, y);
			}
		midX = (2*x + width)/2;
    	midY = (2*y + height)/2;
    	vertices[0] = x+3;
    	vertices[1] = y;
    	vertices[2] = x+width-3;
    	vertices[3] = y;
    	vertices[4] = x+width-3;
    	vertices[5] = y+height;
    	vertices[6] = x+3;
    	vertices[7] = y+height;
    	poly1 = new Polygon();
    	poly1.setVertices(vertices);
    	poly1.setOrigin(midX, midY);
		}
	public boolean checkCollision(Character player)
    {
    	int midX = (player.x*2+player.width)/2;
    	int midY = (player.y*2+player.height);
    	Polygon play = new Polygon();
    	float[] vert = new float[8];
    	vert[0] = player.x+2;
    	vert[1] = player.y+1;
    	vert[2] = player.x+player.width-2;
    	vert[3] = player.y+1;
    	vert[4] = player.x+player.width-2;
    	vert[5] = player.y + player.height;
    	vert[6] = player.x+2;
    	vert[7] = player.y+player.height;
    	play.setVertices(vert);
    	play.setOrigin(midX, midY);
    	if(active && Intersector.overlapConvexPolygons(poly1, play))
    	{
    		player.reset();
    		return true;
    	}
    	return false;
    }
	
	public void setPos(int x1, int y1){
        x = x1;
        y = y1;
    }
	
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
