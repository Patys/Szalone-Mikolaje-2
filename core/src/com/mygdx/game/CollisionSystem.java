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

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public CollisionSystem() {}

    @SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            
            PositionComponent position = pm.get(entity);
            
            Rectangle rect = new Rectangle(position.x, position.y, 32, 32);
            
            if(entities.size() > i+1) {
	            Entity col_entity = entities.get(i+1);
	            
	            PositionComponent col_position = pm.get(col_entity);
	        	
	            Rectangle col_rect = new Rectangle(col_position.x, col_position.y, 32, 32);
	            
	            if(col_rect.overlaps(rect)) {
	            	System.out.println("bumm");
	            }
            }
        }
    }
}
