package com.mygdx.gui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
/**
 * Class that controls logic and sprites for the main menu
 * @author noahthompson
 *
 */
public class WorldController {
	private static final String TAG = WorldController.class.getName();
	/**
	 * Array of sprites, boxes for main menu
	 */
	public Sprite[] menuSprites;
	
	/**
	 * Index of sprite on which actions are performed, i.e. selection
	 */
	public int selectedSprite = -1;
	
	/**
	 * y positions for sprite position of each box
	 */
	private float[] yPositions = {-0.2f, -1.3f, -2.4f};
	
	/**
	 * Constructor world controller
	 */
	public WorldController()
	{
		init();
	}
	
	
	/**
	 * Handles all individual initilizations in one method outside constructor so individual aspects can be reinitialized without altering others
	 */
	private void init()
	{
		initSprites();
	}
	/**
	 * Handles initilization for box sprites
	 */
	private void initSprites()
	{
		menuSprites = new Sprite[3];
		int width = 64;
		int height = 32;
		
		Pixmap pixmap = createProceduralPixmap(width, height);
		Texture texture = new Texture(pixmap);
		
		for (int i = 0; i < menuSprites.length; i++) //Iterates through all boxes for inits
		{
			Sprite spr = new Sprite(texture);
			spr.setSize(2, 1); 
			spr.setOrigin(spr.getWidth()/2.0f, spr.getHeight()/2.0f); //Sets origin of sprite to its center
			spr.setPosition(0-spr.getWidth()/2, yPositions[i]); //Centers sprite on screen, uses yPositions
			menuSprites[i] = spr;
			
			
		}
		
	
	}
	/**
	 * Creates a pixmap object to create a sprite
	 * @param width width of pixmap
	 * @param height height of pixmap
	 * @return pixmap object for creating a sprite
	 */
	private Pixmap createProceduralPixmap(int width, int height)
	{
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888); //Creates a new pixmap to format with RGB and Alpha
		pixmap.setColor(198/255, 198/255, 198/255, 0.5f);
		pixmap.fill(); //Fill the shape
		pixmap.setColor(100/255, 100/255, 95/255, 1);
		pixmap.drawRectangle(0, 0, width, height);

		return pixmap;
	}
	/**
	 * Updates game each frame; updates menu and gets user input
	 * @param deltaTime time between frames, calculated by LibGDX's delta function
	 */
	public void update(float deltaTime)
	{
		handleInput(deltaTime);
	}
	
	/**
	 * Checks for user input and deals with relevant input accordingly
	 * @param deltaTime
	 */
	private void handleInput(float deltaTime)
	{
		if (Gdx.app.getType() != ApplicationType.Desktop) return; //Returns if device is not a desktop, later implement alternatives to keyboard
		
		if (Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN)) //Moves highlighter down one box if applicable
		{
			if(selectedSprite >=2) return;
			selectedSprite++;
			initSprites();
			menuSprites[selectedSprite].setColor(1, 1, 1, 0.5f);
		}
		
		else if (Gdx.input.isKeyJustPressed(Keys.DPAD_UP)) //Moves highlighter up one box if applicable
		{
			if (selectedSprite <= 0) return;
			selectedSprite--;
			initSprites();
			menuSprites[selectedSprite].setColor(1, 1, 1, 0.5f);
			
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ENTER)) //Selects option highlighted
		{
			if (selectedSprite == 0)  System.out.println("Option 0 selected");
			else if (selectedSprite == 1) System.out.println("Option 1 selected");
			else if (selectedSprite == 2) System.out.println("Option 2 selected");
		}
	}
	
	
	
}
