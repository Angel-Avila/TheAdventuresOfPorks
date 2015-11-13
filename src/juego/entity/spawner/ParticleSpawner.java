package juego.entity.spawner;

import juego.entity.particle.Particle;
import juego.level.Level;

public class ParticleSpawner extends Spawner{

	//private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		//this.life = life;
		// Adds all the particles we want to our particles array in Level.java
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life));
		}
	}

}
