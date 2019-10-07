package com.mygdx.games;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.math.Vector3;

public class Player {
    public int x,y;
    public int dx, dy;
    public int width, height;
    public double area;
    public int hp;
    public SpriteBatch batch;
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
    public ArrayList<Bullet> shots = new ArrayList<Bullet>();
    public Player(){
    	sprite = new Sprite();
    	direction = 1;
    	isMoving = false;
    	isRunning = false;
        hp = 100;
        width = 14;
        height = 14;
        area = width*height;
        x = 22*16;
        y = 4*16;
        dx = 3;
        dy = 3;
        numBullets = 0;
        totalBullets = 0;
        sprite.setSize(width,height);
        midX = (2*x+width)/2;
        midY = (2*y+height)/2;
        dagger = new Texture(Gdx.files.internal("dagger.png"));
    }
    public void render(ShapeRenderer shape, OrthographicCamera camera){
        //sprite.setPosition(x, y);
        camera.position.x = x;
        camera.position.y = y;
        sprite.setX(x);
        sprite.setY(y);
        camera.update();

    }

	public void update(TiledMapTileLayer walls, ArrayList<Bullet> a, OrthographicCamera camera) {
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
				a.add(0,new Bullet(dagger, midX-3, midY-3, mouseCords.x, mouseCords.y));
				numBullets++;
				totalBullets++;
			}
			else if(totalBullets>2)
			{
				totalBullets--;
				a.remove(2);
			}
		}
			
	}
    public void setPos(int x1, int y1){
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
}