package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import util.Constants;
import util.JsonParser;

public class UserInfoScreen extends Game implements Screen {
	private Skin skin;
	private Stage stage;
	private Game game;
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
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();

		final TextButton createUserButton = new TextButton("Create User", skin, "default");
		final TextButton loginButton = new TextButton("Log In", skin, "default");
		final TextField usernameTextField = new TextField("Username", skin);
		final TextField passwordTextField = new TextField("Password", skin);
		final TextButton loginWithUsernamePassword = new TextButton("Enter", skin, "default");
		final TextButton createNewUser = new TextButton("New User", skin, "default");
		final Label loginMessage = new Label("Incorect Password or Username", skin, "default"); //TODO change font color to red
		final TextField addFriendField = new TextField("Friends ID", skin);
		final TextButton addFriendButton = new TextButton("Add Friend", skin, "default");
		final TextButton deleteFriendButton = new TextButton("Delete Friend", skin, "default");
		
		//User Info Name and sign in/ sign out button at the top of the screen
        final Label userInfoLabel = new Label("HI GUYS: " + Constants.user, skin, "default");
        final TextButton userSignButton= new TextButton("singin/out",skin,"default");
        userSignButton.setHeight(Gdx.graphics.getHeight() / 40);
        userSignButton.setPosition(Gdx.graphics.getWidth()-userSignButton.getWidth(), Gdx.graphics.getHeight()-userSignButton.getHeight());
        if (Constants.userID == 0) userSignButton.setText("Sign In");
        else userSignButton.setText("Sign Out");
        userSignButton.addListener(new ClickListener(){ //When Sign in/Sing out is pressed in the top right
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(Constants.userID == 0) { //If no one is signed in
            		dispose();
                	game.setScreen(new UserInfoScreen(game));
            	}
            	else { //If a user is currently signed in
            		Constants.userID = 0;
            		Constants.user = "Temporary User";
            		create();
            	}
            }});
		userInfoLabel.setHeight(userSignButton.getHeight());
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth()-userSignButton.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
        stage.addActor(userInfoLabel);
        stage.addActor(userSignButton);
        
		/*
		 * BACK BUTTON 
		 */
        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.setWidth(Gdx.graphics.getWidth() / 4);
        backButton.setHeight(Gdx.graphics.getHeight() / 20);
        backButton.setPosition(0, Gdx.graphics.getHeight()-backButton.getHeight());
        //When back button is clicked
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new MainScreen(game));
            }});
        
        //Left side Tables
        final Table friendsTable = new Table();
        final Table scoreTable = new Table();
        final SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("Friends List","Scores","Statistics");
        selectBox.setWidth(Gdx.graphics.getWidth()/2);
        selectBox.setHeight(backButton.getHeight());
        selectBox.setPosition(0, Gdx.graphics.getHeight()-backButton.getHeight()-selectBox.getHeight());
        selectBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            	friendsTable.setVisible(selectBox.getSelected().equals("Friends List"));
                scoreTable.setVisible(selectBox.getSelected().equals("Scores"));
            }
        });
        friendsTable.setWidth(selectBox.getWidth());
        scoreTable.setWidth(selectBox.getWidth());
        friendsTable.setHeight(Gdx.graphics.getHeight()-selectBox.getHeight()-backButton.getHeight());
        scoreTable.setHeight(Gdx.graphics.getHeight()-selectBox.getHeight()-backButton.getHeight());
        friendsTable.setPosition(selectBox.getX(), 0);
        scoreTable.setPosition(selectBox.getX(), 0);
        Pixmap labelColor = new Pixmap((int)friendsTable.getWidth(), (int)friendsTable.getHeight(), Pixmap.Format.RGB888);
        labelColor.setColor(Color.DARK_GRAY);
        labelColor.fill();
        friendsTable.setBackground(new Image(new Texture(labelColor)).getDrawable());
        scoreTable.setBackground(new Image(new Texture(labelColor)).getDrawable());
        friendsTable.setVisible(true);
        scoreTable.setVisible(false);

