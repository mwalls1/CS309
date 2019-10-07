package com.mygdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.objects.*;
import util.Constants;
import util.JsonParser;

public class LeaderboardScreen extends Game implements Screen {
//	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Game game;

	public LeaderboardScreen(Game game) {
		this.game = game;
		create();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, Constants.alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
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
		stage.dispose();
	}


	@Override
	public void create() {
//		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();

		/*
		 * LEADERBOARD DISPLAY
		 */
		TextArea textField = new TextArea("text", skin, "default");
		
		try {
//			String s = "[{\"id\":29,\"name\":\"tay2\",\"password\":\"newpass\"},{\"id\":28,\"name\":\"tay\",\"password\":\"newpass\"},{\"id\":14,\"name\":\"name3\",\"password\":\"newpass\"},{\"id\":6,\"name\":\"taylor\",\"password\":\"pw\"},{\"id\":15,\"name\":\"name1\",\"password\":\"newpass\"},{\"id\":13,\"name\":\"name2\",\"password\":\"newpass\"}]";
			String s2 = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getScores");
			String userString = "";
			ArrayList<score> scores = JsonParser.parseScore(s2);
			System.out.println(scores.get(0).getScore());
			for (score sc : scores) {
				userString = userString.concat(sc.getName()+":"+sc.getScore()+"\n");
			}
			textField = new TextArea(userString, skin, "default");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textField.setWidth(Constants.BUTTON_WIDTH);
		textField.setHeight(Gdx.graphics.getHeight());
		textField.setPosition(Gdx.graphics.getWidth() / 2 - Constants.BUTTON_WIDTH / 2, 0);

		/*
		 * BACK BUTTON
		 */
		final TextButton backButton = new TextButton("Back", skin, "default");
		backButton.setWidth(Constants.BUTTON_WIDTH);
		backButton.setHeight(Constants.BUTTON_HEIGHT);
		backButton.setPosition(0, Gdx.graphics.getHeight() - Constants.BUTTON_HEIGHT);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Do some stuff when clicked
				dispose();
				game.setScreen(new MainScreen(game));
			}
		});

		/*
		 * REFRESH
		 */
		final TextButton refresh = new TextButton("Refresh", skin, "default");
		refresh.setWidth(Constants.BUTTON_WIDTH);
		refresh.setHeight(Constants.BUTTON_HEIGHT);
		refresh.setPosition(Gdx.graphics.getWidth() - Constants.BUTTON_WIDTH, Gdx.graphics.getHeight() - Constants.BUTTON_HEIGHT);
		refresh.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Do some stuff when clicked
				dispose();
				game.setScreen(new LeaderboardScreen(game));
			}
		});
		
		/*
		 * UPDATE DISPLAY 
		 */
		stage.addActor(backButton);
		stage.addActor(textField);
		stage.addActor(refresh);

	}

}
