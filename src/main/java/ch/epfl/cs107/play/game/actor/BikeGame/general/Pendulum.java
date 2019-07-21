package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.RopeConstraint;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Pendulum extends GameEntity implements Actor{
	private float ropeLength;
	private float radius;
	
	private Entity pendulum;
	private Entity attachPoint;
	
	private RopeConstraint constraint;
	
	private ImageGraphics pendulumGraphics;
	private ShapeGraphics ropeGraphics;
	
	private Polyline rope;
	
	public Pendulum(ActorGame game, Vector attachPointPosition, float ropeLength, float radius) {
		super(game, true, attachPointPosition);
		if(ropeLength <= 0 || radius <= 0)
			throw new IllegalArgumentException("negative argument exception.");
		attachPoint = super.getEntity();
		this.ropeLength = ropeLength;
		this.radius = radius;
		pendulum = getOwner().entityBuilder(false, new Vector(attachPointPosition.getX() + ropeLength,
				attachPointPosition.getY()));
		entityPart(pendulum);
		entityGraphics(pendulum);
		attach();
	}
	//Creating the rope constraint.
	private void attach() {
		constraint = getOwner().ropeConstraintBuilder(getEntity(), pendulum, new Vector(0.005f, 0.005f),
				Vector.ZERO, ropeLength, false);	
	}
	
	@Override
	public void draw(Canvas canvas) {
		rope = new Polyline(attachPoint.getPosition(), pendulum.getPosition()); //Defining the polyline to account for the new position
		ropeGraphics = new ShapeGraphics(rope, Color.BLACK, Color.ORANGE, 0.1f);
		ropeGraphics.draw(canvas);
		pendulumGraphics.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return pendulum.getTransform();
	}

	@Override
	public Vector getVelocity() {
		return pendulum.getVelocity();
	}

	//Creating the imageGraphicsfor the pendulum.
	@Override
	protected void entityGraphics(Entity entity) {
		pendulumGraphics = new ImageGraphics("metal.broken.11.png", 2* radius, 2 * radius, new Vector(0.5f, 0.5f));
		pendulumGraphics.setParent(pendulum);
		
		
	}
	
	
	//Creating the part for the pendulum.
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder entityPartBuilder = entity.createPartBuilder();
		Circle circle = new Circle(radius);
		entityPartBuilder.setShape(circle); 
		entityPartBuilder.build();
		
		
	}
}

