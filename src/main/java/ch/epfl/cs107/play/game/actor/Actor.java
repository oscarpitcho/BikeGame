package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;

public interface Actor extends Graphics, Positionable, Controller {
	/**
	 * Default update method that is overridden by concerned actors.
	 * @param deltaTime
	 */
	default void update (float deltaTime) {
		
	}
	/**
	 * Default destroy method that is overridden by concerned actors.
	 */
	default void destroy () {
		
	}
}
