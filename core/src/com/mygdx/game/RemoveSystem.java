package com.mygdx.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class RemoveSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private Engine engine;
    
    public RemoveSystem(Engine engine) {
    	this.engine = engine;
    }

    @SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            if(position.x < -100) {
            	this.engine.removeEntity(entity);
            	updateLives();
            	return;
            }
            if(position.y < -32) {
            	this.engine.removeEntity(entity);
            	updateStreakAndRecord();
            	return;
            }
        }
    }
    
    private void updateLives() {
		MetaGame.lives -= 1;
		if(MetaGame.lives <= 0){
			MetaGame.bestScore = MetaGame.score;
			MetaGame.score = 0;
			MetaGame.streak = 0;
			MetaGame.lives = 3;
			this.engine.removeAllEntities();
		}
		
	}

	public void updateStreakAndRecord() {
    	if(MetaGame.streak > MetaGame.record)
    		MetaGame.record = MetaGame.streak;
    	MetaGame.streak = 0;
    }
}
