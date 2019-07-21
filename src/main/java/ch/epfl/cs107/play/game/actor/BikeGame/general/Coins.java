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

public class Coins extends Trigger{
	private ImageGraphics coinGraphics;
	private Entity coin;
	private TextGraphics text;
	private final long textDuration = 1500;
	private BasicContactListener listener;
	private boolean hit = false;
	private boolean beginTextDisplay = false;
	private boolean golden;
	private boolean temp1 = true;
	private int scoreValue;
	
	public Coins(ActorGame game, Vector position, boolean golden) {
		super(game, true, position, Long.MAX_VALUE);
		coin = super.getEntity();
		this.golden = golden;
		
		//Building the listener linked with the coin.
		listener = new BasicContactListener() {
			Bike bike = (Bike) getOwner().getPayLoad();
			@Override
			public void beginContact(Contact contact) {
				if (bike.compareHitbox(contact.getOther().getEntity()) || bike.compareWheel(contact.getOther().getEntity())) {
						hit = true;
				}
				return;
			}
	
			@Override
			public void endContact(Contact contact) {}
		};
		coin.addContactListener(listener);
		if(golden) {
			scoreValue = 400;
			text = new TextGraphics("+400!", 1f, Color.RED, Color.WHITE , 0.02f,
					true , false , new Vector(0.5f, 0.5f), 1.0f, 20.0f) ;
		}
		else {
			scoreValue = 100;
			text = new TextGraphics("+100!", 1f, Color.RED, Color.WHITE , 0.02f,
					true , false , new Vector(0.5f, 0.5f), 1.0f, 20.0f);
		}
		text.setParent(coin) ;
		text.setRelativeTransform(Transform.I.translated(0, -1f));
		entityPart(coin);
		entityGraphics(coin);
	}
	


	@Override
	protected void deactivate() {
		BikeGame.addScore(scoreValue);
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(!beginTextDisplay) {
			coinGraphics.draw(canvas);
			
		}
		else {

			if(!timeElapsed(timeAtInteraction, textDuration)) {
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
		if(golden) {
			coinGraphics = new ImageGraphics("coin.gold.png", 1, 1);
		}
		else {
			coinGraphics = new ImageGraphics("coin.bronze.png", 1, 1);
		}
		coinGraphics.setParent(coin);
		
	}
	
	//Building the ghost part associated to the boost.
	@Override
	protected void entityPart(Entity entity) {
		Polygon coinShape = new Polygon(
				new Vector(0f, 0f),
				new Vector(1f, 0f),
				new Vector(1f, 1f),
				new Vector(0f, 1f));
		PartBuilder coinPartBuilder = entity.createPartBuilder();
		coinPartBuilder.setShape(coinShape);
		coinPartBuilder.setGhost(true);
		coinPartBuilder.build();
		
	}
	@Override
	public void update(float deltaTime) {
		//Deactivating the coin.
		if(hit && temp1) {
			beginTextDisplay = true;
			temp1 = false;
			hit = false;
			setTimeAtInteraction(System.currentTimeMillis());
			deactivate();
		}
		//Destroying the actor after the text timeouts.
		if(beginTextDisplay && timeElapsed(getTimeAtInteraction(), textDuration)) {
			destroy = true;
		}
	}
}

