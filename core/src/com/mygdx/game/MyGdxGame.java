package com.mygdx.game;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MyGdxGame extends ApplicationAdapter{
	SpriteBatch batch;
	Texture background;
	BitmapFont font;

	final float appWidth = 800; 
	final float appHeight = 600;
	float screenWidth;
	float screenHeight;
	
	OrthographicCamera camera;
	
	Engine engine;
	MovementSystem movementSystem;
	RenderSystem renderSystem;
	RemoveSystem removeSystem;
	CollisionSystem collisionSystem;
	
	Player player;
	String text_score;
	String text_streak;
	String text_record;
	String text_best_score;
	String text_lives;
	
	float spawnTimer;
	float spawnBulletTimer;
	float randSpawn;
	
	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
  		camera = new OrthographicCamera();
		camera.setToOrtho(false, appWidth, appHeight);
		
		engine = new Engine();
		collisionSystem = new CollisionSystem();
		movementSystem = new MovementSystem();
		renderSystem = new RenderSystem(camera);
		removeSystem = new RemoveSystem(engine);
		engine.addSystem(movementSystem);
		engine.addSystem(renderSystem);
		engine.addSystem(removeSystem);
		engine.addSystem(collisionSystem);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		background = new Texture("snow-background.png");
		
		player = new Player(appHeight);
        
        spawnTimer = 0;
        randSpawn = 1;
        spawnBulletTimer = 0;
        text_score = "" + MetaGame.score;
        text_streak = "" + MetaGame.streak;
        text_record = "" + MetaGame.record;
        text_best_score = "" + MetaGame.bestScore;
        text_lives = "" + MetaGame.lives;
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Raleway-Bold.otf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	@Override
	public void render () {
		
		batch.begin();
		batch.draw(background, 0, 0);
		player.render(batch);

        text_best_score = "Best score: " + MetaGame.bestScore;
		font.draw(batch, text_best_score, (appWidth)-(text_best_score.length()*32)/2, appHeight-6);

        text_lives = "Lives: " + MetaGame.lives;
		font.draw(batch, text_lives, 0, appHeight-6);
		
        text_score = "Score: " + MetaGame.score;
		font.draw(batch, text_score, (appWidth/2)-(text_score.length()*32/2)/2, appHeight/2-6);
        text_streak = "Streak: " + MetaGame.streak;
		font.draw(batch, text_streak, (appWidth/2)-(text_streak.length()*32/2)/2, appHeight/2-37);
        text_record = "Record: " + MetaGame.record;
		font.draw(batch, text_record, (appWidth/2)-(text_record.length()*32/2)/2, appHeight/2-68);
		batch.end();
		
		player.update(Gdx.graphics.getDeltaTime(), appWidth, screenWidth);

		updateSpawn();
		
		engine.update(Gdx.graphics.getDeltaTime());
		
		// Also You can get touch input according to your Screen.
		
//		if (Gdx.input.isTouched()) {
//		 System.out.println(" X " + Gdx.input.getX()
//				* (appWidth / screenWidth));
//		 System.out.println(" Y " + Gdx.input.getY()
//				* (appHeight / screenHeight));
//	   }
	}
	
	private void updateSpawn() {
		spawnTimer += Gdx.graphics.getDeltaTime();
		spawnBulletTimer += Gdx.graphics.getDeltaTime();
		
		if(spawnTimer > randSpawn) {
			Random rand = new Random();
			
			randSpawn = rand.nextFloat() * 2;
			
			spawnSanta();
			spawnTimer = 0;
		}
		
		if(player.isPlayerShooting() && spawnBulletTimer > 0.2) {
			spawnBullet();
			spawnBulletTimer = 0;
		}
	}
	
	private void spawnBullet() {
		VelocityComponent vel = new VelocityComponent();
		vel.y = -300;
		PositionComponent pos = new PositionComponent();
		pos.y = player.y;
		pos.x = player.x + player.img.getWidth()/2;
		RenderComponent ren = new RenderComponent();
		ren.img = new Texture("snowball.png");
		TypeComponent type = new TypeComponent();
		type.type = "bullet";
		Entity entity = new Entity();
		entity.add(pos);
		entity.add(vel);
		entity.add(ren);
		entity.add(type);
		
		MetaGame.score -= 1;
		
		engine.addEntity(entity);
	}
	
	private void spawnSanta() {
		Random rand = new Random();

		int  n = rand.nextInt(400) + 1;
		int  velRand = rand.nextInt(400) + 100;
		
		VelocityComponent vel = new VelocityComponent();
		vel.x = -velRand;
		PositionComponent pos = new PositionComponent();
		pos.y = n;
		pos.x = 800;
		RenderComponent ren = new RenderComponent();
		ren.img = new Texture("santaandsnowman.png");
		TypeComponent type = new TypeComponent();
		type.type = "santa";
		Entity entity = new Entity();
		entity.add(pos);
		entity.add(vel);
		entity.add(ren);
		entity.add(type);
		
		engine.addEntity(entity);
	}
	
	@Override
	public void resize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
        camera.update();
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
