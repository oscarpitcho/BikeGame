package ch.epfl.cs107.play.game.actor.BikeGame.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Timer;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

abstract public class Trigger extends GameEntity implements Actor, Timer {
	
	private long timerDuration;
	private Entity object;
	protected long timeAtInteraction;
	public Trigger(ActorGame game, Boolean fixed, Vector position, long timerDuration) {
		super(game, fixed, position);
		this.timerDuration = timerDuration;
		object = super.getEntity();
	}
	
	abstract protected void deactivate();
	abstract protected void respawn(long timeAtDestroy);

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return object.getTransform();
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return object.getVelocity();
	}
	
	protected long getTimerDuration() {
		return timerDuration;
	}
	protected void setTimeAtInteraction(long time) {
		timeAtInteraction = time;
	}
	protected long getTimeAtInteraction() {
		return timeAtInteraction;
	}

}
