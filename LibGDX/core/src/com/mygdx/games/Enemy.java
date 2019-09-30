package com.mygdx.games;

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
public class Enemy {
    public int x,y;
    public float width, height;
    public Sprite sprite;
    public Texture hazardTexture;
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
	public int tileH = 16;
	float[] vertices = new float[8];
    public Enemy(Texture texture, int x1, int y1, OrthographicCamera cam){
    	sprite = new Sprite(texture);
    	width = sprite.getWidth();
    	height = sprite.getHeight();
    	sprite.setCenter(width/2, height/2);
    	x = x1;
    	y = y1;
    	endX = x+width;
    	endY = y+height;
    	startX = x1;
    	startY = y1;
    	shape = new ShapeRenderer();
    	sprite.setPosition(x, y);
    	camera = cam;
    	midX = (x + endX)/2;
    	midY = (y + endY)/2;
    	vision = new Circle(midX, midY, 64);
    	
    }
    public void render(SpriteBatch batch, Player player, TiledMapTileLayer walls){
    	sprite.draw(batch);
    	sprite.setX(x);
    	sprite.setY(y);
    	update(player, walls);
    	
    }

	public void update(Player player, TiledMapTileLayer walls) 
	{
		if(Math.abs(Math.sqrt(Math.pow(player.midX-midX, 2) + Math.pow(player.midY-midY, 2)))<= 64)
		{
				if(x < player.x)
					x+=1;
				if(x > player.x)
					x-=1;
				if(y < player.y)
					y+=1;
				if(y > player.y)
					y-=1;
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
	}
	public void setPos(int x1, int y1){
        x = x1;
        y = y1;
    }
	public void drawVision()
	{
	}
    public void checkCollision(Player player)
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
    	if(Intersector.overlapConvexPolygons(poly1, play))
    	{
    		player.hp -= 1;
    	}
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

