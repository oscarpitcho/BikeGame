package ch.epfl.cs107.play.game.actor.BikeGame.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.bike.Bike;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Spikes extends Trigger implements Actor{
	private Entity spikes;
	private ImageGraphics spikesGraphics;
	private float width;
	private BasicContactListener listener;
	private long slowTime;
	
	private boolean activated = true;
	private boolean hit = false;
	
	public Spikes(ActorGame game, Vector position, float width, long timerDuration) {
		super(game, true, position, timerDuration);
		if(width <= 0 || timerDuration <= 0)
			throw new IllegalArgumentException("Negative argument exception.");
		spikes = super.getEntity();
		this.width = width;
		this.slowTime = super.getTimerDuration();

		//Creating contact listener
		listener = new BasicContactListener() {
			Bike bike = (Bike) getOwner().getPayLoad();
			@Override
			public void beginContact(Contact contact) {
				if (!bike.compareWheel(contact.getOther().getEntity())) {
					return;
				}
				hit = true;
			}
	
			@Override
			public void endContact(Contact contact) {}
		};
		entityPart(spikes);
		entityGraphics(spikes);
		spikes.addContactListener(listener);
		
	}
	@Override
	public void draw(Canvas canvas) {
		spikesGraphics.draw(canvas);
		
	}
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return spikes.getTransform();
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return spikes.getVelocity();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		spikesGraphics = new ImageGraphics("spikes.png", width, 0.7f);
		spikesGraphics.setParent(spikes);
		
	}
	
	//Creating spikes part (ghost).
	@Override
	protected void entityPart(Entity entity) {
		Polygon spikesShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(width, 0f),
				new Vector(width, 0.7f),
				new Vector(0f, 0.7f));
		PartBuilder spikesPartBuilder = entity.createPartBuilder();
		spikesPartBuilder.setShape(spikesShape);
		spikesPartBuilder.setGhost(true);
		spikesPartBuilder.build();
		
	}
	
	public void update(float deltatime) {
		if(ActorGame.getGameOver())
			Bike.setBoostCoefficient(1);
		if(hit && activated) {
			deactivate();
		}
		respawn(super.getTimeAtInteraction());
	
	}
	@Override
	protected void deactivate() {
		Bike.setBoostCoefficient(0.25f);
		hit = false;
		setTimeAtInteraction(System.currentTimeMillis());
		activated = false;
	}
	@Override
	protected void respawn(long timeAtDestroy) {
		if(timeElapsed(timeAtDestroy, slowTime) && !activated) {
	
			Bike.setBoostCoefficient(1f);
			activated = true;
		}
		
	}

}
	