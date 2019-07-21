package ch.epfl.cs107.play.game.actor;

public abstract class GraphicInterface implements Actor {
	private ActorGame game;
	protected boolean destroy = false;
	
	public GraphicInterface (ActorGame game) {
		if(game == null)
			throw new NullPointerException("game is null.");
		if(!(game instanceof ActorGame))
			throw new IllegalArgumentException("Illegal argument game.");
		this.game = game;
	}
	
	/**
	 * 
	 * @return: The game which contains the instance
	 */
	protected ActorGame getOwner () {
		return game;
	}
	
	/**
	 * @return: The value of destroy attribute.
	 */
	public boolean getDestroy () {
		return destroy;
	}
	
}