package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.util.Iterator;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityWell extends GameEntity implements Actor{
	private Entity gravityWell;
	private BasicContactListener listener;
	private float width;
	private ImageGraphics gravityWellGraphics;
	private Vector force;
	private float height;
	public GravityWell(ActorGame game, Vector position, float width, float height, Vector force) {
		super(game, true, position);
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Negative argument exception.");
		if(force == null)
			throw new NullPointerException("Null argument force.");
		gravityWell = super.getEntity();
		this.width = width;
		this.height = height;
		this.force = force;
		entityPart(gravityWell);
		listener = new BasicContactListener();
		entityGraphics(gravityWell);
		gravityWell.addContactListener(listener);
			
 		
	}
	@Override
	public void draw(Canvas canvas) {
		gravityWellGraphics.draw(canvas);
		
	}
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return gravityWell.getTransform();
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return gravityWell.getVelocity();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		gravityWellGraphics = new ImageGraphics("arrow.blue.png", 2, 2);
		gravityWellGraphics.setParent(gravityWell);
		
	}
	//Creating the part linked with the gravity well(ghost).
	@Override
	protected void entityPart(Entity entity) {
		Polygon gravityWellShape = new Polygon (
				new Vector(0f, 0f),
				new Vector(width, 0f),
				new Vector(width, height),
				new Vector(0f, height));
		PartBuilder gravityWellPartBuilder = entity.createPartBuilder();
		gravityWellPartBuilder.setShape(gravityWellShape);
		gravityWellPartBuilder.setGhost(true);
		gravityWellPartBuilder.build();
		
	}
	
	public void update(float deltatime) {
		//Apply force on all non-fixed entities in contact with the well.
		for (Iterator<Entity> i = listener.getEntities().iterator(); i.hasNext();) {
		    Entity item = i.next();
		    item.applyForce(force, null);
		    
		}
	}
}

