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

public class Spring extends Trigger implements Actor {
	private Entity spring;
	private BasicContactListener listener;
	private float width;
	private ImageGraphics springGraphics;
	private Vector force;
	private long respawnTime;
	
	private boolean extended = false;
	private boolean contactWithWheel = false;
	
	public Spring(ActorGame game, Vector position, float width, Vector force, long respawnTime) {
		super(game, true, position, respawnTime);
		if(force == null)
			throw new NullPointerException("foce is null.");
		if(width <= 0 || respawnTime <= 0 || !(force instanceof Vector))
			throw new IllegalArgumentException("Invalid argument.");
		spring = super.getEntity();
		this.respawnTime = super.getTimerDuration();
		this.width = width;
		this.force = force;
		entityPart(spring);
		listener = new BasicContactListener() {
			Bike bike = (Bike) getOwner().getPayLoad();
			public void beginContact(Contact contact) {
				if (!bike.compareHitbox(contact.getOther().getEntity())) {
					return;
				}
				contactWithWheel = true;
			}
	
			@Override
			public void endContact(Contact contact) {}
		};
		entityGraphics(spring);
		spring.addContactListener(listener);
		
			
 		
	}
	@Override
	public void draw(Canvas canvas) {
		springGraphics.draw(canvas);
		
	}
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return spring.getTransform();
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return spring.getVelocity();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		springGraphics = new ImageGraphics("jumper.normal.png", width, 0.5f);
		springGraphics.setParent(spring);
		
	}
	@Override
	protected void entityPart(Entity entity) {
		Polygon springShape = new Polygon (
				new Vector(0f, 0f),
				new Vector(width, 0f),
				new Vector(width, 1.5f),
				new Vector(0f, 1.5f));
		PartBuilder springPartBuilder = entity.createPartBuilder();
		springPartBuilder.setShape(springShape);
		springPartBuilder.setGhost(true);
		springPartBuilder.build();
		
	}
	public void update(float deltatime) {
		
		if (contactWithWheel && !extended) {
			deactivate();
			super.setTimeAtInteraction(System.currentTimeMillis());
		}
		respawn(getTimeAtInteraction());

		
	}
	
	//Spring extending method
	@Override
	protected void deactivate() {
		Bike bike = (Bike) getOwner().getPayLoad();
		bike.applyImpulse(force);
		springGraphics = new ImageGraphics("jumper.extended.png", width, 2);
		springGraphics.setParent(spring);
		setTimeAtInteraction(System.currentTimeMillis());	
		extended = true;
		
	}
	
	//Spring retracting method.
	@Override
	protected void respawn(long timeAtDestroy) {
		contactWithWheel= false;
		if(timeElapsed(timeAtDestroy, respawnTime)) {
			extended = false;
			springGraphics = new ImageGraphics("jumper.normal.png", width, 0.5f);
			springGraphics.setParent(spring);
		}
	}
}

