package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import util.Constants;
import util.JsonParser;

public class UserInfoScreen extends Game implements Screen {
	private Skin skin;
	private Stage stage;
	private Game game;
	private boolean loggedIn = false;
	public String user = "default";
	public UserInfoScreen(Game game) {
		this.game = game;
		create();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
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
		stage.dispose();
	}

	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
		stage = new Stage();

		final TextButton backButton = new TextButton("Back", skin, "default");
		final TextButton createUserButton = new TextButton("Create User", skin, "default");
		final TextButton loginButton = new TextButton("Log In", skin, "default");
		final TextField usernameTextField = new TextField("Username", skin);
		final TextField passwordTextField = new TextField("Password", skin);
		final TextButton loginWithUsernamePassword = new TextButton("Enter", skin, "default");
		final TextButton createNewUser = new TextButton("New User", skin, "default");
		final Label userInfoLabel = new Label("HI GUYS: " + Constants.user, skin, "default");
		final Label wrongPass = new Label("Incorect Password or Username", skin, "default"); //TODO change font color to red
		/*
		 * BACK BUTTON 
		 */
		backButton.setWidth(Constants.BUTTON_WIDTH);
		backButton.setHeight(Constants.BUTTON_HEIGHT);
		backButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.setScreen(new MainScreen(game));
			}
		});
		
		/*
		 * CREATE USER
		 */
		createUserButton.setWidth(Constants.BUTTON_WIDTH);
		createUserButton.setHeight(Constants.BUTTON_HEIGHT);
		createUserButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - Constants.BUTTON_OFFSET);

		loginButton.setWidth(Constants.BUTTON_WIDTH);
		loginButton.setHeight(Constants.BUTTON_HEIGHT);
		loginButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 2*Constants.BUTTON_OFFSET);
		loginButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
            	dispose();
				loggedIn = true;
				create();
			}
		});
	
		usernameTextField.setWidth(Constants.BUTTON_WIDTH);
		usernameTextField.setHeight(Constants.BUTTON_HEIGHT);
		usernameTextField.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 3*Constants.BUTTON_OFFSET);
		usernameTextField.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				usernameTextField.setText("");
			
			}
		});
		
		
		
		passwordTextField.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				passwordTextField.setText("");
				passwordTextField.setPasswordMode(true);
			}
		});
		
		passwordTextField.setWidth(Constants.BUTTON_WIDTH);
		passwordTextField.setHeight(Constants.BUTTON_HEIGHT);
		passwordTextField.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 4*Constants.BUTTON_OFFSET);
		passwordTextField.setPasswordCharacter('*');
		
		loginWithUsernamePassword.setHeight(Constants.BUTTON_HEIGHT);
		loginWithUsernamePassword.setWidth(Gdx.graphics.getWidth()/8);
		loginWithUsernamePassword.setPosition(Gdx.graphics.getWidth() / 2 + 150f, Gdx.graphics.getHeight() / 2 - 4*Constants.BUTTON_OFFSET);
	
		loginWithUsernamePassword.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Integer newUserID = -1;
				try { newUserID = Integer.parseInt(JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/userLogin?user="+usernameTextField.getText()+"&pass="+passwordTextField.getText()));} catch (Exception e1) {e1.printStackTrace();}
				if (newUserID != -1){
					Constants.userID = newUserID;
					Constants.user = usernameTextField.getText();
					userInfoLabel.setText("HI GUYS: " + Constants.user);
					usernameTextField.setText("");
					passwordTextField.setText("");
					wrongPass.setVisible(false);

				}
				else {
					wrongPass.setVisible(true);
				}
				
			}});
		
		
		createNewUser.setHeight(Constants.BUTTON_HEIGHT);
		createNewUser.setWidth(Gdx.graphics.getWidth()/8);
		createNewUser.setPosition(Gdx.graphics.getWidth() / 2 + 150f, Gdx.graphics.getHeight() / 2 - 3*Constants.BUTTON_OFFSET);
		createNewUser.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//Constants.user = "name=" + usernameTextField.getText() + "&password=" + passwordTextField.getText(); 
				try {
					JsonParser.sendHTML("newUser","name=" + usernameTextField.getText() + "&password=" + passwordTextField.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		userInfoLabel.setWidth(Constants.BUTTON_WIDTH);
		userInfoLabel.setHeight(Constants.BUTTON_HEIGHT);
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
		wrongPass.setWidth(Constants.BUTTON_WIDTH);
		wrongPass.setHeight(Constants.BUTTON_HEIGHT);
		wrongPass.setPosition(passwordTextField.getX(), passwordTextField.getY()-passwordTextField.getHeight());
		wrongPass.setVisible(false);
		
		if (!loggedIn) {
			usernameTextField.setVisible(false);
			passwordTextField.setVisible(false);
			loginWithUsernamePassword.setVisible(false);
			createNewUser.setVisible(false);
		}
		
		
		stage.addActor(backButton);
		stage.addActor(createUserButton);
		stage.addActor(loginButton);
		stage.addActor(usernameTextField);
		stage.addActor(passwordTextField);
		stage.addActor(loginWithUsernamePassword);
		stage.addActor(createNewUser);
		stage.addActor(userInfoLabel);
		stage.addActor(wrongPass);
		Gdx.input.setInputProcessor(stage);
		

	}
	public String getUser()
	{
		return user;
	}

}
