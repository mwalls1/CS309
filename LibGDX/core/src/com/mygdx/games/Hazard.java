package com.mygdx.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
public class Hazard {
    float x;
	public float y;
    public float width, height;
    public Sprite sprite;
    public Texture hazardTexture;
    public ShapeRenderer shape;
	public OrthographicCamera camera;
	public float[] vertices1 = new float[8];
	public float[] vertices2 = new float[8];
	public Polygon poly1;
	public Polygon poly2;
	public float endX;
	public float endY;
	private float elapsed = 0;
	private float print = 0;
	private float numHP = .5f;
	private BitmapFont font = new BitmapFont();
    public Hazard(Texture texture, int x1, int y1, OrthographicCamera cam){
    	sprite = new Sprite(texture);
    	width = sprite.getWidth();
    	height = sprite.getHeight();
    	sprite.setCenter(x1, y1);
    	x = x1-width/2+2;
    	y = y1-height/2+2;
    	endX = x+width;
    	endY = y+height;
    	shape = new ShapeRenderer();
    	sprite.setPosition(x, y);
    	camera = cam;
    	vertices1[0] = x;
    	vertices1[1] = y;
    	vertices1[2] = x+width-5;
    	vertices1[3] = y;
    	vertices1[4] = x+width-5;
    	vertices1[5] = (y*2+height)/2-2;
    	vertices1[6] = x;
    	vertices1[7] = (y*2+height)/2-2;
    	poly1 = new Polygon();
    	poly1.setVertices(vertices1);
    	poly1.setOrigin((x+endX)/2, (y+endY)/2);
    	vertices2[0] = x+5;
    	vertices2[1] = (y*2+height)/2+2;
    	vertices2[2] = x+width;
    	vertices2[3] = (y*2+height)/2+2;
    	vertices2[4] = x+width;
    	vertices2[5] = y+height;
    	vertices2[6] = x+5;
    	vertices2[7] = y+height;
    	poly2 = new Polygon();
    	poly2.setVertices(vertices2);
    	poly2.setOrigin((x+endX)/2, (y+endY)/2);
    	
    }
    public void render(SpriteBatch batch){
    	elapsed+=Gdx.graphics.getDeltaTime();
    	sprite.draw(batch);
    	
    }
    public void drawRect()
    {
    }

	public void update() {
		sprite.rotate(-1);
		poly1.rotate(-1);
		poly2.rotate(-1);
	}
    public void setPos(int x1, int y1){
        x = x1;
        y = y1;
    }
    public void checkCollision(Player player, SpriteBatch batch)
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
    		player.hp -= .5f;
    		
    	}
    	else if(Intersector.overlapConvexPolygons(poly2, play))
    	{
    		player.hp -= .5f;
    	}
    	
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}
