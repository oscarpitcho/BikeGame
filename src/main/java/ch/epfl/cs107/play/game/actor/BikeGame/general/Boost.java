package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.BikeGame;
import ch.epfl.cs107.play.game.actor.BikeGame.bike.Bike;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Boost extends Trigger{
	private ImageGraphics boostGraphics;
	private Entity boost;
	private TextGraphics text;
	private long boostDuration;
	private BasicContactListener listener;
	private float boostValue;
	
	private boolean hit = false;
	private boolean activated = true;
	private boolean beginTextDisplay = false;

	public Boost(ActorGame game, Vector position, float boostValue, long boostDuration) {
		super(game, true, position, boostDuration);
		if(boostValue <=0 || boostDuration <= 0)
			throw new IllegalArgumentException("Negative argument exception.");
		boost = super.getEntity();
		this.boostValue = boostValue;
		this.boostDuration = super.getTimerDuration();
		System.out.println(getTimerDuration());
		
		//Building the contact listener.
		listener = new BasicContactListener() {
			Bike bike = (Bike) getOwner().getPayLoad();
			@Override
			public void beginContact(Contact contact) {
				if (!((bike.compareHitbox(contact.getOther().getEntity()) || bike.compareWheel(contact.getOther().getEntity())) && !beginTextDisplay)) {
					return;
				}
				hit = true;
			}
	
			@Override
			public void endContact(Contact contact) {}
		};
		boost.addContactListener(listener);
		text = new TextGraphics("SPEED!", 0.3f,  Color.RED, Color.WHITE , 0.02f,
				true , false , new Vector(0.5f, 2f), 1.0f, 20.0f) ;
		text.setParent(getOwner().getCanvas()) ;
		text.setRelativeTransform(Transform.I.translated(0f, -1f));
		entityPart(boost);
		entityGraphics(boost);
	}
	

	//Method to temporarily deactivate the boost.
	@Override
	protected void deactivate() {
		Bike.setBoostCoefficient(boostValue);
		beginTextDisplay = true;
		hit = false;
		activated = false;
		setTimeAtInteraction(System.currentTimeMillis());

		
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(!beginTextDisplay) {
			boostGraphics.draw(canvas);
			
		}
		else {
			if(!timeElapsed(getTimeAtInteraction(), boostDuration)) {
				text.draw(canvas);
				
			}
		}	
	}
	
	@Override
	protected void respawn(long timeAtDestroy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void entityGraphics(Entity entity) {
		boostGraphics = new ImageGraphics("star.gold.png", 1, 1);
		boostGraphics.setParent(boost);
		
	}
	//Build the part relative to the boost
	@Override
	protected void entityPart(Entity entity) {
		Polygon boostShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(1f, 0f),
				new Vector(1f, 1f),
				new Vector(0f, 1f));
		PartBuilder boostPartBuilder = entity.createPartBuilder();
		boostPartBuilder.setShape(boostShape);
		boostPartBuilder.setGhost(true);
		boostPartBuilder.build();
		
	}
	
	public void update(float deltaTime) {
		//Setting the boost to 1 to avoid it continuing after reset.
		if(ActorGame.getGameOver())
			Bike.setBoostCoefficient(1);
		if(hit && activated) {
			deactivate();
		}
		if(timeElapsed(getTimeAtInteraction(), boostDuration) && !activated) {
			Bike.setBoostCoefficient(1);
			activated = true;
			beginTextDisplay = false;
		}
	}	
}

