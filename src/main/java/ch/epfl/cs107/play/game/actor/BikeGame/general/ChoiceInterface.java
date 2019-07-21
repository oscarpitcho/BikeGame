package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GraphicInterface;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ChoiceInterface extends GraphicInterface implements Actor {
		
		private ImageGraphics maleSample;
		private ImageGraphics zombieSample;
		private ImageGraphics femaleSample;
		private ImageGraphics maleChoice;
		private ImageGraphics femaleChoice;
		private ImageGraphics zombieChoice;
		private TextGraphics intro;
		
		//Attributes for the choice of the character
		private static boolean chosen = false;
		private static int choice = 0;
		
	public ChoiceInterface (ActorGame game) {
		super (game);
		maleChoice = new ImageGraphics ("box.1.enabled.png", 5f, 5f, new Vector (-15/5f , -2/5f));
		femaleChoice = new ImageGraphics ("box.1.enabled.png", 5f, 5f, new Vector (-0f , -2/5f));
		zombieChoice =  new ImageGraphics ("box.1.enabled.png", 5f, 5f, new Vector (15/5f, -2/5f));
		maleSample = new ImageGraphics ("male_head.png" , 2.5f, 2.5f, new Vector (-16.25f/2.5f, -3.25f/2.5f));
		femaleSample = new ImageGraphics ("female_head.png", 2.5f, 2.5f, new Vector (-1.25f/2.5f, -3.25f/2.5f));
		zombieSample = new ImageGraphics ("zombie_head.png", 2.5f, 2.5f, new Vector (13.75f/2.5f, -3.25f/2.5f));
		intro = new TextGraphics("Choose your Character.", 3f, Color.WHITE, Color.RED , 0.02f,
				true , false , new Vector(0.5f, 0f), 1.0f, 100.0f) ;
		intro.setRelativeTransform(Transform.I.translated(0.0f, 0f)) ;
		chosen = false;
	}
	
	//Static methods used to access the information about the choice of character
	public static int getChoice () {
		return choice;
	}
	public static boolean getChosen () {
		return chosen;
	}

	@Override
	public void draw(Canvas canvas) {
		maleChoice.draw(canvas);
		femaleChoice.draw(canvas);
		zombieChoice.draw(canvas);
		maleSample.draw(canvas);
		zombieSample.draw(canvas);
		femaleSample.draw(canvas);
		intro.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}

	
	public void update (float deltatime) {
		if (getOwner().getMouse().getLeftButton().isPressed() 
			&&  -15f < getOwner().getMouse().getPosition().getX() 
			&&  getOwner().getMouse().getPosition().getX() < -10f 
			&&  2f < getOwner().getMouse().getPosition().getY() 
			&&  getOwner().getMouse().getPosition().getY()<7f) {
			choice = 3;
			chosen = true;
			destroy = true;
		}
		else if (getOwner().getMouse().getLeftButton().isPressed() 
				&& 0 < getOwner().getMouse().getPosition().getX() 
				&&  getOwner().getMouse().getPosition().getX() < 5f 
				&& 2f < getOwner().getMouse().getPosition().getY() 
				&&  getOwner().getMouse().getPosition().getY()<7f) {
			choice = 2;
			chosen = true;
			destroy = true;
		}
		else if (getOwner().getMouse().getLeftButton().isPressed() 
				&& 15f < getOwner().getMouse().getPosition().getX() 
				&&  getOwner().getMouse().getPosition().getX() < 20f 
				&& 	2f < getOwner().getMouse().getPosition().getY() 
				&&  getOwner().getMouse().getPosition().getY()<7f) {
			choice = 1;
			chosen = true;
			destroy = true;
		}
	}
}