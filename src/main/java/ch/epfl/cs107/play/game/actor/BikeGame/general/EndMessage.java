package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GraphicInterface;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.BikeGame;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class EndMessage extends GraphicInterface implements Actor{
	private TextGraphics endMessage;
	private String messageContent;
	public EndMessage(ActorGame game, String message) {
		super(game);
		if(!(message instanceof String))
			throw new IllegalArgumentException("Illegal argument message.");
		this.messageContent = message;
		endMessage = new TextGraphics(message, 0.2f,  Color.RED, Color.WHITE , 0.02f,
				true , false , new Vector(0.5f, 2f), 1.0f, 20.0f) ;
		endMessage.setParent(game.getCanvas()) ;
		endMessage.setRelativeTransform(Transform.I.translated(0f, -1f));
	}
	@Override
	public void draw(Canvas canvas) {
		//System.out.println(BikeGame.getLost());
		if(ActorGame.getGameOver())
			endMessage.draw(canvas);
		
		
		
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
	

}
