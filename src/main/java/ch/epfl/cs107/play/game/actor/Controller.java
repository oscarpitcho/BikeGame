package ch.epfl.cs107.play.game.actor;

public interface Controller  {
	
	/**
	 * Returns a boolean destroy value of the actor, by default false.
	 * @return
	 */
	default public boolean getDestroy() {
		return false;
	}
}
