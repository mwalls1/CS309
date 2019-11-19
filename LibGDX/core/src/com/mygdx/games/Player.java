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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
    public int x,y;
    public int dx, dy;
    public int width, height;
    public double area;
    public float hp;
    public Sprite sprite;
    public Texture dagger;
    public boolean isMoving;
    public int direction;
    public float midX;
    public float midY;
    public boolean isRunning;
    public Vector3 mouseCords;
    public int totalBullets;
    public static int numBullets;
    public ArrayList<Knife> shots = new ArrayList<Knife>();
	private Animation<TextureRegion> runLeft;
	private Animation<TextureRegion> runRight;
	private Animation<TextureRegion> idleLeft;
	private Animation<TextureRegion> idleRight;
	private TextureAtlas rLeft;
	private TextureAtlas rRight;
	public float elapsed;
	private TextureAtlas iLeft;
	private TextureAtlas iRight;
	public int numCoins;
	public static int numEnemies = 45;
	private boolean willUpdate = false;
    public Player(int spawnX, int spawnY){
		numCoins = 0;
    	sprite = new Sprite();
    	direction = 1;
    	elapsed = 0;
    	isMoving = false;
    	isRunning = false;
        hp = 100;
        width = 14;
        height = 14;
        area = width*height;
        x = spawnX;
        y = spawnY;
        dx = 2;
        dy = 2;
        numBullets = 0;
        totalBullets = 0;
        sprite.setSize(width,height);
        midX = (2*x+width)/2;
        midY = (2*y+height)/2;
        dagger = new Texture(Gdx.files.internal("dagger.png"));
		rLeft = new TextureAtlas(Gdx.files.internal("runLeft.atlas"));
		rRight = new TextureAtlas(Gdx.files.internal("runRight.atlas"));
		iRight = new TextureAtlas(Gdx.files.internal("idleRight.atlas"));
		iLeft = new TextureAtlas(Gdx.files.internal("idleLeft.atlas"));
		runLeft = new Animation<TextureRegion>(1/10f, rLeft.getRegions());
		runRight = new Animation<TextureRegion>(1/10f, rRight.getRegions());
		idleLeft = new Animation<TextureRegion>(1/5f, iLeft.getRegions());
		idleRight = new Animation<TextureRegion>(1/5f, iRight.getRegions());
    }
    public void render(ShapeRenderer shape, OrthographicCamera camera){
        //sprite.setPosition(x, y);
        camera.position.x = x;
        camera.position.y = y;
        sprite.setX(x);
        sprite.setY(y);
        camera.update();
        elapsed+=Gdx.graphics.getDeltaTime();
    }
    public void render(){
        sprite.setX(x);
        sprite.setY(y);
        elapsed+=Gdx.graphics.getDeltaTime();
    }

	public void update(TiledMapTileLayer walls, ArrayList<Knife> a, OrthographicCamera camera, SpriteBatch batch) {
		float tileW = walls.getTileWidth();
		float tileH = walls.getTileHeight();
		if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S)
				|| Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(Gdx.input.isKeyPressed(Input.Keys.A))
			{
				for(int i = 0; i < dx; i ++)
				{
					if(walls.getCell((int)((x-1)/tileW), (int)(y/tileH))!=null)
						break;
					else if(walls.getCell((int)((x-1)/tileW), (int)((y+height)/tileH))!=null)
						break;
					else
					{
						x-=1;
						direction = 0;
						isMoving = true;
					}
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D))
			{
				for(int i = 0; i < dx; i ++) 
				{
					 if(walls.getCell((int)((x+width+1)/tileW), (int)(y/tileH))!=null)
						 break;
					 else if(walls.getCell((int)((x+width+1)/tileW), (int)((y+height)/tileH))!=null )
						 break;
					 else
					 {
						x+=1;
						direction = 1;
						isMoving = true;
					 }
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W))
			{
				for(int i = 0; i < dy; i ++) 
				{
					if(walls.getCell((int)((x)/tileW), (int)((y+height+1)/tileH))!=null)
						break;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y+height+1)/tileH))!=null )
						break;
					else
					{
						y+=1;
						isMoving = true;
					}
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S))
			{
				for(int i = 0; i < dy; i ++) 
				{
					if(walls.getCell((int)((x)/tileW), (int)((y-6)/tileH))!=null)
						break;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y-6)/tileH))!=null)
						break;
					else
					{
						isMoving = true;
						y-=1;
					}
				}
			}
	        midX = (2*x+width)/2;
	        midY = (2*y+height)/2;

		} else
			isMoving = false;
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			mouseCords = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(mouseCords);
			if(numBullets<2)
			{
				a.add(0,new Knife(dagger, midX-3, midY-3, mouseCords.x, mouseCords.y));
				numBullets++;
				totalBullets++;
			}
			else if(totalBullets>2)
			{
				totalBullets--;
				a.remove(2);
			}
		}
		if (!isMoving) {
			if (direction == 0) {
				batch.draw(idleLeft.getKeyFrame(elapsed, true), x, y);
			} else {
				batch.draw(idleRight.getKeyFrame(elapsed, true), x, y);
			}
		}
		if (isMoving) {
			if (direction == 0)
				batch.draw(runLeft.getKeyFrame(elapsed, true), x, y);
			else
				batch.draw(runRight.getKeyFrame(elapsed, true), x, y);
		}
			
	}
	public void update(SpriteBatch batch) {
		if (!isMoving) {
			if (direction == 0) {
				batch.draw(idleLeft.getKeyFrame(elapsed, true), x, y);
			} else {
				batch.draw(idleRight.getKeyFrame(elapsed, true), x, y);
			}
		}
		if (isMoving) {
			if (direction == 0)
				batch.draw(runLeft.getKeyFrame(elapsed, true), x, y);
			else
				batch.draw(runRight.getKeyFrame(elapsed, true), x, y);
		}
			
	}
    public void setPos(int x1, int y1){
    	this.willUpdate = false;
        x = x1;
        y = y1;
    }
    public Sprite getSprite()
    {
    	return sprite;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean setUpdate(boolean up) {
    	this.willUpdate = up;
    	return willUpdate;
    }
    public boolean willUpdate() { return willUpdate;}
}