package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity implements Actor {
	private ActorGame game;
	private Entity entity;
	protected boolean destroy = false;
	
	public GameEntity (ActorGame game, boolean fixed, Vector position) {
		if(game == null || position == null)
			throw new NullPointerException("One of the arguments is null.");
		if(!(game instanceof ActorGame) || !(position instanceof Vector))
			throw new IllegalArgumentException("One of the arguments is not valid");

			
		this.game = game;
		entity = game.entityBuilder(fixed, position);
		
	}
	
	public GameEntity (ActorGame game, boolean fixed) {
		this.game = game;
		entity =  game.entityBuilder(fixed);
	}
	/**
	 * 
	 * @return: Returns entity associated to an instance.
	 */
	protected Entity getEntity () {
		return entity;
	}
	/**
	 * 
	 * @return: Returns game associated to an instance.
	 */
	protected ActorGame getOwner () {
		return game;
	}
	public void destroy () {
		entity.destroy();
	}

	@Override
	/**
	 * @return: Returns the value of the destroy attribute.
	 */
	public boolean getDestroy () {
		return destroy;
	}
	
	//Abstract methods to be defined in subclasses.
	abstract protected void entityGraphics (Entity entity);
	abstract protected void entityPart (Entity entity);	
}
