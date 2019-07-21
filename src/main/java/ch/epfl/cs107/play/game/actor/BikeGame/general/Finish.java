package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.BikeGame;
import ch.epfl.cs107.play.game.actor.BikeGame.bike.Bike;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends Trigger implements Actor {
	private Entity finishLine;
	private BasicContactListener trigger;
	private ImageGraphics finishLineGraphics;
	private static boolean arrived = false;
	private long finishTime;
	private TextGraphics message1;
	private TextGraphics message2;
	private TextGraphics message3;
	
	public Finish(ActorGame game, Vector position) {
		super(game, true, position, Long.MAX_VALUE);
		finishLine = super.getEntity();
		entityGraphics (finishLine);
		entityPart (finishLine);
		trigger = new BasicContactListener() {
			Bike bike = (Bike) getOwner().getPayLoad();
			@Override
			public void beginContact(Contact contact) {
				if(!bike.compareHitbox(contact.getOther().getEntity()) && !bike.compareWheel(contact.getOther().getEntity())) {
					return;
				}
				else if(arrived)
					return;
				else {
					arrived = true;
					finishTime = System.currentTimeMillis();
				}
			}
			@Override
			public void endContact(Contact contact) {}
		};
		finishLine.addContactListener(trigger);
		message1 = new TextGraphics("You Win", 0.3f, Color.RED, Color.WHITE , 0.02f,
				true , false , new Vector(0.5f, 2f), 1.0f, 20.0f) ;
		message1.setParent(getOwner().getCanvas()) ;
		message1.setRelativeTransform(Transform.I.translated(0.0f, -1.0f)) ;
		message2 = new TextGraphics("", 0.1f, Color.BLACK, Color.WHITE , 0.02f,
				true , false , new Vector(0.5f, 1f), 1.0f, 20.0f) ;
		message2.setParent(getOwner().getCanvas());
		message2.setRelativeTransform((Transform.I.translated(0.0f, -1f)));
		message3 = new TextGraphics("", 0.1f, Color.BLACK, Color.WHITE , 0.02f,
				true , false , new Vector(0.5f, 0.3f), 1.0f, 20.0f) ;
		message3.setParent(getOwner().getCanvas());
		message3.setRelativeTransform((Transform.I.translated(0.0f, -1f)));
	}
	

	@Override
	public void draw(Canvas canvas) {
		finishLineGraphics.draw(canvas);
	}
	public static boolean getArrived () {
		return arrived;
	}
	public static void setArrived (boolean b) {
		arrived = b;
	}
	
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void entityGraphics(Entity entity) {
		finishLineGraphics = new ImageGraphics ("flag.red.png", 1f, 3f);
		finishLineGraphics.setParent(finishLine);
		
	}

	@Override
	protected void entityPart(Entity entity) {
		Circle circle = new Circle (2f, new Vector (0f, 2f ));
		PartBuilder finishPart = entity.createPartBuilder();
		finishPart.setGhost(true);
		finishPart.setShape(circle);
		finishPart.build();
	}
	@Override
	public void update (float deltatime) {
		if (arrived) {
			message1.draw(getOwner().getCanvas());
			message2.setText("Your score: " + BikeGame.getScore());
			message2.draw(getOwner().getCanvas());
			message3.setText("Your time: " + ((int) (finishTime - BikeGame.getStartTime())/1000) + "seconds");
			message3.draw(getOwner().getCanvas());
		}
		
		
	}


	@Override
	protected void deactivate() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void respawn(long timeAtDestroy) {
		// TODO Auto-generated method stub
		
	}

	
	
}