package ch.epfl.cs107.play.game.actor.BikeGame.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraint;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class RotatingScale extends GameEntity implements Actor{
	private float rotationSpeed;
	private Entity scale;
	private ImageGraphics scaleGraphics;
	private float scaleLength;
	private RevoluteConstraint constraint;
	public RotatingScale(ActorGame game, Vector centerOfRotationPosition, float scaleLength, float rotationSpeed) {
		super(game, true, centerOfRotationPosition);
		if(scaleLength <= 0)
			throw new IllegalArgumentException("Negative argument exception.");
		this.scaleLength = scaleLength;
		this.rotationSpeed = rotationSpeed;
		scale = getOwner().entityBuilder(false,
				new Vector(centerOfRotationPosition.getX() - scaleLength/2, centerOfRotationPosition.getY() - 0.2f));
	entityPart(scale);
	entityGraphics(scale);
	buildRevoluteConstraint();
	
	}



	@Override
	public void draw(Canvas canvas) {
		scaleGraphics.draw(canvas);
		
	}

	@Override
	public Transform getTransform() {
		return scale.getTransform();
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Building the revolute constraint.
	private void buildRevoluteConstraint() {
		constraint = getOwner().revoluteConstraintBuilder(getEntity(), scale, Vector.ZERO, new Vector(scaleLength/2, 0.1f), false); 
	}

	//Creating the imageGraphics.
	@Override
	protected void entityGraphics(Entity entity) {
		scaleGraphics = new ImageGraphics("wood.4.png", scaleLength, 0.2f);
		scaleGraphics.setParent(scale);
		
		
		
	}
	
	//Creating the part of the scale.
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder scalePart = entity.createPartBuilder();
		Polygon scaleShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(scaleLength, 0f),
				new Vector(scaleLength, 0.2f),
				new Vector(0f, 0.2f));
		scalePart.setShape(scaleShape);
		scalePart.build();
		
	}
	@Override
	public void update(float delatatime) {
		if(rotationSpeed != 0)
				scale.setAngularVelocity(rotationSpeed);
	}
}