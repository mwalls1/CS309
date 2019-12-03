
package Race;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
public class TrackSelectScreen extends Game implements Screen{
	private static Skin skin;
	private Stage stage;
	private Game game;
	private Sprite sprite;
	private Texture texture;
	private AssetManager manager;
	
	/**
	 * Main screen with play, options and leaderboard buttons
	 * @param game game object, we use Game's setScreen() method to switch between different screens
	 */
	public TrackSelectScreen(Game game)
	{
		this.game = game;
		create();
			}
	/**
	 * From screen interface, called when this screen is set
	 */
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage); //Uses the stage, gets input from cursor/clicks
		
	}
	
	/**
	 * Runs every frame, draws updated stage and sets background color
	 */
	public void render(float delta) {
		

	     
	  
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
		
	}
	@Override
	public void hide() {
		
	}
	@Override
	/**
	 * Clears screen to free memory
	 */
	public void dispose() {
		stage.dispose();
		
	}
	
	
	/**
	 * Describes button functionality and position
	 */
	public void create() {
		
		// TODO Auto-generated method stub
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        manager = new AssetManager();
        final TextButton next = new TextButton(">", skin, "default");
        final TextButton back = new TextButton("<", skin, "default");
        
        texture = manager.get("track1 copy.png");
        sprite = new Sprite(texture);
        
        next.setWidth(Gdx.graphics.getWidth());
        back.setWidth(next.getWidth());
      
        next.setHeight(next.getWidth());
        back.setHeight(next.getHeight());
        
//        next.setPosition();
//        back.setPosition(x, y);
        
        
        
        next.addListener(new ClickListener(){ //When Sign in/Sing out is pressed in the top right
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Cycle to next track
            	}
            	
            });
        
        
        back.addListener(new ClickListener(){ //When Sign in/Sing out is pressed in the top right
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Cycle to last track
            	}
            	
            });
		
        
        
        //Adds all buttons to stage to be rendered
        stage.addActor(next);
        stage.addActor(back);
        
        Gdx.input.setInputProcessor(stage);
	}
	
	public void loadAssets()
	{
		manager.load("Race/track1 copy.png", Texture.class);
		manager.load("Race/track2.png", Texture.class);
		manager.finishLoading();
	}
}