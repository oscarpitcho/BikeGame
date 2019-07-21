package ch.epfl.cs107.play.game.actor.BikeGame.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Timer;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ItsRainingDucks extends GameEntity implements Actor, Timer{
	private Entity duckZone;
	private long duckFrequency;
	private Vector position;
	private float width;
	private Vector rainDirection;
	private long now = System.currentTimeMillis();
	private float duckSpawnHeight;
	
	public ItsRainingDucks(ActorGame game, Vector position, float width, long duckFrequency, float duckSpawnHeight, Vector rainDirection) {
		super(game, true, position);
		if (width <= 0 || duckFrequency <= 0 || duckSpawnHeight <= 0 || !(rainDirection instanceof Vector))
			throw new IllegalArgumentException("Invalid argument exception.");
		if(position == null)
			throw new NullPointerException("null argument rainDirection.");
		duckZone = super.getEntity();
		this.position = position;
		this.width = width;
		this.duckFrequency = duckFrequency;
		this.duckSpawnHeight = duckSpawnHeight;
		this.rainDirection = rainDirection;
		entityPart(duckZone);
		
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return duckZone.getTransform();
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return duckZone.getVelocity();
	}
	
	private void spawnDuck() {
		Duck duck = new Duck(getOwner(), new Vector((float) Math.random() * width + position.getX(), (float) duckSpawnHeight + position.getY()), duckZone);
		duck.applyImpulse(rainDirection); 
		duck.addEntity();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	//Creating the part linked with the duckZone.
	@Override
	protected void entityPart(Entity entity) {
		Polygon duckZoneShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(0f, width),
				new Vector(width, duckSpawnHeight),
				new Vector(0f, duckSpawnHeight));
		PartBuilder duckZonePartBuilder = entity.createPartBuilder();
		duckZonePartBuilder.setShape(duckZoneShape);
		duckZonePartBuilder.setGhost(true);
		duckZonePartBuilder.build();
		
	}
	public void update(float deltaTime) {
		if(timeElapsed(now, duckFrequency)) {
			now = System.currentTimeMillis();
			spawnDuck();
			
		}
	}
}
class Duck extends GameEntity implements Actor, Timer{
	private Entity duck;
	private ImageGraphics duckGraphics;
	private Part duckPart;
	
	private BasicContactListener listener;
	
	private long timeOfFall;
	private final long duckTimeout = 300;
	
	private boolean fall = false;
	private boolean endAnimation = false;
	
	protected Duck(ActorGame game, Vector position, Entity zoneEntity) {
		super(game, false, position);
		if(zoneEntity == null)
			throw new NullPointerException("Null argument zoneEntity.");
		if(!(zoneEntity instanceof Entity))
			throw new IllegalArgumentException("Illegal argument zoneEntity.");
		duck = super.getEntity();
		entityPart(duck);
		entityGraphics(duck);
		
		//Building the listener for the ducks
		listener = new BasicContactListener() {
			public void beginContact(Contact contact) {
				//Filtering out the contact with the duckZone entity.
				if (contact.getOther().getEntity() == zoneEntity) {
					return;
				}
				fall = true;
			}
	
			@Override
			public void endContact(Contact contact) {}
		};
		duck.addContactListener(listener);
	}
	@Override
	public void draw(Canvas canvas) {
		duckGraphics.draw(canvas);
		
	}
	
	protected void applyImpulse(Vector vector) {
		duck.applyImpulse(vector, null);
	}
	
	protected  void applyAngularImpulse (float rotationSpeed) {
		duck.applyAngularImpulse(rotationSpeed);
	}
	
	//Allows the class ItsRainingDucks to add ducks to the actor list.
	protected void addEntity() {
		getOwner().addEntity(this);
	}
	
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return duck.getTransform();
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return duck.getVelocity();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		duckGraphics = new ImageGraphics("duck.png", 1, 1);
		duckGraphics.setParent(duck);
		
	}
	
	//Builds the part (ghost) of the duck.
	@Override
	protected void entityPart(Entity entity) {
		Polygon duckShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(0f, 1f),
				new Vector(1f, 1f),
				new Vector(0f, 1f));
		PartBuilder duckPartBuilder = entity.createPartBuilder();
		duckPartBuilder.setGhost(false);
		duckPartBuilder.setShape(duckShape);
		duckPart = duckPartBuilder.build();
		
	}
	@Override
	public boolean getDestroy() {
		return destroy;
	}
	@Override
	public void update(float deltaTime) {
		if(fall) {
			timeOfFall = System.currentTimeMillis();
			
			//New fixed entity that stays in place for the animation.
			duck = getOwner().entityBuilder(true, duck.getPosition());
			duckPart.destroy();
			fall = false;
			duckGraphics.setParent(duck);
			
		}
		//Displaying the first part of the animation.
		if(!timeElapsed(timeOfFall, duckTimeout/2)) {
			duckGraphics = new ImageGraphics("smoke.yellow.1.png", 1, 1);
			duckGraphics.setParent(duck);
			endAnimation = true;			
		}
		//Displaying the second part of the animation
		else if(!timeElapsed(timeOfFall + duckTimeout/2, duckTimeout)) {
			duckGraphics = new ImageGraphics("smoke.yellow.3.png", 1, 1);
			duckGraphics.setParent(duck);
		}
		
		//Destroying the duck after the end of the animation.
		else if (timeElapsed(timeOfFall, duckTimeout) && endAnimation) {
			destroy = true;
		}
	}
	
	

}
