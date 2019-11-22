package com.mygdx.ColesGames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gui.SinglePlayerGameSelectScreen;

import util.Constants;

public class SelectScreen extends Game implements Screen {

	private static Skin skin;
	private Stage stage;
	private Game game;

	public SelectScreen(Game game) {
		this.game = game;
		create();
	}

	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();

		final Label textLabel = new Label("Choose your difficulty wisely!", skin, "default");
		final TextButton diff0 = new TextButton("PvP", skin, "default");
		final TextButton diff1 = new TextButton("Random", skin, "default");
		final TextButton diff2 = new TextButton("AI tries to win", skin, "default");
		final TextButton diff3 = new TextButton("AI blocks player", skin, "default");

		// define width
		textLabel.setWidth(Gdx.graphics.getWidth() / 3);
		diff0.setWidth(Gdx.graphics.getWidth() / 3);
		diff1.setWidth(diff0.getWidth());
		diff2.setWidth(diff0.getWidth());
		diff3.setWidth(diff0.getWidth());

		// define height
		textLabel.setHeight(Gdx.graphics.getHeight() / 40);
		diff0.setHeight(Gdx.graphics.getHeight() / 15);
		diff1.setHeight(diff0.getHeight());
		diff2.setHeight(diff0.getHeight());
		diff3.setHeight(diff0.getHeight());

		// set the position
		int xpos = Gdx.graphics.getWidth() / 7;
		textLabel.setPosition(xpos, Gdx.graphics.getHeight() / 2);
		diff0.setPosition(xpos, Gdx.graphics.getHeight() / 2 - 40);
		diff1.setPosition(xpos, diff0.getY() - 32);
		diff2.setPosition(xpos, diff0.getY() - 64);
		diff3.setPosition(xpos, diff0.getY() - 96);

		// add listeners
		diff0.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				stage.dispose();
				game.setScreen(new ConnectFour(game, 0));
			}
		});
		
		diff1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				stage.dispose();
				game.setScreen(new ConnectFour(game, 1));
			}
		});
		
		diff2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				stage.dispose();
				game.setScreen(new ConnectFour(game, 2));
			}
		});
		
		diff3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				stage.dispose();
				game.setScreen(new ConnectFour(game, 3));
			}
		});

		stage.addActor(textLabel);
		stage.addActor(diff0);
		stage.addActor(diff1);
		stage.addActor(diff2);
		stage.addActor(diff3);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Don't know why but you need this
		stage.act(); // Starts button functionality
		stage.draw(); // Draws buttons

		Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			dispose();
			stage.dispose();
			game.setScreen(new SinglePlayerGameSelectScreen(game));
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
