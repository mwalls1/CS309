package com.mygdx.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Player {
    public int x,y;
    public int dx, dy;
    public int width, height;
    public double area;
    public int hp;
    public SpriteBatch batch;
    public Sprite sprite;
    public Texture knuckles;
    public Player(){
        hp = 100;
        width = 1150;
        height = 500;
        area = width*height;
        x = Gdx.graphics.getWidth()/2;
        y = Gdx.graphics.getHeight()/2;
        dx = 5;
        dy = 5;

    }
    public void render(ShapeRenderer shape, OrthographicCamera camera, Sprite sprite){
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        shape.circle(x, y, 0);
        shape.end();
        //sprite.setPosition(x, y);
        camera.position.x = x;
        camera.position.y = y;
        camera.update();

    }
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
        	if(x>960)
        		x -= dx;
    }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
        	if(x<2840)
        		x += dx;
    }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
        	if(y>540)
        		y -= dy;
    }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
        	if(y<1580)
        		y += dy;
    }
}
    public void setPos(int x, int y){
        x = this.x;
        y = this.y;
    }


    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}