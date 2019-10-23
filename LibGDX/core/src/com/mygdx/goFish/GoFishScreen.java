package com.mygdx.goFish;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Cards.Card;
import com.mygdx.Cards.Deck;

import util.Constants;

public class GoFishScreen implements Screen{
private AssetManager manager;
private Game game;
private Sprite[] pondSprites;
private Sprite[] handSprites;
private Stage stage;
private Skin skin;
private SpriteBatch batch;
private String selectedRank;
private Player selectedPlayer;
private int deckIterator;
private Deck deck;
private TextField moveTextField;
private Player currentPlayer;
private Player p1;
private Player p2;
private Player p3;
private Player p4;
private String currentMove;
private GoFish cardGame;


	public GoFishScreen(Game game)
	{
		this.game = game;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     if (selectedPlayer != null && selectedRank != null) {
	    	 moveTextField.setText(currentPlayer.getName() + " asks " + selectedPlayer.getName() + " for " + selectedRank);
	    	 if(selectedPlayer.getName() == currentPlayer.getName()) moveTextField.setText("You can't ask yourself for a card!");
	     }
	     
	     if(currentPlayer.getName() != Constants.user) moveTextField.setText(currentMove);
	     batch.begin();
	     for (int i = 0; i<handSprites.length; i++) handSprites[i].draw(batch);
	     for (int i = 0; i<pondSprites.length; i++) pondSprites[i].draw(batch);
	     batch.end();
	     stage.act();
	     stage.draw();
	     if (Gdx.input.isKeyPressed(Keys.R)) create(); //Reset; for debugging only
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		create();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void create()
	{
		deckIterator = 0;
		manager = new AssetManager();
		deck = new Deck(1, manager);
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	    stage = new Stage();
		p1 = new Player(getUserName());
		p2 = new Player("p2");
		p3 = new Player("p3");
		p4 = new Player("p4");
		currentPlayer = p1;
		deal();
		generateHandSprites();
		generatePondSprites();
		
		p2.addCard(new Card("2", "clubs", manager));
		
		
		//Initialize buttons
		final TextButton button1 = new TextButton("Aces", skin, "default");
		final TextButton button2 = new TextButton("2's", skin, "default");
		final TextButton button3 = new TextButton("3's", skin, "default");
		final TextButton button4 = new TextButton("4's", skin, "default");
		final TextButton button5 = new TextButton("5's", skin, "default");
		final TextButton button6 = new TextButton("6's", skin, "default");
		final TextButton button7 = new TextButton("7's", skin, "default");
		final TextButton button8 = new TextButton("8's", skin, "default");
		final TextButton button9 = new TextButton("9's", skin, "default");
		final TextButton button10 = new TextButton("10's", skin, "default");
		final TextButton button11 = new TextButton("J's", skin, "default");
		final TextButton button12 = new TextButton("Q's", skin, "default");
		final TextButton button13 = new TextButton("K's", skin, "default");
		final TextButton name1 = new TextButton(p1.getName(), skin, "default");
		final TextButton name2 = new TextButton(p2.getName(), skin, "default");
		final TextButton name3 = new TextButton(p3.getName(), skin, "default");
		final TextButton name4 = new TextButton(p4.getName(), skin, "default");
		final TextButton goButton = new TextButton("Go!", skin, "default");
		
		float offset = Gdx.graphics.getWidth()/20;
		float buttonWidth = Gdx.graphics.getWidth() / 25;
		float startingX = Gdx.graphics.getWidth() * 0.6f;
		float startingY = Gdx.graphics.getHeight()*.4f;
		
		button1.setWidth(buttonWidth);
		button1.setHeight(buttonWidth);
		button1.setX(startingX);
		button1.setY(startingY);
		
		button2.setWidth(buttonWidth);
		button2.setHeight(buttonWidth);
		button2.setX(button1.getX()+offset);
		button2.setY(button1.getY());
		
		button3.setWidth(buttonWidth);
		button3.setHeight(buttonWidth);
		button3.setX(button2.getX()+offset);
		button3.setY(button1.getY());
		
		button4.setWidth(buttonWidth);
		button4.setHeight(buttonWidth);
		button4.setX(startingX - offset/2);
		button4.setY(button1.getY() - offset);
		
		button5.setWidth(buttonWidth);
		button5.setHeight(buttonWidth);
		button5.setX(button4.getX() + offset);
		button5.setY(button4.getY());
		
		button6.setWidth(buttonWidth);
		button6.setHeight(buttonWidth);
		button6.setX(button5.getX() + offset);
		button6.setY(button4.getY());
		
		button7.setWidth(buttonWidth);
		button7.setHeight(buttonWidth);
		button7.setX(button6.getX() + offset);
		button7.setY(button4.getY());
		
		button8.setWidth(buttonWidth);
		button8.setHeight(buttonWidth);
		button8.setX(startingX);
		button8.setY(button4.getY() - offset);
		
		button9.setWidth(buttonWidth);
		button9.setHeight(buttonWidth);
		button9.setX(button8.getX() + offset);
		button9.setY(button8.getY());
		
		button10.setWidth(buttonWidth);
		button10.setHeight(buttonWidth);
		button10.setX(button9.getX() + offset);
		button10.setY(button8.getY());
		
		button11.setWidth(buttonWidth);
		button11.setHeight(buttonWidth);
		button11.setX(button5.getX());
		button11.setY(button8.getY() - offset);
		
		button12.setWidth(buttonWidth);
		button12.setHeight(buttonWidth);
		button12.setX(button11.getX() + offset);
		button12.setY(button8.getY() - offset);
		
		button13.setWidth(buttonWidth);
		button13.setHeight(buttonWidth);
		button13.setX(button9.getX());
		button13.setY(button12.getY() - offset);
		
		name4.setWidth(buttonWidth*2);
		name4.setHeight(buttonWidth);
		name4.setX(Gdx.graphics.getWidth() - offset*2);
		name4.setY(button11.getY());
		
		name2.setWidth(buttonWidth*2);
		name2.setHeight(buttonWidth);
		name2.setX(Gdx.graphics.getWidth() - offset*2);
		name2.setY(name4.getY() + offset);
		
		name1.setWidth(buttonWidth*2);
		name1.setHeight(buttonWidth);
		name1.setX(name2.getX() - offset * 2);
		name1.setY(name4.getY() + offset);
		
		name3.setWidth(buttonWidth*2);
		name3.setHeight(buttonWidth);
		name3.setX(name2.getX() - offset * 2);
		name3.setY(name4.getY());
		
		goButton.setWidth(buttonWidth * 2);
		goButton.setHeight(buttonWidth);
		goButton.setPosition(name1.getX()+buttonWidth, button4.getY());
		
		
		moveTextField = new TextField("", skin);
		moveTextField.setWidth((name4.getX()+buttonWidth*2) - button4.getX());
		moveTextField.setPosition(button4.getX(), 5);
	
		goButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if (selectedRank != null && selectedPlayer != null && selectedPlayer != currentPlayer && currentPlayer.getName() == Constants.user)
            	{
            		play(currentPlayer, selectedPlayer, selectedRank);
            		currentMove = (currentPlayer.getName() + ", " + selectedPlayer.getName() + ", " + selectedRank);
            		nextTurn();
            	}
            }
        });
		
		
		name1.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedPlayer = p1;
            }
        });
		
		name2.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedPlayer = p2;
            }
        });
		
		name3.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedPlayer = p3;
            }
        });
		
		name4.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedPlayer = p4;
            }
        });
		
		button1.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "1";
            }
        });
		
		button2.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "2";
            }
        });
		
		button3.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "3";
            }
        });
		
		button4.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "4";
            }
        });
		
		button5.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "5";
            }
        });
		
		button6.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "6";
            }
        });
		
		button7.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "7";
            }
        });
		
		button8.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "8";
            }
        });
		
		button9.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "9";
            }
        });
		
		button10.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "10";
            }
        });
		
		button11.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "jack";
            }
        });
		
		button12.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "queen";
            }
        });
		
		button13.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	selectedRank = "king";
            }
        });
		
		//Add buttons to stage
		stage.addActor(button1);
		stage.addActor(button2);
		stage.addActor(button3);
		stage.addActor(button4);
		stage.addActor(button5);
		stage.addActor(button6);
		stage.addActor(button7);
		stage.addActor(button8);
		stage.addActor(button9);
		stage.addActor(button10);
		stage.addActor(button11);
		stage.addActor(button12);
		stage.addActor(button13);
		stage.addActor(name1);
		stage.addActor(name2);
		stage.addActor(name3);
		stage.addActor(name4);
		stage.addActor(moveTextField);
		stage.addActor(goButton);
		
		 Gdx.input.setInputProcessor(stage);
		
	}
	
	public String getUserName()
	{
		return Constants.user;
	}
	
	public void generateHandSprites()
	{
		ArrayList<Card> hand = p1.getHand();
		handSprites = new Sprite[hand.size()];
		
		for (int i = 0; i<handSprites.length; i++) 
		{
			Sprite temp = hand.get(i).getSprite();
			temp.setPosition(60 * i, 10);
			
			
			handSprites[i] = temp;
		}
		
	}
	
	private void generatePondSprites()
	{
		pondSprites = new Sprite[deck.getSize()-deckIterator];
	
		for (int i = deckIterator; i<deck.getSize(); i++) 
		{
			Card tempCard = deck.getCard(i);
			Sprite tempSprite;
			if (!tempCard.isFlipped()) tempCard.flip();
			tempSprite = tempCard.getSprite();
			
			tempSprite.setPosition(Gdx.graphics.getWidth()/4+(20*(i-deckIterator)), Gdx.graphics.getHeight()/2);
			pondSprites[i-deckIterator] = tempSprite;
		}
	}
	
	
	private void play(Player currentPlayer, Player otherPlayer, String rank)
	{
		if (otherPlayer.hasCard(rank)) {
			Card toMove = otherPlayer.getCard(otherPlayer.getIndexOfCardTaken());
			currentPlayer.addCard(toMove); //Add other player's card to current player's hand
			otherPlayer.removeCard(toMove); //Remove the card that was taken by current player
		}
		else currentPlayer.addCard(fish());
		generatePondSprites();
		generateHandSprites();
	}
	
	private Card fish() {
		Card card = deck.getCard(deckIterator);
		card.flip();
		deckIterator++;
		return card;
	}
	
	private void deal()
	{
		for (int i = 0; i<7; i++)
		{
			p1.addCard(deck.getCard(deckIterator));
			deckIterator++;
			p2.addCard(deck.getCard(deckIterator));
			deckIterator++;
			p3.addCard(deck.getCard(deckIterator));
			deckIterator++;
			p4.addCard(deck.getCard(deckIterator));
			deckIterator++;
		}
	}
	
	private void nextTurn()
	{
		if (currentPlayer == p1) currentPlayer = p2;
		else if (currentPlayer == p2) currentPlayer = p3;
		else if (currentPlayer == p3) currentPlayer = p4;
		else if (currentPlayer == p4) currentPlayer = p1;
		generateHandSprites();
	}
	
	public String getNextMove()
	{
		return null;
	}
	
	
}
