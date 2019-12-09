package Race;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Car {

	private float speed;
	private float acceleration;
	private Sprite sprite;
	private Texture texture;
	private float angle;
	private float deceleration;
	private float topSpeed;
	private OrthographicCamera camera;
	private double radians;
	private float speedX;
	private float speedY;
	public Car(String texturePath, AssetManager manager, OrthographicCamera camera)
	{
		this.camera = camera;
		texture = manager.get(texturePath, Texture.class);
		sprite = new Sprite(texture);
		speed = 0;
		acceleration = 0.1f;
		deceleration = 0.1f;
		angle = 90;
		topSpeed = 20;
	}
	
	public float getX()
	{
		return sprite.getX();
	}
	
	public float getY()
	{
		return sprite.getY();
	}
	
	public void move()
	{
		float radians;
		
		
		if (angle > 270) angle = -90;
		if (angle < -90) angle = 270;
		radians = angle * 0.0174533f;
		
		
		if (Gdx.input.isKeyPressed(Keys.W)) speed += acceleration;
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN) ) speed -= deceleration;
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && speed != 0) {
			sprite.rotate(-1);
			angle--;
		}
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && speed != 0)	
		{
			sprite.rotate(1);
			angle++;
		}
		if(speed < .05f && speed > -0.05f) speed = 0;
		if (speed > 0) speed -= 0.05f;
		if (speed < 0) speed += 0.05f;
		if (speed < -3f) speed = -3f;
		
		if (speed > topSpeed) speed = topSpeed;
		speedX = speed*(float)Math.cos(radians);
		speedY = speed * (float)Math.sin(radians);
		camera.translate(speedX, speedY);
		camera.update();
		
		
	}
	public float getSpeedX()
	{
		return speedX;
	}
	
	public float getSpeedY()
	{
		return speedY;
	}
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
	}
	
	public Point getTilePosition()
	{
		int xPos = (int)Math.floor(camera.position.x + speedX);
		int yPos = (int)Math.floor(camera.position.y + speedY);
		
		
		
		return new Point(xPos / 32, yPos / 32);
		
	}
	
	public void decSpeed(float newSpeed)
	{
		if (speed > 2.0f) speed -= newSpeed;
		if (speed < -0.5f) speed += newSpeed;
	}
	
	public void setSpeed(float newSpeed)
	{
		speed = newSpeed;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void moveAfterCollision()
	{
float radians;
		
		if (angle > 270) angle = -90;
		if (angle < -90) angle = 270;
		radians = angle * 0.0174533f;
		
		
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN) ) speed -= deceleration;
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && speed != 0) {
			sprite.rotate(-1);
			angle--;
		}
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && speed != 0)	
		{
			sprite.rotate(1);
			angle++;
		}
		if(speed < 0.05f && speed > -0.05f) speed = 0;
		if (speed > 0) speed -= 0.05f;
		if (speed < 0) speed += 0.05f;
		if (speed < -3f) speed = -3f;
		
		if (speed > topSpeed) speed = topSpeed;
		speedX = speed*(float)Math.cos(radians);
		speedY = speed * (float)Math.sin(radians);
		camera.translate(speedX, speedY);
		camera.update();
	}
}
