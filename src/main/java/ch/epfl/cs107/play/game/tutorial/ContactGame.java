package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;


public class ContactGame implements Game
{
	private Window window;
	private World world;
	private Entity ball;
	private Entity block;
	private ShapeGraphics ballGraphics;
	private ImageGraphics blockGraphics;
	private BasicContactListener contactDetector;

	public boolean begin(Window window, FileSystem fileSystem) 
	{
		// Window & World
		this.window = window;
		world = new World ();
		world.setGravity(new Vector (0.0f, -9.81f));
		// Ball:
		// Entity:
		EntityBuilder ballBuilder = world.createEntityBuilder();
		ballBuilder.setPosition(new Vector (0.0f,5.0f));
		ballBuilder.setFixed(false);
		ball = ballBuilder.build();
		// Part:
		PartBuilder ballPart = ball.createPartBuilder();
		Circle circle = new Circle (1f);
		ballPart.setShape(circle);
		ballPart.setFriction(0.5f);
		ballPart.build();
		// Graphics:
		ballGraphics = new ShapeGraphics(circle ,Color.BLUE, Color.BLUE, 0.1f,1.0f, 0.0f);
		ballGraphics.setParent(ball);
		// Contact Detector:
		contactDetector = new BasicContactListener() ;
		ball.addContactListener(contactDetector) ;
		
		// Basis
		EntityBuilder blockBuilder = world.createEntityBuilder();
        blockBuilder.setFixed(true);
        blockBuilder.setPosition(new Vector (-5.0f, -1.0f));
        block = blockBuilder.build();
        blockGraphics = new ImageGraphics ("crate.1.png" , 10, 1);
        blockGraphics.setDepth(0);
        blockGraphics.setParent(block);
        PartBuilder part1 = block.createPartBuilder();
        Polygon square = new Polygon(new Vector (0.0f, 0.0f), 
	 					 			 new Vector (10.0f, 0.0f), 
	 					 			 new Vector (10.0f, 1.0f), 
	 					 			 new Vector (0.0f, 1.0f));
        part1.setShape(square); 
        part1.setFriction(0.5f);
        part1.build();
		
		
		
	       
	       
	    
	        return true;
	 }

	   
	    @Override
    public void update(float deltaTime) 
    {
	      world.update(deltaTime);
	      ballGraphics.draw(window);
	      blockGraphics.draw(window);
	      window.setRelativeTransform(Transform.I.scaled(20f));
	      int numberOfCollisions = contactDetector.getEntities().size() ;
	      if (numberOfCollisions > 0){
	      ballGraphics.setFillColor(Color.RED) ;
	      }
	     
    }

	   
	    @Override
    public void end() 
    {
	      
    }
	    
}


