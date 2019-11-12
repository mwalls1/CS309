package Race;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Car {

	private float speed;
	private float acceleration;
	private Sprite sprite;
	private Texture texture;
	private float angle;
	
	public Car(String texturePath, AssetManager manager)
	{
		texture = manager.get(texturePath, Texture.class);
		sprite = new Sprite(texture);
		speed = 0;
		acceleration = 0.1f;
		angle = 0;
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
		speed += acceleration;
		float xTrans = (float)Math.cos(angle) * speed;
		float yTrans = (float)Math.sin(angle) * speed;
		sprite.translate(xTrans, yTrans);
	}
}
