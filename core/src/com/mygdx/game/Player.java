package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	public float x;
	public float y;
	
	public float velocity;
	
	Texture img;
	
	Player(float appHeight) {
		this.img = new Texture("platform-snow.png");
		this.velocity = 400;
		this.y = appHeight - this.img.getHeight();
	}
	
	public void update(float deltaTime, float appWidth, float screenWidth) {
		float touch_x = Gdx.input.getX() * (appWidth / screenWidth);
		
		if(this.x > 0) {
			if(Gdx.input.isKeyPressed(Keys.LEFT) || (Gdx.input.isTouched() && touch_x < (appWidth/2))) {
				this.x += -this.velocity * deltaTime;
			}
		}
		if(this.x < appWidth - img.getWidth()) {
			if(Gdx.input.isKeyPressed(Keys.RIGHT) || (Gdx.input.isTouched() && touch_x > (appWidth/2))) {
				this.x += this.velocity * deltaTime;
			}
		}
	}
	
	public Boolean isPlayerShooting() {
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(this.img, this.x, this.y);
	}
}
