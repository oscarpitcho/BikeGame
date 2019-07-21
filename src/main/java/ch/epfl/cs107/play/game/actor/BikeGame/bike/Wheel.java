package ch.epfl.cs107.play.game.actor.BikeGame.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {
	private Entity wheel;
	private ImageGraphics wheelGraphics;
	private Circle circle;
	private WheelConstraint constraint;

	public Wheel(ActorGame game, boolean fixed, Vector position, float radius ) {
		super(game, fixed, position);
		if(radius <= 0)
			throw new IllegalArgumentException("Negative value exception");
		wheel = super.getEntity();
		circle = new Circle (radius);
		entityGraphics (wheel);
		entityPart (wheel);	
	}
	public Wheel(ActorGame game, boolean fixed, float radius) {
		super(game, fixed);
		wheel = super.getEntity();
		circle = new Circle (radius);
		entityGraphics (wheel);
		entityPart (wheel);
	}
	protected boolean compareWheel(Entity entity) {
		if(entity == wheel)
			return true;
		else
			return false;
	}
	public void addBasicContactListner(BasicContactListener listener) {
		wheel.addContactListener(listener);
	}
	@Override
	public void draw(Canvas canvas) {
		wheelGraphics.draw(canvas);
	}
	@Override
	public Transform getTransform() {
		return wheel.getTransform();
	}
	@Override
	public Vector getVelocity() { 
		return wheel.getVelocity();
	}
	
	//Creates the graphics associated to the wheels.
	@Override
	protected void entityGraphics(Entity entity) {
		wheelGraphics = new ImageGraphics ("stone.11.png", 2.0f*circle.getRadius(), 2.0f*circle.getRadius(), 
		 		new Vector (0.5f, 0.5f));
		wheelGraphics.setParent(entity);
	}
	
	//Builds the part asosciated to the wheel.
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder wheelPart = entity.createPartBuilder();
		wheelPart.setShape(circle);
		wheelPart.setCollisionEffect(1);
		wheelPart.build();
	}
	//Creates a constraint between the wheel and an entity.
	protected void attach(Entity vehicle , Vector anchor , Vector axis) {
		constraint = getOwner().constraintBuilder(vehicle, anchor, axis, wheel, constraint);
	}
	//Motorization of the wheels.
	protected void power(float speed) {
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(-speed);
	}
	
	
	protected void relax() {
		constraint.setMotorEnabled(false);
		
	}
	public void detach() {
		constraint.destroy();
	}
	public float getAngularVelocity() {
		return wheel.getAngularVelocity();
	}
	public float getSpeed() {
		return wheel.getAngularVelocity() - constraint.getFirstBody().getAngularVelocity() ;
	}
	
	


}
