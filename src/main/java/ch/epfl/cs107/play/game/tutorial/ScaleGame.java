package ch.epfl.cs107.play.game.tutorial;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game 
{
	private Window window;
	private World world;
	private Entity ball;
	private Entity basis;
	private Entity planck;
	private ImageGraphics ballGraphics;
	private ImageGraphics basisGraphics;
	private ImageGraphics planckGraphics;
	
	
	 public boolean begin(Window window, FileSystem fileSystem) 
	 {
		 // world & window:
		 this.window = window;
		 world = new World ();
		 world.setGravity(new Vector (0.0f, -9.81f));
		 
		 // Ball Builder:
		 // Entity
		 EntityBuilder ballBuilder = world.createEntityBuilder();
		 ballBuilder.setFixed(false);
		 ballBuilder.setPosition(new Vector (0.5f, 4.0f));
		 ball = ballBuilder.build();
		 
		 //Part
		 PartBuilder ballPart = ball.createPartBuilder();
		 Circle circle = new Circle (0.5f);
		 ballPart.setShape(circle);
		 ballPart.setFriction(0.5f);
		 ballPart.build();
		 
		 //Graphics
		 ballGraphics = new ImageGraphics ("explosive.11.png", 2.0f*circle.getRadius(), 2.0f*circle.getRadius(), 
				 		new Vector(0.5f, 0.5f));
		 ballGraphics.setParent(ball);
		 
		 //Basis & planck:
		 //Entity
		 EntityBuilder basisBuilder = world.createEntityBuilder();
		 EntityBuilder planckBuilder = world.createEntityBuilder();
		 basisBuilder.setPosition(new Vector(-5.0f,-1.0f));
		 planckBuilder.setPosition(new Vector (-2.5f,-0.8f));
		 basisBuilder.setFixed(true);
		 planckBuilder.setFixed(false);
		 basis = basisBuilder.build();
		 planck = planckBuilder.build();
		 
		 
		 // Part
		 PartBuilder basisPart = basis.createPartBuilder();
		 PartBuilder planckPart = planck.createPartBuilder();
		 Polygon rectangle = new Polygon (new Vector(0f, 0f),
				 			  			  new Vector(10.0f, 0.0f),
				 			  			  new Vector(10.0f, 1.0f),
				 			  			  new Vector(0f, 1.0f));
		 Polygon rectangle2 = new Polygon (new Vector(0f, 0f),
	  			  						   new Vector(5f, 0f),
	  			  						   new Vector(5f, 0.2f),
	  			  						   new Vector(0f, 0.2f));
		 basisPart.setShape(rectangle);
		 planckPart.setShape(rectangle2);
		 basisPart.build();
		 planckPart.build();
		
		 //Graphics
		 basisGraphics = new ImageGraphics ("stone.broken.4.png", 10.0f, 1.0f);
		 planckGraphics = new ImageGraphics ("wood.3.png", 5.0f, 0.2f );
		 basisGraphics.setParent(basis);
		 planckGraphics.setParent(planck);
		 
		 // Constraints
		 RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder() ;
		 revoluteConstraintBuilder.setFirstEntity(basis) ;
		 revoluteConstraintBuilder.setFirstAnchor(new Vector(basisGraphics.getWidth()/2,(basisGraphics.getHeight()*7)/4)) ;
		 revoluteConstraintBuilder.setSecondEntity(planck) ;
		 revoluteConstraintBuilder.setSecondAnchor(new Vector(planckGraphics.getWidth()/2,planckGraphics.getHeight()/2)) ;
		 revoluteConstraintBuilder.setInternalCollision(true) ;
		 revoluteConstraintBuilder.build() ;
		 return true;
		        
		    }

	 // This event is called at each frame
	    @Override
	    public void update (float deltatime)
		 {
			 world.update(deltatime);
			 ballGraphics.draw(window);
			 planckGraphics.draw(window);
			 basisGraphics.draw(window);
			 window.setRelativeTransform(Transform.I.scaled(10.0f));
	    
	     if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) 
	        {
	        	
	        	ball.applyAngularForce(10.0f) ;
	        	
	        } else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) 
	        {
	        	ball.applyAngularForce(-10.0f) ;
	        }
	      // The actual rendering will be done now, by the program loop
	    
	    }

	    // This event is raised after game ends, to release additional resources
	    @Override
	    public void end() {
	        // Empty on purpose, no cleanup required yet
	    }
	    
	

}


