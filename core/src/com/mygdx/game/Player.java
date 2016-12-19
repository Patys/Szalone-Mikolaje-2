package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	public float x;
	public float y;
	
	Texture img;
	
	Player() {
		this.img = new Texture("platform-snow.png");
	}
	
	public void update(float deltaTime, float appWidth, float screenWidth) {
		float touch_x = Gdx.input.getX() * (appWidth / screenWidth);
		if(Gdx.input.isKeyPressed(Keys.LEFT) || (Gdx.input.isTouched() && touch_x < (appWidth/2))) {
			this.x += -100 * deltaTime;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || (Gdx.input.isTouched() && touch_x > (appWidth/2))) {
			this.x += 100 * deltaTime;
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(this.img, this.x, this.y);
	}
}
