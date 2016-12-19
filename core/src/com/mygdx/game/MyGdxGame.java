package com.mygdx.game;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter{
	SpriteBatch batch;
	Texture background;

	final float appWidth = 800; 
	final float appHeight = 600;
	float screenWidth;
	float screenHeight;
	
	OrthographicCamera camera;
	
	Engine engine;
	MovementSystem movementSystem;
	RenderSystem renderSystem;
	RemoveSystem removeSystem;
	
	Player player;
	
	float spawnTimer;
	float randSpawn;
	
	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
  		camera = new OrthographicCamera();
		camera.setToOrtho(false, appWidth, appHeight);
		
		engine = new Engine();
		movementSystem = new MovementSystem();
		renderSystem = new RenderSystem(camera);
		removeSystem = new RemoveSystem(engine);
		engine.addSystem(movementSystem);
		engine.addSystem(renderSystem);
		engine.addSystem(removeSystem);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		background = new Texture("snow-background.png");
		
		player = new Player();
        
        spawnTimer = 0;
        randSpawn = 1;
	}

	@Override
	public void render () {
		
		batch.begin();
		batch.draw(background, 0, 0);
		player.render(batch);
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
		
		if(spawnTimer > randSpawn) {
			Random rand = new Random();

			int  n = rand.nextInt(400) + 1;
			int  velRand = rand.nextInt(400) + 100;
			randSpawn = rand.nextFloat() * 2;
			
			VelocityComponent vel = new VelocityComponent();
			vel.x = -velRand;
			PositionComponent pos = new PositionComponent();
			pos.y = n;
			pos.x = 800;
			RenderComponent ren = new RenderComponent();
			ren.img = new Texture("santaandsnowman.png");
			Entity entity = new Entity();
			entity.add(pos);
			entity.add(vel);
			entity.add(ren);
			
			engine.addEntity(entity);
			spawnTimer = 0;
		}
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