//        final ScrollPane friendsScroll = new ScrollPane(friendsTable);
//        friendsScroll.setWidth(friendsTable.getWidth());
//        friendsScroll.setHeight(friendsTable.getHeight()/2);
         
        String friendString;
        try{
        	friendString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getFriendsNames?userId="+Constants.userID);
        } catch (Exception e) { 
        	friendString = "Failed";
        	e.printStackTrace(); 
        }
        
        if(friendString.equals("Failed")) {
        	Label failedFriend = new Label("Failed to get friends list", skin, "default");
        	friendsTable.add(failedFriend).row();
        } else if(friendString.equals("Temporary user")){
        	Label failedFriend = new Label("Sign-in to see your friends list.", skin, "default");
        	friendsTable.add(failedFriend).row();
        } else {
        	if(friendString.equals("You have no friends")){
        		Label noFriends = new Label("You have no friends", skin, "default");
            	friendsTable.add(noFriends).row();
        	} else {
        		String[] arr = friendString.split("::");
        		for(int i = 0; i < arr.length; i+=2) {
        			Label f = new Label(arr[i] + "(" + arr[i+1] + ")", skin, "default");
                	friendsTable.add(f).row();
        		}
        	}
        }
        
		usernameTextField.setWidth((Gdx.graphics.getWidth()-selectBox.getWidth())*9/10);
		usernameTextField.setHeight(Constants.BUTTON_HEIGHT);
		usernameTextField.setPosition((Gdx.graphics.getWidth()-selectBox.getWidth()-usernameTextField.getWidth())/2+Gdx.graphics.getWidth()-selectBox.getWidth(), Gdx.graphics.getHeight()* 3 / 4);
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
		
		passwordTextField.setWidth(usernameTextField.getWidth());
		passwordTextField.setHeight(usernameTextField.getHeight());
		passwordTextField.setPosition(usernameTextField.getX(), usernameTextField.getY()-passwordTextField.getHeight()-25);
		passwordTextField.setPasswordCharacter('*');
		
		loginWithUsernamePassword.setHeight(Constants.BUTTON_HEIGHT);
		loginWithUsernamePassword.setWidth(passwordTextField.getWidth()/2);
		loginWithUsernamePassword.setPosition(passwordTextField.getX()+loginWithUsernamePassword.getWidth(), passwordTextField.getY()-loginWithUsernamePassword.getHeight()-25);
	
		loginWithUsernamePassword.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Integer newUserID = -1;
				try { newUserID = Integer.parseInt(JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/userLogin?user="+usernameTextField.getText()+"&pass="+passwordTextField.getText()));} catch (Exception e1) {e1.printStackTrace();}
				if (newUserID != -1){
					Constants.userID = newUserID;
					Constants.user = usernameTextField.getText();
					create();

				}
				else {
					loginMessage.setVisible(true);
				}
				
			}});
		
		
		createNewUser.setHeight(loginWithUsernamePassword.getHeight());
		createNewUser.setWidth(loginWithUsernamePassword.getWidth());
		createNewUser.setPosition(passwordTextField.getX(),loginWithUsernamePassword.getY());
		createNewUser.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				loginMessage.setVisible(true);
				if(passwordTextField.getText().toLowerCase().equals("password")) {
					loginMessage.setText("That's a Stupid Password");
					loginMessage.setColor(Color.RED);
				}
				else{
					try {JsonParser.sendHTML("newUser","name=" + usernameTextField.getText() + "&password=" + passwordTextField.getText());} catch (Exception e) {e.printStackTrace();}
					loginMessage.setText("User Created");
					loginMessage.setColor(Color.GREEN);
				}
				
			}
		});
		
		/* ------------------------------------------------------
		 * FRIEND BUTTONS AND FIELDS
		 * ----------------------------------------------------*/
		addFriendField.setWidth((Gdx.graphics.getWidth()-selectBox.getWidth())*9/10);
        addFriendField.setHeight(Constants.BUTTON_HEIGHT);
        addFriendField.setPosition(usernameTextField.getX(), loginWithUsernamePassword.getY()-addFriendField.getHeight()-75);
        
        addFriendField.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				addFriendField.setText("");
			}
		});
        
        addFriendButton.setWidth(passwordTextField.getWidth()/2);
        addFriendButton.setHeight(Constants.BUTTON_HEIGHT);
        addFriendButton.setPosition(addFriendField.getX(), addFriendField.getY()-addFriendButton.getHeight()-25);
        addFriendButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String f = null;
				try { f = JsonParser.sendHTML("addFriend", "userId="+Constants.userID+"&friendId="+addFriendField.getText());} catch (Exception e1) {e1.printStackTrace();}
				if (f != null){
					create();
				}
			}});
        
        deleteFriendButton.setWidth(passwordTextField.getWidth()/2);
        deleteFriendButton.setHeight(Constants.BUTTON_HEIGHT);
        deleteFriendButton.setPosition(passwordTextField.getX()+loginWithUsernamePassword.getWidth(), addFriendField.getY()-addFriendButton.getHeight()-25);
        deleteFriendButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String f = null;
				try { f = JsonParser.sendHTML("deleteFriend", "userId="+Constants.userID+"&friendId="+addFriendField.getText());} catch (Exception e1) {e1.printStackTrace();}
				if (f != null){
					create();
				}
			}});
        
        
		
		loginMessage.setWidth(Constants.BUTTON_WIDTH);
		loginMessage.setHeight(Constants.BUTTON_HEIGHT);
		loginMessage.setPosition(passwordTextField.getX(), createNewUser.getY()-passwordTextField.getHeight());
		loginMessage.setColor(255,0,0,1);
		loginMessage.setVisible(false);
		
		stage.addActor(backButton);
		stage.addActor(createUserButton);
		stage.addActor(loginButton);
		stage.addActor(usernameTextField);
		stage.addActor(passwordTextField);
		stage.addActor(addFriendField);
		stage.addActor(addFriendButton);
		stage.addActor(deleteFriendButton);
		stage.addActor(loginWithUsernamePassword);
		stage.addActor(createNewUser);
		stage.addActor(loginMessage);
		stage.addActor(selectBox);
		stage.addActor(friendsTable);
		stage.addActor(scoreTable);
//		stage.addActor(friendsScroll);
		Gdx.input.setInputProcessor(stage);
		

	}
	public String getUser()
	{
		return user;
	}

}
