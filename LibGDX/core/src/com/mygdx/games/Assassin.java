package com.mygdx.games;

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
public class Assassin {
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
	private TextureAtlas iLeft;
	private TextureAtlas iRight;
    public Assassin(Texture texture, int x1, int y1, OrthographicCamera cam){
    	startTime = 0;
    	sprite = new Sprite(texture);
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
    public void render(SpriteBatch batch, Player player, TiledMapTileLayer walls, ArrayList<Bolt> shots, float time){
    	if(active) {
    		sprite.setX(x);
    		sprite.setY(y);
    		midX = (2*x + width)/2;
        	midY = (2*y + height)/2;
    		update(player, walls, shots, time, batch);
    		elapsed+=Gdx.graphics.getDeltaTime();
    	}
    	
    }

	public void update(Player player, TiledMapTileLayer walls, ArrayList<Bolt> shots, float time, SpriteBatch batch) 
	{
		if(active && Math.abs(Math.sqrt(Math.pow(player.midX-midX, 2) + Math.pow(player.midY-midY, 2)))<= 128)
		{
			if((startTime+1)<=time) {
				shots.add(0, new Bolt(bolt, midX, midY, player.midX, player.midY));
				shots.add(0, new Bolt(bolt, midX, midY, player.x-5, player.y-5));
				shots.add(0, new Bolt(bolt, midX, midY, player.x+player.width+5, player.y+player.height+5));
				startTime = time;
			}
			if(Math.abs(Math.sqrt(Math.pow(player.midX-midX, 2) + Math.pow(player.midY-midY, 2)))<= 96 && Math.abs(Math.sqrt(Math.pow(player.midX-midX, 2) + Math.pow(player.midY-midY, 2))) >= 32)
			{
				isMoving = true;
				if(x < player.x)
				{
						 if(walls.getCell((int)((x+width+1)/tileW), (int)(y/tileH))!=null)
							 x=x;
						 else if(walls.getCell((int)((x+width+1)/tileW), (int)((y+height)/tileH))!=null )
							 x=x;
						 else
						 {
							x+=1;
							direction = 1;
						 }

				}
				if(x > player.x)
				{
					if(walls.getCell((int)((x-1)/tileW), (int)(y/tileH))!=null)
						x=x;
					else if(walls.getCell((int)((x-1)/tileW), (int)((y+height)/tileH))!=null)
						x=x;
					else
					{
						x-=1;
						direction = 0;
					}
				}
				if(y < player.y)
				{
					if(walls.getCell((int)((x)/tileW), (int)((y+height+1)/tileH))!=null)
						y=y;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y+height+1)/tileH))!=null )
						y=y;
					else
					{
						y+=1;
					}
				}
				if(y > player.y)
				{
					if(walls.getCell((int)((x)/tileW), (int)((y-6)/tileH))!=null)
						y=y;
					else if(walls.getCell((int)((x+width)/tileW), (int)((y-6)/tileH))!=null)
						y=y;
					else
					{
						y-=1;
					}
				}
			}
			else
				isMoving = false;
			
		}
		if(active)
		{
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
