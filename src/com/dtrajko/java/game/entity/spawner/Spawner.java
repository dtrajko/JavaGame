package com.dtrajko.java.game.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import com.dtrajko.java.game.entity.Entity;
import com.dtrajko.java.game.entity.particle.Particle;
import com.dtrajko.java.game.level.Level;

public class Spawner extends Entity {

	private static List<Entity> entities = new ArrayList<Entity>();

	public enum Type {
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
