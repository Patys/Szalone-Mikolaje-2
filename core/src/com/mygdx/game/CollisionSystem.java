package com.mygdx.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;

public class CollisionSystem  extends EntitySystem {
	private ImmutableArray<Entity> entities;
	private Engine engine;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<TypeComponent> tpm = ComponentMapper.getFor(TypeComponent.class);

    public CollisionSystem() {}

    @SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, TypeComponent.class).get());
        this.engine = engine;
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            
            PositionComponent position = pm.get(entity);
            TypeComponent type = tpm.get(entity);
            
            if(type.type == "bullet") {
            	for(int j = 0; j < entities.size(); ++j) {
    	            Entity col_entity = entities.get(j);
    	            TypeComponent col_type = tpm.get(col_entity);
    	            if(col_type.type == "santa") {
    	            	Rectangle rect = new Rectangle(position.x, position.y, 32, 32);
        	            PositionComponent col_position = pm.get(col_entity);
        	            Rectangle col_rect = new Rectangle(col_position.x, col_position.y, 96, 100);
        	            
        	            if(col_rect.overlaps(rect)) {
        	            	engine.removeEntity(col_entity);
        	            	engine.removeEntity(entity);
        	            	MetaGame.score += 2;
        	                break;
        	            }	
    	            }
            	}
            }
            
        }
    }
}
