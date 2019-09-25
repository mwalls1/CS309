package com.mygdx.games;

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

public class Player {
    public int x,y;
    public int dx, dy;
    public int width, height;
    public double area;
    public int hp;
    public SpriteBatch batch;
    public Sprite sprite;
    public Texture knuckles;
    public boolean isMoving;
    public int direction;
    public boolean isRunning;
    public Player(){
    	direction = 1;
    	isMoving = false;
    	isRunning = false;
        hp = 100;
        width = 16;
        height = 16;
        area = width*height;
        x = 32;
        y = 32;
        dx = 3;
        dy = 3;

    }
    public void render(ShapeRenderer shape, OrthographicCamera camera){
        //sprite.setPosition(x, y);
        camera.position.x = x;
        camera.position.y = y;
        camera.update();

    }

	public void update(TiledMapTileLayer walls, Sprite sprite) {
		float tileW = walls.getTileWidth();
		float tileH = walls.getTileHeight();
		if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S)
				|| Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(Gdx.input.isKeyPressed(Input.Keys.A))
			{
				for(int i = 0; i < dx; i ++)
				{
					if(walls.getCell((int)((x-1)/tileW), (int)(y/tileH))!=null && walls.getCell((int)((x-1)/tileW), (int)(y/tileH)).getTile().getProperties().containsKey("blocked"))
						break;
					else if(walls.getCell((int)((x-1)/tileW), (int)((y+height)/tileH))!=null && walls.getCell((int)((x-1)/tileW), (int)((y+height)/tileH)).getTile().getProperties().containsKey("blocked"))
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
					 if(walls.getCell((int)((x+width+1)/tileW), (int)(y/tileH))!=null && walls.getCell((int)((x+width+1)/tileW), (int)(y/tileH)).getTile().getProperties().containsKey("blocked"))
						 break;
					 else if(walls.getCell((int)((x+width+1)/tileW), (int)((y+height)/tileH))!=null && walls.getCell((int)((x+width+1)/tileW), (int)((y+height)/tileH)).getTile().getProperties().containsKey("blocked"))
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
					if(walls.getCell((int)((x)/tileW), (int)((y+height+1)/tileH))!=null && walls.getCell((int)((x)/tileW), (int)((y+height+1)/tileH)).getTile().getProperties().containsKey("blocked"))
						break;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y+height+1)/tileH))!=null && walls.getCell((int)((x+width)/tileW), (int)((y+height+1)/tileH)).getTile().getProperties().containsKey("blocked"))
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
					if(walls.getCell((int)((x)/tileW), (int)((y-6)/tileH))!=null && walls.getCell((int)((x)/tileW), (int)((y-6)/tileH)).getTile().getProperties().containsKey("blocked"))
						break;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y-6)/tileH))!=null && walls.getCell((int)((x+width)/tileW), (int)((y-6)/tileH)).getTile().getProperties().containsKey("blocked"))
						break;
					else
					{
						isMoving = true;
						y-=1;
					}
				}
			}

		} else
			isMoving = false;
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