package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

class Crate extends GameEntity implements Actor {
	private Entity crate;
	private ImageGraphics graphics;

	public Crate(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		entityGraphics (getEntity());
		entityPart (getEntity());
		crate = super.getEntity();	
	}
	public Crate(ActorGame game, boolean fixed) {
		super(game, fixed);
		entityGraphics (getEntity());
		entityPart (getEntity());
		crate = super.getEntity();	
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphics.draw(canvas);
	}
	@Override
	public Transform getTransform() {
		return crate.getTransform();
	}
	@Override
	public Vector getVelocity() {
		return crate.getVelocity();
	}
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder entityPartBuilder = entity.createPartBuilder();
		Polygon polygon = new Polygon (
				new Vector (0.0f,0.0f),
				new Vector (1.0f, 0.0f),
				new Vector (1.0f, 1.0f),
				new Vector (0.0f, 1.0f));
		entityPartBuilder.setShape(polygon);
		entityPartBuilder.build();
	}
	@Override
	protected void entityGraphics(Entity entity) {
		graphics  = new ImageGraphics("stone.broken.4.png", 1f, 1f);
		graphics.setParent(entity);
	}
	
	
}
